/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public final class SpiderModel
extends Model {
    private ModelPart head = new ModelPart(32, 4);
    private ModelPart neck;
    private ModelPart body;
    private ModelPart leg1;
    private ModelPart leg2;
    private ModelPart leg3;
    private ModelPart leg4;
    private ModelPart leg5;
    private ModelPart leg6;
    private ModelPart leg7;
    private ModelPart leg8;

    public SpiderModel() {
        this.head.setBounds(-4.0f, -4.0f, -8.0f, 8, 8, 8, 0.0f);
        this.head.setPosition(0.0f, 0.0f, -3.0f);
        this.neck = new ModelPart(0, 0);
        this.neck.setBounds(-3.0f, -3.0f, -3.0f, 6, 6, 6, 0.0f);
        this.body = new ModelPart(0, 12);
        this.body.setBounds(-5.0f, -4.0f, -6.0f, 10, 8, 12, 0.0f);
        this.body.setPosition(0.0f, 0.0f, 9.0f);
        this.leg1 = new ModelPart(18, 0);
        this.leg1.setBounds(-15.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.leg1.setPosition(-4.0f, 0.0f, 2.0f);
        this.leg2 = new ModelPart(18, 0);
        this.leg2.setBounds(-1.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.leg2.setPosition(4.0f, 0.0f, 2.0f);
        this.leg3 = new ModelPart(18, 0);
        this.leg3.setBounds(-15.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.leg3.setPosition(-4.0f, 0.0f, 1.0f);
        this.leg4 = new ModelPart(18, 0);
        this.leg4.setBounds(-1.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.leg4.setPosition(4.0f, 0.0f, 1.0f);
        this.leg5 = new ModelPart(18, 0);
        this.leg5.setBounds(-15.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.leg5.setPosition(-4.0f, 0.0f, 0.0f);
        this.leg6 = new ModelPart(18, 0);
        this.leg6.setBounds(-1.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.leg6.setPosition(4.0f, 0.0f, 0.0f);
        this.leg7 = new ModelPart(18, 0);
        this.leg7.setBounds(-15.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.leg7.setPosition(-4.0f, 0.0f, -1.0f);
        this.leg8 = new ModelPart(18, 0);
        this.leg8.setBounds(-1.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.leg8.setPosition(4.0f, 0.0f, -1.0f);
    }

    @Override
    public final void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.head.yaw = var4 / 57.295776f;
        this.head.pitch = var5 / 57.295776f;
        var4 = 0.7853982f;
        this.leg1.roll = -var4;
        this.leg2.roll = var4;
        this.leg3.roll = -var4 * 0.74f;
        this.leg4.roll = var4 * 0.74f;
        this.leg5.roll = -var4 * 0.74f;
        this.leg6.roll = var4 * 0.74f;
        this.leg7.roll = -var4;
        this.leg8.roll = var4;
        var4 = 0.3926991f;
        this.leg1.yaw = var4 * 2.0f;
        this.leg2.yaw = -var4 * 2.0f;
        this.leg3.yaw = var4;
        this.leg4.yaw = -var4;
        this.leg5.yaw = -var4;
        this.leg6.yaw = var4;
        this.leg7.yaw = -var4 * 2.0f;
        this.leg8.yaw = var4 * 2.0f;
        var4 = -(MathHelper.cos(var1 * 0.6662f * 2.0f) * 0.4f) * var2;
        var5 = -(MathHelper.cos(var1 * 0.6662f * 2.0f + (float)Math.PI) * 0.4f) * var2;
        float var7 = -(MathHelper.cos(var1 * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * var2;
        float var8 = -(MathHelper.cos(var1 * 0.6662f * 2.0f + 4.712389f) * 0.4f) * var2;
        float var9 = Math.abs(MathHelper.sin(var1 * 0.6662f) * 0.4f) * var2;
        float var10 = Math.abs(MathHelper.sin(var1 * 0.6662f + (float)Math.PI) * 0.4f) * var2;
        float var11 = Math.abs(MathHelper.sin(var1 * 0.6662f + 1.5707964f) * 0.4f) * var2;
        var2 = Math.abs(MathHelper.sin(var1 * 0.6662f + 4.712389f) * 0.4f) * var2;
        this.leg1.yaw += var4;
        this.leg2.yaw -= var4;
        this.leg3.yaw += var5;
        this.leg4.yaw -= var5;
        this.leg5.yaw += var7;
        this.leg6.yaw -= var7;
        this.leg7.yaw += var8;
        this.leg8.yaw -= var8;
        this.leg1.roll += var9;
        this.leg2.roll -= var9;
        this.leg3.roll += var10;
        this.leg4.roll -= var10;
        this.leg5.roll += var11;
        this.leg6.roll -= var11;
        this.leg7.roll += var2;
        this.leg8.roll -= var2;
        this.head.render(var6);
        this.neck.render(var6);
        this.body.render(var6);
        this.leg1.render(var6);
        this.leg2.render(var6);
        this.leg3.render(var6);
        this.leg4.render(var6);
        this.leg5.render(var6);
        this.leg6.render(var6);
        this.leg7.render(var6);
        this.leg8.render(var6);
    }
}

