/*
 * 
 */
package net.classic.remastered.client.gui;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;

public final class GenerateLevelScreen
extends GuiScreen {
    private GuiScreen parent;

    public GenerateLevelScreen(GuiScreen var1) {
        this.parent = var1;
    }

    @Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4, "Small"));
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 24, "Normal"));
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Huge"));
        this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 120, "Cancel"));
        this.buttons.add(new Button(4, this.width / 2 - 100, this.height / 4 + 150, "Flatland"));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 3) {
            this.minecraft.setCurrentScreen(this.parent);
        } else {
            this.minecraft.generateLevel(var1.id);
            this.minecraft.setCurrentScreen(null);
            this.minecraft.grabMouse();
        }
        

                
    }

    @Override
    public final void render(int var1, int var2) {
        GenerateLevelScreen.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        GenerateLevelScreen.drawCenteredString(this.fontRenderer, "Generate new world", this.width / 2, 40, 0xFFFFFF);
        super.render(var1, var2);
    }
}

