/*
 * 
 */
package net.classic.remastered.client.model;

import net.dungland.util.MathHelper;

public final class Vec3D {
    public float x;
    public float y;
    public float z;

    public Vec3D(float var1, float var2, float var3) {
        this.x = var1;
        this.y = var2;
        this.z = var3;
    }

    public final Vec3D subtract(Vec3D var1) {
        return new Vec3D(this.x - var1.x, this.y - var1.y, this.z - var1.z);
    }

    public final Vec3D normalize() {
        float var1 = MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return new Vec3D(this.x / var1, this.y / var1, this.z / var1);
    }

    public final Vec3D add(float var1, float var2, float var3) {
        return new Vec3D(this.x + var1, this.y + var2, this.z + var3);
    }

    public final float distance(Vec3D var1) {
        float var2 = var1.x - this.x;
        float var3 = var1.y - this.y;
        float var4 = var1.z - this.z;
        return MathHelper.sqrt(var2 * var2 + var3 * var3 + var4 * var4);
    }

    public final float distanceSquared(Vec3D var1) {
        float var2 = var1.x - this.x;
        float var3 = var1.y - this.y;
        float var4 = var1.z - this.z;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }

    public final Vec3D getXIntersection(Vec3D var1, float var2) {
        Vec3D vec3D;
        float var3 = var1.x - this.x;
        float var4 = var1.y - this.y;
        float var5 = var1.z - this.z;
        if (var3 * var3 < 1.0E-7f) {
            vec3D = null;
        } else {
            float f = 0;
            var2 = (var2 - this.x) / var3;
            vec3D = f >= 0.0f && var2 <= 1.0f ? new Vec3D(this.x + var3 * var2, this.y + var4 * var2, this.z + var5 * var2) : null;
        }
        return vec3D;
    }

    public final Vec3D getYIntersection(Vec3D var1, float var2) {
        Vec3D vec3D;
        float var3 = var1.x - this.x;
        float var4 = var1.y - this.y;
        float var5 = var1.z - this.z;
        if (var4 * var4 < 1.0E-7f) {
            vec3D = null;
        } else {
            float f = 0;
            var2 = (var2 - this.y) / var4;
            vec3D = f >= 0.0f && var2 <= 1.0f ? new Vec3D(this.x + var3 * var2, this.y + var4 * var2, this.z + var5 * var2) : null;
        }
        return vec3D;
    }

    public final Vec3D getZIntersection(Vec3D var1, float var2) {
        Vec3D vec3D;
        float f = 0;
        float var3 = var1.x - this.x;
        float var4 = var1.y - this.y;
        float var5 = var1.z - this.z;
        if (f * var5 < 1.0E-7f) {
            vec3D = null;
        } else {
            float f2 = 0;
            var2 = (var2 - this.z) / var5;
            vec3D = f2 >= 0.0f && var2 <= 1.0f ? new Vec3D(this.x + var3 * var2, this.y + var4 * var2, this.z + var5 * var2) : null;
        }
        return vec3D;
    }

    public final String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}

