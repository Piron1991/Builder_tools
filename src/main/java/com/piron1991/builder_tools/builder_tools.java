package com.piron1991.builder_tools;

import com.piron1991.builder_tools.handler.BlockEventHandler;
import com.piron1991.builder_tools.handler.ConfigHandler;
import com.piron1991.builder_tools.init.InitItems;
import com.piron1991.builder_tools.init.InitRecipe;
import com.piron1991.builder_tools.proxy.IProxy;
import com.piron1991.builder_tools.reference.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;



@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)


public class builder_tools {

    @Mod.Instance(Reference.MOD_ID)
    public static com.piron1991.builder_tools.builder_tools instance;

    @SidedProxy(clientSide = Reference.CPROXY, serverSide = Reference.SPROXY)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.preinit(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());

        MinecraftForge.EVENT_BUS.register(new BlockEventHandler());

        InitItems.init();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        InitRecipe.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){

        }
}
