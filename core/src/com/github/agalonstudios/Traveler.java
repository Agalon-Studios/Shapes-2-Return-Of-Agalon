package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

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
        super(32, new Vector2(32, 32), Shape.SQUARE, 100, 50, 10, 1);
        dir = MathUtils.random(7);
        m_color = new Color(199 / 255.f, 143 / 255.f, 97 / 255.f, 1);
        stateTimer = MathUtils.random(1.f, 3.f);
    }

    @Override
    public void runCollision(Entity other) {
       //TODO
    }

    public void update(float delta) {


        stateTimer -= Gdx.graphics.getDeltaTime();

        if (state == State.WALK) {
            translate(
                    Direction.dxdyScreen[dir][0] * m_currentMaxSpeed * Gdx.graphics.getDeltaTime(),
                    Direction.dxdyScreen[dir][1] * m_currentMaxSpeed * Gdx.graphics.getDeltaTime()
            );
        }

        if (stateTimer <= 0) {
            state = state == State.WALK ? State.IDLE : State.WALK;
            if (state == State.WALK)
                dir = MathUtils.random(7);
            stateTimer = MathUtils.random(1.f, 3.f);
        }
    }
}
