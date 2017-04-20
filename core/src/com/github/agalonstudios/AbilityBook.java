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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import static javax.swing.UIManager.get;

/**
 * Created by Jacob on 4/18/2017.
 */

class AbilityBook implements Screen {

    private TextButton m_backButton;
    private Array<TextButton> m_abilityButtons;
    private static final String[] abilityNames = {
            "Strike", "Cleave", "Snipe", "Flame Burst",
            "Ice Arrow", "a"
    };

    private Stage m_stage;
    private Label m_equippedLabel;
    private Label m_bookLabel;
    private Array<Ability> equippedAbilities;
    private Array<Image> m_targetBoxes;

    public AbilityBook(final Agalon a){
        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);
        BitmapFont bfont = new BitmapFont();

        equippedAbilities = new Array<Ability>(4);


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

        // adding the back button
        m_backButton  = new TextButton("DONE",textButtonStyle);
        m_backButton.setPosition(250, 100);
        m_stage.addActor(m_backButton);

        m_backButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                a.backToWorld();
                a.getPlayer().setEquippedAbilities(equippedAbilities);
                HUD.setAbilityButtons(a.getPlayer());
            }
        });

        // add labels
        Label.LabelStyle style = new Label.LabelStyle(bfont, Color.WHITE);
        m_bookLabel = new Label("Available Abilities", style);
        m_bookLabel.setPosition(750,700);
        m_stage.addActor(m_bookLabel);

        m_equippedLabel = new Label("Equipped Abilities", style);
        m_equippedLabel.setPosition(250,700);
        m_stage.addActor(m_equippedLabel);

        // Create buttons for each ability
        // add starting position for each ability
        final float[] startingPos = new float[16];
        boolean alt = false;
        float pos = 600;
        for(int i = 0;i<16;i+=2){
            startingPos[i+1] = pos;
            if(!alt) {
                startingPos[i] = 700;
                alt = true;
            }
            else {
                startingPos[i] = 800;
                pos -= 100;
                alt = false;
            }
        }
        // creating the button style
        Pixmap abilityPixmap = new Pixmap(50, 50, Pixmap.Format.RGBA8888);
        abilityPixmap.setColor(Color.BLUE);
        abilityPixmap.fill();

        skin.add("white", new Texture(abilityPixmap));

        skin.add("default", bfont);

        final TextButton.TextButtonStyle abilityButtonStyle = new TextButton.TextButtonStyle();
        abilityButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        abilityButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        abilityButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        abilityButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        abilityButtonStyle.font = skin.getFont("default");
        skin.add("default", abilityButtonStyle);

        // adding the ability button
        int numAbilities = abilityNames.length;
        m_abilityButtons = new Array<TextButton>(numAbilities);

        for (int i = 0; i < numAbilities; i++) {
            TextButton abilityButton = new TextButton(abilityNames[i], abilityButtonStyle);
            abilityButton.setPosition(startingPos[i * 2], startingPos[i * 2 + 1]);
            m_stage.addActor(abilityButton);
            m_abilityButtons.add(abilityButton);
        }


        // Add already equipped abilites
        equippedAbilities = a.getPlayer().getEquippedAbilities();


        // make targetbox skin
        Skin emptySkin = new Skin();
        emptySkin.add("targetBox", new Texture("targetBox.png"));
        m_targetBoxes = new Array<Image>(numAbilities+4);

        // add targets for equipped abilities
        for(int i = 0; i < 4; i++) {
            final Image targetBox = new Image(emptySkin, "targetBox");
            targetBox.setPosition(250, 600-(i*100));
            m_stage.addActor(targetBox);
            m_targetBoxes.add(targetBox);
            m_targetBoxes.get(i).toBack();
        }

        // adding targets for available abilities
        for(int i = 0; i < numAbilities; i++){
            final Image targetBox = new Image(emptySkin, "targetBox");
            targetBox.setPosition(startingPos[i * 2], startingPos[i * 2 + 1]);
            m_stage.addActor(targetBox);
            m_targetBoxes.add(targetBox);
            m_targetBoxes.get(i+4).toBack();
        }

        // Make Button Drag/Drop-able
        DragAndDrop dragAndDrop = new DragAndDrop();

        for (int i = 0; i < m_abilityButtons.size; i++) {
            final int j = i;
            dragAndDrop.addSource(new Source(m_abilityButtons.get(i)){
                public Payload dragStart (InputEvent event, float x, float y, int pointer) {
                    Payload payload = new Payload();
                    // create copy of actor
                    TextButton copy_abilityButton1  = new TextButton(abilityNames[j] ,abilityButtonStyle);
                    copy_abilityButton1.setName(abilityNames[j]);
                    payload.setDragActor(copy_abilityButton1);
                    return payload;
                }
            });
        }

        // indicate targets for equipped abilities
        for (int i = 0; i < 4; i++) {
            final int j = i;
            dragAndDrop.addTarget(new Target(m_targetBoxes.get(i)){
                public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                    return true;
                }

                public void reset (Source source, Payload payload) {
                    // called when payload is no longer over the target (by drop or move)
                }

                public void drop (Source source, Payload payload, float x, float y, int pointer) {
                    // called when payload is dropped on target
                    // move ability to box
                        source.getActor().setPosition(m_targetBoxes.get(j).getX(),m_targetBoxes.get(j).getY());
                        m_stage.addActor(source.getActor());
                        source.getActor().toFront();

                    // add ability to equipped ability list
                        for (int k = 0; k < abilityNames.length; k++) {
                            if (payload.getDragActor() != null &&
                                    payload.getDragActor().getName().equals(abilityNames[k]))
                                equippedAbilities.set(j, new Ability(Ability.AbilityType.values()[k]));
                        }
                }
            });
        }
        // indicate targets for available abilities
        for (int i = 4; i < m_abilityButtons.size+4; i++) {
            final int j = i;
            dragAndDrop.addTarget(new Target(m_targetBoxes.get(i)){
                public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                    return true;
                }

                public void reset (Source source, Payload payload) {
                    // called when payload is no longer over the target (by drop or move)
                }

                public void drop (Source source, Payload payload, float x, float y, int pointer) {
                    // called when payload is dropped on target
                    // move ability to box
                        source.getActor().setPosition(m_targetBoxes.get(j).getX(),m_targetBoxes.get(j).getY());
                        m_stage.addActor(source.getActor());
                        source.getActor().toFront();

                       //remove ability based on where source came from, only if source is in equipped pos
                }
            });
        }
    }
    // TODO: add info when ability button is pressed and remove abilities when dragged to right side
    @Override
    public void show(){

    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        m_stage.draw();
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
    public void dispose(){

    }
}
