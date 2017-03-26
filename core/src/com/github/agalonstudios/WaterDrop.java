package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by spr on 3/13/17.
 */
public class WaterDrop extends Entity {
    private float m_timer;
    private boolean m_splashing;
    private int m_splashRadius;
    private float m_splashOpacity;
    private float m_yvelocity;

    public WaterDrop(int x, int y) {
        super(x, y, 8, 8);
        m_timer = 1.f;
        m_splashing = false;
        m_splashOpacity = 1.f;
        m_splashRadius = 1;
        m_yvelocity = 0;
    }

    @Override
    public void runCollision(Entity other) {
        ;
    }

    @Override
    public void update(float delta) {
        m_timer -= delta;

        if (!m_splashing) {
            m_yvelocity -= 300 * delta;
            m_rect.y += m_yvelocity * delta;
        }

        if (m_timer <= 0)
            m_splashing = true;

        if (m_splashing) {
            m_splashRadius += 200 * delta;
            m_splashOpacity -= 4.f * delta;
        }
    }

    @Override
    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.setColor(125/255.f, 212/255.f, 238/255.f, m_splashOpacity);
        if (!m_splashing) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, m_rect.height);
            sr.end();
        }
        else {
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.circle(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_splashRadius);
            sr.end();
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public boolean done() {
        return m_splashOpacity <= 0;
    }
}
