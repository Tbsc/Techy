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

package tbsc.techy.common.machine;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tbsc.techy.api.iface.IHasGUI;
import tbsc.techy.client.gui.GuiTechyBase;
import tbsc.techy.client.gui.element.ElementSlot;
import tbsc.techy.common.block.BlockTechyMachine;
import tbsc.techy.common.container.ContainerTechyBase;
import tbsc.techy.common.tile.TilePoweredFurnace;
import tbsc.techy.common.tile.TileTechyBase;

/**
 * This enum contains all of the types of machines in the mod.
 *
 * Created by tbsc on 15/07/2016.
 */
public enum MachineType {

    FURNACE("blockPoweredFurnace", Machine.FURNACE, TilePoweredFurnace.class, "tilePoweredFurnace") {

        IHasGUI gui = new IHasGUI() {

            @Override
            public int getGUIID() {
                return Machine.GUI.GUI_ID_POWERED_FURNACE;
            }

            @Override
            public Container getContainer(EntityPlayer player, World world, BlockPos pos) {
                return new ContainerTechyBase<>(player.inventory, (TilePoweredFurnace) world.getTileEntity(pos));
            }

            @Override
            @SideOnly(Side.CLIENT)
            public GuiScreen getGUI(EntityPlayer player, World world, BlockPos pos) {
                return new GuiTechyBase(getContainer(player, world, pos)) {

                    @Override
                    public void initGui() {
                        super.initGui();
                        // Although the amount of casts I am doing here seems excessive, all of them are safe.
                        TilePoweredFurnace tile = (TilePoweredFurnace) world.getTileEntity(pos);
                        IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                        addElement(new ElementSlot(this, new SlotItemHandler(inv, 0, 10, 10)));
                    }

                    @Override
                    protected void rightClickEvent(int mouseX, int mouseY, boolean isReleased) {

                    }

                    @Override
                    protected void leftClickEvent(int mouseX, int mouseY, boolean isReleased) {

                    }

                    @Override
                    protected void middleClickEvent(int mouseX, int mouseY, boolean isReleased) {

                    }

                    @Override
                    protected void keyTypedEvent(char charTyped, int keyCode) {

                    }

                };
            }

        };

        @Override
        public boolean hasGUI() {
            return true;
        }

        @Override
        public IHasGUI getGUI() {
            return gui;
        }

        @Override
        public <T extends TileTechyBase> T getTile(World world, int meta) {
            return (T) new TilePoweredFurnace();
        }

    };

    private final String regName;
    private final BlockTechyMachine<? extends TileEntity> block;
    private final Class<? extends TileEntity> tileClass;
    private final String tileIdentifier;

    <T extends TileEntity> MachineType(String regName, BlockTechyMachine<T> block, Class<T> tileClass, String tileIdentifier) {
        this.regName = regName;
        this.block = block;
        this.tileClass = tileClass;
        this.tileIdentifier = tileIdentifier;
    }

    public String getRegistryName() {
        return regName;
    }

    public BlockTechyMachine<? extends TileEntity> getBlock() {
        return block;
    }

    public Class<? extends TileEntity> getTileClass() {
        return tileClass;
    }

    public String getTileIdentifier() {
        return tileIdentifier;
    }

    /**
     * Lets any machine specify what tile instance to return in
     * {@link ITileEntityProvider#createNewTileEntity(World, int)}, based on world and metadata.
     * @param world The world
     * @param meta Metadata of the block
     * @param <T> The type of the tile entity
     * @return Instance of the tile entity
     */
    public abstract <T extends TileTechyBase> T getTile(World world, int meta);

    /**
     * Very simple, returns whether this machine has a GUI.
     * Used by {@link BlockTechyMachine} to know if a machine has a GUI, and register it.
     * @return if the machine has a GUI
     */
    public abstract boolean hasGUI();

    /**
     * Returns the GUI capability.
     * If has no GUI capability, return null.
     * This should be safe because {@link #hasGUI()} should be checked before doing anything
     * related to GUIs.
     * @return {@link IHasGUI} capability.
     */
    public abstract IHasGUI getGUI();

}
