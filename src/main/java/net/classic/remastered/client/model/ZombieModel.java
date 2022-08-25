/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.HumanoidModel;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.monster.ai.base.BasicAI;
import net.dungland.util.MathHelper;

public class ZombieModel
extends HumanoidModel {
    private Entity player;
    BasicAI ai = new BasicAI();

    @Override
    public final void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        super.setRotationAngles(var1, var2, var3, var4, var5, var6);
        var1 = MathHelper.sin(this.attackOffset * (float)Math.PI);
        var2 = MathHelper.sin((1.0f - (1.0f - this.attackOffset) * (1.0f - this.attackOffset)) * (float)Math.PI);
        this.rightArm.roll = 0.0f;
        this.leftArm.roll = 0.0f;
        this.rightArm.yaw = -(0.1f - var1 * 0.6f);
        this.leftArm.yaw = 0.1f - var1 * 0.6f;
        this.rightArm.pitch = -1.5707964f;
        this.leftArm.pitch = -1.5707964f;
        this.rightArm.pitch -= var1 * -1.2f - var2 * -0.4f;
        this.leftArm.pitch -= var1 * -1.2f - var2 * -0.4f;
        this.rightArm.roll += MathHelper.cos(var3 * 0.09f) * 0.05f + 0.05f;
        this.leftArm.roll -= MathHelper.cos(var3 * 0.09f) * 0.05f + 0.05f;
        this.rightArm.pitch += MathHelper.sin(var3 * 0.067f) * 0.05f;
        this.leftArm.pitch -= MathHelper.sin(var3 * 0.067f) * 0.05f;
        if (this.ai.running) {
            this.rightArm.pitch = 1.5707964f;
            this.leftArm.pitch = 1.5707964f;
        }
    }
}

