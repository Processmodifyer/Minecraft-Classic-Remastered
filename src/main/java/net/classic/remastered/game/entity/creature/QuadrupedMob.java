/*
 * 
 */
package net.classic.remastered.game.entity.creature;

import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.world.World;

public class QuadrupedMob
extends LivingEntity {


    public QuadrupedMob(World var1, float var2, float var3, float var4) {
        super(var1);
        this.setSize(1.4f, 1.2f);
        this.setPos(var2, var3, var4);
        this.modelName = "pig";
    }
}

