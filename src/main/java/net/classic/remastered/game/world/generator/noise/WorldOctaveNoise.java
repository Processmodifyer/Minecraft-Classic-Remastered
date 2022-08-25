package net.classic.remastered.game.world.generator.noise;

import java.util.Random;

public class WorldOctaveNoise extends WorldNoise
{
	public WorldOctaveNoise(Random random, int octaves)
	{
		this.octaves = octaves;
		perlin = new WorldPerlinNoise[octaves];

		for(int count = 0; count < octaves; count++)
		{
			perlin[count] = new WorldPerlinNoise(random);
		}

	}

	@Override
	public double compute(double x, double z)
	{
		double result = 0.0D;
		double noiseLevel = 1.0D; //unknown0

		for(int count = 0; count < octaves; count++)
		{
			result += perlin[count].compute(x / noiseLevel, z / noiseLevel) * noiseLevel;

			noiseLevel *= 2.0D;
		}

		return result;
	}

	private WorldPerlinNoise[] perlin;
	private int octaves;
}