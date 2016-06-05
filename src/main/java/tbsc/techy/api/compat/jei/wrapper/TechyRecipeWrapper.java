package tbsc.techy.api.compat.jei.wrapper;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * Recipe wrapper for recipes. Used to translate between my recipe registry to a JEI compatible one.
 */
public class TechyRecipeWrapper implements IRecipeWrapper {

    @Nonnull
    private final List inputStacks;
    @Nonnull
    private final List outputStacks;
    @Nonnull
    private final List<FluidStack> inputFluids;
    @Nonnull
    private final List<FluidStack> outputFluids;
    @Nonnull
    private final int energyUsage;
    @Nonnull
    private final int cookTime;

    public TechyRecipeWrapper(int energyUsage, int cookTime, List inputStacks, List outputStacks, List<FluidStack> inputFluids, List<FluidStack> outputFluids) {
        this.inputStacks = inputStacks;
        this.outputStacks = outputStacks;
        this.inputFluids = inputFluids;
        this.outputFluids = outputFluids;
        this.energyUsage = energyUsage;
        this.cookTime = cookTime;
    }

    @Override
    public List getInputs() {
        return inputStacks;
    }

    @Override
    public List getOutputs() {
        return outputStacks;
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return inputFluids;
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return outputFluids;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
        FontRenderer fontRenderer = minecraft.fontRendererObj;
        fontRenderer.drawString(energyUsage + " RF", width - fontRenderer.getStringWidth(energyUsage + " RF"), 0, Color.gray.getRGB());
        fontRenderer.drawString((energyUsage / cookTime) / 10 * 10 + " RF/t", width - fontRenderer.getStringWidth((energyUsage / cookTime) / 10 * 10 + " RF/t"), 10, Color.gray.getRGB());
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int i, int i1) {}

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int i, int i1, int i2) {
        return false;
    }

}
