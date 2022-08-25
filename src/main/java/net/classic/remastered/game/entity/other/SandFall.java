/*
 * 
 */
package net.classic.remastered.game.entity.other;

import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.render.ShapeRenderer;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;

public class SandFall
extends Entity {

    private float xd;
    private float yd;
    private float zd;
    private int id;

    public SandFall(World var1, float var2, float var3, float var4, int id) {
        super(var1);
        this.setSize(0.98f, 0.98f);
        this.heightOffset = this.bbHeight / 2.0f;
        this.setPos(var2, var3, var4);
        this.id = id;
        this.xd = 0.0f;
        this.yd = 0.0f;
        this.zd = 0.0f;
        this.makeStepSound = false;
        this.xo = var2;
        this.yo = var3;
        this.zo = var4;
    }

    @Override
    public void hurt(Entity var1, int var2) {
    }

    @Override
    public boolean isPickable() {
        return !this.removed;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.yd -= 0.04f;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.98f;
        this.yd *= 0.98f;
        this.zd *= 0.98f;
        if (this.onGround) {
            this.xd *= 0.7f;
            this.zd *= 0.7f;
            this.yd *= -0.5f;
            int Tile2 = this.level.getTile((int)this.x, (int)this.y, (int)this.z);
            if (Tile2 == 0 || Tile2 == Block.WATER.id || Tile2 == Block.STATIONARY_WATER.id || Tile2 == Block.LAVA.id || Tile2 == Block.STATIONARY_LAVA.id) {
                if (this.id == Block.SAND.id) {
                    this.level.setTile((int)this.x, (int)this.y, (int)this.z, Block.SAND.id);
                } else {
                    this.level.setTile((int)this.x, (int)this.y, (int)this.z, Block.GRAVEL.id);
                }
            } else if (Tile2 == Block.SAND.id || Tile2 == Block.GRAVEL.id) {
                this.level.setTile((int)this.x, (int)this.y, (int)this.z, this.id);
            }
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, this.id));
        }
        this.remove();
    }

    @Override
    public void playerTouch(Entity var1) {
    }

    @Override
    public void render(TextureManager var1, float var2) {
        int var3 = var1.load("/terrain.png");
        GL11.glBindTexture(3553, var3);
        float var4 = this.level.getBrightness((int)this.x, (int)this.y, (int)this.z);
        GL11.glPushMatrix();
        GL11.glColor4f(var4, var4, var4, 0.005f);
        GL11.glTranslatef(this.xo + (this.x - this.xo) * var2 - 0.5f, this.yo + (this.y - this.yo) * var2 - 0.5f, this.zo + (this.z - this.zo) * var2 - 0.5f);
        GL11.glPushMatrix();
        ShapeRenderer var5 = ShapeRenderer.instance;
        if (this.id == Block.SAND.id) {
            Block.SAND.renderPreview(var5);
        } else {
            Block.GRAVEL.renderPreview(var5);
        }
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        if (this.id == Block.SAND.id) {
            Block.SAND.renderPreview(var5);
        } else {
            Block.GRAVEL.renderPreview(var5);
        }
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}

