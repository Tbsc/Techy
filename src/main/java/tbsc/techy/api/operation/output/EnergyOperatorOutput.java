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

package tbsc.techy.api.operation.output;

import tbsc.techy.api.operation.OperatorBuilder;

/**
 * Created by tbsc on 8/23/16.
 */
public class EnergyOperatorOutput implements IOperatorOutput {

    /**
     * The parent builder instance
     */
    private OperatorBuilder parentBuilder;

    /**
     * Start defining an energy output.
     * @param parentBuilder The builder this output was created from
     */
    public EnergyOperatorOutput(OperatorBuilder parentBuilder) {
        this.parentBuilder = parentBuilder;
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
