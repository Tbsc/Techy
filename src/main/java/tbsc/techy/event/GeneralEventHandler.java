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

package tbsc.techy.event;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tbsc.techy.Techy;

/**
 * Event handler of the mod, ATM only updates config if changes
 *
 * Created by tbsc on 3/26/16.
 */
public class GeneralEventHandler {

    /**
     * Listens to the {@link net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent} and
     * updates config if it changes.
     * @param event instance of the event posted
     */
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (Techy.MODID.equals(event.getModID())) {
            Techy.syncConfig();
        }
    }

}
