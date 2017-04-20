package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by mahzain on 4/19/17.
 */

public class DroppedItem extends Entity
{
    private Item item;

    public DroppedItem(Item item, Vector2 position)
    {
        super(16, position, Shape.SQUARE);
        this.item = item;
        m_color = new Color(0, 0, 1, 1);
    }

    public void runCollision(Entity other)
    {
        if (other instanceof DroppedItem) {
            this.translate(0, 15);
        }
    };
    public void dispose(){

    }
    public Item getItem(){return item;}

    public void render() {
        super.render();
        OrthographicCamera camera = ((Agalon) Gdx.app.getApplicationListener()).getCamera();


        ((Agalon) Gdx.app.getApplicationListener()).getBatch().begin();
        ((Agalon) Gdx.app.getApplicationListener()).getFont().draw(((Agalon) (Gdx.app.getApplicationListener())).getBatch(), item.getName(),
                m_shape.getX() - 10 - camera.position.x, m_shape.getY() - 10 - camera.position.y);
        ((Agalon) Gdx.app.getApplicationListener()).getBatch().end();
    }


}
