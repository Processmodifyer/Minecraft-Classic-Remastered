package net.classic.remastered.game.world.tile;

public final class OreBlock extends Block
{
    public OreBlock(final int var1, final int var2) {
        super(var1, var2);
    }
    
    @Override
    public final int getDrop() {
        return (this == OreBlock.COAL_ORE) ? OreBlock.SLAB.id : ((this == OreBlock.GOLD_ORE) ? OreBlock.GOLD_BLOCK.id : ((this == OreBlock.IRON_ORE) ? OreBlock.IRON_BLOCK.id  : ((this == OreBlock.REDSTONE_ORE) ? OreBlock.REDSTONE_BLOCK.id : this.id)));
    }
    
    @Override
    public final int getDropCount() {
        return OreBlock.random.nextInt(3) + 1;
    }
}
