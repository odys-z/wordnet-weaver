package io.oz.jwi;

/**Synset descriptor.
 * @author Odys Zhou
 *
 */
public class SynsetInf {

	private String root;

	public SynsetInf(String topLemma) {
		this.root = topLemma;
	}
	
	public String name() {
		return root;
	}

}
