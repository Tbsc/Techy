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
import net.minecraftforge.fml.common.FMLLog;
import tbsc.techy.api.client.gui.element.IGuiElement;
import tbsc.techy.client.gui.GuiTechyBase;

import java.awt.*;

/**
 * Simple element, for rendering text on screen.
 * This is a VERY simple element; it doesn't even wrap the text.
 *
 * Created by tbsc on 18/07/2016.
 */
public class ElementText<T extends GuiTechyBase> implements IGuiElement {

    protected T gui;
    public String text;
    protected int xPos, yPos;
    public FontRenderer fontRendererObj;

    public ElementText(T gui, String text, int xPos, int yPos, FontRenderer fontRendererObj) {
        this.gui = gui;
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
    public boolean elementMouseScrolled(int mouseX, int mouseY, int wheelMovement) {
        // NO-OP
        return false;
    }

    @Override
    public boolean elementKeyPressed(char charEntered, int keyCode, boolean pointerOnElement, int mouseX, int mouseY) {
        // NO-OP
        FMLLog.info("key pressed %s, keycode %s", charEntered, keyCode);
        return false;
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
