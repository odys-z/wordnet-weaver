package io.oz.wnw.serv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.odysz.common.Utils;
import io.odysz.semantic.jserv.helper.Html;
import io.oz.wnw.serv.utils.WeaverFlags;

@WebServlet(description = "Dream: echo", urlPatterns = { "/echo.weaver" })
public class Echo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (WeaverFlags.echo)
			Utils.logi("---------- echo.weaver get <- %s ----------", req.getRemoteAddr());

		resp.getWriter().write(Html.ok(req.getRequestURL().toString()));
		resp.flushBuffer();
	}
}
