package net.classic.remastered.game.world;

public final class NextTickListEntry
{
    public int x;
    public int y;
    public int z;
    public int block;
    public int ticks;
    
    public NextTickListEntry(final int var1, final int var2, final int var3, final int var4) {
        this.x = var1;
        this.y = var2;
        this.z = var3;
        this.block = var4;
    }
}
