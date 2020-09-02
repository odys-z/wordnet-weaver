package io.oz.xv.glsl;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.material.XMaterial;

public class WShader extends BaseShader implements Shader {
	protected final int u_projTrans = register(new Uniform("u_vpMat4"));
	protected final int u_worldTrans = register(new Uniform("u_modelMat4"));
	
	public WShader(ShaderFlag mod) {
		super();
		program = new ShaderProgram(Glsl.vs(mod), Glsl.fs(mod));
	}

	public WShader uniforms(XUniforms uniforms) {
		throw new IllegalArgumentException("Need this?");
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
		Material mat = instance.material;
		return mat instanceof XMaterial && ((XMaterial)mat).shader() == this;
	}

	@Override
	public void begin (Camera camera, RenderContext context) {
//		program.bind();
		context.setDepthTest(GL20.GL_LEQUAL, 0f, 1f);
		context.setDepthMask(true);
		set(u_projTrans, camera.combined);
	}
	
	@Override
	public void render(Renderable renderable) {
		set(u_worldTrans, renderable.worldTransform);

		renderable.meshPart.render(program);	
	}

}
