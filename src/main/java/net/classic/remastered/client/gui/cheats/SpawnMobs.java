/*
 * 
 */
package net.classic.remastered.client.gui.cheats;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.CheatMenu;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.cheats.MoreMobs;
import net.classic.remastered.game.entity.monster.Human;
import net.classic.remastered.game.entity.monster.PigZombie;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;

public final class SpawnMobs
extends GuiScreen {
    private SpawnMobs pausa;
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

    public SpawnMobs(CheatMenu cheatMenu) {
    }

    @Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Zombie Pigman"));
        this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 72, "Human"));
        this.buttons.add(new Button(5, this.width / 2 - 100, this.height / 4 + 140, "More..."));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 2) {
            this.minecraft.level.addEntity(new PigZombie(this.minecraft.level, this.minecraft.player.x, this.minecraft.player.y, this.minecraft.player.z));
        }
        if (var1.id == 3) {
            this.minecraft.level.addEntity(new Human(this.minecraft.level, this.minecraft.player.x, this.minecraft.player.y, this.minecraft.player.z));
        }
        if (var1.id == 5) {
            this.minecraft.setCurrentScreen(new MoreMobs(this));
        }
    }

    @Override
    public final void render(int var1, int var2) {
        SpawnMobs.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        SpawnMobs.drawCenteredString(this.fontRenderer, "Spawn Mobs", this.width / 2, 40, 0xFFFFFF);
        super.render(var1, var2);
    }
}

