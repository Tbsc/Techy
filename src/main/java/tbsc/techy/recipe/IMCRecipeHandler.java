package tbsc.techy.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.apache.logging.log4j.Level;

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
                        Object input = messageNBT.getTag("Input");
                        ItemStack output = ItemStack.loadItemStackFromNBT(messageNBT.getCompoundTag("Output"));
                        float experience = messageNBT.getFloat("Experience");
                        int energyUsage = messageNBT.getInteger("EnergyUsage");
                        if (input instanceof NBTTagString) {
                            PoweredFurnaceRecipes.instance().addOreDictionaryRecipe(((NBTTagString) input).getString(), output, experience, energyUsage);
                            FMLLog.log(Level.DEBUG, "Powered furnace recipe with " + ((NBTTagString) input).getString() + " input ore name and " + output.getDisplayName() + " output. Energy usage of " + energyUsage);
                        } else if (input instanceof NBTTagCompound) {
                            ItemStack inputStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) input);
                            PoweredFurnaceRecipes.instance().addItemStackRecipe(inputStack, output, experience, energyUsage);
                            FMLLog.log(Level.DEBUG, "Powered furnace recipe with " + inputStack.getDisplayName() + " input stack and " + output.getDisplayName() + " output. Energy usage of " + energyUsage);
                        }
                    }
                    if (isRecipe.equalsIgnoreCase("Crusher")) {
                        Object input = messageNBT.getTag("Input");
                        ItemStack output = ItemStack.loadItemStackFromNBT(messageNBT.getCompoundTag("FirstOutput"));
                        ItemStack output2 = ItemStack.loadItemStackFromNBT(messageNBT.getCompoundTag("SecondOutput"));
                        int output2Chance = messageNBT.getInteger("SecondOutputChance");
                        float experience = messageNBT.getFloat("Experience");
                        int energyUsage = messageNBT.getInteger("EnergyUsage");
                        if (input instanceof NBTTagString) {
                            CrusherRecipes.instance().addOreDictionaryRecipe(((NBTTagString) input).getString(), output, output2, output2Chance, experience, energyUsage);
                            FMLLog.log(Level.DEBUG, "Powered furnace recipe with " + ((NBTTagString) input).getString() + " input ore name and " + output.getDisplayName() + " output. Energy usage of " + energyUsage);
                        } else if (input instanceof NBTTagCompound) {
                            ItemStack inputStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) input);
                            CrusherRecipes.instance().addItemStackRecipe(inputStack, output, output2, output2Chance, experience, energyUsage);
                            FMLLog.log(Level.DEBUG, "Powered furnace recipe with " + inputStack.getDisplayName() + " input stack, " + output.getDisplayName() + " first output and " + output2.getDisplayName() + " second output. Energy usage of " + energyUsage);
                        }
                    }
                }
            }
        }
    }

}
