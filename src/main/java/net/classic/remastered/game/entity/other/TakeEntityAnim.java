/*
 * 
 */
package net.classic.remastered.game.entity.other;

import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.world.World;

public class TakeEntityAnim
extends Entity {
    private static final long serialVersionUID = 1L;
    private int time = 0;
    private Entity item;
    private Entity player;
    private float xorg;
    private float yorg;
    private float zorg;

    public TakeEntityAnim(World level1, Entity item, Entity player) {
        super(level1);
        this.item = item;
        this.player = player;
        this.setSize(1.0f, 1.0f);
        this.xorg = item.x;
        this.yorg = item.y;
        this.zorg = item.z;
    }

    @Override
    public void tick() {
        ++this.time;
        if (this.time >= 3) {
            this.remove();
        }
        this.level.playSound2("random.pop", this, 0.5f, 0.8f + (float)Math.random());
        float distance = (float)this.time / 3.0f;
        distance *= distance;
        this.xo = this.item.xo = this.item.x;
        this.yo = this.item.yo = this.item.y;
        this.zo = this.item.zo = this.item.z;
        this.x = this.item.x = this.xorg + (this.player.x - this.xorg) * distance;
        this.y = this.item.y = this.yorg + (this.player.y - 1.0f - this.yorg) * distance;
        this.z = this.item.z = this.zorg + (this.player.z - this.zorg) * distance;
        this.setPos(this.x, this.y, this.z);
    }

    @Override
    public void render(TextureManager textureManager, float unknown0) {
        this.item.render(textureManager, unknown0);
    }
}

