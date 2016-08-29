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

package tbsc.techy.api.operation;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import tbsc.techy.api.operation.attribute.IOperatorAttribute;
import tbsc.techy.api.operation.attribute.RecipeRegistryOperatorAttribute;
import tbsc.techy.api.operation.attribute.RedstoneControlOperatorAttribute;
import tbsc.techy.api.operation.input.EnergyOperatorInput;
import tbsc.techy.api.operation.input.IOperatorInput;
import tbsc.techy.api.operation.input.ItemOperatorInput;
import tbsc.techy.api.operation.output.EnergyOperatorOutput;
import tbsc.techy.api.operation.output.IOperatorOutput;
import tbsc.techy.api.operation.output.ItemOperatorOutput;
import tbsc.techy.common.tile.TileTechyBase;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder class for creating operator capabilities that follow a specified set of orders.
 * A powerful way to create machine logic easily.
 *
 * You start by creating an instance of this class and adding attributes, using each of the methods
 * available. If you want an item input, define one using {@link #itemInput()}. Energy input,
 * {@link #energyInput()}.
 * Outputs are just ways to define what will come out after operating. Item output: {@link #itemOutput()},
 * Energy output: {@link #energyOutput()}.
 * Attributes say what the process will do. For example, {@link #recipeRegistryAttribute()}, what recipe
 * registry the operator will use when -operating.
 * Call {@link #build()} and return the object in {@link TileEntity#getCapability(Capability, EnumFacing)}.
 * Remember to also say that the tile has the {@link IOperator} capability in
 * {@link TileEntity#hasCapability(Capability, EnumFacing)}.
 *
 * Created by tbsc on 8/23/16.
 */
public class OperatorBuilder {

    /**
     * List of the inputs that the operator will accept
     */
    public final List<IOperatorInput> inputs = new ArrayList<>();

    /**
     * Attributes for the operator
     */
    public final List<IOperatorAttribute> attributes = new ArrayList<>();

    /**
     * List of the outputs that the operator will have
     */
    public final List<IOperatorOutput> outputs = new ArrayList<>();

    /**
     * The tile entity to build the IOperator to
     */
    private final TileTechyBase tile;

    public OperatorBuilder(TileTechyBase tile) {
        this.tile = tile;
    }

    /**
     * Creates a default empty operator
     * @return {@link IOperator} with no inputs, attributes and outputs
     */
    public static IOperator generateDefaultOperator(TileTechyBase tile) {
        return new OperatorBuilder(tile).build();
    }

    /**
     * Add an input
     * @return {@link EnergyOperatorInput} for configuring options
     */
    public EnergyOperatorInput energyInput() {
        EnergyOperatorInput input = new EnergyOperatorInput(this);
        inputs.add(input);
        return input;
    }

    public ItemOperatorInput itemInput() {
        ItemOperatorInput input = new ItemOperatorInput(this);
        inputs.add(input);
        return input;
    }

    public RedstoneControlOperatorAttribute redstoneControlAttribute() {
        RedstoneControlOperatorAttribute attribute = new RedstoneControlOperatorAttribute(this);
        attributes.add(attribute);
        return attribute;
    }

    public RecipeRegistryOperatorAttribute recipeRegistryAttribute() {
        RecipeRegistryOperatorAttribute attribute = new RecipeRegistryOperatorAttribute(this);
        attributes.add(attribute);
        return attribute;
    }

    public EnergyOperatorOutput energyOutput() {
        EnergyOperatorOutput output = new EnergyOperatorOutput(this);
        outputs.add(output);
        return output;
    }

    public ItemOperatorOutput itemOutput() {
        ItemOperatorOutput output = new ItemOperatorOutput(this);
        outputs.add(output);
        return output;
    }

    @Nullable
    public IOperator build() {
        return new OperatorImpl(tile, this);
    }

}
