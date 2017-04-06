package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spr on 3/12/17.
 */
public abstract class Entity {
    protected int m_ID;
    protected boolean m_fixed;
    protected Polygon m_shape;
    private float[] m_cameraAdjustedVertices;
    protected Color m_color;

    public Entity(float x, float y, float width, float height) {
        float[] vertices = {0, 0, 0, height, width, height, width, 0};
        m_cameraAdjustedVertices = new float[vertices.length];
        m_shape = new Polygon(vertices);

        m_shape.translate(x, y);

        m_ID = EntityManager.getNextValidEntityID();
        EntityManager.registerEntity(this);
    }

    public Entity(float radius, Vector2 position, Shape shape) {
        int numSides = 0;
        float baseRotation = 0;
        switch(shape) {
            case TRIANGLE:
                numSides = 3;

                break;
            case SQUARE:
                numSides = 4;
                baseRotation = 45;
                break;
            case PENTAGON:
                numSides = 5;
                break;
            case CIRCLE:
                //TODO make this work
                numSides = 4;
                break;
            case OCTAGON:
                numSides = 8;
                baseRotation = 45;
                break;
        }
        float[] vertices = new float[numSides * 2];
        m_cameraAdjustedVertices = new float[numSides * 2];

        for (int i = 0; i < numSides; i++) {
            vertices[i * 2] = (float) (radius + radius * Math.cos(2 * Math.PI * i / numSides));
            vertices[i * 2 + 1] = (float) (radius + radius * Math.sin(2 * Math.PI * i / numSides));
        }



        m_shape = new Polygon(vertices);

        m_shape.setOrigin(radius, radius);

        m_shape.translate(position.x, position.y);
        m_shape.setRotation(baseRotation);



        m_ID = EntityManager.getNextValidEntityID();
        EntityManager.registerEntity(this);
    }


    public void setPosition(float x, float y) {
        m_shape.setPosition(x, y);
    }

    public void translate(float x, float y) {
        m_shape.translate(x, y);
    }

    public float getX() {
        return m_shape.getX();
    }

    public float getY() {
        return m_shape.getY();
    }

    public float getWidth() {
        return m_shape.getBoundingRectangle().getWidth();
    }

    public float getHeight() {
        return m_shape.getBoundingRectangle().getHeight();
    }

    public int getID() {
        return m_ID;
    }

    public boolean isFixed() {
        return m_fixed;
    }


    public abstract void runCollision(Entity other);

    public void receiveMessage(Message m) {
        ;
    }

    public void render() {
        ExtendedShapeRenderer er = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        er.setColor(m_color);
        m_shape.rotate(1);
        er.polygon(getcameraAdjustedVertices());
    }

    public float[] getcameraAdjustedVertices() {
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();
        float[] tempVertices = m_shape.getTransformedVertices();

        for (int i = 0; i < tempVertices.length; i += 2) {
            m_cameraAdjustedVertices[i] = tempVertices[i] - camera.position.x;
            m_cameraAdjustedVertices[i + 1] = tempVertices[i + 1] - camera.position.y;
        }

        return m_cameraAdjustedVertices;
    }

    public boolean overlaps(Entity e) {
        return Intersector.overlapConvexPolygons(this.getShape(), e.getShape());
    }

    public Polygon getShape() {
        return this.m_shape;
    }

}
