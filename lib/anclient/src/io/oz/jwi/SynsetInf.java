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
	static HashMap<String, ArrayList<SynsetInf>> web =
			new HashMap<String, ArrayList<SynsetInf>>(2){
		{put("Dreamweaver", new ArrayList<SynsetInf>() {
			{add(new SynsetInf("Moana"));}
			{add(new SynsetInf("Tinkle"));}
			{add(new SynsetInf("Coco"));}
		});}
		{put("indigenous", new ArrayList<SynsetInf>() {
			{add(new SynsetInf("Tibetan"));}
			{add(new SynsetInf("Inuit"));}
			{add(new SynsetInf("Rapa Nui"));}
		});}
		{put("Sumer food", new ArrayList<SynsetInf>() {
			{add(new SynsetInf("lentil"));}
			{add(new SynsetInf("millet"));}
			{add(new SynsetInf("sesame"));}
			{add(new SynsetInf("chickpea"));}
			{add(new SynsetInf("leek"));}
			{add(new SynsetInf("fig"));}
			{add(new SynsetInf("melic"));}
			{add(new SynsetInf("granit fruit"));}
		});}
	};

	private String root;

	public SynsetInf(String topLemma) {
		this.root = topLemma;
	}
	
	public String lemma() {
		return root;
	}

	public ArrayList<SynsetInf> children() {
		return web.get(root);
	}
	
	public float weight() { return 0.1f; }
}
