package io.oz.wnw.norm.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import io.oz.xv.glsl.shader.TestPlaneStar;

public class PlaneStarTest {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = true;
		new LwjglApplication(new TestPlaneStar(), config);
	}
}
