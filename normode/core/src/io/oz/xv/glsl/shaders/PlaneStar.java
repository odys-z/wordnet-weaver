package io.oz.xv.glsl.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
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
	protected int u_noise = register("u_noise");

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
	private double time0;

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

		// FIXME share textures
		// FIXME why byr0.png not working?
		// tex = new Texture(Gdx.files.internal("tex/byr0.png"));
		tex = new Texture(Gdx.files.internal("tex/tex12.png"));

		xuni.f1(u_alpha, 1f);
		xuni.f1(u_noise, 0.1f);
		
		time0 = System.currentTimeMillis();
	}
	
	@Override
	public void begin(Camera camera, RenderContext context) {
		super.begin(camera, context);

		// a shader is updating it's uniforms
		xuni.f1(u_alpha);
		xuni.f1(u_noise);
		float snds = (float) ((System.currentTimeMillis() - time0) / 1000);
//		System.out.println(snds);
		xuni.f1(u_time, snds);
		
		if (tex != null) {
			xuni.sampler2D(u_tex0, tex, context); // now shader has context
			tex = null;
		}
		else xuni.sampler2D(u_tex0);
	}
}
