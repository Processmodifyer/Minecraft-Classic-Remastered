/*
 * 
 */
package net.classic.remastered.game.entity.other;

import net.classic.remastered.client.particle.SmokeParticle;
import net.classic.remastered.client.particle.TerrainParticle;
import net.classic.remastered.client.render.ShapeRenderer;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.classic.remastered.game.entity.other.TakeEntityAnim;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;
import net.dungland.util.MathHelper;

import java.util.Random;
import org.lwjgl.opengl.GL11;

public class PrimedTnt
extends Entity {

    private float xd;
    private float yd;
    private float zd;
    public int life = 0;
    private boolean defused;

    public PrimedTnt(World level1, float x, float y, float z) {
        super(level1);
        this.setSize(0.98f, 0.98f);
        this.heightOffset = this.bbHeight / 2.0f;
        this.setPos(x, y, z);
        float unknown0 = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.xd = -MathHelper.sin(unknown0 * (float)Math.PI / 180.0f) * 0.02f;
        this.yd = 0.2f;
        this.zd = -MathHelper.cos(unknown0 * (float)Math.PI / 180.0f) * 0.02f;
        this.makeStepSound = false;
        this.life = 40;
        this.xo = x;
        this.yo = y;
        this.zo = z;
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
        if (!this.defused) {
            if (this.life-- > 0) {
                SmokeParticle smokeParticle = new SmokeParticle(this.level, this.x, this.y + 0.6f, this.z);
                this.level.particleEngine.spawnParticle(smokeParticle);
            } else {
                this.remove();
                Random random = new Random();
                float radius = 4.0f;
                this.level.explode(null, this.x, this.y, this.z, radius);
                this.level.playSound("random.explode", this, 1.0f, 0.8f + (float)Math.random() / 4.0f);
                for (int i = 0; i < 100; ++i) {
                    float unknown0 = (float)random.nextGaussian() * radius / 4.0f;
                    float unknown1 = (float)random.nextGaussian() * radius / 4.0f;
                    float unknown2 = (float)random.nextGaussian() * radius / 4.0f;
                    float unknown3 = MathHelper.sqrt(unknown0 * unknown0 + unknown1 * unknown1 + unknown2 * unknown2);
                    float unknown4 = unknown0 / unknown3 / unknown3;
                    float unknown5 = unknown1 / unknown3 / unknown3;
                    unknown3 = unknown2 / unknown3 / unknown3;
                    TerrainParticle terrainParticle = new TerrainParticle(this.level, this.x + unknown0, this.y + unknown1, this.z + unknown2, unknown4, unknown5, unknown3, Block.TNT);
                    this.level.particleEngine.spawnParticle(terrainParticle);
                }
            }
        }
    }

    @Override
    public void render(TextureManager textureManager, float unknown0) {
        int textureID = textureManager.load("/terrain.png");
        GL11.glBindTexture(3553, textureID);
        float brightness = this.level.getBrightness((int)this.x, (int)this.y, (int)this.z);
        GL11.glPushMatrix();
        GL11.glColor4f(brightness, brightness, brightness, 1.0f);
        GL11.glTranslatef(this.xo + (this.x - this.xo) * unknown0 - 0.5f, this.yo + (this.y - this.yo) * unknown0 - 0.5f, this.zo + (this.z - this.zo) * unknown0 - 0.5f);
        GL11.glPushMatrix();
        ShapeRenderer shapeRenderer = ShapeRenderer.instance;
        Block.TNT.renderPreview(shapeRenderer);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, (float)((this.life / 4 + 1) % 2) * 0.4f);
        if (this.life <= 16) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, (float)((this.life + 1) % 2) * 0.6f);
        }
        if (this.life <= 2) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        Block.TNT.renderPreview(shapeRenderer);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    @Override
    public void playerTouch(Entity entity) {
        Player player;
        if (this.defused && (player = (Player)entity).addResource(Block.TNT.id)) {
            TakeEntityAnim takeEntityAnim = new TakeEntityAnim(this.level, this, player);
            this.level.addEntity(takeEntityAnim);
            this.remove();
        }
    }

    @Override
    public void hurt(Entity entity, int damage) {
        if (!this.removed) {
            super.hurt(entity, damage);
            if (entity instanceof Player) {
                this.remove();
                this.level.playSound("random.fuse", this, 1.0f, 0.8f + (float)Math.random() / 4.0f);
                NonLivingEntity item = new NonLivingEntity(this.level, this.x, this.y, this.z, Block.TNT.id);
                this.level.addEntity(item);
            }
        }
    }

    @Override
    public boolean isPickable() {
        return !this.removed;
    }
}

