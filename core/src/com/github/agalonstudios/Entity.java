package com.github.agalonstudios;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by spr on 3/12/17.
 */
public abstract class Entity {
    protected Rectangle m_rect;


    public Entity() {
        m_rect = new Rectangle(0, 0, 0, 0);
    }

    public Entity(float x, float y, int w, int h) {
        m_rect = new Rectangle(x, y, w, h);

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


    public abstract void update(float delta);
    public abstract void render(float delta);

}
