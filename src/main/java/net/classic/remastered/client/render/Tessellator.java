package net.classic.remastered.client.render;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.BufferUtils;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;

public final class Tessellator {
    private static boolean convertQuadsToTriangles;
    private static boolean tryVBO;
    private ByteBuffer byteBuffer;
    private int[] rawBuffer;
    private int vertexCount;
    private float textureU;
    private float textureV;
    private int color;
    private boolean hasColor;
    private boolean hasTexture;
    private int rawBufferIndex;
    private int addedVertices;
    private boolean isColorDisabled;
    private int drawMode;
    public static Tessellator instance;
    private boolean isDrawing;
    private boolean useVBO;
    private IntBuffer vertexBuffers;
    private int vboIndex;
    private int vboCount;
    
    private Tessellator() {
        this.byteBuffer = BufferUtils.createByteBuffer(8388608);
        this.rawBuffer = new int[2097152];
        this.vertexCount = 0;
        this.hasColor = false;
        this.hasTexture = false;
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
        this.isColorDisabled = false;
        this.isDrawing = false;
        this.useVBO = false;
        this.vboIndex = 0;
        this.vboCount = 10;
        this.useVBO = false;
        if (this.useVBO) {
            ARBVertexBufferObject.glGenBuffersARB(this.vertexBuffers = BufferUtils.createIntBuffer(this.vboCount));
        }
    }
    
    public final void draw() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not tesselating!");
        }
        this.isDrawing = false;
        if (this.vertexCount > 0) {
            final IntBuffer intBuffer = this.byteBuffer.asIntBuffer();
            final FloatBuffer floatBuffer = this.byteBuffer.asFloatBuffer();
            intBuffer.clear();
            intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
            this.byteBuffer.position(0);
            this.byteBuffer.limit(this.rawBufferIndex << 2);
            if (this.useVBO) {
                this.vboIndex = (this.vboIndex + 1) % this.vboCount;
                ARBVertexBufferObject.glBindBufferARB(34962, this.vertexBuffers.get(this.vboIndex));
                ARBVertexBufferObject.glBufferDataARB(34962, this.byteBuffer, 35040);
            }
            if (this.hasTexture) {
                if (this.useVBO) {
                    GL11.glTexCoordPointer(2, 5126, 32, 12L);
                }
                else {
                    floatBuffer.position(3);
                    GL11.glTexCoordPointer(2, 32, floatBuffer);
                }
                GL11.glEnableClientState(32888);
            }
            if (this.hasColor) {
                if (this.useVBO) {
                    GL11.glColorPointer(4, 5121, 32, 20L);
                }
                else {
                    this.byteBuffer.position(20);
                    GL11.glColorPointer(4, true, 32, this.byteBuffer);
                }
                GL11.glEnableClientState(32886);
            }
            if (this.useVBO) {
                GL11.glVertexPointer(3, 5126, 32, 0L);
            }
            else {
                floatBuffer.position(0);
                GL11.glVertexPointer(3, 32, floatBuffer);
            }
            GL11.glEnableClientState(32884);
            GL11.glDrawArrays(this.drawMode, 0, this.vertexCount);
            GL11.glDisableClientState(32884);
            if (this.hasTexture) {
                GL11.glDisableClientState(32888);
            }
            if (this.hasColor) {
                GL11.glDisableClientState(32886);
            }
        }
        this.reset();
    }
    
    private void reset() {
        this.vertexCount = 0;
        this.byteBuffer.clear();
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
    }
    
    public final void startDrawingQuads() {
        this.startDrawing(7);
    }
    
    public final void startDrawing(final int integer) {

        this.isDrawing = true;
        this.reset();
        this.drawMode = integer;
        this.hasColor = false;
        this.hasTexture = false;
        this.isColorDisabled = false;
    }
    
    public final void setColorOpaque_F(final float float1, final float float2, final float float3) {
        this.setColorOpaque((int)(float1 * 255.0f), (int)(float2 * 255.0f), (int)(float3 * 255.0f));
    }
    
    public final void setColorRGBA_F(final float float1, final float float2, final float float3, final float float4) {
        this.setColorRGBA((int)(float1 * 255.0f), (int)(float2 * 255.0f), (int)(float3 * 255.0f), (int)(float4 * 255.0f));
    }
    
    private void setColorOpaque(final int integer1, final int integer2, final int integer3) {
        this.setColorRGBA(integer1, integer2, integer3, 255);
    }
    
    private void setColorRGBA(int integer1, int integer2, int integer3, int integer4) {
        if (this.isColorDisabled) {
            return;
        }
        if (integer1 > 255) {
            integer1 = 255;
        }
        if (integer2 > 255) {
            integer2 = 255;
        }
        if (integer3 > 255) {
            integer3 = 255;
        }
        if (integer4 > 255) {
            integer4 = 255;
        }
        if (integer1 < 0) {
            integer1 = 0;
        }
        if (integer2 < 0) {
            integer2 = 0;
        }
        if (integer3 < 0) {
            integer3 = 0;
        }
        if (integer4 < 0) {
            integer4 = 0;
        }
        this.hasColor = true;
        this.color = (integer4 << 24 | integer3 << 16 | integer2 << 8 | integer1);
    }
    
    public final void addVertexWithUV(final float float1, final float float2, final float float3, final float float4, float float5) {
        final float textureV = float5;
        float5 = float4;
        this.hasTexture = true;
        this.textureU = float5;
        this.textureV = textureV;
        this.addVertex(float1, float2, float3);
    }
    
    public final void addVertex(final float float1, final float float2, final float float3) {
        ++this.addedVertices;
        if (this.hasTexture) {
            this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits(this.textureU);
            this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits(this.textureV);
        }
        if (this.hasColor) {
            this.rawBuffer[this.rawBufferIndex + 5] = this.color;
        }
        this.rawBuffer[this.rawBufferIndex] = Float.floatToRawIntBits(float1);
        this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits(float2);
        this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits(float3);
        this.rawBufferIndex += 8;
        ++this.vertexCount;
        if (this.vertexCount % 4 == 0 && this.rawBufferIndex >= 2097120) {
            this.draw();
        }
    }
    
    public final void setColorOpaque_I(int integer) {
        final int integer2 = integer >> 16 & 0xFF;
        final int integer3 = integer >> 8 & 0xFF;
        integer &= 0xFF;
        this.setColorOpaque(integer2, integer3, integer);
    }
    
    public final void disableColor() {
        this.isColorDisabled = true;
    }
    
    public static void setNormal(final float float1, final float float2, final float float3) {
        GL11.glNormal3f(float1, float2, float3);
    }
    
    static {
        Tessellator.convertQuadsToTriangles = false;
        Tessellator.tryVBO = false;
        Tessellator.instance = new Tessellator();
    }
}
