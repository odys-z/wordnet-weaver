package io.oz.wnw.norm.A;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import io.oz.jwi.SynsetInf;
import io.oz.wnw.ecs.sys.SysModelRenderer;
import io.oz.wnw.ecs.cmp.Affines;
import io.oz.wnw.ecs.cmp.Obj3;
import io.oz.wnw.ecs.cmp.ds.AffineTrans;
import io.oz.wnw.ecs.cmp.ds.AffineType;
import io.oz.wnw.ecs.sys.SysAffine;
import io.oz.wnw.my.MyWeaver;
import io.oz.xv.material.bisheng.GlyphLib;
import io.oz.xv.treemap.CubeTree;
import io.oz.xv.utils.XVException;

/**Scene A's world / objects manager.
 * 
 * @author Odys Zhou
 */
public class StageA {

	public ArrayList<SynsetInf> synsets;

	private GlyphLib glyphs;

	private MyWeaver me;

	private PooledEngine ecs;
	public PooledEngine engine() { return ecs; }

	public StageA(MyWeaver me) {
		this.me = me;
		glyphs = new GlyphLib(GlyphLib.defaultFnt, false);
		ecs = new PooledEngine();
	}

	/** @deprecated only for ViewA1Try
	 * @param ecs
	 * @param me
	 */
	public StageA(PooledEngine ecs, MyWeaver me) {
		this.me = me;
		glyphs = new GlyphLib(GlyphLib.defaultFnt, false);
		this.ecs = ecs;
	}

	public void init(ViewA1 viewA1) {
		// setup objects
		ecs.addSystem(new SysAffine());
		ecs.addSystem(new SysModelRenderer(viewA1.cam()));
		
		synsets = new ArrayList<SynsetInf>();
		synsets.add(me.myset());
	}

	/** @deprecated only for ViewA1Try() */
	ModelInstance loadSnyset() {
		ModelInstance mi = glyphs.bindText(me.myset().lemma(), new Color(1f, 1f, 0f, 1f));
		return mi;
	}

	/**@deprecated Tried OK for testing ViewA1
	 * @return
	 */
	Entity loadMysetry() {
		Entity entity = ecs.createEntity();
		ecs.addEntity(entity);

		Obj3 obj3 = ecs.createComponent(Obj3.class);
		// works: obj3.modInst = Xutils.modelInstance(Geoshape.cube, new Vector3(5, 5, 5));
		obj3.modInst = glyphs.bindText(me.myset().lemma(), new Color(1f, 1f, 0f, 1f));
		entity.add(obj3);
		
		Affines aff = ecs.createComponent(Affines.class);
		aff.pos = new Vector3(0f, 0f, 0f);

		aff.transforms = new Array<AffineTrans>();
		aff.transforms.add(new AffineTrans(AffineType.scale).scale(0.1f));
		aff.transforms.add(new AffineTrans(AffineType.rotation).rotate(60f, 0f, 0f));
		aff.transforms.add(new AffineTrans(AffineType.translate).translate(60f, 0f, 0f));

		entity.add(aff);

		return entity;
	}

	void loadMyset() throws XVException {
		CubeTree.init(null);
		CubeTree m = new CubeTree();
		m.create(ecs, synsets);
	}

	/**Create treemap node for a word.
	 * @param name
	 * @param context
	private void wordNode(String word, TreeContext context) {
		Entity entity = ecs.createEntity();
		ecs.addEntity(entity);

		Obj3 obj3 = ecs.createComponent(Obj3.class);
		obj3.modInst = glyphs.bindText(word, context.color(word));
		entity.add(obj3);
		
		Affines aff = ecs.createComponent(Affines.class);
		
		context.setAffine(aff, word);
		entity.add(aff);
	}
	 */

	public void update(float delta) {
		ecs.update(delta);
	}

}
