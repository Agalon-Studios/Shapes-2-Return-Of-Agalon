package com.github.agalonstudios;

/**
 * Created by spr on 3/12/17.
 */
public class Stats {
    public final int meleeDamage;
    public final int rangeDamage;
    public final int mageDamage;
    public float speedChange;
    public float defenseChange;
    public float damageChange;
    public float knockback;
    public float duration;

    public Stats(int md, int rd, int gd, float sc, float dx, float mc, float kb, float dur) {
        meleeDamage = md;
        rangeDamage = rd;
        mageDamage = gd;
        speedChange = sc;
        defenseChange = dx;
        damageChange = mc;
        knockback = kb;
        duration = dur;
    }
    // a method to use with Item.getInfo
    public String weaponStatsInfo(){
        String info = "";
        info += "Damage mod: " + String.format("%.2f", damageChange) + ", ";
        info += "Speed mod: " + String.format("%.2f", speedChange) + "\n";
        info += "Knockback mod: " + String.format("%.2f", knockback) + ", ";
        info += "Defense mod: " + String.format("%.2f", defenseChange);
        return info;
    }

    public String consumeStatsInfo(){
        String info = "";
        info += "Duration: " + String.format("%.2f", duration);
        return info;
    }

    public float getSpeedChange(){return speedChange;}
    public float getDefenseChange() {return defenseChange;}
    public float getKnockback() {return knockback;}
    public float getDamageChange() { return damageChange;}
    public float getDuration(){ return duration;}

    public void modStat(int stat, float mod){
        switch(stat){
            case(3):
                speedChange = speedChange*mod;
                break;
            case(4):
                defenseChange = defenseChange*mod;
                break;
            case(5):
                damageChange = damageChange*mod;
                break;
            case(6):
                knockback = knockback*mod;
                break;
            default:
                break;
        }
    }

    public void deModStat(int stat, float mod){
        switch(stat){
            case(3):
                speedChange = speedChange/mod;
                break;
            case(4):
                defenseChange = defenseChange/mod;
                break;
            case(5):
                damageChange = damageChange/mod;
                break;
            case(6):
                knockback = knockback/mod;
                break;
            default:
                break;
        }
    }

    public void printStats(){
        System.out.println(meleeDamage + " " + rangeDamage + " " + mageDamage + " " + speedChange + " " +
                            defenseChange + " " + damageChange + " " + knockback + " " + duration);
    }


}
