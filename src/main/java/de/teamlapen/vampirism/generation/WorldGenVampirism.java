package de.teamlapen.vampirism.generation;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import de.teamlapen.vampirism.entity.EntityVampireHunter;
import de.teamlapen.vampirism.generation.structures.GenerateBloodAltar;
import de.teamlapen.vampirism.util.BALANCE;
import de.teamlapen.vampirism.util.REFERENCE;

/**
 * 
 * @author WILLIAM
 *
 */
public class WorldGenVampirism implements IWorldGenerator {

	/**
	 * Generates entities
	 * 
	 * @param world
	 *            World
	 * @param random
	 *            Random
	 * @param x
	 *            xCoord
	 * @param z
	 *            ZCoord
	 */
	private void addEntities(World world, Random random, int x, int z) {

		int y = world.getHeightValue(x, z);
		Village v = world.villageCollectionObj.findNearestVillage(x, y, z, 0);
		if (v == null) {
			return;
		}

		int r = v.getVillageRadius();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(v.getCenter().posX - r, 0, v.getCenter().posZ - r, v.getCenter().posX + r,
				world.getActualHeight(), v.getCenter().posZ + r);

		int spawnedHunter = world.getEntitiesWithinAABB(EntityVampireHunter.class, box).size();
		// Logger.i("Test", "Found village at: " + v.getCenter().posX + " " +
		// v.getCenter().posY
		// + " " + v.getCenter().posZ + " with " + spawnedHunter + " Hunters");

		// All hunters are currently spawning at the center of the village
		for (int i = 1; i < 20 && spawnedHunter < BALANCE.MOBPROP.VAMPIRE_HUNTER_MAX_PER_VILLAGE; i++) {
			int x1 = v.getCenter().posX + world.rand.nextInt((int) (1.2 * r)) - (int) (0.6 * r);
			int z1 = v.getCenter().posZ + world.rand.nextInt((int) (1.2 * r)) - (int) (0.6 * r);

			int y1 = world.getHeightValue(x1, z1);
			if (v.isInRange(x1, y1, z1)) {
				// Logger.i("test", "going to spawn hunter!");
				Entity e = EntityList.createEntityByName(REFERENCE.ENTITY.VAMPIRE_HUNTER_NAME, world);
				e.setLocationAndAngles(x1, y1, z1, 0.0F, 0.0F);
				((EntityVampireHunter) e).setFoundHome();
				if (((EntityVampireHunter) e).getCanSpawnHere()) {
					((EntityVampireHunter) e).setHomeArea(v.getCenter().posX, v.getCenter().posY, v.getCenter().posZ, r);
					world.spawnEntityInWorld(e);
					// Logger.i("HunterSpawn", "Spawned Hunter at: " + x1 + " "
					// + y1 + " " + z1);

					spawnedHunter++;
				} else {
					e.setDead();
				}

			}
		}
	}

	private void addStructures(World world, Random random, int x, int z) {
		int chance = random.nextInt(1000);
		boolean generatedStructure = false; // needed when a 2nd structure is
											// added
		int spawnChance = 0;
		BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);

		spawnChance += GenerateBloodAltar.spawnChance;
		if (generatedStructure == false && chance < spawnChance) {
			if (biome == BiomeGenBase.swampland || biome == BiomeGenBase.roofedForest) {
				// Create Blood Altar
				int posX = x + random.nextInt(16);
				int posZ = z + random.nextInt(16);
				// Set y to center of altar
				int posY = world.getHeightValue(posX + 1, posZ + 1);
				generatedStructure = new GenerateBloodAltar().generate(world, random, posX, posY, posZ);
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		switch (world.provider.dimensionId) {
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
		case 0:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
		case 1:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}

	}

	private void generateEnd(World world, Random random, int x, int z) {

	}

	private void generateNether(World world, Random random, int x, int z) {

	}

	private void generateSurface(World world, Random random, int x, int z) {
		addStructures(world, random, x, z);
		addEntities(world, random, x + 8, z + 8);
	}
}
