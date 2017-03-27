package com.github.agalonstudios;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by Satya Patel on 3/27/2017.
 */

public class Globals {

    private static AssetManager assetManager;

    public Globals() {
        assetManager = new AssetManager();
    }

    public static AssetManager getAssetManager() {
        if (assetManager == null) assetManager = new AssetManager();
        return assetManager;
    }

}
