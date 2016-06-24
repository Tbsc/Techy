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

package tbsc.techy.api.register;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * After registration, if a field that is annotated with this annotation is found, and the class that it is
 * pointing to has/will be registered, it assigns the instance of the registered object to the field
 * that is annotated by this annotation.
 * To know what class needs to be assigned by this field, a class object and an identifier need to be passed.
 * A class to know by the type of the registered object, and an identifier if there are more than 1 instances
 * of a class registered.
 *
 * Created by tbsc on 6/23/16.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterInstance {

    Class registerClass();

    String identifier();

}
