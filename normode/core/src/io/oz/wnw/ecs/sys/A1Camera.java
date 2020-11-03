//package io.oz.wnw.ecs.sys;
//
//import com.badlogic.ashley.core.ComponentMapper;
//import com.badlogic.ashley.core.Entity;
//import com.badlogic.ashley.core.Family;
//import com.badlogic.ashley.systems.IteratingSystem;
//
//import io.oz.wnw.ecs.cmp.Affines;
//
//public class A1Camera extends IteratingSystem {
//	
//	private ComponentMapper<Affines> tm;
//	private ComponentMapper<CamPerspect> cm;
//	
//	public A1Camera() {
//		super(Family.all(CamPerspect.class).get());
//		
//		tm = ComponentMapper.getFor(Affines.class);
//		cm = ComponentMapper.getFor(CamPerspect.class);
//	}
//
//	@Override
//	public void processEntity(Entity entity, float deltaTime) {
//		CamPerspect cam = cm.get(entity);
//		
//		if (cam.target == null) {
//			return;
//		}
//		
//		Affines target = tm.get(cam.target);
//		
//		if (target == null) {
//			return;
//		}
//		
//		cam.pos.y = Math.max(cam.pos.y, target.pos.y);
//	}
//}
