package net.classic.remastered.game.world.liquid;

public class LiquidType
{
    private LiquidType[] values;
    public static final LiquidType NOT_LIQUID;
    public static final LiquidType WATER;
    public static final LiquidType LAVA;
    
    static {
        NOT_LIQUID = new LiquidType(0);
        WATER = new LiquidType(1);
        LAVA = new LiquidType(2);
    }
    
    private LiquidType(final int type) {
        (this.values = new LiquidType[4])[type] = this;
    }
}
