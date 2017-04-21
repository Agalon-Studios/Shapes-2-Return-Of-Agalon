package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    protected Vector2 m_centroid;

    public Entity(float x, float y, float width, float height) {
        float[] vertices = {0, 0, 0, height, width, height, width, 0};
        m_cameraAdjustedVertices = new float[vertices.length];
        m_shape = new Polygon(vertices);

        m_color = Color.MAGENTA;

        m_fixed = true;
        m_shape.translate(x, y);

        m_ID = EntityManager.getNextValidEntityID();
        EntityManager.registerEntity(this);

        m_centroid = new Vector2();

        ((Agalon) (Gdx.app.getApplicationListener())).getShapeRenderer().getCentroid(m_shape.getVertices(), m_centroid);
        m_centroid.x += x;
        m_centroid.y += y;
    }


    public Entity(float radius, Vector2 position, Shape shape) {
        m_fixed = true;
        int numSides = 0;
        float baseRotation = 0;

        m_color = Color.MAGENTA;

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
                numSides = 16;
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

        m_centroid = new Vector2();

        ((Agalon) (Gdx.app.getApplicationListener())).getShapeRenderer().getCentroid(m_shape.getVertices(), m_centroid);
        m_centroid.x += position.x;
        m_centroid.y += position.y;

    }


    public void setPosition(float x, float y) {
        m_shape.setPosition(x, y);
        ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer().getCentroid(m_shape.getVertices(), m_centroid);
        m_centroid.x += x;
        m_centroid.y += y;
    }

    public void translate(float x, float y) {
        m_shape.translate(x, y);
        m_centroid.x = m_centroid.x + x;
        m_centroid.y = m_centroid.y + y;
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
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        ExtendedShapeRenderer er = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        er.setColor(m_color);
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
        //return m_rect.overlaps(e.getRect());
    }

    public boolean overlaps(EffectArea eot) {
        return Intersector.overlapConvexPolygons(this.getShape(), eot.getShape());
    }

    public Polygon getShape() {
        return this.m_shape;
    }

    public float getCentroidX() {
        return m_centroid.x;
    }

    public float getCentroidY() {
        return m_centroid.y;
    }
}
