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

package tbsc.techy.machine.generator;

import net.minecraft.util.ResourceLocation;
import tbsc.techy.Techy;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.client.gui.element.ElementProgressBar;
import tbsc.techy.client.gui.element.ElementSlotRender;
import tbsc.techy.container.ContainerBase;

/**
 * Gui for the coal generator
 *
 * Created by tbsc on 5/21/16.
 */
public class GuiGeneratorBase extends GuiMachineBase {

    public GuiGeneratorBase(ContainerBase containerBase, TileGeneratorBase tile) {
        super(containerBase, tile, tile.inventorySize, new ResourceLocation(Techy.MODID + ":textures/gui/container/guiItemGenerator.png"));
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
    protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
        super.drawGuiContainerBackgroundLayer(partialTick, x, y);
        addElement(new ElementProgressBar(this, 102, 36, 13, 13, new ResourceLocation("Techy:textures/gui/container/guiItemGenerator.png"), 102, 36, 176, 0, tile.getField(1), 13, tile.getField(2)));
    }
}
