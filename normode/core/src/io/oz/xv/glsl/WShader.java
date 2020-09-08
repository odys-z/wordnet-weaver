package io.oz.xv.glsl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.glsl.Glsl.sdfont;
import io.oz.xv.material.XMaterial;

public class WShader extends BaseShader implements Shader {
	protected final int u_vpM4 = register(new Uniform("u_vpMat4"));
	protected final int u_modelM4 = register(new Uniform("u_modelMat4"));
//	protected final int u_texture = register(new Uniform("u_texture"));

	ShaderFlag flag;

	public ShaderFlag flag() { return flag; }
	
	public WShader(ShaderFlag flag) {
		super();
		this.flag = flag;
		program = new ShaderProgram(Glsl.vs(flag), Glsl.fs(flag));
	}

	public WShader uniforms(XUniforms uniforms) {
		throw new IllegalArgumentException("Need this?");
	}

	@Override
	public void init() {
		super.init(program, null);

		if (flag == ShaderFlag.sdfont)
			sdfont.init(program);
	}

	@Override
	public int compareTo(Shader other) {
		return 0;
	}

	@Override
	public boolean canRender(Renderable instance) {
		Material mat = instance.material;
		return mat instanceof XMaterial; // && ((XMaterial)mat).shader() == this;
	}

	@Override
	public void begin (Camera camera, RenderContext context) {
		program.begin();
		context.setDepthTest(GL20.GL_LEQUAL, 0f, 1f);
		// context.setDepthMask(true);
		set(u_vpM4, camera.combined);
	}
	
	@Override
	public void render(Renderable renderable) {
		set(u_modelM4, renderable.worldTransform);

		program.setUniformi("u_texture", 0);
		// https://stackoverflow.com/questions/8594703/lwjgl-transparency
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		renderable.meshPart.render(program);	
	}

}
