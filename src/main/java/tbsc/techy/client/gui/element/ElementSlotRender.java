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

package tbsc.techy.client.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.render.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;

/**
 * An element that will just render a slot background where a slot is.
 * Size is always 18x18 and texture is hard coded.
 *
 * Created by tbsc on 5/2/16.
 */
public class ElementSlotRender extends ElementBase {

    public ElementSlotRender(GuiBase gui, int posX, int posY) {
        super(gui, posX, posY);
        setSize(18, 18);
        setTexture("Techy:textures/gui/element/slot.png", 18, 18);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {
        RenderHelper.bindTexture(texture);
        GlStateManager.disableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
        GlStateManager.enableAlpha();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

}
