package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by spr on 3/13/17.
 */
public class Enemy1 extends Character {
    private EnemySpawnPoint m_spawnPointRef;
    private int dir;
    private float moveTimer;
    private float moveTime;

    public Enemy1(float x, float y, int l, EnemySpawnPoint sp) {
        super(x, y, 64, 64, 400, 150, l);
        m_fixed = false;
        m_spawnPointRef = sp;
        dir = MathUtils.random(0, 3);
        moveTime = MathUtils.random(.5f, 1.5f);
        moveTimer = moveTime;
    }

    @Override
    public void runCollision(Entity other) {
        if (other.getRect().overlaps(m_rect)) {
            revertPosition();
        }
    }

    @Override
    public void update(float delta, World world) {
        m_revert.x = m_rect.x;
        m_revert.y = m_rect.y;

        if (m_health <= 0) {
            m_spawnPointRef.decrementLivingCount();
        }

        moveTimer -= Gdx.graphics.getDeltaTime();

        m_rect.x += Direction.dxdyScreen[dir][0] * m_currentMaxSpeed * Gdx.graphics.getDeltaTime();
        m_rect.y += Direction.dxdyScreen[dir][1] * m_currentMaxSpeed * Gdx.graphics.getDeltaTime();

        if (moveTimer < 0) {
            switch (dir) {
                case Direction.NORTH:
                    dir = Direction.EAST;
                    break;
                case Direction.SOUTH:
                    dir = Direction.WEST;
                    break;
                case Direction.EAST:
                    dir = Direction.SOUTH;
                    break;
                case Direction.WEST:
                    dir = Direction.NORTH;
                    break;
            }
            moveTimer = moveTime;
        }


    }

    @Override
    public void update(float delta) {
        ;
    }


    @Override
    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sr.setColor(145 / 255.f, 81 / 255.f, 70 / 255.f, 1);
        sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, m_rect.height);
        sr.setColor(220 / 255.f, 78 / 255.f, 59 / 255.f, 1);
        sr.rect(m_rect.x + 3 - camera.position.x, m_rect.y + 3 - camera.position.y, m_rect.width - 6, m_rect.height - 6);
    }
}
