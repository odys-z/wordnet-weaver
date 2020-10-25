package io.oz.wnw.norm.A;

import java.util.ArrayList;

import com.badlogic.ashley.core.PooledEngine;

import io.oz.jwi.SynsetInf;
import io.oz.wnw.ecs.sys.SysAffine;
import io.oz.wnw.ecs.sys.SysModelRenderer;
import io.oz.wnw.my.MyWeaver;
import io.oz.xv.treemap.CubeTree;
import io.oz.xv.utils.XVException;

/**Scene A's world / objects manager.
 * 
 * @author Odys Zhou
 */
public class StageA {

	public ArrayList<SynsetInf> synsets;

	private MyWeaver me;

	private PooledEngine ecs;
	public PooledEngine engine() { return ecs; }

	public StageA(MyWeaver me) {
		this.me = me;
		ecs = new PooledEngine();
	}

	public void init(ViewA1 viewA1) {
		// setup objects
		ecs.addSystem(new SysAffine());
		ecs.addSystem(new SysModelRenderer(viewA1.cam()));
		
		synsets = new ArrayList<SynsetInf>();
		synsets.add(me.myset());
	}

	void loadMyset() throws XVException {
		CubeTree.init(null);
		CubeTree.create(ecs, synsets);
	}

	public void update(float delta) {
		ecs.update(delta);
	}

}
