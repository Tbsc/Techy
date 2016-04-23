package cofh.lib.gui.element;

import cofh.lib.gui.GuiBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ElementFluid extends ElementBase {

	public FluidStack fluid;

	public ElementFluid(GuiBase gui, int posX, int posY) {

		super(gui, posX, posY);
	}

	public ElementFluid setFluid(FluidStack stack) {

		this.fluid = stack;
		return this;
	}

	public ElementFluid setFluid(Fluid fluid) {

		this.fluid = new FluidStack(fluid, 1000); // Why is a field for bucket volume needed? It's always 1000mb
		return this;
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks) {

		gui.drawFluid(posX, posY, fluid, sizeX, sizeY);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {

	}

}
