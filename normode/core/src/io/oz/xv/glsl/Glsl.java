package io.oz.xv.glsl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

import io.oz.wnw.norm.Assets;

public class Glsl {

	public static enum ShaderFlag {
		test("test"), simple("simple"), tex0("tex0"), sdfont("sdfont");

		private String p;
		ShaderFlag(String v) { p = v; };
		public String path() { return p; }
	}

	public static final CharSequence delimiter = System.getProperty("line.separator");
	
	static class test {
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
	}
	
	static class simple {
		static String vs = String.join(delimiter, 
			"uniform mat4 u_modelMat4;",
			"uniform mat4 u_vpMat4;",

			"attribute vec4 a_position;",
			"attribute vec3 a_normal;",

			"void main() { gl_Position = u_vpMat4 * u_modelMat4 * a_position; }");
		static String fs = 
			"void main() { gl_FragColor = vec4(0.0, 0.2, 0.8, 0.75); }";
	}

	static class tex0 {
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
			//"	if (gl_FragColor.a == 0.) discard;",
			//"	gl_FragColor.r = v_uv.t;", // u_uv: 0 ~ 1
			"}");
	}

	static class sdfont {
		static ShaderProgram init(ShaderProgram program) {
			float smoothing = 0.3f;
			float delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);
			try {
				program.setUniformf("u_lower", 0.9f - delta);
				program.setUniformf("u_upper", 0.9f + delta);
			} catch (Throwable t) {}
			program.setUniformi("u_texture", Assets.texGlyph);
			return program;
		}
	}

	public static String vs(ShaderFlag m) {
		switch (m) {
			case test:
				return test.vs;
			case simple:
				return simple.vs;
			case tex0:
				return tex0.vs;

			default:
				return Gdx.files.internal(String.format("glsl/%s.vs", m.path())).readString();
		}
	}

	public static String fs(ShaderFlag m) {
		switch (m) {
			case test:
				return test.fs;
			case simple:
				return simple.fs;
			case tex0:
				return tex0.fs;
			default:
				return Gdx.files.internal(String.format("glsl/%s.fs", m.path())).readString();
		}
	}
}
