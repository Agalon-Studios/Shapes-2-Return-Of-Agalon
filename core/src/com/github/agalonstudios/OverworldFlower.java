package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by spr on 3/13/17.
 */
public class OverworldFlower extends Entity {
    private float m_waveTimer;
    private float m_waveTime;
    private boolean m_fluffPosition;
    private Rectangle m_leftFluff;
    private Rectangle m_rightFluff;
    private float[] m_flowerColor;

    public OverworldFlower(int x, int y, int w, int h) {
        super(x, y, w, h);
        m_waveTimer = MathUtils.random(.2f, 1.f);
        m_waveTime = m_waveTimer;
        m_leftFluff = new Rectangle(x, y, w, h);
        m_rightFluff = new Rectangle(x + w / 2, y + (2 * h) / 3, w, h);
        m_fluffPosition = false;
        m_flowerColor = new float[3];

        for (int i = 0; i < 3; i++)
            m_flowerColor[i] = MathUtils.random(0, 255) / 255.f;
    }

    @Override
    public void runCollision(Entity other) {
        ;
    }

    @Override
    public void update(float delta) {
        m_waveTimer -= Gdx.graphics.getDeltaTime();

        if (m_waveTimer < 0) {
            if (m_fluffPosition) {
                m_rightFluff.x -= m_rightFluff.width / 3;
            } else {
                m_rightFluff.x += m_rightFluff.width / 3;
            }
            m_waveTimer = m_waveTime;
            m_fluffPosition = !m_fluffPosition;
        }
    }

    @Override
    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sr.setColor(99/255.f, 154/255.f, 103/255.f, 1);
        sr.rect(m_leftFluff.x - camera.position.x, m_leftFluff.y - camera.position.y, m_leftFluff.width, m_leftFluff.height);
        sr.setColor(m_flowerColor[0], m_flowerColor[1], m_flowerColor[2], 1);
        sr.rect(m_rightFluff.x - camera.position.x, m_rightFluff.y - camera.position.y, m_rightFluff.width, m_rightFluff.height);
        /*sr.setColor(m_flowerColor[0] - .05f, m_flowerColor[1] - .05f, m_flowerColor[2] - .05f, 1);
        sr.rect(m_rightFluff.x + m_rightFluff.width / 2 - m_rightFluff.width / 3 - camera.position.x,
                m_rightFluff.y + m_rightFluff.height / 2 - m_rightFluff.height / 3 - camera.position.y,
                m_rightFluff.width / 3, m_rightFluff.height / 3);
*/
    }
}
