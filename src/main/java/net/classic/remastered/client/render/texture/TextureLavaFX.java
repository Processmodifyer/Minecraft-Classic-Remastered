/*
 * 
 */
package net.classic.remastered.client.render.texture;

import net.classic.remastered.client.render.texture.TextureFX;
import net.classic.remastered.game.world.tile.Block;
import net.dungland.util.MathHelper;

public final class TextureLavaFX
extends TextureFX {
    private float[] red = new float[256];
    private float[] green = new float[256];
    private float[] blue = new float[256];
    private float[] alpha = new float[256];

    public TextureLavaFX() {
        super(Block.LAVA.textureId);
    }

    @Override
    public final void animate() {
        int var9;
        int var8;
        int var7;
        int var6;
        int var5;
        float var3;
        int var2;
        int var1;
        for (var1 = 0; var1 < 16; ++var1) {
            for (var2 = 0; var2 < 16; ++var2) {
                var3 = 0.0f;
                int var4 = (int)(MathHelper.sin((float)var2 * (float)Math.PI * 2.0f / 16.0f) * 1.2f);
                var5 = (int)(MathHelper.sin((float)var1 * (float)Math.PI * 2.0f / 16.0f) * 1.2f);
                for (var6 = var1 - 1; var6 <= var1 + 1; ++var6) {
                    for (var7 = var2 - 1; var7 <= var2 + 1; ++var7) {
                        var8 = var6 + var4 & 0xF;
                        var9 = var7 + var5 & 0xF;
                        var3 += this.red[var8 + (var9 << 4)];
                    }
                }
                this.green[var1 + (var2 << 4)] = var3 / 10.0f + (this.blue[(var1 & 0xF) + ((var2 & 0xF) << 4)] + this.blue[(var1 + 1 & 0xF) + ((var2 & 0xF) << 4)] + this.blue[(var1 + 1 & 0xF) + ((var2 + 1 & 0xF) << 4)] + this.blue[(var1 & 0xF) + ((var2 + 1 & 0xF) << 4)]) / 4.0f * 0.8f;
                int n = var1 + (var2 << 4);
                this.blue[n] = this.blue[n] + this.alpha[var1 + (var2 << 4)] * 0.01f;
                if (this.blue[var1 + (var2 << 4)] < 0.0f) {
                    this.blue[var1 + (var2 << 4)] = 0.0f;
                }
                int n2 = var1 + (var2 << 4);
                this.alpha[n2] = this.alpha[n2] - 0.06f;
                if (!(Math.random() < 0.005)) continue;
                this.alpha[var1 + (var2 << 4)] = 1.5f;
            }
        }
        float[] var10 = this.green;
        this.green = this.red;
        this.red = var10;
        for (var2 = 0; var2 < 256; ++var2) {
            float f = 0;
            var3 = this.red[var2] * 2.0f;
            if (f > 1.0f) {
                var3 = 1.0f;
            }
            if (var3 < 0.0f) {
                var3 = 0.0f;
            }
            var5 = (int)(var3 * 100.0f + 155.0f);
            var6 = (int)(var3 * var3 * 255.0f);
            var7 = (int)(var3 * var3 * var3 * var3 * 128.0f);
            if (this.anaglyph) {
                var8 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
                var9 = (var5 * 30 + var6 * 70) / 100;
                var1 = (var5 * 30 + var7 * 70) / 100;
                var5 = var8;
                var6 = var9;
                var7 = var1;
            }
            this.textureData[var2 << 2] = (byte)var5;
            this.textureData[(var2 << 2) + 1] = (byte)var6;
            this.textureData[(var2 << 2) + 2] = (byte)var7;
            this.textureData[(var2 << 2) + 3] = -1;
        }
    }
}

