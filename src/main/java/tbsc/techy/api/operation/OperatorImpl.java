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

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import tbsc.techy.api.nbt.NBTKeys;
import tbsc.techy.api.operation.attribute.IOperatorAttribute;
import tbsc.techy.api.operation.attribute.RecipeRegistryOperatorAttribute;
import tbsc.techy.api.operation.attribute.RedstoneControlOperatorAttribute;
import tbsc.techy.api.operation.input.IOperatorInput;
import tbsc.techy.api.operation.input.ItemOperatorInput;
import tbsc.techy.api.operation.output.IOperatorOutput;
import tbsc.techy.api.operation.output.ItemOperatorOutput;
import tbsc.techy.api.registry.recipe.IRecipeRegistry;
import tbsc.techy.common.tile.TileTechyBase;

import java.util.function.Consumer;

/**
 * Created by tbsc on 8/25/16.
 */
public class OperatorImpl implements IOperator.Modifiable {

    private TileTechyBase tile;
    private OperatorBuilder builder;

    private int progress;
    private int totalProgress;

    /* Caching data from attributes and others to improve performance */
    private boolean attributesCached = false;
    private boolean inputsCached;
    private boolean outputsCached;
    private boolean workWhenPowered = false;
    private int minimumRedstoneSignal = 1;
    private IRecipeRegistry registry;
    private Pair<Integer, Consumer<ItemStack>>[] inputSlots;
    private int[] outputSlots;

    public OperatorImpl(TileTechyBase tile, OperatorBuilder builder) {
        this.tile = tile;
        this.builder = builder;

        // Start caching everything

        // If not cached, cache the attribute's data to prevent continuous checking
        if (!attributesCached) {
            for (IOperatorAttribute attribute : builder.attributes) {
                if (attribute instanceof RedstoneControlOperatorAttribute) {
                    RedstoneControlOperatorAttribute rsAttrib = (RedstoneControlOperatorAttribute) attribute;
                    this.workWhenPowered = rsAttrib.workWhenPowered;
                    this.minimumRedstoneSignal = rsAttrib.minimumRedstoneSignal;
                    this.attributesCached = true;
                }
                if (attribute instanceof RecipeRegistryOperatorAttribute) {
                    RecipeRegistryOperatorAttribute regAttrib = (RecipeRegistryOperatorAttribute) attribute;
                    this.registry = regAttrib.registry;
                }
            }
        }
        if (!inputsCached) {
            for (IOperatorInput input : builder.inputs) {
                if (input instanceof ItemOperatorInput) {
                    ItemOperatorInput itemInput = (ItemOperatorInput) input;
                    ArrayUtils.add(inputSlots, Pair.of(itemInput.slotID, itemInput.itemChecker));
                }
            }
        }
        if (!outputsCached) {
            for (IOperatorOutput output : builder.outputs) {
                if (output instanceof ItemOperatorOutput) {
                    ItemOperatorOutput itemOutput = (ItemOperatorOutput) output;
                    ArrayUtils.add(outputSlots, itemOutput.slotID);
                }
            }
        }
    }

    @Override
    public boolean updateOperation() {
        return false;
    }

    @Override
    public boolean operate() {
        return false;
    }

    @Override
    public boolean canOperate() {
        return false;
    }

    /**
     * Checks if should operate by checking redstone control attributes, and caching the data.
     * @return Should operate
     */
    @Override
    public boolean shouldOperate() {
        // If not cached, cache the attribute's data to prevent continuous checking
        if (!attributesCached) {
            for (IOperatorAttribute attribute : builder.attributes) {
                if (attribute instanceof RedstoneControlOperatorAttribute) {
                    RedstoneControlOperatorAttribute rsAttrib = (RedstoneControlOperatorAttribute) attribute;
                    workWhenPowered = rsAttrib.workWhenPowered;
                    minimumRedstoneSignal = rsAttrib.minimumRedstoneSignal;
                    attributesCached = true;
                    break;
                }
            }
        }
        int powerStrength = tile.getWorld().isBlockIndirectlyGettingPowered(tile.getPos());
        return workWhenPowered ? powerStrength >= minimumRedstoneSignal : powerStrength < 1;
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Override
    public int getCurrentTotalProgress() {
        return totalProgress;
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public void setCurrentTotalProgress(int totalProgress) {
        this.totalProgress = totalProgress;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger(NBTKeys.Tile.PROGRESS, progress);
        nbt.setInteger(NBTKeys.Tile.TOTAL_PROGRESS, totalProgress);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.progress = nbt.getInteger(NBTKeys.Tile.PROGRESS);
        this.totalProgress = nbt.getInteger(NBTKeys.Tile.TOTAL_PROGRESS);
    }

}
