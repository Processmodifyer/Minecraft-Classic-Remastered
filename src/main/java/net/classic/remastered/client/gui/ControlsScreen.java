/*
 * 
 */
package net.classic.remastered.client.gui;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.OptionButton;
import net.classic.remastered.client.settings.GameSettings;

public final class ControlsScreen
extends GuiScreen {
    private GuiScreen parent;
    private String title = "Controls";
    private GameSettings settings;
    private int selected = -1;

    public ControlsScreen(GuiScreen var1, GameSettings var2) {
        this.parent = var1;
        this.settings = var2;
    }

    @Override
    public final void onOpen() {
        for (int var1 = 0; var1 < this.settings.bindings.length; ++var1) {
            this.buttons.add(new OptionButton(var1, this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), this.settings.getBinding(var1)));
        }
        this.buttons.add(new Button(200, this.width / 2 - 100, this.height / 6 + 168, "Done"));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        for (int var2 = 0; var2 < this.settings.bindings.length; ++var2) {
            ((Button)this.buttons.get((int)var2)).text = this.settings.getBinding(var2);
        }
        if (var1.id == 200) {
            this.minecraft.setCurrentScreen(this.parent);
        } else {
            this.selected = var1.id;
            var1.text = "> " + this.settings.getBinding(var1.id) + " <";
        }
    }

    @Override
    protected final void onKeyPress(char var1, int var2) {
        if (this.selected >= 0) {
            this.settings.setBinding(this.selected, var2);
            ((Button)this.buttons.get((int)this.selected)).text = this.settings.getBinding(this.selected);
            this.selected = -1;
        } else {
            super.onKeyPress(var1, var2);
        }
    }

    @Override
    public final void render(int var1, int var2) {
        ControlsScreen.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        ControlsScreen.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(var1, var2);
    }
}

