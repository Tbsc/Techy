package tbsc.techy.machine.generator;

import net.minecraft.util.ResourceLocation;
import tbsc.techy.Techy;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.init.BlockInit;

/**
 * Gui for the coal generator
 *
 * Created by tbsc on 5/21/16.
 */
public class GuiGeneratorBase extends GuiMachineBase {

    public GuiGeneratorBase(ContainerBase containerBase) {
        super(containerBase, BlockInit.blockCoalGenerator.tileInvSize, new ResourceLocation(Techy.MODID + ":textures/gui/container/guiCoalGenerator.png"));
    }

    @Override
    public void initGui() {
        super.initGui();
    }

}
