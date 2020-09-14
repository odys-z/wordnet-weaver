package io.oz.wnw.norm.A;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import io.oz.wnw.norm.WGame;

/**<p>3d wordnet overview (cubic treemap).</p>
 *
 * This view also illustrates the better practice of ModelInstance + ShaderProgram. See<br>
 * 1. Answer of Xoppa for question <a href='https://stackoverflow.com/questions/28590802/libgdx-assigning-a-specific-shader-to-a-modelinstance'>
 * LibGDX assigning a specific shader to a ModelInstance</a><br>
 * 2. <a href='https://github.com/libgdx/libgdx/wiki/ModelBatch'>
 * libGDX wiki: ModelBatch</a>, section on ShaderProvider<br>
 * 3. <a href='https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/g3d/ShaderTest.java'>
 * libGDX source: ShaderTest.java</a>, example of how to implement shader been used by ModelInstance.
 *
 * @author Odys Zhou
 */
public class ViewA1 extends ScreenAdapter {
	PooledEngine ecs;
	StageA stage;

	private PerspectiveCamera cam;
	private CameraInputController camController;
	// private Array<ModelInstance> instances;

	public ViewA1(WGame game) {
		stage = new StageA(game.me());
		stage.init(this);
		ecs = stage.engine();

		// create screen
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0f, 20f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		//instances = new Array<ModelInstance>();
		// instances.add(stage.loadSnyset());

		stage.loadMyset();
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
