/*
 * 
 */
package net.classic.remastered.game.entity.other;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;

public final class Tessenator {
    private static boolean b = false;
    private static boolean c = false;
    private ByteBuffer d = BufferUtils.createByteBuffer(0x800000);
    private int[] e = new int[0x200000];
    private int f = 0;
    private float g;
    private float h;
    private int i;
    private boolean j = false;
    private boolean k = false;
    private int l = 0;
    private int m = 0;
    private boolean n = false;
    private int o;
    public static Tessenator a = new Tessenator();
    private boolean p = false;
    private boolean q = false;
    private IntBuffer r;
    private int s = 0;
    private int t = 10;

    private Tessenator() {
        if (this.q) {
            this.r = BufferUtils.createIntBuffer(this.t);
            ARBVertexBufferObject.glGenBuffersARB(this.r);
        }
    }

    public final void a() {
        if (!this.p) {
            throw new IllegalStateException("Not tesselating!");
        }
        this.p = false;
        if (this.f > 0) {
            IntBuffer intBuffer = this.d.asIntBuffer();
            FloatBuffer floatBuffer = this.d.asFloatBuffer();
            intBuffer.clear();
            intBuffer.put(this.e, 0, this.l);
            this.d.position(0);
            this.d.limit(this.l << 2);
            if (this.q) {
                this.s = (this.s + 1) % this.t;
                ARBVertexBufferObject.glBindBufferARB(34962, this.r.get(this.s));
                ARBVertexBufferObject.glBufferDataARB(34962, this.d, 35040);
            }
            if (this.k) {
                if (this.q) {
                    GL11.glTexCoordPointer(2, 5126, 32, 12L);
                } else {
                    floatBuffer.position(3);
                    GL11.glTexCoordPointer(2, 32, floatBuffer);
                }
                GL11.glEnableClientState(32888);
            }
            if (this.j) {
                if (this.q) {
                    GL11.glColorPointer(4, 5121, 32, 20L);
                } else {
                    this.d.position(20);
                    GL11.glColorPointer(4, true, 32, this.d);
                }
                GL11.glEnableClientState(32886);
            }
            if (this.q) {
                GL11.glVertexPointer(3, 5126, 32, 0L);
            } else {
                floatBuffer.position(0);
                GL11.glVertexPointer(3, 32, floatBuffer);
            }
            GL11.glEnableClientState(32884);
            GL11.glDrawArrays(this.o, 0, this.f);
            GL11.glDisableClientState(32884);
            if (this.k) {
                GL11.glDisableClientState(32888);
            }
            if (this.j) {
                GL11.glDisableClientState(32886);
            }
        }
        this.d();
    }

    private void d() {
        this.f = 0;
        this.d.clear();
        this.l = 0;
        this.m = 0;
    }

    public final void b() {
        this.a(7);
    }

    public final void a(int o) {
        if (this.p) {
            throw new IllegalStateException("Already tesselating!");
        }
        this.p = true;
        this.d();
        this.o = o;
        this.j = false;
        this.k = false;
        this.n = false;
    }

    public final void draw(float n, float n2, float n3) {
        this.a((int)(n * 255.0f), (int)(n2 * 255.0f), (int)(n3 * 255.0f));
    }

    public final void a(float n, float n2, float n3, float n4) {
        this.a((int)(n * 255.0f), (int)(n2 * 255.0f), (int)(n3 * 255.0f), (int)(n4 * 255.0f));
    }

    private void a(int n, int n2, int n3) {
        this.a(n, n2, n3, 255);
    }

    private void a(int n, int n2, int n3, int n4) {
        if (this.n) {
            return;
        }
        if (n > 255) {
            n = 255;
        }
        if (n2 > 255) {
            n2 = 255;
        }
        if (n3 > 255) {
            n3 = 255;
        }
        if (n4 > 255) {
            n4 = 255;
        }
        if (n < 0) {
            n = 0;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        if (n3 < 0) {
            n3 = 0;
        }
        if (n4 < 0) {
            n4 = 0;
        }
        this.j = true;
        this.i = n4 << 24 | n3 << 16 | n2 << 8 | n;
    }

    public final void draw(float n, float n2, float n3, float n4, float g) {
        float h = g;
        g = n4;
        this.k = true;
        this.g = g;
        this.h = h;
        this.b(n, n2, n3);
    }

    public final void b(float n, float n2, float n3) {
        ++this.m;
        if (this.k) {
            this.e[this.l + 3] = Float.floatToRawIntBits(this.g);
            this.e[this.l + 4] = Float.floatToRawIntBits(this.h);
        }
        if (this.j) {
            this.e[this.l + 5] = this.i;
        }
        this.e[this.l] = Float.floatToRawIntBits(n);
        this.e[this.l + 1] = Float.floatToRawIntBits(n2);
        this.e[this.l + 2] = Float.floatToRawIntBits(n3);
        this.l += 8;
        ++this.f;
        if (this.f % 4 == 0 && this.l >= 2097120) {
            this.a();
        }
    }

    public final void b(int n) {
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        this.a(n2, n3, n &= 0xFF);
    }

    public final void c() {
        this.n = true;
    }

    public static void c(float n, float n2, float n3) {
        GL11.glNormal3f(n, n2, n3);
    }
}

