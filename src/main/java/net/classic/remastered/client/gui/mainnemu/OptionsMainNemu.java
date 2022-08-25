package net.classic.remastered.client.gui.mainnemu;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.ControlsScreen;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.MainNemuScreen;
import net.classic.remastered.client.gui.OptionButton;
import net.classic.remastered.client.settings.GameSettings;

public final class OptionsMainNemu
extends GuiScreen {
    private GuiScreen parent;
    private String title = "Options";
    private GameSettings settings;

    public OptionsMainNemu(GuiScreen var1, GameSettings var2) {
        this.parent = var1;
        this.settings = var2;
    }

    @Override
    public final void onOpen() {
        for (int var1 = 0; var1 < this.settings.settingCount; ++var1) {
            this.buttons.add(new OptionButton(var1, this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), this.settings.getSetting(var1)));
        }
        this.buttons.add(new Button(100, this.width / 2 - 100, this.height / 6 + 120 + 12, "Controls..."));
        this.buttons.add(new Button(200, this.width / 2 - 100, this.height / 6 + 168, "Done"));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.active) {
            if (var1.id < 100) {
                this.settings.toggleSetting(var1.id, 1);
                var1.text = this.settings.getSetting(var1.id);
            }
            if (var1.id == 100) {
                this.minecraft.setCurrentScreen(new ControlsScreen(this, this.settings));
            }
            if (var1.id == 200) {
            	minecraft.setCurrentScreen(new MainNemuScreen(parent));
            }
        }
    }

    @Override
    public final void render(int var1, int var2) {
        OptionsMainNemu.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        OptionsMainNemu.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(var1, var2);
    }
}

