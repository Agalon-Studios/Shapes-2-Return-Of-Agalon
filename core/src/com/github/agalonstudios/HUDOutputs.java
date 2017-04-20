package com.github.agalonstudios;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.agalonstudios.Ability.AbilityType;
/**
 * Created by Satya Patel on 2/23/2017.
 */

public class HUDOutputs {
    public Vector2 accelerationUpdate;

    public boolean[] abilityIsUsed;
    public Vector2[] abilityCastVectors;

    public HUDOutputs() {
        accelerationUpdate = new Vector2(0, 0);
        abilityIsUsed = new boolean[4];
        abilityCastVectors = new Vector2[4];
        for (int i = 0; i < abilityCastVectors.length; i++)
            abilityCastVectors[i] = new Vector2();
    }

    public void reset() {
        for (int i = 0; i < 4; i++) {
            abilityIsUsed[i] = false;

        }
    }

}