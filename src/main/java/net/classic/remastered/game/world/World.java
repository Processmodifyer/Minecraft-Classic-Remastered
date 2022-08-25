package net.classic.remastered.game.world;

import java.io.*;
import java.util.*;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.model.*;
import net.classic.remastered.client.particle.*;
import net.classic.remastered.client.render.*;
import net.classic.remastered.client.sound.*;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.monster.*;
import net.classic.remastered.game.phys.*;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.liquid.*;
import net.classic.remastered.game.world.tile.*;
import net.dungland.util.*;
import net.classic.remastered.game.world.item.*;
public class World implements Serializable
{
	private static final long serialVersionUID = 1L;
	public int width;
    public int height;
    public int depth;
    public byte[] blocks;
    public String name;
    public String creator;
    public long createTime;
    public static int xSpawn;
    public static int ySpawn;
    public static int zSpawn;
    public float rotSpawn;
    private transient ArrayList<LevelRenderer> listeners;
    private transient int[] blockers;
    public transient Random random;
    private transient int randId;
    private transient ArrayList<NextTickListEntry> tickList;
    public BlockMap blockMap;
    private boolean networkMode;
    public transient Minecraft rendererContext$5cd64a7f;
    public boolean creativeMode;
    public boolean adventureMode;
    public boolean Item;
    public int waterLevel;
    public int skyColor;
    public int fogColor;
    public int cloudColor;
    int unprocessed;
    private int tickCount;
    public Entity player;
    public transient ParticleManager particleEngine;
    public transient Object font;
    public boolean growTrees;
    private Entity mob;
    public String inv;
    private Skeleton skeleton;
    public int cloudLevel;
    public Item item;
    public int timeOfDay;
    private int worldInt1;
    private int worldInt2;
    public int skyBrightness;
    public int skylightSubtracted;
	public int length;

    
    public World() {
        this.listeners = new ArrayList<LevelRenderer>();
        this.random = new Random();
        this.randId = this.random.nextInt();
        this.tickList = new ArrayList<NextTickListEntry>();
        this.networkMode = false;
        this.unprocessed = 0;
        this.tickCount = 0;
        this.growTrees = false;
        this.timeOfDay = 0;
    }
    
    public void initTransient() {
        if (this.blocks == null) {
            throw new RuntimeException("The level is corrupt!");
        }
        this.listeners = new ArrayList<LevelRenderer>();
        Arrays.fill(this.blockers = new int[this.width * this.height], this.depth);
        this.calcLightDepths(0, 0, this.width, this.height);
        this.random = new Random();
        this.randId = this.random.nextInt();
        this.tickList = new ArrayList<NextTickListEntry>();
        if (this.waterLevel == 0) {
            this.waterLevel = this.depth / 2;
        }
        if(this.skyColor == 0) {
            this.random = random;
         }

         if(this.fogColor == 0) {
            this.fogColor = 16777215;
         }

         if(this.cloudColor == 0) {
            this.cloudColor = 16777215;
         }
        if (World.xSpawn == 0 && World.ySpawn == 0 && World.zSpawn == 0) {
            this.findSpawn();
        }
        if (this.blockMap == null) {
            this.blockMap = new BlockMap(this.width, this.depth, this.height);
        }
    }
    
    
    public void playSound2(final String var1, final Entity var2, final float var3, final float var4) {
        if (this.rendererContext$5cd64a7f != null) {
            final Minecraft var5;
            if ((var5 = this.rendererContext$5cd64a7f).soundPlayer == null || !var5.settings.sound) {
                return;
            }
            final AudioInfo var6;
            if (var2.distanceToSqr(var5.player) < 1024.0f && (var6 = var5.sound.getAudioInfo(var1, var3, var4)) != null) {
                var5.soundPlayer.play(var6, new EntitySoundPos(var2, var5.player));
            }
        }
    }
    
    public void playSound1(final String var1, final float var2, final float var3, final float var4, final float var5, final float var6) {
        if (this.rendererContext$5cd64a7f != null) {
            final Minecraft var7;
            if ((var7 = this.rendererContext$5cd64a7f).soundPlayer == null || !var7.settings.sound) {
                return;
            }
            final AudioInfo var8;
            if ((var8 = var7.sound.getAudioInfo(var1, var5, var6)) != null) {
                var7.soundPlayer.play(var8, new LevelSoundPos(var2, var3, var4, var7.player));
            }
        }
    }
    
    public void setData(int var1, final int var2, final int var3, final byte[] var4) {
        this.width = var1;
        this.height = var3;
        this.depth = var2;
        this.blocks = var4;
        Arrays.fill(this.blockers = new int[var1 * var3], this.depth);
        this.calcLightDepths(0, 0, var1, var3);
        for (var1 = 0; var1 < this.listeners.size(); ++var1) {
            ((LevelRenderer) this.listeners.get(var1)).refresh();
        }
        this.tickList.clear();
        this.findSpawn();
        this.initTransient();
        System.gc();
    }
    
