package io.oz.wnw.norm.A;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.utils.Array;

import io.oz.wnw.norm.WGame;
import io.oz.xv.material.XMaterial;

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
    private ModelBatch modelBatch;
	private CameraInputController camController;
	private Array<ModelInstance> instances;

	public ViewA1(WGame game) {
		ecs = new PooledEngine();
		stage = new StageA(ecs, game.me());
		stage.init(this, ecs);
		
		// create screen
		modelBatch = new ModelBatch(new DefaultShaderProvider() {
			@Override
			protected Shader createShader (Renderable renderable) {
				// if (renderable.material.has(TestAttribute.ID)) return new TestShader(renderable);
				if (renderable.material instanceof XMaterial)
					return ((XMaterial)renderable.material).shader();
				return super.createShader(renderable);
			}
		});

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0f, 20f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		instances = new Array<ModelInstance>(); 
		// try
		// instances.add(stage.sphereCones());
		instances.add(stage.cube());

//		instances.add(stage.loadSnyset());
	}

	@Override
	public void show() {
		super.show();

		// refresh word treemap
	}

	public void update (float delta) {
		if (delta > 0.1f) delta = 0.1f;

		ecs.update(delta);

        camController.update();
//        animController.update(delta);
        cam.update();
	}
	
	public void draw(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());

        Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
 
		Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

	    Gdx.gl.glDepthMask(false);
	    
        modelBatch.begin(cam);
		modelBatch.render(instances);
        modelBatch.end();	
	}

	@Override
	public void render (float delta) {
		update(delta);
		draw(delta);
	}

}
