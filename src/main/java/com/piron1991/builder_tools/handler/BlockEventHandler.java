package com.piron1991.builder_tools.handler;


import com.piron1991.builder_tools.client.items.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import com.piron1991.builder_tools.render.FakeBBRender;


public class BlockEventHandler {


    public RenderGlobal context;
    public EntityPlayer player;
    public MovingObjectPosition target;
    public int subID;
    public ItemStack currentItem;
    public float partialTicks;

    @SubscribeEvent
    public void onHighlight(DrawBlockHighlightEvent event) {

        context = event.context;
        player = event.player;
        target = event.target;
        subID = event.subID;
        currentItem = event.currentItem;
        partialTicks = event.partialTicks;
        int block_x = target.blockX;
        int block_y = target.blockY;
        int block_z = target.blockZ;
        World world = player.getEntityWorld();

        if (subID==0 &&
                target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                currentItem!=null &&
                currentItem.getItem() instanceof ItemBase) {

            if(event.isCancelable()){event.setCanceled(true);}
            //draw a bounding box for possible block placement
            FakeBBRender.bb(world, player, block_x, block_y, block_z, target.sideHit, partialTicks, context);

        }
    }

}
