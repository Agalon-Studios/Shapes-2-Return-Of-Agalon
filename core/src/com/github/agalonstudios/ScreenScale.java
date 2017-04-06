package com.github.agalonstudios;

/**
 * Created by spr on 3/17/17.
 */
public class ScreenScale {
    public static float WIDTH_SCALE;
    public static float HEIGHT_SCALE;
    private static boolean initted = false;
    private static final float WIDTH_FULL = 2560;
    private static final float HEIGHT_FULL = 1440;

    public static void init(int width, int height) {
        WIDTH_SCALE = (float) width / WIDTH_FULL;
        HEIGHT_SCALE = (float) height / HEIGHT_FULL;
        initted = true;
    }

    public static int scaleWidth(int width) {
        return (int) (width * WIDTH_SCALE);
    }

    public static int scaleHeight(int height) {
        return (int) (height * HEIGHT_SCALE);
    }

    public static int scale(int x) { return scaleHeight(x); }
}