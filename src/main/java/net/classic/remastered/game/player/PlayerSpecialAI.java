/*
 * 
 */
package net.classic.remastered.game.player;

import net.classic.remastered.game.entity.monster.ai.base.BasicAI;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.tile.Block;
import net.dungland.util.MathHelper;

public class PlayerSpecialAI
extends BasicAI {

    private Player player;

    public PlayerSpecialAI(Player player) {
        this.player = player;
    }

    @Override
    protected void update() {
        this.jumping = this.player.input.jumping;
        this.xxa = this.player.input.xxa;
        this.yya = this.player.input.yya;
        this.running = this.player.input.running;
        float var1 = MathHelper.sin(this.player.yRot * (float)Math.PI / 180.0f);
        float var2 = MathHelper.cos(this.player.yRot * (float)Math.PI / 180.0f);
        int var4 = (int)(this.mob.x + (var1 *= -0.7f));
        int var3 = (int)(this.mob.y - 2.0f);
        int var5 = (int)(this.mob.z + (var2 *= 0.7f));
        if (this.player.grazing) {
            if (this.level.getTile(var4, var3, var5) != Block.GRASS.id) {
                this.player.grazing = false;
            } else {
                if (++this.player.grazingTime == 60) {
                	this.level.explode(player, var2, var3, var4, var5);
                    this.random.nextInt(5);
                }
                this.xxa = 0.0f;
                this.yya = 0.0f;
                this.mob.xRot = 40 + this.player.grazingTime / 2 % 2 * 10;
            }
        } else {
            if (this.level.getTile(var4, var3, var5) == Block.GRASS.id) {
                this.player.grazing = true;
                this.player.grazingTime = 0;
            }
            super.update();
        }
    }
}

