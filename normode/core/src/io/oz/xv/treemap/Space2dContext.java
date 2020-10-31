package io.oz.xv.treemap;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector3;

import io.oz.xv.math.XMath;

/** A helper class for 2d grid space management.<br>
 * Treemap here is actually fixed size of cubes. */
public class Space2dContext {
	public Engine ecs;

	/**Grid scale*/
	private Vector3 space = new Vector3(80f, 20f, 80f);
	public Vector3 space() { return space; }
	
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
	private ArrayList<Cell2D[]> rowPool;

	Space2dContext(PooledEngine ecs) {
		this.ecs = ecs;
		level = 0;
		freeCol = Integer.MAX_VALUE;
	}
	
	public Space2dContext(Space2dContext parentCtx) {
		this.ecs = parentCtx.ecs;
		level = parentCtx.level + 1;
		freeCol = Integer.MAX_VALUE;
	} 

	/**Create tree structure, setup resource pool for allocating.
	 * @param size
	 * @return this context 
	 */
	Space2dContext init(int size) {
		// int size = synsets.size();
		int[] wh = XMath.encampass(size);
		columns = wh[1];
		freeCol = 0;
		
		rowPool = new ArrayList<Cell2D[]>(wh[0]);
		rowPool.add(new Cell2D[wh[1]]);
		return this;
	}

	/**One way allocating a node from pool.
	 * It's the map's responsibility to prune and reuse resource.
	 * @return
	 */
	Cell2D allocatCell() {
		// allocate from initial matrix
		int rx = rowPool.size() - 1;
		Cell2D n = new Cell2D(this, freeCol, -level, rx);
		rowPool.get(rx)[freeCol] = n;

		freeCol++;
		if (freeCol >= columns) {
			// insert to next row when creating, ignored and add new at end when later appending 
			Cell2D[] nextRow = new Cell2D[columns];
			rowPool.add(nextRow);
			freeCol = 0;
		}

		return n;
	}

	/**@deprecated
	 * @return
	TreeContext zoomin() {
		level++;
		return this;
	}
	 */

	/**@deprecated
	 * @return
	 * @throws XVException
	TreeContext zoomout() throws XVException {
		level--;
		if (level < 0) throw new XVException("Level is less than 0.");
		return this;
	}
	 */

}
