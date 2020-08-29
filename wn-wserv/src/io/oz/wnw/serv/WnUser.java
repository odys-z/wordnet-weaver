package io.oz.wnw.serv;

import io.odysz.semantic.jsession.JUser;
import io.odysz.semantics.meta.TableMeta;
import io.odysz.semantics.x.SemanticException;

public class WnUser extends JUser {
	/**Hard coded field string of user table information.
	 * With this class, sample project's user table can be different from the default table,
	 * providing the same semantics presented.
	 */
	public static class WnUserMeta extends JUserMeta {
		public WnUserMeta(String tbl, String... conn) {
			super(tbl, conn);

			this.tbl = "a_users";
			pk = "userId";
			uname = "userName";
			pswd = "pswd";
			iv = "iv";
		}
	}

	public WnUser(String uid, String pswd, String usrName) throws SemanticException {
		super(uid, pswd, usrName);
	}

	public TableMeta meta() {
		return new WnUserMeta("");
	}

}
