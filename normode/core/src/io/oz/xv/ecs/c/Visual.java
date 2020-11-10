package io.oz.xv.ecs.c;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;

import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.glsl.WShader;

/**A equivalent and extension of material.
 * 
 * @author Odys Zhou
 *
 */
public class Visual implements Component {
	public IntMap<Object> uniforms = new IntMap<Object>();

	public ShaderFlag acceptShader;
	public WShader shader;

}
