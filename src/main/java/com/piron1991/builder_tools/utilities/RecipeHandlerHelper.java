package com.piron1991.builder_tools.utilities;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class RecipeHandlerHelper {
    public String name;
    public String domain;
    public int meta;

    private String item;
    private int colonIndex;
    private int metaIndex;

    public RecipeHandlerHelper(String object) {
        if (object=="empty"){
            name=" ";
            meta=0;
        }else if (!object.contains(":")||!object.contains(".")) {
            name = object;
        } else if (object.contains(":")) {

            colonIndex = object.indexOf(':');
            domain = object.substring(0, colonIndex);
            item = object.substring(colonIndex + 1, object.length());
            metaIndex= item.indexOf(':');

            if (metaIndex >= 0) {
                meta = Integer.parseInt(item.substring(meta + 1, item.length()));
                name = item.substring(0, meta);
            } else {
                name = item;
                meta = 0;
            }
        } else {
            LogHelper.error("Wrong ore dictionary or item entry: "+object.toUpperCase());
        }
    }
    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(Blocks.command_block);
        if (this.name != null && this.domain == null) {
            ArrayList<ItemStack> orename = OreDictionary.getOres(this.name);
            if (orename.size() > 0) {
                item = orename.get(0);
            }
        } else if(this.name != null && this.domain != null) {
            item = new ItemStack((Item)Item.itemRegistry.getObject(domain + ":" + name), 1, this.meta);
        }else{item=null;
    }
        return item;
    }

    public String getName(){
        return this.name;
    }
}
