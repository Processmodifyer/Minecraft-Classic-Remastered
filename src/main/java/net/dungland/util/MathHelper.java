/*
 * 
 */
package net.dungland.util;

public final class MathHelper {
    private static float[] SIN_TABLE = new float[65536];

    static {
        for (int var0 = 0; var0 < 65536; ++var0) {
            MathHelper.SIN_TABLE[var0] = (float)Math.sin((double)var0 * Math.PI * 2.0 / 65536.0);
        }
    }

    public static final float sin(float var0) {
        return SIN_TABLE[(int)(var0 * 10430.378f) & 0xFFFF];
    }

    public static final float cos(float var0) {
        return SIN_TABLE[(int)(var0 * 10430.378f + 16384.0f) & 0xFFFF];
    }

    public static final float sqrt(float var0) {
        return (float)Math.sqrt(var0);
    }

    public static float abs(final float float1) {
        if (float1 >= 0.0f) {
            return float1;
        }
        return -float1;
    }
    
}

