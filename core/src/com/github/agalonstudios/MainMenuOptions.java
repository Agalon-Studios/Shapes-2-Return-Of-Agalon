package com.github.agalonstudios;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by BDog on 3/27/2017.
 */

public class MainMenuOptions implements Screen {

    private Stage m_stage;
    private Skin skin;
    private TextButton m_soundButton;
    private TextButton m_backButton;
    private TextButton m_deleteButton;
    private TextButton m_howToButton;

    public MainMenuOptions(final Agalon a){
        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        skin = new Skin();

        // creating the button style
        Pixmap pixmap = new Pixmap(screenWidth/4, screenWidth/4, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        // add sound toggle button
        if(a.getMusic().isPlaying())
            m_soundButton = new TextButton("Sound OFF", textButtonStyle);
        else
            m_soundButton = new TextButton("Sound ON", textButtonStyle);

        m_soundButton.setPosition(0, screenHeight/2-screenWidth/10);
        m_stage.addActor(m_soundButton);

        m_soundButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                if(a.getMusic().isPlaying()) {
                    m_soundButton.setText("Sound OFF");
                    a.turnOffMusic();
                }
                else {
                    m_soundButton.setText("Sound ON");
                    a.turnOnMusic();
                }
            }
        });


        // add back button
        m_backButton = new TextButton("Back", textButtonStyle);
        m_backButton.setPosition(screenWidth*3/4, screenHeight/2-screenWidth/10);
        m_stage.addActor(m_backButton);

        m_backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                a.setScreen(new MainMenu(a));
            }
        });

        // add delete save data button
        m_deleteButton = new TextButton("Delete Save Data", textButtonStyle);
        m_deleteButton.setPosition(screenWidth/2, screenHeight/2-screenWidth/10);
        m_stage.addActor(m_deleteButton);

        m_deleteButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y){

            }
        });

        // add how to play button
        m_howToButton = new TextButton("How to Play", textButtonStyle);
        m_howToButton.setPosition(screenWidth/4, screenHeight/2-screenWidth/10);
        m_stage.addActor(m_howToButton);
        m_howToButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                a.setScreen(new HowToPlay(a));
            }
        });
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
