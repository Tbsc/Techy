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
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tbsc.techy.ConfigData;

/**
 * This packet send the config data from a server to a client, to make sure both are using
 * the same configuration to prevent issues like different RF data between server and client.
 *
 * Created by tbsc on 6/13/16.
 */
public class CPacketUpdateConfig implements IMessage {

    public int furnaceDefaultCookTime;
    public int furnaceDefaultEnergyUsage;
    public int crusherDefaultProcessTime;
    public int crusherDefaultEnergyUsage;
    public int coalGeneratorProcessTime;
    public int coalGeneratorRFPerTick;
    public int basicPowerCellCapacity;
    public int basicPowerCellTransferRate;
    public int improvedPowerCellCapacity;
    public int improvedPowerCellTransferRate;
    public int advancedPowerCellCapacity;
    public int advancedPowerCellTransferRate;
    public int copperPerChunk;
    public int tinPerChunk;
    public int silverPerChunk;
    public int aluminiumPerChunk;
    public int lithiumPerChunk;
    public int copperPerVein;
    public int tinPerVein;
    public int silverPerVein;
    public int aluminiumPerVein;
    public int lithiumPerVein;
    public int copperMaxHeight;
    public int tinMaxHeight;
    public int silverMaxHeight;
    public int aluminiumMaxHeight;
    public int lithiumMaxHeight;

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(ConfigData.furnaceDefaultCookTime);
        buf.writeInt(ConfigData.furnaceDefaultEnergyUsage);
        buf.writeInt(ConfigData.crusherDefaultProcessTime);
        buf.writeInt(ConfigData.crusherDefaultEnergyUsage);
        buf.writeInt(ConfigData.coalGeneratorProcessTime);
        buf.writeInt(ConfigData.coalGeneratorRFPerTick);
        buf.writeInt(ConfigData.basicPowerCellCapacity);
        buf.writeInt(ConfigData.basicPowerCellTransferRate);
        buf.writeInt(ConfigData.improvedPowerCellCapacity);
        buf.writeInt(ConfigData.improvedPowerCellTransferRate);
        buf.writeInt(ConfigData.advancedPowerCellCapacity);
        buf.writeInt(ConfigData.advancedPowerCellTransferRate);
        buf.writeInt(ConfigData.copperPerChunk);
        buf.writeInt(ConfigData.tinPerChunk);
        buf.writeInt(ConfigData.silverPerChunk);
        buf.writeInt(ConfigData.aluminiumPerChunk);
        buf.writeInt(ConfigData.lithiumPerChunk);
        buf.writeInt(ConfigData.copperPerVein);
        buf.writeInt(ConfigData.tinPerVein);
        buf.writeInt(ConfigData.silverPerVein);
        buf.writeInt(ConfigData.aluminiumPerVein);
        buf.writeInt(ConfigData.lithiumPerVein);
        buf.writeInt(ConfigData.copperMaxHeight);
        buf.writeInt(ConfigData.tinMaxHeight);
        buf.writeInt(ConfigData.silverMaxHeight);
        buf.writeInt(ConfigData.aluminiumMaxHeight);
        buf.writeInt(ConfigData.lithiumMaxHeight);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        furnaceDefaultCookTime = buf.readInt();
        furnaceDefaultEnergyUsage = buf.readInt();
        crusherDefaultProcessTime = buf.readInt();
        crusherDefaultEnergyUsage = buf.readInt();
        coalGeneratorProcessTime = buf.readInt();
        coalGeneratorRFPerTick = buf.readInt();
        basicPowerCellCapacity = buf.readInt();
        basicPowerCellTransferRate = buf.readInt();
        improvedPowerCellCapacity = buf.readInt();
        improvedPowerCellTransferRate = buf.readInt();
        advancedPowerCellCapacity = buf.readInt();
        advancedPowerCellTransferRate = buf.readInt();
        copperPerChunk = buf.readInt();
        tinPerChunk = buf.readInt();
        silverPerChunk = buf.readInt();
        aluminiumPerChunk = buf.readInt();
        lithiumPerChunk = buf.readInt();
        copperPerVein = buf.readInt();
        tinPerVein = buf.readInt();
        silverPerVein = buf.readInt();
        aluminiumPerVein = buf.readInt();
        lithiumPerVein = buf.readInt();
        copperMaxHeight = buf.readInt();
        tinMaxHeight = buf.readInt();
        silverMaxHeight = buf.readInt();
        aluminiumMaxHeight = buf.readInt();
        lithiumMaxHeight = buf.readInt();
    }

    public static class Handler implements IMessageHandler<CPacketUpdateConfig, IMessage> {

        @Override
        public IMessage onMessage(CPacketUpdateConfig message, MessageContext ctx) {
            ConfigData.furnaceDefaultCookTime = message.furnaceDefaultCookTime;
            ConfigData.furnaceDefaultEnergyUsage = message.furnaceDefaultEnergyUsage;
            ConfigData.crusherDefaultProcessTime = message.crusherDefaultProcessTime;
            ConfigData.crusherDefaultEnergyUsage = message.crusherDefaultEnergyUsage;
            ConfigData.coalGeneratorProcessTime = message.coalGeneratorProcessTime;
            ConfigData.coalGeneratorRFPerTick = message.coalGeneratorRFPerTick;
            ConfigData.basicPowerCellCapacity = message.basicPowerCellCapacity;
            ConfigData.basicPowerCellTransferRate = message.basicPowerCellTransferRate;
            ConfigData.improvedPowerCellCapacity = message.improvedPowerCellCapacity;
            ConfigData.improvedPowerCellTransferRate = message.improvedPowerCellTransferRate;
            ConfigData.advancedPowerCellCapacity = message.advancedPowerCellCapacity;
            ConfigData.advancedPowerCellTransferRate = message.advancedPowerCellTransferRate;
            ConfigData.copperPerChunk = message.copperPerChunk;
            ConfigData.tinPerChunk = message.tinPerChunk;
            ConfigData.silverPerChunk = message.silverPerChunk;
            ConfigData.aluminiumPerChunk = message.aluminiumPerChunk;
            ConfigData.lithiumPerChunk = message.lithiumPerChunk;
            ConfigData.copperPerVein = message.copperPerVein;
            ConfigData.tinPerVein = message.tinPerVein;
            ConfigData.silverPerVein = message.silverPerVein;
            ConfigData.aluminiumPerVein = message.aluminiumPerVein;
            ConfigData.lithiumPerVein = message.lithiumPerVein;
            ConfigData.copperMaxHeight = message.copperMaxHeight;
            ConfigData.tinMaxHeight = message.tinMaxHeight;
            ConfigData.silverMaxHeight = message.silverMaxHeight;
            ConfigData.aluminiumMaxHeight = message.aluminiumMaxHeight;
            ConfigData.lithiumMaxHeight = message.lithiumMaxHeight;
            FMLLog.info("Loaded configuration from server!");
            return null;
        }

    }

}
