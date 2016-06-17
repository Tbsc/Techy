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

package tbsc.techy.machine.crusher;

import net.minecraft.util.ResourceLocation;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.client.gui.element.ElementProgressBar;
import tbsc.techy.client.gui.element.ElementSlotRender;

/**
 * GUI for the crusher
 *
 * Created by tbsc on 5/4/16.
 */
public class GuiCrusher extends GuiMachineBase {

    public GuiCrusher(ContainerCrusher container, TileCrusher tile) {
        super(container, tile, BlockCrusher.tileInvSize, new ResourceLocation("Techy:textures/gui/container/guiCrusher.png"));
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
        addElement(new ElementProgressBar(this, 72, 37, 22, 16, new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 0, 0, 22, 0, tile.getField(1), 16, tile.getField(2)));
    }
}
