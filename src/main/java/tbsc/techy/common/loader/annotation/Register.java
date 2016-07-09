/*
 * Copyright © 2016 Tbsc
 *
 * Butter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Butter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Butter.  If not, see <http://www.gnu.org/licenses/>.
 */

package tbsc.techy.common.loader.annotation;

import net.minecraft.block.Block;
import tbsc.techy.api.IHasCustomModel;
import tbsc.techy.api.IHasItemBlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This class contains 2 types of annotations to register blocks and items to the game, based
 * on the type of annotated object and the annotated object's properties (parent class, implementations).
 *
 * Created by tbsc on 03/07/2016.
 */
public class Register {

    /**
     * Fields annotated with this annotation will be loaded to the game, based on the underlying
     * class (for example, if subclass of {@link Block} then it'll register it as a block). It'll
     * also check interfaces such as {@link IHasItemBlock} and {@link IHasCustomModel} to know how exactly
     * to register the class to the game.
     *
     * Since this annotation requires you to already create an instance for the class, a registry name
     * isn't needed because the instance needs to already assign one.
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Instance {}

    /**
     * This annotation can only be used on classes. What it does is that if you want to register a
     * class only once, instead of having to create a field for the object, assign it and annotate it
     * with {@link Register.Instance}, you can just annotate the class and the instance will be
     * assigned to all of the fields whose type is the underlying class.
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Class {}

}
