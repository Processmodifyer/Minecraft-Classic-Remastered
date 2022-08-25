package net.classic.remastered.game.world.tile;

public enum Tile$SoundType
{
    none("none", 0, "none", 0, "-", 0.0f, 0.0f), 
    grass("grass", 1, "grass", 1, "grass", 0.6f, 1.0f), 
    cloth("cloth", 2, "cloth", 2, "grass", 0.7f, 1.2f), 
    gravel("gravel", 3, "gravel", 3, "gravel", 1.0f, 1.0f), 
    stone("stone", 4, "stone", 4, "stone", 1.0f, 1.0f), 
    metal("metal", 5, "metal", 5, "stone", 1.0f, 2.0f), 
    wood("wood", 6, "wood", 6, "wood", 1.0f, 1.0f);
    
    public final String name;
    private final float volume;
    private final float pitch;
    private static final Tile$SoundType[] values;
    
    static {
        values = new Tile$SoundType[] { Tile$SoundType.none, Tile$SoundType.grass, Tile$SoundType.cloth, Tile$SoundType.gravel, Tile$SoundType.stone, Tile$SoundType.metal, Tile$SoundType.wood };
    }
    
    private Tile$SoundType(final String s, final int n, final String var1, final int var2, final String var3, final float var4, final float var5) {
        this.name = var3;
        this.volume = var4;
        this.pitch = var5;
    }
    
    public final float getVolume() {
        return this.volume / (Block.random.nextFloat() * 0.4f + 1.0f) * 0.5f;
    }
    
    public final float getPitch() {
        return this.pitch / (Block.random.nextFloat() * 0.2f + 0.9f);
    }
}
