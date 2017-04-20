package com.github.agalonstudios;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by mahzain on 4/19/17.
 */

public class DroppedItem extends Entity
{
    private Item item;

    public DroppedItem(Item item, Vector2 position)
    {
        super(10, position, Shape.SQUARE);
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


}
