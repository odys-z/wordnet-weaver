package io.oz.xv.treemap;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import io.oz.jwi.SynsetInf;
import io.oz.jwi.WMemory;
import io.oz.wnw.ecs.cmp.Word;
import io.oz.wnw.ecs.cmp.ds.AffineTrans;
import io.oz.wnw.ecs.cmp.ds.AffineType;
import io.oz.xv.ecs.c.Affines;
import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.ecs.c.RayPickable;
import io.oz.xv.ecs.c.Visual;
import io.oz.xv.ecs.s.RayPicker;
import io.oz.xv.ecs.s.RayPicker.PickingShape;
import io.oz.xv.gdxpatch.utils.QuadShapeBuilder;
import io.oz.xv.gdxpatch.utils.XModelBuilder;
import io.oz.xv.glsl.shaders.Cubic;
import io.oz.xv.glsl.shaders.PlaneStar;
import io.oz.xv.material.XMaterial;
import io.oz.xv.material.bisheng.GlyphLib;
import io.oz.xv.utils.XVException;

/**
 * <p>
 * Cube with Children of blinking word stars.
 * </p>
 * At the exact positions of hyponyms? And even shape clue of word length?<br>
 * 
 * <h6>Design Notes:</h6> A CubeTree has 2 cube layers: the sky and the
 * ground.<br>
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

	private static long uuid = 0;

	private static long getId() {
		return uuid++;
	}

	private static XMaterial groundSkin;

	private static XMaterial starMatrl;

	private static Color _colr;

	/**
	 * @param font
	 */
	public static void init(String font) {
		glyphs = new GlyphLib(font == null ? GlyphLib.defaultFnt : font, false);

		Visual vCubic = new Visual();
		Cubic c = new Cubic(vCubic);
		c.init();
		vCubic.shader = c;
		groundSkin = new XMaterial("cube-skin").visual(vCubic);

		Visual vStar = new Visual();
		PlaneStar s = new PlaneStar(vStar);
		s.init();
		vStar.shader = s;
		starMatrl = new XMaterial().visual(vStar);

		_colr = new Color();
	}

	public static void create(PooledEngine ecs, ArrayList<SynsetInf> synsets) throws XVException {
		Space2dContext context = new Space2dContext(ecs);
		context.init(synsets.size());

		createGround(context);

		for (SynsetInf si : synsets) {
			context = createCube(si, context);
		}
	}

	/**
	 * Create a cube skin box
	 * 
	 * @param context
	 * @return
	 */
	private static Space2dContext createGround(Space2dContext context) {
		PooledEngine ecs = (PooledEngine) context.ecs;
		Entity ground = ecs.createEntity();
		ecs.addEntity(ground);

		Obj3 obj3 = ecs.createComponent(Obj3.class);

		// TODO optimize builder usage
		Vector3 whd = context.space();
		ModelBuilder builder = new ModelBuilder();
		builder.begin();
		MeshPartBuilder mpbuilder = builder.part("ground-" + getId(), GL20.GL_TRIANGLES,
				Usage.Position | Usage.ColorUnpacked | Usage.TextureCoordinates | Usage.Normal, groundSkin);
		BoxShapeBuilder.build(mpbuilder, whd.x, whd.y, whd.z); // test size
		Model model = builder.end();
		model.calculateTransforms();
		// FIXME
		// FIXME
		// FIXME
		// obj3.modInst = new XModelInstance(model);

		ground.add(obj3);

		RayPickable pickable = ecs.createComponent(RayPickable.class);
		pickable.uuid = RayPicker.uuId();
		pickable.pickingShape = PickingShape.box;
		pickable.whd = obj3.modInst.calculateBoundingBox(pickable.whd);
		pickable.entity = ground;
		ground.add(pickable);

		Affines aff = ecs.createComponent(Affines.class);
		aff.transforms = new Array<AffineTrans>();
		aff.transforms.add(new AffineTrans(AffineType.translate).translate(0, -20, -50));
		ground.add(aff);

		return context;
	}

	/**
	 * Create lemma node of stars, word, etc.
	 * <p>
	 * This method will create an entity managed by ECS engine, no node returned.
	 * </p>
	 * 
	 * @param si
	 * @param context
	 * @return context
	 */
	static Space2dContext createCube(SynsetInf si, Space2dContext context) {
		if (si == null)
			return context;

		PooledEngine ecs = (PooledEngine) context.ecs;
		Entity eLemma = ecs.createEntity();
		ecs.addEntity(eLemma);

		Word wrd = ecs.createComponent(Word.class);
		// wrd.word = si.lemma();
		// wrd.color, ...
		setWordVisual(si, wrd);
		wrd.children = si.getMemory();
		eLemma.add(wrd);

		Visual lemVis = new Visual();
		eLemma.add(lemVis);

		Obj3 obj3 = ecs.createComponent(Obj3.class);
		// FIXME
		// FIXME
		// FIXME
		// FIXME
		// obj3.modInst = glyphs.bindText(wrd.word, wrd.color, lemVis );

		eLemma.add(obj3);

		Affines aff = ecs.createComponent(Affines.class);
		initAffine(aff, si, context);
		eLemma.add(aff);
		
		RayPickable pickable = ecs.createComponent(RayPickable.class);
		pickable.uuid = RayPicker.uuId();
		pickable.pickingShape = PickingShape.box;
		pickable.whd = obj3.modInst.calculateBoundingBox(pickable.whd);
		pickable.entity = eLemma;
		eLemma.add(pickable);

		HashMap<String,WMemory> memory = si.getMemory();
		if (memory != null) {
			Space2dContext childCtx = new Space2dContext(context).init(memory.size());
			XModelBuilder builder = new XModelBuilder();
			builder.begin();
			for (String w : memory.keySet())
				addStarVisual(builder, w, memory.get(w), childCtx);

			Model model = builder.end();
			model.calculateTransforms();
			obj3.orthoFace = new ModelInstance(model);
		}

		return context;
	}

	/**Setup word's visual
	 * @param si
	 * @param wrd
	 */
	private static void setWordVisual(SynsetInf si, Word wrd) {
		wrd.word = si.lemma();
		wrd.color = new Color(1f, 1f, 0f, 0f);
	}

	private static void initAffine(Affines aff, SynsetInf si, Space2dContext context) {
		Cell2D n = context.allocatCell().rotate(30f, 0, 0f);

		// aff.pos = n.pos();
		aff.transforms = new Array<AffineTrans>();
		aff.transforms.add(new AffineTrans(AffineType.scale).scale(si.txtWeight()));
		aff.transforms.add(new AffineTrans(AffineType.translate).translate(n.pos().scl(context.space())));
		aff.transforms.add(new AffineTrans(AffineType.rotation).rotate(n.rotate()));
		aff.transforms.add(new AffineTrans(AffineType.translate).translate(n.offset()));
	}

	private static void addStarVisual(XModelBuilder builder, String word, WMemory wMemory, Space2dContext contxt) {
		MeshPartBuilder mpbuilder = builder.part(word, GL20.GL_TRIANGLES,
				Usage.Position | Usage.ColorUnpacked | Usage.TextureCoordinates | Usage.Normal, starMatrl);
		Cell2D grid = contxt.allocatCell();
		float mem = wMemory.memory;
		QuadShapeBuilder.build(mpbuilder, grid.pos(), _colr.set(mem, mem / 3, 0, 1), 3.6f, 3.6f); 
	}
}
