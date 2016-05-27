package tbsc.techy.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * World generator for generating ores in the world.
 * Works by giving an instance of the block to generate.
 *
 * Created by tbsc on 5/26/16.
 */
public class OreWorldGenerator implements IWorldGenerator {

    private IBlockState blockToGenerate;
    private int blocksPerVein;

    public OreWorldGenerator(IBlockState blockToGenerate, int blocksPerVein) {
        this.blockToGenerate = blockToGenerate;
        this.blocksPerVein = blocksPerVein;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 0:
                generateOverworld(chunkX, chunkZ, random, world);
                break;
        }
    }

    private void generateOverworld(int chunkX, int chunkZ, Random rand, World world) {
        for(int k = 0; k < 10; k++){
            int firstBlockXCoord = chunkX + rand.nextInt(16);
            int firstBlockYCoord = rand.nextInt(64);
            int firstBlockZCoord = chunkZ + rand.nextInt(16);

            new WorldGenMinable(blockToGenerate, blocksPerVein).generate(world, rand, new BlockPos(firstBlockXCoord, firstBlockYCoord, firstBlockZCoord));
        }
    }

}
