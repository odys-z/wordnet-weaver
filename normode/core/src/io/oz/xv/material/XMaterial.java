package io.oz.xv.material;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;

import io.oz.xv.glsl.WShader;

public class XMaterial extends Material {

	@Override
	public Material copy() {
		return new XMaterial(this);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof XMaterial) && ((other == this) || ((((XMaterial)other).id.equals(id)) && super.equals(other)));
	}

	private WShader shader;

	/**Create XMaterial with shader xshader.
	 * @param matId material id
	 * @param xshader
	 * @param attr
	 */
	public XMaterial(String matId, WShader xshader, Attribute... attr) {
		super(matId, attr);
		this.shader = xshader;
	}

	public XMaterial(XMaterial from) {
		super(from.id, from);
		this.shader = from.shader();
	}

	public WShader shader() {
		return this.shader;
	}

}
