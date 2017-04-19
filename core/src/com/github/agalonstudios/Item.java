package com.github.agalonstudios;

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
    private enum m_itemType{WEAPON, CONSUMABLE};
    private enum m_consumableType{DAMAGE, HEALTH, ENERGY, SPEED, KNOCK}
    private enum m_weaponType{SWORD, WAND, AXE, BOW}

    // types that the item will have
    private m_itemType m_type;
    private m_consumableType m_theConsumableType;
    private m_weaponType m_theWeaponType;
    private String m_itemName;

    // strings for weapon name generation
    public static final String swordNames[] = {"Sword", "Blade", "Sabre", "Dagger", "Scimitar", "Rapier", "Cutlass", "Broadsword"};
    public static final String axeNames[] = {"Axe", "Tomahawk", "Hatchet", "Scythe", "Cleaver"};
    public static final String wandNames[] = {"Wand", "Baton", "Staff", "Scepter", "Caduceus"};
    public static final String bowNames[] = {"Bow", "Longbow", "Crossbow", "Recurve"};
    public static final String endNames[] = {"Power", "Destruction", "Agalon", "Memes", "Death", "Fun", "Class", "The Elders",
            "The Moon", "Dragons", "Brody", "Sean", "Satya", "Mahzain", "Jacob"};


    public Item(){

    }

    public static Item generateWeapon(){
        Item weapon = new Item();
        weapon.m_type = m_itemType.WEAPON;

        // generate the weapon type
        Random rand = new Random();
        int weaponTypeInt = rand.nextInt() % 4;
        switch(weaponTypeInt){
            case(0):
                weapon.m_theWeaponType = m_weaponType.SWORD;
                break;
            case(1):
                weapon.m_theWeaponType = m_weaponType.WAND;
                break;
            case(2):
                weapon.m_theWeaponType = m_weaponType.AXE;
                break;
            case(3):
                weapon.m_theWeaponType = m_weaponType.BOW;
                break;
            default:
                weapon.m_theWeaponType = m_weaponType.SWORD;
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
                break;
            case (1):
                consumable.m_theConsumableType = m_consumableType.HEALTH;
                consumable.m_itemName = "Health Potion";
                // TODO consumable.m_healthChange
                consumable.m_playerStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);
                consumable.m_enemyStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);
                break;
            case (2):
                consumable.m_theConsumableType = m_consumableType.ENERGY;
                consumable.m_itemName = "Energy Potion";
                // TODO consumable.m_energyChange
                consumable.m_playerStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);
                consumable.m_enemyStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);
                break;
            case (3):
                consumable.m_theConsumableType = m_consumableType.SPEED;
                consumable.m_itemName = "Speed Potion";
                consumable.m_playerStats = new Stats(0, 0, 0, rand.nextFloat()/2+1, 0, 0, 0, rand.nextInt(10) + 10);
                consumable.m_enemyStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);
                break;
            case (4):
                consumable.m_theConsumableType = m_consumableType.KNOCK;
                consumable.m_itemName = "Knockback Potion";
                consumable.m_playerStats = new Stats(0, 0, 0, 0, 0, 0, rand.nextFloat()/2+1, rand.nextInt(10) + 10);
                consumable.m_enemyStats = new Stats(0, 0, 0, 0, 0, 0, 0, 0);
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
}
