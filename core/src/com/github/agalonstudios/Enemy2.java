package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spr on 3/13/17.
 */
public class Enemy2 extends Character {
    private EnemySpawnPoint m_spawnPointRef;
    private float moveTimer;
    private float moveTime;

    public Enemy2(float x, float y, int l, EnemySpawnPoint sp) {
        super(32, new Vector2(x, y), Shape.PENTAGON, 400, 150, 10, 2);
        m_fixed = false;
        m_spawnPointRef = sp;
        m_directionFacing = MathUtils.random(0, 3);
        moveTime = MathUtils.random(.5f, 1.5f);
        moveTimer = moveTime;
        m_engaged = true;
        m_color = new Color(80 / 255.f, 220 / 255.f, 70 / 255.f, 1);
    }



    @Override
    public void update(float delta, World world) {

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
                    m_directionFacing = Direction.EAST;
                    break;
                case Direction.SOUTH:
                    m_directionFacing = Direction.WEST;
                    break;
                case Direction.EAST:
                    m_directionFacing = Direction.SOUTH;
                    break;
                case Direction.WEST:
                    m_directionFacing = Direction.NORTH;
                    break;
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
