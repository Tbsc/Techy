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

package tbsc.techy.api.client.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * Interface for basic elements.
 * Has methods for drawing background and foreground.
 *
 * Created by tbsc on 17/07/2016.
 */
public interface IGuiElement {

    // Render methods

    /**
     * Renders the background layer.
     * @param partialTicks I actually don't know what partial ticks are used for :P
     * @param mouseX Mouse X position
     * @param mouseY Mouse Y position
     */
    void drawBackground(float partialTicks, int mouseX, int mouseY);

    /**
     * Renders the foreground layer.
     * @param mouseX Mouse X position
     * @param mouseY Mouse Y position
     */
    void drawForeground(int mouseX, int mouseY);

    // Event methods

    /**
     * Gets called when the element is being right clicked.
     * The GUI containing the element is called before the element, so keep that in mind.
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param isReleased Has the right mouse button been released or not
     */
    void elementRightClicked(int mouseX, int mouseY, boolean isReleased);

    /**
     * Gets called when the element is being left clicked
     * The GUI containing the element is called before the element, so keep that in mind
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param isReleased Has the left mouse button been released or not
     */
    void elementLeftClicked(int mouseX, int mouseY, boolean isReleased);

    /**
     * Gets called when the element is being middle clicked
     * The GUI containing the element is called before the element, so keep that in mind
     * @param mouseX X position of the mouse
     * @param mouseY Y position of the mouse
     * @param isReleased Has the middle mouse button been released or not
     */
    void elementMiddleClicked(int mouseX, int mouseY, boolean isReleased);

    // Position methods

    /**
     * @return X position of the element
     */
    int getX();

    /**
     * @return Y position of the element
     */
    int getY();

    /**
     * @return Width of the element
     */
    int getWidth();

    /**
     * @return Height of the element
     */
    int getHeight();

    // Util methods

    /**
     * Checks if the position given is on the element.
     * @param xPos X position
     * @param yPos Y position
     * @return Is position inside the element
     */
    default boolean isPointOnElement(int xPos, int yPos) {
        return xPos >= getX() - 1 && xPos < getX() + getWidth() + 1 && yPos >= getY() - 1 && yPos < getY() + getHeight() + 1;
    }

    /**
     * Binds the texture to the texture renderer.
     * @param texture The texture to bind
     */
    default void bindTexture(ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    }

}
