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

package tbsc.techy.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import tbsc.techy.api.client.gui.element.IGuiElement;

import java.io.IOException;
import java.util.HashSet;

/**
 * Base class for GUIs.
 * Adds support for modular GUI system with elements and tabs.
 * Add elements/tabs to the screen using {@link #addElement(IGuiElement)}, and the element will
 * be rendered on the screen.
 * <p>
 * Created by tbsc on 17/07/2016.
 */
public abstract class GuiTechyBase extends GuiContainer {

    protected int mouseX, mouseY;

    public GuiTechyBase(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    // Elements

    /**
     * Set with any elements that will be displayed on screen.
     */
    protected final HashSet<IGuiElement> elementSet = new HashSet<>();

    /**
     * Adds element to the GUI.
     *
     * @param element The element to add to the GUI, and that will be rendered every tick.
     * @param <T>     The type of the element
     * @return The element registered, can be used for saving instances of it. If failed adding, returns null.
     */
    public <T extends IGuiElement> T addElement(T element) {
        return elementSet.add(element) ? element : null;
    }

    /**
     * Adds element to the GUI, and returns the GUI's intance
     *
     * @param element The element to add to the GUI, and that will be rendered every tick.
     * @param <T>     The type of the element
     * @return The GUI's instance
     */
    public <T extends IGuiElement> GuiTechyBase addElementGUI(T element) {
        addElement(element);
        return this;
    }

    // Render methods


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    /**
     * Draws the GUI's background layer on screen.
     * Loops through all of the registered elements and draws them on screen.
     * NOTE: When overriding this, remember to call the super method for elements to render.
     *
     * @param partialTicks Partial ticks
     * @param mouseX       Mouse X position
     * @param mouseY       Mouse Y position
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(new ResourceLocation("Techy:textures/gui/container/guiPoweredFurnace.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft, guiTop, 0.0F);
        elementSet.forEach(element -> element.drawBackground(partialTicks, mouseX, mouseY));
        GL11.glPopMatrix();
    }

    /**
     * Draws the GUI's foreground layer on screen.
     * NOTE: When overriding this, remember to call the super method for elements to render.
     * Loops through all of the registered elements and draws them on screen.
     *
     * @param mouseX Mouse X position
     * @param mouseY Mouse Y position
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        elementSet.forEach(element -> element.drawForeground(mouseX, mouseY));
    }

    // Button interaction methods

    /**
     * Listens for when any mouse button is clicked, and calls the abstract methods to
     * let the GUI know there has been a button press somewhere.
     *
     * @param mouseX      Mouse X position
     * @param mouseY      Mouse Y position
     * @param mouseButton The mouse button pressed (0 = left, 1 = right, 2 = middle)
     * @throws IOException I don't know why this method throws {@link IOException}s, but I don't care either
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        // Fire events based on mouse button
        switch (mouseButton) {
            case 0:
                leftClickEvent(mouseX, mouseY, false);
                break;
            case 1:
                rightClickEvent(mouseX, mouseY, false);
                break;
            case 2:
                middleClickEvent(mouseX, mouseY, false);
                break;
        }

        // Loop through registered elements
        for (IGuiElement element : elementSet) {
            // Check if click was on the element
            if (isPointInRegion(element.getX(), element.getY(), element.getWidth(), element.getHeight(), mouseX, mouseY)) {
                // Fire events based on mouse button
                switch (mouseButton) {
                    case 0:
                        element.elementLeftClicked(mouseX, mouseY, false);
                        break;
                    case 1:
                        element.elementRightClicked(mouseX, mouseY, false);
                        break;
                    case 2:
                        element.elementMiddleClicked(mouseX, mouseY, false);
                        break;
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        // Fire event
        keyTypedEvent(typedChar, keyCode);

        // Loop through elements
        for (IGuiElement element : elementSet) {
            // If mouse is on element
            if (isPointInRegion(element.getX(), element.getY(), element.getWidth(), element.getHeight(), mouseX, mouseY)) {
                // Call event with mouse on element parameter
                element.elementKeyPressed(typedChar, keyCode, true, mouseX, mouseY);
            } else {
                // If not, then call it set to false
                element.elementKeyPressed(typedChar, keyCode, false, mouseX, mouseY);
            }
        }
    }

    /**
     * Gets called when the right mouse button is clicked.
     *
     * @param mouseX     The position the mouse was on the X axis
     * @param mouseY     The position the mouse was on the Y axis
     * @param isReleased If the mouse button was just pressed or released
     */
    protected abstract void rightClickEvent(int mouseX, int mouseY, boolean isReleased);

    /**
     * Gets called when the left mouse button is clicked.
     *
     * @param mouseX     The position the mouse was on the X axis
     * @param mouseY     The position the mouse was on the Y axis
     * @param isReleased If the mouse button was just pressed or released
     */
    protected abstract void leftClickEvent(int mouseX, int mouseY, boolean isReleased);

    /**
     * Gets called when the middle mouse button is clicked.
     *
     * @param mouseX     The position the mouse was on the X axis
     * @param mouseY     The position the mouse was on the Y axis
     * @param isReleased If the mouse button was just pressed or released
     */
    protected abstract void middleClickEvent(int mouseX, int mouseY, boolean isReleased);

    /**
     * Gets called when the middle mouse button is clicked.
     * @param charTyped The character that was entered using the key
     * @param keyCode The {@link Keyboard} key code
     */
    protected abstract void keyTypedEvent(char charTyped, int keyCode);

    // Util methods

    /**
     * Gets the slot in the coord given.
     * @param xCoord X coord
     * @param yCoord Y coord
     * @return Slot at position, or null if doesn't exist
     */
    public Slot getSlotAtPosition(int xCoord, int yCoord) {
        for (int k = 0; k < this.inventorySlots.inventorySlots.size(); ++k) {
            Slot slot = this.inventorySlots.inventorySlots.get(k);

            if (this.isMouseOverSlot(slot, xCoord, yCoord)) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Checks if the mouse is currently on a slot
     * @param theSlot The slot to check for
     * @param xCoord Mouse X coord
     * @param yCoord Mouse Y coord
     * @return is mouse coords inside slot rectangle
     */
    public boolean isMouseOverSlot(Slot theSlot, int xCoord, int yCoord) {
        return this.isPointInRegion(theSlot.xDisplayPosition, theSlot.yDisplayPosition, 16, 16, xCoord, yCoord);
    }

    /**
     * Returns the value of the {@link #xSize} field, that stores the width of the GUI itself
     * @return {@link #xSize}
     */
    public int getXSize() {
        return xSize;
    }

    /**
     * Returns the value of the {@link #ySize} field, that stores the size of the GUI itself
     * @return {@link #ySize}
     */
    public int getYSize() {
        return ySize;
    }

    /**
     * Binds the texture to the texture renderer.
     * @param texture The texture to bind
     */
    public void bindTexture(ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    }

}
