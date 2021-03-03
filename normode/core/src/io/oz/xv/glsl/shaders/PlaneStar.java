package io.oz.xv.glsl.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import io.oz.xv.ecs.c.Visual;
import io.oz.xv.glsl.Glsl.ShaderFlag;

/**Shader of word's star rendered in a plane facing screen.
 * @author Odys Zhou
 *
 */
public class PlaneStar extends WShader {
	protected int u_alpha = register("u_alpha");
	protected int u_tex0 = register("u_tex0");
	protected int u_time = register("u_time");

	/**For translate plane facing scring,
	 * see https://stackoverflow.com/a/5487981
	protected int u_shiftM4 = register("u_shiftM4");
	protected Matrix4 shiftM4 = new Matrix4();
	 * */

	static String vs;
	static String fs;

	XUniformer xuni;
	public Visual visual;
	// no obj3?

	/** texture of u_tex0 */
	Texture tex = null;

	/**
	 * @param visual given Visual that ecs tweening, moving or updating
	 */
	public PlaneStar(Visual visual) {
		super(ShaderFlag.cubic, visual);
		vs = Gdx.files.classpath("io/oz/xv/glsl/shaders/plane-star2.vert.glsl").readString();
		// fs = Gdx.files.classpath("io/oz/xv/glsl/shaders/plane-star.frag.glsl").readString();
		fs = Gdx.files.classpath("io/oz/xv/glsl/shaders/plane-star.fragx.glsl").readString();

		this.visual = visual;
		program = new ShaderProgram(vs, fs);
		this.xuni = new XUniformer(this, visual);
	}
	
	@Override
	public void init() {
		enableViewM4();
		super.init();

		/*
		Pixmap pixmap;
		pixmap = new Pixmap(256, 256, Format.RGBA8888);
		pixmap.setColor(0.0f, 0.0f, 1f, 1);
		pixmap.fill();
		pixmap.setColor(0, 0, 0, 1);
		pixmap.drawLine(0, 0, 256, 256);
		pixmap.drawLine(256, 0, 0, 256);
		tex = new Texture(pixmap);
		pixmap.dispose();
		*/
		// FIXME share textures
		tex = new Texture(Gdx.files.internal("tex/byr0.png"));

		xuni.f1(u_alpha, 1f);
	}
	
	@Override
	public void begin(Camera camera, RenderContext context) {
		super.begin(camera, context);

		// a shader is updating it's uniforms
		xuni.f1(u_alpha);
		xuni.f1(u_time, System.currentTimeMillis() % 1000);
		
		if (tex != null) {
			xuni.sampler2D(u_tex0, tex, context); // now shader has context
			tex = null;
		}
		else xuni.sampler2D(u_tex0);
	}
}
