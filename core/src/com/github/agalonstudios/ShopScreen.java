package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

/**
 * Created by mahzain on 4/20/17.
 */

public class ShopScreen implements Screen {
    private Array<Item> m_playerInventory;
    private Array<Item> m_shopInventory;
    private Button m_backButton;
    private Button m_buyButton;
    private Button m_sellButton;
    private Array<Array<Button>> m_shopArr;
    private Array<Array<Button>> m_inventoryArr;
    private Label m_nameLabel;
    private Stage m_stage;
    private int m_shopItemCount;
    private int m_inventoryItemCount;
    private Button m_clicked;
    private ShopEntity m_shop;

    public ShopScreen(final Agalon a, final ShopEntity shop)
    {
        // TODO GET THE ITEM COUNTS SO YOU CAN ACTUALLY CODE THIS

        m_stage = new Stage();
        Gdx.input.setInputProcessor(m_stage);
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        m_shopInventory = shop.getShopInventory();
        m_shopItemCount = shop.getShopSize();
        m_playerInventory = a.getPlayer().getInventory();
        m_inventoryItemCount = a.getPlayer().getNumInventory();



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

        // stuff for skin

        slotSkin.add("sword", new Texture("swordArt.png"));
        slotSkin.add("axe", new Texture("axe.png"));
        slotSkin.add("bow", new Texture("bow.png"));
        slotSkin.add("wand", new Texture("wand.png"));
        slotSkin.add("healthPotion", new Texture("potion.png"));
        slotSkin.add("energyPotion", new Texture("energyPotion.png"));
        slotSkin.add("damagePotion", new Texture("damagePotion.png"));
        slotSkin.add("speedPotion", new Texture("speedPotion.png"));
        slotSkin.add("knockPotion", new Texture("knockPotion.png"));



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

        m_shopArr = new Array<Array<Button>>(4);
        m_inventoryArr = new Array<Array<Button>>(4);
        for(int i = 0; i < 4; i++){
            m_shopArr.add(new Array<Button>(4));
            m_inventoryArr.add(new Array<Button>(4));
        }
        BitmapFont bfont = new BitmapFont();
        final Label.LabelStyle labelStyle = new Label.LabelStyle(bfont, Color.WHITE);
        m_nameLabel = new Label("Item Name", labelStyle);
        m_nameLabel.setPosition((screenWidth/4), (screenHeight*9/10));
        m_stage.addActor(m_nameLabel);
        int rowInv, colInv;
        if(m_inventoryItemCount % 4 == 0)
            rowInv = m_inventoryItemCount / 4;
        else
            rowInv = m_inventoryItemCount / 4 + 1;

        for(int i = 0; i < rowInv; i++) {
            // stuff for how many cols
            if(rowInv ==1)
                colInv = m_inventoryItemCount;
            else if(rowInv == 2 && i == 1)
                colInv = m_inventoryItemCount-4;
            else if(rowInv == 3 && i == 2)
                colInv = m_inventoryItemCount - 8;
            else if(rowInv == 4 && i == 3)
                colInv = m_inventoryItemCount - 12;
            else
                colInv = 4;

            for(int j = 0; j < colInv; j++) {
                // create the button
                if(m_playerInventory.get(4*i+j).getType() == Item.m_itemType.WEAPON){
                    switch(m_playerInventory.get(4*i+j).getTheWeaponType()){
                        case SWORD:
                            m_inventoryArr.get(i).add(new Button(swordStyle));
                            break;
                        case AXE:
                            m_inventoryArr.get(i).add(new Button(axeStyle));
                            break;
                        case WAND:
                            m_inventoryArr.get(i).add(new Button(wandStyle));
                            break;
                        case BOW:
                            m_inventoryArr.get(i).add(new Button(bowStyle));
                            break;
                    }
                }
                else{
                    switch(m_playerInventory.get(4*i+j).getConsumableType()){
                        case HEALTH:
                            m_inventoryArr.get(i).add(new Button(healthStyle));
                            break;
                        case ENERGY:
                            m_inventoryArr.get(i).add(new Button(energyStyle));
                            break;
                        case DAMAGE:
                            m_inventoryArr.get(i).add(new Button(damageStyle));
                            break;
                        case SPEED:
                            m_inventoryArr.get(i).add(new Button(speedStyle));
                            break;
                        case KNOCK:
                            m_inventoryArr.get(i).add(new Button(knockStyle));
                            break;
                    }
                }
                // set the button's size, pos, and info
                final Button thisButton = m_inventoryArr.get(i).get(j);
                //thisButton.setName(Item.getInfo(m_inventory.get(4*i+j)));
                int pos = 4*i+j;
                thisButton.setName(Integer.toString(pos));
                thisButton.setHeight(screenHeight / 5);
                thisButton.setWidth(screenWidth / 4 / 2);
                thisButton.setPosition((screenWidth*j/4/2), (screenHeight*(3-i)/5));
                m_stage.addActor(thisButton);

                thisButton.addListener(new ClickListener() {
                    public void clicked (InputEvent event, float x, float y){
                        // changes the info displayed
                        m_clicked = thisButton;
                        m_nameLabel.setText(m_playerInventory.get(Integer.parseInt(m_clicked.getName())).getInfo(m_playerInventory.get(Integer.parseInt(m_clicked.getName()))));
                        //System.out.println(m_clicked.getName());
                        //System.out.println(m_nameLabel.toString());
                    }
                });
            }
        }
        int rowShop, colShop;

        System.out.println(m_shopItemCount);
        if(m_shopItemCount % 4 == 0)
            rowShop = m_shopItemCount / 4;
        else
            rowShop = m_shopItemCount / 4 + 1;
        for(int i = 0; i < rowShop; i++) {
            // stuff for how many cols
            if(rowShop ==1)
                colShop = m_shopItemCount;
            else if(rowShop == 2 && i == 1)
                colShop = m_shopItemCount-4;
            else if(rowShop == 3 && i == 2)
                colShop = m_shopItemCount - 8;
            else if(rowShop == 4 && i == 3)
                colShop = m_shopItemCount - 12;
            else
                colShop = 4;

            for(int j = 0; j < colShop; j++) {
                // create the button'
                System.out.println(i + " " + j);
                if(m_shopInventory.get(4*i+j).getType() == Item.m_itemType.WEAPON){
                    switch(m_shopInventory.get(4*i+j).getTheWeaponType()){
                        case SWORD:
                            m_shopArr.get(i).add(new Button(swordStyle));
                            break;
                        case AXE:
                            m_shopArr.get(i).add(new Button(axeStyle));
                            break;
                        case WAND:
                            m_shopArr.get(i).add(new Button(wandStyle));
                            break;
                        case BOW:
                            m_shopArr.get(i).add(new Button(bowStyle));
                            break;
                    }
                }
                else{
                    switch(m_shopInventory.get(4*i+j).getConsumableType()){
                        case HEALTH:
                            m_shopArr.get(i).add(new Button(healthStyle));
                            break;
                        case ENERGY:
                            m_shopArr.get(i).add(new Button(energyStyle));
                            break;
                        case DAMAGE:
                            m_shopArr.get(i).add(new Button(damageStyle));
                            break;
                        case SPEED:
                            m_shopArr.get(i).add(new Button(speedStyle));
                            break;
                        case KNOCK:
                            m_shopArr.get(i).add(new Button(knockStyle));
                            break;
                    }
                }
                // set the button's size, pos, and info
                final Button thisButton = m_shopArr.get(i).get(j);
                //thisButton.setName(Item.getInfo(m_inventory.get(4*i+j)));
                int pos = 4*i+j;
                thisButton.setName(Integer.toString(pos));
                thisButton.setHeight(screenHeight / 5);
                thisButton.setWidth(screenWidth / 4 / 2);
                thisButton.setPosition(screenWidth*j/4/2+ (screenWidth/2), (screenHeight*(3-i)/5));
                m_stage.addActor(thisButton);

                thisButton.addListener(new ClickListener() {
                    public void clicked (InputEvent event, float x, float y){
                        // changes the info displayed
                        m_clicked = thisButton;
                        m_nameLabel.setText(m_shopInventory.get(Integer.parseInt(m_clicked.getName())).getInfo(m_shopInventory.get(Integer.parseInt(m_clicked.getName()))));
                        //System.out.println(m_clicked.getName());
                        //System.out.println(m_nameLabel.toString());
                    }
                });
            }
        }
        Skin backSkin = new Skin();
        backSkin.add("backButton", new Texture("backButton.png"));

        Button.ButtonStyle backStyle = new Button.ButtonStyle();
        backStyle.checked = backSkin.getDrawable("backButton");
        backStyle.up = backSkin.getDrawable("backButton");
        backStyle.down = backSkin.getDrawable("backButton");

        m_backButton = new Button(backStyle);

        m_backButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y){
                a.getPlayer().resetVelocity();
                HUD.reset();
                a.returnToOverworld();
                a.getPlayer().setPosition(shop.getX() + shop.getWidth() + 10, shop.getY() + shop.getHeight() / 2);
            }
        });
        m_backButton.setBounds(0, ScreenScale.scaleHeight(-200) + Gdx.graphics.getHeight(),
                ScreenScale.scale(200), ScreenScale.scale(200));
        m_stage.addActor(m_backButton);


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
        TextButton m_buyButton = new TextButton("Buy", textButtonStyle);
        m_buyButton.setHeight(screenHeight / 5);
        m_buyButton.setWidth(screenWidth / 4);
        m_buyButton.setPosition((screenWidth*2/4), (screenHeight*4/5));
        m_stage.addActor(m_buyButton);

        m_buyButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

            }
        });
        TextButton m_sellButton = new TextButton("Sell", textButtonStyle);
        m_sellButton.setHeight(screenHeight / 5);
        m_sellButton.setWidth(screenWidth / 4);
        m_sellButton.setPosition((screenWidth*2/4), (screenHeight*4/5));
        m_stage.addActor(m_sellButton);

        m_sellButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {

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
