package io.oz.xv.glsl;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class WShader extends ShaderProgram implements Shader {

	public static enum Mode {
		test("test"), simple("simple");

		private String n;
		Mode(String v) { n = v; };
		public String n() { return n; }
	};

	public WShader(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader);
	}

	public WShader (FileHandle vs, FileHandle fs) {
		super(vs, fs);
	}

	// FIXME what about uniforms?
	// FIXME what about uniforms?
	// FIXME what about uniforms?
	public WShader(Mode mod) {
		super(Glsl.simple.vs(), Glsl.simple.fs());
	}

	@Override
	public void init() {
		super.init(program, null);	
	}

	@Override
	public int compareTo(Shader other) {
		return 0;
	}

	@Override
	public boolean canRender(Renderable instance) {
		return instance.shader == this;
	}

	@Override
	public void begin(Camera camera, RenderContext context) { }

	@Override
	public void render(Renderable renderable) { }

}
