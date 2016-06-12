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
