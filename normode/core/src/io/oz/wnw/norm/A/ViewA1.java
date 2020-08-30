package io.oz.wnw.norm.A;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import io.oz.wnw.norm.WGame;

/**<p>3d wordnet overview (cubic treemap).</p>
 * 
 * This view also illustrates the better practice of ModelInstance + ShaderProgram. See<br>
 * 1. <a href='https://stackoverflow.com/questions/28590802/libgdx-assigning-a-specific-shader-to-a-modelinstance'>
 * Answer of Xoppa</a> for question "LibGDX assigning a specific shader to a ModelInstance"<br>
 * 2. <a href='https://github.com/libgdx/libgdx/wiki/ModelBatch'>
 * libGDX wiki: ModelBatch</a>, section on ShaderProvider<br>
 * 3. <a href='https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/g3d/ShaderTest.java'>
 * libGDX source: ShaderTest.java</a>, example of how to implement shader been used by ModelInstance.
 * @author odys-z@github.com
 */
public class ViewA1 extends ScreenAdapter {
	PooledEngine ecs;
	StageA stage;

	private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private Model model;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController controller;	

	public ViewA1(WGame game) {
		ecs = new PooledEngine();
		stage = new StageA(ecs);
		stage.init(this, ecs);
		
		// load personal skybox
		// load top level synset (correct time?)
		if (stage.synsets.get("top") == null)
			stage.loadSnyset("top");
	
	}

	@Override
	public void show() {
		super.show();

		// refresh word treemap

	}

	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		ecs.update(deltaTime);
	}
	
	public void draw() {
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        camera.update();
        controller.update(Gdx.graphics.getDeltaTime());
        modelBatch.begin(camera);
        modelBatch.render(modelInstance);
        modelBatch.end();	
	}

	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}

}
