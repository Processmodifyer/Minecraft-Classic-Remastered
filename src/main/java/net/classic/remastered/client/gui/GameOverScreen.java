/*
 * 
 */
package net.classic.remastered.client.gui;

import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GenerateLevelScreen;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.LoadLevelScreen;
import net.classic.remastered.client.gui.OptionsScreen;
import net.classic.remastered.client.gui.PauseScreen;
import net.classic.remastered.game.entity.other.NonLivingEntity;

public final class GameOverScreen
extends GuiScreen {
    private float xloc;
    private float yloc;
    private float zloc;
    protected PauseScreen pausa;

    @Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 72, "Generate new level..."));
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 96, "Load level.."));
        this.buttons.add(new Button(4, this.width / 2 - 100, this.height / 4 + 144, "Respawn"));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 0) {
            this.minecraft.setCurrentScreen(new OptionsScreen(this, this.minecraft.settings));
        }
        if (var1.id == 1) {
            this.minecraft.setCurrentScreen(new GenerateLevelScreen(this));
        }
        if (this.minecraft.session != null && var1.id == 2) {
            this.minecraft.setCurrentScreen(new LoadLevelScreen(this));
        }
        if (var1.id == 4) {
            this.minecraft.player.health = 20;
            this.minecraft.setCurrentScreen(this.pausa);
            this.minecraft.player.resetPos();
            for (int i = 0; i <= 8; ++i) {
                for (int varacable = this.minecraft.player.inventory.count[i]; varacable > 0; --varacable) {
                    this.minecraft.level.addEntity(new NonLivingEntity(this.minecraft.level, this.xloc, this.yloc, this.zloc, this.minecraft.player.inventory.slots[i]));
                }
                this.minecraft.player.inventory.slots[i] = -1;
                this.minecraft.player.inventory.count[i] = 0;
            }
            this.minecraft.gamemode.preparePlayer(this.minecraft.player);
        }
    }

    @Override
    public final void render(int var1, int var2) {
        GameOverScreen.drawFadingBox(0, 0, this.width, this.height, 0x60500000, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GameOverScreen.drawCenteredString(this.fontRenderer, "Game over!", this.width / 2 / 2, 30, 0xFFFFFF);
        GL11.glPopMatrix();
        GameOverScreen.drawCenteredString(this.fontRenderer, "Score: &e" + this.minecraft.player.getScore(), this.width / 2, 100, 0xFFFFFF);
        super.render(var1, var2);
    }
}

