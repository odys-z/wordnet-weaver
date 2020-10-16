package io.oz.xv.material;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;

import io.oz.xv.glsl.Glsl;
import io.oz.xv.glsl.WShader;
import io.oz.xv.glsl.Glsl.ShaderFlag;

public class CubeSkin extends XMaterial {

	public CubeSkin(String matId, Attribute[] attr) {
		super(matId, cubeSkinShader(), 
				new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
	}

	private static WShader cubeSkinShader() {
		return Glsl.wshader(ShaderFlag.cubic);
	}

}
