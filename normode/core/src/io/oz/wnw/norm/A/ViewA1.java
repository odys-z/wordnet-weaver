package io.oz.wnw.norm.A;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import io.oz.wnw.norm.WGame;

/**<p>3d wordnet overview (cubic treemap).</p>
 *
 * @author Odys Zhou
 */
public class ViewA1 extends ScreenAdapter {
	PooledEngine ecs;
	StageA stage;

	private CameraInputController camController;

	private PerspectiveCamera cam;
	public PerspectiveCamera cam() { return cam; }

	public ViewA1(WGame game) {
		// create screen
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0f, 20f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		stage = new StageA(game.me());

		// Entities must created before creating EntitySystems
		// stage.loadMysetry();
		stage.loadMyset();

		stage.init(this);
		ecs = stage.engine();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);
	}

	@Override
	public void show() {
		super.show();

		// refresh word treemap
	}

	@Override
	public void render (float delta) {
		if (delta > 0.1f) delta = 0.1f;
		
		stage.update(delta);
		camController.update();
		cam.update();
		ecs.update(delta);;
	}

}
