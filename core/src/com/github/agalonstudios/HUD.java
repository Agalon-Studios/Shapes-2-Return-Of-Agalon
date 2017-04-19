package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.scenes.scene2d.Actor;
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
    private static Button m_bookButton;
    private static final int[] abilityButtonXPos = {300, 600, 900, 1200, 1500};
    private static final int[] abilityButtonYPos = {0, 0, 0, 0, 0};


    private static Array<Actor> m_AbilityButtons;
    private static Array<Ability> m_Abilities;
    private HUD() { }
    static {
        hudOutputs = new HUDOutputs();

        m_AbilityButtons = new Array<Actor>(6);
        m_Abilities = new Array<Ability>(6);

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

        //Ability book button
        Skin bookSkin = new Skin();
        bookSkin.add("bookButton", new Texture("bookButton.png"));

        Button.ButtonStyle bookStyle = new Button.ButtonStyle();
        bookStyle.checked = bookSkin.getDrawable("bookButton");
        bookStyle.up = bookSkin.getDrawable("bookButton");
        bookStyle.down = bookSkin.getDrawable("bookButton");

        m_bookButton = new Button(bookStyle);

        m_bookButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y){
                //((Agalon) Gdx.app.getApplicationListener()).setScreen(new AbilityBook(((Agalon) Gdx.app.getApplicationListener())));
            }
        });


        m_bookButton.setBounds(0, ScreenScale.scaleHeight(-400) + Gdx.graphics.getHeight(), ScreenScale.scale(200), ScreenScale.scale(200));
        m_stage.addActor(m_bookButton);
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

        drawStatusBars(player, er);

        m_stage.draw();
    }

    public static void setAbilityButtons(Player p) {
        m_stage.getActors().removeAll(m_AbilityButtons, false);


        for (int i = 0; i < p.m_equippedAbilities.size; i++) {
            m_Abilities.set(i, p.m_equippedAbilities.get(i));
            switch(m_Abilities.get(i).getAbilityType()) {
                case SELF:
                case SELF_AREA_OF_EFFECT:
                    Button abilityButton = makeAbilityButton(i);
                    m_stage.addActor(abilityButton);
                    m_AbilityButtons.set(i, abilityButton);
                    break;

                default:
                    Touchpad abilityJoystick = makeAbilityJoyStick(i);
                    m_stage.addActor(abilityJoystick);
                    m_AbilityButtons.set(i, abilityJoystick);
                    break;
            }
        }

    }

    private static Button makeAbilityButton(int location) {
        Skin skin = new Skin();
        skin.add("button", new Texture("pauseButton.png"));

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.checked = skin.getDrawable("pauseButton");
        style.up = skin.getDrawable("pauseButton");
        style.down = skin.getDrawable("pauseButton");

        Button ret = new Button(style);

        ret.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y){
                //TODO;
            }
        });


        ret.setBounds(
                ScreenScale.scaleWidth(abilityButtonXPos[location]),
                ScreenScale.scaleHeight(abilityButtonYPos[location]),
                ScreenScale.scale(150),
                ScreenScale.scale(150));

        return ret;
    }

    private static Touchpad makeAbilityJoyStick(int location) {
        Skin abilityButtonSkin = new Skin();
        abilityButtonSkin.add("bkgd", new Texture("movementJoystickBkgd.png"));
        abilityButtonSkin.add("knob", new Texture("movementJoystickKnob.png"));
        Touchpad temp = new Touchpad(0,
                new TouchpadStyle(
                        abilityButtonSkin.getDrawable("movementJoystickBkgd"),
                        abilityButtonSkin.getDrawable("movementJoystickKnob")
                )
        );
        temp.setBounds(
                abilityButtonXPos[location],
                abilityButtonYPos[location],
                ScreenScale.scale(150),
                ScreenScale.scale(150)
        );

        return temp;
    }


    private static void drawStatusBars(Player player, ExtendedShapeRenderer er) {
        er.begin();
        er.set(ShapeType.Filled);

        er.setColor(1, 0, 0, 1);

        float pixelDiff = 9;
        er.rect(
                ScreenScale.scale((int) (m_HealthBar.getX() + pixelDiff)),
                ScreenScale.scale((int) (m_HealthBar.getY() + pixelDiff)),
                ScreenScale.scale((int) ((m_HealthBar.getWidth() - 2*pixelDiff) * (player.m_health / (float) player.m_maxHealth))),
                ScreenScale.scale((int) (m_HealthBar.getHeight() - 2*pixelDiff))
        );

        pixelDiff *= m_AbilityBar.getScaleX();

        er.setColor(Color.GREEN);

        er.rect(
                ScreenScale.scale((int) (m_AbilityBar.getX() + pixelDiff)),
                ScreenScale.scale((int) (m_AbilityBar.getY() + pixelDiff)),
                ScreenScale.scale((int) ((m_AbilityBar.getWidth() - 2*pixelDiff) * (player.m_stamina / (float) player.m_maxStamina))),
                ScreenScale.scale((int) (m_AbilityBar.getHeight() - 2*pixelDiff))
        );

        er.end();

    }

    public static void dispose() {
        m_stage.dispose();
    }

    public static Stage getStage(){
        return m_stage;
    }
}
