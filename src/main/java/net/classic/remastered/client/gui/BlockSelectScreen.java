/*
 * 
 */
package net.classic.remastered.client.gui;

import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.render.ShapeRenderer;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.player.inventory.CreativeInventoryStorage;
import net.classic.remastered.game.world.tile.Block;

public class BlockSelectScreen
extends GuiScreen {
    public BlockSelectScreen() {
        this.grabsMouse = true;
    }

    private int getBlockOnScreen(int var1, int var2) {
        for (int var3 = 0; var3 < CreativeInventoryStorage.allowedBlocks.size(); ++var3) {
            int var4 = this.width / 2 + var3 % 9 * 24 + -108 - 3;
            int var5 = this.height / 2 + var3 / 9 * 24 + -60 + 3;
            if (var1 < var4 || var1 > var4 + 24 || var2 < var5 - 12 || var2 > var5 + 12) continue;
            return var3;
        }
        return -1;
    }

    @Override
    public void render(int var1, int var2) {
        var1 = this.getBlockOnScreen(var1, var2);
        BlockSelectScreen.drawFadingBox(this.width / 2 - 120, 30, this.width / 2 + 120, 200, -1878719232, -1070583712);
        if (var1 >= 0) {
            var2 = this.width / 2 + var1 % 9 * 24 + -108;
            int var3 = this.height / 2 + var1 / 9 * 24 + -60;
            BlockSelectScreen.drawFadingBox(var2 - 3, var3 - 8, var2 + 23, var3 + 24 - 6, -1862270977, -1056964609);
        }
        BlockSelectScreen.drawCenteredString(this.fontRenderer, "Select block", this.width / 2, 40, 0xFFFFFF);
        TextureManager var7 = this.minecraft.textureManager;
        ShapeRenderer var8 = ShapeRenderer.instance;
        var2 = var7.load("/terrain.png");
        GL11.glBindTexture(3553, var2);
        for (var2 = 0; var2 < CreativeInventoryStorage.allowedBlocks.size(); ++var2) {
            Block var4 = (Block)CreativeInventoryStorage.allowedBlocks.get(var2);
            GL11.glPushMatrix();
            int var5 = this.width / 2 + var2 % 9 * 24 + -108;
            int var6 = this.height / 2 + var2 / 9 * 24 + -60;
            GL11.glTranslatef(var5, var6, 0.0f);
            GL11.glScalef(10.0f, 10.0f, 10.0f);
            GL11.glTranslatef(1.0f, 0.5f, 8.0f);
            GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            if (var1 == var2) {
                GL11.glScalef(1.6f, 1.6f, 1.6f);
            }
            GL11.glTranslatef(-1.5f, 0.5f, 0.5f);
            GL11.glScalef(-1.0f, -1.0f, -1.0f);
            var8.begin();
            var4.renderFullbright(var8);
            var8.end();
            GL11.glPopMatrix();
        }
    }

    @Override
    protected final void onMouseClick(int var1, int var2, int var3) {
        if (var3 == 0) {
            this.minecraft.player.inventory.replaceSlot(this.getBlockOnScreen(var1, var2));
            this.minecraft.setCurrentScreen(null);
        }
    }
}

