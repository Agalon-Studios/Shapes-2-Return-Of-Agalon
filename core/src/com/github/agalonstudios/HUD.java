package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Satya Patel on 2/23/2017.
 */

public class HUD {
    public static HUDOutputs hudOutputs;
    private static Touchpad m_movementJoystick;
    private static Stage m_stage;
    private static Button m_pauseButton;
    private static Sprite m_HealthBar;
    private static Sprite m_AbilityBar;

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

        m_movementJoystick.setBounds(0, 0, ScreenScale.scale(300), ScreenScale.scale(300));
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


        m_pauseButton.setBounds(0, ScreenScale.scaleHeight(-200) + Gdx.graphics.getHeight(), ScreenScale.scale(200), ScreenScale.scale(200));
        m_stage.addActor(m_pauseButton);


        m_HealthBar = new Sprite(new Texture("StatusBar.png"));
        m_HealthBar.setPosition(150, Gdx.graphics.getHeight() - 50);
        m_AbilityBar = new Sprite(new Texture("AbilityBar.png"));
        m_AbilityBar.setPosition(150, Gdx.graphics.getHeight() - 78);
        m_AbilityBar.setScale(.7f);
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

    public static void render(Player player) {
        SpriteBatch batch = ((Agalon) Gdx.app.getApplicationListener()).getSpriteBatch();
        ExtendedShapeRenderer er = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        batch.begin();

        batch.draw(m_HealthBar, m_HealthBar.getX(), m_HealthBar.getY());

        batch.draw(m_AbilityBar, m_AbilityBar.getX(), m_AbilityBar.getY());

        batch.end();

        er.begin();
        er.set(ShapeType.Filled);

        er.setColor(1, 0, 0, 1);

        float pixelDiff = 9;
        er.rect(
                m_HealthBar.getX() + pixelDiff,
                m_HealthBar.getY() + pixelDiff,
                (m_HealthBar.getWidth() - 2*pixelDiff) * (player.m_health / (float) player.m_maxHealth),
                m_HealthBar.getHeight() - 2*pixelDiff
        );

        pixelDiff *= m_AbilityBar.getScaleX();

        er.setColor(Color.GREEN);

        er.rect(
                m_AbilityBar.getX() + pixelDiff,
                m_AbilityBar.getY() + pixelDiff,
                (m_AbilityBar.getWidth() - 2*pixelDiff) * (player.m_stamina / (float) player.m_maxStamina),
                m_AbilityBar.getHeight() - 2*pixelDiff
        );

        er.end();

        m_stage.draw();
    }

    public static void dispose() {
        m_stage.dispose();
    }

    public static Stage getStage(){
        return m_stage;
    }
}
