package io.oz.xv.glsl.shader;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import io.oz.wnw.ecs.cmp.ds.AffineTrans;
import io.oz.xv.ecs.c.Affines;
import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.ecs.c.Visual;
import io.oz.xv.ecs.s.SysAffine;
import io.oz.xv.ecs.s.SysModelRenderer;
import io.oz.xv.ecs.s.SysVisual;
import io.oz.xv.gdxpatch.utils.QuadShapeBuilder;
import io.oz.xv.gdxpatch.utils.XModelBuilder;
import io.oz.xv.glsl.shaders.PlaneStar;
import io.oz.xv.material.XMaterial;

public class TestPlaneStarView extends ScreenAdapter {
	PooledEngine ecs;

	private CameraInputController camController;

	private PerspectiveCamera cam;
	private SysVisual visualsys;
	public PerspectiveCamera cam() { return cam; }

	public TestPlaneStarView() {
		// buffer
		_colr = new Color();

		// create screen
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 20f, 20f);
		cam.lookAt(0, 0, 0);
		cam.near = 0.001f;
		cam.far = 30000f;
		cam.update();

		ecs = new PooledEngine();

		camController = new CameraInputController(cam);
		camController.translateUnits *= 2f;
		Gdx.input.setInputProcessor(camController);

		visualsys = new SysVisual(null);
		ecs.addSystem(visualsys);

		ecs.addSystem(new SysAffine());
		ecs.addSystem(new SysModelRenderer(cam)
						  .option("background-color", new float[] {0.2f, .6f, 0.3f, 1.0f}));

		createStar(ecs, 1f);
	}

	@Override
	public void render (float delta) {
		if (delta > 0.1f) delta = 0.1f;
		
		camController.update();
		cam.update();
		ecs.update(delta);;
	}

	private static Color _colr;
	private static void createStar(PooledEngine ecs, float memory) {
		Entity star = ecs.createEntity();
		ecs.addEntity(star);

		Visual vStar = new Visual();
		PlaneStar s = new PlaneStar(vStar);
		s.init();
		vStar.shader = s;
		XMaterial starMatrl = new XMaterial().visual(vStar);

		XModelBuilder builder = new XModelBuilder();
		builder.begin();

		MeshPartBuilder mpbuilder = builder.part("test", GL20.GL_TRIANGLES,
				Usage.Position | Usage.ColorUnpacked | Usage.TextureCoordinates | Usage.Normal, starMatrl);
		Vector3 pos = new Vector3();
		QuadShapeBuilder.build(mpbuilder, pos, _colr.set(memory, memory / 3, 0, 1), 40f, 40f); 

		Model model = builder.end();
		model.calculateTransforms();
		Obj3 obj3 = ecs.createComponent(Obj3.class);
		obj3.orthoFace = new ModelInstance(model);

		Affines aff = ecs.createComponent(Affines.class);
		aff.transforms = new Array<AffineTrans>();
		star.add(aff);
		star.add(obj3);
	}
}
