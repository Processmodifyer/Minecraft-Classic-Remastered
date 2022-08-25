/*
 * 
 */
package net.classic.remastered.game.entity.other;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.other.NonLivingEntityModel;
import net.classic.remastered.game.entity.other.TakeEntityAnim;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.item.Item;
import net.classic.remastered.game.world.tile.Block;
import net.dungland.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class NonLivingEntity
extends Entity {

    private static NonLivingEntityModel[] models = new NonLivingEntityModel[256];
    private float xd;
    private float yd;
    private float zd;
    private float rot;
    private int resource;
    private int tickCount;
    private int age = 0;
	private Block block;
	
	public NonLivingEntity(World level1, float x, float y, float z, int block) {
        super(level1);
        this.setSize(0.25f, 0.25f);
        this.heightOffset = this.bbHeight / 2.0f;
        this.setPos(x, y, z);
        this.resource = block;
        this.rot = (float)(Math.random() * 360.0);
        this.xd = (float)(Math.random() * (double)0.2f - (double)0.1f);
        this.yd = 0.2f;
        this.zd = (float)(Math.random() * (double)0.2f - (double)0.1f);
        this.makeStepSound = false;
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
        }
        ++this.tickCount;
        ++this.age;
        if (this.age >= 6000) {
            this.remove();
        }
    }

    public void render(TextureManager textureManager, float unknown0) {
        this.textureId = textureManager.load("/terrain.png");
        GL11.glBindTexture(3553, this.textureId);
        float brightness = this.level.getBrightness((int)this.x, (int)this.y, (int)this.z);
        float unknown1 = this.rot + ((float)this.tickCount + unknown0) * 3.0f;
        GL11.glPushMatrix();
        GL11.glColor4f(brightness, brightness, brightness, 1.0f);
        brightness = MathHelper.sin(unknown1 / 10.0f);
        float unknown2 = brightness * 0.1f + 0.1f;
        GL11.glTranslatef(this.xo + (this.x - this.xo) * unknown0, this.yo + (this.y - this.yo) * unknown0 + unknown2, this.zo + (this.z - this.zo) * unknown0);
        GL11.glRotatef(unknown1, 0.0f, 1.0f, 0.0f);
        models[this.resource].generateList(block);
        brightness = brightness * 0.5f + 0.5f;
        brightness *= brightness;
        brightness *= brightness;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, brightness * 0.4f);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(3008);
        models[this.resource].generateList(block);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        GL11.glEnable(3553);
    }

    @Override
    public void playerTouch(Entity entity) {
        Player player = (Player)entity;
        if (player.addResource(this.resource)) {
            TakeEntityAnim takeEntityAnim = new TakeEntityAnim(this.level, this, player);
            this.level.addEntity(takeEntityAnim);
            this.remove();
        }
 
    }

    public static void initModels() {    
        for (int unknown0 = 0; unknown0 < 256; ++unknown0) {
            Block var1 = Block.blocks[unknown0];
            if (var1 == null) continue;
            NonLivingEntity.models[unknown0] = new NonLivingEntityModel(var1.textureId, unknown0);
        }
    }
}

