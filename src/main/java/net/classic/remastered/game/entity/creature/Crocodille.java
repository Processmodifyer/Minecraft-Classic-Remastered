/*
 * 
 */
package net.classic.remastered.game.entity.creature;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.entity.monster.ai.base.BasicAI;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;

public class Crocodille
extends LivingEntity {
    public static final long serialVersionUID = 1L;

    public Crocodille(World var1, float var2, float var3, float var4) {
        super(var1);
        this.modelName = "crocodille";
        this.textureName = "/mob/croc.png";
        this.heightOffset = 1.62f;
        BasicAI var5 = new BasicAI();
        this.deathScore = 540;
        var5.defaultLookAngle = 20;
        var5.runSpeed = 0.7f;
        this.ai = var5;
    }

    @Override
    public void die(Entity var1) {
        int var2 = (int)(Math.random() + Math.random() + 1.0);
        for (int var3 = 0; var3 < var2; ++var3) {
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Block.SPONGE.id));
        }
        super.die(var1);
    }
}

