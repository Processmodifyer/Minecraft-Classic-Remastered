/*
 * 
 */
package net.classic.remastered.client.gui;

import java.io.File;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.LevelNameScreen;
import net.classic.remastered.client.gui.LoadLevelScreen;
import net.classic.remastered.client.main.Minecraft;

public final class SaveLevelScreen
extends LoadLevelScreen {
    public SaveLevelScreen(GuiScreen var1) {
        super(var1);
        this.title = "Save level";
        this.saving = true;
    }

    @Override
    public final void onOpen() {
        super.onOpen();
        ((Button)this.buttons.get((int)5)).text = "Save file...";
    }

    @Override
    protected final void setLevels(String[] var1) {
        for (int var2 = 0; var2 < 5; ++var2) {
            ((Button)this.buttons.get((int)var2)).text = var1[var2];
            ((Button)this.buttons.get((int)var2)).visible = true;
            ((Button)this.buttons.get((int)var2)).active = this.minecraft.session.haspaid;
        }
    }

    @Override
    public final void render(int var1, int var2) {
        super.render(var1, var2);
        if (!this.minecraft.session.haspaid) {
            SaveLevelScreen.drawFadingBox(this.width / 2 - 80, 72, this.width / 2 + 80, 120, -536870912, -536870912);
            SaveLevelScreen.drawCenteredString(this.fontRenderer, "Premium only!", this.width / 2, 80, 0xFF9090);
            SaveLevelScreen.drawCenteredString(this.fontRenderer, "Purchase the game to be able", this.width / 2, 96, 0xE08080);
            SaveLevelScreen.drawCenteredString(this.fontRenderer, "to save your levels online.", this.width / 2, 104, 0xE08080);
        }
    }

    @Override
    protected final void openLevel(File var1) {
        if (!var1.getName().endsWith(".mine")) {
            var1 = new File(var1.getParentFile(), String.valueOf(var1.getName()) + ".mine");
        }
        File var2 = var1;
        Minecraft var3 = this.minecraft;
        this.minecraft.levelIo.save(var3.level, var2);
        this.minecraft.setCurrentScreen(this.parent);
    }

    @Override
    protected final void openLevel(int var1) {
        this.minecraft.setCurrentScreen(new LevelNameScreen(this, ((Button)this.buttons.get((int)var1)).text, var1));
    }
}

