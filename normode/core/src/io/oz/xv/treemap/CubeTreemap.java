package io.oz.xv.treemap;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

import io.oz.jwi.SynsetInf;
import io.oz.wnw.ecs.cmp.Affines;
import io.oz.wnw.ecs.cmp.Obj3;
import io.oz.wnw.ecs.cmp.Word;
import io.oz.xv.material.bisheng.GlyphLib;

public class CubeTreemap {
	private GlyphLib glyphs;
	
	/**<p>Top level cubes</p>
	 * Children blinking word stars in it's cube, at the exact positions of hyponyms?
	 * And even shape clue of word length?<br>
	 * Cube State:<br>
	 * stable, selected, dig-down, on-sky<br>
	 */
	private ArrayList<TreemapNode[]> cubes;

	/** active lemma index */
	private int lmx = -1;
	/** Layer index */
	private int lyx = 0;
	/**
	 * Layers:<br>
	 * hypernym, lemma, hyponym
	 */
	private ArrayList<?>[] layers = (ArrayList<?>[]) new ArrayList<?>[3];
	@SuppressWarnings("unchecked")
	public ArrayList<TreemapNode> hypernyms() {
		return (ArrayList<TreemapNode>) layers[lyx];
	}
	@SuppressWarnings("unchecked")
	public ArrayList<TreemapNode> lemmas() {
		return (ArrayList<TreemapNode>) layers[lyx + 1];
	}
	@SuppressWarnings("unchecked")
	public ArrayList<TreemapNode> hyponyms() {
		return (ArrayList<TreemapNode>) layers[lyx + 2];
	}
	
	public CubeTreemap(String font) {
		glyphs = new GlyphLib(font == null ? GlyphLib.defaultFnt : font, false);
	}

	public void create(PooledEngine ecs, ArrayList<SynsetInf> synsets) {
		this.cubes = createCubes(synsets);
		TreeContext context = new TreeContext();
		context.level = 0;
		SynsetInf me = synsets.get(0);
		wordNode(ecs, me.name(), context);

		context.level = 1;
		for (String cn : me.childrenName())
			wordNode(ecs, cn, context);
	}

	/**Create treemap node for a word.
	 * @param ecs 
	 * @param name
	 * @param context
	 */
	private void wordNode(PooledEngine ecs, String word, TreeContext context) {
		Entity entity = ecs.createEntity();
		ecs.addEntity(entity);

		Obj3 obj3 = ecs.createComponent(Obj3.class);
		obj3.modInst = glyphs.bindText(word, context.color(word));
		entity.add(obj3);
		
		Affines aff = ecs.createComponent(Affines.class);
		context.setAffine(aff, word);
		entity.add(aff);

		Word wrd = ecs.createComponent(Word.class);
		wrd.word = word;
		entity.add(wrd);
	}

	private ArrayList<TreemapNode[]> createCubes(ArrayList<SynsetInf> synsets) {
		return null;
	}

}
