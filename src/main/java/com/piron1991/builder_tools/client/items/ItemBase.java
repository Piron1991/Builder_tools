package com.piron1991.builder_tools.client.items;

import com.piron1991.builder_tools.creativeTab.CreativeTab;
import com.piron1991.builder_tools.reference.NBTreference;
import com.piron1991.builder_tools.reference.Reference;
import com.piron1991.builder_tools.utilities.NBTHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.HashMap;

public class ItemBase extends Item {

    public static String BlockName="SavedWorldBlock";
    public static String BlockMeta="SavedWorldMeta";

    public ItemBase() {
        super();
        this.setCreativeTab(CreativeTab.testTab);
        this.setMaxStackSize(1);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }



    public void setCLickedBlock(ItemStack itemstack, World world, int x, int y, int z){
        String blockName= GameRegistry.findUniqueIdentifierFor(world.getBlock(x, y, z)).toString();
        blockName=blockName.substring(blockName.indexOf(":") + 1);
        int meta = world.getBlockMetadata(x,y,z);
        NBTHelper.setString(itemstack, BlockName, blockName);
        NBTHelper.setInt(itemstack, BlockMeta, meta);}

    public HashMap getClickedBlock(ItemStack itemstack,World world, int x, int y, int z){
        HashMap tempMap = new HashMap();
        String string=NBTHelper.getString(itemstack, BlockName);
        int meta = NBTHelper.getInt(itemstack,BlockMeta);
        tempMap.put(BlockName,string);
        tempMap.put(BlockMeta,meta);
        return tempMap;}


    public boolean onItemLeftClick(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int face, float hit_x, float hit_y, float hit_z) {

        return true;
    }

    public static void consumeItem(EntityPlayer player,Block block){

        InventoryPlayer inventory = player.inventory;
        Item item = Item.getItemFromBlock(block);

        for (int i =0;i<=inventory.getSizeInventory();i++){
            ItemStack stack =inventory.getStackInSlot(i);
            if(stack != null && stack.getItem() == item){
                inventory.decrStackSize(i,1);
                player.inventoryContainer.detectAndSendChanges();
                break;
            }
        }
    }
    public boolean checkCollisions(World world, Block block, int x, int y, int z, int[] cords){
        return (world.checkNoEntityCollision(block.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.getBlock(cords[0],cords[1],cords[2]).getMaterial()== Material.air);
    }

    public boolean checkInventory(EntityPlayer player, Block block) {
        InventoryPlayer inventory = player.inventory;
        Item item = Item.getItemFromBlock(block);
        return inventory.hasItem(item);
    }
    public String getChosenBlockNameString(){return BlockName;}
    public String getChosenBlockMetaString(){return BlockMeta;}
}
