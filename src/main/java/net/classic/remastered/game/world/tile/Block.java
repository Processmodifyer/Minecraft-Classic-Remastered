package net.classic.remastered.game.world.tile;

import java.util.*;

import net.classic.remastered.*;
import net.classic.remastered.client.model.*;
import net.classic.remastered.client.particle.*;
import net.classic.remastered.client.render.*;
import net.classic.remastered.game.entity.other.*;
import net.classic.remastered.game.phys.*;
import net.classic.remastered.game.world.*;
import net.classic.remastered.game.world.item.AppleItem;
import net.classic.remastered.game.world.item.ArrowItem;
import net.classic.remastered.game.world.item.BowItem;
import net.classic.remastered.game.world.item.FeatherItem;
import net.classic.remastered.game.world.item.Item;
import net.classic.remastered.game.world.item.LighterItem;
import net.classic.remastered.game.world.liquid.*;

public class Block
{
    protected static Random random;
    public static final Block[] blocks;
    public static final boolean[] physics;
    private static boolean[] opaque;
    private static boolean[] cube;
    public static final boolean[] liquid;
    private static int[] tickDelay;
    public static Object[] c;
    public static final Block STONE;
    public static final Block GRASS;
    public static final Block DIRT;
    public static final Block COBBLESTONE;
    public static final Block WOOD;
    public static final Block SAPLING;
    public static final Block BEDROCK;
    public static final Block WATER;
    public static final Block STATIONARY_WATER;
    public static final Block LAVA;
    public static final Block STATIONARY_LAVA;
    public static final Block SAND;
    public static final Block GRAVEL;
    public static final Block GOLD_ORE;
    public static final Block IRON_ORE;
    public static final Block COAL_ORE;
    public static final Block LOG;
    public static final Block LEAVES;
    public static final Block SPONGE;
    public static final Block GLASS;
    public static final Block RED_WOOL;
    public static final Block ORANGE_WOOL;
    public static final Block YELLOW_WOOL;
    public static final Block LIME_WOOL;
    public static final Block GREEN_WOOL;
    public static final Block AQUA_GREEN_WOOL;
    public static final Block CYAN_WOOL;
    public static final Block BLUE_WOOL;
    public static final Block PURPLE_WOOL;
    public static final Block INDIGO_WOOL;
    public static final Block VIOLET_WOOL;
    public static final Block MAGENTA_WOOL;
    public static final Block PINK_WOOL;
    public static final Block BLACK_WOOL;
    public static final Block GRAY_WOOL;
    public static final Block WHITE_WOOL;
    public static final Block DANDELION;
    public static final Block ROSE;
    public static final Block BROWN_MUSHROOM;
    public static final Block RED_MUSHROOM;
    public static final Block GOLD_BLOCK;
    public static final Block IRON_BLOCK;
    public static final Block DOUBLE_SLAB;
    public static final Block SLAB;
    public static final Block BRICK;
    public static final Block TNT;
    public static final Block BOOKSHELF;
    public static final Block MOSSY_COBBLESTONE;
    public static final Block OBSIDIAN;
    public static final Block WORKBENCH;
    public static final Block WEB;
    public static final Block TORCH;
    public static final Block REDSTONE_BLOCK;
    public static final Block REDSTONE_ORE;
    public static final Block FIREBLOCK;
    public static final Item BOW;
    public static final Item APPLE;
    public static final Item FEATHER;
    public static final Item ARROW;
    public static final Item FLINTSTEEL;
    public int textureId;
    public final int id;
    public Tile$SoundType stepsound;
    private int hardness;
    private boolean explodes;
    public static Item item;
	public static boolean[] tickOnLoad;
    public float x1;
    public float y1;
    public float z1;
    public float x2;
    public float y2;
    public float z2;
    public float particleGravity;
	public int textureId2;
    
