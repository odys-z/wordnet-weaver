/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package io.oz.wnw.norm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import io.oz.wnw.norm.A.ViewA1;
import io.oz.wnw.norm.C.ViewC1;

public class ScnMenu extends ScreenAdapter {
	WGame game;
	OrthographicCamera guiCam;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
	Vector3 touchPoint;

	SpriteBatch batcher;
	static TextureRegion mainMenu;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public ScnMenu (WGame game) {
		this.game = game;

		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		soundBounds = new Rectangle(0, 0, 64, 64);
		playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
		highscoresBounds = new Rectangle(160 - 150, 200 - 18, 300, 36);
		helpBounds = new Rectangle(160 - 150, 200 - 18 - 36, 300, 36);
		touchPoint = new Vector3();

		Texture items = loadTexture("wn/items.png");
		mainMenu = new TextureRegion(items, 0, 224, 300, 110);
	}

	/**Check button bounds against unprojected Gdx.input.xy.<br>
	 * For ex., if My Words (playBounds) touched, do<br>
	 * {@link #game}.setScree(new {@link ViewA1}(game));
	 * @param delta 
	 */
	public void update (float delta) {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new ViewC1(game));
				return;
			}
//			if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
//				Assets.playSound(Assets.clickSound);
//				game.setScreen(new HighscoresScreen(game));
//				return;
//			}
//			if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
//				Assets.playSound(Assets.clickSound);
//				game.setScreen(new HelpScreen(game));
//				return;
//			}
//			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
//				Assets.playSound(Assets.clickSound);
//				Settings.soundEnabled = !Settings.soundEnabled;
//				if (Settings.soundEnabled)
//					Assets.music.play();
//				else
//					Assets.music.pause();
//			}
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);

		batcher.disableBlending();
		batcher.begin();
//		batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);
		batcher.end();

		batcher.enableBlending();
		batcher.begin();
//		batcher.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274, 142);
		batcher.draw(mainMenu, 10, 200 - 110 / 2, 300, 110);
//		batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
		batcher.end();	
	}

	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}

	@Override
	public void pause () {
		// Settings.save();
	}
}
