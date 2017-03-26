package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by spr on 3/12/17.
 */
public class Wall extends Entity {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    public Wall(int x, int y) {
        super(x, y, Wall.WIDTH, Wall.HEIGHT);
        m_fixed = true;
    }


    @Override
    public void runCollision(Entity other) {

    }

    @Override
    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, m_rect.height);
    }
}
