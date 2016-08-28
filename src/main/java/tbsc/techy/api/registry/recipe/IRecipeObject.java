package tbsc.techy.api.registry.recipe;

/**
 * Created by tbsc on 8/3/16.
 */
public interface IRecipeObject<V> {

    /**
     * Returns the containing object.
     * @return The object this holds.
     */
    V getObject();

}
