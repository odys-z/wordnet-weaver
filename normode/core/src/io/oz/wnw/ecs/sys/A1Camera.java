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
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.oz.wnw.ecs.cmp.CamPerspect;
import io.oz.wnw.ecs.cmp.Affine;

public class A1Camera extends IteratingSystem {
	
	private ComponentMapper<Affine> tm;
	private ComponentMapper<CamPerspect> cm;
	
	public A1Camera() {
		super(Family.all(CamPerspect.class).get());
		
		tm = ComponentMapper.getFor(Affine.class);
		cm = ComponentMapper.getFor(CamPerspect.class);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		CamPerspect cam = cm.get(entity);
		
		if (cam.target == null) {
			return;
		}
		
		Affine target = tm.get(cam.target);
		
		if (target == null) {
			return;
		}
		
		cam.pos.y = Math.max(cam.pos.y, target.pos.y);
	}
}
