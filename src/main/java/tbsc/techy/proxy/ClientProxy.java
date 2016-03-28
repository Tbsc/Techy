package tbsc.techy.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;

/**
 * Created by tbsc on 3/27/16.
 */
public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void initModels() {
        ItemInit.initModels();
        BlockInit.initModels();
    }

}
