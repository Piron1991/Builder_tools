package com.piron1991.builder_tools.handler;


import com.piron1991.builder_tools.client.items.*;
import com.piron1991.builder_tools.utilities.BlockPlacingHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import com.piron1991.builder_tools.render.FakeBBRender;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BlockEventHandler {

    @SubscribeEvent
    public void onHighlight(DrawBlockHighlightEvent event) {

        int block_x = event.target.blockX;
        int block_y = event.target.blockY;
        int block_z = event.target.blockZ;
        World world = event.player.getEntityWorld();

        if (event.subID==0 &&
                event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                event.currentItem!=null &&
                event.currentItem.getItem() instanceof ItemBase) {

            if(event.isCancelable()){event.setCanceled(true);}
            //draw a bounding box for possible block placement
            FakeBBRender.bb(world, event.player, block_x, block_y, block_z, event.target.sideHit, event.partialTicks, event.context);

        }
    }


    @SubscribeEvent
    public void onLeftClick(PlayerInteractEvent event){

        ItemStack stack=event.entityPlayer.getHeldItem();
        if (stack!=null){
        Item item =stack.getItem();
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK &&
                item instanceof ItemBase) {
                BlockPlacingHelper.setSideAxis();
                ItemBase.onItemLeftClick(stack, event.entityPlayer, event.entityPlayer.getEntityWorld(), event.x, event.y, event.z, event.face);
            }
        }
}
}
