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
    private boolean m_isGold;
    private int m_goldAmount;

    public DroppedItem(Item item, Vector2 position)
    {
        super(16, position, Shape.SQUARE);
        this.item = item;
        switch (item.getType()) {
            case WEAPON:
                m_color = new Color(189 / 255.f, 189 / 255.f, 189 / 255.f, 1);
                break;
            case CONSUMABLE:
                switch (item.getConsumableType()) {
                    case HEALTH:
                        m_color = new Color(189 / 255.f, 189 / 255.f, 189 / 255.f, 1);
                        break;
                    case DAMAGE:
                        m_color = new Color(1, 127 / 255.f, 39 / 255.f, 1);
                        break;
                    case ENERGY:
                        m_color = new Color(34 / 255.f, 177 / 255.f, 76 / 255.f, 1);
                        break;
                    case SPEED:
                        m_color = new Color(63 / 255.f, 72 / 255.f, 204 / 255.f, 1);
                        break;
                    case KNOCK:
                        m_color = new Color(163 / 255.f, 73 / 255.f, 164 / 255.f, 1);
                        break;
                }
                break;
        }
    }

    public DroppedItem(int amount, Vector2 position) {
        super(8, position, Shape.OCTAGON);
        m_isGold = true;
        m_goldAmount = amount;
        m_color = new Color(236 / 255.f, 236 / 255.f, 17 / 255.f, 1);
    }

    public boolean isGold() {
        return m_isGold;
    }

    public int getAmount() {
        return m_goldAmount;
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
        ((Agalon) Gdx.app.getApplicationListener()).getFont().draw(((Agalon) (Gdx.app.getApplicationListener())).getBatch(),
                m_isGold ? "" + m_goldAmount + " Gold" : item.getName(),
                m_shape.getX() - 10 - camera.position.x, m_shape.getY() - 10 - camera.position.y);
        ((Agalon) Gdx.app.getApplicationListener()).getBatch().end();
    }


}
