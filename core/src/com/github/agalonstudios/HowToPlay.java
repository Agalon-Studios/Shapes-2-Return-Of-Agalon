package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Peter on 4/17/2017.
 */

public class HowToPlay implements Screen {
    private Stage m_stage;
    private Button m_backButton;
    private Button m_howToButton;

    public HowToPlay(final Agalon a){
        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);

        // back button
        Skin backSkin = new Skin();

        backSkin.add("backButton", new Texture("backButton.png"));

        Button.ButtonStyle backStyle = new Button.ButtonStyle();
        backStyle.checked = backSkin.getDrawable("backButton");
        backStyle.up = backSkin.getDrawable("backButton");
        backStyle.down = backSkin.getDrawable("backButton");

        m_backButton = new Button(backStyle);

        m_backButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y){
                a.setScreen(new MainMenuOptions(a));
            }
        });


        m_backButton.setBounds(0, ScreenScale.scaleHeight(-200) + Gdx.graphics.getHeight(),
                ScreenScale.scale(200), ScreenScale.scale(200));


        // How to play graphic
        Skin howToSkin = new Skin();
        howToSkin.add("howToPlay", new Texture("howToPlay.png"));

        Button.ButtonStyle howToStyle = new Button.ButtonStyle();
        howToStyle.checked = howToSkin.getDrawable("howToPlay");
        howToStyle.up = howToSkin.getDrawable("howToPlay");
        howToStyle.down = howToSkin.getDrawable("howToPlay");

        m_howToButton = new Button(howToStyle);
        m_howToButton.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        m_stage.addActor(m_howToButton);
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
        //m_stage.setDebugAll(true);
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
