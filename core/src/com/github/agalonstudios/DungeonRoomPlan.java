import java.lang.*;
import java.util.*;
import java.io.*;


public class DungeonRoomPlan {
	public DungeonRoomPlanItem[][] grid;
	private int[][] doors;
	
	private static final int GENERATIONS = 6;
	private static final int BIRTHMIN    = 4;
	private static final int DEATHLIM    = 3;
	private static final int TREASURELIM = 5;


	private int[][] start;
	
	private static final int[] sy = {0, 0, 1, 1};
	private static final int[] rds = {1, -1, -1, 1};

	// test
	public static void main(String[] args) {
		DungeonRoomPlan test = new DungeonRoomPlan(Direction.SOUTH, 0, Theme.CAVE, false);
		test.print();	
	}

	public void print() {
		print(1);
	}

	public void print(int scale) {
		for (int i = 0; i < grid.length; i += scale) {
			for (int j = 0; j < grid[0].length; j+= scale) {
				System.out.print(grid[i][j].toChar());
				System.out.print(grid[i][j].toChar());
			}
			System.out.println();
		}	
	}

	public void printWithPlayer(int x, int y) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (x == j && y == i) {
					System.out.print("@");
					System.out.print("@");
					continue;
				}

				System.out.print(grid[i][j].toChar());
				System.out.print(grid[i][j].toChar());
			}
			System.out.println();
		}
	}

	public DungeonRoomPlan(int dir, int difficulty, Theme theme, boolean open) {
		allocateGrid(difficulty, theme);
		start = new int[4][2];
		start[0][0] = 0;
		start[0][1] = grid[0].length / 2;
		start[1][0] = grid.length - 1;
		start[1][1] = grid[0].length / 2;
		start[2][0] = grid.length / 2;
		start[2][1] = grid[0].length - 1;
		start[3][0] = grid.length / 2;
		start[3][1] = 0;

		doors = new int[4][2];

		for (int i = 0; i < 4; i++)
			Arrays.fill(doors[i], -1);
	
		placeWalls(difficulty, theme, open);
		placeDoors(difficulty, theme, dir);
		placeEnemySpawnPoints(difficulty, theme);
		placeChests(difficulty, theme);
	}

	private void allocateGrid(int difficulty, Theme theme) {
		// TODO room size can vary based on difficulty and theme
		grid = new DungeonRoomPlanItem[45][45];
	}

	private void placeWalls(int difficulty, Theme theme, boolean open) {
		populateRandomly(open);

		for (int g = 0; g < GENERATIONS; g++) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					int neighbors = countNeighbors(i, j);
					if (grid[i][j] instanceof WallItem)
						if (neighbors < DEATHLIM)
							grid[i][j] = FloorItem.getInstance();
						else
							grid[i][j] = WallItem.getInstance();
					else
						if (neighbors > BIRTHMIN)
							grid[i][j] = WallItem.getInstance();
						else
							grid[i][j] = FloorItem.getInstance();
				}
			}
		}

		for (int i = 0; i < grid.length; i++) {
			grid[i][0] = WallItem.getInstance();
			grid[0][i] = WallItem.getInstance();
			grid[grid.length - 1][i] = WallItem.getInstance();
			grid[i][grid[0].length - 1] = WallItem.getInstance();
		}



		fillSmallRegions();
	}

	/* fillSmallRegions
	 * When a room is generated, there might be areas in the room
	 * that are unreachable. This method fills in all ares with walls,
	 * other than the largest area in the room.
	 * e.g.:
	 *
	 * XXXX		XXXX
	 * X.XX		XXXX
	 * X..X		XXXX
	 * XXXX		XXXX
	 * X..X =>	X..X
	 * X..X		X..X
	 * X.XX		X.XX
	 * XXXX		XXXX
	 *
	 * This algorithm uses a single-pass implementation of Connected Component
	 * Labeling, and retains the size of each component, then fills all but the
	 * largest components. BFS is ran once per component, then the entire grid
	 * is traversed once.
	 */
	private void fillSmallRegions() {
		// Queue for bfs
		Queue<int[]> queue = new ArrayDeque<int[]>();
		// Current label
		int currentLabel = 1;
		// current label current size -- present day, present time hahaha
		int currentSize = 0;
		// current maximally sized label region
		int maxLabel = 1;
		// current size of maxLabel region
		int maxSize = 0;
		// Label grid
		int[][] labelGrid = new int[grid.length][grid[0].length];
		// used for adding coords to queue <huge meme>



		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				// If we've already labelled this cell or it's not a floor cell
				if (labelGrid[i][j] != 0 || !(grid[i][j] instanceof FloorItem))
					continue; // skip it

				queue.clear();
				labelGrid[i][j] = currentLabel;

				int[] start = {i, j};
				queue.add(start);

				while (!queue.isEmpty()) {
					int[] currentCoords = queue.remove();
		
					for (int d = 0; d < Direction.dxdy.length; d++) {
						int newX = currentCoords[0] + Direction.dxdy[d][0];
						int newY = currentCoords[1] + Direction.dxdy[d][1];
						
						if (newX < 0 || newY < 0 || newX >= labelGrid.length || newY >= labelGrid[0].length)
							continue;

						if (labelGrid[newX][newY] == 0 && grid[newX][newY] instanceof FloorItem) {
							labelGrid[newX][newY] = currentLabel;
							currentSize++;
							int[] addCoords = {newX, newY};

							queue.add(addCoords);
						}
					}
				}

				if (currentSize > maxSize) {
					maxSize = currentSize;
					maxLabel = currentLabel;
				}	
				currentLabel++;
				currentSize = 0;
			}
		}

		print();
		System.out.println();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (labelGrid[i][j] != maxLabel)
					grid[i][j] = WallItem.getInstance();
			}
		}

	}

	private void populateRandomly(boolean isopen) {
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[0].length; j++)
				grid[i][j] = Math.random() * 100 < (isopen ? 35 : 40) ? 
					WallItem.getInstance() :
					FloorItem.getInstance();
	}

	private int countNeighbors(int i, int j) {
		int returnValue = 0;

		for (int c = i - 1; c <= i + 1; c++) {
			for (int r = j - 1; r <= j + 1; r++) {
				if (c == i && r == j) continue;
				if (c >= 0 && r >= 0 && c < grid.length && r < grid[0].length) {
					if (grid[c][r] instanceof WallItem)
						returnValue++;
				}
				else returnValue++;
			}
		}

		return returnValue;
	}

	private void placeDoors(int difficulty, Theme theme, int fromDir) {
		for (int i = 0; i < Direction.dxdy.length; i++) {
			if (i != fromDir && Math.random() * 5 > 2)
				continue;
			addDoorInThe(i);
		}
	}
	private void placeEnemySpawnPoints(int difficulty, Theme theme) {
		;
	}

	private void placeChests(int difficulty, Theme theme) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (countNeighbors(i, j) >= TREASURELIM && grid[i][j] instanceof FloorItem) {
					grid[i][j] = new ChestItem(difficulty, theme);
				}
			}
		}
	}

	public boolean hasDoorInThe(int dir) {
		return doors[dir][0] != -1;
	}

	public void addDoorInThe(int i) {
		int[] newvec = new int[2];

		outer:
		for (int rd = 0; rd < grid.length; rd++) {
			start[i][sy[i]] += rds[i];

			if (start[i][sy[i]] < 0 || start[i][sy[i]] >= grid.length)
				break;
			
			for (int dist = 0; dist < grid.length; dist++) {
				newvec[1 ^ sy[i]] = start[i][1 ^ sy[i]] + dist;
				newvec[sy[i]] = start[i][sy[i]];

				if (newvec[1 ^ sy[i]] < 0 || newvec[1 ^ sy[i]] >= grid.length)
					break;

				//grid[newvec[0]][newvec[1]] = new DoorItem(i);
				//print();
				//System.out.println();

				if (grid[newvec[0]][newvec[1]] instanceof FloorItem) {
					grid[newvec[0] + Direction.dxdy[i][0]][newvec[1] + Direction.dxdy[i][1]] = new DoorItem(i);
					doors[i][0] = newvec[0] + Direction.dxdy[i][0];
					doors[i][1] = newvec[1] + Direction.dxdy[i][1];
					break outer;
				}

				newvec[1 ^ sy[i]] = start[i][1 ^ sy[i]] - dist;	

				if (grid[newvec[0]][newvec[1]] instanceof FloorItem) {
					grid[newvec[0] + Direction.dxdy[i][0]][newvec[1] + Direction.dxdy[i][1]]
						= new DoorItem(i);
					doors[i][0] = newvec[0] + Direction.dxdy[i][0];
					doors[i][1] = newvec[1] + Direction.dxdy[i][1];

					break outer;
				}		
			}
		}
	}

	public void removeDoor(int dir) {
		if (doors[dir][0] == -1)
			return;

		grid[doors[dir][0]][doors[dir][1]] = WallItem.getInstance();
		doors[dir][0] = -1;
	}

	public int[] getDoorCoordinates(int dir) {
		return doors[dir];
	}
} 
