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
 * <p>Cube with Children of blinking word stars.</p>
 * At the exact positions of hyponyms?
 * And even shape clue of word length?<br>
 * 
 * <h6>Design Notes:</h6>
 * A CubeTree has 2 cube layers: the sky and the ground.<br>
 * A Layer has a box of volumetric shading and a group of cubes.<br>
 * CubeTree also has a empty layer used for animation<br>
 * 
 * <img src='../../../../../../../docsphinx/res/cube-layers.png' />
 * 
 * @author Odys Zhou
 */
public class CubeTree {
	final float space = 160f;

	private static GlyphLib glyphs;

	/** active lemma index
	private int lmx = -1; */
	/** Layer index
	private int lyx = 0; */
	/**
	 * <p>Layer Cubes:<br>
	
	 * hypernym, lemma, hyponym</p>
	 * <p>"lemma" means word base form, see <a href='https://wordnet.princeton.edu/documentation/wngloss7wn'>
	 * wngloss(7WN) wordnet documentation</a>:</p>
	 * <p>Lower case ASCII text of word as found in the WordNet database index files.</p>
	 * <p>Cube State:<br>
	 * lemma-stable, selected, push-down(hidden), on-sky<br>
	 * Usually the base form for a word or collocation.</p>
	private ArrayList<TreemapNode[]>[] layers = (ArrayList<TreemapNode[]>[]) new ArrayList<?>[3];

	public ArrayList<TreemapNode[]> hypernyms() {
		return (ArrayList<TreemapNode[]>) layers[lyx]; // correct?
	}
	public ArrayList<TreemapNode[]> lemmas() {
		return (ArrayList<TreemapNode[]>) layers[lyx + 1];
	}
	public ArrayList<TreemapNode[]> hyponyms() {
		return (ArrayList<TreemapNode[]>) layers[lyx + 2];
	}
	 */

	public static void init(String font) {
		glyphs = new GlyphLib(font == null ? GlyphLib.defaultFnt : font, false);
	}

//	public CubeTree(String font) {
//		// glyphs = new GlyphLib(font == null ? GlyphLib.defaultFnt : font, false);
//	}

	public static void create(PooledEngine ecs, ArrayList<SynsetInf> synsets) throws XVException {
		TreeContext context = new TreeContext(ecs); //.space(20f);
		context.init(synsets);

		createGround(context);

		for (SynsetInf si : synsets) {
			context = createCube(si, context);
		}
	}

	private static void createGround(TreeContext context) {
		// TODO Auto-generated method stub
		
	}

	/**Create treemap node of a lemma.
	 * <p>This method create entity managed by ECS engine, no node returned.</p>
	 * @param si
	 * @param context
	 * @return
	 * @throws XVException 
	 */
	private static TreeContext createCube(SynsetInf si, TreeContext context) throws XVException {
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
				createCube(child, context);
			context.zoomout();
		}

		return context;
	}

	private static void initAffine(Affines aff, SynsetInf si, TreeContext context) throws XVException {
		TreemapNode n = context.allocatNode(si).rotate(30f, 0, 0f);

		// aff.pos = n.pos();
		aff.transforms = new Array<AffineTrans>();
		aff.transforms.add(new AffineTrans(AffineType.scale).scale(si.weight()));
		aff.transforms.add(new AffineTrans(AffineType.rotation).rotate(n.rotate()));
		aff.transforms.add(new AffineTrans(AffineType.translate).translate(n.pos().scl(context.space())));
		aff.transforms.add(new AffineTrans(AffineType.translate).translate(n.offset()));
	}
}
