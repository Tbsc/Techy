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

package tbsc.techy.api.util;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.FMLLog;

/**
 * All methods here will only execute if run in a development environment (unobfuscated minecraft).
 * Used mostly for debug.
 *
 * Created by tbsc on 6/23/16.
 */
public class DevUtil {

    /**
     * Prints the message to chat with the format given, ONLY when executed
     * in an unobfuscated development environment.
     * @param message The message to print
     * @param format The objects to put in the message
     */
    public static void info(String message, Object... format) {
        if (isDevEnv()) {
            FMLLog.info(message, format);
        }
    }

    public static boolean isDevEnv() {
        return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

}
