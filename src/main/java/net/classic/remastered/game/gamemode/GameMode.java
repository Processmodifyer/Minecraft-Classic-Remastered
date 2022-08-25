/*
 * 
 */
package net.classic.remastered.game.gamemode;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;
import net.classic.remastered.game.world.tile.Tile$SoundType;

public class GameMode {
    public Minecraft minecraft;
    public boolean instantBreak;

    public GameMode(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.instantBreak = false;
    }

    public void apply(World level) {
        level.creativeMode = false;
        level.growTrees = true;
    }

    public void openInventory() {
    }

    public void hitBlock(int x, int y, int z) {
        this.breakBlock(x, y, z);
    }

    public boolean canPlace(int block) {
        return true;
    }

    
    public void breakBlock(int x, int y, int z) {
        World level = this.minecraft.level;
        Block block = Block.blocks[level.getTile(x, y, z)];
        boolean success = level.netSetTile(x, y, z, 0);
        if (block != null && success) {
            if (this.minecraft.isOnline()) {
                this.minecraft.networkManager.sendBlockChange(x, y, z, 0, this.minecraft.player.inventory.getSelected());
            }
            if (block.stepsound != Tile$SoundType.none) {
                level.playSound("step." + block.stepsound.name, x, y, z, (block.stepsound.getVolume() + 1.0f) / 2.0f, block.stepsound.getPitch() * 0.8f);
            }
            block.spawnBreakParticles(level, x, y, z, this.minecraft.particleManager);
        }
    }

    public void hitBlock(int x, int y, int z, int side) {
    }

    public void resetHits() {
    }

    public void applyCracks(float time) {
    }

    public float getReachDistance() {
        return 5.0f;
    }

    public boolean useItem(Player player, int type) {
        return false;
    }

    public void preparePlayer(Player player) {
    }

    public void spawnMob() {
    }

    public void prepareLevel(World level) {
    }

    public boolean isSurvival() {
        return true;
    }

    public void apply(Player player) {
    }
}

