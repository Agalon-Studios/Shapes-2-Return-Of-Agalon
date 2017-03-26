package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by spr on 3/26/17.
 */
public class StrikeCastObject extends CastObject {
    private int m_moveDirection;
    private float m_dx;
    private float m_dy;

    public StrikeCastObject(Character casterRef) {
        super(casterRef, 0, 0, 16, 16);

        m_effect = new Effect(2, 0, 0, 0, 0, 0, 0, null);

        m_dx = 0;
        m_dy = 0;

        float x = 0;
        float y = 0;

        switch (casterRef.getDirectionFacing()) {
            case Direction.NORTH:
                y += casterRef.getRect().height + 10;
                m_moveDirection = Direction.EAST;
                break;
            case Direction.SOUTH:
                x += casterRef.getRect().width;
                y -= 10 + m_rect.height;
                m_moveDirection = Direction.WEST;
                break;
            case Direction.EAST:
                x += casterRef.getRect().width + 10;
                y += casterRef.getRect().height;
                m_moveDirection = Direction.SOUTH;
                break;
            case Direction.WEST:
                x -= 10 + m_rect.width;
                m_moveDirection = Direction.NORTH;
                break;
        }

        m_rect.x = x;
        m_rect.y = y;
    }

    @Override
    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sr.setColor(106 / 255.f, 173 / 255.f, 253 / 255.f, 1);
        sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, m_rect.height);
        sr.setColor(166 / 255.f, 206 / 255.f, 1, 1);
        sr.rect(m_rect.x + 2 - camera.position.x, m_rect.y + 3 - camera.position.y, m_rect.width - 6, m_rect.height - 6);
    }

    @Override
    public void update(float delta, World world) {
        switch (m_moveDirection) {
            case Direction.EAST:
                m_dx += 100 * Gdx.graphics.getDeltaTime();
                break;
            case Direction.WEST:
                m_dx -= 100 * Gdx.graphics.getDeltaTime();
                break;
            case Direction.NORTH:
                m_dy += 100 * Gdx.graphics.getDeltaTime();
                break;
            case Direction.SOUTH:
                m_dy -= 100 * Gdx.graphics.getDeltaTime();
                break;
        }

        float x = 0;
        float y = 0;

        switch (m_casterRef.getDirectionFacing()) {
            case Direction.NORTH:
                y += m_casterRef.getRect().height + 10;
                m_moveDirection = Direction.EAST;
                break;
            case Direction.SOUTH:
                x += m_casterRef.getRect().width;
                y -= 10 + m_rect.height;
                m_moveDirection = Direction.WEST;
                break;
            case Direction.EAST:
                x += m_casterRef.getRect().width + 10;
                y += m_casterRef.getRect().height;
                m_moveDirection = Direction.SOUTH;
                break;
            case Direction.WEST:
                x -= 10 + m_rect.width;
                m_moveDirection = Direction.NORTH;
                break;
        }

        m_rect.x = m_casterRef.getRect().x + x + m_dx;
        m_rect.y = m_casterRef.getRect().y + y + m_dy;
    }


    @Override
    public boolean done() {
        return Math.abs(m_dx) >= m_casterRef.getRect().width ||
                Math.abs(m_dy) >= m_casterRef.getRect().height;

    }

    @Override
    public void runCollision(Entity other) {
        if (other instanceof Character) {
            ((Character) other).apply(m_effect);
        }
    }
}
