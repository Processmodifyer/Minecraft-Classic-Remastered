/*
 * 
 */
package net.classic.remastered.game.gamemode;

import net.classic.remastered.client.gui.CreativeInventoryScreen;
import net.classic.remastered.client.gui.SurvivalInventoryScreen;
import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.gamemode.GameMode;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.LivingEntitySpawner;
import net.classic.remastered.game.world.item.Item;
import net.classic.remastered.game.world.tile.Block;

public final class SurvivalGameMode
extends GameMode {
    private int hitX;
    private int hitY;
    private int hitZ;
    private int hits;
    private int hardness;
    private int hitDelay;
    private LivingEntitySpawner spawner;
    private Block block;
    private LivingEntity player;
    private int type;
    private World level;
    private Block blocks;
    private Item item;
    private GameMode gamemode;

    public SurvivalGameMode(Minecraft minecraft) {
        super(minecraft);
    }

    @Override
    public void apply(World level) {
        super.apply(level);
        this.spawner = new LivingEntitySpawner(level);
    }

    @Override
    public final void openInventory() {
        this.minecraft.setCurrentScreen(new SurvivalInventoryScreen());
    }

    @Override
    public void hitBlock(int x, int y, int z, int side) {
        if (this.hitDelay > 0) {
            --this.hitDelay;
        } else if (x == this.hitX && y == this.hitY && z == this.hitZ) {
            int type = this.minecraft.level.getTile(x, y, z);
            if (type != 0) {
                Block block = Block.blocks[type];
                this.hardness = block.getHardness();
                block.spawnBlockParticles(this.minecraft.level, x, y, z, side, this.minecraft.particleManager);
                ++this.hits;
                if (this.hits == this.hardness + 1) {
                    this.breakBlock(x, y, z);
                    this.hits = 0;
                    this.hitDelay = 5;
                }
            }
        } else {
            this.hits = 0;
            this.hitX = x;
            this.hitY = y;
            this.hitZ = z;
        }
    }

    @Override
    public boolean canPlace(int block) {
        return this.minecraft.player.inventory.removeResource(block);
        
    }

    
    @Override
    public void breakBlock(int x, int y, int z) {
        int block = this.minecraft.level.getTile(x, y, z);
        Block.blocks[block].onBreak(this.minecraft.level, x, y, z);
        super.breakBlock(x, y, z);
    }

    @Override
    public void hitBlock(int x, int y, int z) {
        int block = this.minecraft.level.getTile(x, y, z);
        if (block > 0 && Block.blocks[block].getHardness() == 0) {
            this.breakBlock(x, y, z);
        }
    }

    @Override
    public void resetHits() {
        this.hits = 0;
        this.hitDelay = 0;
    }

    @Override
    public void applyCracks(float time) {
        this.minecraft.levelRenderer.cracks = this.hits <= 0 ? 0.0f : ((float)this.hits + time - 1.0f) / (float)this.hardness;
    }

    @Override
    public float getReachDistance() {
        return 4.0f;
    }

    @Override
    public boolean useItem(Player player, int type) {
        Block block = Block.blocks[type];
        if (block == Item.APPLE && this.minecraft.player.inventory.removeResource(type)) {
            player.heal(4);
            return true;
        }
        return false;
    }

    @Override
    public void preparePlayer(Player player) {
		player.inventory.slots[1] = Block.WORKBENCH.id;
		player.inventory.count[1] = 1;
		player.inventory.slots[2] = Block.FLINTSTEEL.id;
		player.inventory.count[2] = 64;
    }

    @Override
    public void spawnMob() {
    	
    
    }

    @Override
    public void prepareLevel(World level) {
        this.spawner = new LivingEntitySpawner(level);
        this.minecraft.progressBar.setText("Spawning..");
        int area = level.width * level.height * level.depth / 800;
        this.spawner.spawn(area, null, this.minecraft.progressBar);
    }
}

