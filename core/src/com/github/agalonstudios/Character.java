package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by spr on 3/12/17.
 */
public abstract class Character extends Entity {
    protected int m_health;
    protected int m_maxHealth;
    protected float m_maxSpeed;
    protected float m_currentMaxSpeed;
    protected int m_level;
    protected boolean m_engaged;
    protected Vector2 m_velocity;
    protected Vector2 m_acceleration;
    protected float m_maxAcceleration;
    protected int m_directionFacing;
    protected Array<EffectOverTime> m_effectOverTimes;
    protected Array<Float> m_cooldownTimers;
    protected Stats m_Stats;


    public Character(float x, float y, int w, int h, int health, float ms, float ma, int l) {
        super(x, y, w, h);
        m_health = health;
        m_maxHealth = health;
        m_maxSpeed = ms;
        m_currentMaxSpeed = m_maxSpeed;
        m_level = l;
        m_effectOverTimes = new Array<EffectOverTime>();
        m_velocity = new Vector2(0, 0);
        m_acceleration = new Vector2(0, 0);
        m_maxAcceleration = ma;
        m_fixed = false;
        m_directionFacing = Direction.EAST;
        m_engaged = false;
        m_cooldownTimers = new Array<Float>();
        // TODO m_Stats = new m_Stats
    }

    protected void bindVelocity() {
        m_velocity.x *= .95;
        m_velocity.y *= .95;
        float currentVelocity = (float) Math.sqrt(m_velocity.x * m_velocity.x + m_velocity.y * m_velocity.y);
        if (currentVelocity >  m_currentMaxSpeed) {
            m_velocity.x *= (m_currentMaxSpeed / currentVelocity);
            m_velocity.y *= (m_currentMaxSpeed / currentVelocity);
            System.out.println(m_velocity.x + " " + m_velocity.y);
        }
    }



    public boolean alive() {
        return m_health > 0;
    }

    public void apply(Stats e) {
        m_health -= e.m_meleeDamage + e.m_rangeDamage + e.m_mageDamage;
        // TODO do all of these
    }

    public int getDirectionFacing() {
        return m_directionFacing;
    }

    public void update(float delta, World world) {
        updateCooldowns(delta);

        m_velocity.x += m_acceleration.x * delta;
        m_velocity.y += m_acceleration.y * delta;

        bindVelocity();



        m_rect.x += m_velocity.x * delta;
        m_rect.y += m_velocity.y * delta;


    }

    private void updateCooldowns(float delta) {

        for (int i = 0; i < m_cooldownTimers.size; i++)
            m_cooldownTimers.set(i, m_cooldownTimers.get(i) > 0 ? m_cooldownTimers.get(i) - delta : 0);


    }

}
