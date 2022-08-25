/*
 * 
 */
package net.classic.remastered.client.gui.cheats;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.cheats.MoreMoreMobs;
import net.classic.remastered.client.gui.cheats.SpawnMobs;
import net.classic.remastered.game.entity.creature.Chicken;
import net.classic.remastered.game.entity.creature.Cow;
import net.classic.remastered.game.entity.monster.Speer;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;

public final class MoreMobs
extends GuiScreen {
    private MoreMobs pausa;
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

    public MoreMobs(SpawnMobs spawnMobs) {
    }

    @Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4, "Speer"));
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Prit"));
        this.buttons.add(new Button(4, this.width / 2 - 100, this.height / 4 + 100, "Chicken"));
        this.buttons.add(new Button(5, this.width / 2 - 100, this.height / 4 + 120, "Cow"));
        this.buttons.add(new Button(6, this.width / 2 - 100, this.height / 4 + 140, "more.."));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 1) {
            this.minecraft.level.addEntity(new Speer(this.minecraft.level, this.minecraft.player.x, this.minecraft.player.y, this.minecraft.player.z));
        }

        if (var1.id == 4) {
            this.minecraft.level.addEntity(new Chicken(this.minecraft.level, this.minecraft.player.x, this.minecraft.player.y, this.minecraft.player.z));
        }
        if (var1.id == 5) {
            this.minecraft.level.addEntity(new Cow(this.minecraft.level, this.minecraft.player.x, this.minecraft.player.y, this.minecraft.player.z));
        }
        if (var1.id == 6) {
            this.minecraft.setCurrentScreen(new MoreMoreMobs(this));
        }
    }

    @Override
    public final void render(int var1, int var2) {
        MoreMobs.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        MoreMobs.drawCenteredString(this.fontRenderer, "More mobs page 1", this.width / 2, 40, 0xFFFFFF);
        super.render(var1, var2);
    }
}

