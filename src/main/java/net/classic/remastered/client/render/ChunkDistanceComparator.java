/*
 * 
 */
package net.classic.remastered.client.render;

import java.util.Comparator;

import net.classic.remastered.client.render.Chunk;
import net.classic.remastered.game.player.Player;

public class ChunkDistanceComparator
implements Comparator {
    private Player player;

    public ChunkDistanceComparator(Player player) {
        this.player = player;
    }

    public int compare(Object o1, Object o2) {
        float otherSqDist;
        Chunk chunk = (Chunk)o1;
        Chunk other = (Chunk)o2;
        float sqDist = chunk.distanceSquared(this.player);
        if (sqDist == (otherSqDist = other.distanceSquared(this.player))) {
            return 0;
        }
        if (sqDist > otherSqDist) {
            return -1;
        }
        return 1;
    }
}