    static {
        Block.random = new Random();
        blocks = new Block[256];
        physics = new boolean[256];
        Block.opaque = new boolean[256];
        Block.cube = new boolean[256];
        liquid = new boolean[256];
        Block.tickDelay = new int[256];
        Block var10000 = new StoneBlock(1, 1).setData(Tile$SoundType.stone, 1.0f, 1.0f, 1.0f);
        boolean var10001 = false;
        Block var10002 = var10000;
        var10000.explodes = false;
        STONE = var10002;
        GRASS = new GrassBlock(99).setData(Tile$SoundType.grass, 0.9f, 1.0f, 0.6f);
        DIRT = new DirtBlock(3, 2).setData(Tile$SoundType.grass, 0.8f, 1.0f, 0.5f);
        var10000 = new Block(4, 16).setData(Tile$SoundType.stone, 1.0f, 1.0f, 1.5f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        COBBLESTONE = var10002;
        WOOD = new Block(5, 4).setData(Tile$SoundType.wood, 1.0f, 1.0f, 1.5f);
        SAPLING = new SaplingBlock(6, 15).setData(Tile$SoundType.none, 0.7f, 1.0f, 0.0f);
        var10000 = new Block(7, 17).setData(Tile$SoundType.stone, 1.0f, 1.0f, 999.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        BEDROCK = var10002;
        WATER = new LiquidBlock(8, LiquidType.WATER).setData(Tile$SoundType.none, 1.0f, 1.0f, 100.0f);
        STATIONARY_WATER = new StillLiquidBlock(9, LiquidType.WATER).setData(Tile$SoundType.none, 1.0f, 1.0f, 100.0f);
        LAVA = new LiquidBlock(10, LiquidType.LAVA).setData(Tile$SoundType.none, 1.0f, 1.0f, 100.0f);
        STATIONARY_LAVA = new StillLiquidBlock(11, LiquidType.LAVA).setData(Tile$SoundType.none, 1.0f, 1.0f, 100.0f);
        SAND = new SandBlock(12, 18).setData(Tile$SoundType.gravel, 0.8f, 1.0f, 0.5f);
        GRAVEL = new SandBlock(13, 19).setData(Tile$SoundType.gravel, 0.8f, 1.0f, 0.6f);
        var10000 = new OreBlock(14, 32).setData(Tile$SoundType.stone, 1.0f, 1.0f, 3.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        GOLD_ORE = var10002;
        var10000 = new OreBlock(15, 33).setData(Tile$SoundType.stone, 1.0f, 1.0f, 3.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        IRON_ORE = var10002;
        var10000 = new OreBlock(16, 34).setData(Tile$SoundType.stone, 1.0f, 1.0f, 3.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        COAL_ORE = var10002;
        LOG = new WoodBlock(17).setData(Tile$SoundType.wood, 1.0f, 1.0f, 2.5f);
        LEAVES = new LeavesBlock(18, 22).setData(Tile$SoundType.grass, 1.0f, 0.4f, 0.2f);
        SPONGE = new SpongeBlock(19).setData(Tile$SoundType.cloth, 1.0f, 0.9f, 0.6f);
        GLASS = new GlassBlock(20, 49, false).setData(Tile$SoundType.metal, 1.0f, 1.0f, 0.3f);
        RED_WOOL = new Block(21, 64).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        ORANGE_WOOL = new Block(22, 65).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        YELLOW_WOOL = new Block(23, 66).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        LIME_WOOL = new Block(24, 67).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        GREEN_WOOL = new Block(25, 68).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        AQUA_GREEN_WOOL = new Block(26, 69).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        CYAN_WOOL = new Block(27, 70).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        BLUE_WOOL = new Block(28, 71).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        PURPLE_WOOL = new Block(29, 72).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        INDIGO_WOOL = new Block(30, 73).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        VIOLET_WOOL = new Block(31, 74).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        MAGENTA_WOOL = new Block(32, 75).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        PINK_WOOL = new Block(33, 76).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        BLACK_WOOL = new Block(34, 77).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        GRAY_WOOL = new Block(35, 78).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        WHITE_WOOL = new Block(36, 79).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.8f);
        DANDELION = new FlowerBlock(37, 13).setData(Tile$SoundType.none, 0.7f, 1.0f, 0.0f);
        ROSE = new FlowerBlock(38, 12).setData(Tile$SoundType.none, 0.7f, 1.0f, 0.0f);
        BROWN_MUSHROOM = new MushroomBlock(39, 29).setData(Tile$SoundType.none, 0.7f, 1.0f, 0.0f);
        RED_MUSHROOM = new MushroomBlock(40, 28).setData(Tile$SoundType.none, 0.7f, 1.0f, 0.0f);
        var10000 = new MetalBlock(41, 40).setData(Tile$SoundType.metal, 0.7f, 1.0f, 3.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        GOLD_BLOCK = var10002;
        var10000 = new MetalBlock(42, 39).setData(Tile$SoundType.metal, 0.7f, 1.0f, 5.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        IRON_BLOCK = var10002;
        var10000 = new SlabBlock(43, true).setData(Tile$SoundType.stone, 1.0f, 1.0f, 2.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        DOUBLE_SLAB = var10002;
        var10000 = new SlabBlock(44, false).setData(Tile$SoundType.stone, 1.0f, 1.0f, 2.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        SLAB = var10002;
        var10000 = new Block(45, 7).setData(Tile$SoundType.stone, 1.0f, 1.0f, 2.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        BRICK = var10002;
        TNT = new TNTBlock(46, 8).setData(Tile$SoundType.cloth, 1.0f, 1.0f, 0.0f);
        BOOKSHELF = new BookshelfBlock(47, 35).setData(Tile$SoundType.wood, 1.0f, 1.0f, 1.5f);
        var10000 = new Block(48, 36).setData(Tile$SoundType.stone, 1.0f, 1.0f, 1.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        MOSSY_COBBLESTONE = var10002;
        var10000 = new StoneBlock(49, 37).setData(Tile$SoundType.stone, 1.0f, 1.0f, 10.0f);
        var10001 = false;
        var10002 = var10000;
        var10000.explodes = false;
        OBSIDIAN = var10002;
        TORCH = new TorchBlock(56, 54).setData(Tile$SoundType.none, 0.7f, 1.0f, 0.9f);
        WORKBENCH = new WorkBenchBlock(54, 26).setData(Tile$SoundType.wood, 1.0f, 1.0f, 1.5f);
        WEB = new CobwebBlock(55, 11, 0.5f).setData(Tile$SoundType.cloth, 1.0f, 0.4f, 0.2f);
        FIREBLOCK = new FireBlock(67, 31).setData(Tile$SoundType.none, 0.3f, 1.0f, 0f);
        //Items list
    	APPLE = new AppleItem(50, 50);
    	FEATHER = new FeatherItem(51, 51);
    	ARROW = new ArrowItem(52, 52);
    	BOW = new BowItem(53, 53);
    	FLINTSTEEL = new LighterItem(54, 54);
    	REDSTONE_BLOCK = new RedstoneBlock(59, 112).setData(Tile$SoundType.metal, 0.7f, 1.0f, 5.0f);
    	REDSTONE_ORE = new OreBlock(60, 81).setData(Tile$SoundType.stone, 1.0f, 1.0f, 3.0f);
    	    
    }
    
    protected Block(final int id) {
        this.explodes = true;
        Block.blocks[id] = this;
        this.id = id;
        this.setBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        Block.opaque[id] = this.isSolid();
        Block.cube[id] = this.isCube();
        Block.liquid[id] = false;
    }
    
    protected Block(final int id, final int textureID) {
        this(id);
        this.textureId = textureID;
    }
    
    public boolean isCube() {
        return true;
    }
    
    protected Block setData(final Tile$SoundType soundType, final float var2, final float particleGravity, final float hardness) {
        this.particleGravity = particleGravity;
        this.stepsound = soundType;
        this.hardness = (int)(hardness * 20.0f);
        if (this instanceof FlowerBlock) {
            this.stepsound = Tile$SoundType.grass;
        }
        return this;
    }
    
    protected void setPhysics(final boolean physics) {
        Block.physics[this.id] = physics;
    }
    
    protected void setBounds(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }
    
    public void setTickDelay(final int tickDelay) {
        Block.tickDelay[this.id] = tickDelay;
    }
    
    public void renderFullbright(final ShapeRenderer shapeRenderer) {
        final float red = 0.5f;
        final float green = 0.8f;
        final float blue = 0.6f;
        shapeRenderer.color(red, red, red);
        this.renderInside(shapeRenderer, -2, 0, 0, 0);
        shapeRenderer.color(1.0f, 1.0f, 1.0f);
        this.renderInside(shapeRenderer, -2, 0, 0, 1);
        shapeRenderer.color(green, green, green);
        this.renderInside(shapeRenderer, -2, 0, 0, 2);
        shapeRenderer.color(green, green, green);
        this.renderInside(shapeRenderer, -2, 0, 0, 3);
        shapeRenderer.color(blue, blue, blue);
        this.renderInside(shapeRenderer, -2, 0, 0, 4);
        shapeRenderer.color(blue, blue, blue);
        this.renderInside(shapeRenderer, -2, 0, 0, 5);
    }
    
    protected float getBrightness(final World level, final int x, final int y, final int z) {
        return level.getBrightness(x, y, z);
    }
    
    public boolean canRenderSide(final World level, final int x, final int y, final int z, final int side) {
        return !level.isSolidTile(x, y, z);
    }
    
    protected int getTextureId(final int texture) {
        return this.textureId;
    }
    
    public void renderInside(final ShapeRenderer shapeRenderer, final int x, final int y, final int z, final int side) {
        final int textureID1 = this.getTextureId(side);
        this.renderSide(shapeRenderer, x, y, z, side, textureID1);
    }
    
    public void renderSide(final ShapeRenderer shapeRenderer, final int x, final int y, final int z, final int side, final int textureID) {
        final int var7 = textureID % 16 << 4;
        final int var8 = textureID / 16 << 4;
        final float var9 = var7 / 256.0f;
        final float var10 = (var7 + 15.99f) / 256.0f;
        float var11 = var8 / 256.0f;
        float var12 = (var8 + 15.99f) / 256.0f;
        if (side >= 2 && textureID < 240) {
            if (this.y1 >= 0.0f && this.y2 <= 1.0f) {
                var11 = (var8 + this.y1 * 15.99f) / 256.0f;
                var12 = (var8 + this.y2 * 15.99f) / 256.0f;
            }
            else {
                var11 = var8 / 256.0f;
                var12 = (var8 + 15.99f) / 256.0f;
            }
        }
        final float var13 = x + this.x1;
        final float var14 = x + this.x2;
        final float var15 = y + this.y1;
        final float var16 = y + this.y2;
        final float var17 = z + this.z1;
        final float var18 = z + this.z2;
        if (side == 0) {
            shapeRenderer.vertexUV(var13, var15, var18, var9, var12);
            shapeRenderer.vertexUV(var13, var15, var17, var9, var11);
            shapeRenderer.vertexUV(var14, var15, var17, var10, var11);
            shapeRenderer.vertexUV(var14, var15, var18, var10, var12);
        }
        else if (side == 1) {
            shapeRenderer.vertexUV(var14, var16, var18, var10, var12);
            shapeRenderer.vertexUV(var14, var16, var17, var10, var11);
            shapeRenderer.vertexUV(var13, var16, var17, var9, var11);
            shapeRenderer.vertexUV(var13, var16, var18, var9, var12);
        }
        else if (side == 2) {
            shapeRenderer.vertexUV(var13, var16, var17, var10, var11);
            shapeRenderer.vertexUV(var14, var16, var17, var9, var11);
            shapeRenderer.vertexUV(var14, var15, var17, var9, var12);
            shapeRenderer.vertexUV(var13, var15, var17, var10, var12);
        }
        else if (side == 3) {
            shapeRenderer.vertexUV(var13, var16, var18, var9, var11);
            shapeRenderer.vertexUV(var13, var15, var18, var9, var12);
            shapeRenderer.vertexUV(var14, var15, var18, var10, var12);
            shapeRenderer.vertexUV(var14, var16, var18, var10, var11);
        }
        else if (side == 4) {
            shapeRenderer.vertexUV(var13, var16, var18, var10, var11);
            shapeRenderer.vertexUV(var13, var16, var17, var9, var11);
            shapeRenderer.vertexUV(var13, var15, var17, var9, var12);
            shapeRenderer.vertexUV(var13, var15, var18, var10, var12);
        }
        else if (side == 5) {
            shapeRenderer.vertexUV(var14, var15, var18, var9, var12);
            shapeRenderer.vertexUV(var14, var15, var17, var10, var12);
            shapeRenderer.vertexUV(var14, var16, var17, var10, var11);
            shapeRenderer.vertexUV(var14, var16, var18, var9, var11);
        }
    }
    
    public final void renderSide(final ShapeRenderer var1, final int var2, final int var3, final int var4, final int var5) {
        final int var8;
        final float var7;
        final float var6 = (var7 = (var8 = this.getTextureId(var5)) % 16 / 16.0f) + 0.0624375f;
        final float var10;
        final float var9 = (var10 = var8 / 16 / 16.0f) + 0.0624375f;
        final float var11 = var2 + this.x1;
        final float var12 = var2 + this.x2;
        final float var13 = var3 + this.y1;
        final float var14 = var3 + this.y2;
        final float var15 = var4 + this.z1;
        final float var16 = var4 + this.z2;
        if (var5 == 0) {
            var1.vertexUV(var12, var13, var16, var6, var9);
            var1.vertexUV(var12, var13, var15, var6, var10);
            var1.vertexUV(var11, var13, var15, var7, var10);
            var1.vertexUV(var11, var13, var16, var7, var9);
        }
        if (var5 == 1) {
            var1.vertexUV(var11, var14, var16, var7, var9);
            var1.vertexUV(var11, var14, var15, var7, var10);
            var1.vertexUV(var12, var14, var15, var6, var10);
            var1.vertexUV(var12, var14, var16, var6, var9);
        }
        if (var5 == 2) {
            var1.vertexUV(var11, var13, var15, var6, var9);
            var1.vertexUV(var12, var13, var15, var7, var9);
            var1.vertexUV(var12, var14, var15, var7, var10);
            var1.vertexUV(var11, var14, var15, var6, var10);
        }
        if (var5 == 3) {
            var1.vertexUV(var12, var14, var16, var6, var10);
            var1.vertexUV(var12, var13, var16, var6, var9);
            var1.vertexUV(var11, var13, var16, var7, var9);
            var1.vertexUV(var11, var14, var16, var7, var10);
        }
        if (var5 == 4) {
            var1.vertexUV(var11, var13, var16, var6, var9);
            var1.vertexUV(var11, var13, var15, var7, var9);
            var1.vertexUV(var11, var14, var15, var7, var10);
            var1.vertexUV(var11, var14, var16, var6, var10);
        }
        if (var5 == 5) {
            var1.vertexUV(var12, var14, var16, var7, var10);
            var1.vertexUV(var12, var14, var15, var6, var10);
            var1.vertexUV(var12, var13, var15, var6, var9);
            var1.vertexUV(var12, var13, var16, var7, var9);
        }
    }
    
    public AABB getSelectionBox(final int x, final int y, final int z) {
        final AABB aabb = new AABB(x + this.x1, y + this.y1, z + this.z1, x + this.x2, y + this.y2, z + this.z2);
        return aabb;
    }
    
    public AABB getCollisionBox(final int x, final int y, final int z) {
        final AABB aabb = new AABB(x + this.x1, y + this.y1, z + this.z1, x + this.x2, y + this.y2, z + this.z2);
        return aabb;
    }
    
    public boolean isOpaque() {
        return true;
    }
    
    public boolean isSolid() {
        return true;
    }
    
    public void update(final World level, final int x, final int y, final int z, final Random rand) {
    }
    
    public void spawnBreakParticles(final World level, final int x, final int y, final int z, final ParticleManager particleManager) {
        for (int var6 = 0; var6 < 4; ++var6) {
            for (int var7 = 0; var7 < 4; ++var7) {
                for (int var8 = 0; var8 < 4; ++var8) {
                    final float var9 = x + (var6 + 0.5f) / 4.0f;
                    final float var10 = y + (var7 + 0.5f) / 4.0f;
                    final float var11 = z + (var8 + 0.5f) / 4.0f;
                    particleManager.spawnParticle(new TerrainParticle(level, var9, var10, var11, var9 - x - 0.5f, var10 - y - 0.5f, var11 - z - 0.5f, this));
                }
            }
        }
    }
    
    public final void spawnBlockParticles(final World var1, final int var2, final int var3, final int var4, final int var5, final ParticleManager var6) {
        final float var7 = 0.1f;
        float var8 = var2 + Block.random.nextFloat() * (this.x2 - this.x1 - var7 * 2.0f) + var7 + this.x1;
        float var9 = var3 + Block.random.nextFloat() * (this.y2 - this.y1 - var7 * 2.0f) + var7 + this.y1;
        float var10 = var4 + Block.random.nextFloat() * (this.z2 - this.z1 - var7 * 2.0f) + var7 + this.z1;
        if (var5 == 0) {
            var9 = var3 + this.y1 - var7;
        }
        if (var5 == 1) {
            var9 = var3 + this.y2 + var7;
        }
        if (var5 == 2) {
            var10 = var4 + this.z1 - var7;
        }
        if (var5 == 3) {
            var10 = var4 + this.z2 + var7;
        }
        if (var5 == 4) {
            var8 = var2 + this.x1 - var7;
        }
        if (var5 == 5) {
            var8 = var2 + this.x2 + var7;
        }
        var6.spawnParticle(new TerrainParticle(var1, var8, var9, var10, 0.0f, 0.0f, 0.0f, this).setPower(0.2f).scale(0.6f));
    }
    
    public LiquidType getLiquidType() {
        return LiquidType.NOT_LIQUID;
    }
    
    public void onNeighborChange(final World var1, final int var2, final int var3, final int var4, final int var5) {
    }
    
    public void onPlace(final World level, final int x, final int y, final int z) {
    }
    
    public int getTickDelay() {
        return 0;
    }
    
    public void onAdded(final World level, final int x, final int y, final int z) {
    }
    
    public void onRemoved(final World var1, final int var2, final int var3, final int var4) {
    }
    
    public void onRightclicked(final World level, final int var2, final int var3, final int var4) {
    }
    
    public int getDropCount() {
        return 1;
    }
    
    public int getDrop() {
        return this.id;
    }
    
    public final int getHardness() {
        return this.hardness;
    }
    
    public void onBreak(final World var1, final int var2, final int var3, final int var4) {
        this.dropItems(var1, var2, var3, var4, 1.0f);
    }
    
    public void dropItems(final World var1, final int var2, final int var3, final int var4, final float var5) {
        for (int var6 = this.getDropCount(), var7 = 0; var7 < var6; ++var7) {
            if (Block.random.nextFloat() <= var5) {
                float var8 = 0.7f;
                final float var9 = Block.random.nextFloat() * var8 + (1.0f - var8) * 0.5f;
                final float var10 = Block.random.nextFloat() * var8 + (1.0f - var8) * 0.5f;
                var8 = Block.random.nextFloat() * var8 + (1.0f - var8) * 0.5f;
                var1.addEntity(new NonLivingEntity(var1, var2 + var9, var3 + var10, var4 + var8, this.getDrop()));
            }
        }
    }
    
    public void renderPreview(final ShapeRenderer var1) {
        var1.begin();
        for (int var2 = 0; var2 < 6; ++var2) {
            if (var2 == 0) {
                var1.normal(0.0f, 1.0f, 0.0f);
            }
            if (var2 == 1) {
                var1.normal(0.0f, -1.0f, 0.0f);
            }
            if (var2 == 2) {
                var1.normal(0.0f, 0.0f, 1.0f);
            }
            if (var2 == 3) {
                var1.normal(0.0f, 0.0f, -1.0f);
            }
            if (var2 == 4) {
                var1.normal(1.0f, 0.0f, 0.0f);
            }
            if (var2 == 5) {
                var1.normal(-1.0f, 0.0f, 0.0f);
            }
            this.renderInside(var1, 0, 0, 0, var2);
        }
        var1.end();
    }
    
    public final boolean canExplode() {
        return this.explodes;
    }
    
    public final MovingObjectPosition clip(final int var1, final int var2, final int var3, Vec3D var4, Vec3D var5) {
        var4 = var4.add((float)(-var1), (float)(-var2), (float)(-var3));
        var5 = var5.add((float)(-var1), (float)(-var2), (float)(-var3));
        Vec3D var6 = var4.getXIntersection(var5, this.x1);
        Vec3D var7 = var4.getXIntersection(var5, this.x2);
        Vec3D var8 = var4.getYIntersection(var5, this.y1);
        Vec3D var9 = var4.getYIntersection(var5, this.y2);
        Vec3D var10 = var4.getZIntersection(var5, this.z1);
        var5 = var4.getZIntersection(var5, this.z2);
        if (!this.xIntersects(var6)) {
            var6 = null;
        }
        if (!this.xIntersects(var7)) {
            var7 = null;
        }
        if (!this.yIntersects(var8)) {
            var8 = null;
        }
        if (!this.yIntersects(var9)) {
            var9 = null;
        }
        if (!this.zIntersects(var10)) {
            var10 = null;
        }
        if (!this.zIntersects(var5)) {
            var5 = null;
        }
        Vec3D var11 = null;
        if (var6 != null) {
            var11 = var6;
        }
        if (var7 != null && (var11 == null || var4.distance(var7) < var4.distance(var11))) {
            var11 = var7;
        }
        if (var8 != null && (var11 == null || var4.distance(var8) < var4.distance(var11))) {
            var11 = var8;
        }
        if (var9 != null && (var11 == null || var4.distance(var9) < var4.distance(var11))) {
            var11 = var9;
        }
        if (var10 != null && (var11 == null || var4.distance(var10) < var4.distance(var11))) {
            var11 = var10;
        }
        if (var5 != null && (var11 == null || var4.distance(var5) < var4.distance(var11))) {
            var11 = var5;
        }
        if (var11 == null) {
            return null;
        }
        byte var12 = -1;
        if (var11 == var6) {
            var12 = 4;
        }
        if (var11 == var7) {
            var12 = 5;
        }
        if (var11 == var8) {
            var12 = 0;
        }
        if (var11 == var9) {
            var12 = 1;
        }
        if (var11 == var10) {
            var12 = 2;
        }
        if (var11 == var5) {
            var12 = 3;
        }
        return new MovingObjectPosition(var1, var2, var3, var12, var11.add((float)var1, (float)var2, (float)var3));
    }
    
    private boolean xIntersects(final Vec3D var1) {
        return var1 != null && (var1.y >= this.y1 && var1.y <= this.y2 && var1.z >= this.z1 && var1.z <= this.z2);
    }
    
    private boolean yIntersects(final Vec3D var1) {
        return var1 != null && (var1.x >= this.x1 && var1.x <= this.x2 && var1.z >= this.z1 && var1.z <= this.z2);
    }
    
    private boolean zIntersects(final Vec3D var1) {
        return var1 != null && (var1.x >= this.x1 && var1.x <= this.x2 && var1.y >= this.y1 && var1.y <= this.y2);
    }
    
    public void explode(final World var1, final int var2, final int var3, final int var4) {
    }
    
    public boolean render(final World var1, final int var2, final int var3, final int var4, final ShapeRenderer var5) {
        boolean var6 = false;
        final float var7 = 0.5f;
        final float var8 = 0.8f;
        final float var9 = 0.6f;
        if (this.canRenderSide(var1, var2, var3 - 1, var4, 0)) {
            final float var10 = this.getBrightness(var1, var2, var3 - 1, var4);
            var5.color(var7 * var10, var7 * var10, var7 * var10);
            this.renderInside(var5, var2, var3, var4, 0);
            var6 = true;
        }
        if (this.canRenderSide(var1, var2, var3 + 1, var4, 1)) {
            final float var10 = this.getBrightness(var1, var2, var3 + 1, var4);
            var5.color(var10 * 1.0f, var10 * 1.0f, var10 * 1.0f);
            this.renderInside(var5, var2, var3, var4, 1);
            var6 = true;
        }
        if (this.canRenderSide(var1, var2, var3, var4 - 1, 2)) {
            final float var10 = this.getBrightness(var1, var2, var3, var4 - 1);
            var5.color(var8 * var10, var8 * var10, var8 * var10);
            this.renderInside(var5, var2, var3, var4, 2);
            var6 = true;
        }
        if (this.canRenderSide(var1, var2, var3, var4 + 1, 3)) {
            final float var10 = this.getBrightness(var1, var2, var3, var4 + 1);
            var5.color(var8 * var10, var8 * var10, var8 * var10);
            this.renderInside(var5, var2, var3, var4, 3);
            var6 = true;
        }
        if (this.canRenderSide(var1, var2 - 1, var3, var4, 4)) {
            final float var10 = this.getBrightness(var1, var2 - 1, var3, var4);
            var5.color(var9 * var10, var9 * var10, var9 * var10);
            this.renderInside(var5, var2, var3, var4, 4);
            var6 = true;
        }
        if (this.canRenderSide(var1, var2 + 1, var3, var4, 5)) {
            final float var10 = this.getBrightness(var1, var2 + 1, var3, var4);
            var5.color(var9 * var10, var9 * var10, var9 * var10);
            this.renderInside(var5, var2, var3, var4, 5);
            var6 = true;
        }
        return var6;
    }
    
    public int getRenderPass() {
        return 0;
    }

	public void onAdded(World level, int x, int y, int z, int item) {
		// TODO Auto-generated method stub
		
	}
}
