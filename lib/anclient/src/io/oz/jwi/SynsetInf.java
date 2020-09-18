package io.oz.jwi;

import java.util.ArrayList;

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

	@SuppressWarnings("serial")
	public ArrayList<String> childrenName() {
		return new ArrayList<String>() {
			{add("Tibetan");}
			{add("Inuit");}
			{add("Rapa Nui");}
		};
	}
}
