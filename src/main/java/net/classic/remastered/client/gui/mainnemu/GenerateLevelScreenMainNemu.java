/*
 * 
 */
package net.classic.remastered.client.gui.mainnemu;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.MainNemuScreen;

public final class GenerateLevelScreenMainNemu
extends GuiScreen {
    private GuiScreen parent;

    public GenerateLevelScreenMainNemu(GuiScreen var1) {
        this.parent = var1;
    }

    @SuppressWarnings("unchecked")
	@Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4, "Small"));
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 24, "Normal"));
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Huge"));
        this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 120, "Cancel"));

    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 3) {
         	minecraft.setCurrentScreen(new MainNemuScreen(parent));
         	} else {
            this.minecraft.generateLevel(var1.id);
            this.minecraft.setCurrentScreen(null);
            this.minecraft.grabMouse();
        }
        
  
                
    }

    @Override
    public final void render(int var1, int var2) {
        GenerateLevelScreenMainNemu.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        GenerateLevelScreenMainNemu.drawCenteredString(this.fontRenderer, "Generate world", this.width / 2, 40, 0xFFFFFF);
        super.render(var1, var2);
    }
}

