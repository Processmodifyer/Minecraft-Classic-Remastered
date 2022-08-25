/*
 * 
 */
package net.classic.remastered.client.model;

import net.classic.remastered.client.model.Model;
import net.classic.remastered.client.model.ModelPart;

public class PrinterModel
extends Model {
    ModelPart Base;
    ModelPart RightWall;
    ModelPart LeftWall;
    ModelPart MiddleComp;
    ModelPart WholeBase;
    ModelPart Rack;
    ModelPart Back;
    ModelPart TopLeftNobble;
    ModelPart TopRightNobble;
    ModelPart TopMiddleNobble;
    ModelPart LeftLine;
    ModelPart RightLine;

    public PrinterModel() {
        this.headOffset = 1.4375f;
        this.Base = new ModelPart(0, 0);
        this.Base.setBounds(0.0f, 0.0f, 0.0f, 16, 1, 16, 0.0f);
        this.Base.setPosition(-8.0f, 23.0f, -8.0f);
        this.Base.mirror = true;
        this.setRotation(this.Base, 0.0f, 0.0f, 0.0f);
        this.RightWall = new ModelPart(0, 19);
        this.RightWall.setBounds(0.0f, 0.0f, 0.0f, 1, 5, 14, 0.0f);
        this.RightWall.setPosition(6.0f, 18.0f, -7.0f);
        this.RightWall.mirror = true;
        this.setRotation(this.RightWall, 0.0f, 0.0f, 0.0f);
        this.LeftWall = new ModelPart(0, 19);
        this.LeftWall.setBounds(0.0f, 0.0f, 0.0f, 1, 5, 14, 0.0f);
        this.LeftWall.setPosition(-7.0f, 18.0f, -7.0f);
        this.LeftWall.mirror = true;
        this.setRotation(this.LeftWall, 0.0f, 0.0f, 0.0f);
        this.MiddleComp = new ModelPart(31, 19);
        this.MiddleComp.setBounds(0.0f, 0.0f, 0.0f, 8, 4, 11, 0.0f);
        this.MiddleComp.setPosition(-4.0f, 17.5f, -7.0f);
        this.MiddleComp.mirror = true;
        this.setRotation(this.MiddleComp, 0.0f, 0.0f, 0.0f);
        this.WholeBase = new ModelPart(65, 0);
        this.WholeBase.setBounds(0.0f, 0.0f, 0.0f, 12, 5, 11, 0.0f);
        this.WholeBase.setPosition(-6.0f, 17.0f, -6.5f);
        this.WholeBase.mirror = true;
        this.setRotation(this.WholeBase, 0.0f, 0.0f, 0.0f);
        this.Rack = new ModelPart(0, 40);
        this.Rack.setBounds(0.0f, -1.0f, 0.0f, 12, 8, 1, 0.0f);
        this.Rack.setPosition(-6.0f, 13.0f, 7.0f);
        this.Rack.mirror = true;
        this.setRotation(this.Rack, -0.3346075f, 0.0f, 0.0f);
        this.Back = new ModelPart(0, 50);
        this.Back.setBounds(0.0f, 0.0f, 0.0f, 12, 4, 1, 0.0f);
        this.Back.setPosition(-6.0f, 19.0f, 5.8f);
        this.Back.mirror = true;
        this.setRotation(this.Back, 0.0f, 0.0f, 0.0f);
        this.TopLeftNobble = new ModelPart(0, 58);
        this.TopLeftNobble.setBounds(0.0f, 0.0f, 0.0f, 3, 1, 3, 0.0f);
        this.TopLeftNobble.setPosition(-5.0f, 16.0f, 0.0f);
        this.TopLeftNobble.mirror = true;
        this.setRotation(this.TopLeftNobble, 0.0f, 0.0f, 0.0f);
        this.TopRightNobble = new ModelPart(0, 58);
        this.TopRightNobble.setBounds(0.0f, 0.0f, 0.0f, 3, 1, 3, 0.0f);
        this.TopRightNobble.setPosition(2.0f, 16.0f, 0.0f);
        this.TopRightNobble.mirror = true;
        this.setRotation(this.TopRightNobble, 0.0f, 0.0f, 0.0f);
        this.TopMiddleNobble = new ModelPart(13, 58);
        this.TopMiddleNobble.setBounds(0.0f, 0.0f, 0.0f, 4, 1, 6, 0.0f);
        this.TopMiddleNobble.setPosition(-2.0f, 16.5f, -3.0f);
        this.TopMiddleNobble.mirror = true;
        this.setRotation(this.TopMiddleNobble, 0.0f, 0.0f, 0.0f);
        this.LeftLine = new ModelPart(28, 42);
        this.LeftLine.setBounds(0.0f, 0.0f, 0.0f, 1, 1, 9, 0.0f);
        this.LeftLine.setPosition(-4.0f, 16.5f, -7.0f);
        this.LeftLine.mirror = true;
        this.setRotation(this.LeftLine, 0.0f, 0.0f, 0.0f);
        this.RightLine = new ModelPart(28, 42);
        this.RightLine.setBounds(0.0f, 0.0f, 0.0f, 1, 1, 9, 0.0f);
        this.RightLine.setPosition(3.0f, 16.5f, -7.0f);
        this.RightLine.mirror = true;
        this.setRotation(this.RightLine, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public final void render(float var1, float var2, float var3, float yawDegrees, float pitchDegrees, float scale) {
        this.Base.render(scale);
        this.RightWall.render(scale);
        this.LeftWall.render(scale);
        this.MiddleComp.render(scale);
        this.WholeBase.render(scale);
        this.Rack.render(scale);
        this.Back.render(scale);
        this.TopLeftNobble.render(scale);
        this.TopRightNobble.render(scale);
        this.TopMiddleNobble.render(scale);
        this.LeftLine.render(scale);
        this.RightLine.render(scale);
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }
}

