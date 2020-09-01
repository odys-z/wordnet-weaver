package io.oz.wnw.norm.A;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.ConeShapeBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.SphereShapeBuilder;
import com.badlogic.gdx.utils.Array;

import io.oz.wnw.norm.WGame;
import io.oz.xv.glsl.WShader;
import io.oz.xv.material.XMaterial;

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

	private PerspectiveCamera cam;
    private ModelBatch modelBatch;
    private Model model;
    private Environment environment;
    private AnimationController controller;
	private CameraInputController camController;
	private Array<ModelInstance> instances;

	public ViewA1(WGame game) {
		ecs = new PooledEngine();
		stage = new StageA(ecs);
		stage.init(this, ecs);
		
		// load personal skybox
		// load top level synset (correct time?)
		if (stage.synsets.get("top") == null)
			stage.loadSnyset("top");
	
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

		WShader sh1 = new WShader(WShader.Mode.simple);
		WShader sh2 = new WShader(WShader.Mode.test);
		Material testMaterial1 = new XMaterial("TestMaterial1", sh1);
		Material redMaterial = new Material("RedMaterial", ColorAttribute.createDiffuse(Color.RED));
		Material testMaterial2 = new XMaterial("TestMaterial2", sh2, ColorAttribute.createDiffuse(Color.BLUE));

		ModelBuilder builder = new ModelBuilder();
		Node node;

		builder.begin();
		node = builder.node();
		node.id = "testCone1";
		node.translation.set(-10, 0f, 0f);
		// builder.part("testCone", GL20.GL_TRIANGLES, Usage.Position, testMaterial1).cone(5, 5, 5, 20);
		// cone() is deprecated, this:?
		ConeShapeBuilder.build(builder.part("testCone", GL20.GL_TRIANGLES, Usage.Position, testMaterial1), 5, 5, 5, 20);

		node = builder.node();
		node.id = "redSphere";
		// builder.part("redSphere", GL20.GL_TRIANGLES, Usage.Position, redMaterial).sphere(5, 5, 5, 20, 20);
		SphereShapeBuilder.build(builder.part("redSphere", GL20.GL_TRIANGLES, Usage.Position, redMaterial), 5, 5, 5, 20, 20);

		node = builder.node();
		node.id = "testCone1";
		node.translation.set(10, 0f, 0f);
		// builder.part("testCone", GL20.GL_TRIANGLES, Usage.Position, testMaterial2).cone(5, 5, 5, 20);
		ConeShapeBuilder.build(builder.part("testCone", GL20.GL_TRIANGLES, Usage.Position, testMaterial2), 5, 5, 5, 20);

		model = builder.end();

		ModelInstance modelInstance;
		modelInstance = new ModelInstance(model);
//		testAttribute1 = (TestAttribute)modelInstance.getMaterial("TestMaterial1").get(TestAttribute.ID);
//		testAttribute2 = (TestAttribute)modelInstance.getMaterial("TestMaterial2").get(TestAttribute.ID);
		instances.add(modelInstance);
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
        controller.update(delta);
        cam.update();
	}
	
	public void draw(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

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
