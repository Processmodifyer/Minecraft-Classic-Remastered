/*
 * 
 */
package net.classic.remastered.game.entity.monster.ai.base;

import java.util.List;
import java.util.Random;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.entity.monster.ai.AI;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;

public class BasicAI
extends AI {

    public Random random = new Random();
    public float xxa;
    public float yya;
    protected float yRotA;
    public World level;
    public LivingEntity mob;
    public boolean jumping = false;
    protected int attackDelay = 0;
    public float runSpeed = 0.7f;
    protected int noActionTime = 0;
    public Class<? extends LivingEntity> attackTarget1 = null;
    public Entity attackTarget = null;
    public boolean running = false;
	private Player player;

    @Override
    public void tick(World var1, LivingEntity var2) {
        Entity var3;
        ++this.noActionTime;
        if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && (var3 = var1.getPlayer()) != null) {
            float var4 = var3.x - var2.x;
            float var5 = var3.y - var2.y;
            float var6 = var3.z - var2.z;
            if (var4 * var4 + var5 * var5 + var6 * var6 < 1024.0f) {
                this.noActionTime = 0;
            } else {
                var2.remove();
            }
        }
        this.level = var1;
        this.mob = var2;
        if (this.attackDelay > 0) {
            --this.attackDelay;
        }
        if (var2.health <= 0) {
            this.jumping = false;
            this.xxa = 0.0f;
            this.yya = 0.0f;
            this.yRotA = 0.0f;
        } else {
            this.update();
        }
        boolean var7 = var2.isInWater();
        boolean var9 = var2.isInLava();
        if (this.jumping) {
            if (var7) {
                var2.yd += 0.04f;
            } else if (var9) {
                var2.yd += 0.04f;
            } else if (var2.onGround) {
                this.jumpFromGround();
            }
        }
        this.xxa *= 0.98f;
        this.yya *= 0.98f;
        this.yRotA *= 0.9f;
        var2.travel(this.xxa, this.yya);
        List var11 = var1.findEntities(var2, var2.bb.grow(0.2f, 0.0f, 0.2f));
        if (var11 != null && var11.size() > 0) {
            for (int var8 = 0; var8 < var11.size(); ++var8) {
                Entity var10 = (Entity)var11.get(var8);
                if (!var10.isPushable()) continue;
                var10.push(var2);
            }
         if (var2.health <= 10) {
         }
        }
    }

    protected void jumpFromGround() {
        this.mob.yd = 0.42f;
    }

    protected void update() {
        if (this.random.nextFloat() < 0.07f) {
            this.xxa = (this.random.nextFloat() - 0.5f) * this.runSpeed;
            this.yya = this.random.nextFloat() * this.runSpeed;
        }
        boolean bl = this.jumping = this.random.nextFloat() < 0.01f;
        if (this.random.nextFloat() < 0.04f) {
            this.yRotA = (this.random.nextFloat() - 0.5f) * 60.0f;
        }
        this.mob.yRot += this.yRotA;
        this.mob.xRot = this.defaultLookAngle;
        if (this.attackTarget != null) {
            this.yya = this.runSpeed;
            this.jumping = this.random.nextFloat() < 0.04f;
        }
        boolean var1 = this.mob.isInWater();
        boolean var2 = this.mob.isInLava();
        if (var1 || var2) {
            this.jumping = this.random.nextFloat() < 0.8f;
        }
    }

    @Override
    public void beforeRemove() {
    }

    @Override
    public void hurt(Entity var1, int var2) {
        super.hurt(var1, var2);
        this.noActionTime = 0;
    }
}

