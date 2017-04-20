package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;


/**
 * Created by Peter on 4/19/2017.
 */

public class Item {
    // changes/effects
    private Stats m_playerStats;
    private Stats m_enemyStats;
    private int m_healthChange; // for consumables only
    private int m_energyChange; // "               "
    private boolean m_isEquipped;// for weapons only

    // enums for item type, consumable type, weapon type
    public enum m_itemType{WEAPON, CONSUMABLE};
    public enum m_consumableType{DAMAGE, HEALTH, ENERGY, SPEED, KNOCK}
    public enum m_weaponType{SWORD, WAND, AXE, BOW}

    // types that the item will have
    private m_itemType m_type;
    private m_consumableType m_theConsumableType;
    private m_weaponType m_theWeaponType;
    private String m_itemName;

    // strings for weapon name generation
    public static final String swordNames[] = {"Sword", "Blade", "Sabre", "Dagger", "Scimitar", "Rapier", "Cutlass", "Broadsword", "Rapier"};
    public static final String axeNames[] = {"Axe", "Tomahawk", "Hatchet", "Scythe", "Cleaver"};
    public static final String wandNames[] = {"Wand", "Baton", "Staff", "Scepter", "Caduceus"};
    public static final String bowNames[] = {"Bow", "Longbow", "Crossbow", "Recurve", "Shortbow"};
    public static final String endNames[] = {"Power", "Destruction", "Agalon", "Memes", "Death", "Fun", "Class", "The Elders",
            "The Moon", "Dragons", "Brody", "Sean", "Satya", "Mahzain", "Jacob", "Kanye", "Edwin", "Safa", "Programming", "Charles",
            "Shahram Jahani", "Pawel Wocjan", "Szumlanski", "Kuppalapale Vajravelu"};

    // Textures and sprite
    private SpriteBatch m_batch;
    private Texture m_texture;
    private Sprite m_sprite;

    public Item(){

    }

    public static Item generateWeapon(){
        Item weapon = new Item();
        weapon.m_type = m_itemType.WEAPON;
        weapon.m_batch = new SpriteBatch();

        // generate the weapon type
        Random rand = new Random();
        int weaponTypeInt = rand.nextInt() % 4;
        switch(weaponTypeInt){
            case(0):
                weapon.m_theWeaponType = m_weaponType.SWORD;
                weapon.m_texture = new Texture("swordArt.png");
                weapon.m_sprite = new Sprite(weapon.m_texture);
                break;
            case(1):
                weapon.m_theWeaponType = m_weaponType.WAND;
                weapon.m_texture = new Texture("swordArt.png");
                weapon.m_sprite = new Sprite(weapon.m_texture);
                break;
            case(2):
                weapon.m_theWeaponType = m_weaponType.AXE;
                weapon.m_texture = new Texture("swordArt.png");
                weapon.m_sprite = new Sprite(weapon.m_texture);
                break;
            case(3):
                weapon.m_theWeaponType = m_weaponType.BOW;
                weapon.m_texture = new Texture("swordArt.png");
                weapon.m_sprite = new Sprite(weapon.m_texture);
                break;
            default:
                weapon.m_theWeaponType = m_weaponType.SWORD;
                weapon.m_texture = new Texture("swordArt.png");
                weapon.m_sprite = new Sprite(weapon.m_texture);
                break;
        }

        // name the weapon
        weapon.m_itemName = generateItemName(weapon.m_theWeaponType, rand);

        // generate its stats for speed, defense, damage, knockback
        weapon.m_playerStats = new Stats(0, 0, 0, rand.nextFloat()/2+1, rand.nextFloat()/2+1,
                rand.nextFloat()/2+1, rand.nextFloat()/2+1, 0);
        weapon.m_enemyStats = new Stats(0, 0, 0, rand.nextFloat()/2+1, rand.nextFloat()/2+1,
                rand.nextFloat()/2+1, rand.nextFloat()/2+1, 0);

        weapon.m_isEquipped = false;
        return weapon;
    }


