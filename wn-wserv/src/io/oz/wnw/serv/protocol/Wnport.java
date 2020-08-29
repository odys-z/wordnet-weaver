package io.oz.wnw.serv.protocol;

import java.io.IOException;
import java.io.OutputStream;

import io.odysz.anson.IJsonable;
import io.odysz.anson.JSONAnsonListener;
import io.odysz.anson.JsonOpt;
import io.odysz.anson.x.AnsonException;
import io.odysz.semantic.jprotocol.IPort;
import io.odysz.semantic.jprotocol.AnsonMsg.Port;
import io.odysz.semantics.x.SemanticException;

/**Wordnet.weaver prots*/
public enum Wnport implements IPort {
	/**port provided by {@link io.odysz.jsample.SysMenu} */
	menu("menu.weaver"),
	/** user notifications notify.weaver */
	notify("notify.weaver"),
	/**Port of memory status & health report*/
	health("health.weaver"),
	/** reading tasks, etc. */
	readings("reading.weaver"),
	/**Port of tutor's function */
	tutor("tutor.weaver");

	static {
		JSONAnsonListener.registFactory(Wnport.class,
			(s) -> {
				return Wnport.valueOf(s);
			});
	}

	private String url;
	Wnport(String v) { url = v; };
	public String url() { return url; }
	@Override
	public IPort valof(String pname) throws SemanticException {
		try {
			IPort p = Port.valueOf(pname);
			return p;
		} catch (Exception e) {
			try { return valueOf(pname); }
			catch (IllegalArgumentException ex) {
				throw new SemanticException(ex.getMessage());
			}

		}
	}

	@Override
	public IJsonable toBlock(OutputStream stream, JsonOpt... opts) throws AnsonException, IOException {
		stream.write('\"');
		stream.write(url.getBytes());
		stream.write('\"');
		return this;
	}

	@Override
	public IJsonable toJson(StringBuffer buf) throws IOException, AnsonException {
		buf.append('\"');
		buf.append(url);
		buf.append('\"');
		return this;
	}
}
