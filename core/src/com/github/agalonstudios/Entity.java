package com.github.agalonstudios;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spr on 3/12/17.
 */
public abstract class Entity {
    protected Rectangle m_rect;
    protected Vector2 m_revert;
    protected Vector2 m_drevert;

    protected boolean m_fixed;


    public Entity() {
        m_rect = new Rectangle(0, 0, 0, 0);
    }

    public Entity(float x, float y, int w, int h) {
        m_rect = new Rectangle(x, y, w, h);
        m_revert = new Vector2();
        m_drevert = new Vector2();

    }

    public Entity(int x, int y, int w, int h, int radius) {
        m_rect = new Rectangle(x, y, w, h);
    }

    public Entity(Rectangle rect) {
        m_rect = new Rectangle(rect.x, rect.y, rect.width, rect.height);
    }

    public Rectangle getRect() {
        return m_rect;
    }

    public boolean isFixed() {
        return m_fixed;
    }

    protected void revertPosition() {
        m_rect.x = m_revert.x;
        m_rect.y = m_revert.y;
    }

    @Override
    public String toString() {
        return m_rect.toString();
    }

    public abstract void runCollision(Entity other);


    public abstract void update(float delta);
    public abstract void render(float delta);

}
