/*
 * 
 */
package net.classic.remastered.client.render.texture;

import net.classic.remastered.client.render.texture.TextureFX;
import net.classic.remastered.game.world.tile.Block;

public final class TextureWaterFX
extends TextureFX {
    private float[] red = new float[256];
    private float[] blue = new float[256];
    private float[] green = new float[256];
    private float[] alpha = new float[256];
    private int updates = 0;

    public TextureWaterFX() {
        super(Block.WATER.textureId);
    }

    @Override
    public final void animate() {
        int var6;
        int var5;
        int var4;
        float var3;
        int var2;
        int var1;
        ++this.updates;
        for (var1 = 0; var1 < 16; ++var1) {
            for (var2 = 0; var2 < 16; ++var2) {
                var3 = 0.0f;
                for (var4 = var1 - 1; var4 <= var1 + 1; ++var4) {
                    var5 = var4 & 0xF;
                    var6 = var2 & 0xF;
                    var3 += this.red[var5 + (var6 << 4)];
                }
                this.blue[var1 + (var2 << 4)] = var3 / 3.3f + this.green[var1 + (var2 << 4)] * 0.8f;
            }
        }
        for (var1 = 0; var1 < 16; ++var1) {
            for (var2 = 0; var2 < 16; ++var2) {
                int n = var1 + (var2 << 4);
                this.green[n] = this.green[n] + this.alpha[var1 + (var2 << 4)] * 0.05f;
                if (this.green[var1 + (var2 << 4)] < 0.0f) {
                    this.green[var1 + (var2 << 4)] = 0.0f;
                }
                int n2 = var1 + (var2 << 4);
                this.alpha[n2] = this.alpha[n2] - 0.1f;
                if (!(Math.random() < 0.05)) continue;
                this.alpha[var1 + (var2 << 4)] = 0.5f;
            }
        }
        float[] var8 = this.blue;
        this.blue = this.red;
        this.red = var8;
        for (var2 = 0; var2 < 256; ++var2) {
            float f = 0;
            var3 = this.red[var2];
            if (f > 1.0f) {
                var3 = 1.0f;
            }
            if (var3 < 0.0f) {
                var3 = 0.0f;
            }
            float var9 = var3 * var3;
            var5 = (int)(32.0f + var9 * 32.0f);
            var6 = (int)(50.0f + var9 * 64.0f);
            var1 = 255;
            int var10 = (int)(146.0f + var9 * 50.0f);
            if (this.anaglyph) {
                var1 = (var5 * 30 + var6 * 59 + 2805) / 100;
                var4 = (var5 * 30 + var6 * 70) / 100;
                int var7 = (var5 * 30 + 17850) / 100;
                var5 = var1;
                var6 = var4;
                var1 = var7;
            }
            this.textureData[var2 << 2] = (byte)var5;
            this.textureData[(var2 << 2) + 1] = (byte)var6;
            this.textureData[(var2 << 2) + 2] = (byte)var1;
            this.textureData[(var2 << 2) + 3] = (byte)var10;
        }
    }
}

