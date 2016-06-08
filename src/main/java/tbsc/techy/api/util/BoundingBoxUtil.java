package tbsc.techy.api.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Used for complex bounding boxes. Can create combined bounding boxes based on 2 or more bounding boxes given,
 * and will attempt to create 1 bounding box that'll contain all of the at once.
 *
 * Created by tbsc on 6/7/16.
 */
public class BoundingBoxUtil {

    /**
     * Takes all parameters given, loops through them, and returns 1 bounding box that will
     * *CONTAIN* all of the bounding boxes given.
     *
     * Note that it'll return a cube - {@link AxisAlignedBB} doesn't support any shape other than a cube.
     * For other shapes you'll need to do something else
     *
     * @param bbs array of bounding boxes to combine
     * @return A bounding box that contains everything in the array
     */
    public static AxisAlignedBB combine(AxisAlignedBB... bbs) {
        double startX = 0;
        double startY = 0;
        double startZ = 0;
        double endX = 0;
        double endY = 0;
        double endZ = 0;

        for (AxisAlignedBB bb : bbs) {
            startX = Math.min(bb.minX, startX);
            startY = Math.min(bb.minY, startY);
            startZ = Math.min(bb.minZ, startZ);
            endX = Math.max(bb.maxX, endX);
            endY = Math.max(bb.maxY, endY);
            endZ = Math.max(bb.maxZ, endZ);
        }

        return new AxisAlignedBB(startX, startY, startZ, endX, endY, endZ);
    }

    /**
     * Checks if the {@link Vec3d} (or {@link BlockPos}) given is in the bounding box given.
     * @param pos position
     * @param boundingBox the bounding box to check
     * @return is position in bounding box
     */
    public static boolean isInSameLocation(Vec3d pos, AxisAlignedBB boundingBox) {
        return boundingBox.minX <= pos.xCoord && pos.xCoord <= boundingBox.maxX &&
                boundingBox.minY <= pos.yCoord && pos.xCoord <= boundingBox.maxY &&
                boundingBox.minZ <= pos.zCoord && pos.xCoord <= boundingBox.maxY;
    }

}
