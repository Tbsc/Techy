package tbsc.techy.api.compat.jei.handler;

import mezz.jei.api.recipe.IRecipeHandler;
import tbsc.techy.api.compat.jei.wrapper.TechyRecipeWrapper;

import javax.annotation.Nonnull;

/**
 * Generic class for recipe handlers.
 *
 * Created by tbsc on 6/4/16.
 */
public abstract class TechyGenericRecipeHandler<T extends TechyRecipeWrapper> implements IRecipeHandler<T> {

    @Nonnull
    private final String id;

    public TechyGenericRecipeHandler(String id) {
        this.id = id;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return id;
    }

}
