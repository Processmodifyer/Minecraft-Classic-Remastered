package net.classic.remastered.game.world.generator.noise;

public final class WorldCombinedNoise extends WorldNoise
{
    private WorldNoise noise1;
    private WorldNoise noise2;
    
    public WorldCombinedNoise(final WorldNoise noise1, final WorldNoise noise2) {
        this.noise1 = noise1;
        this.noise2 = noise2;
    }
    
    @Override
    public double compute(final double x, final double z) {
        return this.noise1.compute(x + this.noise2.compute(x, z), z);
    }
}
