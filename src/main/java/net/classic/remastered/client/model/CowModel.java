/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public class CowModel
extends Model {
    ModelPart head = new ModelPart(0, 0);
    ModelPart body;
    ModelPart leg1;
    ModelPart leg2;
    ModelPart leg3;
    ModelPart leg4;
    ModelPart horn1;
    ModelPart horn2;

    public CowModel() {
        this.head.setBounds(-4.0f, -4.0f, -6.0f, 8, 8, 6, 0.0f);
        this.head.setPosition(0.0f, 4.0f, -8.0f);
        this.head.mirror = true;
        this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
        this.body = new ModelPart(18, 4);
        this.body.setBounds(-6.0f, -10.0f, -7.0f, 12, 18, 10, 0.0f);
        this.body.setPosition(0.0f, 5.0f, 2.0f);
        this.body.mirror = true;
        this.setRotation(this.body, 1.570796f, 0.0f, 0.0f);
        this.leg1 = new ModelPart(0, 16);
        this.leg1.setBounds(-3.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
        this.leg1.setPosition(-3.0f, 12.0f, 7.0f);
        this.leg1.mirror = true;
        this.setRotation(this.leg1, 0.0f, 0.0f, 0.0f);
        this.leg2 = new ModelPart(0, 16);
        this.leg2.setBounds(-1.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
        this.leg2.setPosition(3.0f, 12.0f, 7.0f);
        this.leg2.mirror = true;
        this.setRotation(this.leg2, 0.0f, 0.0f, 0.0f);
        this.leg3 = new ModelPart(0, 16);
        this.leg3.setBounds(-3.0f, 0.0f, -3.0f, 4, 12, 4, 0.0f);
        this.leg3.setPosition(-3.0f, 12.0f, -5.0f);
        this.leg3.mirror = true;
        this.setRotation(this.leg3, 0.0f, 0.0f, 0.0f);
        this.leg4 = new ModelPart(0, 16);
        this.leg4.setBounds(-1.0f, 0.0f, -3.0f, 4, 12, 4, 0.0f);
        this.leg4.setPosition(3.0f, 12.0f, -5.0f);
        this.leg4.mirror = true;
        this.setRotation(this.leg4, 0.0f, 0.0f, 0.0f);
        this.horn1 = new ModelPart(22, 0);
        this.horn1.setBounds(-4.0f, -5.0f, -4.0f, 1, 3, 1, 0.0f);
        this.horn1.setPosition(0.0f, 3.0f, -7.0f);
        this.horn1.mirror = true;
        this.setRotation(this.horn1, 0.0f, 0.0f, 0.0f);
        this.horn2 = new ModelPart(22, 0);
        this.horn2.setBounds(3.0f, -5.0f, -4.0f, 1, 3, 1, 0.0f);
        this.horn2.setPosition(0.0f, 3.0f, -7.0f);
        this.horn2.mirror = true;
        this.setRotation(this.horn2, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(float par1, float par2, float par3, float yawDegrees, float pitchDegrees, float scale) {
        super.render(par1, par2, par3, yawDegrees, pitchDegrees, scale);
        this.setRotationAngles(par1, par2, par3, yawDegrees, pitchDegrees, scale);
        this.head.render(scale);
        this.body.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);
        this.horn1.render(scale);
        this.horn2.render(scale);
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }

    public void setRotationAngles(float par1, float par2, float par3, float yawDegrees, float pitchDegrees, float scale) {
        this.head.yaw = yawDegrees / 57.295776f;
        this.horn1.pitch = this.head.pitch = pitchDegrees / 57.295776f;
        this.horn1.yaw = this.head.yaw;
        this.horn2.pitch = this.head.pitch;
        this.horn2.yaw = this.head.yaw;
        this.leg1.pitch = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
        this.leg2.pitch = MathHelper.cos(par1 * 0.6662f + (float)Math.PI) * 1.4f * par2;
        this.leg3.pitch = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
        this.leg4.pitch = MathHelper.cos(par1 * 0.6662f + (float)Math.PI) * 1.4f * par2;
    }
}

