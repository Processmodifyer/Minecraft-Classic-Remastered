/*
 * 
 */
package net.classic.remastered.client.gui.cheats;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.CheatMenu;
import net.classic.remastered.client.gui.CraftingGuideScreen;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;

public final class Gui
extends GuiScreen {
    private Gui pausa;
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

    public Gui(CheatMenu cheatMenu) {
    }

    @Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4, "CraftTest1"));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 1) {
            this.minecraft.setCurrentScreen(new CraftingGuideScreen());
        }
    }

    @Override
    public final void render(int var1, int var2) {
        Gui.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        Gui.drawCenteredString(this.fontRenderer, "Gui Tests", this.width / 2, 40, 0xFFFFFF);
        super.render(var1, var2);
    }
}

