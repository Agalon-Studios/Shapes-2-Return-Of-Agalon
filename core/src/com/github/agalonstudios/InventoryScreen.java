package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by Peter on 4/17/2017.
 */

public class InventoryScreen implements Screen {
    private Rectangle m_BigRect;
    private Array<Array<Button>> m_buttonArr;
    private Stage m_stage;
    private Button m_backButton;
    private Label m_nameLabel;
    private Array<Item> m_inventory;
    private Button m_clicked;
    private TextButton m_equipOrUse;
    private TextButton m_drop;
    private int m_numItems;

    public InventoryScreen(final Agalon a){
        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        m_inventory = a.getPlayer().getInventory();
         m_numItems = a.getPlayer().getNumInventory();

        // stuff for skin
        Skin slotSkin = new Skin();
        slotSkin.add("sword", new Texture("swordArt.png"));
        slotSkin.add("healthPotion", new Texture("potion.png"));

        // for sword
        Button.ButtonStyle swordStyle = new Button.ButtonStyle();
        swordStyle.checked = slotSkin.getDrawable("sword");
        swordStyle.up = slotSkin.getDrawable("sword");
        swordStyle.down = slotSkin.getDrawable("sword");
        // for health potion
        Button.ButtonStyle healthStyle = new Button.ButtonStyle();
        healthStyle.checked = slotSkin.getDrawable("healthPotion");
        healthStyle.up = slotSkin.getDrawable("healthPotion");
        healthStyle.down = slotSkin.getDrawable("healthPotion");


        // create the Array
        m_buttonArr = new Array<Array<Button>>(4);
        for(int i = 0; i < 4; i++){
            m_buttonArr.add(new Array<Button>(4));
        }
        // create label style
        BitmapFont bfont = new BitmapFont();
        final Label.LabelStyle labelStyle = new Label.LabelStyle(bfont, Color.WHITE);
        m_nameLabel = new Label("Item Name", labelStyle);
        m_nameLabel.setPosition((screenWidth/4), (screenHeight*9/10));
        m_stage.addActor(m_nameLabel);


        // create the buttons
        int row;
        int col;
        // stuff for what rows
        if(m_numItems == 0)
            row = 0;
        else if(m_numItems<=4 && m_numItems >=1)
            row = 1;
        else if(m_numItems <=8 && m_numItems>=5)
            row = 2;
        else if(m_numItems<=12 && m_numItems>=9)
            row = 3;
        else
            row = 4;

        for(int i = 0; i < row; i++) {
            // stuff for how many cols
            if(row ==1)
                col = m_numItems;
            else if(row == 2 && i == 1)
                col = m_numItems-4;
            else if(row == 3 && i == 2)
                col = m_numItems - 8;
            else if(row == 4 && i == 3)
                col = m_numItems - 12;
            else
                col = 4;

            for(int j = 0; j < col; j++) {
                // create the button
                if(m_inventory.get(4*i+j).getType() == Item.m_itemType.WEAPON){
                    m_buttonArr.get(i).add(new Button(swordStyle));
                }
                else{
                    m_buttonArr.get(i).add(new Button(healthStyle));
                }
                // set the button's size, pos, and info
                final Button thisButton = m_buttonArr.get(i).get(j);
                //thisButton.setName(Item.getInfo(m_inventory.get(4*i+j)));
                int pos = 4*i+j;
                thisButton.setName(Integer.toString(pos));
                thisButton.setHeight(screenHeight / 5);
                thisButton.setWidth(screenWidth / 4);
                thisButton.setPosition((screenWidth*j/4), (screenHeight*(3-i)/5));
                m_stage.addActor(thisButton);

                thisButton.addListener(new ClickListener() {
                    public void clicked (InputEvent event, float x, float y){
                        // changes the info displayed
                        m_clicked = thisButton;
                        m_nameLabel.setText(m_inventory.get(Integer.parseInt(m_clicked.getName())).getInfo(m_inventory.get(Integer.parseInt(m_clicked.getName()))));
                        //System.out.println(m_clicked.getName());
                        //System.out.println(m_nameLabel.toString());
                    }
                });
            }
        }

        // add back button
        Skin backSkin = new Skin();
        backSkin.add("backButton", new Texture("backButton.png"));

        Button.ButtonStyle backStyle = new Button.ButtonStyle();
        backStyle.checked = backSkin.getDrawable("backButton");
        backStyle.up = backSkin.getDrawable("backButton");
        backStyle.down = backSkin.getDrawable("backButton");

        m_backButton = new Button(backStyle);

        m_backButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y){
                a.setScreen(new InGameOptionsTab(a));
            }
        });


        m_backButton.setBounds(0, ScreenScale.scaleHeight(-200) + Gdx.graphics.getHeight(),
                ScreenScale.scale(200), ScreenScale.scale(200));
        m_stage.addActor(m_backButton);

        // create Equip button
        Pixmap pixmap = new Pixmap(screenWidth/5, screenWidth/5, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        Skin skin = new Skin();

        skin.add("white", new Texture(pixmap));
        skin.add("default", bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        TextButton m_equipOrUse = new TextButton("Equip", textButtonStyle);
        m_equipOrUse.setHeight(screenHeight / 5);
        m_equipOrUse.setWidth(screenWidth / 4);
        m_equipOrUse.setPosition((screenWidth*2/4), (screenHeight*4/5));
        m_stage.addActor(m_equipOrUse);

        m_equipOrUse.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

            }
        });

        m_drop = new TextButton("Drop", textButtonStyle);
        m_drop.setHeight(screenHeight / 5);
        m_drop.setWidth(screenWidth / 4);
        m_drop.setPosition((screenWidth*3/4), (screenHeight*4/5));
        m_stage.addActor(m_drop);

        m_drop.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                removeItem(Integer.parseInt(m_clicked.getName()), a);
                a.setScreen(new InventoryScreen(a));
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
    public Array<Item> getInventory(){return m_inventory;}

    public void removeItem(int pos, final Agalon a){

        for(int i = pos; i < m_numItems-1; i++){
            m_inventory.set(i, m_inventory.get(i+1));
        }
        m_inventory.removeIndex(m_numItems-1);
        m_numItems--;
        a.getPlayer().setNumInventory(m_numItems);

    }
}
