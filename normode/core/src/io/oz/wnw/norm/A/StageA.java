package io.oz.wnw.norm.A;

import java.util.Map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import io.oz.jwi.Synset;
import io.oz.wnw.ecs.sys.SysModelRenderer;
import io.oz.wnw.ecs.cmp.Affine;
import io.oz.wnw.ecs.cmp.Obj3;
import io.oz.wnw.ecs.sys.SysAffine;
import io.oz.wnw.my.MyWeaver;
import io.oz.xv.material.bisheng.GlyphLib;
import io.oz.xv.math.Geoshape;
import io.oz.xv.utils.Xutils;

/**Scene A's world / objects manager.
 * 
 * @author Odys Zhou
 */
public class StageA {

	public Map<String, Synset> synsets;

	private GlyphLib glyphs;

	private MyWeaver me;

	private PooledEngine ecs;
	public PooledEngine engine() { return ecs; }

	public StageA(MyWeaver me) {
		this.me = me;
		ecs = new PooledEngine();
	}

	/** @deprecated only for ViewA1Try
	 * @param ecs
	 * @param me
	 */
	public StageA(PooledEngine ecs, MyWeaver me) {
		this.me = me;
		this.ecs = ecs;
	}

	public void init(ScreenAdapter viewA1) {
		glyphs = new GlyphLib("font/verdana39distancefield.fnt", false);
		
		ecs.addSystem(new SysAffine());
		ecs.addSystem(new SysModelRenderer());
		
		// setup objects

	}

	/** @deprecated only for ViewA1Try() */
	ModelInstance loadSnyset() {
		ModelInstance mi = glyphs.bindText(me.myset().name(), new Color(1f, 1f, 0f, 1f));
		return mi;
	}

	Entity loadMyset() {
		Entity entity = ecs.createEntity();

		Obj3 obj3 = ecs.createComponent(Obj3.class);
		// ModelInstance mi = glyphs.bindText(me.myset().name(), new Color(1f, 1f, 0f, 1f));
		obj3.modInst = Xutils.modelInstance(Geoshape.cube);
		entity.add(obj3);
		
		Affine aff = ecs.createComponent(Affine.class);
		aff.pos(0f, 0f, 0f);

		return entity;
	}

	public void update(float delta) {
		ecs.update(delta);
	}

}
