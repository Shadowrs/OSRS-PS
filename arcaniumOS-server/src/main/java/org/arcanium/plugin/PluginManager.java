package org.arcanium.plugin;

import org.arcanium.game.content.activity.ActivityManager;
import org.arcanium.game.content.activity.ActivityPlugin;
import org.arcanium.game.content.dialogue.DialoguePlugin;
import org.arcanium.game.system.SystemLogger;
import plugin.PluginRepo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class used to handle the loading of all plugins.
 *
 * @author Emperor
 */
public final class PluginManager {

    /**
     * The amount of plugins loaded.
     */
    private static int pluginCount;

    /**
     * The currently loaded plugin names.
     */
    private static List<String> loadedPlugins = new ArrayList<>();

    /**
     * Initializes the plugin manager.
     */
    public static void init() {
        try {
            loadLocal(new File("plugin/"));
            loadedPlugins.clear();
            loadedPlugins = null;
            PluginRepo.init();
            for (Plugin p : PluginRepo.combatPluginRepo) {
                definePlugin(p);
            }
            SystemLogger.log("Initialized " + PluginRepo.combatPluginRepo.size() + " Combat Plugins...");
            for (Plugin p : PluginRepo.dialoguePluginRepo) {
                definePlugin(p);
            }
            SystemLogger.log("Initialized " + PluginRepo.dialoguePluginRepo.size() + " Dialogue Plugins...");
            for (Plugin p : PluginRepo.interactionPluginRepo) {
                definePlugin(p);
            }
            SystemLogger.log("Initialized " + PluginRepo.interactionPluginRepo.size() + " Interaction Plugins...");
            for (Plugin p : PluginRepo.NPCPluginRepo) {
                definePlugin(p);
            }
            SystemLogger.log("Initialized " + PluginRepo.NPCPluginRepo.size() + " NPC Plugins...");
            for (Plugin p : PluginRepo.skillPluginRepo) {
                definePlugin(p);
            }
            SystemLogger.log("Initialized " + PluginRepo.skillPluginRepo.size() + " Skill Plugins...");
            for (Plugin p : PluginRepo.tutorialPluginRepo) {
                definePlugin(p);
            }
            SystemLogger.log("Initialized " + PluginRepo.tutorialPluginRepo.size() + " Tutorial Plugins...");
        } catch (Throwable t) {
            SystemLogger.log("Error initializing Plugins...");
        }
    }

    /**
     * Loads the plugins in the local directory.
     *
     * @param directory The directory.
     * @throws Throwable When an exception occurs.
     */
    @SuppressWarnings("rawtypes")
    public static void loadLocal(File directory) throws Throwable {

    }

    /**
     * Defines a list of plugins.
     *
     * @param plugins the plugins.
     */
    public static void definePlugin(Plugin<?>... plugins) {
        for (Plugin<?> p : plugins) {
            definePlugin(p);
        }
    }

    /**
     * Defines the plugin.
     *
     * @param plugin The plugin.
     */
    @SuppressWarnings("unchecked")
    public static void definePlugin(Plugin<?> plugin) {
        try {
            PluginManifest manifest = plugin.getClass().getAnnotation(PluginManifest.class);
            if (manifest == null) {
                manifest = plugin.getClass().getSuperclass().getAnnotation(PluginManifest.class);
            }
            if (manifest == null || manifest.type() == PluginType.ACTION) {
                plugin.newInstance(null);
            } else if (manifest.type() == PluginType.DIALOGUE) {
                ((DialoguePlugin) plugin).init();
            } else if (manifest.type() == PluginType.ACTIVITY) {
                ActivityManager.register((ActivityPlugin) plugin);
            } else if (manifest.type() == PluginType.LOGIN) {
                //LoginConfiguratiFon.getLoginPlugins().add((Plugin<Object>) plugin);
            } else {
                System.out.println("Manifest: " + manifest.type());
            }
            pluginCount++;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the amount of plugins currently loaded.
     *
     * @return The plugin count.
     */
    public static int getAmountLoaded() {
        return pluginCount;
    }

    /**
     * Checks if the plugin shouldn't be repacked.
     *
     * @param name The plugin name.
     * @return {@code True} if so.
     */
    public static boolean isAutoPacked(String name) {
        return name.equals("VicTheTraderPlugin.jar");
    }
}