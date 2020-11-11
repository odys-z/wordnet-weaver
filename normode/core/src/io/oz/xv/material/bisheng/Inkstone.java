package io.oz.xv.material.bisheng;

import io.oz.xv.ecs.c.Visual;
import io.oz.xv.glsl.Glsl;
import io.oz.xv.glsl.Glsl.Sdfont;
import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.material.XMaterial;

/**Ink material helper
 * @author Odys Zhou
 *
 */
public class Inkstone {

	/** Generate a default material with visual not exposed to ecs.
	 * @param name
	 * @return
	 */
	public static XMaterial colorful(String name) {
		Visual v = new Visual();
		v.name = name;
		v.shader = ((Sdfont) Glsl.wshader(ShaderFlag.sdfont)).smooth(0.06f).thin(0.5f)
        					  .white(GlyphLib.debug ? "0.5" : "0.0");
		v.shader.init();
		return new XMaterial().visual(v);
	}

	/**TODO
	 * @param color
	 * @return
	 */
	public static XMaterial pixel(String color) {
		Visual v = new Visual();
		v.shader = Glsl.wshader(ShaderFlag.test);
		v.shader.init();
		return new XMaterial().visual(v);
	}

}
