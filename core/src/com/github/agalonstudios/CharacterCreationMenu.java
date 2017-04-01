package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by Jacob on 3/31/2017.
 */

public class CharacterCreationMenu implements Screen {

    private Stage m_stage;
    private TextButton m_doneButton;
    private Slider m_colorSlider;

    public CharacterCreationMenu(final Agalon a){

        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);

        Skin skin = new Skin();
        Skin slideSkin = new Skin();

        // creating the button style
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        // Creating Slider style (setting x to 500 and y to 50 makes the
        // slider look nicer, but i'm not able to drag it, the button skin works fine)
        Pixmap pixmapSlider = new Pixmap(500, 50, Pixmap.Format.RGBA8888);
        pixmapSlider.setColor(Color.GREEN);
        pixmapSlider.fill();

        skin.add("white", new Texture(pixmap));
        slideSkin.add("white", new Texture(pixmapSlider));

        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        Slider.SliderStyle slideStyle = new Slider.SliderStyle();
        slideStyle.background = slideSkin.newDrawable("white", Color.WHITE);
        slideStyle.background.setMinWidth(100);
        slideStyle.knob = slideSkin.newDrawable("white", Color.DARK_GRAY);
        slideStyle.knobDown = slideSkin.newDrawable("white", Color.PINK);
        slideStyle.knobOver = slideSkin.newDrawable("white", Color.LIGHT_GRAY);

        slideSkin.add("default", slideStyle);
        // adding the done button
        m_doneButton  = new TextButton("DONE",textButtonStyle);
        m_doneButton.setPosition(250, 250);
        m_stage.addActor(m_doneButton);

        m_doneButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                //System.out.println("Clicked! Is checked: " + button.isChecked());
                m_doneButton.setText("Wow, so shapely");
                a.setScreen(new Overworld(new Player(100, 200, "player-image-path")));
            }
        });

        // adding the color slider
        m_colorSlider = new Slider(0, 100, 10, false, slideStyle);
        m_colorSlider.setPosition(100,50);
        m_stage.addActor(m_colorSlider);

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

        float colorVal;
        colorVal = m_colorSlider.getPercent();


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
