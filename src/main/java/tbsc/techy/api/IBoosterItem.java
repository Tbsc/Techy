package tbsc.techy.api;

/**
 * Should be implemented on items that have a boosting functionality.
 *
 * Created by tbsc on 5/1/16.
 */
public interface IBoosterItem {

    int getEnergyModifier(int tier);

    int getTimeModifier(int tier);

    int getExperienceModifier(int tier);

    int getAdditionalItemModifier(int tier);

    int[] getTiers();

}
