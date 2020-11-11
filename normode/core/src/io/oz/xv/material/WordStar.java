//package io.oz.xv.material;
//
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.g3d.Attribute;
//import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
//
//import io.oz.xv.ecs.c.Visual;
//import io.oz.xv.glsl.shaders.PlaneStar;
//import io.oz.xv.glsl.shaders.WShader;
//
//public class WordStar extends XMaterial {
//
//	private static final Attribute attr = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//	public WordStar(Visual visual) {
//		super("ws", attr);
//		shader(starShader(visual));
//	}
//
//	public static WShader starShader(Visual visual) {
////		Visual wordVisual = new Visual();
////		wordVisual.uniforms.put(u_alpha, 1f);
//
//		visual.uniforms.put(u_alpha, 1f);
//		PlaneStar s = new PlaneStar(visual);
//		s.init();
//		return s;
//	}
//
//
//}
