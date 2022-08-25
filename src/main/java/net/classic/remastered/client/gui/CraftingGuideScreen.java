/*
 * 
 */
package net.classic.remastered.client.gui;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;

public final class CraftingGuideScreen
extends GuiScreen {
    @Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(4, this.width / 2 - 100, this.height / 4 + 144, "Back to game"));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 4) {
            this.minecraft.setCurrentScreen(null);
            this.minecraft.grabMouse();
        }
    }

    @Override
    public final void render(int var1, int var2) {
        CraftingGuideScreen.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        CraftingGuideScreen.drawCenteredString(this.fontRenderer, "Crafting Instructions for dummies", this.width / 2, 10, 0xFFFFFF);
        CraftingGuideScreen.drawCenteredString(this.fontRenderer, "WOOD: Put log on table.", this.width / 2, 30, 0xFFFFFF);
        CraftingGuideScreen.drawCenteredString(this.fontRenderer, "WORKBENCH: Put four wooden planks on table on top of eachother.", this.width / 2, 40, 0xFFFFFF);
        CraftingGuideScreen.drawCenteredString(this.fontRenderer, "TORCH: Put stone on table and coal ore on top.", this.width / 2, 50, 0xFFFFFF);
        CraftingGuideScreen.drawCenteredString(this.fontRenderer, "BOOKTABLE: Put workbench on table and stone on top.", this.width / 2, 60, 0xFFFFFF);
        super.render(var1, var2);
    }
}

