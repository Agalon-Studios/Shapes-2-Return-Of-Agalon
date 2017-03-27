package com.github.agalonstudios;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spr on 3/12/17.
 */
public abstract class Entity {
    protected int m_ID;
    protected Rectangle m_rect;
    protected boolean m_fixed;
    protected Shape shape;
    protected Sprite sprite;

    public Entity(String fileHandle, Shape shape) {
        this.shape = shape;
        sprite = new Sprite(new Texture(fileHandle));
        m_rect = sprite.getBoundingRectangle();

    }



    public Entity() {
        m_rect = new Rectangle(0, 0, 0, 0);
        m_ID = EntityManager.getNextValidEntityID();
        EntityManager.registerEntity(this);
    }

    public Entity(float x, float y, int w, int h) {
        m_ID = EntityManager.getNextValidEntityID();
        m_rect = new Rectangle(x, y, w, h);

        EntityManager.registerEntity(this);
    }

    public Entity(int x, int y, int w, int h, int radius) {
        m_rect = new Rectangle(x, y, w, h);
        m_ID = EntityManager.getNextValidEntityID();
        EntityManager.registerEntity(this);
    }

    public Entity(Rectangle rect) {
        m_rect = new Rectangle(rect.x, rect.y, rect.width, rect.height);
        m_ID = EntityManager.getNextValidEntityID();
        EntityManager.registerEntity(this);
    }

    public int getID() {
        return m_ID;
    }

    public Rectangle getRect() {
        return m_rect;
    }

    public boolean isFixed() {
        return m_fixed;
    }

    @Override
    public String toString() {
        return m_rect.toString();
    }

    public abstract void runCollision(Entity other);

    public boolean isOverlapping(Entity other) {
        return false;
    }

    public void receiveMessage(Message m) {
        ;
    }

    public abstract void render(float delta);


}
