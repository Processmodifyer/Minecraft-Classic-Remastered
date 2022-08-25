/*
 * 
 */
package net.classic.remastered.game.player;

import net.classic.remastered.client.model.HumanoidModel;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.client.settings.GameSettings;
import net.classic.remastered.client.settings.ThirdPersonMode;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.player.InputHandler;
import net.classic.remastered.game.player.Player$1;
import net.classic.remastered.game.player.inventory.Inventory;
import net.classic.remastered.game.world.World;
import net.dungland.util.MathHelper;

import java.awt.image.BufferedImage;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class Player
extends LivingEntity {
    private GameSettings settings;
    private float delta;
    public boolean hasFur = false;
    public boolean grazing = false;
    public int grazingTime = 0;
    public float graze;
    public float grazeO;

    public static final int MAX_HEALTH = 20;
    public static final int MAX_ARROWS = 99;
    public transient InputHandler input;
    public Inventory inventory = new Inventory();
    public byte userType = 0;
    public float oBob;
    public float bob;
    public int score = 0;
    public int arrows = 20;
    private static int newTextureId = -1;
    public static BufferedImage newTexture;

    public Player(World var1) {
        super(var1);
        if (var1 != null) {
            var1.player = this;
            var1.removeEntity(this);
            var1.addEntity(this);
        }
		this.heightOffset = 1.62F;
        this.health = 20;
        this.modelName = "humanoid";
        this.rotOffs = 180.0f;
        this.ai = new Player$1(this);
    }

    @Override
    public void aiStep() {
        List var3;
        this.inventory.tick();
        this.oBob = this.bob;
        this.input.updateMovement();
        super.aiStep();
        float var1 = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
        float var2 = (float)Math.atan(-this.yd * 0.2f) * 15.0f;
        if (var1 > 0.1f) {
            var1 = 0.1f;
        }
        if (!this.onGround || this.health <= 0) {
            var1 = 0.0f;
        }
        if (this.onGround || this.health <= 0) {
            var2 = 0.0f;
        }
        this.bob += (var1 - this.bob) * 0.4f;
        this.tilt += (var2 - this.tilt) * 0.8f;
        if (this.health > 0 && (var3 = this.level.findEntities(this, this.bb.grow(1.0f, 0.0f, 1.0f))) != null) {
            for (int var4 = 0; var4 < var3.size(); ++var4) {
                ((Entity)var3.get(var4)).playerTouch(this);
            }
        }
    }

    @Override
    public void bindTexture(TextureManager var1) {
        if (newTexture != null) {
            newTextureId = var1.load(newTexture);
            newTexture = null;
        }
        if (newTextureId < 0) {
            int var2 = var1.load("/char.png");
            GL11.glBindTexture(3553, var2);
        } else {
            int var2 = newTextureId;
            GL11.glBindTexture(3553, var2);
        }
    }

    @Override
    public void render(TextureManager var1, float var2) {

    }

    @Override
    public void resetPos() {
        this.heightOffset = 1.62f;
        this.setSize(0.6f, 1.8f);
        super.resetPos();
        if (this.level != null) {
            this.level.player = this;
        }
        this.health = 20;
        this.deathTime = 0;
    }

    @Override
    public void die(Entity var1) {
        this.setSize(0.2f, 0.2f);
        this.setPos(this.x, this.y, this.z);
        this.yd = 0.1f;
        if (var1 != null) {
            this.xd = -MathHelper.cos((this.hurtDir + this.yRot) * (float)Math.PI / 180.0f) * 0.1f;
            this.zd = -MathHelper.sin((this.hurtDir + this.yRot) * (float)Math.PI / 180.0f) * 0.1f;
        } else {
            this.zd = 0.0f;
            this.xd = 0.0f;
        }
        this.heightOffset = 0.1f;
    }

    @Override
    public boolean isShootable() {
        return true;
    }

    @Override
    public void awardKillScore(Entity var1, int var2) {
        this.score += var2;
    }

    @Override
    public void remove() {
    }

    @Override
    public void hurt(Entity var1, int var2) {
        if (!this.level.creativeMode) {
            super.hurt(var1, var2);
        }
    }

    @Override
    public boolean isCreativeModeAllowed() {
        return true;
    }

    public void releaseAllKeys() {
        this.input.resetKeys();
    }

    public void setKey(int var1, boolean var2) {
        this.input.setKeyState(var1, var2);
    }

    public boolean addResource(int var1) {
        return this.inventory.addResource(var1);
    }

    public int getScore() {
        return this.score;
    }

    public HumanoidModel getModel() {
        return (HumanoidModel)modelCache.getModel(this.modelName);
    }
}

