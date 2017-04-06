package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
        super(32, new Vector2(x, y), Shape.SQUARE, 200, 200, 10, 1);
        m_fixed = false;
        m_spawnPointRef = sp;
        m_directionFacing = MathUtils.random(0, 3);
        moveTime = MathUtils.random(.5f, 1.5f);
        moveTimer = moveTime;
        m_color = new Color(80 / 255.f, 78 / 255.f, 137 / 255.f, 1);
    }

    @Override
    public void runCollision(Entity other) {
        if (other instanceof CastObject) {
            m_engaged = true;
            return;
        }
        // TODO this is a temporary collision handle
        // that just makes it so the
        // entity """doesn't move""" if there's a collision
        // For example, if you're running NW and hit a vertical wall,
        // you will stop completely instead of sliding vertically along
        // the wall, while holding W and A. If you just hit W though
        // you start moving vertically along the wall.

    }


    @Override
    public void update(float delta, World world) {

        if (m_health <= 0) {
            m_spawnPointRef.decrementLivingCount();
        }

        moveTimer -= Gdx.graphics.getDeltaTime();

        m_shape.translate(
                Direction.dxdyScreen[m_directionFacing][0] * m_currentMaxSpeed * delta,
                Direction.dxdyScreen[m_directionFacing][1] * m_currentMaxSpeed * delta
        );


        if (moveTimer < 0) {
            switch (m_directionFacing) {
                case Direction.NORTH:
                    m_directionFacing = Direction.EAST;
                    moveTime *= 2;
                    break;
                case Direction.SOUTH:
                    m_directionFacing = Direction.WEST;
                    break;
                case Direction.EAST:
                    m_directionFacing = Direction.SOUTH;
                    moveTime /= 2;
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
        //TODO er.borderedPoly()
        //sr.setColor(102 / 255.f, 99 / 255.f, 198 / 255.f, 1);
        //sr.rect(m_rect.x + 3 - camera.position.x, m_rect.y + 3 - camera.position.y, m_rect.width - 6, m_rect.height - 6);

        //TODO char.displayHP()
//        if (m_engaged) {
//            sr.setColor(255 / 255.f, 0, 0, 1);
//            sr.rect(m_rect.x - camera.position.x, m_rect.y + 10 + m_rect.height - camera.position.y, (m_health / (float) m_maxHealth) * m_rect.width, 5);
//        }
    }
}
