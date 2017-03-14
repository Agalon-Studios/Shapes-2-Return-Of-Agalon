package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by spr on 3/13/17.
 */
public class DungeonEntrance extends Entity {
    private int m_level;
    private Theme m_theme;

    public DungeonEntrance(int x, int y, int level, Theme theme) {
        super(x, y, 96, 160);
        m_level = level;
        m_theme = theme;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        ShapeRenderer sr = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sr.setColor(137/255.f, 90/255.f, 56/255.f, 1);
        sr.rect(m_rect.x - camera.position.x, m_rect.y - camera.position.y, m_rect.width, m_rect.height);
        sr.setColor(0, 0, 0, 0);
        sr.rect(m_rect.x + 10 - camera.position.x, m_rect.y - camera.position.y, m_rect.width - 20, m_rect.height - 32);
    }

    public int getLevel() { return m_level; }
    public Theme getTheme() { return m_theme; }
}
