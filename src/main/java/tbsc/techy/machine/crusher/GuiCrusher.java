package tbsc.techy.machine.crusher;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.client.gui.element.ElementProgressBar;
import tbsc.techy.client.gui.element.ElementSlotRender;
import tbsc.techy.tile.TileMachineBase;

/**
 * GUI for the crusher
 *
 * Created by tbsc on 5/4/16.
 */
public class GuiCrusher extends GuiMachineBase {

    public GuiCrusher(ContainerCrusher container, BlockPos pos, World world) {
        super(container, pos, world, BlockCrusher.tileInvSize, new ResourceLocation("Techy:textures/gui/container/guiCrusher.png"));
    }

    @Override
    public void initGui() {
        super.initGui();
        addElement(new ElementSlotRender(this, -21, 15));
        addElement(new ElementSlotRender(this, -21, 35));
        addElement(new ElementSlotRender(this, -21, 55));
        addElement(new ElementSlotRender(this, -21, 75));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float gameTicks, int x, int y) {
        super.drawGuiContainerBackgroundLayer(gameTicks, x, y);
        TileMachineBase tile = (TileMachineBase) world.getTileEntity(machine);
        if (tile != null) {
            addElement(new ElementProgressBar(this, 72, 37, 22, 16, new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 0, 0, 22, 0, true, tile.getField(1), 13, tile.getField(2)));
        }
    }
}
