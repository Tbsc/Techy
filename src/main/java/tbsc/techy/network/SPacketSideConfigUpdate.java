/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    /**
     * Needed for Minecraft to use this packet correctly
     */
    public SPacketSideConfigUpdate() {}

    public SPacketSideConfigUpdate(BlockPos pos, int sideOrdinal, int sideConfigOrdinal) {
        this.pos = pos;
        this.sideOrdinal = sideOrdinal;
        this.sideConfigOrdinal = sideConfigOrdinal;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        sideOrdinal = buf.readInt();
        sideConfigOrdinal = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        pos = new BlockPos(x, y, z);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(sideOrdinal);
        buf.writeInt(sideConfigOrdinal);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public static class Handler implements IMessageHandler<SPacketSideConfigUpdate, IMessage> {

        @Override
        public IMessage onMessage(SPacketSideConfigUpdate message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        /**
         * Updates the tile entity of the changes
         * @param message packet
         * @param ctx more data to use
         */
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
