package com.github.agalonstudios;


import java.util.*;
import java.lang.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/* Dungeon
 * Author: Sean Rapp
 */

public class Dungeon {
    private DungeonRoom         m_currentRoom;
    private int[] 			    m_currentRoomCoordinates;
    private DungeonRoomPlan[][] m_roomPlanGrid;
    private Player              m_playerRef;

    public Dungeon(int difficulty, Theme theme, Player pRef) {
        m_currentRoomCoordinates = new int[2];
        generateRoomPlans(difficulty, theme);
        m_playerRef = pRef;
        loadCurrentRoom();
    }

    // Change from one room to another
    public void changeRoom(int dir, int fromDir) {
        moveRoom(Direction.dxdy[dir][0], Direction.dxdy[dir][1]);
        m_currentRoom.movePlayerToDoorAt(Direction.opposite(fromDir));
    }

    private void moveRoom(int x, int y) {
        m_currentRoomCoordinates[0] = m_currentRoomCoordinates[0] + x;

        if (m_currentRoomCoordinates[0] < 0 ||
                m_currentRoomCoordinates[0] > m_roomPlanGrid.length)
            System.out.println("This shouldn't happen");

        m_currentRoomCoordinates[1] = m_currentRoomCoordinates[1] + y;

        if (m_currentRoomCoordinates[1] < 0 ||
                m_currentRoomCoordinates[1] > m_roomPlanGrid[0].length)
            System.out.println("This shouldn't happen either");

        System.out.println("Loading room r" + m_currentRoomCoordinates[0] + "c" + m_currentRoomCoordinates[1] + ": null ? " +
                (m_roomPlanGrid[m_currentRoomCoordinates[0]][m_currentRoomCoordinates[1]] == null ? "yes" : "no"));
		loadCurrentRoom();
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
        int x = m_currentRoomCoordinates[0];
        int y = m_currentRoomCoordinates[1];
        Queue<int[]> roomQueue = new ArrayDeque<int[]>();

        // Start at the first room
        roomQueue.add(m_currentRoomCoordinates);
        // Add the door to the Overworld at the South end of the room
        m_roomPlanGrid[x][y] = new DungeonRoomPlan(Direction.SOUTH, difficulty, theme, false);

        while (!roomQueue.isEmpty()) {
            int[] currentRoom = roomQueue.remove();

            // Possible create a room to the north, east, south, and west
            for (int i = 0; i < Direction.dxdy.length; i++) {
                if (m_roomPlanGrid[currentRoom[0]][currentRoom[1]]
                        .hasDoorInThe(i)) {
                    // The coordinates of the potential new room
                    int newX = currentRoom[0] + Direction.dxdy[i][0];
                    int newY = currentRoom[1] + Direction.dxdy[i][1];

                    // If this room would be off of the grid, skip
                    if (newX < 0 || newX >= m_roomPlanGrid.length ||
                            newY < 0 || newY >= m_roomPlanGrid[0].length) {
                        m_roomPlanGrid[currentRoom[0]][currentRoom[1]]
                                .removeDoor(i);
                        continue;
                    }

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
                            difficulty, theme, Math.random() * 5 > 4);
                    int[] addCoordinates = {newX, newY};
                    roomQueue.add(addCoordinates);
                }
            }
        }

        // TODO add Exit door (i.e. door that ends the dungeon) and the start door/location
        // TODO (where the player is put when they enter dungeon from overworld)
        // TODO Consider distance from the start room when placing deciding which room is
        // TODO the final room.
    }

    public void printPlanGrid() {
        final char[] doorChars = {
                '^', 'v', '>', '<'
        };
        final int[][] dirList = {
                {3, 0, 1, 2},
                {3, 1, 0, 2}
        };

        for (int i = 0; i < m_roomPlanGrid.length; i++) {
            System.out.print(i);
            for (int j = 0; j < m_roomPlanGrid[0].length; j++)	{
                if (m_roomPlanGrid[i][j] == null) {
                    System.out.print("      ");
                }
                else {
                    System.out.print(i == m_currentRoomCoordinates[0] && j == m_currentRoomCoordinates[1] ? "@" : "|");

                    for (int k = 0; k < dirList[0].length; k++)
                        System.out.print(m_roomPlanGrid[i][j].hasDoorInThe(dirList[i%2][k]) ? doorChars[dirList[i%2][k]] : " ");

                    System.out.print(i == m_currentRoomCoordinates[0] && j == m_currentRoomCoordinates[1] ? "@" : "|");
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
        // TODO pick an actual starting room.
        // This one just picks the middle-ish room.
        m_currentRoomCoordinates[0] = m_roomPlanGrid.length / 2;
        m_currentRoomCoordinates[1] = m_roomPlanGrid[0].length / 2;
    }

    /* loadCurrentRoom
     * creates a DungeonRoom from the DungeonRoomPlan at currentRoomCoordinates in
     * roomPlanGrid
     */
    private void loadCurrentRoom() {
        // TODO remove
        printPlanGrid();

		m_currentRoom = new DungeonRoom(m_roomPlanGrid[m_currentRoomCoordinates[0]]
				[m_currentRoomCoordinates[1]], this);
        ((Agalon) Gdx.app.getApplicationListener()).setScreen(m_currentRoom);

    }

    public DungeonRoom currentRoom() {
        return m_currentRoom;
    }

    public Player getPlayerRef() { return m_playerRef; }
}