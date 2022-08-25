package net.classic.remastered.game.world.tile;

public final class LeavesBlock extends LeavesBaseBlock
{
    protected LeavesBlock(final int var1, final int var2) {
        super(18, 22, true);
    }
    
    @Override
    public final int getDropCount() {
        return (LeavesBlock.random.nextInt(10) == 0) ? 1 : 0;
    }
    
    @Override
    public final int getDrop() {
        return Block.SAPLING.id;
    }
}
