package io.oz.xv.ecs.s;

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
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Elastic;
import io.oz.wnw.ecs.cmp.ds.AffineTrans;
import io.oz.wnw.ecs.cmp.ds.AffineType;
import io.oz.xv.ecs.c.Affines;
import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.ecs.c.Visual;
import io.oz.xv.ecs.s.SysAffine;
import io.oz.xv.ecs.s.SysModelRenderer;
import io.oz.xv.ecs.s.SysVisual;
import io.oz.xv.gdxpatch.utils.QuadShapeBuilder;
import io.oz.xv.gdxpatch.utils.XModelBuilder;
import io.oz.xv.glsl.Glsl;
import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.glsl.shaders.PlaneStar;
import io.oz.xv.material.XMaterial;
import io.oz.xv.test.WGameTest;

public class TestBasicTweenView extends ScreenAdapter {
	PooledEngine ecs;
	RayPicker rayPicker; 

	private CameraInputController camController;

	private PerspectiveCamera cam;
	private SysVisual visualsys;
	public PerspectiveCamera cam() { return cam; }

	public TestBasicTweenView(WGameTest game) {
		// create screen
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0f, 20f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		ecs = new PooledEngine();

		camController = new CameraInputController(cam);
		camController.translateUnits *= 10f;
		Gdx.input.setInputProcessor(camController);

//		rayPicker = new RayPicker(cam);
//		Gdx.input.setInputProcessor(new InputMultiplexer(rayPicker, camController));
//		ecs.addSystem(rayPicker);

		visualsys = new SysVisual(rayPicker);
		ecs.addSystem(visualsys);

		ecs.addSystem(new SysAffine());
		ecs.addSystem(new SysModelRenderer(cam));

		createBox(ecs, 0);
	}

	@Override
	public void render (float delta) {
		if (delta > 0.1f) delta = 0.1f;
		
		camController.update();
		cam.update();
		ecs.update(delta);;
	}

	private static Entity createBox(PooledEngine ecs, float x) {
		Entity box = ecs.createEntity();
		ecs.addEntity(box);

		Visual vis = new Visual();
		vis.shader = Glsl.wshader(ShaderFlag.simple, vis);
		XMaterial mat = new XMaterial("simple").visual(vis);
		box.add(vis);

		Obj3 obj3 = ecs.createComponent(Obj3.class);

		Vector3 whd = new Vector3(3, 3, 3);
		ModelBuilder builder = new ModelBuilder();
		builder.begin();
		MeshPartBuilder mpbuilder = builder.part("box1", GL20.GL_TRIANGLES,
				Usage.Position | Usage.ColorUnpacked | Usage.TextureCoordinates | Usage.Normal, mat);
		BoxShapeBuilder.build(mpbuilder, whd.x, whd.y, whd.z); // test size
		Model model = builder.end();
		model.calculateTransforms();
		obj3.modInst = new ModelInstance(model);
		box.add(obj3);

		Affines aff = ecs.createComponent(Affines.class);
		aff.transforms = new Array<AffineTrans>();
		aff.transforms.add(new AffineTrans(AffineType.translate).translate(x, 0, 0));
		box.add(aff);
		
		Tween.to(aff.transforms.get(0), -1, 1.0f).target(20, 30).ease(Elastic.INOUT);
		
		return box;
	}
}
