package com.piron1991.builder_tools.client.items;

import com.piron1991.builder_tools.utilities.BlockPlacingHelper;
import com.piron1991.builder_tools.utilities.BlockRotationHelper;
import com.piron1991.builder_tools.utilities.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DebugStick extends ItemBase {

    public DebugStick(){
    super();
    this.setUnlocalizedName("WoodenHand");
}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("stick");
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int face, float hit_x, float hit_y, float hit_z) {
    LogHelper.info(world.getBlock(x, y, z));
    LogHelper.info(world.getBlockMetadata(x,y,z));
    float[] hits = {hit_x,hit_y,hit_z};
    LogHelper.info(BlockRotationHelper.rotateMetaToFace(world, world.getBlock(x,y,z), x,y,z, face, world.getBlockMetadata(x,y,z), hits, BlockPlacingHelper.drawAxisChecker(Math.abs(player.rotationYaw) % 360)));
    return true;
    }

    public static boolean onItemLeftClick(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int face) {

        LogHelper.info(BlockPlacingHelper.drawAxisChecker(Math.abs(player.rotationYaw) % 360));
        LogHelper.info(BlockPlacingHelper.getSideAxis());

        return true;
    }
}
