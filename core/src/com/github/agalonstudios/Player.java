package com.github.agalonstudios;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static javax.swing.UIManager.get;


/**
 * Created by spr on 3/12/17.
 */
public class Player extends Character {
    private int m_gold;
    private int m_xp;

    protected float m_stamina;
    public int m_maxStamina;

    // TODO inventory, equipped items, abilities, other properties
    private Array<Item> m_inventory;
    private int m_numInInventory;
    private Item m_equipped;
    private float m_statCooldowns[][]; // stat mod in [i][0], duration time in [i][1]

    public Player(int h, int l, Color c) {
        super(
                32,
                new Vector2(Gdx.graphics.getWidth() / 2 - 32, Gdx.graphics.getHeight() / 2 - 32),
                Shape.SQUARE,
                h,
                150,
                800,
                l
        );
        m_fixed = false;
        m_gold = 0;
        m_xp = 0;
        m_color = c;

        m_equippedAbilities.add(new Ability(Ability.AbilityType.FLAME_BURST));
        m_equippedAbilities.add(new Ability(Ability.AbilityType.SNIPE));
        m_equippedAbilities.add(new Ability(Ability.AbilityType.ICE_ARROW));
        m_equippedAbilities.add(new Ability(Ability.AbilityType.HEAL));
        m_cooldownTimers.add(0.f);
        m_cooldownTimers.add(0.f);
        m_cooldownTimers.add(0.f);
        m_cooldownTimers.add(0.f);


        //TODO dont hardcode these in (need to make file to read hp/stam in)
        m_maxStamina = 100;
        m_stamina = 100;
        m_maxHealth = 100;
        m_health = 50;
        m_maxAbilityCount = 4;


        // TODO add constructor for stats
        m_inventory = new Array<Item>(16);
        m_numInInventory = 0;

        m_inventory.add(Item.generateWeapon());
        m_numInInventory++;
        m_inventory.add(Item.generateConsumable());
        m_inventory.add(Item.generateConsumable());
        m_numInInventory += 2;

        for (int i = 0; i < 9; i++) {
            m_inventory.add(Item.generateConsumable());
            m_numInInventory++;
        }

        System.out.println();
        m_equipped = null;

        m_statCooldowns = new float[8][2];
        for(int i = 0; i < 8; i ++)
            for(int j = 0; j<2; j++)
                m_statCooldowns[i][j] = 0;
    }

    @Override
    public void update(float delta, World world) {
        super.update(delta, world);


        m_acceleration.x = HUD.hudOutputs.accelerationUpdate.x;
        m_acceleration.y = HUD.hudOutputs.accelerationUpdate.y;

        if (Gdx.input.isKeyPressed((Input.Keys.ESCAPE))) {
            ((Agalon) Gdx.app.getApplicationListener()).returnToOverworld();
            this.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        }

        handleCasting();

        // stat effect cooldowns
        for(int i = 3; i < 7; i++){
                if(m_statCooldowns[i][0] > 0){
                    if(m_statCooldowns[i][1]-delta <= 0){
                        m_Stats.deModStat(i, m_statCooldowns[i][0]);
                        m_statCooldowns[i][0] = 0;
                        m_statCooldowns[i][1] = 0;
                    }
                    else
                        m_statCooldowns[i][1] -= delta;
            }
        }

        m_stamina += .2f;
        if (m_stamina > m_maxStamina) m_stamina = m_maxStamina;
    }

    private void handleCasting() {
        for (int i = 0; i < m_equippedAbilities.size; i++) {
            if (m_cooldownTimers.get(i) <= 0) {
                if (HUD.hudOutputs.abilityIsUsed[i] && m_stamina > m_equippedAbilities.get(i).getStaminaCost()) {
                    m_equippedAbilities.get(i).cast(this, HUD.hudOutputs.abilityCastVectors[i]);
                    m_cooldownTimers.set(i, m_equippedAbilities.get(i).getCoolDown());
                    m_stamina -= m_equippedAbilities.get(i).getStaminaCost();
                    HUD.hudOutputs.abilityIsUsed[i] = false;
                }
            }
        }
    }


    public void setEquippedAbilities(Array<Ability> abilities) {
        m_equippedAbilities = abilities;
    }


    public Array<Ability> getEquippedAbilities() {
        return m_equippedAbilities;
    }

    @Override
    public void render() {
       super.render();


        //TODO fix this one if needed
    }

    // shut up its fine loooool
    public void incrementNumInInvy() {
        m_numInInventory++;
    }

    public void decrementNumInInvy() {
        m_numInInventory--;
    }

    public void runCollision(Entity e) {

        if (e instanceof Character)
            m_health -= 3;

        if (e instanceof CastObject) {
            if (((CastObject) e).getCaster() == this) {
                return;
            }
        }

        if(e instanceof DroppedItem)
        {
            if (((DroppedItem) e).isGold()) {
                m_gold += ((DroppedItem) e).getAmount();
                ((Agalon) Gdx.app.getApplicationListener()).getCurrentWorld().removeItem((DroppedItem) e);

            }
            else if (m_numInInventory < 16) {
                m_inventory.add(((DroppedItem) e).getItem());
                m_numInInventory++;
                ((Agalon) Gdx.app.getApplicationListener()).getCurrentWorld().removeItem((DroppedItem) e);
            }
            return;
        }
        if (e instanceof ShopEntity) {
            ((Agalon) Gdx.app.getApplicationListener()).setScreen(new ShopScreen((Agalon) Gdx.app.getApplicationListener(), (ShopEntity) e));
        }

        if (e instanceof Chest) {
            return;
        }

        super.runCollision(e);

    }
    public int getGold() {
        return m_gold;
    }

    public void addGold(int amt) { m_gold += amt; }

    public void loseGold(int amt) { m_gold -= amt; }

    public Array<Item> getInventory(){ return m_inventory;}
    public int getNumInventory(){ return m_numInInventory;}
    public void setNumInventory(int num){m_numInInventory = num;}
    public void addHealth(int num){
        if(num + m_health > m_maxHealth){
            m_health = m_maxHealth;
        }
        else
            m_health += num;
    }
    public void addStamina(int num){
        if(num + m_stamina > m_maxStamina){
            m_stamina = m_maxStamina;
        }
        else
            m_stamina += num;
    }

    public void modifyStatsConsume(int whichStat, float effectMod, float time){
        // stats are 0-7 of which stat they affect in stats
        m_Stats.modStat(whichStat, effectMod);
        m_statCooldowns[whichStat][0] = effectMod;
        m_statCooldowns[whichStat][1] = time;
        m_Stats.printStats();
    }

    public void modifyStatsWeapon(Item item){
        System.out.println(item.getName());

        m_Stats.modStat(3, item.getStats().getSpeedChange());
        m_Stats.modStat(4, item.getStats().getDefenseChange());
        m_Stats.modStat(5, item.getStats().getDamageChange());
        m_Stats.modStat(6, item.getStats().getKnockback());

        // m_Stats.printStats();
    }

    public void demodifyStatsWeapon(Item item){
        m_Stats.deModStat(3, item.getStats().getSpeedChange());
        m_Stats.deModStat(4, item.getStats().getDefenseChange());
        m_Stats.deModStat(5, item.getStats().getDamageChange());
        m_Stats.deModStat(6, item.getStats().getKnockback());

        // m_Stats.printStats();
    }

    public Item getEquipped(){ return m_equipped;}
    public void setEquipped(Item item){ m_equipped = item;}
}
