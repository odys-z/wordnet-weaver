package io.oz.xv.glsl.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.glsl.WShader;

public class PlaneStar extends WShader {
	protected int u_alpha = register("u_alpha");
	protected int u_tex0 = register("u_tex0");


	static String vs;
	static String fs;

	private float uAlpha;

	/** texture of u_tex0 */
	Texture tex = null;

	public PlaneStar() {
		super(ShaderFlag.cubic);
		vs = Gdx.files.classpath("io/oz/xv/glsl/shaders/plane-star.vert.glsl").readString();
		fs = Gdx.files.classpath("io/oz/xv/glsl/shaders/plane-star.frag.glsl").readString();
		program = new ShaderProgram(vs, fs);
	}
	
	@Override
	public void init() {
		super.init();

		uAlpha = 0.5f;

		Pixmap pixmap;
		pixmap = new Pixmap(256, 256, Format.RGBA8888);
		pixmap.setColor(0.3f, 0.0f, 0.1f, 1);
		pixmap.fill();
		pixmap.setColor(0, 0, 0, 1);
		pixmap.drawLine(0, 0, 256, 256);
		pixmap.drawLine(256, 0, 0, 256);
		tex = new Texture(pixmap);
		pixmap.dispose();

	}
	
	@Override
	public void begin(Camera camera, RenderContext context) {
		super.begin(camera, context);
		set(u_alpha, uAlpha);
		int uTex0 = context.textureBinder.bind(tex);
		set(u_tex0, uTex0);
	}

}
