package io.oz.jwi;

import java.util.ArrayList;
import java.util.HashMap;

/**<h6>Synset Descriptor</h6>
 * The synset information for rendering preparation
 * - avoid loading data for most of it is asynchronous.
 * 
 * @author Odys Zhou
 *
 */
public class SynsetInf {
	@SuppressWarnings("serial")
	static HashMap<String, ArrayList<String>> web =
			new HashMap<String, ArrayList<String>>(2){
		{put("indigenous", new ArrayList<String>() {
			{add("Tibetan");}
			{add("Inuit");}
			{add("Rapa Nui");}
		});}
		{put("Sumer food", new ArrayList<String>() {
			{add("lentil");}
			{add("millet");}
			{add("sesame");}
			{add("chickpea");}
			{add("leek");}
			{add("fig");}
			{add("melic");}
			{add("granit fruit");}
		});}
	};

	private String root;

	public SynsetInf(String topLemma) {
		this.root = topLemma;
	}
	
	public String name() {
		return root;
	}

	@SuppressWarnings("serial")
	public ArrayList<String> childrenName() {
		if (web.containsKey(root))
			return web.get(root);
		else
			return new ArrayList<String>() {
				{add("Dreamweaver");}
			};
	}
}
