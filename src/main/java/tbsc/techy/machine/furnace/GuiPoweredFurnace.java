package tbsc.techy.machine.furnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import tbsc.techy.client.gui.GuiMachineBase;

/**
 * Created by tbsc on 3/28/16.
 */
public class GuiPoweredFurnace extends GuiMachineBase {

    public GuiPoweredFurnace(IInventory playerInv, TilePoweredFurnace tile) {
        super(new ContainerPoweredFurnace(playerInv, tile, BlockPoweredFurnace.tileInvSize), BlockPoweredFurnace.tileInvSize, new ResourceLocation("Techy", "textures/gui/container/guiPoweredFurnace.png"));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

}