/*
 * 
 */
package net.classic.remastered.client.gui;

import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.gui.GuiScreen;
import net.classic.remastered.client.render.ShapeRenderer;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.player.inventory.InventoryValues;
import net.classic.remastered.game.world.tile.Block;

public final class SurvivalInventoryScreen
extends GuiScreen {
    public SurvivalInventoryScreen() {
        this.grabsMouse = true;
    }

    private int getBlockOnScreen(int var1, int var2) {
        for (int i = 0; i < InventoryValues.allowedBlocks.size(); ++i) {
            int var3 = this.width / 2 + i % 13 * 24 - 156 - 3;
            int var4 = this.height / 2 + i / 13 * 24 - 80 + 3;
            if (var1 < var3 || var1 > var3 + 24 || var2 < var4 - 12 || var2 > var4 + 12) continue;
            return i;
        }
        return -1;
    }

    @Override
    public final void render(int var1, int var2) {
        var1 = this.getBlockOnScreen(var1, var2);
        SurvivalInventoryScreen.drawFadingBox(this.width / 2 - 168, 10, this.width / 2 + 168, 204, -1878719232, -1070583712);
        if (var1 >= 0) {
            var2 = this.width / 2 + var1 % 13 * 24 - 156;
            int var3 = this.height / 2 + var1 / 13 * 24 - 80;
            SurvivalInventoryScreen.drawFadingBox(var2 - 3, var3 - 8, var2 + 23, var3 + 24 - 6, -1862270977, -1056964609);
        }
        SurvivalInventoryScreen.drawCenteredString(this.fontRenderer, "Inventory", this.width / 2, 20, 0xFFFFFF);
        TextureManager var4 = this.minecraft.textureManager;
        ShapeRenderer var5 = ShapeRenderer.instance;
        for (var2 = 0; var2 < InventoryValues.allowedBlocks.size(); ++var2) {
            Block var6 = InventoryValues.allowedBlocks.get(var2);
            Integer count = InventoryValues.inventoryCount.get(var2);
            GL11.glPushMatrix();
            int var7 = this.width / 2 + var2 % 13 * 24 - 156;
            int var8 = this.height / 2 + var2 / 13 * 24 - 80;
            GL11.glTranslatef(var7, var8, 0.0f);
            GL11.glScalef(10.0f, 10.0f, 10.0f);
            GL11.glTranslatef(1.0f, 0.5f, 8.0f);
            GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            if (var1 == var2) {
                GL11.glScalef(1.6f, 1.6f, 1.6f);
            }
            GL11.glTranslatef(-1.5f, 0.5f, 0.5f);
            GL11.glScalef(-1.0f, -1.0f, -1.0f);
            int varterpn = var4.load("/terrain.png");
            if (this.minecraft.level.Item) {
                int varterpn1 = var4.load("/items.png");
            }
            GL11.glBindTexture(3553, varterpn);
            var5.begin();
            var6.renderFullbright(var5);
            var5.end();
            GL11.glPopMatrix();
            if (count <= 1) continue;
            String strang = "" + count;
            this.fontRenderer.render(strang, var7 + 19 - this.fontRenderer.getWidth(strang), var8 + 12, 0xFFFFFF);
        }
    }

    @Override
    protected void onKeyPress(char var1, int var2) {
        this.minecraft.setCurrentScreen(null);
        this.minecraft.grabMouse();
    }

    @Override
    protected final void onMouseClick(int var1, int var2, int var3) {
        if (var3 == 0) {
            int index = this.getBlockOnScreen(var1, var2);
            if (index != -1) {
                int idxblk = this.minecraft.player.inventory.slots[this.minecraft.player.inventory.selected];
                if (idxblk > 0 && this.minecraft.player.inventory.getSlot(InventoryValues.allowedBlocks.get((int)index).id) < 0) {
                    Block get = Block.blocks[idxblk];
                    int old = this.minecraft.player.inventory.count[this.minecraft.player.inventory.selected];
                    InventoryValues.addItem(get, old);
                }
                if (this.minecraft.player.inventory.getSlot(InventoryValues.allowedBlocks.get((int)index).id) >= 0) {
                    int n;
                    int idx = this.minecraft.player.inventory.getSlot(InventoryValues.allowedBlocks.get((int)index).id);
                    int[] count = this.minecraft.player.inventory.count;
                    int n2 = n = idx;
                    count[n2] = count[n2] + InventoryValues.inventoryCount.get(index);
                    this.minecraft.player.inventory.selected = idx;
                } else {
                    this.minecraft.player.inventory.replaceSlot(InventoryValues.allowedBlocks.get(index));
                    this.minecraft.player.inventory.count[this.minecraft.player.inventory.selected] = InventoryValues.inventoryCount.get(index);
                }
                InventoryValues.inventoryCount.remove(index);
                InventoryValues.allowedBlocks.remove(index);
            } else if (this.minecraft.player.inventory.slots[this.minecraft.player.inventory.selected] != -1) {
                Block get2 = Block.blocks[this.minecraft.player.inventory.slots[this.minecraft.player.inventory.selected]];
                int old2 = this.minecraft.player.inventory.count[this.minecraft.player.inventory.selected];
                this.minecraft.player.inventory.slots[this.minecraft.player.inventory.selected] = -1;
                this.minecraft.player.inventory.count[this.minecraft.player.inventory.selected] = 0;
                InventoryValues.addItem(get2, old2);
            }
            this.minecraft.level.inv = InventoryValues.getList();
        }
        this.minecraft.setCurrentScreen(null);
    }
}

