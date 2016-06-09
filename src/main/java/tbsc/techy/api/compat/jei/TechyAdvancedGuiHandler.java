package tbsc.techy.api.compat.jei;

import cofh.lib.util.Rectangle4i;
import com.google.common.collect.Lists;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import tbsc.techy.client.gui.GuiMachineBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 * Used to tell JEI about the tab on the side of the screen.
 *
 * Created by tbsc on 6/9/16.
 */
public class TechyAdvancedGuiHandler<T extends GuiMachineBase> implements IAdvancedGuiHandler<T> {

    @Nonnull
    private final Class<T> clazz;

    public TechyAdvancedGuiHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Nonnull
    @Override
    public Class<T> getGuiContainerClass() {
        return clazz;
    }

    @Nullable
    @Override
    public java.util.List<Rectangle> getGuiExtraAreas(T gui) {
        Rectangle4i bounds = gui.tabSides.getBounds();
        return Lists.newArrayList(new Rectangle(bounds.x, bounds.y, bounds.w, bounds.h));
    }

}
