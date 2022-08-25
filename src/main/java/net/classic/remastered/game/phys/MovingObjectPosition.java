/*
 * 
 */
package net.classic.remastered.game.phys;

import net.classic.remastered.client.model.Vec3D;
import net.classic.remastered.game.entity.Entity;

public class MovingObjectPosition {
    public int entityPos;
    public int x;
    public int y;
    public int z;
    public int face;
    public Vec3D vec;
    public Entity entity;

    public MovingObjectPosition(int x, int y, int z, int side, Vec3D blockPos) {
        this.entityPos = 0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.face = side;
        this.vec = new Vec3D(blockPos.x, blockPos.y, blockPos.z);
    }

    public MovingObjectPosition(Entity entity) {
        this.entityPos = 1;
        this.entity = entity;
    }
}

