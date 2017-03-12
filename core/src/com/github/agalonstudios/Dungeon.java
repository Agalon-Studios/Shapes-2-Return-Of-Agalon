import java.util.*;
import java.lang.*;
import java.util.*;

class Vector2 {
	int x, y;

	public Vector2(int a, int b) { x = a; y = b; }
	public void setX(int a) { x = a; }
	public void setY(int a) { y = a; }
	public int  getX() { return x; }
	public int  getY() { return y; }
}

public class Dungeon {
	//private DungeonRoom 		m_currentRoom;
	private Vector2 			m_currentRoomCoordinates;
	private DungeonRoomPlan[][] m_roomPlanGrid;

	public static void main(String[] args) {
		Dungeon d = new Dungeon(4, Theme.CAVE);
		d.test();
	}

	public void test() {
		int[] player = new int[2];
		int[] doorcoords = m_roomPlanGrid[m_currentRoomCoordinates.getX()][m_currentRoomCoordinates.getY()]
			.getDoorCoordinates(Direction.SOUTH);
		player[0] = doorcoords[1];
		player[1] = doorcoords[0];
		Scanner sin = new Scanner(System.in);
		char[] input;

		while (true) {
			m_roomPlanGrid[m_currentRoomCoordinates.getX()][m_currentRoomCoordinates.getY()]
				.printWithPlayer(player[0], player[1]);

			printPlanGrid();

			input = sin.next().toCharArray();
			if (input[0] == 'w')
				player[1]--;
			if (input[0] == 'a')
				player[0]--;
			if (input[0] == 's')
				player[1]++;
			if (input[0] == 'd')
				player[0]++;

			if (m_roomPlanGrid[m_currentRoomCoordinates.getX()][m_currentRoomCoordinates.getY()]
					.grid[player[1]][player[0]] instanceof DoorItem) {

				DoorItem door = (DoorItem) m_roomPlanGrid[m_currentRoomCoordinates.getX()]
					[m_currentRoomCoordinates.getY()]
					.grid[player[1]][player[0]];
				if (m_currentRoomCoordinates.getX() 
						+ Direction.dxdy[door.getDir()][0] < 0 ||
					   	m_currentRoomCoordinates.getX() 
						+ Direction.dxdy[door.getDir()][0] >= m_roomPlanGrid.length ||
					   	m_currentRoomCoordinates.getY() + 
						Direction.dxdy[door.getDir()][1] >= m_roomPlanGrid[0].length ||
					   	m_currentRoomCoordinates.getY() + 
						Direction.dxdy[door.getDir()][1] < 0)
					continue;
			
				moveRoom(Direction.dxdy[door.getDir()][0], 
						Direction.dxdy[door.getDir()][1]);
				doorcoords = m_roomPlanGrid[m_currentRoomCoordinates.getX()]
					[m_currentRoomCoordinates.getY()]
					.getDoorCoordinates(Direction.opposite(door.getDir()));

				player[0] = doorcoords[1];
				player[1] = doorcoords[0];
			}
		}
	}
		

	public Dungeon(int difficulty, Theme theme) {
		m_currentRoomCoordinates = new Vector2(0,0);
		generateRoomPlans(difficulty, theme);
	//	loadCurrentRoom();
	}

	public void changeRoom(int dir) {
		moveRoom(Direction.dxdy[dir][0], Direction.dxdy[dir][1]);
	}

	private void moveRoom(int x, int y) {
		m_currentRoomCoordinates.setX(m_currentRoomCoordinates.getX() + x);

		if (m_currentRoomCoordinates.getX() < 0 ||
				m_currentRoomCoordinates.getX() > m_roomPlanGrid.length)
			System.out.println("This shouldn't happen");

		m_currentRoomCoordinates.setY(m_currentRoomCoordinates.getY() + y);

		if (m_currentRoomCoordinates.getY() < 0 ||
				m_currentRoomCoordinates.getY() > m_roomPlanGrid[0].length)
			System.out.println("This shouldn't happen either");

//		loadCurrentRoom();
	}

