package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.agalonstudios.Ability.AbilityType;
/**
 * Created by spr on 3/12/17.
 */
public class EffectArea extends Entity {
    private Character m_characterRef;
    private int m_count;
    private float m_duration;
    private float m_radius;
    private Stats m_effect;
    private AbilityType m_type;
    private float m_currentDuration;
    private boolean m_isDone;
    private float m_timeTillActive;
    private boolean m_isActive;

    public EffectArea(Stats e, int count, float duration, Vector2 pos, float radius, AbilityType type) {
        super(radius, pos, Shape.CIRCLE);
        m_effect = e;
        m_count = count;
        m_duration = duration;

        m_radius = radius;
        m_type = type;



    }


    public float getRadius() {
        return m_radius;
    }

    public void update(float delta) {
        m_currentDuration += delta;
        m_timeTillActive += delta;

        m_isActive = false;
        if (m_timeTillActive >= m_duration / m_count) {
            m_isActive = true;
            m_timeTillActive = 0;
        }

        if (m_currentDuration >= m_duration)
            m_isDone = true;
    }

    public void render() {
        ExtendedShapeRenderer er = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();

        switch (m_type) {
            case FLAME_BURST:
                er.setColor(1, 0, 0, 0.5f);
                er.circle(getX(), getY(), m_radius);
                break;
        }
    }

    public Stats getStats() {
        return m_effect;
    }

    public int getCount() {
        return m_count;
    }

    public float getDuration() {
        return m_duration;
    }

    public Vector2 getPosition() {
        return getPosition();
    }

    @Override
    public void runCollision(Entity e) {
        if (m_isActive && e instanceof Character && e != m_characterRef) {
            ((Character) e).apply(m_effect);

            float xmove, ymove;
            xmove = e.getCentroidX() - this.getCentroidX();
            ymove = e.getCentroidY() - this.getCentroidY();
            float norm = (float) Math.sqrt(xmove * xmove + ymove * ymove);
            xmove /= norm;
            ymove /= norm;

            xmove *= m_effect.knockback * m_currentDuration / m_duration;
            ymove *= m_effect.knockback * m_currentDuration / m_duration;

            e.translate(xmove, ymove);
        }
    }

    public boolean done() {
        return m_isDone;
    }
}
