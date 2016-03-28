package tbsc.techy.machine.furnace;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tbsc.techy.block.BlockBasePoweredMachine;

/**
 * Created by tbsc on 3/27/16.
 */
public class BlockPoweredFurnace extends BlockBasePoweredMachine {

    public BlockPoweredFurnace() {
        super(Material.iron, "blockPoweredFurnace");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePoweredFurnace();
    }

}
