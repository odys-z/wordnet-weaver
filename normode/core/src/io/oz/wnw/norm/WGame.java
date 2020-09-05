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
//	public SpriteBatch batcher;
	private MyWeaver me;

	@Override
	public void create () {
//		batcher = new SpriteBatch();
		Assets.load();
		
		// setup weaver client
		ISettings settings = new Settings().load();
		me = new MyWeaver(settings);
		
		// setScreen(new ScnMenu(this));
		setScreen(new ViewA1(this));
	}
	
//	@Override
//	public void render() {
//		GL20 gl = Gdx.gl;
//		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
//		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		
//		super.render();
//	}
}
