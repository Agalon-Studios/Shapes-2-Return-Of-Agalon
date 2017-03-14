package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * DungeonRoom
 * Author: Sean Rapp
 */
public class DungeonRoom extends World {
    private Array<Door> m_doors;
    private Array<EnemySpawnPoint> m_enemySpawnPoints;
    private Dungeon m_dungeonRef;
    private WaterDrop m_waterDrop;


    public DungeonRoom(DungeonRoomPlan plan, Dungeon d) {
        super(d.getPlayerRef());
        m_doors = new Array<Door>();
        m_dungeonRef = d;

        for (int i = 0; i < 4; i++)
            m_doors.insert(i, null);

        for (int i = 0; i < plan.grid.length; i++) {
            for (int j = 0; j < plan.grid[0].length; j++) {
                if (plan.grid[i][j] instanceof WallItem) {
                    m_walls.add(new Wall(j * Wall.WIDTH, (plan.grid.length - i) * Wall.HEIGHT));
                }
                if (plan.grid[i][j] instanceof DoorItem) {
                    m_doors.set(((DoorItem) plan.grid[i][j]).getDir(), new Door(((DoorItem) plan.grid[i][j]).getDir(), j * Wall.WIDTH, (plan.grid.length - i) * Wall.HEIGHT));
                }
                if (plan.grid[i][j] instanceof EnemySpawnPointItem) {

                }
            }
        }

        m_waterDrop = null;

        // TODO this is placeholder for the testing
        m_playerRef.getRect().x = plan.grid.length / 2 * Wall.WIDTH;
        m_playerRef.getRect().y = plan.grid[0].length / 2 * Wall.HEIGHT;
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
        m_shapeRendererRef.end();

        if (m_waterDrop != null)
            m_waterDrop.render(delta);
    }

    private void update(float delta) {
        m_playerRef.update(delta);



        if (m_waterDrop != null) {
            m_waterDrop.update(delta);
            if (m_waterDrop.done())
                m_waterDrop = null;
        }
        else if (MathUtils.random(100) > 90) {
            m_waterDrop = new WaterDrop((int)m_playerRef.getRect().x + MathUtils.random(-1000, 1000), (int)m_playerRef.getRect().y + MathUtils.random(-1000, 1000));
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
    }

    public void movePlayerToDoorAt(int dir) {
        m_playerRef.getRect().x = m_doors.get(dir).getRect().x;
        m_playerRef.getRect().y = m_doors.get(dir).getRect().y;

        // TODO make displacement equal for all doors (i.e. entering from the east displacement
        // TODO is player.width less than entering from the west displacement, because the X coordinate
        // TODO of the player is the top-left of the player. Same for north and south, but with the height,
        // TODO and the Y coordinate.
        m_playerRef.getRect().x += Direction.dxdyScreen[Direction.opposite(dir)][0] * Wall.WIDTH + Wall.WIDTH / 2;
        m_playerRef.getRect().y += Direction.dxdyScreen[Direction.opposite(dir)][1] * Wall.HEIGHT + Wall.HEIGHT / 2;

    }
}
