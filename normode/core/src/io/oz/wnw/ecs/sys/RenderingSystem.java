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

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Array;

import io.oz.wnw.ecs.cmp.Affine;

public class RenderingSystem extends IteratingSystem {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	static final float PIXELS_TO_METRES = 1.0f / 32.0f;
	
	private ModelBatch batch;
	private Array<Entity> renderQueue;
	private Comparator<Entity> comparator;
	private OrthographicCamera cam;
	
	private ComponentMapper<Affine> affineM;
	
	public RenderingSystem() {
		super(Family.all(Affine.class).get());
		
		affineM = ComponentMapper.getFor(Affine.class);
		
		renderQueue = new Array<Entity>();
		
		comparator = new Comparator<Entity>() {
			@Override
			public int compare(Entity entityA, Entity entityB) {
				return (int)Math.signum(affineM.get(entityB).pos.z -
										affineM.get(entityA).pos.z);
			}
		};
		
		cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		renderQueue.sort(comparator);
		
		cam.update();
		// batch.setProjectionMatrix(cam.combined);
		batch.begin(cam);
		
		for (Entity entity : renderQueue) {
			
			Affine t = affineM.get(entity);
		
//			float width = tex.region.getRegionWidth();
//			float height = tex.region.getRegionHeight();
//			float originX = width * 0.5f;
//			float originY = height * 0.5f;
//			
//			batch.draw(tex.region,
//					   t.pos.x - originX, t.pos.y - originY,
//					   originX, originY,
//					   width, height,
//					   t.scale.x * PIXELS_TO_METRES, t.scale.y * PIXELS_TO_METRES,
//					   MathUtils.radiansToDegrees * t.rotation);
		}
		
		batch.end();
		renderQueue.clear();
	}
	
	@Override
	public void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(entity);
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
}
