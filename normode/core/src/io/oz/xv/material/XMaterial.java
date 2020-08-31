package io.oz.xv.material;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;

import io.oz.xv.glsl.WShader;

public class XMaterial extends Material {

	private WShader shader;

	public XMaterial(String string, WShader shader, Attribute... attr) {
		super(string, attr);
		this.shader = shader;
	}

}
