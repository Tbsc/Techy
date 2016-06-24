/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

package tbsc.techy.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.ImmutablePair;
import tbsc.techy.Techy;
import tbsc.techy.api.register.*;
import tbsc.techy.api.util.AnnotationUtil;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;
import tbsc.techy.init.MiscInit;
import tbsc.techy.misc.cmd.CommandRetroGen;
import tbsc.techy.network.CPacketEnergyChanged;
import tbsc.techy.network.CPacketUpdateConfig;
import tbsc.techy.network.SPacketSideConfigUpdate;
import tbsc.techy.recipe.IMCRecipeHandler;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common proxy, stuff will run on both sides
 *
 * Created by tbsc on 3/27/16.
 */
public abstract class CommonProxy implements IProxy {

    int packetId = 0;

    private int nextID() {
        return packetId++;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BlockInit.init();
        ItemInit.init();
        loadTechyRegisters();
        MiscInit.preInit();
        Techy.network = NetworkRegistry.INSTANCE.newSimpleChannel("Techy");
        Techy.network.registerMessage(SPacketSideConfigUpdate.Handler.class, SPacketSideConfigUpdate.class, nextID(), Side.SERVER);
        Techy.network.registerMessage(CPacketUpdateConfig.Handler.class, CPacketUpdateConfig.class, nextID(), Side.CLIENT);
        Techy.network.registerMessage(CPacketEnergyChanged.Handler.class, CPacketEnergyChanged.class, nextID(), Side.CLIENT);
        Techy.config = new Configuration(event.getSuggestedConfigurationFile());
        Techy.syncConfig();
    }

    /**
     * Loads all classes annotated with @TechyRegister to the game
     */
    private void loadTechyRegisters() {
        try {
            List<Constructor> registerConstructors = new ArrayList<>();

            // Get all classes under the techy mod (at least under the package tbsc.techy)
            Iterable<Class> classes = AnnotationUtil.getClasses("tbsc.techy");

            // Loop through the classes
            for (Class clazz : classes) {
                // Loop through constructors
                for (Constructor constructor : clazz.getConstructors()) {
                    // Check if constructor is annotated with @TechyRegister
                    if (constructor.isAnnotationPresent(TechyRegister.class)) {
                        // Add to the registerConstructors list
                        registerConstructors.add(constructor);
                    }
                }
            }

            // Map to store data about registered data
            // The string is the identifier, and the object is the registered instance
            Map<String, ImmutablePair<Object, Constructor>> existingIdentifiers = new HashMap<>();

            // Loop through the constructors to be registered
            for (Constructor constructor : registerConstructors) {
                // Get the annotation for data
                TechyRegister annotation = (TechyRegister) constructor.getAnnotation(TechyRegister.class);

                // Make sure there are no duplicate identifiers
                if (!existingIdentifiers.containsKey(annotation.identifier())) {
                    // Create new instance
                    Object instance = constructor.newInstance();

                    // Put it in the registered map so we know what we have registered
                    existingIdentifiers.put(annotation.identifier(), new ImmutablePair<>(instance, constructor));

                    // All annotated constructors should be registered as follows.
                    if (instance instanceof IForgeRegistryEntry) {
                        // Register to the game
                        GameRegistry.register((IForgeRegistryEntry<?>) instance);
                        ItemBlock itemBlock = null;

                        // Is it a block
                        if (instance instanceof Block) {
                            // Should it register an item block
                            if (instance instanceof IHasItemBlock) {
                                // Register an ItemBlock instance of this block
                                GameRegistry.register(itemBlock = new ItemBlock((Block) instance), ((Block) instance).getRegistryName());
                            }

                            // Is it implementing ITechyRegister
                            if (instance instanceof ITechyRegister) {
                                ITechyRegister register = (ITechyRegister) instance;
                                register.initModel(itemBlock);
                            }

                            // Should it register a tile entity
                            if (instance instanceof IHasTileEntity) {
                                // Register the tile entity class from the interface methods
                                try {
                                    GameRegistry.registerTileEntity(((IHasTileEntity) instance).getTileClass(), ((IHasTileEntity) instance).getTileID());
                                } catch (IllegalArgumentException e) {
                                    // If the tile entity is already registered, then silently ignore
                                }
                            }
                        }
                    } else {
                        // Something attempted to register a class that can't be registereed
                        FMLLog.bigWarning("Class %s has attempted to register without being registrable! This is very bad, and should be reported as a bug to the owner of the class! Silently ignoring", constructor.getDeclaringClass().getName());
                    }
                } else {
                    // Something used the same identifier as other class
                    FMLLog.warning("Found duplicate Techy register identifiers, silently ignoring");
                }
            }

            // Loop through the registered constructors
            for (Constructor constructor : registerConstructors) {
                // Loop through the fields in the constructor's class
                for (Field field : constructor.getDeclaringClass().getFields()) {
                    // Is the RegisterInstance annotation present on the field
                    if (field.isAnnotationPresent(RegisterInstance.class)) {
                        // Get annotation data
                        RegisterInstance instanceAnnotation = field.getAnnotation(RegisterInstance.class);
                        // If the identifier has been registered
                        if (existingIdentifiers.containsKey(instanceAnnotation.identifier())) {
                            // If the classes are equal
                            if (existingIdentifiers.get(instanceAnnotation.identifier()).getRight().getDeclaringClass() == instanceAnnotation.registerClass()) {
                                // Give access
                                field.setAccessible(true);
                                // In the instance we created, assign the value of the field to the same instance
                                field.set(existingIdentifiers.get(instanceAnnotation.identifier()).getLeft(), existingIdentifiers.get(instanceAnnotation.identifier()).getLeft());
                                // Take access
                                field.setAccessible(false);
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // Exception, so crash, although:
            // - ClassNotFoundException can't occur, due to fetching classes from disk
            // - IOException can occur, but very unlikely
            // - InstantiationException can occur if the constructor annotated has parameters, therefore I can't init it
            // - IllegalAccessException can't happen because I make sure that I have access
            // - InvocationTargetException can't happen because there should be any exceptions
            e.printStackTrace();
            // But even if something happens, then print the stack trace, and continue with the game cycle
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        MiscInit.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        MiscInit.postInit();
    }

    @Override
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandRetroGen());
    }

    @Override
    public void imcMessageReceived(FMLInterModComms.IMCEvent event) {
        IMCRecipeHandler.imcMessageReceived(event.getMessages());
    }

}
