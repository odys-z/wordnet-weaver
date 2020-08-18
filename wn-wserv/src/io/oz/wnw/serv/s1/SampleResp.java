package io.oz.wnw.serv.s1;

import io.odysz.semantic.jprotocol.AnsonResp;
import io.odysz.semantic.jprotocol.IPort;

public class SampleResp extends AnsonResp {

	public SampleResp(IPort p) { }

	public SampleResp msg(String string) {
		m = string;
		return this;
	}

}
