/*
 * 
 */
package net.classic.remastered.game.entity.other;

import net.classic.remastered.client.render.ShapeRenderer;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.other.TakeEntityAnim;
import net.classic.remastered.game.phys.AABB;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;
import net.dungland.util.MathHelper;

import java.util.List;
import org.lwjgl.opengl.GL11;

public class NewArrow
extends Entity {

    private float xd;
    private float yd;
    private float zd;
    private float yRot;
    private float xRot;
    private float yRotO;
    private float xRotO;
    private boolean hasHit = false;
    private int stickTime = 0;
    private Entity owner;
    private int time = 0;
    private int type = 0;
    private float gravity = 0.0f;
    private int damage;

    public NewArrow(World var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
        super(var1);
        float n2;
        float n;
        this.owner = var2;
        this.setSize(0.3f, 0.5f);
        this.heightOffset = this.bbHeight / 2.0f;
        this.damage = 3;
        if (!(var2 instanceof Player)) {
            this.type = 1;
        } else {
            this.damage = 7;
        }
        this.heightOffset = 0.25f;
        float var9 = MathHelper.cos(-var6 * ((float)Math.PI / 180) - (float)Math.PI);
        float var10 = MathHelper.sin(-var6 * ((float)Math.PI / 180) - (float)Math.PI);
        var6 = MathHelper.cos(-var7 * ((float)Math.PI / 180));
        var7 = MathHelper.sin(-var7 * ((float)Math.PI / 180));
        this.slide = false;
        this.gravity = 1.0f / var8;
        this.xo -= var9 * 0.2f;
        this.zo += var10 * 0.2f;
        this.xd = var10 * var6 * var8;
        this.yd = var7 * var8;
        this.zd = var9 * var6 * var8;
        this.setPos(var3 -= var9 * 0.2f, var4, var5 += var10 * 0.2f);
        var9 = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
        this.yRot = n = (float)(Math.atan2(this.xd, this.zd) * 180.0 / 3.1415927410125732);
        this.yRotO = n;
        this.xRot = n2 = (float)(Math.atan2(this.yd, var9) * 180.0 / 3.1415927410125732);
        this.xRotO = n2;
        this.makeStepSound = false;
    }

    @Override
    public void tick() {
        block12: {
            block10: {
                block11: {
                    ++this.time;
                    this.xRotO = this.xRot;
                    this.yRotO = this.yRot;
                    this.xo = this.x;
                    this.yo = this.y;
                    this.zo = this.z;
                    if (!this.hasHit) break block10;
                    ++this.stickTime;
                    if (this.type != 0) break block11;
                    if (this.stickTime >= 300 && Math.random() < (double)0.01f) {
                        this.remove();
                    }
                    break block12;
                }
                if (this.type != 1 || this.stickTime < 20) break block12;
                this.remove();
                break block12;
            }
            this.xd *= 0.998f;
            this.yd *= 0.998f;
            this.zd *= 0.998f;
            this.yd -= 0.02f * this.gravity;
            int var1 = (int)(MathHelper.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd) / 0.2f + 1.0f);
            float var2 = this.xd / (float)var1;
            float var3 = this.yd / (float)var1;
            float var4 = this.zd / (float)var1;
            for (int var5 = 0; var5 < var1 && !this.collision; ++var5) {
                AABB var6 = this.bb.expand(var2, var3, var4);
                if (this.level.getCubes(var6).size() > 0) {
                    this.collision = true;
                }
                List var7 = this.level.blockMap.getEntities(this, var6);
                for (int var8 = 0; var8 < var7.size(); ++var8) {
                    Entity var9 = (Entity)var7.get(var8);
                    if (!var9.isShootable() || var9 == this.owner && this.time <= 5) continue;
                    var9.hurt(this, this.damage);
                    this.collision = true;
                    this.remove();
                    return;
                }
                if (this.collision) continue;
                this.bb.move(var2, var3, var4);
                this.x += var2;
                this.y += var3;
                this.z += var4;
                this.blockMap.moved(this);
            }
            if (this.collision) {
                this.hasHit = true;
                float xd = 0.0f;
                this.zd = 0.0f;
                this.yd = 0.0f;
                this.xd = 0.0f;
                this.level.playSound("random.drr", this, 0.5f, 0.8f + (float)Math.random());
            }
            if (!this.hasHit) {
                float var10 = MathHelper.sqrt(this.xd * this.xd + this.zd * this.zd);
                this.yRot = (float)(Math.atan2(this.xd, this.zd) * 180.0 / 3.1415927410125732);
                this.xRot = (float)(Math.atan2(this.yd, var10) * 180.0 / 3.1415927410125732);
                while (this.xRot - this.xRotO < -180.0f) {
                    this.xRotO -= 360.0f;
                }
                while (this.xRot - this.xRotO >= 180.0f) {
                    this.xRotO += 360.0f;
                }
                while (this.yRot - this.yRotO < -180.0f) {
                    this.yRotO -= 360.0f;
                }
                while (this.yRot - this.yRotO >= 180.0f) {
                    this.yRotO += 360.0f;
                }
            }
        }
    }

    @Override
    public void render(TextureManager var1, float var2) {
        this.textureId = var1.load("/item/arrows.png");
        GL11.glBindTexture(3553, this.textureId);
        float var3 = this.level.getBrightness((int)this.x, (int)this.y, (int)this.z);
        GL11.glPushMatrix();
        GL11.glColor4f(var3, var3, var3, 1.0f);
        GL11.glTranslatef(this.xo + (this.x - this.xo) * var2, this.yo + (this.y - this.yo) * var2 - this.heightOffset / 2.0f, this.zo + (this.z - this.zo) * var2);
        GL11.glRotatef(this.yRotO + (this.yRot - this.yRotO) * var2 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(this.xRotO + (this.xRot - this.xRotO) * var2, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        ShapeRenderer var4 = ShapeRenderer.instance;
        var2 = 0.5f;
        float var5 = (float)(0 + this.type * 10) / 32.0f;
        float var6 = (float)(5 + this.type * 10) / 32.0f;
        float var7 = 0.15625f;
        float var8 = (float)(5 + this.type * 10) / 32.0f;
        float var9 = (float)(10 + this.type * 10) / 32.0f;
        float var10 = 0.05625f;
        GL11.glScalef(0.05625f, 0.05625f, 0.05625f);
        GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
        var4.begin();
        var4.vertexUV(-7.0f, -2.0f, -2.0f, 0.0f, var8);
        var4.vertexUV(-7.0f, -2.0f, 2.0f, 0.15625f, var8);
        var4.vertexUV(-7.0f, 2.0f, 2.0f, 0.15625f, var9);
        var4.vertexUV(-7.0f, 2.0f, -2.0f, 0.0f, var9);
        var4.end();
        GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
        var4.begin();
        var4.vertexUV(-7.0f, 2.0f, -2.0f, 0.0f, var8);
        var4.vertexUV(-7.0f, 2.0f, 2.0f, 0.15625f, var8);
        var4.vertexUV(-7.0f, -2.0f, 2.0f, 0.15625f, var9);
        var4.vertexUV(-7.0f, -2.0f, -2.0f, 0.0f, var9);
        var4.end();
        for (int var11 = 0; var11 < 4; ++var11) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, -0.05625f, 0.0f);
            var4.vertexUV(-8.0f, -2.0f, 0.0f, 0.0f, var5);
            var4.vertexUV(8.0f, -2.0f, 0.0f, var2, var5);
            var4.vertexUV(8.0f, 2.0f, 0.0f, var2, var6);
            var4.vertexUV(-8.0f, 2.0f, 0.0f, 0.0f, var6);
            var4.end();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void awardKillScore(Entity var1, int var2) {
        this.owner.awardKillScore(var1, var2);
    }

    public Entity getOwner() {
        return this.owner;
    }

    @Override
    public void playerTouch(Entity var1) {
        Player var2 = (Player)var1;
        if (this.hasHit && this.owner == var2 && var2.arrows < 99) {
            this.level.addEntity(new TakeEntityAnim(this.level, this, var2));
            this.level.playSound2("random.pop", this, 0.5f, 0.8f + (float)Math.random());
            Player player = var2;
            ++player.arrows;
            this.remove();
        }
    }
}

