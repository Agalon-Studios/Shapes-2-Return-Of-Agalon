package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.util.Queue;
/**
 * DungeonRoom
 * Author: Sean Rapp
 */
public class DungeonRoom extends World {
    private Array<Door> m_doors;
    private Array<EnemySpawnPoint> m_enemySpawnPoints;
    private Array<Chest> m_chests;
    private Dungeon m_dungeonRef;
    private WaterDrop m_waterDrop;
    private Theme m_theme;

    public DungeonRoom(DungeonRoomPlan plan, Dungeon d) {
        super(d.getPlayerRef());
        m_doors = new Array<Door>();
        m_enemySpawnPoints = new Array<EnemySpawnPoint>();
        m_chests = new Array<Chest>();
        m_nonPlayerCharacters = new Array<Character>();
        m_theme = plan.getTheme();
        m_dungeonRef = d;

        for (int i = 0; i < 4; i++)
            m_doors.insert(i, null);

        for (int i = 0; i < plan.grid.length; i++) {
            for (int j = 0; j < plan.grid[0].length; j++) {
                if (plan.grid[i][j] instanceof WallItem) {
                    m_walls.add(new Wall(j * Wall.WIDTH, (plan.grid.length - i) * Wall.HEIGHT));
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

        m_waterDrop = null;

        placePlayerInCenter(plan);
    }

    private void placePlayerInCenter(DungeonRoomPlan planRef) {
        // TODO this is placeholder for the testing
        m_playerRef.getRect().x = planRef.grid.length / 2 * Wall.WIDTH;
        m_playerRef.getRect().y = planRef.grid[0].length / 2 * Wall.HEIGHT;

        // flood fill from center of room until a floor cell, place
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(182/255.f, 125/255.f, 84/255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        super.render(delta);

        m_shapeRendererRef.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < m_doors.size; i++) {
            if (m_doors.get(i) == null)
                continue;
            m_doors.get(i).render(delta);
        }
        for (Chest c : m_chests)
            c.render(delta);

        for (Character e : m_nonPlayerCharacters) {
            e.render(delta);
        }

        for (CastObject co : m_castObjects) {
            co.render(delta);
        }

        m_shapeRendererRef.end();

        if (m_waterDrop != null)
            m_waterDrop.render(delta);

        // TODO remove
        // just printing fps
        // System.out.println(1.f / delta);
    }

    protected void update(float delta) {
        for (EnemySpawnPoint esp : m_enemySpawnPoints) {
            if (m_playerRef.getRect().overlaps(esp.getRect()))
                esp.spawnEnemies(this);
        }

        for (int i = 0; i < m_nonPlayerCharacters.size; i++) {
            m_nonPlayerCharacters.get(i).update(delta, this);
            if (!m_nonPlayerCharacters.get(i).alive()) {
                m_nonPlayerCharacters.removeIndex(i);
            }
        }

        m_playerRef.update(delta, this);

        if (m_waterDrop != null) {
            m_waterDrop.update(delta);
            if (m_waterDrop.done())
                m_waterDrop = null;
        }
        else if (MathUtils.random(100) > 90) {
            m_waterDrop = new WaterDrop((int)m_playerRef.getRect().x + MathUtils.random(-1000, 1000), (int)m_playerRef.getRect().y + MathUtils.random(-1000, 1000));
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

            if (m_playerRef.getRect().overlaps(m_doors.get(i).getRect())) {
                m_dungeonRef.changeRoom(m_doors.get(i).getDir(), i);
                m_dungeonRef.currentRoom().movePlayerToDoorAt(Direction.opposite(this.m_doors.get(i).getDir()));
                break;
            }
        }

        runCollisions(m_nonPlayerCharacters, m_walls, m_playerRefArray, m_chests, m_castObjects);
    }

    public void movePlayerToDoorAt(int dir) {
        m_playerRef.getRect().x = m_doors.get(dir).getRect().x;
        m_playerRef.getRect().y = m_doors.get(dir).getRect().y;

        // TODO make displacement equal for all doors
        //  (i.e. entering from the east displacement
        //  is player.width less than entering from the west displacement, because the X coordinate
        //  of the player is the top-left of the player. Same for north and south, but with the height,
        //  and the Y coordinate.
        m_playerRef.getRect().x += Direction.dxdyScreen[Direction.opposite(dir)][0] * Wall.WIDTH + Wall.WIDTH / 2;
        m_playerRef.getRect().y += Direction.dxdyScreen[Direction.opposite(dir)][1] * Wall.HEIGHT + Wall.HEIGHT / 2;

        // TODO remove this when real collision added
        m_playerRef.clearRevert();

    }
}
