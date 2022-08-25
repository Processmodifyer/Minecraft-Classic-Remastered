/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public class EnderKingModel
extends Model {
    ModelPart Head = new ModelPart(0, 0);
    ModelPart Body;
    ModelPart RightArm;
    ModelPart LeftArm;
    ModelPart RightLeg;
    ModelPart LeftLeg;
    ModelPart Block;

    public EnderKingModel() {
        this.Head.setBounds(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.0f);
        this.Head.setPosition(0.0f, -14.0f, 0.0f);
        this.Head.mirror = true;
        this.setRotation(this.Head, 0.0f, 0.0f, 0.0f);
        this.Body = new ModelPart(32, 16);
        this.Body.setBounds(-4.0f, 0.0f, -2.0f, 8, 12, 4, 0.0f);
        this.Body.setPosition(0.0f, -14.0f, 0.0f);
        this.Body.mirror = true;
        this.setRotation(this.Body, 0.0f, 0.0f, 0.0f);
        this.RightArm = new ModelPart(56, 0);
        this.RightArm.setBounds(-1.0f, -2.0f, -1.0f, 2, 30, 2, 0.0f);
        this.RightArm.setPosition(-5.0f, -12.0f, 0.0f);
        this.RightArm.mirror = true;
        this.setRotation(this.RightArm, 0.0f, 0.0f, 0.0f);
        this.LeftArm = new ModelPart(56, 0);
        this.LeftArm.setBounds(-1.0f, -2.0f, -1.0f, 2, 30, 2, 0.0f);
        this.LeftArm.setPosition(5.0f, -12.0f, 0.0f);
        this.LeftArm.mirror = true;
        this.setRotation(this.LeftArm, -3.141593f, 0.0f, 0.0f);
        this.LeftArm.mirror = false;
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
        this.Block = new ModelPart(70, 0);
        this.Block.setBounds(-1.0f, -53.0f, -9.0f, 13, 13, 14, 0.0f);
        this.Block.setPosition(0.0f, 0.0f, 0.0f);
        this.Block.mirror = true;
        this.setRotation(this.Block, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Head.render(f5);
        this.Body.render(f5);
        this.RightArm.render(f5);
        this.LeftArm.render(f5);
        this.RightLeg.render(f5);
        this.LeftLeg.render(f5);
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.RightLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.LeftLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f1;
    }
}

