/*
 * 
 */
package net.classic.remastered.game.player;

import net.classic.remastered.game.entity.monster.ai.base.BasicAI;
import net.classic.remastered.game.player.Player;

public class Player$1
extends BasicAI {

    private Player player;

    public Player$1(Player player) {
        this.player = player;
    }

    @Override
    protected void update() {
        this.jumping = this.player.input.jumping;
        this.xxa = this.player.input.xxa;
        this.yya = this.player.input.yya;
        this.running = this.player.input.running;
    }
}

