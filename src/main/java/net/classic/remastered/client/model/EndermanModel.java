/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public class EndermanModel
extends Model {
    ModelPart Headwear = new ModelPart(0, 16);
    ModelPart Body;
    ModelPart RightArm;
    ModelPart LeftArm;
    ModelPart RightLeg;
    ModelPart LeftLeg;

    public EndermanModel() {
        this.Headwear.setBounds(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.0f);
        this.Headwear.setPosition(0.0f, -14.0f, 0.0f);
        this.Headwear.mirror = true;
        this.setRotation(this.Headwear, 0.0f, 0.0f, 0.0f);
        this.Body = new ModelPart(32, 16);
        this.Body.setBounds(-4.0f, 0.0f, -2.0f, 8, 12, 4, 0.0f);
        this.Body.setPosition(0.0f, -14.0f, 0.0f);
        this.Body.mirror = true;
        this.setRotation(this.Body, 0.0f, 0.0f, 0.0f);
        this.RightArm = new ModelPart(56, 0);
        this.RightArm.setBounds(-1.0f, -2.0f, -1.0f, 2, 30, 2, 0.0f);
        this.RightArm.setPosition(-5.0f, -12.0f, 0.0f);
        this.RightArm.mirror = true;
        this.setRotation(this.RightArm, -1.710216f, 0.0371786f, -0.0185893f);
        this.LeftArm = new ModelPart(56, 0);
        this.LeftArm.setBounds(-1.0f, -2.0f, -1.0f, 2, 30, 2, 0.0f);
        this.LeftArm.setPosition(5.0f, -12.0f, 0.0f);
        this.setRotation(this.LeftArm, -1.710216f, 0.0f, -0.0185893f);
        this.RightLeg = new ModelPart(56, 0);
        this.RightLeg.setBounds(-1.0f, 0.0f, -1.0f, 2, 30, 2, 0.0f);
        this.RightLeg.setPosition(-2.0f, -2.0f, 0.0f);
        this.RightLeg.mirror = true;
        this.setRotation(this.RightLeg, 0.0f, 0.0f, 0.0f);
        this.LeftLeg = new ModelPart(56, 0);
        this.LeftLeg.setBounds(-1.0f, 0.0f, -1.0f, 2, 30, 2, 0.0f);
        this.LeftLeg.setPosition(2.0f, -2.0f, 0.0f);
        this.LeftLeg.mirror = true;
        this.setRotation(this.LeftLeg, 0.0f, 0.0f, 0.0f);
        this.LeftLeg.mirror = false;
    }

    @Override
    public void render(float par1, float par2, float par3, float yawDegrees, float pitchDegrees, float scale) {
        super.render(par1, par2, par3, yawDegrees, pitchDegrees, scale);
        this.setRotationAngles(par1, par2, par3, yawDegrees, pitchDegrees, scale);
        this.Headwear.render(scale);
        this.Body.render(scale);
        this.RightArm.render(scale);
        this.LeftArm.render(scale);
        this.RightLeg.render(scale);
        this.LeftLeg.render(scale);
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }

    public void setRotationAngles(float par1, float par2, float par3, float yawDegrees, float pitchDegrees, float scale) {
        this.Headwear.yaw = yawDegrees / 57.295776f;
        this.Headwear.pitch = pitchDegrees / 57.295776f;
        this.RightLeg.pitch = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
        this.LeftLeg.pitch = MathHelper.cos(par1 * 0.6662f + (float)Math.PI) * 1.4f * par2;
        this.RightArm.roll += MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
        this.LeftArm.roll -= MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
    }
}

