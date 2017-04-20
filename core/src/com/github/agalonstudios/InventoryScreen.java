package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
    private static int m_numDroppedItems = 0;
    private Label m_goldLabel;


    public InventoryScreen(final Agalon a) {
        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        m_inventory = a.getPlayer().getInventory();


         m_numItems = a.getPlayer().getNumInventory();

        for (int i = 0; i < m_inventory.size; i++) {
            System.out.println(m_inventory.get(i).getName());
        }

        //System.out.println(m_numItems);
        //System.out.println("was in inventory");



        int numShifted = 0;
        for (int i = 0; i < m_inventory.size; i++) {
            if (m_inventory.get(i) == null) {
                m_inventory.set(i, m_inventory.get(i+numShifted++));
            }
            if (numShifted == m_numItems) break;
        }
        // stuff for skin
        Skin slotSkin = new Skin();
        slotSkin.add("sword", new Texture("swordArt.png"));
        slotSkin.add("axe", new Texture("axe.png"));
        slotSkin.add("bow", new Texture("bow.png"));
        slotSkin.add("wand", new Texture("wand.png"));
        slotSkin.add("healthPotion", new Texture("potion.png"));
        slotSkin.add("energyPotion", new Texture("energyPotion.png"));
        slotSkin.add("damagePotion", new Texture("damagePotion.png"));
        slotSkin.add("speedPotion", new Texture("speedPotion.png"));
        slotSkin.add("knockPotion", new Texture("knockPotion.png"));


        // for sword
        Button.ButtonStyle swordStyle = new Button.ButtonStyle();
        swordStyle.checked = slotSkin.getDrawable("sword");
        swordStyle.up = slotSkin.getDrawable("sword");
        swordStyle.down = slotSkin.getDrawable("sword");
        // for bow
        Button.ButtonStyle bowStyle = new Button.ButtonStyle();
        bowStyle.checked = slotSkin.getDrawable("bow");
        bowStyle.up = slotSkin.getDrawable("bow");
        bowStyle.down = slotSkin.getDrawable("bow");
        // for axe
        Button.ButtonStyle axeStyle = new Button.ButtonStyle();
        axeStyle.checked = slotSkin.getDrawable("axe");
        axeStyle.up = slotSkin.getDrawable("axe");
        axeStyle.down = slotSkin.getDrawable("axe");
        // for wand
        Button.ButtonStyle wandStyle = new Button.ButtonStyle();
        wandStyle.checked = slotSkin.getDrawable("wand");
        wandStyle.up = slotSkin.getDrawable("wand");
        wandStyle.down = slotSkin.getDrawable("wand");
        // for health potion
        Button.ButtonStyle healthStyle = new Button.ButtonStyle();
        healthStyle.checked = slotSkin.getDrawable("healthPotion");
        healthStyle.up = slotSkin.getDrawable("healthPotion");
        healthStyle.down = slotSkin.getDrawable("healthPotion");
        // for energy potion
        Button.ButtonStyle energyStyle = new Button.ButtonStyle();
        energyStyle.checked = slotSkin.getDrawable("energyPotion");
        energyStyle.up = slotSkin.getDrawable("energyPotion");
        energyStyle.down = slotSkin.getDrawable("energyPotion");
        // for damage potion
        Button.ButtonStyle damageStyle = new Button.ButtonStyle();
        damageStyle.checked = slotSkin.getDrawable("damagePotion");
        damageStyle.up = slotSkin.getDrawable("damagePotion");
        damageStyle.down = slotSkin.getDrawable("damagePotion");
        // for speed potion
        Button.ButtonStyle speedStyle = new Button.ButtonStyle();
        speedStyle.checked = slotSkin.getDrawable("speedPotion");
        speedStyle.up = slotSkin.getDrawable("speedPotion");
        speedStyle.down = slotSkin.getDrawable("speedPotion");
        // for knock potion
        Button.ButtonStyle knockStyle = new Button.ButtonStyle();
        knockStyle.checked = slotSkin.getDrawable("knockPotion");
        knockStyle.up = slotSkin.getDrawable("knockPotion");
        knockStyle.down = slotSkin.getDrawable("knockPotion");



        // create the Array
        m_buttonArr = new Array<Array<Button>>(4);
        for(int i = 0; i < 4; i++){
            m_buttonArr.add(new Array<Button>(4));
        }
        // create label style and labels
        BitmapFont bfont = new BitmapFont();
        final Label.LabelStyle labelStyle = new Label.LabelStyle(bfont, Color.WHITE);
        m_nameLabel = new Label("Item Name", labelStyle);
        m_nameLabel.setPosition((screenWidth/4), (screenHeight*9/10));
        m_stage.addActor(m_nameLabel);

        m_goldLabel = new Label("Gold: " + a.getPlayer().getGold(), labelStyle);
        m_goldLabel.setPosition((screenWidth/4), (screenHeight*9/10)+50);
        m_stage.addActor(m_goldLabel);

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
                    switch(m_inventory.get(4*i+j).getTheWeaponType()){
                        case SWORD:
                            m_buttonArr.get(i).add(new Button(swordStyle));
                            break;
                        case AXE:
                            m_buttonArr.get(i).add(new Button(axeStyle));
                            break;
                        case WAND:
                            m_buttonArr.get(i).add(new Button(wandStyle));
                            break;
                        case BOW:
                            m_buttonArr.get(i).add(new Button(bowStyle));
                            break;
                    }
                }
                else{
                    switch(m_inventory.get(4*i+j).getConsumableType()){
                        case HEALTH:
                            m_buttonArr.get(i).add(new Button(healthStyle));
                            break;
                        case ENERGY:
                            m_buttonArr.get(i).add(new Button(energyStyle));
                            break;
                        case DAMAGE:
                            m_buttonArr.get(i).add(new Button(damageStyle));
                            break;
                        case SPEED:
                            m_buttonArr.get(i).add(new Button(speedStyle));
                            break;
                        case KNOCK:
                            m_buttonArr.get(i).add(new Button(knockStyle));
                            break;
                    }

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
                        if(m_inventory.get(Integer.parseInt(m_clicked.getName())).getType() == Item.m_itemType.CONSUMABLE)
                            m_equipOrUse.setText("Use");
                        else
                            m_equipOrUse.setText("Equip");
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
                InventoryScreen.m_numDroppedItems = 0;
            }
        });


        m_backButton.setBounds(0, ScreenScale.scaleHeight(-200) + Gdx.graphics.getHeight(),
                ScreenScale.scale(200), ScreenScale.scale(200));
        m_stage.addActor(m_backButton);

        // create Style
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

        // create equip button
        m_equipOrUse = new TextButton("Equip", textButtonStyle);
        m_equipOrUse.setHeight(screenHeight / 5);
        m_equipOrUse.setWidth(screenWidth / 4);
        m_equipOrUse.setPosition((screenWidth*2/4), (screenHeight*4/5));
        m_stage.addActor(m_equipOrUse);

        m_equipOrUse.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                if(m_inventory.get(Integer.parseInt(m_clicked.getName())).getType() == Item.m_itemType.CONSUMABLE){
                    switch(m_inventory.get(Integer.parseInt(m_clicked.getName())).getConsumableType()){
                        case HEALTH:
                            a.getPlayer().addHealth(m_inventory.get(Integer.parseInt(m_clicked.getName())).giveHealth());
                            removeItem(Integer.parseInt(m_clicked.getName()), a);
                            a.setScreen(new InventoryScreen(a));
                            break;
                        case ENERGY:
                            a.getPlayer().addStamina(m_inventory.get(Integer.parseInt(m_clicked.getName())).giveEnergy());
                            removeItem(Integer.parseInt(m_clicked.getName()), a);
                            a.setScreen(new InventoryScreen(a));
                            break;
                        case DAMAGE:
                            a.getPlayer().modifyStatsConsume(5, m_inventory.get(Integer.parseInt(m_clicked.getName())).getStats().getDamageChange(),
                                    m_inventory.get(Integer.parseInt(m_clicked.getName())).getStats().getDuration());
                            removeItem(Integer.parseInt(m_clicked.getName()), a);
                            a.setScreen(new InventoryScreen(a));
                            break;
                        case SPEED:
                            a.getPlayer().modifyStatsConsume(3, m_inventory.get(Integer.parseInt(m_clicked.getName())).getStats().getSpeedChange(),
                                    m_inventory.get(Integer.parseInt(m_clicked.getName())).getStats().getDuration());
                            removeItem(Integer.parseInt(m_clicked.getName()), a);
                            a.setScreen(new InventoryScreen(a));
                            break;
                        case KNOCK:
                            a.getPlayer().modifyStatsConsume(6, m_inventory.get(Integer.parseInt(m_clicked.getName())).getStats().getKnockback(),
                                    m_inventory.get(Integer.parseInt(m_clicked.getName())).getStats().getDuration());
                            removeItem(Integer.parseInt(m_clicked.getName()), a);
                            a.setScreen(new InventoryScreen(a));
                            break;
                        default:
                            break;
                    }
                }
                else{
                    if(a.getPlayer().getEquipped() == null) {
                        a.getPlayer().modifyStatsWeapon(m_inventory.get(Integer.parseInt(m_clicked.getName())));
                        a.getPlayer().setEquipped(m_inventory.get(Integer.parseInt(m_clicked.getName())));
                    }
                    else {
                        a.getPlayer().demodifyStatsWeapon(a.getPlayer().getEquipped());
                        a.getPlayer().modifyStatsWeapon(m_inventory.get(Integer.parseInt(m_clicked.getName())));
                        a.getPlayer().setEquipped(m_inventory.get(Integer.parseInt(m_clicked.getName())));
                    }
                }
            }
        });

        m_drop = new TextButton("Drop", textButtonStyle);
        m_drop.setHeight(screenHeight / 5);
        m_drop.setWidth(screenWidth / 4);
        m_drop.setPosition((screenWidth*3/4), (screenHeight*4/5));
        m_stage.addActor(m_drop);


        m_drop.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                m_numDroppedItems++;
                a.getCurrentWorld().addItem(m_inventory.get(Integer.parseInt(m_clicked.getName())),
                        new Vector2(a.getPlayer().getX() + 120 * MathUtils.cosDeg(m_numDroppedItems * 25),
                                a.getPlayer().getY() + 120 * MathUtils.sinDeg(m_numDroppedItems * 25)));
                removeItem(Integer.parseInt(m_clicked.getName()), a);
                System.out.println(m_numDroppedItems + "----");
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
        // unequips the item if it is equipped
        if(a.getPlayer().getEquipped() == m_inventory.get(pos)){
            a.getPlayer().demodifyStatsWeapon(m_inventory.get(pos));
            a.getPlayer().setEquipped(null);
        }
        for(int i = pos; i < m_numItems-1; i++){
            m_inventory.set(i, m_inventory.get(i+1));
        }
        m_inventory.removeIndex(m_numItems-1);
        m_numItems--;
        a.getPlayer().setNumInventory(m_numItems);

    }
}
