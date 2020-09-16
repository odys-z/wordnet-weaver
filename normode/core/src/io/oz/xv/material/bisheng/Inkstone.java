package io.oz.xv.material.bisheng;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;

import io.oz.xv.glsl.Glsl;
import io.oz.xv.glsl.Glsl.Sdfont;
import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.material.XMaterial;

/**Ink material helper
 * @author Odys Zhou
 *
 */
public class Inkstone {

	public static XMaterial colorful(String name) {
		return new XMaterial(name,
        		((Sdfont) Glsl.wshader(ShaderFlag.sdfont)).smooth(0.06f).thin(0.5f)
        					  .white(GlyphLib.debug ? "0.5" : "0.0"),
        		new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

	}

	/**TODO
	 * @param color
	 * @return
	 */
	public static XMaterial pixel(String color) {
		return new XMaterial(color,
        		Glsl.wshader(ShaderFlag.test),
        		new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
	}

}
