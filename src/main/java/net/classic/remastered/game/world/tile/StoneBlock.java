package net.classic.remastered.game.world.tile;

public final class StoneBlock extends Block
{
    public StoneBlock(final int var1, final int var2) {
        super(var1, var2);
    }
    
    @Override
    public final int getDrop() {
        return StoneBlock.COBBLESTONE.id;
    }
}
