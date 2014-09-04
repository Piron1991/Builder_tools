package com.piron1991.builder_tools.handler;

import com.piron1991.builder_tools.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.List;

public class ConfigHandler {

    public static Configuration config;

    public static int borderRed;
    public static int borderGreen;
    public static int borderBlue;
    public static float borderAlpha;
    public static float borderWidth;

    public static int woodenToolSize;
    public static String[] woodenToolRecipeDefault={"plankWood","plankWood","plankWood","empty", "logWood", "plankWood","stickWood","empty","plankWood"};
    public static String[] woodenToolRecipe;

    public static int stoneToolSize;
    public static String[] stoneToolRecipeDefault={"cobblestone","cobblestone","cobblestone","empty", "minecraft:stone", "cobblestone","stickWood","empty","cobblestone"};
    public static String[] stoneToolRecipe;

    public static int ironToolSize;
    public static String[] ironToolRecipeDefault={"ingotIron","ingotIron","ingotIron","empty", "blockIron", "ingotIron","stickWood","empty","ingotIron"};
    public static String[] ironToolRecipe;

    public static int goldToolSize;
    public static String[] goldToolRecipeDefault={"ingotGold","ingotGold","ingotGold","empty", "blockGold", "ingotGold","stickWood","empty","ingotGold"};
    public static String[] goldToolRecipe;

    public static int ironToolSizeMinX;
    public static int ironToolSizeMaxX;

    public static int goldToolSizeMinX;
    public static int goldToolSizeMaxX;
    public static int goldToolSizeMinY;
    public static int goldToolSizeMaxY;

    public static void preinit(File configFile) {

        if (config == null) {
            config = new Configuration(configFile);}
        loadConfig();
    }


    @SubscribeEvent
    public void OnConfigChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(Reference.MOD_ID)) {
            loadConfig();
        }

    }

    private static void loadConfig() {



        borderRed=config.getInt("Red",config.CATEGORY_GENERAL, 255, 0, 255, "Red hue for border.");
        borderGreen=config.getInt("Green",config.CATEGORY_GENERAL, 255, 0, 255, "Green hue for border.");
        borderBlue=config.getInt("Blue",config.CATEGORY_GENERAL, 255, 0, 255, "Blue hue for border.");
        borderAlpha=config.getFloat("Alpha",config.CATEGORY_GENERAL, 1.0F, 0.0F, 1.0F, "Transparency for border.");
        borderWidth=config.getFloat("Width",config.CATEGORY_GENERAL,4.0F,1.0F,8.0F,"Width of the borderline.");

        woodenToolSize=config.getInt("Wooden tool size", Reference.SIZE_CATEGORY,3,1,15,"Range for block placing for wooden tool.");
        stoneToolSize=config.getInt("Stone tool size", Reference.SIZE_CATEGORY, 3, 1, 15, "Range for block placing for stone tool,count for both x and y range.");
        //ironToolSizeMinX=config.getInt("IRON_TOOL_SIZE_MIN_X", Reference.SIZE_CATEGORY, 1, 1, 15, "Min range for block placing for iron tool.");
        //ironToolSizeMaxX=config.getInt("IRON_TOOL_SIZE_MAX_X", Reference.SIZE_CATEGORY, 5, 1, 15, "Max range for block placing for iron tool.");
        //goldToolSizeMinX=config.getInt("GOLD_TOOL_SIZE_X", Reference.SIZE_CATEGORY, 1, 1, 15, "Min x range for block placing for gold tool.");
        //goldToolSizeMinY=config.getInt("GOLD_TOOL_SIZE_Y", Reference.SIZE_CATEGORY, 1, 1, 15, "Min Y range for block placing for gold tool.");
        //goldToolSizeMaxX=config.getInt("GOLD_TOOL_SIZE_X", Reference.SIZE_CATEGORY, 7, 1, 15, "Max x range for block placing for gold tool.");
        //goldToolSizeMaxY=config.getInt("GOLD_TOOL_SIZE_Y", Reference.SIZE_CATEGORY, 7, 1, 15, "Max y range for block placing for gold tool.");


        woodenToolRecipe=config.get("Wooden tool recipe", Reference.RECIPE_CATEGORY,woodenToolRecipeDefault).getStringList();
        stoneToolRecipe=config.get("Stone tool recipe", Reference.RECIPE_CATEGORY,stoneToolRecipeDefault).getStringList();
        //ironToolRecipe=config.get("IRON_TOOL_RECIPE", Reference.RECIPE_CATEGORY,ironToolRecipeDefault).getStringList();
        //goldToolRecipe=config.get("GOLD_TOOL_RECIPE", Reference.RECIPE_CATEGORY,goldToolRecipeDefault).getStringList();

        config.addCustomCategoryComment(Reference.SIZE_CATEGORY,"Values always rounded down to odd number.");
        config.addCustomCategoryComment(Reference.RECIPE_CATEGORY,"Valid options for items are:\n empty\n modDomain:item\n oredict\nif entry isn't valid item will be replaced with command block ");
        if (config.hasChanged()) {
            config.save();
        }


    }


}

