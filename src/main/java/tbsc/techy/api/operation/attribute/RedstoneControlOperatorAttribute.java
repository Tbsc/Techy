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

package tbsc.techy.api.operation.attribute;

import tbsc.techy.api.operation.OperatorBuilder;

/**
 * Created by tbsc on 8/26/16.
 */
public class RedstoneControlOperatorAttribute implements IOperatorAttribute {

    private OperatorBuilder builder;
    public boolean workWhenPowered = false;
    public int minimumRedstoneSignal = 1;

    public RedstoneControlOperatorAttribute(OperatorBuilder builder) {
        this.builder = builder;
    }

    public RedstoneControlOperatorAttribute setWorkWhenPowered(boolean workWhenPowered) {
        this.workWhenPowered = workWhenPowered;
        return this;
    }

    public RedstoneControlOperatorAttribute setMinimumSignal(int minSignal) {
        this.minimumRedstoneSignal = minSignal;
        return this;
    }

    @Override
    public OperatorBuilder finish() {
        return builder;
    }

}
