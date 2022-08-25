/*
 * 
 */
package net.classic.remastered.game.player;

import net.classic.remastered.client.settings.GameSettings;
import net.classic.remastered.game.player.InputHandler;

public class InputHandlerImpl
extends InputHandler {
    private boolean[] keyStates = new boolean[100];
    private GameSettings settings;

    public InputHandlerImpl(GameSettings gameSettings) {
        this.settings = gameSettings;
    }

    @Override
    public void updateMovement() {
        this.xxa = 0.0f;
        this.yya = 0.0f;
        if (this.keyStates[0]) {
            this.yya -= 1.0f;
        }
        if (this.keyStates[1]) {
            this.yya += 1.0f;
        }
        if (this.keyStates[2]) {
            this.xxa -= 1.0f;
        }
        if (this.keyStates[3]) {
            this.xxa += 1.0f;
        }
        this.jumping = this.keyStates[4];
        this.running = this.keyStates[5];
    }

    @Override
    public void resetKeys() {
        for (int i = 0; i < this.keyStates.length; ++i) {
            this.keyStates[i] = false;
        }
    }

    @Override
    public void setKeyState(int key, boolean state) {
        int index = -1;
        if (key == this.settings.forwardKey.key) {
            index = 0;
        }
        if (key == this.settings.backKey.key) {
            index = 1;
        }
        if (key == this.settings.leftKey.key) {
            index = 2;
        }
        if (key == this.settings.rightKey.key) {
            index = 3;
        }
        if (key == this.settings.jumpKey.key) {
            index = 4;
        }
        if (key == this.settings.runKey.key) {
            index = 5;
        }
        if (index >= 0) {
            this.keyStates[index] = state;
        }
    }
}

