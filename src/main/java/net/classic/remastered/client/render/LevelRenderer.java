/*
 * 
 */
package net.classic.remastered.client.render;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.render.Chunk;
import net.classic.remastered.client.render.ChunkDistanceComparator;
import net.classic.remastered.client.render.ShapeRenderer;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;

public final class LevelRenderer {
    public World level;
    public TextureManager textureManager;
    public int listId;
    public IntBuffer buffer = BufferUtils.createIntBuffer(65536);
    public List chunks = new ArrayList();
    private Chunk[] loadQueue;
    public Chunk[] chunkCache;
    private int xChunks;
    private int yChunks;
    private int zChunks;
    private int baseListId;
    public Minecraft minecraft;
    private int[] chunkDataCache = new int[50000];
    public int ticks = 0;
    private float lastLoadX = -9999.0f;
    private float lastLoadY = -9999.0f;
    private float lastLoadZ = -9999.0f;
    public float cracks;

    public LevelRenderer(Minecraft var1, TextureManager var2) {
        this.minecraft = var1;
        this.textureManager = var2;
        this.listId = GL11.glGenLists(2);
        this.baseListId = GL11.glGenLists(524288);
    }

    public final void refresh() {
        int var7;
        int var4;
        int var2;
        int var1;
        if (this.chunkCache != null) {
            for (var1 = 0; var1 < this.chunkCache.length; ++var1) {
                this.chunkCache[var1].dispose();
            }
        }
        this.xChunks = this.level.width / 16;
        this.yChunks = this.level.depth / 16;
        this.zChunks = this.level.height / 16;
        this.chunkCache = new Chunk[this.xChunks * this.yChunks * this.zChunks];
        this.loadQueue = new Chunk[this.xChunks * this.yChunks * this.zChunks];
        var1 = 0;
        for (var2 = 0; var2 < this.xChunks; ++var2) {
            for (int var3 = 0; var3 < this.yChunks; ++var3) {
                for (var4 = 0; var4 < this.zChunks; ++var4) {
                    this.chunkCache[(var4 * this.yChunks + var3) * this.xChunks + var2] = new Chunk(this.level, var2 << 4, var3 << 4, var4 << 4, 16, this.baseListId + var1);
                    this.loadQueue[(var4 * this.yChunks + var3) * this.xChunks + var2] = this.chunkCache[(var4 * this.yChunks + var3) * this.xChunks + var2];
                    var1 += 2;
                }
            }
        }
        for (var2 = 0; var2 < this.chunks.size(); ++var2) {
            ((Chunk)this.chunks.get((int)var2)).loaded = false;
        }
        this.chunks.clear();
        GL11.glNewList(this.listId, 4864);
        LevelRenderer var9 = this;
        float var10 = 0.5f;
        GL11.glColor4f(0.5f, var10, var10, 1.0f);
        ShapeRenderer var11 = ShapeRenderer.instance;
        float var12 = this.level.getGroundLevel();
        int var5 = 128;
        if (128 > this.level.width) {
            var5 = this.level.width;
        }
        if (var5 > this.level.height) {
            var5 = this.level.height;
        }
        int var6 = 2048 / var5;
        var11.begin();
        for (var7 = -var5 * var6; var7 < var9.level.width + var5 * var6; var7 += var5) {
            for (int var8 = -var5 * var6; var8 < var9.level.height + var5 * var6; var8 += var5) {
                var10 = var12;
                if (var7 >= 0 && var8 >= 0 && var7 < var9.level.width && var8 < var9.level.height) {
                    var10 = 0.0f;
                }
                var11.vertexUV(var7, var10, var8 + var5, 0.0f, var5);
                var11.vertexUV(var7 + var5, var10, var8 + var5, var5, var5);
                var11.vertexUV(var7 + var5, var10, var8, var5, 0.0f);
                var11.vertexUV(var7, var10, var8, 0.0f, 0.0f);
            }
        }
        var11.end();
        GL11.glColor3f(0.8f, 0.8f, 0.8f);
        var11.begin();
        for (var7 = 0; var7 < var9.level.width; var7 += var5) {
            var11.vertexUV(var7, 0.0f, 0.0f, 0.0f, 0.0f);
            var11.vertexUV(var7 + var5, 0.0f, 0.0f, var5, 0.0f);
            var11.vertexUV(var7 + var5, var12, 0.0f, var5, var12);
            var11.vertexUV(var7, var12, 0.0f, 0.0f, var12);
            var11.vertexUV(var7, var12, var9.level.height, 0.0f, var12);
            var11.vertexUV(var7 + var5, var12, var9.level.height, var5, var12);
            var11.vertexUV(var7 + var5, 0.0f, var9.level.height, var5, 0.0f);
            var11.vertexUV(var7, 0.0f, var9.level.height, 0.0f, 0.0f);
        }
        GL11.glColor3f(0.6f, 0.6f, 0.6f);
        for (var7 = 0; var7 < var9.level.height; var7 += var5) {
            var11.vertexUV(0.0f, var12, var7, 0.0f, 0.0f);
            var11.vertexUV(0.0f, var12, var7 + var5, var5, 0.0f);
            var11.vertexUV(0.0f, 0.0f, var7 + var5, var5, var12);
            var11.vertexUV(0.0f, 0.0f, var7, 0.0f, var12);
            var11.vertexUV(var9.level.width, 0.0f, var7, 0.0f, var12);
            var11.vertexUV(var9.level.width, 0.0f, var7 + var5, var5, var12);
            var11.vertexUV(var9.level.width, var12, var7 + var5, var5, 0.0f);
            var11.vertexUV(var9.level.width, var12, var7, 0.0f, 0.0f);
        }
        var11.end();
        GL11.glEndList();
        GL11.glNewList(this.listId + 1, 4864);
        var9 = this;
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        var10 = this.level.getWaterLevel();
        GL11.glBlendFunc(770, 771);
        var11 = ShapeRenderer.instance;
        var4 = 128;
        if (128 > this.level.width) {
            var4 = this.level.width;
        }
        if (var4 > this.level.height) {
            var4 = this.level.height;
        }
        var5 = 2048 / var4;
        var11.begin();
        for (var6 = -var4 * var5; var6 < var9.level.width + var4 * var5; var6 += var4) {
            for (var7 = -var4 * var5; var7 < var9.level.height + var4 * var5; var7 += var4) {
                float var13 = var10 - 0.1f;
                if (var6 >= 0 && var7 >= 0 && var6 < var9.level.width && var7 < var9.level.height) continue;
                var11.vertexUV(var6, var13, var7 + var4, 0.0f, var4);
                var11.vertexUV(var6 + var4, var13, var7 + var4, var4, var4);
                var11.vertexUV(var6 + var4, var13, var7, var4, 0.0f);
                var11.vertexUV(var6, var13, var7, 0.0f, 0.0f);
                var11.vertexUV(var6, var13, var7, 0.0f, 0.0f);
                var11.vertexUV(var6 + var4, var13, var7, var4, 0.0f);
                var11.vertexUV(var6 + var4, var13, var7 + var4, var4, var4);
                var11.vertexUV(var6, var13, var7 + var4, 0.0f, var4);
            }
        }
        var11.end();
        GL11.glDisable(3042);
        GL11.glEndList();
        this.queueChunks(0, 0, 0, this.level.width, this.level.depth, this.level.height);
    }

