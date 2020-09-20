package io.oz.xv.treemap;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;

import io.oz.jwi.SynsetInf;
import io.oz.xv.math.XMath;
import io.oz.xv.utils.XVException;

public class TreeContext {
	private static final Color color = new Color(1f, 1f, 0f, 1f);
	public Engine ecs;

	/**Grid scale*/
	float scale;
	
	/**Current level*/
	int level;

	/**availability*/
	private int[] wh;
	/**current empty slot */
	private int[] freex;

	private ArrayList<TreemapNode>[] pool;

	TreeContext(PooledEngine ecs2) {
		this.ecs = ecs2;
		level = 0;
		scale = 1f;
		freex = new int[] {-1, -1};
	}
	
	public TreeContext scale(float s) {
		this.scale = s;
		return this;
	}

	public Color getColor(String word) {
		return color;
	}

	/**Create tree structure, setup resource pool for allocating.
	 * @param synsets
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	ArrayList<TreemapNode>[] init(ArrayList<SynsetInf> synsets) {
		int size = synsets.size();
		wh = XMath.encampass(size);
		freex[0] = 0;
		freex[1] = 0;
		
		pool = (ArrayList<TreemapNode>[]) new ArrayList<?>[wh[0]];
		for (int i = 0; i < wh[0]; i++) {
			pool[i] = new ArrayList<TreemapNode>(wh[1]);
			// TODO init row = [ null, ...]
		}
		return pool;
	}

	TreemapNode allocatNode(SynsetInf si) {
		TreemapNode n = new TreemapNode(this, freex[0], level, freex[1]);
		freex[1]++;
		if (freex[1] >= wh[1] && freex[0] < wh[0] - 1
			// insert to next row when creating, ignored and add new at end when later appending 
			&& freex[1] < wh[1] && pool[freex[0] + 1].get(0) == null) {
			freex[1] = 0;
			freex[0]++;
			pool[freex[1]].set(freex[1], n);
		}
		else {
			// TODO find shortest
			pool[0].add(0, n);
		}
		return n;
	}
	
	TreeContext zoomin() {
		level++;
		return this;
	}

	TreeContext zoomout() throws XVException {
		level--;
		if (level < 0) throw new XVException("Level is less than 0.");
		return this;
	}

}
