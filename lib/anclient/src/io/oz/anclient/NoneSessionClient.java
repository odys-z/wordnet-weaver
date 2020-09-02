package io.oz.anclient;

import io.odysz.semantic.jsession.SessionInf;

public class NoneSessionClient extends SessionClient {

	NoneSessionClient(String servRt, String conn) {
		super(robotSsInf());
	}

	private static SessionInf robotSsInf() {
		return new SessionInf("uid", "robot"); // mac?
	}

}
