package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Peter on 4/17/2017.
 */

public class InventoryScreen implements Screen {
    private Rectangle m_BigRect;
    private Array<Array<Button>> m_buttonArr;
    private Stage m_stage;
    private Button m_backButton;

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
        int row;
        int col;
        if(numItems == 0)
            row = 0;
        else if(numItems<=4 && numItems >=1)
            row = 1;
        else if(numItems <=8 && numItems>=5)
            row = 2;
        else if(numItems<=12 && numItems>=9)
            row = 3;
        else
            row = 4;

        for(int i = 0; i < row; i++) {
            if(row ==1)
                col = numItems;
            else if(row == 2 && i == 1)
                col = numItems-4;
            else if(row == 3 && i == 2)
                col = numItems - 8;
            else if(row == 4 && i == 3)
                col = numItems - 12;
            else
                col = 4;

            for(int j = 0; j < col; j++) {
                if(inventory.get(4*i + j).getType() == Item.m_itemType.WEAPON){
                    m_buttonArr.get(i).add(new Button(swordStyle));
                }
                else{
                    m_buttonArr.get(i).add(new Button(healthStyle));
                }

                Button thisButton = m_buttonArr.get(i).get(j);
                thisButton.setHeight(screenHeight / 5);
                thisButton.setWidth(screenWidth / 4);
                thisButton.setPosition((screenWidth*j/4), (screenHeight*(3-i)/5));

                m_stage.addActor(thisButton);
            }
        }

        // add back button
        Skin backSkin = new Skin();
        backSkin.add("backButton", new Texture("backButton.png"));

        Button.ButtonStyle backStyle = new Button.ButtonStyle();
        backStyle.checked = backSkin.getDrawable("backButton");
        backStyle.up = backSkin.getDrawable("backButton");
        backStyle.down = backSkin.getDrawable("backButton");

        m_backButton = new Button(backStyle);

        m_backButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y){
                a.setScreen(new InGameOptionsTab(a));
            }
        });


        m_backButton.setBounds(0, ScreenScale.scaleHeight(-200) + Gdx.graphics.getHeight(),
                ScreenScale.scale(200), ScreenScale.scale(200));
        m_stage.addActor(m_backButton);
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
