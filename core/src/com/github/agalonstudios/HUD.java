package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;

/**
 * Created by Satya Patel on 2/23/2017.
 */

public class HUD {
    public HUDOutputs hudOutputs;
    private Touchpad m_movementJoystick;
    private Stage m_stage;

    public HUD() {
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
    }

    public void update(float delta, Player player) {
        m_stage.act(delta);
        hudOutputs.accelerationUpdate.x = m_movementJoystick.getKnobPercentX() * player.m_maxAcceleration;
        hudOutputs.accelerationUpdate.y = m_movementJoystick.getKnobPercentY() * player.m_maxAcceleration;
    }

    public void render() {
        m_stage.draw();
    }

    public void dispose() {
        m_stage.dispose();
    }
}
