/*
 * 
 */
package net.classic.remastered.game.entity.monster.ai.base.goal;

public class JumpAttackAI
extends NeutralAI {


    public JumpAttackAI() {
        this.runSpeed *= 0.8f;
    }

    @Override
    protected void jumpFromGround() {
        if (this.attackTarget == null) {
            super.jumpFromGround();
        } else {
            this.mob.xd = 0.0f;
            this.mob.zd = 0.0f;
            this.mob.moveRelative(0.0f, 1.0f, 0.6f);
            this.mob.yd = 0.5f;
        }
    }
}

