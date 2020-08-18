package io.oz.wnw.serv.s1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

import io.odysz.anson.x.AnsonException;
import io.odysz.semantic.jprotocol.AnsonMsg;
import io.odysz.semantic.jserv.ServPort;
import io.odysz.semantics.x.SemanticException;
import io.oz.wnw.serv.protocol.Wnport;

/**
 * @author odys-z@github.com
 */
@WebServlet(description = "jserv.sample example: extend serv handler", urlPatterns = { "/custom.weave" })
public class WnExample extends ServPort<SampleReq> {
	public WnExample() {
		super(null);
		p = Wnport.example;
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void onGet(AnsonMsg<SampleReq> msg, HttpServletResponse resp)
			throws ServletException, IOException, AnsonException, SemanticException {
	}

	@Override
	protected void onPost(AnsonMsg<SampleReq> msg, HttpServletResponse resp)
			throws ServletException, IOException, AnsonException, SemanticException {
	}

}
