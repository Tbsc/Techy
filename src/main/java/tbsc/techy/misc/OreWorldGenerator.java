package tbsc.techy.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderHell;
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
    private int maxHeight;
    private int blocksPerChunk;

    public OreWorldGenerator(IBlockState blockToGenerate, int blocksPerVein, int maxHeight, int blocksPerChunk) {
        this.blockToGenerate = blockToGenerate;
        this.blocksPerVein = blocksPerVein;
        this.maxHeight = maxHeight;
        this.blocksPerChunk = blocksPerChunk;
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!(chunkGenerator instanceof ChunkProviderHell) && !(chunkGenerator instanceof ChunkProviderEnd)) {
            for(int i = 0; i < blocksPerChunk; i++){
                int firstBlockXCoord = chunkX * 16 + rand.nextInt(16);
                int firstBlockYCoord = rand.nextInt(maxHeight);
                int firstBlockZCoord = chunkZ * 16 + rand.nextInt(16);

                new WorldGenMinable(blockToGenerate, blocksPerVein, BlockMatcher.forBlock(Blocks.STONE)).generate(world, rand, new BlockPos(firstBlockXCoord, firstBlockYCoord, firstBlockZCoord));
            }
        }
    }

}
