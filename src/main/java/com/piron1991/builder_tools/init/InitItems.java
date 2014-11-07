package com.piron1991.builder_tools.init;

import com.piron1991.builder_tools.client.items.DebugStick;
import com.piron1991.builder_tools.client.items.ItemBase;
import com.piron1991.builder_tools.client.items.StoneHand;
import com.piron1991.builder_tools.client.items.WoodenHand;
import com.piron1991.builder_tools.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class InitItems {

    //public static final ItemBase myItemVar = new ItemClass();
    public static final ItemBase woodhand = new WoodenHand();
    public static final ItemBase stonehand = new StoneHand();
    public static final ItemBase debugstick= new DebugStick();

    public static void init() {
        // GameRegistry.registerItem(myItemVar, "myItemName");
        GameRegistry.registerItem(woodhand, "WoodenHand");
        GameRegistry.registerItem(stonehand,"StoneHand");
        GameRegistry.registerItem(debugstick,"debug");
    }
}
