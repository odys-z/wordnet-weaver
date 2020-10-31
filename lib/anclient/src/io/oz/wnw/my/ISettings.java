package io.oz.wnw.my;

/**Client's setting persisted.
 * 
 * @author Odys Zhou
 *
 */
public interface ISettings {
	public ISettings load ();
	public ISettings save ();
	public ISettings put (String k, Object v);
	public Object get (String k);
}