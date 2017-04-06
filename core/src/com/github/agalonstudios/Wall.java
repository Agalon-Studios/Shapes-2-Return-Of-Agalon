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
        m_color = new Color(137/255.f, 90/255.f, 56/255.f, 1);
        m_fixed = true;
    }


    @Override
    public void runCollision(Entity other) {

    }


}
