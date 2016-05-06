package tbsc.techy.item;

/**
 * Basic crafting component.
 * Since most crafting components are just normal items with a texture, but
 * have no real functionality other than being used as a crafting ingredient
 * or used in another machine. Therefore 1 class that can be reused with
 * different unlocalized names is good.
 *
 * Created by tbsc on 5/5/16.
 */
public class ItemGenericCraftingComponent extends ItemBase {

    public ItemGenericCraftingComponent(String unlocalizedName) {
        super(unlocalizedName);
    }

}
