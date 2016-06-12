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

package tbsc.techy.api.compat.jei.handler;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import tbsc.techy.api.compat.jei.TechyJEIPlugin;
import tbsc.techy.api.compat.jei.wrapper.TechyRecipeWrapper;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * Same reason for this class as {@link TechyFurnaceRecipeHandler}, I have to use different classes
 * for each handler
 *
 * Created by tbsc on 6/5/16.
 */
public class TechyCrusherRecipeHandler extends TechyGenericRecipeHandler<TechyCrusherRecipeHandler.CrusherRecipeWrapper> {

    public TechyCrusherRecipeHandler() {
        super(TechyJEIPlugin.CRUSHER_UID);
    }

    @Nonnull
    @Override
    public Class<CrusherRecipeWrapper> getRecipeClass() {
        return CrusherRecipeWrapper.class;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull CrusherRecipeWrapper recipeWrapper) {
        return recipeWrapper;
    }

    @Override
    public boolean isRecipeValid(@Nonnull CrusherRecipeWrapper recipeWrapper) {
        return recipeWrapper.getInputs().size() == 1 && recipeWrapper.getOutputs().size() == 2 &&
                recipeWrapper.getFluidInputs().isEmpty() && recipeWrapper.getFluidOutputs().isEmpty();
    }

    public static class CrusherRecipeWrapper extends TechyRecipeWrapper {

        @Nonnull
        private final int outputChance;

        public CrusherRecipeWrapper(int energyUsage, int cookTime, List inputStacks, List outputStacks, int outputChance) {
            super(energyUsage, cookTime, inputStacks, outputStacks, Collections.emptyList(), Collections.emptyList());
            this.outputChance = outputChance;
        }

        @Override
        public void drawInfo(@Nonnull Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
            super.drawInfo(minecraft, width, height, mouseX, mouseY);
            FontRenderer fontRenderer = minecraft.fontRendererObj;
            if (outputChance != 0) {
                fontRenderer.drawString("Second Chance: " + outputChance + "%", 4, 50, Color.gray.getRGB());
            }
        }
    }

}
