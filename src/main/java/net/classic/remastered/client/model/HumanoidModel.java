/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public class HumanoidModel
extends Model {
    public ModelPart head = new ModelPart(0, 0);
    public ModelPart headwear;
    public ModelPart body;
    public ModelPart rightArm;
    public ModelPart leftArm;
    public ModelPart rightLeg;
    public ModelPart leftLeg;

    public HumanoidModel() {
        this(0.0f);
    }

    public HumanoidModel(float var1) {
        this.head.setBounds(-4.0f, -8.0f, -4.0f, 8, 8, 8, var1);
        this.headwear = new ModelPart(32, 0);
        this.headwear.setBounds(-4.0f, -8.0f, -4.0f, 8, 8, 8, var1 + 0.5f);
        this.body = new ModelPart(16, 16);
        this.body.setBounds(-4.0f, 0.0f, -2.0f, 8, 12, 4, var1);
        this.rightArm = new ModelPart(40, 16);
        this.rightArm.setBounds(-3.0f, -2.0f, -2.0f, 4, 12, 4, var1);
        this.rightArm.setPosition(-5.0f, 2.0f, 0.0f);
        this.leftArm = new ModelPart(40, 16);
        this.leftArm.mirror = true;
        this.leftArm.setBounds(-1.0f, -2.0f, -2.0f, 4, 12, 4, var1);
        this.leftArm.setPosition(5.0f, 2.0f, 0.0f);
        this.rightLeg = new ModelPart(0, 16);
        this.rightLeg.setBounds(-2.0f, 0.0f, -2.0f, 4, 12, 4, var1);
        this.rightLeg.setPosition(-2.0f, 12.0f, 0.0f);
        this.leftLeg = new ModelPart(0, 16);
        this.leftLeg.mirror = true;
        this.leftLeg.setBounds(-2.0f, 0.0f, -2.0f, 4, 12, 4, var1);
        this.leftLeg.setPosition(2.0f, 12.0f, 0.0f);
    }

    @Override
    public final void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.setRotationAngles(var1, var2, var3, var4, var5, var6);
        this.head.render(var6);
        this.body.render(var6);
        this.rightArm.render(var6);
        this.leftArm.render(var6);
        this.rightLeg.render(var6);
        this.leftLeg.render(var6);
    }

    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.head.yaw = var4 / 57.295776f;
        this.head.pitch = var5 / 57.295776f;
        this.rightArm.pitch = MathHelper.cos(var1 * 0.6662f + (float)Math.PI) * 2.0f * var2;
        this.rightArm.roll = (MathHelper.cos(var1 * 0.2312f) + 1.0f) * var2;
        this.leftArm.pitch = MathHelper.cos(var1 * 0.6662f) * 2.0f * var2;
        this.leftArm.roll = (MathHelper.cos(var1 * 0.2812f) - 1.0f) * var2;
        this.rightLeg.pitch = MathHelper.cos(var1 * 0.6662f) * 1.4f * var2;
        this.leftLeg.pitch = MathHelper.cos(var1 * 0.6662f + (float)Math.PI) * 1.4f * var2;
        this.rightArm.roll += MathHelper.cos(var3 * 0.09f) * 0.05f + 0.05f;
        this.leftArm.roll -= MathHelper.cos(var3 * 0.09f) * 0.05f + 0.05f;
        this.rightArm.pitch += MathHelper.sin(var3 * 0.067f) * 0.05f;
        this.leftArm.pitch -= MathHelper.sin(var3 * 0.067f) * 0.05f;
    }
}

