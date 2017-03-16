package com.github.agalonstudios;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by spr on 3/12/17.
 */
public abstract class Character extends Entity {
    protected int m_health;
    protected int m_maxHealth;
    protected int m_maxSpeed;
    protected int m_currentMaxSpeed;
    protected int m_level;
    protected Vector2 m_velocity;
    protected Vector2 m_acceleration;
    Array<EffectOverTime> m_effectOverTimes;

    public Character() {
        m_health = 0;
        m_maxHealth = 0;
        m_maxSpeed = 0;
        m_currentMaxSpeed = 0;
        m_level = 0;
        m_effectOverTimes = new Array<EffectOverTime>();
        m_velocity = new Vector2();
        m_acceleration = new Vector2();
    }

    public Character(float x, float y, int w, int h, int health, int ms, int l) {
        super(x, y, w, h);
        m_health = health;
        m_maxHealth = h;
        m_maxSpeed = ms;
        m_currentMaxSpeed = m_maxSpeed;
        m_level = l;
        m_effectOverTimes = new Array<EffectOverTime>();
        m_velocity = new Vector2();
        m_acceleration = new Vector2();
    }

    protected void bindVelocity() {
        if (m_velocity.x > m_currentMaxSpeed)
            m_velocity.x = m_currentMaxSpeed;
        if (0 - m_velocity.x < 0 - m_currentMaxSpeed)
            m_velocity.x = 0 - m_currentMaxSpeed;
        if (m_velocity.y > m_currentMaxSpeed)
            m_velocity.y = m_currentMaxSpeed;
        if (0 - m_velocity.y < 0 - m_currentMaxSpeed)
            m_velocity.y = 0 - m_currentMaxSpeed;
    }

    public void moveBy(int x, int y) {
        m_rect.x += x;
        m_rect.y += y;
    }

    public void update(float delta, World world) {
        ;
    }
}
