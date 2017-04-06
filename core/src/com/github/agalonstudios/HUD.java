package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.actor;

/**
 * Created by Satya Patel on 2/23/2017.
 */

public class HUD {
    public static HUDOutputs hudOutputs;
    private static Touchpad m_movementJoystick;
    private static Stage m_stage;
    private static Button m_pauseButton;

    private HUD() { }
    static {
        hudOutputs = new HUDOutputs();

        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);

        Skin movementJoystickSkin = new Skin();
        movementJoystickSkin.add("movementJoystickBkgd", new Texture("movementJoystickBkgd.png"));
        movementJoystickSkin.add("movementJoystickKnob", new Texture("movementJoystickKnob.png"));

        m_movementJoystick = new Touchpad(0,
                new TouchpadStyle(
                        movementJoystickSkin.getDrawable("movementJoystickBkgd"),
                        movementJoystickSkin.getDrawable("movementJoystickKnob")
                )
        );

        m_movementJoystick.setBounds(0, 0, 100, 100);
        m_stage.addActor(m_movementJoystick);

        // pause button
        Skin pauseSkin = new Skin();
        pauseSkin.add("pauseButton", new Texture("pauseButton.png"));

        Button.ButtonStyle pauseStyle = new Button.ButtonStyle();
        pauseStyle.checked = pauseSkin.getDrawable("pauseButton");
        pauseStyle.up = pauseSkin.getDrawable("pauseButton");
        pauseStyle.down = pauseSkin.getDrawable("pauseButton");

        m_pauseButton = new Button(pauseStyle);

        m_pauseButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y){
                ((Agalon) Gdx.app.getApplicationListener()).setScreen(new InGameOptionsTab(((Agalon) Gdx.app.getApplicationListener())));
            }
        });


        m_pauseButton.setBounds(0, Gdx.graphics.getHeight()-50, 50, 50);
        m_stage.addActor(m_pauseButton);
    }



    public static void update(float delta, Player player) {
        m_stage.act(delta);
        /*
        Array<Actor> arr = m_stage.getActors();
        int size = arr.size;
        for(int i = 0; i < size; i++){
            System.out.println(arr.get(i));
        }
        */
        hudOutputs.accelerationUpdate.x = m_movementJoystick.getKnobPercentX() * player.m_maxAcceleration;
        hudOutputs.accelerationUpdate.y = m_movementJoystick.getKnobPercentY() * player.m_maxAcceleration;
    }

    public static void render() {
        m_stage.draw();
    }

    public static void dispose() {
        m_stage.dispose();
    }

    public static Stage getStage(){
        return m_stage;
    }
}
