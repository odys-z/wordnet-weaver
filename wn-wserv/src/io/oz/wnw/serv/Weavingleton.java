package io.oz.wnw.serv;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import io.odysz.semantic.jprotocol.AnsonMsg;
import io.odysz.semantic.jserv.JSingleton;
import io.oz.wnw.serv.protocol.Wnport;

@WebListener
public class Weavingleton extends JSingleton implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		super.onInitialized(sce);
		
		String relapath = null;
		// Because of the java enum limitation, or maybe the author's knowledge limitation, 
		// JMessage needing a IPort instance to handle ports that implemented a new version of valof() method handling all ports.<br>
		// E.g. {@link Samport#menu#valof(name)} can handling both {@link Port} and Samport's enums.
		AnsonMsg.understandPorts(Wnport.menu);

		// HashMap<String, ICheapChecker> checker = null; // To be tested

		// relapath = Configs.getCfg("cheap", "config-path");
		// meta must loaded by DATranscxt before initCheap()
		// CheapEnginv1.initCheap(getFileInfPath(relapath), checker);
//		} catch (IOException e) {
//			e.printStackTrace();
//			Utils.warn("%s: %s\nCheck Config.xml:\ntable=cheap\nk=config-path\nv=%s",
//					e.getClass().getlemma(), e.getMessage(), relapath);
		System.out.println("try:\nhttp://127.0.0.1:8080/wnw/echo.weaver");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// CheapEnginv1.stopCheap();
		super.onDestroyed(sce);
	}

}
