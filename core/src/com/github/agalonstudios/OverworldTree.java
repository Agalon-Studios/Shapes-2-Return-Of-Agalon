package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by spr on 3/17/17.
 */
public class OverworldTree extends Entity {
    public OverworldTree(int x, int y) {
        super(x, y, 45, 32);
    }

    @Override
    public void runCollision(Entity other) {
        ;
    }

    @Override
    public void render() {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        Rectangle boundingRectangle = m_shape.getBoundingRectangle();

        sr.setColor(164/255.f, 97/255.f, 13/255.f, 1);
        sr.rect(boundingRectangle.x - camera.position.x, boundingRectangle.y - camera.position.y, 45, 128);
        sr.setColor(186/255.f, 122/255.f, 43/255.f, 1);
        sr.rect(boundingRectangle.x + 3 - camera.position.x,boundingRectangle.y + 3 - camera.position.y, 45 - 6, 128 - 6);

        sr.setColor(17/255.f, 164/255.f, 13/255.f, 1);
        sr.rect(boundingRectangle.x - 50 - camera.position.x, boundingRectangle.y + 64 - camera.position.y, 50 + 45 + 50, 128);
        sr.setColor(63/255.f, 206/255.f, 60/255.f, 1);
        sr.rect(boundingRectangle.x - 50 + 3 - camera.position.x, boundingRectangle.y + 3 + 64 - camera.position.y, 50 + 45 + 50 - 6, 128 - 6);

    }
}
