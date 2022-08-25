/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;

public class VillagerModel
extends Model {
    public ModelPart head = new ModelPart(0, 0);
    public ModelPart headwear;
    public ModelPart body;
    public ModelPart Arm;
    public ModelPart rightLeg;
    public ModelPart leftLeg;

    public VillagerModel(float var1) {
        this.head.setBounds(-4.0f, -8.0f, -4.0f, 8, 8, 8, var1);
        this.headwear = new ModelPart(32, 0);
        this.headwear.setBounds(-4.0f, -8.0f, -4.0f, 8, 8, 8, var1 + 0.5f);
        this.body = new ModelPart(16, 16);
        this.body.setBounds(-4.0f, 0.0f, -2.0f, 8, 12, 4, var1);
        this.Arm.setBounds(-3.0f, -2.0f, -2.0f, 4, 12, 4, var1);
        this.Arm.setPosition(-5.0f, 2.0f, 0.0f);
        this.rightLeg = new ModelPart(0, 16);
        this.rightLeg.setBounds(-2.0f, 0.0f, -2.0f, 4, 12, 4, var1);
        this.rightLeg.setPosition(-2.0f, 12.0f, 0.0f);
        this.leftLeg = new ModelPart(0, 16);
        this.leftLeg.mirror = true;
        this.leftLeg.setBounds(-2.0f, 0.0f, -2.0f, 4, 12, 4, var1);
        this.leftLeg.setPosition(2.0f, 12.0f, 0.0f);
    }
}

