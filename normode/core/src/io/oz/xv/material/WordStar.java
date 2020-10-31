package io.oz.xv.material;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;

import io.oz.xv.glsl.WShader;
import io.oz.xv.glsl.shaders.PlaneStar;

public class WordStar extends XMaterial {

	private static final Attribute attr = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

	public WordStar() {
		super("ws", starShader(), attr);
	}

	private static WShader starShader() {
		PlaneStar s = new PlaneStar();
		s.init();
		return s;
	}


}
