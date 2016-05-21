package tbsc.techy.machine.generator.coal;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tbsc.techy.block.BlockBaseFacingMachine;

/**
 * Coal generator for RF energy
 *
 * Created by tbsc on 5/20/16.
 */
public class BlockCoalGenerator extends BlockBaseFacingMachine {

    public BlockCoalGenerator() {
        super(Material.CIRCUITS, "blockCoalGenerator", 5);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCoalGenerator();
    }

}
