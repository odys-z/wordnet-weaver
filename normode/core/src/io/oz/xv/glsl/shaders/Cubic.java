package io.oz.xv.glsl.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import io.oz.xv.ecs.c.Visual;
import io.oz.xv.glsl.Glsl.ShaderFlag;

public class Cubic extends WShader {
	protected int u_alpha = register("u_alpha");
	protected int u_tex0 = register("u_tex0");

	static String vs;
	static String fs;

	private float uAlpha;

	/** texture of u_tex0 */
	Texture tex = null;

	public Cubic(Visual visual) {
		super(ShaderFlag.cubic, visual);
		vs = Gdx.files.classpath("io/oz/xv/glsl/shaders/cube-skin.vert.glsl").readString();
		fs = Gdx.files.classpath("io/oz/xv/glsl/shaders/cube-skin.frag.glsl").readString();
		program = new ShaderProgram(vs, fs);
	}
	
	@Override
	public void init() {
		super.init();

		uAlpha = 0.7f;

		/*
		Pixmap pixmap;
		pixmap = new Pixmap(256, 256, Format.RGBA8888);
		pixmap.setColor(0.1f, 0.1f, 0.1f, 1);
		pixmap.fill();
		pixmap.setColor(1, 0, 0, 1);
		pixmap.drawLine(0, 0, 256, 256);
		pixmap.drawLine(256, 0, 0, 256);
		tex = new Texture(pixmap);
		pixmap.dispose();
		*/
		tex = new Texture(Gdx.files.internal("tex/byr0.png"));

	}
	
	@Override
	public void begin(Camera camera, RenderContext context) {
		super.begin(camera, context);
		set(u_alpha, uAlpha);
		int uTex0 = context.textureBinder.bind(tex);
		set(u_tex0, uTex0);
	}

//	@Override
//	public WShader setVisual(int cmd, float val) {
//		if (cmd == u_mode) {
//			set(u_mode, val);
//		}
//		return this;
//	}
}
