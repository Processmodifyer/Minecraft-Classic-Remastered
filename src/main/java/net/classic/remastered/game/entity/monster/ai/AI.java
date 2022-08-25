/*
 * 
 */
package net.classic.remastered.game.entity.monster.ai;

import java.io.Serializable;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.world.World;

public abstract class AI
implements Serializable {

    public int defaultLookAngle = 0;
    public float runSpeed = 1.0f;
    
    public void tick(World var1, LivingEntity var2) {
    }

    public void beforeRemove() {
    }

    public void hurt(Entity var1, int var2) {
    	
    }
}

