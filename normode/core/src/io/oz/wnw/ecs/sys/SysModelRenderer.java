/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package io.oz.wnw.ecs.sys;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;

import io.oz.wnw.ecs.cmp.Affines;
import io.oz.wnw.ecs.cmp.Obj3;
import io.oz.xv.material.XMaterial;

public class SysModelRenderer extends EntitySystem {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	static final float PIXELS_TO_METRES = 1.0f / 32.0f;
	
    private ModelBatch modelBatch;

	private OrthographicCamera cam;
	
	private ComponentMapper<Obj3> mObj3;
	private ImmutableArray<Entity> entities;
	
	public SysModelRenderer() {
		super();
		
		mObj3 = ComponentMapper.getFor(Obj3.class);
		
		modelBatch = new ModelBatch(new DefaultShaderProvider() {
			@Override
			protected Shader createShader (Renderable renderable) {
				// if (renderable.material.has(TestAttribute.ID)) return new TestShader(renderable);
				if (renderable.material instanceof XMaterial)
					return ((XMaterial)renderable.material).shader();
				return super.createShader(renderable);
			}
		});
		
		cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(Affines.class, Obj3.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glDepthMask(false);
	
		modelBatch.begin(cam);
		for (int i = 0; i < entities.size(); ++i) {
			Entity entity = entities.get(i);
			Obj3 obj3 = mObj3.get(entity);
			modelBatch.render(obj3.modInst);
		}
		modelBatch.end();
	}
	
}
