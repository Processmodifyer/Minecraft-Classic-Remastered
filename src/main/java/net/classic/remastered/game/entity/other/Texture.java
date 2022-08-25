/*
 * 
 */
package net.classic.remastered.game.entity.other;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Texture {
    private int[] a;
    private int b;
    private int c;

    public BufferedImage a(BufferedImage bufferedImage) {
        this.b = 64;
        this.c = 32;
        BufferedImage bufferedImage2 = new BufferedImage(this.b, this.c, 2);
        Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        this.a = ((DataBufferInt)bufferedImage2.getRaster().getDataBuffer()).getData();
        this.b(0, 0, 32, 16);
        this.a(32, 0, 64, 32);
        this.b(0, 16, 64, 32);
        return bufferedImage2;
    }

    private void a(int i, int j, int n, int n2) {
        int n3 = 32;
        boolean n4 = false;
        int n5 = 64;
        int n6 = 32;
        n2 = 64;
        n = 0;
        j = 32;
        j = 32;
        block0: while (true) {
            if (j < n2) {
                for (int k = n; k < 32; ++k) {
                    if (this.a[j + k * this.b] >>> 24 < 128) break block0;
                }
                ++j;
                continue;
            }
            boolean bl = false;
        }
        boolean b = true;
    }

    private void b(int i, int n, int n2, int n3) {
        for (i = 0; i < n2; ++i) {
            for (int j = n; j < n3; ++j) {
                int n4;
                int[] a = this.a;
                int n5 = n4 = i + j * this.b;
                a[n5] = a[n5] | 0xFF000000;
            }
        }
    }
}

