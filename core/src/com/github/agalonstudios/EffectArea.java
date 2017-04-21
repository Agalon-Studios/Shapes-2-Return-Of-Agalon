package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.github.agalonstudios.Ability.AbilityType;

import static com.badlogic.gdx.Input.Keys.C;

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
    private boolean isTranslated;

    public EffectArea(Stats e, int count, float duration, Vector2 pos, float radius, AbilityType type) {
        super(radius, pos, Shape.CIRCLE);
        m_effect = e;
        m_count = count;
        m_duration = duration;
        this.translate(radius, radius);
        m_radius = radius;
        m_type = type;
    }


    public float getRadius() {
        return m_radius;
    }

    public void update(float delta) {
        if (!isTranslated) {
            isTranslated = true;
           m_shape.translate(-getRadius(), -getRadius());
        }
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
        Camera c = ((Agalon) Gdx.app.getApplicationListener()).getCamera();
        ExtendedShapeRenderer er = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();

       // float alpha = (m_duration -m_currentDuration) / m_duration;

        //Gdx.gl.glEnable(GL20.GL_BLEND);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        switch (m_type) {
            case FLAME_BURST:
                er.borderedCircle(getX() + getRadius() - c.position.x, getY() + getRadius() - c.position.y, m_radius, new Color(Color.RED.r, Color.RED.g, Color.RED.b, 1));
                break;
            case ICE_ARROW:
                er.borderedCircle(getX() + getRadius() - c.position.x, getY() + getRadius() - c.position.y, m_radius, new Color(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, 1));
                break;
        }
        //Gdx.gl.glDisable(GL20.GL_BLEND);

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
        }
    }

    public boolean done() {
        return m_isDone;
    }
}
