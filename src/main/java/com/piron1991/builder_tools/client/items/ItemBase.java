package com.piron1991.builder_tools.client.items;

import com.piron1991.builder_tools.creativeTab.CreativeTab;

import com.piron1991.builder_tools.reference.Reference;
import com.piron1991.builder_tools.utilities.BlockPlacingHelper;
import com.piron1991.builder_tools.utilities.BlockRotationHelper;
import com.piron1991.builder_tools.utilities.LogHelper;
import com.piron1991.builder_tools.utilities.NBTHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.util.RotationHelper;

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
        NBTHelper.setInt(itemstack, BlockMeta, meta);
    }

    public HashMap getClickedBlock(ItemStack itemstack,World world, int x, int y, int z){
        HashMap tempMap = new HashMap();
        String string=NBTHelper.getString(itemstack, BlockName);
        int meta = NBTHelper.getInt(itemstack,BlockMeta);
        tempMap.put(BlockName,string);
        tempMap.put(BlockMeta,meta);
        return tempMap;
    }
    //TODO get rid of static(probably need to check registering new event for that)
    public static boolean onItemLeftClick(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int face) {
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
    public boolean checkCollisions(World world, Block block, int x, int y, int z, int cord_x, int cord_y, int cord_z){
        /*TODO: fix for NPE when grass like blocks are clicked as target
        Stacktrace:
	at net.minecraft.world.World.getEntitiesWithinAABBExcludingEntity(World.java:3452)
	at net.minecraft.world.World.getEntitiesWithinAABBExcludingEntity(World.java:3446)
	at net.minecraft.world.World.checkNoEntityCollision(World.java:2336)
	at net.minecraft.world.World.checkNoEntityCollision(World.java:2328)
	at com.piron1991.builder_tools.client.items.ItemBase.checkCollisions(ItemBase.java:93)
	at com.piron1991.builder_tools.client.items.WoodenHand.onItemUse(WoodenHand.java:101)
	at net.minecraft.item.ItemStack.tryPlaceItemIntoWorld(ItemStack.java:145)
	at net.minecraft.server.management.ItemInWorldManager.activateBlockOrUseItem(ItemInWorldManager.java:422)
	at net.minecraft.network.NetHandlerPlayServer.processPlayerBlockPlacement(NetHandlerPlayServer.java:591)
	at net.minecraft.network.play.client.C08PacketPlayerBlockPlacement.processPacket(C08PacketPlayerBlockPlacement.java:74)
	at net.minecraft.network.play.client.C08PacketPlayerBlockPlacement.processPacket(C08PacketPlayerBlockPlacement.java:122)
	at net.minecraft.network.NetworkManager.processReceivedPackets(NetworkManager.java:247)



	Stacktrace:
	at net.minecraft.world.World.getEntitiesWithinAABBExcludingEntity(World.java:3452)
	at net.minecraft.world.World.getEntitiesWithinAABBExcludingEntity(World.java:3446)
	at net.minecraft.world.World.checkNoEntityCollision(World.java:2336)
	at net.minecraft.world.World.checkNoEntityCollision(World.java:2328)
	at com.piron1991.builder_tools.client.items.ItemBase.checkCollisions(ItemBase.java:102)
	at com.piron1991.builder_tools.client.items.WoodenHand.onItemUse(WoodenHand.java:98)
	at net.minecraft.item.ItemStack.tryPlaceItemIntoWorld(ItemStack.java:145)
	at net.minecraft.server.management.ItemInWorldManager.activateBlockOrUseItem(ItemInWorldManager.java:422)
	at net.minecraft.network.NetHandlerPlayServer.processPlayerBlockPlacement(NetHandlerPlayServer.java:591)
	at net.minecraft.network.play.client.C08PacketPlayerBlockPlacement.processPacket(C08PacketPlayerBlockPlacement.java:74)
	at net.minecraft.network.play.client.C08PacketPlayerBlockPlacement.processPacket(C08PacketPlayerBlockPlacement.java:122)
	at net.minecraft.network.NetworkManager.processReceivedPackets(NetworkManager.java:247)

         */
        return (world.checkNoEntityCollision(block.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.getBlock(cord_x,cord_y,cord_z).getMaterial()== Material.air);
    }
    public boolean checkInventory(EntityPlayer player, Block block) {
        InventoryPlayer inventory = player.inventory;
        Item item = Item.getItemFromBlock(block);
        return inventory.hasItem(item);
    }

    public void placeBlockOnWorld(World world, Block block,int[] cords,float[] hits, int face, int meta, int offsetX,int offsetY, int offsetZ, int axis){
        //TODO: decide if should change slab into doubleslab
        int newMeta= BlockRotationHelper.rotateMetaToFace(world,block,cords[0] - offsetX, cords[1] - offsetY, cords[2] - offsetZ,face,meta,hits,axis);
        if (block.canPlaceBlockAt(world, cords[0] - offsetX, cords[1] - offsetY, cords[2] - offsetZ)) {
            world.setBlock(cords[0] - offsetX, cords[1] - offsetY, cords[2] - offsetZ, block, newMeta, 3);
        }
        block.rotateBlock(world,cords[0] - offsetX, cords[1] - offsetY, cords[2] - offsetZ, ForgeDirection.getOrientation(face));
    }

}
