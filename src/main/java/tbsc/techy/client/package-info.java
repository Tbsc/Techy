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

/**
 * Everything in this package can only be called on the client side.
 * That means that ALL of the client stuff here can use the common methods (that's
 * why they are common), but NONE of the common stuff can use the client stuff.
 * You can't guarantee (without checking) that you are running on the client, and therefore
 * anything that needs to be done on the client is here.
 */
package tbsc.techy.client;