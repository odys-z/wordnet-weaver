package io.oz.wnw.norm.A;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;

import io.oz.wnw.norm.WGame;

/**3d wordnet overview (cubic treemap).
 * 
 * @author odys-z@github.com
 */
public class ViewA1 extends ScreenAdapter {
	PooledEngine ecs;
	StageA stageA;
	
	public ViewA1(WGame game) {
		ecs = new PooledEngine();
		stageA = new StageA(ecs);
		stageA.init(this, ecs);
		
		// load personal skybox
		
		// load top level synset
	}
	
	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		ecs.update(deltaTime);
	}
	
	public void draw() {
		
	}

	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}

}
