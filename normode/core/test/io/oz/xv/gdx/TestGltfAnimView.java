package io.oz.xv.gdx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import io.oz.xv.ecs.c.AffineAnim;
import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.ecs.c.Visual;
import io.oz.xv.ecs.s.RayPicker;
import io.oz.xv.ecs.s.SysAffine3;
import io.oz.xv.ecs.s.SysModelRenderer;
import io.oz.xv.ecs.s.SysVisual;
import io.oz.xv.gdxpatch.g3d.XModelInstance;
import io.oz.xv.glsl.Glsl;
import io.oz.xv.glsl.Glsl.ShaderFlag;
import io.oz.xv.material.XMaterial;
import io.oz.xv.test.WGameTest;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneModel;
import patch.net.mgsx.gltf.scene3d.animation.XAnimationControllerGltf;

public class TestGltfAnimView extends ScreenAdapter {
	PooledEngine ecs;
	RayPicker rayPicker; 

	private CameraInputController camController;

	private PerspectiveCamera cam;
	private SysVisual visualsys;
	public PerspectiveCamera cam() { return cam; }

	public TestGltfAnimView(WGameTest game) {
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

		visualsys = new SysVisual(rayPicker);
		ecs.addSystem(visualsys);

		ecs.addSystem(new SysAffine3());
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

		// prepare animation
		SceneAsset sceneAsset = new GLTFLoader().load(Gdx.files.classpath("res/khronosgroup/simple.gltf"));
		for(SceneModel scene : sceneAsset.scenes) {
			model.animations.addAll(scene.model.animations); // Of course only appliable should be added
		}

		model.calculateTransforms();
		obj3.modInst = new XModelInstance(model);
		// obj3.modInst.nodes.clear();

		// replace nodes
//		NodePlus node = new NodePlus();
//		NodePartPlus nodePart = new NodePartPlus();
//		nodePart.morphTargets = ((NodePlus)node).weights;
//		nodePart.meshPart = model.meshParts.get(0);
//		obj3.modInst.nodes.add(node);
		box.add(obj3);
	
		AffineAnim aff = ecs.createComponent(AffineAnim.class); 
		aff.translation = new Array<NodeKeyframe<Vector3>>();
		aff.translation.add(new NodeKeyframe<Vector3>(1, new Vector3(x, 0, 0)));
		aff.controllor = new XAnimationControllerGltf(obj3.modInst);
	    // aff.controllor.setAnimation("simple");
	    aff.controllor.animate("simple", 0);
	    box.add(aff);

		return box;
	}
}
