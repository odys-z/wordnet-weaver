package io.oz.xv.glsl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

import io.oz.wnw.norm.Assets;
import io.oz.xv.glsl.shaders.Cubic;

/**Shader Factory - factory pattern replacing js style x-visual/xglsl.
 * 
 * @author Odys Zhou
 *
 */
public class Glsl {
	/**Line delimiter for joining glsl source string */
	public static final CharSequence delimiter = System.getProperty("line.separator");

	public static enum ShaderFlag {
		/** shader type of {@link Test} */
		test("test"),
		/** shader type of {@link Simple} */
		simple("simple"),
		/**shader type of {@link Tex0} */
		tex0("tex0"),
		/** shader type of {@link Sdfont} */
		sdfont("sdfont"),
		/** shader type of {@link CubeSkin} */
		cubic("cubeSkin");

		private String p;
		ShaderFlag(String v) { p = v; };
		public String path() { return p; }
	}
	
	/** A test texture shader with color shows normal */
	static class Test extends WShader {
		static String vs = String.join(delimiter, 
			"uniform mat4 u_modelMat4;",
			"uniform mat4 u_vpMat4;",

			"attribute vec4 a_position;",
			"attribute vec3 a_normal;",

			"varying vec3 vColor;",

			"void main() {",
			"	vec4 pos4 = u_modelMat4 * a_position;",
			"	vColor = a_normal;",
			"	gl_Position = u_vpMat4 * pos4;",
			"}");
		static String fs = String.join(delimiter, 
			"varying vec3 vColor;",
			"void main() { gl_FragColor = vec4(abs(vColor), 1.); }"
		);

		public Test() {
			super(ShaderFlag.test);
			program = new ShaderProgram(vs, fs);
		}
	}
	
	/** A cheap texture shader with blue color (0, 0.2, 0.8, 0.75) for testing */
	static class Simple extends WShader {
		static String vs = String.join(delimiter, 
			"uniform mat4 u_modelMat4;",
			"uniform mat4 u_vpMat4;",

			"attribute vec4 a_position;",
			"attribute vec3 a_normal;",

			"void main() { gl_Position = u_vpMat4 * u_modelMat4 * a_position; }");
		static String fs = 
			"void main() { gl_FragColor = vec4(0.0, 0.2, 0.8, 0.75); }";

		public Simple() {
			super(ShaderFlag.simple);
			program = new ShaderProgram(vs, fs);
		}
	}

	/** A cheap texture shader attribute 'a_texCoord0' as sampler */
	static class Tex0 extends WShader {
		static String vs = String.join(delimiter, 
			"uniform mat4 u_modelMat4;",
			"uniform mat4 u_vpMat4;",

			"attribute vec4 a_position;",
			"attribute vec3 a_normal;",
			"attribute vec2 a_texCoord0;",
			"varying vec2 v_uv;",

			"void main() {",
			"    gl_Position = u_vpMat4 * u_modelMat4 * a_position;",
			"    v_uv = a_texCoord0;",
			"}");
		static String fs = String.join(delimiter, 
			"uniform sampler2D u_texture;",
			"varying vec2 v_uv;",
			"void main() {",
			"	gl_FragColor = texture2D(u_texture, v_uv);",
			"	if (gl_FragColor.a == 0.) discard;",
			"}");

		public Tex0( ) {
			super(ShaderFlag.tex0);
			program = new ShaderProgram(vs, fs);
		}
	}

	/**<p>Shader of SDF font</p>
	 * {@link io.oz.xv.material.bisheng.GlyphLib GlyphLib } use this for default ink. */
	public static class Sdfont extends WShader {
		final int u_vpM4 = register(new Uniform("u_vpMat4"));
		final int u_modelM4 = register(new Uniform("u_modelMat4"));
		private float delta = 0.1f;
		/** Font weight */
		private float weight = 0.4f;

		/** White area alpha - for debug */
		private String whiteAlpha = "0.0";

		public Sdfont() {
			super(ShaderFlag.sdfont);

			String vs = String.format(Gdx.files.internal(String.format("glsl/%s.fs", flag.path())).readString(),
							whiteAlpha);
			program = new ShaderProgram(
				Gdx.files.internal(String.format("glsl/%s.vs", flag.path())).readString(), vs);
		}

		public Sdfont smooth(float smoothing) {
			this.delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);
			return this;
		}

		public Sdfont thin(float weight) {
			this.weight = weight;
			return this;
		}

		/**Set white alpha (alpha without texture color)
		 * @param alpha
		 * @return
		 */
		public Sdfont white(String alpha) {
			this.whiteAlpha = alpha;
			String vs = String.format(Gdx.files.internal(String.format("glsl/%s.fs", flag.path())).readString(),
							whiteAlpha);
			program = new ShaderProgram(
				Gdx.files.internal(String.format("glsl/%s.vs", flag.path())).readString(), vs);
			return this;
		}

		@Override
		public void render(Renderable renderable) {
			program.setUniformf("u_lower", weight - delta);
			program.setUniformf("u_upper", weight + delta);
			program.setUniformi("u_texture", Assets.texGlyph);
			super.render(renderable);
		}
	}

	public static WShader wshader(ShaderFlag f) {
		WShader s;
		switch (f) {
			case test:
				s = new Test();
				break;
			case simple:
				s = new Simple();
				break;
			case tex0:
				s = new Tex0();
				break;
			case sdfont:
				s = new Sdfont();
				break;
			case cubic:
				s = new Cubic();
				break;
			default:
				throw new IllegalArgumentException("TODO");
		}
		s.init();
		return s;
	}
}
