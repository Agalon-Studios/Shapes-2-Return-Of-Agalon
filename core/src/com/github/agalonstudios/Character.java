package com.github.agalonstudios;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by spr on 3/12/17.
 */
public abstract class Character extends Entity {
    protected float m_health;
    protected int m_maxHealth;
    protected float m_maxSpeed;
    protected float m_currentMaxSpeed;
    protected int m_level;
    protected boolean m_engaged;
    protected Vector2 m_velocity;
    protected Vector2 m_acceleration;
    protected float m_maxAcceleration;
    protected int m_directionFacing;
    protected Array<Float> m_cooldownTimers;
    protected Stats m_Stats;
    protected Array<Ability> m_equippedAbilities;
    protected int m_maxAbilityCount;
    protected Array<Stats> m_currentEffects;
    public Character(float radius, Vector2 center, Shape shape, int health, float ms, float ma, int l) {
        super(radius, center, shape);
        m_health = health;
        m_maxHealth = health;
        m_maxSpeed = ms;
        m_currentMaxSpeed = m_maxSpeed;
        m_level = l;
        m_velocity = new Vector2(0, 0);
        m_acceleration = new Vector2(0, 0);
        m_maxAcceleration = ma;
        m_fixed = false;
        m_directionFacing = Direction.EAST;
        m_engaged = false;
        m_cooldownTimers = new Array<Float>();
        m_equippedAbilities = new Array<Ability>();
        m_Stats = new Stats(0, 0, 0, 1, 1, 1, 1, 0);

        m_currentEffects = new Array<Stats>();
    }

    protected void bindVelocity() {
        m_velocity.x *= .95;
        m_velocity.y *= .95;
        float currentVelocity = (float) Math.sqrt(m_velocity.x * m_velocity.x + m_velocity.y * m_velocity.y);
        if (currentVelocity >  m_currentMaxSpeed * m_Stats.speedChange) {
            m_velocity.x *= (m_currentMaxSpeed * m_Stats.speedChange / currentVelocity);
            m_velocity.y *= (m_currentMaxSpeed * m_Stats.speedChange / currentVelocity);
        }
    }



    public boolean alive() {
        return m_health > 0;
    }

    public void apply(Stats e) {
        m_Stats.speedChange += e.speedChange;
        m_Stats.damageChange += e.damageChange;
        m_Stats.defenseChange += e.defenseChange;
        m_health -= e.mageDamage + e.rangeDamage + e.meleeDamage;
        m_currentEffects.add(e);
    }

    public int getDirectionFacing() {
        return m_directionFacing;
    }

    public void update(float delta, World world) {
        updateCooldowns(delta);



        m_velocity.x += m_acceleration.x * delta * m_Stats.speedChange;
        m_velocity.y += m_acceleration.y * delta * m_Stats.speedChange;


        updateStats();

        bindVelocity();

        m_health += .1;
        if (m_health > m_maxHealth)
            m_health = m_maxHealth;

        if (m_health < 0) m_health = 0;
        translate(m_velocity.x * delta, m_velocity.y * delta);
    }

    private void updateStats() {
        for (int i = 0; i < m_currentEffects.size; i++) {
           Stats e = m_currentEffects.get(i);
            if (e.duration <= 0) {
                m_currentEffects.removeIndex(i--);
                m_Stats.speedChange -= e.speedChange;
                m_Stats.damageChange -= e.damageChange;
                m_Stats.defenseChange -= e.defenseChange;
            }

        }
    }

    public void resetVelocity() {
        m_acceleration.x = m_acceleration.y = m_velocity.x = m_velocity.y = 0;
    }

    private void updateCooldowns(float delta) {

        for (int i = 0; i < m_cooldownTimers.size; i++)
            m_cooldownTimers.set(i, m_cooldownTimers.get(i) > 0 ? m_cooldownTimers.get(i) - delta : 0);


    }

    public void cast(int abilityNum) {
        m_equippedAbilities.get(abilityNum).cast(this);
    }

    @Override
    public void runCollision(Entity other) {
        if (other instanceof EffectArea) return;

        if (other instanceof Player)
            if (this instanceof Character)
            ((Player) other).m_health -= 5;
        if (this instanceof Player)
            if (other instanceof Character)
            m_health -= 5;

        float xmove, ymove;
        xmove = this.getCentroidX() - other.getCentroidX();
        ymove = this.getCentroidY() - other.getCentroidY();
        float norm = (float) Math.sqrt(xmove * xmove + ymove * ymove);
        xmove /= norm;
        ymove /= norm;

        while (this.overlaps(other)) {
            this.translate(xmove, ymove);
        }
    }

}
