package io.oz.xv.ecs.s;

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

import io.oz.xv.ecs.c.Obj3;
import io.oz.xv.gdxpatch.XShaderProvider;

/**Equivalent of Thrender. MRT?
 *
 * @author Odys Zhou
 */
public class SysModelRenderer extends EntitySystem {

	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	static final float PIXELS_TO_METRES = 1.0f / 32.0f;
	
    private ModelBatch modelBatch;

	private PerspectiveCamera cam;
	
	private ComponentMapper<Obj3> mObj3;

	private ImmutableArray<Entity> entities;
	private float[] _bgcolor;
	
	public SysModelRenderer(PerspectiveCamera camera) {
		super();
		mObj3 = ComponentMapper.getFor(Obj3.class);

		modelBatch = new ModelBatch(new XShaderProvider());
		this.cam = camera;
		
		// default
		_bgcolor = new float[] {0f, 0f, 0f, 1f};
	}
	
	/**<pre>
	 name              |   val
	 background-color  |   float[3(4)] rgb(a), 0 ~ 1
	 </pre>
	 * @param name<br>
	 * @param val
	 * @return
	 */
	public SysModelRenderer option(String name, Object val) {
		if (name == "background-color") {
			float[] v = (float[])val;
			_bgcolor = new float[] {v[0], v[1], v[2], v.length > 3 ? v[3] : 1f};
		}
		return this;
	}

	@Override
	public void addedToEngine(Engine engine) {
		// entities = engine.getEntitiesFor(Family.all(Affines.class, Obj3.class).get());
		entities = engine.getEntitiesFor(Family.all(AffineAnim.class, Obj3.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		// super.update(deltaTime);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		// Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClearColor(_bgcolor[0], _bgcolor[1], _bgcolor[2], _bgcolor[3]);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		// Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_DST_COLOR);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glDepthMask(true); // MRT ? https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/g3d/MultipleRenderTargetTest.java
	
		modelBatch.begin(cam);
		for (int i = 0; i < entities.size(); ++i) {
			Entity entity = entities.get(i);
			Obj3 obj3 = mObj3.get(entity);

			if (obj3.modInst != null)
				modelBatch.render(obj3.modInst);

			if (obj3.orthoFace != null)
				modelBatch.render(obj3.orthoFace);
		}
		modelBatch.end();
	}
	
}
