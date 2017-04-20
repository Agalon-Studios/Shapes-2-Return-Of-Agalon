package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.math.Interpolation.circle;
import static sun.audio.AudioPlayer.player;


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


    private static boolean[] abilityWasJustPressed = new boolean[4];

    private static final int abilityButtonRadius = ScreenScale.scale(150);



    private static final int abilityButtonOriginX = Gdx.graphics.getWidth() - abilityButtonRadius;
    private static final int abilityButtonOriginY = 0;

    private static final int[] abilityButtonXPos = {
            abilityButtonOriginX,
            abilityButtonOriginX - abilityButtonRadius * 2,
            (int) (abilityButtonOriginX - MathUtils.cosDeg(45) * (abilityButtonRadius * 2)),
            abilityButtonOriginX
    };

    private static final int[] abilityButtonYPos = {
            abilityButtonOriginY,
            abilityButtonOriginY,
            (int) (abilityButtonOriginY + MathUtils.sinDeg(45) * (abilityButtonRadius * 2)),
            abilityButtonOriginY + (abilityButtonRadius * 2)
    };


    private static Array<Actor> m_AbilityButtons;
    private static Array<Ability> m_Abilities;
    private HUD() { }
    static {
        hudOutputs = new HUDOutputs();

        m_AbilityButtons = new Array<Actor>(6);
        m_Abilities = new Array<Ability>(6);

        for (int i = 0; i < 4; i++) {
            m_AbilityButtons.add(null);
            m_Abilities.add(null);
        }

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
                ((Agalon) Gdx.app.getApplicationListener()).setScreen(new AbilityBook(((Agalon) Gdx.app.getApplicationListener())));
            }
        });


        m_bookButton.setBounds(0, ScreenScale.scaleHeight(-400) + Gdx.graphics.getHeight(), ScreenScale.scale(200), ScreenScale.scale(200));
        m_stage.addActor(m_bookButton);
    }



    public static void update(float delta, Player player) {
        if (m_Abilities.get(0) == null)
            setAbilityButtons(player);

        m_stage.act(delta);

        hudOutputs.reset();

        for (int i = 0; i < m_AbilityButtons.size; i++) {
            if (m_AbilityButtons.get(i) instanceof Touchpad) {
                Touchpad ref = (Touchpad) m_AbilityButtons.get(i);
                if (abilityWasJustPressed[i] && !ref.isTouched())
                    hudOutputs.abilityIsUsed[i] = true;


                abilityWasJustPressed[i] = ref.isTouched();

                if (hudOutputs.abilityIsUsed[i]) continue;

                hudOutputs.abilityCastVectors[i].set(ref.getKnobPercentX(), ref.getKnobPercentY());

            }
            else if (m_AbilityButtons.get(i) instanceof Button) {
                Button ref = (Button) m_AbilityButtons.get(i);

                if (abilityWasJustPressed[i] && !ref.isPressed())
                    hudOutputs.abilityIsUsed[i] = true;
                if (ref.isPressed()) {
                    abilityWasJustPressed[i] = true;
                }

            }
        }



        hudOutputs.accelerationUpdate.x = m_movementJoystick.getKnobPercentX() * player.m_maxAcceleration;
        hudOutputs.accelerationUpdate.y = m_movementJoystick.getKnobPercentY() * player.m_maxAcceleration;
    }


    public static void render(Player player) {
        renderAbilityButtonOutputs(player);
        drawStatusBars(player);
        m_stage.draw();
    }

    public static void renderAbilityButtonOutputs(Player player) {
        ExtendedShapeRenderer er = ((Agalon) Gdx.app.getApplicationListener()).getShapeRenderer();
        OrthographicCamera c = ((Agalon) Gdx.app.getApplicationListener()).getCamera();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        er.begin();
        er.setColor(1, 0, 0, .2f);
        er.set(ShapeType.Filled);
        for (int i = 0; i < m_AbilityButtons.size; i++) {
            Ability currentAbility = player.getEquippedAbilities().get(i);
            float abilityCenterX = player.getCentroidX() + hudOutputs.abilityCastVectors[i].x * currentAbility.getRange() - c.position.x;
            float abilityCenterY = player.getCentroidY() + hudOutputs.abilityCastVectors[i].y * currentAbility.getRange() - c.position.y;
            switch(currentAbility.getType()) {
                case DROP_AREA_OF_EFFECT:
                    if (((Touchpad)m_AbilityButtons.get(i)).isTouched()) {
                        renderDropAOE(abilityCenterX, abilityCenterY, er, player, c, currentAbility);
                    }
                    break;
//                case PROJECTILE_AREA_OF_EFFECT:
//                    if (((Touchpad)m_AbilityButtons.get(i)).isTouched()) {
//                        renderProjAOE(abilityCenterX, abilityCenterY, er, player, currentAbility);
//                    }
//                    break;
            }
        }
        er.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private static void renderProjAOE(float circleX, float circleY, ExtendedShapeRenderer er, Player player, Ability currentAbility) {
        er.circle(circleX, circleY, currentAbility.getAreaofEffect());
        er.setColor(.5f, 0, 0, 1);
        er.set(ShapeType.Line);
        er.circle(circleX, circleY, currentAbility.getAreaofEffect());
        er.circle(circleX, circleY, currentAbility.getAreaofEffect()-1);

        float fraction = (float)Math.sqrt(Math.pow(player.getCentroidX() - circleX, 2) + Math.pow(player.getCentroidY() - circleY, 2));
        float cx = currentAbility.getAreaofEffect() * (player.getCentroidX()-circleX);
        cx /= fraction;
        cx += circleX;
        float cy = currentAbility.getAreaofEffect() * (player.getCentroidY() - circleY);
        cy /= fraction;
        cy += circleY;
        er.line(
                cx,
                cy,
                player.getCentroidX(),
                player.getCentroidY()
        );
    }

    private static void renderDropAOE(float circleX, float circleY, ExtendedShapeRenderer er, Player player, Camera c,Ability currentAbility) {
        er.circle(circleX, circleY, currentAbility.getAreaofEffect());
        er.setColor(.5f, 0, 0, 1);
        er.set(ShapeType.Line);
        er.circle(circleX, circleY, currentAbility.getAreaofEffect());
        er.circle(circleX, circleY, currentAbility.getAreaofEffect()-1);

        float ax = player.getCentroidX() - c.position.x;
        float ay = player.getCentroidY() - c.position.y;

        float fraction = (float)Math.sqrt(Math.pow(ax - circleX, 2) + Math.pow(ay - circleY, 2));
        float cx = currentAbility.getAreaofEffect() * (ax - circleX);
        cx /= fraction;
        cx += circleX;
        float cy = currentAbility.getAreaofEffect() * (ay - circleY);
        cy /= fraction;
        cy += circleY;
        er.line(
                cx,
                cy,
                ax,
                ay
        );
    }


    public static void setAbilityButtons(Player p) {
        if (m_AbilityButtons.get(0) != null)
            m_stage.getActors().removeAll(m_AbilityButtons, false);

        for (int i = 0; i < p.m_equippedAbilities.size; i++) {
            m_Abilities.set(i, p.m_equippedAbilities.get(i));
            switch(m_Abilities.get(i).getType()) {
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
        style.checked = skin.getDrawable("button");
        style.up = skin.getDrawable("button");
        style.down = skin.getDrawable("button");

        Button ret = new Button(style);

        ret.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y){
                //TODO;
            }
        });


        ret.setBounds(
                abilityButtonXPos[location],
                abilityButtonYPos[location],
                abilityButtonRadius,
                abilityButtonRadius
        );

        return ret;
    }

    private static Touchpad makeAbilityJoyStick(int location) {
        Skin abilityButtonSkin = new Skin();
        abilityButtonSkin.add("bkgd", new Texture("movementJoystickBkgd.png"));
        abilityButtonSkin.add("knob", new Texture("movementJoystickKnob.png"));
        Touchpad temp = new Touchpad(0,
                new TouchpadStyle(
                        abilityButtonSkin.getDrawable("bkgd"),
                        abilityButtonSkin.getDrawable("knob")
                )
        );
        temp.setBounds(
                abilityButtonXPos[location],
                abilityButtonYPos[location],
                abilityButtonRadius,
                abilityButtonRadius
        );

        return temp;
    }


    private static void drawStatusBars(Player player) {
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

    }

    public static void dispose() {
        m_stage.dispose();
    }

    public static Stage getStage(){
        return m_stage;
    }
}
