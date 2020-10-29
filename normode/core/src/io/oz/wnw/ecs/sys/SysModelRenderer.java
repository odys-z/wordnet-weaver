package io.oz.wnw.ecs.sys;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import io.oz.wnw.ecs.cmp.Affines;
import io.oz.wnw.ecs.cmp.Obj3;
import io.oz.xv.gdxpatch.XShaderProvider;

public class SysModelRenderer extends EntitySystem {

	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	static final float PIXELS_TO_METRES = 1.0f / 32.0f;
	
    private ModelBatch modelBatch;

	private PerspectiveCamera cam;
	
	private ComponentMapper<Obj3> mObj3;
	private ImmutableArray<Entity> entities;
	
	public SysModelRenderer(PerspectiveCamera camera) {
		super();
		
		mObj3 = ComponentMapper.getFor(Obj3.class);
		
		/*
		modelBatch = new ModelBatch(new DefaultShaderProvider() {
			@Override
			public Shader getShader(Renderable randerable) {
				return super.getShader(randerable);
			}

			@Override
			protected Shader createShader (Renderable renderable) {
				// if (renderable.material.has(TestAttribute.ID)) return new TestShader(renderable);
				if (renderable.material instanceof XMaterial)
					return ((XMaterial)renderable.material).shader();
				return super.createShader(renderable);
			}
		});
		*/
		modelBatch = new ModelBatch(new XShaderProvider());
		
		this.cam = camera;
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Affines.class, Obj3.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glDepthMask(false);
	
		modelBatch.begin(cam);
		for (int i = 0; i < entities.size(); ++i) {
			Entity entity = entities.get(i);
			Obj3 obj3 = mObj3.get(entity);
			modelBatch.render(obj3.modInst);
			if (obj3.orthoFace != null)
				modelBatch.render(obj3.orthoFace);
		}
		modelBatch.end();
	}
	
}
