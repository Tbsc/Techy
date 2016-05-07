package tbsc.techy.block;

import net.minecraft.block.material.Material;

/**
 * NOTE: This is a REAL block, and a crafting component. For the base class,
 * look at {@link BlockBaseMachine}.
 *
 * Created by tbsc on 5/6/16.
 */
public class BlockMachineBase extends BlockBase {

    public BlockMachineBase(String unlocalizedName) {
        super(Material.circuits, unlocalizedName);
        setHardness(5.0F);
    }

}
