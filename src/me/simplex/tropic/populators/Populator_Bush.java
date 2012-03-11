package me.simplex.tropic.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.Vector;

public class Populator_Bush extends BlockPopulator {

	/* (non-Javadoc)
	 * @see org.bukkit.generator.BlockPopulator#populate(org.bukkit.World, java.util.Random, org.bukkit.Chunk)
	 */
	@Override
	public void populate(World world, Random rnd, Chunk source) {
		int runs = rnd.nextInt(7) + 4;
		for (int i = 0; i <= runs; i++) {
			int x_bush = rnd.nextInt(16);
			int z_bush = rnd.nextInt(16);
		
			Block start = getHighestBlock(source, x_bush, z_bush);

			if (start != null) {
				//System.out.println("Bush! " + x_tree + ", " + z_tree);
				createBush(start.getLocation(), rnd);			
			}
		}
	}

	/**
	 * @param loc
	 * @param rnd
	 */
	private void createBush(Location loc, Random rnd) {
		Block toHandle = loc.getBlock();
		toHandle = loc.getBlock();
		toHandle.setType(Material.LOG);
		createLeaves(toHandle, rnd);
	}

	/**
	 * @param block
	 * @param rnd
	 * @param leaves
	 */
	private void createLeaves(Block block, Random rnd) {
		int radius = rnd.nextInt(3) + 2;
		int radius_squared = radius * radius;
		Location center = block.getLocation();
		Vector c = new Vector(0, 0, 0);
		for (int x = -radius; x <= radius; x++)
			for (int z = -radius; z <= radius; z++)
				for (int y = 0; y <= radius - (radius == 4 ? 2 : 1); y++) {
					// Calculate 3 dimensional distance
					Vector v = new Vector(x, y, z);
					// If it's within this radius gen the sphere
					if (c.distanceSquared(v) <= radius_squared) {
						Block b = center.getBlock().getRelative(x, y, z);
						if (b.getType() == Material.AIR || b.getType() == Material.VINE){
							b.setType(Material.LEAVES);
						}
					}
				}
	}
	
	/**
	 * Iteratively determines the highest grass block
	 * @param world
	 * @param x
	 * @param z
	 * @return Block highest non-air
	 */
	private Block getHighestBlock(Chunk chunk, int x, int z) {
		Block block = null;
		// Return the highest block
		for(int i=chunk.getWorld().getMaxHeight(); i>=0; i--)
			if((block = chunk.getBlock(x, i, z)).getTypeId() == 2)
				return block;
		// And as a matter of completeness, return the lowest point
		return block;
	}
}
