package io.oz.jwi;

import java.util.HashMap;

/**<h6>Synset Descriptor</h6>
 * The synset information for rendering preparation
 * - avoid loading data for most of it is asynchronous.
 * 
 * @author Odys Zhou
 *
 */
public class SynsetInf {
	private void load(String lemma) {
		if ("dreamweaver".equals(lemma)) {
			setMemory("Moana", 1);
			setMemory("Tinkle", 2);
			setMemory("Coco", 3);
		}
		else if ("indigenous".equals(lemma)) {
			setMemory("Tibetan", 1);
			setMemory("Inuit", 2);
			setMemory("Rapa Nui", 3);
		}
		else if ("Sumer food".equals(lemma)) {
			setMemory("lentil", 0);
			setMemory("millet", 1);
			setMemory("sesame", 2);
			setMemory("chickpea", 3);
			setMemory("leek", 4);
			setMemory("fig", 5);
			setMemory("melic", 2);
			setMemory("granit fruit", 0);
		}
	}

	private String root;
	private HashMap<String, WMemory> memory;

	public SynsetInf(String topLemma) {
		this.root = topLemma;
		memory = new HashMap<String, WMemory>();
	}
	
	public String lemma() {
		return root;
	}

	public HashMap<String, WMemory> getMemory() {
		if (memory == null || memory.size() == 0) {
			load(root);
		}
		return memory;
	}

	public float txtWeight() { return 0.1f; }

	public SynsetInf setMemory(String word, int memo) {
		if (!memory.containsKey(word))
			memory.put(word, new WMemory());
		memory.get(word).memory = memo;
		return this;
	}
}
