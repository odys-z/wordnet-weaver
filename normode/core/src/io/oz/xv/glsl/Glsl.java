package io.oz.xv.glsl;

import com.badlogic.gdx.Gdx;

public class Glsl {

	public static enum ShaderFlag {
		test("test"), simple("simple"), sdfont("sdfont");

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

	public static String vs(ShaderFlag m) {
		switch (m) {
			case test:
				return test.vs;
			case simple:
				return simple.vs;

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
			default:
				return Gdx.files.internal(String.format("glsl/%s.fs", m.path())).readString();
		}

	}

}
