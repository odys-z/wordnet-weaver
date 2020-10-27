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
	/**
	 * @deprecated 
	 */
	@SuppressWarnings("serial")
	static HashMap<String, ArrayList<SynsetInf>> web =
			new HashMap<String, ArrayList<SynsetInf>>(3){
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

	@SuppressWarnings("serial")
	static HashMap<String,SynsetInf> wnet =
			new HashMap<String, SynsetInf>(3){
		{put("dreamweaver", new SynsetInf("dreamweaver")
				.setMemory("Moana", 1)
				.setMemory("Tinkle", 2)
				.setMemory("Coco", 3));}
		{put("indigenous", new SynsetInf("indigenous")
				.setMemory("Tibetan", 1)
				.setMemory("Inuit", 2)
				.setMemory("Rapa Nui", 3));}
		{put("Sumer food", new SynsetInf("Sumer food")
				.setMemory("lentil", 0)
				.setMemory("millet", 1)
				.setMemory("sesame", 2)
				.setMemory("chickpea", 3)
				.setMemory("leek", 4)
				.setMemory("fig", 5)
				.setMemory("melic", 2)
				.setMemory("granit fruit", 0));}
	};

	private String root;
	private HashMap<String, WMemory> memory;

	public SynsetInf(String topLemma) {
		this.root = topLemma;
	}
	
	public String lemma() {
		return root;
	}

	/**
	 * @return
	public ArrayList<SynsetInf> children() {
		return web.get(root);
	}
	 */
	
	public HashMap<String,WMemory> getMemory() {
		return wnet.get(root).memory;
	}

	public float txtWeight() { return 0.1f; }

	public SynsetInf setMemory(String word, int memo) {
		if (!memory.containsKey(word))
			memory.put(word, new WMemory());
		memory.get(word).memory = memo;
		return this;
	}
}
