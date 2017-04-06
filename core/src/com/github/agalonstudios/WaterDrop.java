package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spr on 3/13/17.
 */
@Deprecated
public class WaterDrop extends Entity {
    private float m_timer;
    private boolean m_splashing;
    private int m_splashRadius;
    private float m_splashOpacity;
    private float m_yvelocity;

    public WaterDrop(int x, int y) {
        super(4, new Vector2(x, y), Shape.SQUARE);
        m_timer = 1.f;
        m_splashing = false;
        m_splashOpacity = 1.f;
        m_splashRadius = 1;
        m_yvelocity = 0;
        m_color = new Color(125/255.f, 212/255.f, 238/255.f, m_splashOpacity);
    }

    @Override
    public void runCollision(Entity other) {
        ;
    }

    //@Override
    public void update(float delta) {
        m_timer -= delta;

        if (!m_splashing) {
            m_yvelocity -= 300 * delta;
            m_shape.translate(0, m_yvelocity * delta);
        }

        if (m_timer <= 0)
            m_splashing = true;

        if (m_splashing) {
            m_splashRadius += 200 * delta;
            m_splashOpacity -= 4.f * delta;
        }
    }

    @Override
    public void render() {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.setColor(m_color);
        float[] vertices = getcameraAdjustedVertices();
        if (!m_splashing) {
            sr.set(ShapeRenderer.ShapeType.Filled);
            sr.polygon(getcameraAdjustedVertices());
        }
        else {
            sr.set(ShapeRenderer.ShapeType.Line);
            sr.circle(vertices[0], vertices[1], m_splashRadius);
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public boolean done() {
        return m_splashOpacity <= 0;
    }
}
