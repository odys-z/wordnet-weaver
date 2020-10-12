package io.oz.xv.treemap;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;

import io.oz.jwi.SynsetInf;
import io.oz.xv.math.XMath;
import io.oz.xv.utils.XVException;

/** A helper class for treemap space grid management.<br>
 * Treemap here is actually fixed size of cubes. */
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

	/**Next empty cell index to be used in current (last) row */
	private int freeCol;

	/**rows of columns, size = c x r
	 * where column is also represented as grid.w and count in +x axis direction,
	 * row also represented as grid.d, count in +z axis direction.
	 * */
	private ArrayList<TreemapNode[]> rowPool;

	TreeContext(PooledEngine ecs2) {
		this.ecs = ecs2;
		level = 0;
		freeCol = Integer.MAX_VALUE;
	}
	
	public Color getColor(String word) {
		return color;
	}

	/**Create tree structure, setup resource pool for allocating.
	 * @param synsets
	 * @return this context 
	 */
	TreeContext init(ArrayList<SynsetInf> synsets) {
		int size = synsets.size();
		int[] wh = XMath.encampass(size);
		columns = wh[1];
		freeCol = 0;
		
		rowPool = new ArrayList<TreemapNode[]>(wh[0]);
		rowPool.add(new TreemapNode[wh[1]]);
		return this;
	}

	/**One way allocating a node from pool.
	 * It's the map's responsibility to prune and reuse resource.
	 * @param si
	 * @return
	 */
	TreemapNode allocatNode(SynsetInf si) {
		// allocate from initial matrix
		int rx = rowPool.size() - 1;
		TreemapNode n = new TreemapNode(this, freeCol, -level, rx);
		rowPool.get(rx)[freeCol] = n;

		freeCol++;
		if (freeCol >= columns) {
			// insert to next row when creating, ignored and add new at end when later appending 
			TreemapNode[] nextRow = new TreemapNode[columns];
			rowPool.add(nextRow);
			freeCol = 0;
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
