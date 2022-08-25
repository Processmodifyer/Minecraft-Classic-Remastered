/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.classic.remastered.game.entity.LivingEntity;
import net.dungland.util.MathHelper;

public class ChickenModel
extends Model {
    public ModelPart head;
    public ModelPart body;
    public ModelPart rightLeg;
    public ModelPart leftLeg;
    public ModelPart rightWing;
    public ModelPart leftWing;
    public ModelPart bill;
    public ModelPart chin;
    public LivingEntity mob;

    public ChickenModel() {
        this.headOffset = 1.0f;
        int var1 = 16;
        this.head = new ModelPart(0, 0);
        this.head.setBounds(-2.0f, -6.0f, -2.0f, 4, 6, 3, 0.0f);
        this.head.setPosition(0.0f, -1 + var1, -4.0f);
        this.bill = new ModelPart(14, 0);
        this.bill.setBounds(-2.0f, -4.0f, -4.0f, 4, 2, 2, 0.0f);
        this.bill.setPosition(0.0f, -1 + var1, -4.0f);
        this.chin = new ModelPart(14, 4);
        this.chin.setBounds(-1.0f, -2.0f, -3.0f, 2, 2, 2, 0.0f);
        this.chin.setPosition(0.0f, -1 + var1, -4.0f);
        this.body = new ModelPart(0, 9);
        this.body.setBounds(-3.0f, -4.0f, -3.0f, 6, 8, 6, 0.0f);
        this.body.setPosition(0.0f, var1, 0.0f);
        this.rightLeg = new ModelPart(26, 0);
        this.rightLeg.setBounds(-1.0f, 0.0f, -3.0f, 3, 5, 3, 0.0f);
        this.rightLeg.setPosition(-2.0f, 3 + var1, 1.0f);
        this.leftLeg = new ModelPart(26, 0);
        this.leftLeg.setBounds(-1.0f, 0.0f, -3.0f, 3, 5, 3, 0.0f);
        this.leftLeg.setPosition(1.0f, 3 + var1, 1.0f);
        this.rightWing = new ModelPart(24, 13);
        this.rightWing.setBounds(0.0f, 0.0f, -3.0f, 1, 4, 6, 0.0f);
        this.rightWing.setPosition(-4.0f, -3 + var1, 0.0f);
        this.leftWing = new ModelPart(24, 13);
        this.leftWing.setBounds(-1.0f, 0.0f, -3.0f, 1, 4, 6, 0.0f);
        this.leftWing.setPosition(4.0f, -3 + var1, 0.0f);
    }

    @Override
    public void render(float par2, float par3, float par4, float yawDegrees, float pitchDegrees, float scale) {
        this.setRotationAngles(par2, par3, par4, yawDegrees, pitchDegrees, scale);
        this.head.render(scale);
        this.bill.render(scale);
        this.chin.render(scale);
        this.body.render(scale);
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
        this.rightWing.render(scale);
        this.leftWing.render(scale);
    }

    public void setRotationAngles(float par1, float par2, float par3, float yawDegrees, float pitchDegrees, float scale) {
        this.head.pitch = pitchDegrees / 57.295776f;
        this.head.yaw = yawDegrees / 57.295776f;
        this.bill.pitch = this.head.pitch;
        this.bill.yaw = this.head.yaw;
        this.chin.pitch = this.head.pitch;
        this.chin.yaw = this.head.yaw;
        this.body.pitch = 1.5707964f;
        this.rightLeg.pitch = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
        this.leftLeg.pitch = MathHelper.cos(par1 * 0.6662f + (float)Math.PI) * 1.4f * par2;
        this.rightWing.roll = par3;
        this.leftWing.roll = -par3;
    }
}

