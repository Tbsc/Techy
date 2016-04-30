package tbsc.techy.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Listens to IMC messages, and when a message is received it attempts to
 * add a recipe based on the data given.
 *
 * Created by tbsc on 4/30/16.
 */
public class IMCRecipeHandler {

    /**
     * Gets called by {@link tbsc.techy.Techy#imcMessage(FMLInterModComms.IMCEvent)} event handler.
     * @param messageList list of the messages sent through IMC
     */
    public static void imcMessageReceived(ImmutableList<FMLInterModComms.IMCMessage> messageList) {
        for (FMLInterModComms.IMCMessage message : messageList) {
            if (message.getMessageType() == NBTTagCompound.class) {
                NBTTagCompound messageNBT = message.getNBTValue();
                String isRecipe = messageNBT.getString("RecipeType");
                if (isRecipe != null) {
                    if (isRecipe.equalsIgnoreCase("PoweredFurnace")) {
                        ItemStack input = ItemStack.loadItemStackFromNBT(messageNBT.getCompoundTag("Input"));
                        ItemStack output = ItemStack.loadItemStackFromNBT(messageNBT.getCompoundTag("Output"));
                        float experience = messageNBT.getFloat("Experience");
                        int energyUsage = messageNBT.getInteger("EnergyUsage");
                        PoweredFurnaceRecipes.instance().addItemStackRecipe(input, output, experience, energyUsage);
                    }
                }
            }
        }
    }

}
