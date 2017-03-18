package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by spr on 3/13/17.
 */
public class DungeonEntrance extends Entity {
    private int m_level;
    private Theme m_theme;
    private Rectangle m_entranceRect;

    // 96 160

    public DungeonEntrance(int x, int y, int level, Theme theme) {
        super(x, y, 96, 32);
        m_entranceRect = new Rectangle(x + 10, y, m_rect.width - 20, 64);
        m_level = level;
        m_theme = theme;
    }

    public Rectangle getEntranceRect() {
        return m_entranceRect;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void runCollision(Entity other) {
        ;
    }

    @Override
    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sr.setColor(137/255.f, 90/255.f, 56/255.f, 1);
        sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, 160);
        sr.setColor(180/255.f, 144/255.f, 67/255.f, 1);
        sr.rect(m_rect.x + 3 - camera.position.x, m_rect.y + 3 - camera.position.y, m_rect.width - 6, 154);

        sr.setColor(137/255.f, 90/255.f, 56/255.f, 1);
        sr.rect(m_rect.x + 7 - camera.position.x, m_rect.y - camera.position.y, m_rect.width - 14, 160 - 29);
        sr.setColor(0, 0, 0, 0);
        sr.rect(m_rect.x + 10 - camera.position.x, m_rect.y - camera.position.y, m_rect.width - 20, 160 - 32);
    }

    public int getLevel() { return m_level; }
    public Theme getTheme() { return m_theme; }
}
