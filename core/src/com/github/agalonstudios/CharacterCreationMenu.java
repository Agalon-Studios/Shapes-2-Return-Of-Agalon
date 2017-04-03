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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Jacob on 3/31/2017.
 */

public class CharacterCreationMenu implements Screen {

    private Stage m_stage;
    private TextButton m_doneButton;
    private Slider m_colorSlider;
    private Label m_label;

    public CharacterCreationMenu(final Agalon a){

        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);
        BitmapFont bfont = new BitmapFont();

        Skin skin = new Skin();

        // creating the button style
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        skin.add("default", bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

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

        // add label for slider
        Label.LabelStyle style = new Label.LabelStyle(bfont, Color.WHITE);
        m_label = new Label("Choose Character Color", style);
        m_label.setPosition(250,150);
        m_stage.addActor(m_label);

        // creating the slider style
        Pixmap sliderPixmap = new Pixmap(50, 50, Pixmap.Format.RGBA8888);
        sliderPixmap.setColor(Color.WHITE);
        sliderPixmap.fill();

        Skin sliderSkin = new Skin();
        sliderSkin.add("white", new Texture(sliderPixmap));

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.knob = skin.newDrawable("white", Color.BLUE);
        sliderStyle.knobOver = skin.newDrawable("white", Color.BROWN);
        sliderStyle.knobDown = skin.newDrawable("white", Color.DARK_GRAY);
        sliderStyle.background = skin.newDrawable("white", Color.WHITE);

        sliderSkin.add("default", sliderStyle);
        float[] snapvals = new float[]{0,.1f,.2f,.3f,.4f,.5f,.6f,.7f,.8f,.9f,1};

        // adding the slider
        m_colorSlider  = new Slider(0,1,.1f,false, sliderStyle);
        m_colorSlider.setWidth(500);
        // Would like the height to be smaller but can't seem to get the skin
        // to be smaller with it.
        m_colorSlider.setHeight(100);
        m_colorSlider.setPosition(50, 50);
        m_colorSlider.setSnapToValues(snapvals,.04f);
        m_stage.addActor(m_colorSlider);

        // add preview for character color

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
        colorVal = m_colorSlider.getValue();
        // use enum for character colors?

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
