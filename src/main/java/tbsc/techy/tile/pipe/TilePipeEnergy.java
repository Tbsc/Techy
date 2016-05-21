package tbsc.techy.tile.pipe;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLLog;
import tbsc.techy.api.pipes.IPipeRoutable;
import tbsc.techy.api.pipes.impl.EnergyPipeRoutable;
import tbsc.techy.block.pipe.BlockPipeEnergy;

/**
 * Tile entity for energy pipes.
 *
 * Created by tbsc on 5/9/16.
 */
public class TilePipeEnergy extends TilePipeBase {

    int maxTransfer;

    public TilePipeEnergy() {
        super(1, BlockPipeEnergy.class);
        this.maxTransfer = 400;
    }

    @Override
    protected void sendPipeRoutable(IPipeRoutable routable, BlockPos receiver) {
        TileEntity tile = worldObj.getTileEntity(receiver);
        if (tile instanceof TilePipeBase) {
            TilePipeBase pipe = (TilePipeBase) tile;
            pipe.receivePipeRoutable(routable, pos);
            toBeRouted.remove(routable);
        }
    }

    @Override
    protected void receivePipeRoutable(IPipeRoutable routable, BlockPos sender) {
        toBeRouted.put(routable, transferDelay);
    }

    @Override
    protected void attemptToExtract() {
        for (EnumFacing side : EnumFacing.VALUES) {
            BlockPos pos = this.pos.offset(side);
            TileEntity tile = worldObj.getTileEntity(pos);
            if (tile != null) {
                FMLLog.info("Found nearby tile entity");
                if (tile instanceof IEnergyProvider) {
                    FMLLog.info("Nearby tile is energy provider");
                    BlockPos destination = findDestination();
                    if (destination != null) {
                        FMLLog.info("Found destination at " + destination.toString());
                        int extractedEnergy = ((IEnergyProvider) tile).extractEnergy(side, maxTransfer, false);
                        FMLLog.info("Extracted " + extractedEnergy + " from tile");
                        IPipeRoutable routable = EnergyPipeRoutable.create(pos, destination, extractedEnergy);
                        receivePipeRoutable(routable, pos);
                        FMLLog.info("Sent " + routable.toReadableString());
                    }
                }
            }
        }
    }

    @Override
    protected void insertRoutable(IPipeRoutable routable) {
        TileEntity tile = worldObj.getTileEntity(routable.getDestination());
        if (tile != null) {
            IEnergyReceiver receiver = (IEnergyReceiver) tile;
            EnumFacing insertSide = EnumFacing.DOWN; // Default value if no match is found (for some reason)
            for (EnumFacing side : EnumFacing.VALUES) {
                BlockPos neighbor = routable.getDestination().offset(side);
                if (neighbor == pos) insertSide = side;
            }
            receiver.receiveEnergy(insertSide, (int) routable.getRoutedObject(), false);
        } else {
            FMLLog.info("Energy was sent to an invalid destination?!");
        }
    }

    @Override
    protected boolean isConnectiblePipe(BlockPos block) {
        return false;
    }

    @Override
    protected boolean isAnyNeighborConnectiblePipe(BlockPos pos) {
        Block block = worldObj.getBlockState(pos).getBlock();
        if (block != null) {
            if (block instanceof BlockPipeEnergy) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean canInsertTo(BlockPos pos) {
        TileEntity tile = worldObj.getTileEntity(pos);
        return tile != null && tile instanceof IEnergyReceiver;
    }

}