    public void findSpawn() {
        final Random var1 = new Random();
        int var2 = 0;
        int var3;
        int var4;
        int var5;
        do {
            ++var2;
            var4 = var1.nextInt(this.width / 2) + this.width / 4;
            var5 = var1.nextInt(this.height / 2) + this.height / 4;
            var3 = this.getHighestTile(var4, var5) + 1;
            if (var2 == 10000) {
                World.xSpawn = var4;
                World.ySpawn = -100;
                World.zSpawn = var5;
                return;
            }
        } while (var3 <= this.getWaterLevel());
        World.xSpawn = var4;
        World.ySpawn = var3;
        World.zSpawn = var5;
    }
    
    public void calcLightDepths(final int var1, final int var2, final int var3, final int var4) {
        for (int var5 = var1; var5 < var1 + var3; ++var5) {
            for (int var6 = var2; var6 < var2 + var4; ++var6) {
                int var7 = this.blockers[var5 + var6 * this.width];
                int var8;
                for (var8 = this.depth - 1; var8 > 0 && !this.isLightBlocker(var5, var8, var6); --var8) {}
                if (var7 != (this.blockers[var5 + var6 * this.width] = var8)) {
                    final int var9 = (var7 < var8) ? var7 : var8;
                    var7 = ((var7 > var8) ? var7 : var8);
                    for (var8 = 0; var8 < this.listeners.size(); ++var8) {
                        ((LevelRenderer) this.listeners.get(var8)).queueChunks(var5 - 1, var9 - 1, var6 - 1, var5 + 1, var7 + 1, var6 + 1);
                    }
                }
            }
        }
    }
    
    public void addListener(final LevelRenderer var1) {
        this.listeners.add(var1);
    }
    
    public void finalize() {
    }
    
    public void removeListener(final LevelRenderer var1) {
        this.listeners.remove(var1);
    }
    
    public boolean isLightBlocker(final int var1, final int var2, final int var3) {
        final Block var4;
        return (var4 = Block.blocks[this.getTile(var1, var2, var3)]) != null && var4.isOpaque();
    }
    
    public ArrayList<AABB> getCubes(final AABB var1) {
        final ArrayList<AABB> var2 = new ArrayList<AABB>();
        int var3 = (int)var1.x0;
        final int var4 = (int)var1.x1 + 1;
        int var5 = (int)var1.y0;
        final int var6 = (int)var1.y1 + 1;
        int var7 = (int)var1.z0;
        final int var8 = (int)var1.z1 + 1;
        if (var1.x0 < 0.0f) {
            --var3;
        }
        if (var1.y0 < 0.0f) {
            --var5;
        }
        if (var1.z0 < 0.0f) {
            --var7;
        }
        for (var3 = var3; var3 < var4; ++var3) {
            for (int var9 = var5; var9 < var6; ++var9) {
                for (int var10 = var7; var10 < var8; ++var10) {
                    if (var3 >= 0 && var9 >= 0 && var10 >= 0 && var3 < this.width && var9 < this.depth && var10 < this.height) {
                        final Block var11;
                        final AABB var12;
                        if ((var11 = Block.blocks[this.getTile(var3, var9, var10)]) != null && (var12 = var11.getCollisionBox(var3, var9, var10)) != null && var1.intersectsInner(var12)) {
                            var2.add(var12);
                        }
                    }
                    else {
                        final AABB var12;
                        if ((var3 < 0 || var9 < 0 || var10 < 0 || var3 >= this.width || var10 >= this.height) && (var12 = Block.BEDROCK.getCollisionBox(var3, var9, var10)) != null && var1.intersectsInner(var12)) {
                            var2.add(var12);
                        }
                    }
                }
            }
        }
        return var2;
    }
    
    public void swap(final int var1, final int var2, final int var3, final int var4, final int var5, final int var6) {
        if (!this.networkMode) {
            final int var7 = this.getTile(var1, var2, var3);
            final int var8 = this.getTile(var4, var5, var6);
            this.setTileNoNeighborChange(var1, var2, var3, var8);
            this.setTileNoNeighborChange(var4, var5, var6, var7);
            this.updateNeighborsAt(var1, var2, var3, var8);
            this.updateNeighborsAt(var4, var5, var6, var7);
        }
    }
    
