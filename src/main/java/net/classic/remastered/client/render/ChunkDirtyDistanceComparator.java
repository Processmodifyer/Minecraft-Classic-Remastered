/*
 * 
 */
package net.classic.remastered.client.render;

import java.util.Comparator;

import net.classic.remastered.client.render.Chunk;
import net.classic.remastered.game.player.Player;

public class ChunkDirtyDistanceComparator
implements Comparator {
    private Player player;

    public ChunkDirtyDistanceComparator(Player player) {
        this.player = player;
    }

    public int compare(Object o1, Object o2) {
        Chunk chunk = (Chunk)o1;
        Chunk other = (Chunk)o2;
        if (chunk.visible || !other.visible) {
            if (other.visible) {
                float otherSqDist;
                float sqDist = chunk.distanceSquared(this.player);
                if (sqDist == (otherSqDist = other.distanceSquared(this.player))) {
                    return 0;
                }
                if (sqDist > otherSqDist) {
                    return -1;
                }
                return 1;
            }
            return 1;
        }
        return -1;
    }
}

