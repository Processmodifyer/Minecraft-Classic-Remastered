/*
 * 
 */
package net.classic.remastered.client.gui.cheats;

import net.classic.remastered.client.gui.Button;
import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.gui.cheats.MoreMobs;
import net.classic.remastered.game.entity.creature.Sheep;
import net.classic.remastered.game.entity.monster.Endermen;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;

public final class MoreMoreMobs
extends GuiScreen {
    private MoreMoreMobs pausa;
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

    public MoreMoreMobs(MoreMobs moreMobs) {
    }

    @Override
    public final void onOpen() {
        this.buttons.clear();
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4, "Sheep"));
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Endermen"));
    }

    @Override
    protected final void onButtonClick(Button var1) {
        if (var1.id == 1) {
            this.minecraft.level.addEntity(new Sheep(this.minecraft.level, this.minecraft.player.x, this.minecraft.player.y, this.minecraft.player.z));
        }
        if (var1.id == 2) {
            this.minecraft.level.addEntity(new Endermen(this.minecraft.level, this.minecraft.player.x, this.minecraft.player.y, this.minecraft.player.z));
        }

    }

    @Override
    public final void render(int var1, int var2) {
        MoreMoreMobs.drawFadingBox(0, 0, this.width, this.height, 0x60050500, -1607454624);
        MoreMoreMobs.drawCenteredString(this.fontRenderer, "More mobs page 2", this.width / 2, 40, 0xFFFFFF);
        super.render(var1, var2);
    }
}

