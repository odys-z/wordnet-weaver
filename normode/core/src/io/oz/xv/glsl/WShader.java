package io.oz.xv.glsl;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class WShader extends ShaderProgram implements Shader {

	public WShader(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader);
	}

	public WShader (FileHandle vs, FileHandle fs) {
		super(vs, fs);
	}

	@Override
	public void init() { }

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
