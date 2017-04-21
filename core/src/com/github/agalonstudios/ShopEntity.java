package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by mahzain on 4/20/17.
 */

public class ShopEntity extends Entity {
    private Array<Item> shopInventory;
    private int m_itemCount;
    public ShopEntity(Vector2 position)
    {
        super(100, position, Shape.OCTAGON);
        m_color = new Color(236 / 255.f, 207 / 255.f, 116 / 255.f, 1);
        shopInventory = generateShopInventory();

        m_itemCount = shopInventory.size;
    }

    public void runCollision(Entity e)
    {

    }
    public Array<Item> generateShopInventory()
    {
        Array<Item> inventory = new Array<Item>();
        int size = (int)(Math.random()* 15 + 1);
        int itemType;
        for(int i = 0; i < size; i++)
        {
            itemType = (int)(Math.random() * 2);
            if(itemType == 0)
                inventory.add(Item.generateWeapon());
            else
                inventory.add(Item.generateConsumable());

        }

        return inventory;
    }

    public Array<Item> getShopInventory()
    {
        System.out.println(shopInventory.get(0).getName());
        return shopInventory;
    }
    public int getShopSize()
    {
        return m_itemCount;
    }

    public void incrementItemCount() {
        m_itemCount++;
    }

    public void decrementItemCount() {
        m_itemCount--;
    }

    @Override
    public void render() {
        super.render();
        /*SpriteBatch sb = ((Agalon) Gdx.app.getApplicationListener()).getSpriteBatch();
        BitmapFont bf = ((Agalon) Gdx.app.getApplicationListener()).getFont();
        OrthographicCamera oc = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        sb.begin();
        bf.setColor(0, 0, 0, 1);
        bf.draw(sb, "Shop", this.getX() + this.getWidth() / 2 - oc.position.x, this.getY() + () / 2 - oc.position.y);
        sb.end();
        */
    }

}
