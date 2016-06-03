package tbsc.techy.machine.powercell;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tbsc.techy.block.BlockBaseFacingMachine;

/**
 * Generic block for adding power cells to the game.
 * This class is built so the only thing you need to do is register an instance of this
 * class, and it'll take care of everything (mostly, I can't do tile entities).
 *
 * Created by tbsc on 6/3/16.
 */
public class BlockGenericPowerCell extends BlockBaseFacingMachine {

    protected TilePowerCellBase tile;

    public BlockGenericPowerCell(String registryName, int capacity, int maxTransfer, TilePowerCellBase tile) {
        super(Material.IRON, registryName, 2);
        this.tile = tile;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return tile;
    }

}