    public boolean setTileNoNeighborChange(final int var1, final int var2, final int var3, final int var4) {
        return !this.networkMode && this.netSetTileNoNeighborChange(var1, var2, var3, var4);
    }
    public final void restartTimeOfDay() {
        ++this.timeOfDay;
        if (this.timeOfDay == 24000) {
            this.timeOfDay = 0;
        }

        ++this.worldInt2;
        skyBrightness = 1;
        int n = 1;
        while (1 << skyBrightness < this.width) {
            ++skyBrightness;
        }
        while (1 << n < this.length) {
            ++n;
        }
        final int n2 = this.length - 1;
        final int n3 = this.width - 1;
        final int n4 = this.height - 1;
        int size;
        if ((size = this.tickList.size()) > 200) {
            size = 200;
        }
        for (int i = 0; i < size; ++i) {
            final NextTickListEntry nextTickListEntry;
            if ((nextTickListEntry = (NextTickListEntry)this.tickList.remove(0)).ticks > 0) {
                final NextTickListEntry nextTickListEntry2 = nextTickListEntry;
                --nextTickListEntry2.ticks;
                this.tickList.add(nextTickListEntry);
            }
            else {
                final int x = nextTickListEntry.x;
                final int y = nextTickListEntry.y;
                final int z = nextTickListEntry.z;
                final int n5 = y;
                final int zCoord = x;
                final byte b;
                if (zCoord >= 0 && n5 >= 0 && z >= 0 && zCoord < this.width && n5 < this.height && z < this.length && (b = this.blocks[(nextTickListEntry.y * this.length + nextTickListEntry.z) * this.width + nextTickListEntry.x]) == nextTickListEntry.block && b > 0) {
                    Block.blocks[b].update(this, nextTickListEntry.x, nextTickListEntry.y, nextTickListEntry.z, this.random);
                }
            }
        }
        this.worldInt1 += this.width * this.length * this.height;
        size = this.worldInt1 / 200;
        this.worldInt1 -= size * 200;
        for (int i = 0; i < size; ++i) {
            this.randId = this.randId * 3 + 1013904223;
            int yCoord;
            final int xCoord = (yCoord = this.randId >> 2) & n3;
            final int zCoord = yCoord >> skyBrightness & n2;
            yCoord = (yCoord >> skyBrightness + n & n4);
            final byte b2 = this.blocks[(yCoord * this.length + zCoord) * this.width + xCoord];
            if (Block.tickOnLoad[b2]) {
                Block.blocks[b2].update(this, xCoord, yCoord, zCoord, this.random);
            }
        }
    }
    
    public boolean netSetTileNoNeighborChange(final int var1, final int var2, final int var3, int var4) {
        if (var1 < 0 || var2 < 0 || var3 < 0 || var1 >= this.width || var2 >= this.depth || var3 >= this.height) {
            return false;
        }
        if (var4 == this.blocks[(var2 * this.height + var3) * this.width + var1]) {
            return false;
        }
        if (var4 == 0 && (var1 == 0 || var3 == 0 || var1 == this.width - 1 || var3 == this.height - 1) && var2 >= this.getGroundLevel() && var2 < this.getWaterLevel()) {
            var4 = Block.WATER.id;
        }
        final byte var5 = this.blocks[(var2 * this.height + var3) * this.width + var1];
        this.blocks[(var2 * this.height + var3) * this.width + var1] = (byte)var4;
        if (var5 != 0) {
            Block.blocks[var5].onRemoved(this, var1, var2, var3);
        }
        if (var4 != 0) {
            Block.blocks[var4].onAdded(this, var1, var2, var3);
        }
        this.calcLightDepths(var1, var3, 1, 1);
        for (var4 = 0; var4 < this.listeners.size(); ++var4) {
            ((LevelRenderer) this.listeners.get(var4)).queueChunks(var1 - 1, var2 - 1, var3 - 1, var1 + 1, var2 + 1, var3 + 1);
        }
        return true;
    }
    
    public boolean setTile(final int var1, final int var2, final int var3, final int var4) {
        if (this.networkMode) {
            return false;
        }
        if (this.setTileNoNeighborChange(var1, var2, var3, var4)) {
            this.updateNeighborsAt(var1, var2, var3, var4);
            return true;
        }
        return false;
    }
    
    public boolean netSetTile(final int var1, final int var2, final int var3, final int var4) {
        if (this.netSetTileNoNeighborChange(var1, var2, var3, var4)) {
            this.updateNeighborsAt(var1, var2, var3, var4);
            return true;
        }
        return false;
    }
    
    public void updateNeighborsAt(final int var1, final int var2, final int var3, final int var4) {
        this.updateTile(var1 - 1, var2, var3, var4);
        this.updateTile(var1 + 1, var2, var3, var4);
        this.updateTile(var1, var2 - 1, var3, var4);
        this.updateTile(var1, var2 + 1, var3, var4);
        this.updateTile(var1, var2, var3 - 1, var4);
        this.updateTile(var1, var2, var3 + 1, var4);
    }
    
    public boolean setTileNoUpdate(final int var1, final int var2, final int var3, final int var4) {
        if (var1 < 0 || var2 < 0 || var3 < 0 || var1 >= this.width || var2 >= this.depth || var3 >= this.height) {
            return false;
        }
        if (var4 == this.blocks[(var2 * this.height + var3) * this.width + var1]) {
            return false;
        }
        this.blocks[(var2 * this.height + var3) * this.width + var1] = (byte)var4;
        return true;
    }
    
    private void updateTile(final int var1, final int var2, final int var3, final int var4) {
        final Block var5;
        if (var1 >= 0 && var2 >= 0 && var3 >= 0 && var1 < this.width && var2 < this.depth && var3 < this.height && (var5 = Block.blocks[this.blocks[(var2 * this.height + var3) * this.width + var1]]) != null) {
            var5.onNeighborChange(this, var1, var2, var3, var4);
        }
    }
    
