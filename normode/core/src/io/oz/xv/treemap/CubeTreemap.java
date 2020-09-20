package io.oz.xv.treemap;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;

import io.oz.jwi.SynsetInf;
import io.oz.wnw.ecs.cmp.Affines;
import io.oz.wnw.ecs.cmp.Obj3;
import io.oz.wnw.ecs.cmp.Word;
import io.oz.wnw.ecs.cmp.ds.AffineTrans;
import io.oz.wnw.ecs.cmp.ds.AffineType;
import io.oz.xv.material.bisheng.GlyphLib;
import io.oz.xv.utils.XVException;

/**
 * Children blinking word stars in it's cube, at the exact positions of hyponyms?
 * And even shape clue of word length?<br>
 * 
 * @author Odys Zhou
 */
@SuppressWarnings("unchecked")
public class CubeTreemap {
	private GlyphLib glyphs;
	
	/** active lemma index */
	private int lmx = -1;
	/** Layer index */
	private int lyx = 0;
	/**
	 * <p>Layer Cubes:<br>
	 * hypernym, lemma, hyponym</p>
	 * <p>"lemma" means word base form, see <a href='https://wordnet.princeton.edu/documentation/wngloss7wn'>
	 * wngloss(7WN) wordnet documentation</a>:</p>
	 * <p>Lower case ASCII text of word as found in the WordNet database index files.</p>
	 * <p>Cube State:<br>
	 * stable, selected, dig-down, on-sky<br>
	 * Usually the base form for a word or collocation.</p>
	 */
	private ArrayList<?>[][] layers = new ArrayList<?>[3][];

	public ArrayList<TreemapNode>[] hypernyms() {
		return (ArrayList<TreemapNode>[]) layers[lyx];
	}
	public ArrayList<TreemapNode>[] lemmas() {
		return (ArrayList<TreemapNode>[]) layers[lyx + 1];
	}
	public ArrayList<TreemapNode>[] hyponyms() {
		return (ArrayList<TreemapNode>[]) layers[lyx + 2];
	}
	
	public CubeTreemap(String font) {
		glyphs = new GlyphLib(font == null ? GlyphLib.defaultFnt : font, false);
	}

	public void create(PooledEngine ecs, ArrayList<SynsetInf> synsets) throws XVException {
		TreeContext context = new TreeContext(ecs).scale(0.25f);
		lmx = 1;
		layers[lmx] = context.init(synsets);
		for (SynsetInf si : synsets) {
			context = cubeNode(si, context);
		}
	}

	/**Create treemap node of a lemma.
	 * @param si
	 * @param context
	 * @return
	 * @throws XVException 
	 */
	private TreeContext cubeNode(SynsetInf si, TreeContext context) throws XVException {
		if (si == null) return context;

		PooledEngine ecs = (PooledEngine) context.ecs;
		Entity entity = ecs.createEntity();
		ecs.addEntity(entity);


		Word wrd = ecs.createComponent(Word.class);
		wrd.word = si.lemma();
		entity.add(wrd);

		Obj3 obj3 = ecs.createComponent(Obj3.class);
		obj3.modInst = glyphs.bindText(wrd.word, context.getColor(wrd.word));
		entity.add(obj3);
		
		Affines aff = ecs.createComponent(Affines.class);
		initAffine(aff, si, context);
		entity.add(aff);
		
		ArrayList<SynsetInf> children = si.children();
		if (children != null) {
			context.zoomin();
			for (SynsetInf child : si.children())
				cubeNode(child, context);
			context.zoomout();
		}

		return context;
	}

	private void initAffine(Affines aff, SynsetInf si, TreeContext context) {
		TreemapNode n = context.allocatNode(si);

		aff.pos = n.pos();
		aff.transforms = new Array<AffineTrans>();
		aff.transforms.add(new AffineTrans(AffineType.scale).scale(n.scale()));
		aff.transforms.add(new AffineTrans(AffineType.rotation).rotate(n.rotate()));
		aff.transforms.add(new AffineTrans(AffineType.translate).translate(n.offset()));
	}
}
