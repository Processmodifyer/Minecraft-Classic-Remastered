/*
 * 
 */
package net.classic.remastered.game.entity.creature;

import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.model.HumanoidModel;
import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.world.World;

public class HumanoidMob
extends LivingEntity {


    public HumanoidMob(World var1, float var2, float var3, float var4) {
        super(var1);
        this.modelName = "humanoid";
        this.heightOffset = 1.62f;
        this.setPos(var2, var3, var4);
    }

    @Override
    public void renderModel(TextureManager var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        super.renderModel(var1, var2, var3, var4, var5, var6, var7);
        Model var9 = modelCache.getModel(this.modelName);
        GL11.glEnable(3008);
        if (this.allowAlpha) {
            GL11.glEnable(2884);
        }
        if (this.hasHair) {
            GL11.glDisable(2884);
            HumanoidModel var10 = null;
            var10 = (HumanoidModel)var9;
            ((HumanoidModel)var9).headwear.yaw = var10.head.yaw;
            var10.headwear.pitch = var10.head.pitch;
            var10.headwear.render(var7);
            GL11.glEnable(2884);
        }
        GL11.glDisable(3008);
    }
}

