package io.oz.wnw.serv.my;

import io.odysz.semantic.jprotocol.AnsonResp;
import io.odysz.semantic.jprotocol.IPort;

public class NotifyResp extends AnsonResp {

	public NotifyResp(IPort p) { }

	public NotifyResp msg(String string) {
		m = string;
		return this;
	}

}
