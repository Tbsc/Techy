package tbsc.techy.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.tile.TileBase;

/**
 * Created by tbsc on 3/27/16.
 */
public abstract class BlockBaseMachine extends BlockBase implements ITileEntityProvider {

    public BlockBaseMachine(Material material, String registryName) {
        super(material, registryName);
    }

    protected TileBase getTileBase(World world, BlockPos pos) {
        return (TileBase) world.getTileEntity(pos);
    }

}
