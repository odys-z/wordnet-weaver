package io.oz.wnw.serv.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

import io.odysz.anson.x.AnsonException;
import io.odysz.semantic.jprotocol.AnsonMsg;
import io.odysz.semantic.jserv.ServPort;
import io.odysz.semantic.jserv.helper.Html;
import io.odysz.semantic.jserv.user.UserReq;
import io.odysz.semantics.x.SemanticException;
import io.oz.wnw.serv.protocol.Wnport;

@WebServlet(description = "totor's functions", urlPatterns = { "/health.weaver" })
public class Health extends ServPort<UserReq> {
	private static final long serialVersionUID = 1L;

	public Health() {
		super(null);
		p = Wnport.health;
	}

	@Override
	protected void onGet(AnsonMsg<UserReq> msg, HttpServletResponse resp)
			throws ServletException, IOException, AnsonException, SemanticException {
		try {
			resp.getWriter().write(Html.ok("Please use html POST request."));
		} finally {
			resp.flushBuffer();
		}

	}

	@Override
	protected void onPost(AnsonMsg<UserReq> msg, HttpServletResponse resp)
			throws ServletException, IOException, AnsonException, SemanticException {
		
	}

}
