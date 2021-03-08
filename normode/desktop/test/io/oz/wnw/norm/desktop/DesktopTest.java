package io.oz.wnw.norm.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import io.oz.xv.ecs.s.RayPickerTest;

/**Back at 28 Feb 2021:
 * continuing on ray picking bug fixing.
 * 
 * @author Odys Zhou
 */
public class DesktopTest {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = true;
		new LwjglApplication(new RayPickerTest(), config);
	}
}
