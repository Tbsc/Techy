package tbsc.techy.recipe;

import net.minecraft.item.ItemStack;
import tbsc.techy.api.IMachineRecipe;

/**
 * This class is the base of machine recipes.
 * You can *NOT* change values here, as they would affect NOTHING and will just mess things
 * up in this instance.
 *
 * Created by tbsc on 3/31/16.
 */
public class BaseMachineRecipe implements IMachineRecipe {

    private String identifier;
    private ItemStack input;
    private ItemStack output;
    private int experiencePoints;
    private int energyConsumption;
    private boolean ignoreInputMetadata;
    private boolean ignoreInputNBT;
    private boolean ignoreInputDamage;

    public BaseMachineRecipe(String identifier, ItemStack input, ItemStack output, int experiencePoints,
                             int energyConsumption, boolean ignoreInputMetadata, boolean ignoreInputNBT,
                             boolean ignoreInputDamage) {
        this.identifier = identifier;
        this.input = input;
        this.output = output;
        this.experiencePoints = experiencePoints;
        this.energyConsumption = energyConsumption;
        this.ignoreInputDamage = ignoreInputDamage;
        this.ignoreInputNBT = ignoreInputNBT;
        this.ignoreInputMetadata = ignoreInputMetadata;
    }

    @Override
    public String getRecipeIdentifier() {
        return identifier;
    }

    @Override
    public ItemStack getInputStack() {
        return input;
    }

    @Override
    public ItemStack getOutputStack() {
        return output;
    }

    @Override
    public int getExperienceAmount() {
        return experiencePoints;
    }

    @Override
    public int getEnergyConsumption() {
        return energyConsumption;
    }

    @Override
    public boolean shouldIgnoreInputMetadata() {
        return ignoreInputMetadata;
    }

    @Override
    public boolean shouldIgnoreInputNBT() {
        return ignoreInputNBT;
    }

    @Override
    public boolean shouldIgnoreInputDamage() {
        return ignoreInputDamage;
    }

}
