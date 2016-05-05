package tbsc.techy.recipe;

/**
 * Ore dictionary recipe input
 *
 * Created by tbsc on 5/5/16.
 */
public class OreRecipeInput<T extends String> implements IRecipeInput {

    public T input;

    public OreRecipeInput(T input) {
        this.input = input;
    }

    public static OreRecipeInput<String> of(String data) {
        return new OreRecipeInput<>(data);
    }

    @Override
    public T getInput() {
        return input;
    }
}
