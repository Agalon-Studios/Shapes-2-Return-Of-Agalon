package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Peter on 4/20/2017.
 */

public class Enemy3 extends Character {
    private EnemySpawnPoint m_spawnPointRef;
    private float moveTimer;
    private float moveTime;

    public Enemy3(float x, float y, int l, EnemySpawnPoint sp) {
        super(32, new Vector2(x, y), Shape.TRIANGLE, 800, 50, 7, 2);
        //m_fixed = false;
        m_spawnPointRef = sp;
        m_directionFacing = MathUtils.random(0, 1);
        m_directionFacing = MathUtils.random(0, 1);
        moveTime = MathUtils.random(5.0f, 7.0f);
        moveTimer = moveTime;
        m_engaged = true;
        m_color = new Color(Color.SLATE);
    }



    @Override
    public void update(float delta, World world) {
         // TODO remove, this is to test dropping items on death
        // before we can actually kill them
        if (m_health <= 0)
            m_spawnPointRef.decrementLivingCount();


        moveTimer -= Gdx.graphics.getDeltaTime();

        translate(
                Direction.dxdyScreen[m_directionFacing][0] * m_currentMaxSpeed * Gdx.graphics.getDeltaTime(),
                Direction.dxdyScreen[m_directionFacing][1] * m_currentMaxSpeed * Gdx.graphics.getDeltaTime()
        );

        if (moveTimer < 0) {
            switch (m_directionFacing) {
                case Direction.NORTH:
                    m_directionFacing = Direction.SOUTH;
                    break;
                case Direction.SOUTH:
                    m_directionFacing = Direction.NORTH;
                    break;
                /*
                case Direction.EAST:
                    m_directionFacing = Direction.SOUTH;
                    break;
                case Direction.WEST:
                    m_directionFacing = Direction.NORTH;
                    break;
                    */
            }
            moveTimer = moveTime;
        }
    }


    @Override
    public void render() {
        super.render();

        //TODO make in extendedshaperenderer borderedPolygon()
        //  er.setColor(220 / 255.f, 78 / 255.f, 59 / 255.f, 1);
        //  er.rect(m_rect.x + 3 - camera.position.x, m_rect.y + 3 - camera.position.y, m_rect.width - 6, m_rect.height - 6);

        //TODO set up hp bars for enemies properly
//        if (m_engaged) {
//            er.setColor(255 / 255.f, 0, 0, 1);
//            er.rect(m_rect.x - camera.position.x, m_rect.y + 10 + m_rect.height - camera.position.y, (m_health / (float) m_maxHealth) * m_rect.width, 5);
//        }
    }
}

