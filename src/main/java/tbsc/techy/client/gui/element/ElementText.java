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

import net.minecraft.client.gui.FontRenderer;
import tbsc.techy.api.client.gui.element.IGuiElement;

import java.awt.*;

/**
 * Simple element, for rendering text on screen.
 * This is a VERY simple element; it doesn't even wrap the text.
 *
 * Created by tbsc on 18/07/2016.
 */
public class ElementText implements IGuiElement {

    public String text;
    public int xPos, yPos;
    public FontRenderer fontRendererObj;

    public ElementText(String text, int xPos, int yPos, FontRenderer fontRendererObj) {
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.fontRendererObj = fontRendererObj;
    }

    @Override
    public void drawBackground(float partialTicks, int mouseX, int mouseY) {

    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        fontRendererObj.drawString(text, getX(), getY(), Color.gray.getRGB());
    }

    @Override
    public void elementRightClicked(int mouseX, int mouseY, boolean isReleased) {
        // NO-OP
    }

    @Override
    public void elementLeftClicked(int mouseX, int mouseY, boolean isReleased) {
        // NO-OP
    }

    @Override
    public void elementMiddleClicked(int mouseX, int mouseY, boolean isReleased) {
        // NO-OP
    }

    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }

    @Override
    public int getWidth() {
        return fontRendererObj.getStringWidth(text);
    }

    @Override
    public int getHeight() {
        return fontRendererObj.FONT_HEIGHT;
    }

}
