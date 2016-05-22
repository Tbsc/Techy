package tbsc.techy.api.pipes.impl;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLLog;
import tbsc.techy.api.pipes.IPipePacket;

/**
 * Implementation for {@link IPipePacket}, that is used to transfer
 * energy.
 *
 * Created by tbsc on 5/14/2016.
 */
public class EnergyPipePacket implements IPipePacket<Integer> {

    protected BlockPos origin;
    protected BlockPos destination;
    protected int energyTransferred;

    private EnergyPipePacket(BlockPos origin, BlockPos destination, int energyTransferred) {
        this.origin = origin;
        this.destination = destination;
        this.energyTransferred = energyTransferred;
    }

    public static EnergyPipePacket create(BlockPos origin, BlockPos destination, int energyTransferred) {
        if (origin == null || destination == null || energyTransferred <= 0) {
            FMLLog.warning("Something attempted to create an invalid EnergyPipePacket instance! This is an error!");
            return null;
        }
        return new EnergyPipePacket(origin, destination, energyTransferred);
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
    public Integer getPacketContents() {
        return energyTransferred;
    }

}
