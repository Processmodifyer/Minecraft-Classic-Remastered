/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.HumanoidModel;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public final class SkeletonModel
extends HumanoidModel {
    public SkeletonModel() {
        this.rightArm = new ModelPart(40, 16);
        this.rightArm.setBounds(-1.0f, -2.0f, -1.0f, 2, 12, 2, 0.0f);
        this.rightArm.setPosition(-5.0f, 2.0f, 0.0f);
        this.leftArm = new ModelPart(40, 16);
        this.leftArm.mirror = true;
        this.leftArm.setBounds(-1.0f, -2.0f, -1.0f, 2, 12, 2, 0.0f);
        this.leftArm.setPosition(5.0f, 2.0f, 0.0f);
        this.rightLeg = new ModelPart(0, 16);
        this.rightLeg.setBounds(-1.0f, 0.0f, -1.0f, 2, 12, 2, 0.0f);
        this.rightLeg.setPosition(-2.0f, 12.0f, 0.0f);
        this.leftLeg = new ModelPart(0, 16);
        this.leftLeg.mirror = true;
        this.leftLeg.setBounds(-1.0f, 0.0f, -1.0f, 2, 12, 2, 0.0f);
        this.leftLeg.setPosition(2.0f, 12.0f, 0.0f);
    }

    @Override
    public final void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        super.setRotationAngles(var1, var2, var3, var4, var5, var6);
        this.rightArm.pitch = -1.5707964f;
        this.leftArm.pitch = -1.5707964f;
        var1 = MathHelper.sin(this.attackOffset * (float)Math.PI);
        var2 = MathHelper.sin((1.0f - (1.0f - this.attackOffset) * (1.0f - this.attackOffset)) * (float)Math.PI);
    }
}

