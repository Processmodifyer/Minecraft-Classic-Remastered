/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public class GiantCreeperModel
extends Model {
    ModelPart head = new ModelPart(0, 0);
    ModelPart body;
    ModelPart leg1;
    ModelPart leg2;
    ModelPart leg3;
    ModelPart leg4;

    public GiantCreeperModel() {
        this.head.setBounds(-5.0f, -17.0f, 6.0f, 8, 8, 6, 0.0f);
        this.head.setPosition(0.0f, 4.0f, -8.0f);
        this.head.mirror = true;
        this.setRotation(this.head, 0.0f, 0.0f, 0.0f);
        this.body = new ModelPart(18, 4);
        this.body.setBounds(-6.0f, -10.0f, -7.0f, 12, 18, 10, 0.0f);
        this.body.setPosition(0.0f, 5.0f, 2.0f);
        this.body.mirror = true;
        this.setRotation(this.body, -0.027884f, 0.0f, 0.0f);
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
        this.setRotation(this.leg3, 0.0f, 0.0f, -0.0371786f);
        this.leg4 = new ModelPart(0, 16);
        this.leg4.setBounds(-1.0f, 0.0f, -3.0f, 4, 12, 4, 0.0f);
        this.leg4.setPosition(3.0f, 12.0f, -5.0f);
        this.leg4.mirror = true;
        this.setRotation(this.leg4, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5);
        this.head.render(f5);
        this.body.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
        this.head.yaw = f3 / 57.295776f;
        this.head.pitch = f4 / 57.295776f;
        this.leg1.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.leg2.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f1;
        this.leg3.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f1;
        this.leg4.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
    }
}

