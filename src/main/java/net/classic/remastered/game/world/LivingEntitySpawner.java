package net.classic.remastered.game.world;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.entity.creature.Chicken;
import net.classic.remastered.game.entity.creature.Cow;
import net.classic.remastered.game.entity.creature.Pig;
import net.classic.remastered.game.entity.creature.Sheep;
import net.classic.remastered.game.entity.monster.*;
import net.classic.remastered.game.world.liquid.*;

public final class LivingEntitySpawner
{
    public World level;
    
    public LivingEntitySpawner(final World var1) {
        this.level = var1;
    }
    
    public final int spawn(final int var1, final Entity var2, final ProgressBarDisplay var3) {
        int var4 = 0;
        for (int var5 = 0; var5 < var1; ++var5) {
            if (var3 != null) {
                var3.setProgress(var5 * 100 / (var1 - 1));
            }
            final int var6 = this.level.random.nextInt(6);
            final int var7 = this.level.random.nextInt(this.level.width);
            final int var8 = (int)(Math.min(this.level.random.nextFloat(), this.level.random.nextFloat()) * this.level.depth);
            final int var9 = this.level.random.nextInt(this.level.height);
            if (!this.level.isSolidTile(var7, var8, var9) && this.level.getLiquid(var7, var8, var9) == LiquidType.NOT_LIQUID && (!this.level.isLit(var7, var8, var9) || this.level.random.nextInt(5) == 0)) {
                for (int var10 = 0; var10 < 3; ++var10) {
                    int var11 = var7;
                    int var12 = var8;
                    int var13 = var9;
                    for (int var14 = 0; var14 < 3; ++var14) {
                        var11 += this.level.random.nextInt(6) - this.level.random.nextInt(6);
                        var12 += this.level.random.nextInt(1) - this.level.random.nextInt(1);
                        var13 += this.level.random.nextInt(6) - this.level.random.nextInt(6);
                        if (var11 >= 0 && var13 >= 1 && var12 >= 0 && var12 < this.level.depth - 2 && var11 < this.level.width && var13 < this.level.height && this.level.isSolidTile(var11, var12 - 1, var13) && !this.level.isSolidTile(var11, var12, var13) && !this.level.isSolidTile(var11, var12 + 1, var13)) {
                            final float var15 = var11 + 0.5f;
                            final float var16 = var12 + 1.0f;
                            final float var17 = var13 + 0.5f;
                            if (var2 != null) {
                                final float var18 = var15 - var2.x;
                                final float var19 = var16 - var2.y;
                                final float var20 = var17 - var2.z;
                                if (var18 * var18 + var19 * var19 + var20 * var20 < 256.0f) {
                                    continue;
                                }
                            }
                            else {
                                final float var18 = var15 - World.xSpawn;
                                final float var19 = var16 - World.ySpawn;
                                final float var20 = var17 - World.zSpawn;
                                if (var18 * var18 + var19 * var19 + var20 * var20 < 256.0f) {
                                    continue;
                                }
                            }
                            Object var21 = null;
                            if (var6 == 0) {
                                var21 = new Zombie(this.level, var15, var16, var17);
                            }
                            if (var6 == 1) {
                                var21 = new Skeleton(this.level, var15, var16, var17);
                            }
                            if (var6 == 2) {
                                var21 = new Pig(this.level, var15, var16, var17);
                            }
                            if (var6 == 3) {
                                var21 = new Creeper(this.level, var15, var16, var17);
                            }
                            if (var6 == 4) {
                                var21 = new Spider(this.level, var15, var16, var17);
                            }
                            if (var6 == 5) {
                                var21 = new Chicken(this.level, var15, var16, var17);
                            }
                            if (var6 == 6) {
                                var21 = new Sheep(this.level, var15, var16, var17);
                            }
                            if (var6 == 7) {
                                var21 = new Cow(this.level, var15, var16, var17);
                            }   
                            if (var6 == 8) {
                                var21 = new Endermen(this.level, var15, var16, var17);
                            }                            
                            if (this.level.isFree(((LivingEntity)var21).bb)) {
                                ++var4;
                                this.level.addEntity((Entity)var21);
                            }
                        }
                    }
                }
            }
        }
        return var4;
    }
}
