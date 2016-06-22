package tbsc.techy.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tbsc.techy.tile.TileMachineBase;

/**
 * This packet gets sent to the client when a change in energy occurs in any machine, to
 * notify the client of the change and update the GUI.
 *
 * SERVER --> CLIENT
 *
 * Created by tbsc on 6/22/16.
 */
public class CPacketEnergyChanged implements IMessage {

    private BlockPos pos;
    public int energyStored;

    public CPacketEnergyChanged() {}

    public CPacketEnergyChanged(BlockPos pos, int energyStored) {
        this.pos = pos;
        this.energyStored = energyStored;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(energyStored);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.energyStored = buf.readInt();
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static class Handler implements IMessageHandler<CPacketEnergyChanged, IMessage> {

        @Override
        public IMessage onMessage(CPacketEnergyChanged message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        /**
         * Updates the tile entity of the changes
         * @param message packet
         * @param ctx more data to use
         */
        private void handle(CPacketEnergyChanged message, MessageContext ctx) {
            EntityPlayerSP playerEntity = FMLClientHandler.instance().getClientPlayerEntity();
            World world = playerEntity.worldObj;
            TileEntity tile = world.getTileEntity(message.pos);
            if (tile != null) {
                if (tile instanceof TileMachineBase) {
                    TileMachineBase machine = (TileMachineBase) tile;
                    machine.energyStorage.setEnergyStored(message.energyStored);
                    machine.markDirty();
                }
            }
        }

    }

}
