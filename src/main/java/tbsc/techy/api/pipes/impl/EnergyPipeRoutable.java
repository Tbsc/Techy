package tbsc.techy.api.pipes.impl;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.FMLLog;
import tbsc.techy.api.pipes.IPipeRoutable;

/**
 * Implementation for {@link IPipeRoutable}, that is used to transfer
 * energy.
 *
 * Created by tbsc on 5/14/2016.
 */
public class EnergyPipeRoutable implements IPipeRoutable<Integer> {

    protected BlockPos origin;
    protected BlockPos destination;
    protected int energyTransferred;

    private EnergyPipeRoutable(BlockPos origin, BlockPos destination, int energyTransferred) {
        this.origin = origin;
        this.destination = destination;
        this.energyTransferred = energyTransferred;
    }

    public static EnergyPipeRoutable create(BlockPos origin, BlockPos destination, int energyTransferred) {
        if (origin == null || destination == null || energyTransferred <= 0) {
            FMLLog.warning("Something attempted to create an invalid EnergyPipeRoutable instance! This is an error!");
            return null;
        }
        return new EnergyPipeRoutable(origin, destination, energyTransferred);
    }

    @Override
    public BlockPos getDestination() {
        return origin;
    }

    @Override
    public BlockPos getOrigin() {
        return destination;
    }

    @Override
    public Integer getRoutedObject() {
        return energyTransferred;
    }

}
