package io.oz.xv.test;

import com.badlogic.gdx.Game;

import io.oz.wnw.norm.Assets;

/**Test application manager like XWorld of x-visual.
 * <br>
 * keep assets<br>
 * hold user state<br>
 * manage data<br>
 * 
 * @author Odys Zhou
 */
public class WGameTest extends Game {

	@Override
	public void create () {
		Assets.load();
		
		// setup weaver client
		// ISettings settings = new Settings().load();
	}

}
