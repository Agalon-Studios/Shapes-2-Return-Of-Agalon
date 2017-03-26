package com.github.agalonstudios;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Satya Patel on 2/18/2017.
 */

public class ExtendedShapeRenderer extends ShapeRenderer {
    private static final int borderSize = 5;
    private static final int borderColorDepth = 2;
    private static final Color grayCooldownColor = new Color(0, 0, 0, .4f);


    public void borderedRect(Rectangle boundingBox, Color color) {
        borderedRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, color);
    }

    public void borderedRect(float x, float y, float width, float height, Color color) {
        float interiorWidth = width - borderSize * 2;
        float interiorHeight = height - borderSize * 2;

        super.set(ShapeType.Filled);

        super.setColor(getBorderColor(color));
        super.rect(x, y, width, height);
        super.setColor(color);
        super.rect(x + (width - interiorWidth) / 2, y + (height - interiorHeight) / 2, interiorWidth, interiorHeight);
    }



    public void borderedCircle(float x, float y, float radius, Color color) {
        float interiorRadius = radius - borderSize * 2;

        super.set(ShapeType.Filled);
        super.setColor(getBorderColor(color));
        super.circle(x, y, radius);
        super.setColor(color);
        super.circle(x, y, interiorRadius);
    }

    public void borderedTriangle(float x1, float y1, float x2, float y2, float x3, float y3, Color color) {
        float centroidX = (x1 + x2 + x3) / 3;
        float centroidY = (y1 + y2 + y3) / 3;

        float interiorX1 = borderSize * x1 + (1 - borderSize) * centroidX;
        float interiorX2 = borderSize * x2 + (1 - borderSize) * centroidX;
        float interiorX3 = borderSize * x3 + (1 - borderSize) * centroidX;

        float interiorY1 = borderSize * y1 + (1 - borderSize) * centroidY;
        float interiorY2 = borderSize * y2 + (1 - borderSize) * centroidY;
        float interiorY3 = borderSize * y3 + (1 - borderSize) * centroidY;


        super.set(ShapeType.Filled);
        super.setColor(getBorderColor(color));
        super.triangle(x1, y1, x2, y2, x3, y3);
        super.setColor(color);
        super.triangle(interiorX1, interiorY1, interiorX2, interiorY2, interiorX3, interiorY3);
    }

    private static void partialSquare(float xPosition, float yPosition, float radius, float start, float degrees) {
        int segments = Math.max(1, (int)(6 * (float)Math.cbrt(radius) * (degrees / 360.0f)));
        float delta = (2 * MathUtils.PI * (degrees / 360.0f)) / segments;
        float centerX = xPosition + radius;
        float centerY = yPosition + radius;
        float currentTheta = (2 * MathUtils.PI * (start / 360.0f));

        for (int i = 0; i < segments; i++) {
            float cos = MathUtils.cos(start);
            float sin = MathUtils.sin(start);

            float endX = cos * radius * 2;
            float endY = sin * radius * 2;

            if (currentTheta <= MathUtils.PI/4 || currentTheta > 7* (MathUtils.PI/4)) {

            }
            else if (currentTheta > MathUtils.PI/4 && currentTheta <= (MathUtils.PI/4) * 3) {

            }
            else if (currentTheta > (MathUtils.PI/4) * 3 && currentTheta <= (MathUtils.PI/4) * 5) {

            }
            else {

            }

            currentTheta += delta;
        }

    }

    private static Color getBorderColor(Color color) {
        int colorMask = Color.argb8888(color);
        int colorSection;

        for (int i = 0; i < 3; i++) {
            colorSection = 0x000000FF & (colorMask >> i * 8);
            colorSection /= borderColorDepth;
            colorSection = (0x000000FF & colorSection) << i * 8;
            colorMask |= 0x000000FF << i*8;
            colorMask ^= 0x000000FF << i*8;
            colorMask |= colorSection;

        }
        Color ret = new Color();
        Color.argb8888ToColor(ret, colorMask);
        return ret;
    }
}
