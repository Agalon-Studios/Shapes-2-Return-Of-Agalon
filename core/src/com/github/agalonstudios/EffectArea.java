package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.github.agalonstudios.Ability.AbilityType;
/**
 * Created by spr on 3/12/17.
 */
public class EffectArea {
    private Character m_characterRef;
    private int m_count;
    private float m_duration;
    private float m_radius;
    private Vector2 m_position;
    private Stats m_effect;
    private Polygon effectArea;
    private float[] m_cameraAdjustedVertices;
    private AbilityType m_type;
    private float m_currentDuration;
    private boolean m_isDone;
    private float m_timeTillActive;
    private boolean m_isActive;

    public EffectArea(Stats e, int count, float duration, float x, float y, float radius, Character characterRef, AbilityType type) {
        m_effect = e;
        m_count = count;
        m_duration = duration;
        m_position = new Vector2(x, y);
        m_radius = radius;
        m_characterRef = characterRef;
        m_type = type;
        int numSides = 16;

        float[] vertices = new float[numSides * 2];
        m_cameraAdjustedVertices = new float[numSides * 2];

        for (int i = 0; i < numSides; i++) {
            vertices[i * 2] = (float) (radius + radius * Math.cos(2 * Math.PI * i / numSides));
            vertices[i * 2 + 1] = (float) (radius + radius * Math.sin(2 * Math.PI * i / numSides));
        }

        effectArea = new Polygon(vertices);

        effectArea.setOrigin(radius, radius);

        effectArea.translate(m_position.x, m_position.y);

    }

    public void setPosition(float x, float y) {
        m_position.set(x, y);
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
                er.circle(m_position.x, m_position.y, m_radius);
                break;
        }
    }

    public boolean done() {
        return m_isDone;
    }

    public void runCollision(Entity o) {
        if (m_isActive) {

        }
    }
}
