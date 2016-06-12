package tbsc.techy.api.util;

import com.google.common.base.Predicate;
import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * Created by Elec332 on 4-3-2016.
 * Taken from https://github.com/McJty/XNet
 * Used for any property that I may ever need, and instead of creating multiple
 * I can just use this one more than once
 */
public class PropertyType<T> implements IUnlistedProperty<T> {

    public PropertyType(String name, Class<T> clazz){
        this(name, clazz, null);
    }

    public PropertyType(String name, Class<T> clazz, Predicate<T> predicate) {
        this.name = name;
        this.clazz = clazz;
        this.predicate = predicate;
    }

    public static <T> PropertyType<T> create(String name, Class<T> clazz) {
        return new PropertyType(name, clazz);
    }

    public static <T> PropertyType<T> create(String name, Class<T> clazz, Predicate<T> predicate) {
        return new PropertyType(name, clazz, predicate);
    }

    private final String name;
    private final Class<T> clazz;
    private final Predicate<T> predicate;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(T value) {
        return predicate == null || predicate.apply(value);
    }

    @Override
    public Class<T> getType() {
        return clazz;
    }

    @Override
    public String valueToString(T value) {
        return value == null ? "null" : value.toString();
    }

}