package io.oz.xv.ecs.c;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;

import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.glsl.shaders.WShader;

/**A equivalent and extension of material.
 * 
 * @author Odys Zhou
 *
 */
public class Visual implements Component {
	/**
	 * Uniforms values. Don't confused this with {@link com.badlogic.gdx.graphics.g3d.shaders.BaseShader}'s private field uniforms.
	 */
	public IntMap<Object> uniforms = new IntMap<Object>();
	public boolean needsUpdateUniforms = false;

	public ShaderFlag acceptShader;
	public WShader shader;

	public String name;

}
