package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.utils.Align.center;
import static com.badlogic.gdx.utils.Align.right;


/**
 * Created by BDog on 3/27/2017.
 */

public class MainMenu implements Screen {

    private Stage m_stage;
    private TextButton m_playButton;
    private Label m_title;
    private Button m_main;


    public MainMenu(final Agalon a){

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        m_stage = new Stage();
        //m_viewport = new ExtendViewport(100, 100, a.getCamera());
        //m_viewport.apply();
        //a.getCamera().position.set(a.getCamera().viewportWidth/2,a.getCamera().viewportHeight/2,0);
        Gdx.input.setInputProcessor(m_stage);

        Skin skin = new Skin();

        // creating the button style
        Pixmap pixmap = new Pixmap(screenWidth/5, screenWidth/5, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.ORANGE);
        textButtonStyle.down = skin.newDrawable("white", Color.WHITE);
        textButtonStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        // adding the play button
        m_playButton  = new TextButton("PLAY",textButtonStyle);
        m_playButton.setPosition(screenWidth/5, screenHeight/2-screenWidth/10);





        m_playButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                a.setScreen(new CharacterCreationMenu(a));
            }
        });

        // adding the options button

        TextButton m_optionsButton = new TextButton("Options", textButtonStyle);
        m_optionsButton.setPosition(screenWidth*3/5, screenHeight/2-screenWidth/10);


        m_optionsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                a.setScreen(new MainMenuOptions(a));
            }
        });

        Skin howToSkin = new Skin();
        howToSkin.add("main", new Texture("mainShapes.png"));

        Button.ButtonStyle main = new Button.ButtonStyle();
        main.checked = howToSkin.getDrawable("main");
        main.up = howToSkin.getDrawable("main");
        main.down = howToSkin.getDrawable("main");

        m_main = new Button(main);
        m_main.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        m_stage.addActor(m_main);
        m_stage.addActor(m_optionsButton);
        m_stage.addActor(m_playButton);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135/255f,206/255f, 235/255f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        m_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //m_viewport.update(width,height);
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
