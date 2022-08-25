/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.AnimalModel;
import net.classic.remastered.client.model.ModelPart;

public final class SheepFurModel
extends AnimalModel {
    public SheepFurModel() {
        super(12, 0.0f);
        this.head = new ModelPart(0, 0);
        this.head.setBounds(-3.0f, -4.0f, -4.0f, 6, 6, 6, 0.6f);
        this.head.setPosition(0.0f, 6.0f, -8.0f);
        this.body = new ModelPart(28, 8);
        this.body.setBounds(-4.0f, -10.0f, -7.0f, 8, 16, 6, 1.75f);
        this.body.setPosition(0.0f, 5.0f, 2.0f);
        float var1 = 0.5f;
        this.leg1 = new ModelPart(0, 16);
        this.leg1.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.leg1.setPosition(-3.0f, 12.0f, 7.0f);
        this.leg2 = new ModelPart(0, 16);
        this.leg2.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.leg2.setPosition(3.0f, 12.0f, 7.0f);
        this.leg3 = new ModelPart(0, 16);
        this.leg3.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.leg3.setPosition(-3.0f, 12.0f, -5.0f);
        this.leg4 = new ModelPart(0, 16);
        this.leg4.setBounds(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.leg4.setPosition(3.0f, 12.0f, -5.0f);
    }
}

