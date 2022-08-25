package net.classic.remastered.game.world;

import java.io.*;
import java.util.*;

import net.classic.remastered.client.model.*;
import net.classic.remastered.client.render.*;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.phys.*;

public class BlockMap implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width;
    private int depth;
    private int height;
    private BlockMap$Slot slot;
    private BlockMap$Slot slot2;
    @SuppressWarnings("rawtypes")
	public List[] entityGrid;
    public List<Entity> all;
    private List<Entity> tmp;
    
    public BlockMap(int var1, int var2, int var3) {
        this.slot = new BlockMap$Slot(this, null);
        this.slot2 = new BlockMap$Slot(this, null);
        this.all = new ArrayList<Entity>();
        this.tmp = new ArrayList<Entity>();
        this.width = var1 / 16;
        this.depth = var2 / 16;
        this.height = var3 / 16;
        if (this.width == 0) {
            this.width = 1;
        }
        if (this.depth == 0) {
            this.depth = 1;
        }
        if (this.height == 0) {
            this.height = 1;
        }
        this.entityGrid = new ArrayList[this.width * this.depth * this.height];
        for (var1 = 0; var1 < this.width; ++var1) {
            for (var2 = 0; var2 < this.depth; ++var2) {
                for (var3 = 0; var3 < this.height; ++var3) {
                    this.entityGrid[(var3 * this.depth + var2) * this.width + var1] = new ArrayList<Object>();
                }
            }
        }
    }
    
    public void insert(final Entity var1) {
        this.all.add(var1);
        this.slot.init(var1.x, var1.y, var1.z).add(var1);
        var1.xOld = var1.x;
        var1.yOld = var1.y;
        var1.zOld = var1.z;
        var1.blockMap = this;
    }
    
    public void remove(final Entity var1) {
        this.slot.init(var1.xOld, var1.yOld, var1.zOld).remove(var1);
        this.all.remove(var1);
    }
    
    public void moved(final Entity var1) {
        final BlockMap$Slot var2 = this.slot.init(var1.xOld, var1.yOld, var1.zOld);
        final BlockMap$Slot var3 = this.slot2.init(var1.x, var1.y, var1.z);
        if (!var2.equals(var3)) {
            var2.remove(var1);
            var3.add(var1);
            var1.xOld = var1.x;
            var1.yOld = var1.y;
            var1.zOld = var1.z;
        }
    }
    
    public List<Entity> getEntities(final Entity var1, final float var2, final float var3, final float var4, final float var5, final float var6, final float var7) {
        this.tmp.clear();
        return this.getEntities(var1, var2, var3, var4, var5, var6, var7, this.tmp);
    }
    
    public List<Entity> getEntities(final Entity var1, final float var2, final float var3, final float var4, final float var5, final float var6, final float var7, final List<Entity> var8) {
        final BlockMap$Slot var9 = this.slot.init(var2, var3, var4);
        final BlockMap$Slot var10 = this.slot2.init(var5, var6, var7);
        for (int var11 = BlockMap$Slot.getXSlot(var9) - 1; var11 <= BlockMap$Slot.getXSlot(var10) + 1; ++var11) {
            for (int var12 = BlockMap$Slot.getYSlot(var9) - 1; var12 <= BlockMap$Slot.getYSlot(var10) + 1; ++var12) {
                for (int var13 = BlockMap$Slot.getZSlot(var9) - 1; var13 <= BlockMap$Slot.getZSlot(var10) + 1; ++var13) {
                    if (var11 >= 0 && var12 >= 0 && var13 >= 0 && var11 < this.width && var12 < this.depth && var13 < this.height) {
                        final List<?> var14 = this.entityGrid[(var13 * this.depth + var12) * this.width + var11];
                        for (int var15 = 0; var15 < var14.size(); ++var15) {
                            final Entity var16;
                            if ((var16 = (Entity) var14.get(var15)) != var1 && var16.intersects(var2, var3, var4, var5, var6, var7)) {
                                var8.add(var16);
                            }
                        }
                    }
                }
            }
        }
        return var8;
    }
    
    public void removeAllNonCreativeModeEntities() {
        for (int var1 = 0; var1 < this.width; ++var1) {
            for (int var2 = 0; var2 < this.depth; ++var2) {
                for (int var3 = 0; var3 < this.height; ++var3) {
                    final List<?> var4 = this.entityGrid[(var3 * this.depth + var2) * this.width + var1];
                    for (int var5 = 0; var5 < var4.size(); ++var5) {
                        if (!((Entity) var4.get(var5)).isCreativeModeAllowed()) {
                            var4.remove(var5--);
                        }
                    }
                }
            }
        }
    }
    
    public void clear() {
        for (int var1 = 0; var1 < this.width; ++var1) {
            for (int var2 = 0; var2 < this.depth; ++var2) {
                for (int var3 = 0; var3 < this.height; ++var3) {
                    this.entityGrid[(var3 * this.depth + var2) * this.width + var1].clear();
                }
            }
        }
    }
    
    public List<Entity> getEntities(final Entity var1, final AABB var2) {
        this.tmp.clear();
        return this.getEntities(var1, var2.x0, var2.y0, var2.z0, var2.x1, var2.y1, var2.z1, this.tmp);
    }
    
    public List<Entity> getEntities(final Entity var1, final AABB var2, final List<Entity> var3) {
        return this.getEntities(var1, var2.x0, var2.y0, var2.z0, var2.x1, var2.y1, var2.z1, var3);
    }
    
    public void tickAll() {
        for (int var1 = 0; var1 < this.all.size(); ++var1) {
            final Entity var2;
            (var2 = this.all.get(var1)).tick();
            if (var2.removed) {
                this.all.remove(var1--);
                this.slot.init(var2.xOld, var2.yOld, var2.zOld).remove(var2);
            }
            else {
                final int var3 = (int)(var2.xOld / 16.0f);
                final int var4 = (int)(var2.yOld / 16.0f);
                final int var5 = (int)(var2.zOld / 16.0f);
                final int var6 = (int)(var2.x / 16.0f);
                final int var7 = (int)(var2.y / 16.0f);
                final int var8 = (int)(var2.z / 16.0f);
                if (var3 != var6 || var4 != var7 || var5 != var8) {
                    this.moved(var2);
                }
            }
        }
    }
    
    @SuppressWarnings("unused")
	public void render(final Vec3D var1, final Frustrum var2, final TextureManager var3, final float var4) {
        for (int var5 = 0; var5 < this.width; ++var5) {
            final float var6 = (float)((var5 << 4) - 2);
            final float var7 = (float)((var5 + 1 << 4) + 2);
            for (int var8 = 0; var8 < this.depth; ++var8) {
                final float var9 = (float)((var8 << 4) - 2);
                final float var10 = (float)((var8 + 1 << 4) + 2);
                List<?> var12;
                float var13;
                float var14;
                float var15;
                float var16;
                float var17;
                float var18;
                Frustrum var19;
                int var20;
                boolean var21;
                boolean var22;
                Entity var24;
                AABB var25;
                Label_0684_Outer:Label_0791:
                for (int var11 = 0; var11 < this.height; ++var11) {
                    if ((var12 = this.entityGrid[(var11 * this.depth + var8) * this.width + var5]).size() != 0) {
                        var13 = (float)((var11 << 4) - 2);
                        var14 = (float)((var11 + 1 << 4) + 2);
                        if (var2.isBoxInFrustrum(var6, var9, var13, var7, var10, var14)) {
                            var15 = var14;
                            var16 = var10;
                            var17 = var7;
                            var14 = var13;
                            var13 = var9;
                            var18 = var6;
                            var19 = var2;
                            var20 = 0;
                            while (true) {
                                while (var20 < 6) {
                                    if (var19.frustrum[var20][0] * var18 + var19.frustrum[var20][1] * var13 + var19.frustrum[var20][2] * var14 + var19.frustrum[var20][3] <= 0.0f) {
                                        var21 = false;
                                    }
                                    else if (var19.frustrum[var20][0] * var17 + var19.frustrum[var20][1] * var13 + var19.frustrum[var20][2] * var14 + var19.frustrum[var20][3] <= 0.0f) {
                                        var21 = false;
                                    }
                                    else if (var19.frustrum[var20][0] * var18 + var19.frustrum[var20][1] * var16 + var19.frustrum[var20][2] * var14 + var19.frustrum[var20][3] <= 0.0f) {
                                        var21 = false;
                                    }
                                    else if (var19.frustrum[var20][0] * var17 + var19.frustrum[var20][1] * var16 + var19.frustrum[var20][2] * var14 + var19.frustrum[var20][3] <= 0.0f) {
                                        var21 = false;
                                    }
                                    else if (var19.frustrum[var20][0] * var18 + var19.frustrum[var20][1] * var13 + var19.frustrum[var20][2] * var15 + var19.frustrum[var20][3] <= 0.0f) {
                                        var21 = false;
                                    }
                                    else if (var19.frustrum[var20][0] * var17 + var19.frustrum[var20][1] * var13 + var19.frustrum[var20][2] * var15 + var19.frustrum[var20][3] <= 0.0f) {
                                        var21 = false;
                                    }
                                    else if (var19.frustrum[var20][0] * var18 + var19.frustrum[var20][1] * var16 + var19.frustrum[var20][2] * var15 + var19.frustrum[var20][3] <= 0.0f) {
                                        var21 = false;
                                    }
                                    else {
                                        if (var19.frustrum[var20][0] * var17 + var19.frustrum[var20][1] * var16 + var19.frustrum[var20][2] * var15 + var19.frustrum[var20][3] > 0.0f) {
                                            ++var20;
                                        }
                                        var21 = false;
                                    }
                                    var22 = var21;
                                    for (int var23 = 0; var23 < var12.size(); ++var23) {
                                        if ((var24 = (Entity) var12.get(var23)).shouldRender(var1)) {
                                            if (!var22) {
                                                var25 = var24.bb;
                                                if (!var2.isBoxInFrustrum(var25.x0, var25.y0, var25.z0, var25.x1, var25.y1, var25.z1)) {
                                                    continue;
                                                }
                                            }
                                            var24.render(var3, var4);
                                        }
                                    }
                                    continue Label_0791;
                                }
                                var21 = true;
                                continue;
                            }
                        }
                    }
                }
            }
        }
    }
    
    static int getWidth(final BlockMap var0) {
        return var0.width;
    }
    
    static int getDepth(final BlockMap var0) {
        return var0.depth;
    }
    
    static int getHeight(final BlockMap var0) {
        return var0.height;
    }
}
