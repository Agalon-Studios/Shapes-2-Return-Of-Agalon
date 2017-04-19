package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

        Skin slotSkin = new Skin();
        slotSkin.add("sword", new Texture("swordArt.png"));

        Button.ButtonStyle slotStyle = new Button.ButtonStyle();
        slotStyle.checked = slotSkin.getDrawable("sword");
        slotStyle.up = slotSkin.getDrawable("sword");
        slotStyle.down = slotSkin.getDrawable("sword");

        m_buttonArr = new Array<Array<Button>>(4);
        for(int i = 0; i < 4; i++){
            m_buttonArr.add(new Array<Button>(4));
        }
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                m_buttonArr.get(i).get(j).add(new Button(slotStyle));
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
