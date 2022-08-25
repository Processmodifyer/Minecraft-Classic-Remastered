/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public class AnimalModel
extends Model {
    public ModelPart head = new ModelPart(0, 0);
    public ModelPart body;
    public ModelPart leg1;
    public ModelPart leg2;
    public ModelPart leg3;
    public ModelPart leg4;

    public AnimalModel(int var1, float var2) {
        this.head.setBounds(-4.0f, -4.0f, -8.0f, 8, 8, 8, 0.0f);
        this.head.setPosition(0.0f, 18 - var1, -6.0f);
        this.body = new ModelPart(28, 8);
        this.body.setBounds(-5.0f, -10.0f, -7.0f, 10, 16, 8, 0.0f);
        this.body.setPosition(0.0f, 17 - var1, 2.0f);
        this.leg1 = new ModelPart(0, 16);
        this.leg1.setBounds(-2.0f, 0.0f, -2.0f, 4, var1, 4, 0.0f);
        this.leg1.setPosition(-3.0f, 24 - var1, 7.0f);
        this.leg2 = new ModelPart(0, 16);
        this.leg2.setBounds(-2.0f, 0.0f, -2.0f, 4, var1, 4, 0.0f);
        this.leg2.setPosition(3.0f, 24 - var1, 7.0f);
        this.leg3 = new ModelPart(0, 16);
        this.leg3.setBounds(-2.0f, 0.0f, -2.0f, 4, var1, 4, 0.0f);
        this.leg3.setPosition(-3.0f, 24 - var1, -5.0f);
        this.leg4 = new ModelPart(0, 16);
        this.leg4.setBounds(-2.0f, 0.0f, -2.0f, 4, var1, 4, 0.0f);
        this.leg4.setPosition(3.0f, 24 - var1, -5.0f);
    }

    @Override
    public final void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.head.yaw = var4 / 57.295776f;
        this.head.pitch = var5 / 57.295776f;
        this.body.pitch = 1.5707964f;
        this.leg1.pitch = MathHelper.cos(var1 * 0.6662f) * 1.4f * var2;
        this.leg2.pitch = MathHelper.cos(var1 * 0.6662f + (float)Math.PI) * 1.4f * var2;
        this.leg3.pitch = MathHelper.cos(var1 * 0.6662f + (float)Math.PI) * 1.4f * var2;
        this.leg4.pitch = MathHelper.cos(var1 * 0.6662f) * 1.4f * var2;
        this.head.render(var6);
        this.body.render(var6);
        this.leg1.render(var6);
        this.leg2.render(var6);
        this.leg3.render(var6);
        this.leg4.render(var6);
    }
}

