package io.oz.wnw.serv.my;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

import org.xml.sax.SAXException;

import io.odysz.common.Utils;
import io.odysz.semantic.DATranscxt;
import io.odysz.semantic.jprotocol.AnsonMsg;
import io.odysz.semantic.jprotocol.AnsonMsg.MsgCode;
import io.odysz.semantic.jprotocol.AnsonResp;
import io.odysz.semantic.jserv.JSingleton;
import io.odysz.semantic.jserv.ServPort;
import io.odysz.semantic.jserv.helper.Html;
import io.odysz.semantic.jserv.user.UserReq;
import io.odysz.semantic.jserv.x.SsException;
import io.odysz.semantics.IUser;
import io.odysz.semantics.x.SemanticException;
import io.odysz.transact.sql.Insert;
import io.odysz.transact.sql.Update;
import io.odysz.transact.x.TransException;
import io.oz.wnw.serv.protocol.Wnport;
import io.oz.wnw.serv.utils.WeaverFlags;

/**Get user's notifications.
 */
@WebServlet(description = "normal/notify.weaver", urlPatterns = { "/notify.weaver" })
public class Notify extends ServPort<UserReq> {
	private static final long serialVersionUID = 1L;

	public Notify() {
		super(null);
		p = Wnport.notify;
	}

	static DATranscxt st;

	static {
		try {
			// this constructor can only been called after metas has been loaded
			// (Jsingleton got a chance to initialize)
			st = new DATranscxt("inet");
		} catch (SemanticException | SQLException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onGet(AnsonMsg<UserReq> req, HttpServletResponse resp)
			throws IOException {
		if (WeaverFlags.my)
			Utils.logi("---------- servs.Business A GET ----------");
		try {
			resp.getWriter().write(Html.ok("Please visit POST."));
		} finally {
			resp.flushBuffer();
		}
	}

	@Override
	protected void onPost(AnsonMsg<UserReq> jmsg, HttpServletResponse resp)
			throws IOException {
		if (WeaverFlags.my)
			Utils.logi("========== servs.Business A POST ==========");

		resp.setCharacterEncoding("UTF-8");
		try {
//			AnsonMsg<UserReq> jmsg = ServletAdapter.<UserReq>read(req, jReq, UserReq.class);
			IUser usr = JSingleton.getSessionVerifier().verify(jmsg.header());

			UserReq jreq = jmsg.body(0);

			NotifyResp rsp = null;
			if ("A".equals(jreq.a()))
				rsp = A(jreq, usr);
			else if ("B".equals(jreq.a()))
				rsp = B(jreq, usr);
			else if ("C".equals(jreq.a()))
				rsp = C(jreq, usr);
			else throw new SemanticException("request.body.a can not handled: %s", jreq.a());

			AnsonMsg<AnsonResp> rp = ok(rsp);
			
			write(resp, rp);
		} catch (SemanticException e) {
			write(resp, err(MsgCode.exSemantic, e.getMessage()));
		} catch (SQLException | TransException e) {
			if (WeaverFlags.my)
				e.printStackTrace();
			write(resp, err(MsgCode.exTransct, e.getMessage()));
		} catch (SsException e) {
			write(resp, err(MsgCode.exSession, e.getMessage()));
		} finally {
			resp.flushBuffer();
		}
	}

	private NotifyResp A(UserReq req, IUser usr) throws TransException {
		String borrowId = (String) req.get("borrowId");
		@SuppressWarnings("unchecked")
		ArrayList<String[]> items = (ArrayList<String[]>) req.get("items");
		
		int total = 0;
		Insert ins = st.insert("detailsTbl", usr);
		for (String[] it : items) {
			ins.nv(it[0], it[1]);
			try { total += Integer.valueOf(it[1]); }
			catch (Exception e) {}
		}

		Update upd = st.update("borrowTbl", usr)
				.nv("total", String.valueOf(total))
				.where_("=", "borrowId", borrowId);
		
		
		ArrayList<String> sqls = new ArrayList<String>();
		st.delete((String)req.tabl(), usr)
				.where_("=", "borrowId", borrowId)
				.post(ins)
				.post(upd)
				// If calling D() instead of commit() here, should also committed logs to DB (cmd = save)
				.commit(sqls, usr);
		
		Utils.logi(sqls);

		return new NotifyResp(p).msg(String.valueOf(sqls.size()));
	}

	private NotifyResp B(UserReq req, IUser usr) {
		return new NotifyResp(p)
				.msg("B");
	}

	private NotifyResp C(UserReq req, IUser usr) {
		return new NotifyResp(p)
				.msg("C");
	}
}
