/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public class CrocodileModel
extends Model {
    ModelPart tail;
    ModelPart head;
    ModelPart body;
    ModelPart leg1;
    ModelPart leg2;
    ModelPart leg4;
    ModelPart leg3;

    public CrocodileModel() {
        this.headOffset = 0.937f;
        this.tail = new ModelPart(0, 0);
        this.tail.setBounds(0.0f, 0.0f, 0.0f, 8, 2, 17, 0.0f);
        this.tail.setPosition(-4.0f, 11.0f, 5.0f);
        this.tail.pitch = 0.0f;
        this.tail.yaw = 0.0f;
        this.tail.roll = 0.0f;
        this.tail.mirror = false;
        this.head = new ModelPart(0, 0);
        this.head.setBounds(-4.0f, -4.0f, -8.0f, 8, 5, 11, 0.0f);
        this.head.setPosition(0.0f, 15.0f, -9.0f);
        this.head.pitch = 0.0f;
        this.head.yaw = 0.0f;
        this.head.roll = 0.0f;
        this.head.mirror = false;
        this.body = new ModelPart(28, 8);
        this.body.setBounds(-5.0f, -10.0f, -7.0f, 10, 16, 8, 0.0f);
        this.body.setPosition(0.0f, 11.0f, 2.0f);
        this.body.pitch = 1.5708f;
        this.body.yaw = 0.0f;
        this.body.roll = 0.0f;
        this.body.mirror = false;
        this.leg1 = new ModelPart(0, 16);
        this.leg1.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, 0.0f);
        this.leg1.setPosition(-3.0f, 18.0f, 7.0f);
        this.leg1.pitch = 0.0f;
        this.leg1.yaw = 0.0f;
        this.leg1.roll = 0.0f;
        this.leg1.mirror = false;
        this.leg2 = new ModelPart(0, 16);
        this.leg2.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, 0.0f);
        this.leg2.setPosition(3.0f, 18.0f, 7.0f);
        this.leg2.pitch = 0.0f;
        this.leg2.yaw = 0.0f;
        this.leg2.roll = 0.0f;
        this.leg2.mirror = false;
        this.leg4 = new ModelPart(0, 16);
        this.leg4.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, 0.0f);
        this.leg4.setPosition(3.0f, 18.0f, -5.0f);
        this.leg4.pitch = 0.0f;
        this.leg4.yaw = 0.0f;
        this.leg4.roll = 0.0f;
        this.leg4.mirror = false;
        this.leg3 = new ModelPart(0, 16);
        this.leg3.setBounds(-2.0f, 18.0f, -2.0f, 4, 6, 4, 0.0f);
        this.leg3.setPosition(-3.0f, 0.0f, -5.0f);
        this.leg3.pitch = 0.0f;
        this.leg3.yaw = 0.0f;
        this.leg3.roll = 0.0f;
        this.leg3.mirror = false;
    }

    @Override
    public void render(float f, float f1, float f2, float yawDegrees, float pitchDegrees, float scale) {
        super.render(f, f1, f2, yawDegrees, pitchDegrees, scale);
        this.setRotationAngles(f, f1, f2, yawDegrees, pitchDegrees, scale);
        this.tail.render(scale);
        this.head.render(scale);
        this.body.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg4.render(scale);
        this.leg3.render(scale);
    }

    public void setRotationAngles(float f, float f1, float f2, float yawDegrees, float pitchDegrees, float scale) {
        this.tail.yaw = MathHelper.cos(f / 0.95955384f * ((float)Math.PI / 90) * f1 + 0.0f);
    }
}

