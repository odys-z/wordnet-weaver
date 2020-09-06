package io.oz.wnw.norm;

import com.badlogic.gdx.Game;

import io.oz.wnw.my.ISettings;
import io.oz.wnw.my.MyWeaver;
import io.oz.wnw.norm.A.ViewA1;

/**Application manager like XWorld of x-visual.
 * <br>
 * keep assets<br>
 * hold user state<br>
 * manage data<br>
 * 
 * @author Odys Zhou
 */
public class WGame extends Game {
	private MyWeaver me;

	@Override
	public void create () {
		Assets.load();
		
		// setup weaver client
		ISettings settings = new Settings().load();
		me = new MyWeaver(settings);
		
		// setScreen(new ScnMenu(this));
		setScreen(new ViewA1(this));
	}

	public MyWeaver me() {
		return me;
	}
	
}
