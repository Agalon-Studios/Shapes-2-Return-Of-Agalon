package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by spr on 3/13/17.
 */
public class Door extends Entity {
    private int m_direction;
    private boolean m_locked;

    public Door(int dir, int x, int y) {
        super(x, y, 100, 100);
        m_color = new Color(0.7f, 0.7f, 0.7f, 1);
        m_direction = dir;
    }

    public int getDir() {
        return m_direction;
    }

    @Override
    public void runCollision(Entity other) {

    }

    @Override
    public void receiveMessage(Message m) {
        ;
    }

    public void render(float delta) {
       super.render();
        ExtendedShapeRenderer er = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        Camera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        Rectangle rect = m_shape.getBoundingRectangle();

        er.setColor(0.3f, -.3f, 0.3f, 1);
        //TODO stylize door
        switch (m_direction) {
            case Direction.NORTH:
            case Direction.SOUTH:
                er.rect(rect.x - camera.position.x + rect.width / 2 - 10, rect.y - camera.position.y + rect.height / 2,
                        5, 5);
                er.rect(rect.x - camera.position.x + rect.width / 2 + 10, rect.y - camera.position.y + rect.height / 2,
                        5, 5);
                break;
            case Direction.EAST:
            case Direction.WEST:
                er.rect(rect.x - camera.position.x + rect.width / 2, rect.y - camera.position.y + rect.height / 2 - 10,
                        5, 5);
                er.rect(rect.x - camera.position.x + rect.width / 2, rect.y - camera.position.y + rect.height / 2 + 10,
                        5, 5);
        }

        if (m_locked) {
            er.setColor(15/255.f, 107/255.f, 29/255.f, 1);
            switch (m_direction) {
                case Direction.NORTH:
                case Direction.SOUTH:
                    er.rect(rect.x - camera.position.x + 5, rect.y - camera.position.y + rect.height / 2 + 20, rect.width - 10,
                            10);
                    break;
                case Direction.EAST:
                case Direction.WEST:
                    er.rect(rect.x - camera.position.x + rect.width / 2 + 20, rect.y - camera.position.y + 5, 10, rect.height - 10);
            }
        }
    }
}
