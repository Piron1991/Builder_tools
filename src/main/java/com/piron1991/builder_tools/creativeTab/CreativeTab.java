package com.piron1991.builder_tools.creativeTab;

import com.piron1991.builder_tools.init.InitItems;
import com.piron1991.builder_tools.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab {

    public static final CreativeTabs testTab = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {

        @Override
        public Item getTabIconItem() {
            //return item from registry
            return InitItems.woodhand;
        }
    };


}
