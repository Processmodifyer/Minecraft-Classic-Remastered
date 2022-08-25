/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.AnimalModel;
import net.classic.remastered.client.model.ModelPart;

public final class SheepModel
extends AnimalModel {
    public SheepModel() {
        super(12, 0.0f);
        this.head = new ModelPart(0, 0);
        this.head.setBounds(-3.0f, -4.0f, -6.0f, 6, 6, 8, 0.0f);
        this.head.setPosition(0.0f, 6.0f, -8.0f);
        this.body = new ModelPart(28, 8);
        this.body.setBounds(-4.0f, -10.0f, -7.0f, 8, 16, 6, 0.0f);
        this.body.setPosition(0.0f, 5.0f, 2.0f);
    }
}

