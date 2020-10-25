package io.oz.xv.material;

import com.badlogic.gdx.graphics.g3d.Attribute;

import io.oz.xv.glsl.WShader;
import io.oz.xv.glsl.shaders.PlaneStart;

public class WordStar extends XMaterial {

	public WordStar(String matId, Attribute[] attr) {
		super(matId, starShader(), attr);
	}

	private static WShader starShader() {
		PlaneStart s = new PlaneStart();
		s.init();
		return s;
	}


}
