public class Direction {
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;

	public static int opposite(int dir) {
		return dir %  2 == 0 ? dir + 1 : dir - 1;
	}

	public static final int[][] dxdy = {
		{-1, 0},
		{ 1,  0},
		{ 0,  1},
		{ 0,  -1}
	};
}
