package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by spr on 3/18/17.
 */
public class Traveler extends Character {
    private int dir;
    private State state;
    private float stateTimer;

    private enum State {
        WALK, IDLE
    }

    public Traveler(int x, int y) {
        super(x, y, 32, 32, 100, 50, 10, 1);
        dir = MathUtils.random(7);

        stateTimer = MathUtils.random(1.f, 3.f);
    }

    @Override
    public void runCollision(Entity other) {
        if (m_rect.overlaps(other.getRect()))
            revertPosition();
    }

    public void update(float delta) {
        m_revert.x = m_rect.x;
        m_revert.y = m_rect.y;

        stateTimer -= Gdx.graphics.getDeltaTime();

        if (state == State.WALK) {
            m_rect.x += Direction.dxdyScreen[dir][0] * m_currentMaxSpeed * Gdx.graphics.getDeltaTime();
            m_rect.y += Direction.dxdyScreen[dir][1] * m_currentMaxSpeed * Gdx.graphics.getDeltaTime();
        }

        if (stateTimer <= 0) {
            state = state == State.WALK ? State.IDLE : State.WALK;
            if (state == State.WALK)
                dir = MathUtils.random(7);
            stateTimer = MathUtils.random(1.f, 3.f);
        }
    }

    @Override
    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sr.setColor(199 / 255.f, 143 / 255.f, 97 / 255.f, 1);
        sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, m_rect.height);
        sr.setColor(230 / 255.f, 169 / 255.f, 120 / 255.f, 1);
        sr.rect(m_rect.x + 3 - camera.position.x, m_rect.y + 3 - camera.position.y, m_rect.width - 6, m_rect.height - 6);

    }
}
