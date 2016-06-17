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

package tbsc.techy.machine.furnace;

import net.minecraft.util.ResourceLocation;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.client.gui.element.ElementProgressBar;
import tbsc.techy.client.gui.element.ElementSlotRender;

/**
 * GUI for the powered furnace.
 * Right now does nothing other than define the location of the GUI texture.
 *
 * Created by tbsc on 3/28/16.
 */
public class GuiPoweredFurnace extends GuiMachineBase {

    public GuiPoweredFurnace(ContainerPoweredFurnace container, TilePoweredFurnace tile) {
        super(container, tile, BlockPoweredFurnace.tileInvSize, new ResourceLocation("Techy:textures/gui/container/guiPoweredFurnace.png"));
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
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        super.drawScreen(mouseX, mouseY, partialTick);
        addElement(new ElementProgressBar(this, 80, 37, 22, 16, new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 0, 0, 22, 0, tile.getField(1), 16, tile.getField(2)));
    }

}