    public boolean isLit(final int var1, final int var2, final int var3) {
        return var1 < 0 || var2 < 0 || var3 < 0 || var1 >= this.width || var2 >= this.depth || var3 >= this.height || var2 >= this.blockers[var1 + var3 * this.width];
    }
    
    public int getTile(final int var1, final int var2, final int var3) {
        return (var1 >= 0 && var2 >= 0 && var3 >= 0 && var1 < this.width && var2 < this.depth && var3 < this.height) ? (this.blocks[(var2 * this.height + var3) * this.width + var1] & 0xFF) : 0;
    }
    
    public boolean isSolidTile(final int var1, final int var2, final int var3) {
        final Block var4;
        return (var4 = Block.blocks[this.getTile(var1, var2, var3)]) != null && var4.isSolid();
    }
    
    public void tickEntities() {
        this.blockMap.tickAll();
    }
    
    public void tick() {
        ++this.tickCount;
        int var1 = 1;
        int var2 = 1;
        while (1 << var1 < this.width) {
            ++var1;
        }
        while (1 << var2 < this.height) {
            ++var2;
        }
        final int var3 = this.height - 1;
        final int var4 = this.width - 1;
        final int var5 = this.depth - 1;
        if (this.tickCount % 5 == 0) {
            for (int var6 = this.tickList.size(), var7 = 0; var7 < var6; ++var7) {
                final NextTickListEntry var8;
                if ((var8 = (NextTickListEntry) this.tickList.remove(0)).ticks > 0) {
                    final NextTickListEntry nextTickListEntry = var8;
                    --nextTickListEntry.ticks;
                    this.tickList.add(var8);
                }
                else {
                    final byte var9;
                    if (this.isInBounds(var8.x, var8.y, var8.z) && (var9 = this.blocks[(var8.y * this.height + var8.z) * this.width + var8.x]) == var8.block && var9 > 0) {
                        Block.blocks[var9].update(this, var8.x, var8.y, var8.z, this.random);
                    }
                }
            }
        }
        this.unprocessed += this.width * this.height * this.depth;
        final int var6 = this.unprocessed / 200;
        this.unprocessed -= var6 * 200;
        for (int var7 = 0; var7 < var6; ++var7) {
            this.randId = this.randId * 3 + 1013904223;
            int var11;
            final int var10 = (var11 = this.randId >> 2) & var4;
            final int var12 = var11 >> var1 & var3;
            var11 = (var11 >> var1 + var2 & var5);
            final byte var13 = this.blocks[(var11 * this.height + var12) * this.width + var10];
            if (Block.physics[var13]) {
                Block.blocks[var13].update(this, var10, var11, var12, this.random);
            }
        }
    }
    
    public int countInstanceOf(final Class<?> var1) {
        int var2 = 0;
        for (int var3 = 0; var3 < this.blockMap.all.size(); ++var3) {
            final Entity var4 = (Entity) this.blockMap.all.get(var3);
            if (var1.isAssignableFrom(var4.getClass())) {
                ++var2;
            }
        }
        return var2;
    }
    
    private boolean isInBounds(final int var1, final int var2, final int var3) {
        return var1 >= 0 && var2 >= 0 && var3 >= 0 && var1 < this.width && var2 < this.depth && var3 < this.height;
    }
    
    public float getGroundLevel() {
        return this.getWaterLevel() - 2.0f;
    }
    
    public float getWaterLevel() {
        return (float)this.waterLevel;
    }
    
