/*
 * 
 */
package net.classic.remastered.game.entity.monster.ai.base.goal;

import net.classic.remastered.client.model.Vec3D;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.monster.ai.base.BasicAI;
import net.classic.remastered.game.entity.other.NewArrow;
import net.classic.remastered.game.player.Player;
import net.dungland.util.MathHelper;

public class NeutralAI
extends BasicAI {
    private int damage = 6;
    
    @Override
    protected void update() {
        super.update();
        if (this.mob.health > 0) {
            this.doAttack();
        }
    }

    protected void doAttack() {
        float var5;
        float var4;
        float var3;
        Entity var1 = this.level.getMob();
        float var2 = 16.0f;
        if (this.attackTarget != null && this.attackTarget.removed) {
            this.attackTarget = null;
        }
        if (var1 != null && this.attackTarget == null && (var3 = var1.x - this.mob.x) * var3 + (var4 = var1.y - this.mob.y) * var4 + (var5 = var1.z - this.mob.z) * var5 < var2 * var2) {
            this.attackTarget = var1;
        }
        if (this.attackTarget != null) {
            float f = 0;
            var3 = this.attackTarget.x - this.mob.x;
            var4 = this.attackTarget.y - this.mob.y;
            var5 = this.attackTarget.z - this.mob.z;
            float var6 = var3 * var3 + var4 * var4 + var5 * var5;
            if (f > var2 * var2 * 2.0f * 2.0f && this.random.nextInt(100) == 0) {
                this.attackTarget = null;
            }
            if (this.attackTarget != null) {
                var6 = MathHelper.sqrt(var6);
                this.mob.yRot = (float)(Math.atan2(var5, var3) * 180.0 / 3.1415927410125732) - 90.0f;
                this.mob.xRot = -((float)(Math.atan2(var4, var6) * 180.0 / 3.1415927410125732));
                if (MathHelper.sqrt(var3 * var3 + var4 * var4 + var5 * var5) < 2.0f && this.attackDelay == 0) {
                    this.attack(this.attackTarget);
                }
            }
        }
    }

    public boolean attack(Entity var1) {
        if (this.level.clip(new Vec3D(this.mob.x, this.mob.y, this.mob.z), new Vec3D(var1.x, var1.y, var1.z)) != null) {
            return false;
        }
        this.mob.attackTime = 5;
        this.attackDelay = this.random.nextInt(20) + 10;
        int var2 = (int)((this.random.nextFloat() + this.random.nextFloat()) / 2.0f * (float)this.damage + 1.0f);
        var1.hurt(this.mob, var2);
        this.noActionTime = 0;
        return true;
    }

    @Override
    public void hurt(Entity var1, int var2) {
        super.hurt(var1, var2);
        if (var1 instanceof NewArrow) {
            var1 = ((NewArrow)var1).getOwner();
        }
        if (var1 != null && !var1.getClass().equals(this.mob.getClass())) {
            this.attackTarget = var1;
        }
    }
}

