package tbsc.techy.api.pipes;

import net.minecraft.util.math.BlockPos;

/**
 * Pipes will work so, for example, items transfer through the pipe, an instance of
 * this interface will be passed from the previous pipe, and that way the pipe can
 * determine where to send the item based on the given data.
 *
 * Created by tbsc on 5/14/2016.
 */
public interface IPipePacket<T> {

    /**
     * When something flows through pipes, it needs to know where to go,
     * so this method returns the block the thing should transfer *into*,
     * NOT the pipe it should get to. So if you are trying to route items
     * into a chest, this needs to return the {@link BlockPos} of the
     * chest, not of the pipe that's connected to the chest.
     *
     * Pipes themselves should implement and decide where should it go
     * next, and that's why it returns the destination and not the next
     * pipe it should go to.
     * @return Destination of current object transfer
     */
    BlockPos getDestination();

    /**
     * When items go into the network, they enter it only if they can.
     * If they somehow entered the network because they found a valid
     * destination, and then that destination became unavailable, the
     * item should return to the inventory it came from, and not just
     * stay in the network or drop as an item on the ground.
     * Note, that what I've explained above is mostly only applicable
     * to item pipes, as energy and fluid pipes will (most likely) just
     * keep the energy or fluid in the pipe itself.
     * @return Block the object came from
     */
    BlockPos getOrigin();

    /**
     * Returns the object of the thing that is transferred through the
     * pipe.
     * @return object of what's routed through the pipe
     */
    T getPacketContents();

    /**
     * Creates a human readable string from an instance of this class
     * @return human readable string
     */
    default String toReadableString() {
        return "Origin: " + getOrigin().toString() + ", Destination: " + getDestination().toString() + ", Routed Object: " + getPacketContents();
    }

}
