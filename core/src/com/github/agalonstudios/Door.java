package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by spr on 3/13/17.
 */
public class Door extends Entity {
    private int m_direction;

    public Door(int dir, int x, int y) {
        super(x, y, 100, 100);
        m_direction = dir;
    }

    public int getDir() {
        return m_direction;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void runCollision(Entity other) {

    }

    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();
        sr.setColor(0.7f, 0.7f, 0.7f, 1);
        sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, m_rect.height);
        sr.setColor(0.3f, -.3f, 0.3f, 1);

        switch (m_direction) {
            case Direction.NORTH:
            case Direction.SOUTH:
                sr.rect(m_rect.x - camera.position.x + m_rect.width / 2 - 10, m_rect.y - camera.position.y + m_rect.height / 2,
                        5, 5);
                sr.rect(m_rect.x - camera.position.x + m_rect.width / 2 + 10, m_rect.y - camera.position.y + m_rect.height / 2,
                        5, 5);
                break;
            case Direction.EAST:
            case Direction.WEST:
                sr.rect(m_rect.x - camera.position.x + m_rect.width / 2, m_rect.y - camera.position.y + m_rect.height / 2 - 10,
                        5, 5);
                sr.rect(m_rect.x - camera.position.x + m_rect.width / 2, m_rect.y - camera.position.y + m_rect.height / 2 + 10,
                        5, 5);

        }
    }
}
