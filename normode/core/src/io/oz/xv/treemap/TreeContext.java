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
//	private float space;
	
	/**Current level*/
	int level;

	/**availability*/
	// private int[] wh;
	private int columns;

	/**current empty cell in current (last) row */
	private int freex;

	/**w-column(+x) x h-row(+z) */
	private ArrayList<TreemapNode[]> pool;

//	public TreeContext space(float f) {
//		this.space = f;
//		return this;
//	}

	TreeContext(PooledEngine ecs2) {
		this.ecs = ecs2;
		level = 0;
//		space = 1f;
		freex = Integer.MAX_VALUE;
	}
	
	public Color getColor(String word) {
		return color;
	}

	/**Create tree structure, setup resource pool for allocating.
	 * @param synsets
	 * @return 
	 */
	ArrayList<TreemapNode[]> init(ArrayList<SynsetInf> synsets) {
		int size = synsets.size();
		int[] wh = XMath.encampass(size);
		columns = wh[1];
		freex = 0;
		
		pool = new ArrayList<TreemapNode[]>(wh[0]);
		pool.add(new TreemapNode[wh[1]]);
		return pool;
	}

	/**One way allocating a node from pool.
	 * It's the map's responsibility to prune and reuse resource.
	 * @param si
	 * @return
	 */
	TreemapNode allocatNode(SynsetInf si) {
		// allocate from initial matrix
		int rx = pool.size() - 1;
		TreemapNode n = new TreemapNode(this, freex, -level, rx);
		pool.get(rx)[freex] = n;

		freex++;
		if (freex >= columns) {
			// insert to next row when creating, ignored and add new at end when later appending 
			TreemapNode[] nextRow = new TreemapNode[columns];
			pool.add(nextRow);
			freex = 0;
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
