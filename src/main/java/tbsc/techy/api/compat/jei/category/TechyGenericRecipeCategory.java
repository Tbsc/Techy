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

package tbsc.techy.api.compat.jei.category;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;

/**
 * Implementation of recipe categories for any machine, based on parameters
 * given in the constructor. All that's needed for registering recipe
 * categories using this generic class is overriding it and implementing
 * {@link #setRecipe(IRecipeLayout, IRecipeWrapper)}. If needed, implementation
 * of {@link #drawExtras(Minecraft)} and {@link #drawAnimations(Minecraft)} can
 * also be done.
 *
 * Created by tbsc on 6/4/16.
 */
public abstract class TechyGenericRecipeCategory implements IRecipeCategory {

    /**
     * Used to draw the background on screen.
     */
    @Nonnull
    protected final IDrawable background;

    /**
     * The location of the texture drawn
     */
    @Nonnull
    protected final ResourceLocation backgroundTexture;

    /**
     * Used to render the title of the machine on top of the screen
     */
    @Nonnull
    private final String unlocalizedName;

    public TechyGenericRecipeCategory(IGuiHelper guiHelper, String unlocalizedName, ResourceLocation background) {
        this.background = guiHelper.createDrawable(background, 30, 10, 120, 66);
        this.unlocalizedName = unlocalizedName;
        this.backgroundTexture = background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {

    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public String getUid() {
        return unlocalizedName;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return I18n.translateToLocal(unlocalizedName);
    }

}
