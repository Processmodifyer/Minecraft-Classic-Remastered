/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public class TntSpeerModel
extends Model {
    ModelPart head;
    ModelPart body;
    ModelPart rightarm;
    ModelPart leftarm;
    ModelPart rightleg;
    ModelPart leftleg;
    ModelPart TNT;

    public TntSpeerModel(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.headOffset = 1.4375f;
        var1 = MathHelper.sin(this.attackOffset * (float)Math.PI);
        var2 = MathHelper.sin((1.0f - (1.0f - this.attackOffset) * (1.0f - this.attackOffset)) * (float)Math.PI);
        this.head = new ModelPart(0, 0);
        this.head.setBounds(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.0f);
        this.head.setPosition(0.0f, 0.0f, 0.0f);
        this.head.mirror = true;
        this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
        this.body = new ModelPart(16, 16);
        this.body.setBounds(-4.0f, 0.0f, -2.0f, 8, 12, 4, 0.0f);
        this.body.setPosition(0.0f, 0.0f, 0.0f);
        this.body.mirror = true;
        this.setRotation(this.body, 0.0f, 0.0f, 0.0f);
        this.rightarm = new ModelPart(40, 16);
        this.rightarm.setBounds(-3.0f, -10.0f, -2.0f, 4, 12, 4, 0.0f);
        this.rightarm.setPosition(-5.0f, 2.0f, 0.0f);
        this.rightarm.mirror = true;
        this.setRotation(this.rightarm, 1.245484f, 0.0f, 0.0f);
        this.leftarm = new ModelPart(40, 16);
        this.leftarm.setBounds(-1.0f, -10.0f, -2.0f, 4, 12, 4, 0.0f);
        this.leftarm.setPosition(5.0f, 2.0f, 0.0f);
        this.leftarm.mirror = true;
        this.setRotation(this.leftarm, 1.542912f, 0.0f, 0.0f);
        this.rightleg = new ModelPart(0, 16);
        this.rightleg.setBounds(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
        this.rightleg.setPosition(-2.0f, 12.0f, 0.0f);
        this.rightleg.mirror = true;
        this.setRotation(this.rightleg, 0.0f, 0.0f, 0.0f);
        this.leftleg = new ModelPart(0, 16);
        this.leftleg.setBounds(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
        this.leftleg.setPosition(2.0f, 12.0f, 0.0f);
        this.leftleg.mirror = true;
        this.setRotation(this.leftleg, 0.0f, 0.0f, 0.0f);
        this.TNT = new ModelPart(33, 0);
        this.TNT.setBounds(-4.0f, -6.0f, -10.0f, 8, 8, 6, 0.0f);
        this.TNT.setPosition(0.0f, 0.0f, 0.0f);
        this.TNT.mirror = true;
        this.setRotation(this.TNT, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(float par1, float par2, float par3, float yawDegrees, float pitchDegrees, float scale) {
        super.render(par1, par1, par2, par3, yawDegrees, scale);
        this.setRotationAngles(par1, par2, par3, yawDegrees, pitchDegrees, scale);
        this.head.render(scale);
        this.body.render(scale);
        this.head.yaw = yawDegrees / 57.295776f;
        this.head.pitch = pitchDegrees / 57.295776f;
        this.rightarm.render(scale);
        this.leftarm.render(scale);
        this.rightleg.render(scale);
        this.leftleg.render(scale);
        this.TNT.render(scale);
    }

    public void setRotationAngles(float par1, float par2, float par3, float yawDegrees, float pitchDegrees, float scale) {
        this.rightleg.pitch = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
        this.leftleg.pitch = MathHelper.cos(par1 * 0.6662f + (float)Math.PI) * 1.4f * par2;
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }
}

