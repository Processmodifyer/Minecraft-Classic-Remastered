/*
 * 
 */
package net.classic.remastered.client.gui;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.PauseScreen;
import net.classic.remastered.client.gui.cheats.Gui;
import net.classic.remastered.client.gui.cheats.SpawnMobs;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;

public final class CheatMenu
extends GuiScreen {
    private CheatMenu pausa;
    private float xloc;
    private float yloc;
    private float zloc;
    private World level;
    private float yRot;
    private float z;
    private float y;
    private float x;
    private float xRot;
    private Player player;

    public CheatMenu(PauseScreen pauseScreen) {
    }

    @Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4, "Set The Health to 20..."));
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Cause Player to death.."));
        this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 72, "Heal 4 Health..."));
        this.buttons.add(new Button(4, this.width / 2 - 100, this.height / 4 + 90, "Testing Gui List..."));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 0) {
            this.minecraft.player.health = 20;
        }
        if (var1.id == 2) {
            this.minecraft.player.hurt(null, 20);
        }
        if (var1.id == 3) {
            this.minecraft.player.heal(5);
        }
        if (var1.id == 4) {
            this.minecraft.setCurrentScreen(new Gui(this));
        }
        if (var1.id == 5) {
            this.minecraft.setCurrentScreen(new SpawnMobs(this));
        }
    }

    @Override
    public final void render(int var1, int var2) {
        CheatMenu.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        CheatMenu.drawCenteredString(this.fontRenderer, "Cheats", this.width / 2, 40, 0xFFFFFF);
        super.render(var1, var2);
    }
}

