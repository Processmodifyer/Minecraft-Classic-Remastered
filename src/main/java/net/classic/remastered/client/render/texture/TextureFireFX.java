package net.classic.remastered.client.render.texture;

import net.classic.remastered.game.world.tile.Block;

public final class TextureFireFX extends TextureFX {
    private float[] flamesFloat1;
    private float[] flamesFloat2;
    
    public TextureFireFX(final int iconIndex) {
        super(Block.FIREBLOCK.textureId + (iconIndex << 4));
        this.flamesFloat1 = new float[320];
        this.flamesFloat2 = new float[320];
    }
    
    public final void animate() {
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 20; ++j) {
                int n = 18;
                float n2 = this.flamesFloat1[i + ((j + 1) % 20 << 4)] * 18.0f;
                for (int k = i - 1; k <= i + 1; ++k) {
                    for (int l = j; l <= j + 1; ++l) {
                        final int n3 = k;
                        final int n4 = l;
                        if (n3 >= 0 && n4 >= 0 && n3 < 16 && n4 < 20) {
                            n2 += this.flamesFloat1[n3 + (n4 << 4)];
                        }
                        ++n;
                    }
                }
                this.flamesFloat2[i + (j << 4)] = n2 / (n * 1.06f);
                if (j >= 19) {
                    this.flamesFloat2[i + (j << 4)] = (float)(Math.random() * Math.random() * Math.random() * 4.0 + Math.random() * 0.10000000149011612 + 0.20000000298023224);
                }
            }
        }
        final float[] flamesFloat2 = this.flamesFloat2;
        this.flamesFloat2 = this.flamesFloat1;
        this.flamesFloat1 = flamesFloat2;
        for (int j = 0; j < 256; ++j) {
            float n5;
            if ((n5 = this.flamesFloat1[j] * 1.8f) > 1.0f) {
                n5 = 1.0f;
            }
            if (n5 < 0.0f) {
                n5 = 0.0f;
            }
            final float n2;
            int k = (int)((n2 = n5) * 155.0f + 100.0f);
            int l = (int)(n2 * n2 * 255.0f);
            int n3 = (int)(n2 * n2 * n2 * n2 * n2 * n2 * n2 * n2 * n2 * n2 * 255.0f);
            int n4 = 255;
            if (n2 < 0.5f) {
                n4 = 0;
            }
            if (this.anaglyph) {
                final int i = (k * 30 + l * 59 + n3 * 11) / 100;
                final int n = (k * 30 + l * 70) / 100;
                final int n6 = (k * 30 + n3 * 70) / 100;
                k = i;
                l = n;
                n3 = n6;
            }
            this.textureData[j << 2] = (byte)k;
            this.textureData[(j << 2) + 1] = (byte)l;
            this.textureData[(j << 2) + 2] = (byte)n3;
            this.textureData[(j << 2) + 3] = (byte)n4;
        }
    }
}
