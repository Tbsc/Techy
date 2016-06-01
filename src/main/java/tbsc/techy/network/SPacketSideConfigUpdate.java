package tbsc.techy.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.tile.TileMachineBase;

/**
 * Packet for synchronizing changes in side configuration between the client and the server.
 *
 * CLIENT ----> SERVER
 *
 * Created by tbsc on 6/1/16.
 */
public class SPacketSideConfigUpdate implements IMessage {

    public int sideOrdinal, sideConfigOrdinal;
    public BlockPos pos;

    public SPacketSideConfigUpdate(BlockPos pos, int sideOrdinal, int sideConfigOrdinal) {
        this.pos = pos;
        this.sideOrdinal = sideOrdinal;
        this.sideConfigOrdinal = sideConfigOrdinal;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        sideOrdinal = buf.getInt(0);
        sideConfigOrdinal = buf.getInt(1);
        int x = buf.getInt(2);
        int y = buf.getInt(3);
        int z = buf.getInt(4);
        pos = new BlockPos(x, y, z);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.setInt(0, sideOrdinal);
        buf.setInt(1, sideConfigOrdinal);
        buf.setInt(2, pos.getX());
        buf.setInt(3, pos.getY());
        buf.setInt(4, pos.getZ());
    }

    public static class Handler implements IMessageHandler<SPacketSideConfigUpdate, IMessage> {

        @Override
        public IMessage onMessage(SPacketSideConfigUpdate message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SPacketSideConfigUpdate message, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().playerEntity;
            World world = playerEntity.worldObj;
            TileEntity tile = world.getTileEntity(message.pos);
            if (tile != null) {
                if (tile instanceof TileMachineBase) {
                    TileMachineBase machine = (TileMachineBase) tile;
                    Sides side = Sides.fromOrdinal(message.sideOrdinal);
                    SideConfiguration config = SideConfiguration.fromOrdinal(message.sideConfigOrdinal);
                    machine.setConfigurationForSide(side, config);
                }
            }
        }

    }

}