    public static Item generateConsumable() {
        Item consumable = new Item();
        consumable.m_type = m_itemType.CONSUMABLE;

        // generate consumable type
        Random rand = new Random();
        int typeInt = rand.nextInt(5);

        switch (typeInt) {
            case (0):
                consumable.m_theConsumableType = m_consumableType.DAMAGE;
                consumable.m_itemName = "Damage Potion";
                consumable.m_playerStats = new Stats(0, 0, 0, 0, 0, rand.nextFloat()/2+1, 0, rand.nextInt(10) + 10);
                consumable.m_enemyStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);
                consumable.m_texture = new Texture(Gdx.files.internal("potion.png"));
                consumable.m_sprite = new Sprite(consumable.m_texture);
                break;
            case (1):
                consumable.m_theConsumableType = m_consumableType.HEALTH;
                consumable.m_itemName = "Health Potion";
                // TODO consumable.m_healthChange
                consumable.m_playerStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);
                consumable.m_enemyStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);

                consumable.m_texture = new Texture(Gdx.files.internal("potion.png"));
                consumable.m_sprite = new Sprite(consumable.m_texture);
                break;
            case (2):
                consumable.m_theConsumableType = m_consumableType.ENERGY;
                consumable.m_itemName = "Energy Potion";
                // TODO consumable.m_energyChange
                consumable.m_playerStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);
                consumable.m_enemyStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);

                consumable.m_texture = new Texture(Gdx.files.internal("potion.png"));
                consumable.m_sprite = new Sprite(consumable.m_texture);
                break;
            case (3):
                consumable.m_theConsumableType = m_consumableType.SPEED;
                consumable.m_itemName = "Speed Potion";
                consumable.m_playerStats = new Stats(0, 0, 0, rand.nextFloat()/2+1, 0, 0, 0, rand.nextInt(10) + 10);
                consumable.m_enemyStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);

                consumable.m_texture = new Texture(Gdx.files.internal("potion.png"));
                consumable.m_sprite = new Sprite(consumable.m_texture);
                break;
            case (4):
                consumable.m_theConsumableType = m_consumableType.KNOCK;
                consumable.m_itemName = "Knockback Potion";
                consumable.m_playerStats = new Stats(0, 0, 0, 0, 0, 0, rand.nextFloat()/2+1, rand.nextInt(10) + 10);
                consumable.m_enemyStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);

                consumable.m_texture = new Texture(Gdx.files.internal("potion.png"));
                consumable.m_sprite = new Sprite(consumable.m_texture);
                break;
        }
        return consumable;
    }

    private static String generateItemName(m_weaponType type, Random rand){
        String first[] = {};
        switch(type) {
            case SWORD:
                first = swordNames;
                break;
            case WAND:
                first = wandNames;
                break;
            case AXE:
                first = axeNames;
                break;
            case BOW:
                first = bowNames;
        }
        int firstWord = rand.nextInt(first.length);
        int secondWord = rand.nextInt(endNames.length);
        return first[firstWord] + " of " + endNames[secondWord];
    }

    public Texture getTexture(){ return m_texture;}
    public Sprite getSprite(){ return m_sprite;}
    public m_itemType getType() {return m_type;}
    public String getName() {return m_itemName;}
    public m_consumableType getConsumableType() { return m_theConsumableType;}
    public Stats getStats() {return m_playerStats;}

    // returns info for the Inventory screen to display
    public static String getInfo(Item item){
        String info = "";
        info += item.getName();
        info += "\n";
        if(item.getType() == m_itemType.WEAPON) {
            info += item.m_playerStats.weaponStatsInfo();
        }
        else {
            m_consumableType type = item.getConsumableType();
            switch(type){
                case HEALTH:
                    info += item.m_healthChange;
                    break;
                case DAMAGE:
                    info+= "Damage Mod: " +  String.format("+%%%d", (item.getStats().getDamageChange() - 1 )* 100);
                    info += ", " + item.m_playerStats.consumeStatsInfo();
                    break;
                case SPEED:
                    info+= "Speed Mod: " +  String.format("+%%%d", (item.getStats().getSpeedChange() - 1) * 100);
                    info += ", " + item.m_playerStats.consumeStatsInfo();
                    break;
                case KNOCK:
                    info+= "Knockback Mod: " +  String.format("+%%%d", (item.getStats().getKnockback() - 1) * 100);
                    info += ", " + item.m_playerStats.consumeStatsInfo();
                    break;
                case ENERGY:
                    info+= "Energy: " +  item.m_energyChange;
                    break;
            }
        }
        return info;
    }

    public int giveHealth(){return m_healthChange;}
    public int giveEnergy(){return m_energyChange;}
}
