package tbsc.techy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import tbsc.techy.api.capability.TechyCapabilities;
import tbsc.techy.api.loader.IHasCustomModel;
import tbsc.techy.api.loader.IHasItemBlock;
import tbsc.techy.client.gui.handler.TechyGUIHandler;
import tbsc.techy.common.Techy;

/**
 * Base class for blocks. To allow for advanced functionality, use capabilities.
 *
 * Created by tbsc on 10/07/2016.
 */
public abstract class BlockBase extends Block implements ICapabilityProvider, IHasItemBlock, IHasCustomModel {

    public BlockBase(String regName, Material materialIn) {
        super(materialIn);
        setRegistryName(regName);
        setUnlocalizedName(Techy.MODID + ":" + regName);
        setCreativeTab(Techy.TAB_TECHY);

        // If has GUI, register the GUI
        if (hasCapability(TechyCapabilities.CAPABILITY_GUI, null)) {
            TechyGUIHandler.registerGUI(getCapability(TechyCapabilities.CAPABILITY_GUI, null));
        }
    }

    // Rendering

    @Override
    public void loadCustomModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    // Item block

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }

}
