package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by spr on 3/13/17.
 */
public class DungeonEntrance extends Entity {
    private int m_level;
    private Theme m_theme;
    private Polygon m_entranceRect;


    public DungeonEntrance(int x, int y, int level, Theme theme) {
        super(x, y, 96, 32);

        float[] vertices = {0, 0, 0, 64, 96 - 20, 64, 96-20, 0};

        m_entranceRect = new Polygon(vertices);
        m_entranceRect.translate(x, y);

        m_level = level;
        m_theme = theme;
    }


    @Override
    public void runCollision(Entity other) {
        ;
    }

    @Override
    public Polygon getShape() {
        return m_entranceRect;
    }

    @Override
    public void render() {
        ExtendedShapeRenderer er = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        Rectangle rect = m_shape.getBoundingRectangle();

        er.setColor(137/255.f, 90/255.f, 56/255.f, 1);
        er.rect(rect.x - camera.position.x, rect.y - camera.position.y, rect.width, 160);
        er.setColor(180/255.f, 144/255.f, 67/255.f, 1);
        er.rect(rect.x + 3 - camera.position.x, rect.y + 3 - camera.position.y, rect.width - 6, 154);

        er.setColor(137/255.f, 90/255.f, 56/255.f, 1);
        er.rect(rect.x + 7 - camera.position.x, rect.y - camera.position.y, rect.width - 14, 160 - 29);
        er.setColor(0, 0, 0, 1);
        er.rect(rect.x + 10 - camera.position.x, rect.y - camera.position.y, rect.width - 20, 160 - 32);
    }

    public int getLevel() { return m_level; }
    public Theme getTheme() { return m_theme; }


}
