package io.oz.wnw.norm.A;

import java.util.Map;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import io.oz.jwi.Synset;
import io.oz.wnw.ecs.sys.RenderingSystem;
import io.oz.wnw.ecs.sys.SysAffine;
import io.oz.wnw.my.MyWeaver;
import io.oz.xv.material.bisheng.GlyphLib;

/**Scene A's world / objects manager.
 * 
 * @author Odys Zhou
 */
public class StageA {

	public Map<String, Synset> synsets;

	private GlyphLib glyphs;

	private MyWeaver me;

	public StageA(PooledEngine ecs, MyWeaver me) {
		this.me = me;
	}

	public void init(ScreenAdapter viewA1, PooledEngine ecs) {
		glyphs = new GlyphLib("font/verdana39distancefield.fnt", false);
		
		ecs.addSystem(new SysAffine());
		ecs.addSystem(new RenderingSystem());
	}

	ModelInstance loadSnyset() {
		ModelInstance mi = glyphs.bindText(me.myset().name(), new Color(1f, 1f, 0f, 1f));
		return mi;
	}

	public void update(PooledEngine ecs) {
	}
}
