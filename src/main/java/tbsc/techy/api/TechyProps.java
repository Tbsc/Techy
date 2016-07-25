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

package tbsc.techy.api;

import net.minecraftforge.fml.common.Mod;
import tbsc.techy.common.Techy;

/**
 * API Class for properties, such as API version and API id.
 *
 * Created by tbsc on 10/07/2016.
 */
public class TechyProps {

    /**
     * API fields, used for the API only.
     */
    public static class API {

        /**
         * The MODID of the mod that owns the API.
         */
        public static final String OWNER = TechyProps.MODID;

        /**
         * The APIID (if you may) of the API. Think of it as a MODID for the API.
         */
        public static final String PROVIDES = OWNER + "API";

        /**
         * The current version of the API. Used by note what API version are allowed.
         */
        public static final String API_VER = "0.0.1";

    }

    /**
     * The mod's MODID
     */
    public static final String MODID = "Techy";

    /**
     * The mod's current version.
     * I really need to find a way to change this automatically from the build task, but that'll
     * require me to actually learn gradle and I got no time for that
     */
    public static final String VERSION = "2.0.0.0";

    /**
     * What mods Techy depends on.
     * Right now the mods that Techy depends on are:
     *  - Forge (version *what im building with*)
     */
    public static final String DEPENDENCIES = "required-after:Forge@["
            + net.minecraftforge.common.ForgeVersion.majorVersion + '.'
            + net.minecraftforge.common.ForgeVersion.minorVersion + '.'
            + net.minecraftforge.common.ForgeVersion.revisionVersion + '.'
            + net.minecraftforge.common.ForgeVersion.buildVersion + ",)";

    /**
     * The string identifier for the client proxy's class.
     */
    public static final String CLIENT_PROXY = "tbsc.techy.client.proxy.ClientProxy";

    /**
     * The string identifier for the server proxy's class.
     */
    public static final String SERVER_PROXY = "tbsc.techy.common.proxy.ServerProxy";

    /**
     * Instance of the mod's main class
     */
    @Mod.Instance(MODID)
    public static Techy INSTANCE;

}
