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

package tbsc.techy.api.operation.input;

import net.minecraft.item.ItemStack;
import tbsc.techy.api.operation.OperatorBuilder;

import java.util.function.Consumer;

/**
 * Created by tbsc on 8/23/16.
 */
public class ItemOperatorInput implements IOperatorInput {

    /**
     * The parent builder instance
     */
    private OperatorBuilder parentBuilder;
    public Consumer<ItemStack> itemChecker;
    public int slotID;

    /**
     * Start defining an item input.
     * @param parentBuilder The builder this input was created from
     */
    public ItemOperatorInput(OperatorBuilder parentBuilder) {
        this.parentBuilder = parentBuilder;
    }

    /**
     * Set the slot ID of this item input. If you want more than one slot for inputs, add
     * another {@link ItemOperatorInput}.
     * @param slotID The slot ID this input is tied to
     * @return This object
     */
    public ItemOperatorInput setSlotID(int slotID) {
        this.slotID = slotID;
        return this;
    }

    /**
     * Allows setting a consumer which will be called to see what items can be inserted to what slots.
     * @param callback Consumer for calling the method for check
     * @return This object
     */
    public ItemOperatorInput setCheckConsumer(Consumer<ItemStack> callback) {
        this.itemChecker = callback;
        return this;
    }

    /**
     * Return to the builder object, to continue building the operator.
     * @return Builder
     */
    @Override
    public OperatorBuilder finish() {
        return parentBuilder;
    }

}