    public boolean containsAnyLiquid(final AABB var1) {
        int var2 = (int)var1.x0;
        int var3 = (int)var1.x1 + 1;
        int var4 = (int)var1.y0;
        int var5 = (int)var1.y1 + 1;
        int var6 = (int)var1.z0;
        int var7 = (int)var1.z1 + 1;
        if (var1.x0 < 0.0f) {
            --var2;
        }
        if (var1.y0 < 0.0f) {
            --var4;
        }
        if (var1.z0 < 0.0f) {
            --var6;
        }
        if (var2 < 0) {
            var2 = 0;
        }
        if (var4 < 0) {
            var4 = 0;
        }
        if (var6 < 0) {
            var6 = 0;
        }
        if (var3 > this.width) {
            var3 = this.width;
        }
        if (var5 > this.depth) {
            var5 = this.depth;
        }
        if (var7 > this.height) {
            var7 = this.height;
        }
        for (int var8 = var2; var8 < var3; ++var8) {
            for (var2 = var4; var2 < var5; ++var2) {
                for (int var9 = var6; var9 < var7; ++var9) {
                    final Block var10;
                    if ((var10 = Block.blocks[this.getTile(var8, var2, var9)]) != null && var10.getLiquidType() != LiquidType.NOT_LIQUID) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean containsLiquid(final AABB var1, final LiquidType var2) {
        int var3 = (int)var1.x0;
        int var4 = (int)var1.x1 + 1;
        int var5 = (int)var1.y0;
        int var6 = (int)var1.y1 + 1;
        int var7 = (int)var1.z0;
        int var8 = (int)var1.z1 + 1;
        if (var1.x0 < 0.0f) {
            --var3;
        }
        if (var1.y0 < 0.0f) {
            --var5;
        }
        if (var1.z0 < 0.0f) {
            --var7;
        }
        if (var3 < 0) {
            var3 = 0;
        }
        if (var5 < 0) {
            var5 = 0;
        }
        if (var7 < 0) {
            var7 = 0;
        }
        if (var4 > this.width) {
            var4 = this.width;
        }
        if (var6 > this.depth) {
            var6 = this.depth;
        }
        if (var8 > this.height) {
            var8 = this.height;
        }
        for (int var9 = var3; var9 < var4; ++var9) {
            for (var3 = var5; var3 < var6; ++var3) {
                for (int var10 = var7; var10 < var8; ++var10) {
                    final Block var11;
                    if ((var11 = Block.blocks[this.getTile(var9, var3, var10)]) != null && var11.getLiquidType() == var2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void addToTickNextTick(final int var1, final int var2, int var3, final int var4) {
        if (!this.networkMode) {
            final NextTickListEntry var5 = new NextTickListEntry(var1, var2, var3, var4);
            if (var4 > 0) {
                var3 = Block.blocks[var4].getTickDelay();
                var5.ticks = var3;
            }
            this.tickList.add(var5);
        }
    }
    
    public boolean isFree(final AABB var1) {
        return this.blockMap.getEntities(null, var1).size() == 0;
    }
    
    public List<?> findEntities(final Entity var1, final AABB var2) {
        return this.blockMap.getEntities(var1, var2);
    }
    
    public boolean isSolid(final float var1, final float var2, final float var3, final float var4) {
        return this.isSolid(var1 - var4, var2 - var4, var3 - var4) || this.isSolid(var1 - var4, var2 - var4, var3 + var4) || this.isSolid(var1 - var4, var2 + var4, var3 - var4) || this.isSolid(var1 - var4, var2 + var4, var3 + var4) || this.isSolid(var1 + var4, var2 - var4, var3 - var4) || this.isSolid(var1 + var4, var2 - var4, var3 + var4) || this.isSolid(var1 + var4, var2 + var4, var3 - var4) || this.isSolid(var1 + var4, var2 + var4, var3 + var4);
    }
    
    private boolean isSolid(final float var1, final float var2, final float var3) {
        final int var4;
        return (var4 = this.getTile((int)var1, (int)var2, (int)var3)) > 0 && Block.blocks[var4].isSolid();
    }
    
    public int getHighestTile(final int var1, final int var2) {
        int var3;
        for (var3 = this.depth; (this.getTile(var1, var3 - 1, var2) == 0 || Block.blocks[this.getTile(var1, var3 - 1, var2)].getLiquidType() != LiquidType.NOT_LIQUID) && var3 > 0; --var3) {}
        return var3;
    }
    
    public void setSpawnPos(final int var1, final int var2, final int var3, final float var4) {
        World.xSpawn = var1;
        World.ySpawn = var2;
        World.zSpawn = var3;
        this.rotSpawn = var4;
    }
    
    public float getBrightness(final int var1, final int var2, final int var3) {
        return this.isLit(var1, var2, var3) ? 1.0f : 0.6f;
    }
    
    public float getCaveness(final float var1, final float var2, final float var3, final float var4) {
        final int var5 = (int)var1;
        final int var6 = (int)var2;
        final int var7 = (int)var3;
        float var8 = 0.0f;
        float var9 = 0.0f;
        for (int var10 = var5 - 6; var10 <= var5 + 6; ++var10) {
            for (int var11 = var7 - 6; var11 <= var7 + 6; ++var11) {
                if (this.isInBounds(var10, var6, var11) && !this.isSolidTile(var10, var6, var11)) {
                    float var12 = var10 + 0.5f - var1;
                    float var14;
                    float var13;
                    for (var13 = (float)(Math.atan2(var14 = var11 + 0.5f - var3, var12) - var4 * 3.1415927f / 180.0f + 1.5707963705062866); var13 < -3.1415927f; var13 += 6.2831855f) {}
                    while (var13 >= 3.1415927f) {
                        var13 -= 6.2831855f;
                    }
                    if (var13 < 0.0f) {
                        var13 = -var13;
                    }
                    var12 = MathHelper.sqrt(var12 * var12 + 4.0f + var14 * var14);
                    var12 = 1.0f / var12;
                    if (var13 > 1.0f) {
                        var12 = 0.0f;
                    }
                    if (var12 < 0.0f) {
                        var12 = 0.0f;
                    }
                    var9 += var12;
                    if (this.isLit(var10, var6, var11)) {
                        var8 += var12;
                    }
                }
            }
        }
        if (var9 == 0.0f) {
            return 0.0f;
        }
        return var8 / var9;
    }
    
    public float getCaveness(final Entity var1) {
        final float var2 = MathHelper.cos(-var1.yRot * 0.017453292f + 3.1415927f);
        final float var3 = MathHelper.sin(-var1.yRot * 0.017453292f + 3.1415927f);
        final float var4 = MathHelper.cos(-var1.xRot * 0.017453292f);
        final float var5 = MathHelper.sin(-var1.xRot * 0.017453292f);
        final float var6 = var1.x;
        final float var7 = var1.y;
        final float var8 = var1.z;
        final float var9 = 1.6f;
        float var10 = 0.0f;
        float var11 = 0.0f;
        for (int var12 = 0; var12 <= 200; ++var12) {
            final float var13 = (var12 / 200.0f - 0.5f) * 2.0f;
            for (int var14 = 0; var14 <= 200; ++var14) {
                float var15 = (var14 / 200.0f - 0.5f) * var9;
                float var16 = var4 * var15 + var5;
                var15 = var4 - var5 * var15;
                final float var17 = var2 * var13 + var3 * var15;
                var16 = var16;
                var15 = var2 * var15 - var3 * var13;
                for (int var18 = 0; var18 < 10; ++var18) {
                    final float var19 = var6 + var17 * var18 * 0.8f;
                    final float var20 = var7 + var16 * var18 * 0.8f;
                    final float var21 = var8 + var15 * var18 * 0.8f;
                    if (this.isSolid(var19, var20, var21)) {
                        break;
                    }
                    ++var10;
                    if (this.isLit((int)var19, (int)var20, (int)var21)) {
                        ++var11;
                    }
                }
            }
        }
        if (var10 == 0.0f) {
            return 0.0f;
        }
        float var22;
        if ((var22 = var11 / var10 / 0.1f) > 1.0f) {
            var22 = 1.0f;
        }
        var22 = 1.0f - var22;
        return 1.0f - var22 * var22 * var22;
    }
    
    public byte[] copyBlocks() {
        return Arrays.copyOf(this.blocks, this.blocks.length);
    }
    
    public LiquidType getLiquid(final int var1, final int var2, final int var3) {
        final int var4;
        return ((var4 = this.getTile(var1, var2, var3)) == 0) ? LiquidType.NOT_LIQUID : Block.blocks[var4].getLiquidType();
    }
    
    public boolean isWater(final int var1, final int var2, final int var3) {
        final int var4;
        return (var4 = this.getTile(var1, var2, var3)) > 0 && Block.blocks[var4].getLiquidType() == LiquidType.WATER;
    }
    
    public void setNetworkMode(final boolean var1) {
        this.networkMode = var1;
    }
    
    public MovingObjectPosition clip(final Vec3D var1, final Vec3D var2) {
        if (Float.isNaN(var1.x) || Float.isNaN(var1.y) || Float.isNaN(var1.z)) {
            return null;
        }
        if (!Float.isNaN(var2.x) && !Float.isNaN(var2.y) && !Float.isNaN(var2.z)) {
            final int var3 = (int)Math.floor(var2.x);
            final int var4 = (int)Math.floor(var2.y);
            final int var5 = (int)Math.floor(var2.z);
            int var6 = (int)Math.floor(var1.x);
            int var7 = (int)Math.floor(var1.y);
            int var8 = (int)Math.floor(var1.z);
            int var9 = 20;
            while (var9-- >= 0) {
                if (Float.isNaN(var1.x) || Float.isNaN(var1.y) || Float.isNaN(var1.z)) {
                    return null;
                }
                if (var6 == var3 && var7 == var4 && var8 == var5) {
                    return null;
                }
                float var10 = 999.0f;
                float var11 = 999.0f;
                float var12 = 999.0f;
                if (var3 > var6) {
                    var10 = var6 + 1.0f;
                }
                if (var3 < var6) {
                    var10 = (float)var6;
                }
                if (var4 > var7) {
                    var11 = var7 + 1.0f;
                }
                if (var4 < var7) {
                    var11 = (float)var7;
                }
                if (var5 > var8) {
                    var12 = var8 + 1.0f;
                }
                if (var5 < var8) {
                    var12 = (float)var8;
                }
                float var13 = 999.0f;
                float var14 = 999.0f;
                float var15 = 999.0f;
                final float var16 = var2.x - var1.x;
                final float var17 = var2.y - var1.y;
                final float var18 = var2.z - var1.z;
                if (var10 != 999.0f) {
                    var13 = (var10 - var1.x) / var16;
                }
                if (var11 != 999.0f) {
                    var14 = (var11 - var1.y) / var17;
                }
                if (var12 != 999.0f) {
                    var15 = (var12 - var1.z) / var18;
                }
                byte var20;
                if (var13 < var14 && var13 < var15) {
                    if (var3 > var6) {
                        var20 = 4;
                    }
                    else {
                        var20 = 5;
                    }
                    var1.x = var10;
                    var1.y += var17 * var13;
                    var1.z += var18 * var13;
                }
                else if (var14 < var15) {
                    if (var4 > var7) {
                        var20 = 0;
                    }
                    else {
                        var20 = 1;
                    }
                    var1.x += var16 * var14;
                    var1.y = var11;
                    var1.z += var18 * var14;
                }
                else {
                    if (var5 > var8) {
                        var20 = 2;
                    }
                    else {
                        var20 = 3;
                    }
                    var1.x += var16 * var15;
                    var1.y += var17 * var15;
                    var1.z = var12;
                }
                final Vec3D var21;
                final Vec3D vec3D = var21 = new Vec3D(var1.x, var1.y, var1.z);
                final float x = (float)Math.floor(var1.x);
                vec3D.x = x;
                var6 = (int)x;
                if (var20 == 5) {
                    --var6;
                    final Vec3D vec3D2 = var21;
                    ++vec3D2.x;
                }
                final Vec3D vec3D3 = var21;
                final float y = (float)Math.floor(var1.y);
                vec3D3.y = y;
                var7 = (int)y;
                if (var20 == 1) {
                    --var7;
                    final Vec3D vec3D4 = var21;
                    ++vec3D4.y;
                }
                final Vec3D vec3D5 = var21;
                final float z = (float)Math.floor(var1.z);
                vec3D5.z = z;
                var8 = (int)z;
                if (var20 == 3) {
                    --var8;
                    final Vec3D vec3D6 = var21;
                    ++vec3D6.z;
                }
                final int var22 = this.getTile(var6, var7, var8);
                final Block var23 = Block.blocks[var22];
                if (var22 <= 0 || var23.getLiquidType() != LiquidType.NOT_LIQUID) {
                    continue;
                }
                if (var23.isCube()) {
                    final MovingObjectPosition var24;
                    if ((var24 = var23.clip(var6, var7, var8, var1, var2)) != null) {
                        return var24;
                    }
                    continue;
                }
                else {
                    final MovingObjectPosition var24;
                    if ((var24 = var23.clip(var6, var7, var8, var1, var2)) != null) {
                        return var24;
                    }
                    continue;
                }
            }
            return null;
        }
        return null;
    }
    
    public void playSound(final String var1, final Entity var2, final float var3, final float var4) {
        if (this.rendererContext$5cd64a7f != null) {
            final Minecraft var5;
            if ((var5 = this.rendererContext$5cd64a7f).soundPlayer == null || !var5.settings.sound) {
                return;
            }
            final AudioInfo var6;
            if (var2.distanceToSqr(var5.player) < 1024.0f && (var6 = var5.sound.getAudioInfo(var1, var3, var4)) != null) {
                var5.soundPlayer.play(var6, new EntitySoundPos(var2, var5.player));
            }
        }
    }
    
    public void playSound(final String var1, final float var2, final float var3, final float var4, final float var5, final float var6) {
        if (this.rendererContext$5cd64a7f != null) {
            final Minecraft var7;
            if ((var7 = this.rendererContext$5cd64a7f).soundPlayer == null || !var7.settings.sound) {
                return;
            }
            final AudioInfo var8;
            if ((var8 = var7.sound.getAudioInfo(var1, var5, var6)) != null) {
                var7.soundPlayer.play(var8, new LevelSoundPos(var2, var3, var4, var7.player));
            }
        }
    }
    
    public boolean maybeGrowTree(int var1, int var2, int var3) {
        int var4 = this.random.nextInt(3) + 4;
        boolean var5 = true;

        int var6;
        int var8;
        int var9;
        for(var6 = var2; var6 <= var2 + 1 + var4; ++var6) {
           byte var7 = 1;
           if(var6 == var2) {
              var7 = 0;
           }

           if(var6 >= var2 + 1 + var4 - 2) {
              var7 = 2;
           }

           for(var8 = var1 - var7; var8 <= var1 + var7 && var5; ++var8) {
              for(var9 = var3 - var7; var9 <= var3 + var7 && var5; ++var9) {
                 if(var8 >= 0 && var6 >= 0 && var9 >= 0 && var8 < this.width && var6 < this.depth && var9 < this.height) {
                    if((this.blocks[(var6 * this.height + var9) * this.width + var8] & 255) != 0) {
                       var5 = false;
                    }
                 } else {
                    var5 = false;
                 }
              }
           }
        }

        if(!var5) {
           return false;
        } else if((this.blocks[((var2 - 1) * this.height + var3) * this.width + var1] & 255) == Block.GRASS.id && var2 < this.depth - var4 - 1) {
           this.setTile(var1, var2 - 1, var3, Block.DIRT.id);

           int var13;
           for(var13 = var2 - 3 + var4; var13 <= var2 + var4; ++var13) {
              var8 = var13 - (var2 + var4);
              var9 = 1 - var8 / 2;

              for(int var10 = var1 - var9; var10 <= var1 + var9; ++var10) {
                 int var12 = var10 - var1;

                 for(var6 = var3 - var9; var6 <= var3 + var9; ++var6) {
                    int var11 = var6 - var3;
                    if(Math.abs(var12) != var9 || Math.abs(var11) != var9 || this.random.nextInt(2) != 0 && var8 != 0) {
                       this.setTile(var10, var13, var6, Block.LEAVES.id);
                    }
                 }
              }
           }

           for(var13 = 0; var13 < var4; ++var13) {
              this.setTile(var1, var2 + var13, var3, Block.LOG.id);
           }

           return true;
        } else {
           return false;
        }
     }
    
    public Entity getPlayer() {
        return this.player;
    }
    
    public Entity getMob() {
        return this.mob;
    }
    
    public Entity Attackskeleton() {
        return this.skeleton;
    }
    
    public void addEntity(final Entity var1) {
        this.blockMap.insert(var1);
        var1.setLevel(this);
    }
    
    public void removeEntity(final Entity var1) {
        this.blockMap.remove(var1);
    }
    
    public void explode(final Entity var1, final float var2, final float var3, final float var4, final float var5) {
        final int var6 = (int)(var2 - var5 - 1.0f);
        final int var7 = (int)(var2 + var5 + 1.0f);
        final int var8 = (int)(var3 - var5 - 1.0f);
        final int var9 = (int)(var3 + var5 + 1.0f);
        final int var10 = (int)(var4 - var5 - 1.0f);
        final int var11 = (int)(var4 + var5 + 1.0f);
        for (int var12 = var6; var12 < var7; ++var12) {
            for (int var13 = var9 - 1; var13 >= var8; --var13) {
                for (int var14 = var10; var14 < var11; ++var14) {
                    final float var15 = var12 + 0.5f - var2;
                    final float var16 = var13 + 0.5f - var3;
                    final float var17 = var14 + 0.5f - var4;
                    final int var18;
                    if (var12 >= 0 && var13 >= 0 && var14 >= 0 && var12 < this.width && var13 < this.depth && var14 < this.height && var15 * var15 + var16 * var16 + var17 * var17 < var5 * var5 && (var18 = this.getTile(var12, var13, var14)) > 0 && Block.blocks[var18].canExplode()) {
                        Block.blocks[var18].dropItems(this, var12, var13, var14, 0.3f);
                        this.setTile(var12, var13, var14, 0);
                        Block.blocks[var18].explode(this, var12, var13, var14);
                    }
                }
            }
        }
        final List<?> var19 = this.blockMap.getEntities(var1, (float)var6, (float)var8, (float)var10, (float)var7, (float)var9, (float)var11);
        for (int var13 = 0; var13 < var19.size(); ++var13) {
            final float var15;
            final Entity var20;
            if ((var15 = (var20 = (Entity) var19.get(var13)).distanceTo(var2, var3, var4) / var5) <= 1.0f) {
                final float var16 = 1.0f - var15;
                var20.hurt(var1, (int)(var16 * 15.0f + 1.0f));
            }
        }
    }
    
    public Entity findSubclassOf(final Class<?> var1) {
        for (int var2 = 0; var2 < this.blockMap.all.size(); ++var2) {
            final Entity var3 = (Entity) this.blockMap.all.get(var2);
            if (var1.isAssignableFrom(var3.getClass())) {
                return var3;
            }
        }
        return null;
    }
    
    public void removeAllNonCreativeModeEntities() {
        this.blockMap.removeAllNonCreativeModeEntities();
    }
    
    public void playSound(final String var1, final TNTBlock tntBlock, final float var3, final float var4) {
    }
    
    public Skeleton getSkeleton() {
        return this.skeleton;
    }

	public boolean getBlock(AABB var1, Block var2) {
        int var3 = (int)var1.x0;
        int var4 = (int)var1.x1 + 1;
        int var5 = (int)var1.y0;
        int var6 = (int)var1.y1 + 1;
        int var7 = (int)var1.z0;
        int var8 = (int)var1.z1 + 1;
        if (var1.x0 < 0.0f) {
            --var3;
        }
        if (var1.y0 < 0.0f) {
            --var5;
        }
        if (var1.z0 < 0.0f) {
            --var7;
        }
        if (var3 < 0) {
            var3 = 0;
        }
        if (var5 < 0) {
            var5 = 0;
        }
        if (var7 < 0) {
            var7 = 0;
        }
        if (var4 > this.width) {
            var4 = this.width;
        }
        if (var6 > this.depth) {
            var6 = this.depth;
        }
        if (var8 > this.height) {
            var8 = this.height;
        }
        for (int var9 = var3; var9 < var4; ++var9) {
            for (var3 = var5; var3 < var6; ++var3) {
                for (int var10 = var7; var10 < var8; ++var10) {
                    if ((Block.blocks[this.getTile(var9, var3, var10)]) == var2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

	public boolean items(Player player2, int var3) {
		return false;
	}
	
	public int daylightcycle(int day, int night) {
		return night;
		
	}



    public final float getStarBrightness(float float1) {
        if ((float1 = 1.0f - (MathHelper.cos(float1 * 3.1415927f * 2.0f) * 2.0f + 0.75f)) < 0.0f) {
            float1 = 0.0f;
        }
        if (float1 > 1.0f) {
            float1 = 1.0f;
        }
        return float1 * float1 * 0.5f;
    }
    
    public final float getCelestialAngle(float float1) {
        if (this.skyBrightness > 15) {
            return 0.0f;
        }
        return float1 = (this.timeOfDay + float1) / 24000.0f - 0.15f;
    }

	
}
