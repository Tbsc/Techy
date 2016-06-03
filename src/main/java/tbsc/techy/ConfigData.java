package tbsc.techy;

/**
 * Stores any data that may be changed in config.
 * Values that are already here are default values.
 *
 * Created by tbsc on 4/3/16.
 */
public class ConfigData {

    // POWERED FURNACE CONFIG //

    public static int furnaceDefaultCookTime = 120;
    public static int furnaceDefaultEnergyUsage = 4800;

    // CRUSHER CONFIG //

    public static int crusherDefaultProcessTime = 100;
    public static int crusherDefaultEnergyUsage = 5000;

    // COAL GENERATOR CONFIG //

    public static int coalGeneratorProcessTime = 400;
    public static int coalGeneratorRFPerTick = 80;

    // BASIC POWER CELL CONFIG //

    public static int basicPowerCellCapacity = 1000000; // 1M
    public static int basicPowerCellTransferRate = 360; // 0.36K

    // IMPROVED POWER CELL CONFIG //

    public static int improvedPowerCellCapacity = 15000000; // 15M
    public static int improvedPowerCellTransferRate = 1280; // 1.28K

    // ADVANCED POWER CELL CONFIG //

    public static int advancedPowerCellCapacity = 40000000; // 40M
    public static int advancedPowerCellTransferRate = 3840; // 3.84K

    // WORLD GEN CONFIG //

    public static int copperPerChunk = 16;
    public static int tinPerChunk = 14;
    public static int silverPerChunk = 11;
    public static int aluminiumPerChunk = 17;
    public static int lithiumPerChunk = 6;

    public static int copperPerVein = 6;
    public static int tinPerVein = 10;
    public static int silverPerVein = 5;
    public static int aluminiumPerVein = 7;
    public static int lithiumPerVein = 3;

    public static int copperMaxHeight = 48;
    public static int tinMaxHeight = 52;
    public static int silverMaxHeight = 36;
    public static int aluminiumMaxHeight = 45;
    public static int lithiumMaxHeight = 18;

}
