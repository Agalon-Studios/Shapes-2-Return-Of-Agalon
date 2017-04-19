package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Peter on 4/17/2017.
 */

public class InventoryScreen implements Screen {
    private Rectangle m_BigRect;
    private Array<Array<Button>> m_buttonArr;
    private Stage m_stage;

    public InventoryScreen(final Agalon a){
        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Array<Item> inventory = a.getPlayer().getInventory();
        int numItems = a.getPlayer().getNumInventory();

        // stuff for skin
        Skin slotSkin = new Skin();
        slotSkin.add("sword", new Texture("swordArt.png"));
        slotSkin.add("healthPotion", new Texture("potion.png"));

        // for sword
        Button.ButtonStyle swordStyle = new Button.ButtonStyle();
        swordStyle.checked = slotSkin.getDrawable("sword");
        swordStyle.up = slotSkin.getDrawable("sword");
        swordStyle.down = slotSkin.getDrawable("sword");
        // for health potion
        Button.ButtonStyle healthStyle = new Button.ButtonStyle();
        healthStyle.checked = slotSkin.getDrawable("healthPotion");
        healthStyle.up = slotSkin.getDrawable("healthPotion");
        healthStyle.down = slotSkin.getDrawable("healthPotion");


        // create the Array
        m_buttonArr = new Array<Array<Button>>(4);
        for(int i = 0; i < 4; i++){
            m_buttonArr.add(new Array<Button>(4));
        }

        // create the buttons
        int row = numItems/16;
        int col = numItems%4;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {

                if(inventory.get(4*i + j).getType() == Item.m_itemType.WEAPON){
                    m_buttonArr.get(i).get(j).add(new Button(swordStyle));
                }
                else{
                    m_buttonArr.get(i).get(j).add(new Button(healthStyle));
                }

                Button thisButton = m_buttonArr.get(i).get(j);
                thisButton.setHeight(screenHeight / 5);
                thisButton.setWidth(screenWidth / 4);
                thisButton.setPosition(screenHeight*(4-i)/5, screenWidth*j/4);

                m_stage.addActor(thisButton);
            }
        }


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        m_stage.draw();
        m_stage.setDebugAll(true);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
