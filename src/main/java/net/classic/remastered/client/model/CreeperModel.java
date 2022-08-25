/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;
import net.dungland.util.MathHelper;

public final class CreeperModel
extends Model {
    private ModelPart head = new ModelPart(0, 0);
    private ModelPart unused;
    private ModelPart body;
    private ModelPart leg1;
    private ModelPart leg2;
    private ModelPart leg3;
    private ModelPart leg4;

    public CreeperModel() {
        this.head.setBounds(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.0f);
        this.unused = new ModelPart(32, 0);
        this.unused.setBounds(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.5f);
        this.body = new ModelPart(16, 16);
        this.body.setBounds(-4.0f, 0.0f, -2.0f, 8, 12, 4, 0.0f);
        this.leg1 = new ModelPart(0, 16);
        this.leg1.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, 0.0f);
        this.leg1.setPosition(-2.0f, 12.0f, 4.0f);
        this.leg2 = new ModelPart(0, 16);
        this.leg2.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, 0.0f);
        this.leg2.setPosition(2.0f, 12.0f, 4.0f);
        this.leg3 = new ModelPart(0, 16);
        this.leg3.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, 0.0f);
        this.leg3.setPosition(-2.0f, 12.0f, -4.0f);
        this.leg4 = new ModelPart(0, 16);
        this.leg4.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, 0.0f);
        this.leg4.setPosition(2.0f, 12.0f, -4.0f);
    }

    @Override
    public final void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.head.yaw = var4 / 57.295776f;
        this.head.pitch = var5 / 57.295776f;
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

