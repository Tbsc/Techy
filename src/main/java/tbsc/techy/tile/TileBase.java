package tbsc.techy.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by tbsc on 3/27/16.
 */
public class TileBase extends TileEntity {

    public boolean isUsuableByPlayer(EntityPlayer player) {
        // Makes sure you are in range of 8 blocks from the block
        return player.getPosition().distanceSq(pos.getX(), pos.getY(), pos.getZ()) <= 64;
    }

}
