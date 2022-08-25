/*
 * 
 */
package net.dungland.util;

import java.awt.Color;
import java.io.Serializable;

public class ColorCache
implements Serializable {
    public float R;
    public float G;
    public float B;
    public float A;

    public ColorCache(float r, float g, float b) {
        this.R = this.FixColor(r);
        this.G = this.FixColor(g);
        this.B = this.FixColor(b);
        this.A = 1.0f;
    }

    public ColorCache(float r, float g, float b, float a) {
        this.R = this.FixColor(r);
        this.G = this.FixColor(g);
        this.B = this.FixColor(b);
        this.A = a;
    }

    public static ColorCache parseHex(String hex) {
        Color col = Color.decode("#" + hex);
        float r = (float)col.getRed() / 255.0f;
        float g = (float)col.getGreen() / 255.0f;
        float b = (float)col.getBlue() / 255.0f;
        return new ColorCache(r, g, b);
    }

    private float FixColor(float color) {
        if (color > 1.0f) {
            return 1.0f;
        }
        if (color < 0.0f) {
            return 0.0f;
        }
        return color;
    }
}