    public final int sortChunks(Player var1, int var2) {
        float var3 = var1.x - this.lastLoadX;
        float var4 = var1.y - this.lastLoadY;
        float var5 = var1.z - this.lastLoadZ;
        if (var3 * var3 + var4 * var4 + var5 * var5 > 64.0f) {
            this.lastLoadX = var1.x;
            this.lastLoadY = var1.y;
            this.lastLoadZ = var1.z;
            Arrays.sort(this.loadQueue, new ChunkDistanceComparator(var1));
        }
        int var6 = 0;
        for (int var7 = 0; var7 < this.loadQueue.length; ++var7) {
            var6 = this.loadQueue[var7].appendLists(this.chunkDataCache, var6, var2);
        }
        this.buffer.clear();
        this.buffer.put(this.chunkDataCache, 0, var6);
        this.buffer.flip();
        if (this.buffer.remaining() > 0) {
            GL11.glBindTexture(3553, this.textureManager.load("/terrain.png"));
            GL11.glCallLists(this.buffer);
        }
        return this.buffer.remaining();
    }

    public final void queueChunks(int var1, int var2, int var3, int var4, int var5, int var6) {
        var2 /= 16;
        var3 /= 16;
        var4 /= 16;
        var5 /= 16;
        var6 /= 16;
        if ((var1 /= 16) < 0) {
            var1 = 0;
        }
        if (var2 < 0) {
            var2 = 0;
        }
        if (var3 < 0) {
            var3 = 0;
        }
        if (var4 > this.xChunks - 1) {
            var4 = this.xChunks - 1;
        }
        if (var5 > this.yChunks - 1) {
            var5 = this.yChunks - 1;
        }
        if (var6 > this.zChunks - 1) {
            var6 = this.zChunks - 1;
        }
        while (var1 <= var4) {
            for (int var7 = var2; var7 <= var5; ++var7) {
                for (int var8 = var3; var8 <= var6; ++var8) {
                    Chunk var9 = this.chunkCache[(var8 * this.yChunks + var7) * this.xChunks + var1];
                    if (var9.loaded) continue;
                    var9.loaded = true;
                    this.chunks.add(this.chunkCache[(var8 * this.yChunks + var7) * this.xChunks + var1]);
                }
            }
            ++var1;
        }
    }
}

