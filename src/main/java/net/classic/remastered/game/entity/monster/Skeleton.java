/*
 * 
 */
package net.classic.remastered.game.entity.monster;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.entity.creature.HumanoidMob;
import net.classic.remastered.game.entity.monster.ai.base.BasicAttackAI;
import net.classic.remastered.game.entity.other.NewArrow;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.item.Item;
import net.classic.remastered.game.world.tile.Block;

public class Skeleton
extends HumanoidMob {


    public Skeleton(World var1, float var2, float var3, float var4) {
        super(var1, var2, var3, var4);
        this.modelName = "skeleton";
        this.textureName = "/mob/skeleton.png";
        SkeletonAI var5 = new SkeletonAI(this);
        this.deathScore = 120;
        this.heightOffset = 1.62f;
        var5.runSpeed = 0.3f;
        this.ai = var5;
    }

    public void shootArrow(World var1) {
        var1.addEntity(new NewArrow(var1, this, this.x, this.y, this.z, this.yRot + 180.0f + (float)(Math.random() * 45.0 - 22.5), this.xRot - (float)(Math.random() * 45.0 - 10.0), 1.0f));
    }

    @Override
    public void hurt(Entity var1, int var2) {
        if (this.health <= 0) {
            this.level.playSound("mob.skeletondeath", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        if (this.health >= 0) {
            this.level.playSound("mob.skeletonhurt", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        super.hurt(var1, var2);
    }

    static void shootRandomArrow(Skeleton var0) {
        int var1 = (int)((Math.random() + Math.random()) * 3.0 + 4.0);
        for (int var2 = 0; var2 < var1; ++var2) {
            var0.level.addEntity(new NewArrow(var0.level, var0.level.getPlayer(), var0.x, var0.y - 0.2f, var0.z, (float)Math.random() * 360.0f, -((float)Math.random()) * 60.0f, 0.4f));
        }
    }

    @Override
    public void die(Entity var1) {
        int var2 = (int)(Math.random() + Math.random() + 1.0);
        for (int var3 = 0; var3 < var2; ++var3) {
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Item.ARROW.id));
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Item.ARROW.id));
        }
        super.die(var1);
    }

    public final class SkeletonAI
    extends BasicAttackAI {

        final Skeleton parent;

        SkeletonAI(Skeleton var1) {
            this.parent = var1;
        }

        @Override
        public final void tick(World var1, LivingEntity var2) {
            super.tick(var1, var2);
            if (var2.health > 0 && this.random.nextInt(30) == 0 && this.attackTarget != null) {
                this.parent.shootArrow(var1);
            }
        }

        @Override
        public final void beforeRemove() {
            Skeleton.shootRandomArrow(this.parent);
        }
    }


}

