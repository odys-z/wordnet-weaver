package io.oz.wnw.norm.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tests.g3d.ShadowMappingTest;

import io.oz.wnw.norm.WGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new
				// ViewHome(),
				// WGame(),
				ShadowMappingTest(),
				config);
	}
}
