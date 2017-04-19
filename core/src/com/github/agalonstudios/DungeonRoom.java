package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
/**
 * DungeonRoom
 * Author: Sean Rapp
 */
public class DungeonRoom extends World {
    private Array<Door> m_doors;
    private Array<EnemySpawnPoint> m_enemySpawnPoints;
    private Array<Chest> m_chests;
    private Dungeon m_dungeonRef;
    private Theme m_theme;
    private Rectangle m_floor;
    private Array<Wall> m_innerWalls;

    public DungeonRoom(DungeonRoomPlan plan, Dungeon d) {
        super(d.getPlayerRef());
        m_doors = new Array<Door>();
        m_enemySpawnPoints = new Array<EnemySpawnPoint>();
        m_chests = new Array<Chest>();
        m_nonPlayerCharacters = new Array<Character>();
        m_theme = plan.getTheme();
        m_dungeonRef = d;
        m_innerWalls = new Array<Wall>();

        for (int i = 0; i < 4; i++)
            m_doors.insert(i, null);

        for (int i = 0; i < plan.grid.length; i++) {
            for (int j = 0; j < plan.grid[0].length; j++) {
                if (plan.grid[i][j] instanceof WallItem) {
                    if (!allNeighborsAreWalls(plan, i, j))
                        m_walls.add(new Wall(j * Wall.WIDTH, (plan.grid.length - i) * Wall.HEIGHT));
                    else
                        m_innerWalls.add(new Wall(j * Wall.WIDTH, (plan.grid.length - i) * Wall.HEIGHT));
                }
                else if (plan.grid[i][j] instanceof ChestItem) {
                    m_chests.add(new Chest(((ChestItem) plan.grid[i][j]), j * Wall.WIDTH, (plan.grid.length - i) * Wall.HEIGHT));
                }
                else if (plan.grid[i][j] instanceof DoorItem) {
                    m_doors.set(((DoorItem) plan.grid[i][j]).getDir(), new Door(((DoorItem) plan.grid[i][j]).getDir(), j * Wall.WIDTH, (plan.grid.length - i) * Wall.HEIGHT));
                }
                else if (plan.grid[i][j] instanceof EnemySpawnPointItem) {
                    EnemySpawnPointItem item = (EnemySpawnPointItem) plan.grid[i][j];
                    m_enemySpawnPoints.add(new EnemySpawnPoint(item, j * Wall.WIDTH - item.getRadius(), (plan.grid.length - i) * Wall.HEIGHT - item.getRadius()));
                }
            }
        }

        m_floor = new Rectangle(0, Wall.HEIGHT, plan.grid[0].length * Wall.WIDTH, plan.grid.length * Wall.HEIGHT);
        placePlayerInCenter(plan);
    }

    private boolean allNeighborsAreWalls(DungeonRoomPlan plan, int i, int j) {
        int count = 0;

        if (i > 0)
            count += plan.grid[i - 1][j] instanceof WallItem ? 1 : 0;
        else
            count++;

        if (i < plan.grid.length - 1)
            count += plan.grid[i + 1][j] instanceof WallItem ? 1 : 0;
        else
            count++;
        if (j > 0)
            count += plan.grid[i][j - 1] instanceof WallItem ? 1 : 0;
        else
            count++;
        if (j < plan.grid[0].length - 1)
            count += plan.grid[i][j + 1] instanceof WallItem ? 1 : 0;
        else
            count++;

        return count == 4;

    }

    private void placePlayerInCenter(DungeonRoomPlan planRef) {
        // TODO this is placeholder for the testing
        m_playerRef.setPosition(
                planRef.grid.length / 2 * Wall.WIDTH,
                planRef.grid[0].length / 2 * Wall.HEIGHT
        );

        // flood fill from center of room until a floor cell, place
    }

    @Override
    public void render(float delta) {
        delta *= 1;
        update(delta);

        Gdx.gl.glClearColor(137/255.f, 90/255.f, 56/255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        m_cameraRef.position.set(m_playerRef.getX() - Gdx.graphics.getWidth() / 2, m_playerRef.getY() - Gdx.graphics.getHeight() / 2, 0);

        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);

        /* Set the floor color and draw it */
        m_shapeRendererRef.setColor(182/255.f, 125/255.f, 84/255.f, 1);
        m_shapeRendererRef.rect(
                m_floor.x - m_cameraRef.position.x,
                m_floor.y - m_cameraRef.position.y,
                m_floor.width,
                m_floor.height
        );


        for (int i = 0; i < m_doors.size; i++) {
            if (m_doors.get(i) == null)
                continue;
            m_doors.get(i).render();
        }


        for (Character e : m_nonPlayerCharacters) {
            e.render();
        }

        for (CastObject co : m_castObjects) {
            co.render();
        }

        for (Chest c : m_chests)
            c.render();

        for (Wall w : m_innerWalls)
            w.render();

        m_shapeRendererRef.end();



        super.render(delta);
    }

    protected void update(float delta) {
        super.update(delta);
        for (EnemySpawnPoint esp : m_enemySpawnPoints) {
            if (m_playerRef.overlaps(esp))
                esp.spawnEnemies(this);
        }

        for (int i = 0; i < m_nonPlayerCharacters.size; i++) {
            m_nonPlayerCharacters.get(i).update(delta, this);
            if (!m_nonPlayerCharacters.get(i).alive()) {
                m_nonPlayerCharacters.removeIndex(i);
            }
        }



        for (int i = 0; i < m_castObjects.size; i++) {
            m_castObjects.get(i).update(delta, this);
            if (m_castObjects.get(i).done()) {
                m_castObjects.removeIndex(i);
            }
        }

        for (int i = 0; i < 4; i++) {
            if (m_doors.get(i) == null)
                continue;

            if (m_playerRef.overlaps(m_doors.get(i))) {
                m_dungeonRef.changeRoom(m_doors.get(i).getDir(), i);
                m_dungeonRef.currentRoom().movePlayerToDoorAt(Direction.opposite(this.m_doors.get(i).getDir()));
                break;
            }
        }

        runCollisions(m_nonPlayerCharacters, m_walls, m_playerRefArray, m_chests, m_castObjects);
    }

    public void movePlayerToDoorAt(int dir) {
        m_playerRef.m_shape.setPosition(m_doors.get(dir).m_shape.getX(), m_doors.get(dir).m_shape.getY());


        // TODO make displacement equal for all doors
        //  (i.e. entering from the east displacement
        //  is player.width less than entering from the west displacement, because the X coordinate
        //  of the player is the top-left of the player. Same for north and south, but with the height,
        //  and the Y coordinate.
        m_playerRef.translate(
                Direction.dxdyScreen[Direction.opposite(dir)][0] * Wall.WIDTH + Wall.WIDTH / 2,
                Direction.dxdyScreen[Direction.opposite(dir)][1] * Wall.HEIGHT + Wall.HEIGHT / 2
        );

    }
}