	/* generateRoomPlans
	 * parameters:
	 * 	difficulty: int - the difficulty of the Dungeon
	 * 	theme: Theme - the theme of the Dungeon
	 * This is called in the Dungeon constructor ONLY.
	 * BFS is used to generate rooms. This is preferred over DFS, as DFS
	 * has a higher probability of generating long independent chains of rooms
	 */ 
	private void generateRoomPlans(int difficulty, Theme theme) {
		allocateRoomGrid(difficulty, theme);
		// Set m_currentRoomCoordinates
		pickStartingRoom();
		int x = m_currentRoomCoordinates.getX();
		int y = m_currentRoomCoordinates.getY();
		Queue<Vector2> roomQueue = new ArrayDeque<Vector2>();

		// Start at the first room
		roomQueue.add(m_currentRoomCoordinates);
		// Add the door to the Overworld at the South end of the room
		m_roomPlanGrid[x][y] = new DungeonRoomPlan(Direction.SOUTH, difficulty, theme, false);
		printPlanGrid();
		while (!roomQueue.isEmpty()) {
			Vector2 currentRoom = roomQueue.remove();

			// Possible create a room to the north, east, south, and west
			for (int i = 0; i < Direction.dxdy.length; i++) {
				if (m_roomPlanGrid[currentRoom.getX()][currentRoom.getY()]
						.hasDoorInThe(i)) {
					// The coordinates of the potential new room
					int newX = currentRoom.getX() + Direction.dxdy[i][0];
					int newY = currentRoom.getY() + Direction.dxdy[i][1];

					// If this room would be off of the grid, skip
					if (newX < 0 || newX >= m_roomPlanGrid.length ||
							newY < 0 || newY >= m_roomPlanGrid[0].length)
						continue;

					// If there's already a room there, this door goes to a room that
					// has already been made, so don't make a new room there.
					// However, if there is a door there in this room,
					// there might not be a door in the other room
					// leading to this room, so that door must be added.
					// Note: This includes the room that this room 'comes' from!
					if (m_roomPlanGrid[newX][newY] != null) {
						if (!m_roomPlanGrid[newX][newY].hasDoorInThe(Direction.opposite(i)))
							m_roomPlanGrid[newX][newY].addDoorInThe(Direction.opposite(i));
						continue;
					}
						
					// Create the new room
					m_roomPlanGrid[newX][newY] = new DungeonRoomPlan(Direction.opposite(i),
							difficulty, theme, Math.random() * 100 > 80);
					// Add it to the queue
					roomQueue.add(new Vector2(newX, newY));

					// TODO remove
					printPlanGrid();
				}
			}
		}

		trimOuterDoors();
	}

	/* trimOuterDoors
	 * Removes doors from rooms at the edge of the dungeon grid that
	 * lead beyond the grid, i.e.
	 *  43
	 * 12
	 * 567
	 *
	 * An eastern door in room 7 leads beyond the grid, and will be trimmed.
	 */
	private void trimOuterDoors() {
		for (int i = 0; i < m_roomPlanGrid.length; i++) {
			if (m_roomPlanGrid[0][i] != null)
				m_roomPlanGrid[0][i].removeDoor(Direction.NORTH);
			if (m_roomPlanGrid[m_roomPlanGrid.length - 1][i] != null)
				m_roomPlanGrid[m_roomPlanGrid.length - 1][i].removeDoor(Direction.SOUTH);
		}

		for (int i = 0; i < m_roomPlanGrid[0].length; i++) {
			if (m_roomPlanGrid[i][m_roomPlanGrid[0].length - 1] != null)
				m_roomPlanGrid[i][m_roomPlanGrid[0].length - 1].removeDoor(Direction.EAST);
			if (m_roomPlanGrid[i][0] != null)
				m_roomPlanGrid[i][0].removeDoor(Direction.WEST);
		}
	}

	private void printPlanGrid() {
		final char[] doorChars = {
			'^', 'v', '>', '<'
		};

		for (int i = 0; i < m_roomPlanGrid.length; i++) {
			System.out.print(i);
			for (int j = 0; j < m_roomPlanGrid[0].length; j++)	{
				if (m_roomPlanGrid[i][j] == null) {
					System.out.print("      ");
				}
				else {
					System.out.print(i == m_currentRoomCoordinates.getX() && j == m_currentRoomCoordinates.getY() ? "@" : "|");
					for (int k = 0; k < 4; k++) {
						System.out.print(m_roomPlanGrid[i][j].hasDoorInThe(k) ? doorChars[k] : " ");
					}
					System.out.print(i == m_currentRoomCoordinates.getX() && j == m_currentRoomCoordinates.getY() ? "@" : "|");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private void allocateRoomGrid(int difficulty, Theme theme) {
		m_roomPlanGrid = new DungeonRoomPlan[difficulty][difficulty];
	}

	private void pickStartingRoom() {
		m_currentRoomCoordinates.setX(m_roomPlanGrid.length / 2);
		m_currentRoomCoordinates.setY(m_roomPlanGrid[0].length / 2);
	}

	/* loadCurrentRoom
	 * creates a DungeonRoom from the DungeonRoomPlan at currentRoomCoordinates in
	 * roomPlanGrid
	 */
	private void loadCurrentRoom() {
//		m_currentRoom = new DungeonRoom(m_roomPlanGrid[m_currentRoomCoordinates.getX()]
//				[m_currentRoomCoordinates.getY()]);
	}
}
