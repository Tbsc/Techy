package tbsc.techy.client.gui.element;

import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import tbsc.techy.api.client.gui.element.IGuiElement;
import tbsc.techy.client.gui.GuiTechyBase;
import tbsc.techy.common.container.ContainerTechyBase;

/**
 * Basic element for rendering
 *
 * Created by tbsc on 18/07/2016.
 */
public class ElementSlot<T extends GuiTechyBase> implements IGuiElement {

    protected T gui;
    protected int xPos;
    protected int yPos;

    /**
     * Constructor. Texture and the dimensions are hard coded (if you need something else, use a different
     * element).
     * If the container stored in the GUI is a subclass of {@link ContainerTechyBase}, then it'll add the slot
     * to the container.
     * @param gui The GUI this element will be added to.
     * @param slot The slot to add
     */
    public ElementSlot(T gui, Slot slot) {
        this.gui = gui;
        // -1 so the render surrounds the container slot
        this.xPos = slot.xDisplayPosition - 1;
        this.yPos = slot.yDisplayPosition - 1;
        // Add to container
        if (gui.inventorySlots instanceof ContainerTechyBase) {
            ((ContainerTechyBase) gui.inventorySlots).addSlotToContainer(slot);
        }
    }

    /**
     * Draws the slot on screen
     * @param partialTicks I actually don't know what partial ticks are used for :P
     * @param mouseX Mouse X position
     * @param mouseY Mouse Y position
     */
    @Override
    public void drawBackground(float partialTicks, int mouseX, int mouseY) {
        bindTexture(new ResourceLocation("Techy:textures/gui/element/slot.png"));
        GuiUtils.drawTexturedModalRect(getX(), getY(), 0, 0, getWidth(), getHeight(), 0.0F);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

    @Override
    public void elementRightClicked(int mouseX, int mouseY, boolean isReleased) {

    }

    @Override
    public void elementLeftClicked(int mouseX, int mouseY, boolean isReleased) {

    }

    @Override
    public void elementMiddleClicked(int mouseX, int mouseY, boolean isReleased) {

    }

    @Override
    public boolean elementMouseScrolled(int mouseX, int mouseY, int wheelMovement) {
        return false;
    }

    @Override
    public boolean elementKeyPressed(char charEntered, int keyCode, boolean pointerOnElement, int mouseX, int mouseY) {
        return false;
    }

    /**
     * @return X position for the slot render
     */
    @Override
    public int getX() {
        return xPos;
    }

    /**
     * @return Y position for the slot render
     */
    @Override
    public int getY() {
        return yPos;
    }

    /**
     * @return Width of the slot - 16
     */
    @Override
    public int getWidth() {
        return 18;
    }

    /**
     * @return Height of the slot - 16
     */
    @Override
    public int getHeight() {
        return 18;
    }

}
