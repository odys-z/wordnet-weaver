package io.oz.anclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.odysz.anson.x.AnsonException;
import io.odysz.common.AESHelper;
import io.odysz.common.Utils;
import io.odysz.module.rs.AnResultset;
import io.odysz.semantic.DA.DatasetCfg.AnTreeNode;
import io.odysz.semantic.ext.AnDatasetReq;
import io.odysz.semantic.ext.AnDatasetResp;
import io.odysz.semantic.jprotocol.AnsonBody;
import io.odysz.semantic.jprotocol.AnsonHeader;
import io.odysz.semantic.jprotocol.AnsonMsg;
import io.odysz.semantic.jprotocol.AnsonMsg.MsgCode;
import io.odysz.semantic.jserv.R.AnQueryReq;
import io.odysz.semantic.jserv.U.AnInsertReq;
import io.odysz.semantic.jserv.U.AnUpdateReq;
import io.odysz.semantics.x.SemanticException;
import io.oz.wnw.serv.protocol.Wnport;

/**
 * Unit test for Word Dreamer App. 
 */
public class SessionClientTest {
	private static String jserv = null;
	private static String pswd = null;
	private static String filename = "src/test/res/Sun_Yat-sen_2.jpg";
	
	private SessionClient client;

	@BeforeAll
	public static void init() {
		Utils.printCaller(false);
		jserv = System.getProperty("jserv");
		if (jserv == null)
			fail("\nTo test AnsonClient, you need start a jsample server and define @jserv like this to run test:\n" +
				"-Djserv=http://localhost:8080/doc-base\n" +
				"In Eclipse, it is defined in:\n" +
				"Run -> Debug Configurations ... -> Junit [your test case name] -> Arguments");
   		pswd = System.getProperty("pswd");
   		if (pswd == null)
			fail("\nTo test Jclient.java, you need to configure user 'admin' and it's password at jsample server, then define @pswd like this to run test:\n" +
				"-Dpswd=*******");

    	Clients.init(jserv);
    }

    @Test
    public void queryTest() throws IOException,
    		SemanticException, SQLException, GeneralSecurityException, AnsonException {
    	Utils.printCaller(false);

    	String sys = "sys-sqlite";
    	
    	client = Clients.login("admin", pswd);
    	AnsonMsg<AnQueryReq> req = client.query(sys,
    			"a_users", "u",
    			-1, -1); // don't paging

    	req.body(0)
    		.expr("userName", "uname")
    		.expr("userId", "uid")
    		.expr("r.roleId", "role")
    		.j("a_roles", "r", "u.roleId = r.roleId")
    		.where("=", "u.userId", "'admin'");

    	client.commit(req, (code, data) -> {
				List<AnResultset> rses = (List<AnResultset>) data.rs();
  				for (AnResultset rs : rses) {
  					rs.printSomeData(true, 2, "uid", "uname", "role");
  					rs.beforeFirst();
  					while(rs.next()) {
  						String uid0 = rs.getString("uid");
  						assertEquals("admin", uid0);
  								
  						String roleId = rs.getString("role");
  						getMenu("admin", roleId);

  						// function/semantics tests
//  						testUpload(client);
  					}
  				}
    		}, (code, err) -> {
  				fail(err.msg());
  				client.logout();
    	});
    }

	private void getMenu(String string, String roleId)
			throws SemanticException, IOException, SQLException, AnsonException {
		AnDatasetReq req = new AnDatasetReq(null, "sys-sqlite");

		String t = "menu";
		AnsonHeader header = client.header();
		String[] act = AnsonHeader.usrAct("SemanticClientTest", "init", t,
				"test jclient.java loading menu from menu.sample");

		AnsonMsg<? extends AnsonBody> jmsg = client.userReq(Wnport.menu, act, req);
		jmsg.header(header);

		client.console(jmsg);
		
    	client.commit(jmsg, (code, data) -> {
			List<?> rses = ((AnDatasetResp)data).forest();
			// see data in wn-wserv/WebContent/WEB-INF/weave.sqlite
			assertEquals("sys-domain", ((AnTreeNode)((AnTreeNode)rses.get(0)).children().get(0)).get("id"));
			assertEquals("1 sys.1 domain", ((AnTreeNode)((AnTreeNode)rses.get(0)).child(0)).get("fullpath"));
    	});
	}

	static void testUpload(SessionClient client)
			throws SemanticException, IOException, SQLException, AnsonException {
		Path p = Paths.get(filename);
		byte[] f = Files.readAllBytes(p);
		String b64 = AESHelper.encode64(f);

		AnsonMsg<? extends AnsonBody> jmsg = client.update(null, "a_users");
		AnUpdateReq upd = (AnUpdateReq) jmsg.body(0);
		upd.nv("nationId", "CN")
			.whereEq("userId", "admin")
			// .post(((UpdateReq) new UpdateReq(null, "a_attach")
			.post(AnUpdateReq.formatDelReq(null, null, "a_attaches")
					.whereEq("busiTbl", "a_users")
					.whereEq("busiId", "admin")
					.post((AnInsertReq.formatInsertReq(null, null, "a_attaches")
							.cols("attName", "busiId", "busiTbl", "uri")
							.nv("attName", "'s Portrait")
							// The parent pk can't be resulved, we must provide the value.
							// See https://odys-z.github.io/notes/semantics/best-practices.html#fk-ins-cate
							.nv("busiId", "admin")
							.nv("busiTbl", "a_users")
							.nv("uri", b64))));

		jmsg.header(client.header());

		client.console(jmsg);
		
    	client.commit(jmsg,
    		(code, data) -> {
    			// This line can not been tested without branch
    			// branching v1.1
				if (MsgCode.ok.eq(code.name()))
					Utils.logi(code.name());
				else Utils.warn(data.toString());
    		},
    		(c, err) -> {
				fail(String.format("code: %s, error: %s", c, err.msg()));
    		});
	}

}
