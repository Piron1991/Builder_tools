package com.piron1991.builder_tools.render;


import com.piron1991.builder_tools.client.items.StoneHand;
import com.piron1991.builder_tools.client.items.WoodenHand;
import com.piron1991.builder_tools.handler.ConfigHandler;
import com.piron1991.builder_tools.utilities.BlockPlacingHelper;
import com.piron1991.builder_tools.utilities.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;


public class FakeBBRender {

    private static float red= ConfigHandler.borderRed/255;
    private static float green= ConfigHandler.borderGreen/255;
    private static float blue= ConfigHandler.borderBlue/255;
    private static float alpha= ConfigHandler.borderAlpha;
    private static float width= ConfigHandler.borderWidth;
    private static int min_i=0;
    private static int max_i=0;
    private static int min_j=0;
    private static int max_j=0;

    //draw possible block placement
    public static void bb(World world,EntityPlayer player, int block_x, int block_y, int block_z,int side,float tick, RenderGlobal context) {
        if (world.isRemote) {
            //set sizes to render, for item that is held by player
            setSizing(player);
            if (min_i != 0 && max_i != 0 && min_j != 0 && max_j != 0) {
                //bounding box code for blending with existing block
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glColor4f(red, green, blue, alpha);
                GL11.glLineWidth(width);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDepthMask(false);
                float expansion_border = 0.0002F;

                //get block that mouse is pointing
                Block block = world.getBlock(block_x, block_y, block_z);

                if (block.getMaterial() != Material.air) {

                    //base for offsetting rendering box
                    double offset_x = (player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) tick) - expansion_border * 2;
                    double offset_y = (player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) tick) - expansion_border * 2;
                    double offset_z = (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) tick) - expansion_border * 2;

                    //check which side player is facing to render on x or z axis when pointing on top or bottom of block
                    int xzCheck = BlockPlacingHelper.drawAxisChecker(Math.abs(player.rotationYaw) % 360);
                    //actual render code
                    //TODO: stop grabbing BB from pointed block, grab it from selected one or render just cube
                    switch (side) {
                        case 0: {
                            if (xzCheck == 0 || xzCheck == 2) {
                                for (int j = min_j; j <= max_j; j++) {
                                    offset_y++;
                                    for (int i = min_i; i <= max_i; i++) {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-(offset_x - i), -offset_y, -offset_z), -1);
                                    }
                                }
                            } else if (xzCheck == 1 || xzCheck == 3) {
                                for (int j = min_j; j <= max_j; j++) {
                                    offset_y++;
                                    for (int i = min_i; i <= max_i; i++) {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-offset_x, -offset_y, -(offset_z - i)), -1);
                                    }
                                }
                            }
                            break;
                        }
                        case 1: {
                            if (xzCheck == 0 || xzCheck == 2) {
                                for (int j = min_j; j <= max_j; j++) {
                                    offset_y--;
                                    for (int i = min_i; i <= max_i; i++) {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-(offset_x - i), -offset_y, -offset_z), -1);
                                    }
                                }
                            } else if (xzCheck == 1 || xzCheck == 3) {
                                for (int j = min_j; j <= max_j; j++) {
                                    offset_y--;
                                    for (int i = min_i; i <= max_i; i++) {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-offset_x, -offset_y, -(offset_z - i)), -1);
                                    }
                                }
                            }
                            break;
                        }
                        case 2: {
                            for (int j = min_j; j <= max_j; j++) {
                                offset_z++;
                                for (int i = min_i; i <= max_i; i++) {
                                    if (BlockPlacingHelper.getSideAxis()) {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-(offset_x - i), -offset_y, -offset_z), -1);
                                    } else {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-offset_x, -(offset_y - i), -offset_z), -1);
                                    }
                                }
                            }
                            break;
                        }
                        case 3: {
                            for (int j = min_j; j <= max_j; j++) {
                                offset_z--;
                                for (int i = min_i; i <= max_i; i++) {
                                    if (BlockPlacingHelper.getSideAxis()) {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-(offset_x - i), -offset_y, -offset_z), -1);
                                    } else {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-offset_x, -(offset_y - i), -offset_z), -1);
                                    }
                                }
                            }
                            break;

                        }
                        case 4: {
                            for (int j = min_j; j <= max_j; j++) {
                                offset_x++;
                                for (int i = min_i; i <= max_i; i++) {
                                    if (BlockPlacingHelper.getSideAxis()) {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-offset_x, -offset_y, -(offset_z - i)), -1);
                                    } else {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-offset_x, -(offset_y - i), -offset_z), -1);
                                    }
                                }
                            }
                            break;
                        }

                        case 5: {
                            for (int j = min_j; j <= max_j; j++) {
                                offset_x--;
                                for (int i = min_i; i <= max_i; i++) {
                                    if (BlockPlacingHelper.getSideAxis()) {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-offset_x, -offset_y, -(offset_z - i)), -1);
                                    } else {
                                        context.drawOutlinedBoundingBox(block.getSelectedBoundingBoxFromPool(world, block_x, block_y, block_z).expand((double) expansion_border, (double) expansion_border, (double) expansion_border).getOffsetBoundingBox(-offset_x, -(offset_y - i), -offset_z), -1);
                                    }
                                }
                            }
                            break;

                        }

                    }
                    GL11.glDepthMask(true);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_BLEND);
                }
            }
        }
    }


    private static void setMinI(int size){
        min_i= BlockPlacingHelper.getMinI(size);
    }

    private static void setMaxI(int size){
        max_i= BlockPlacingHelper.getMaxI(size);
    }
    private static void setMinJ(int size){
        min_j= BlockPlacingHelper.getMinJ(size);
    }
    private static void setMaxJ(int size){
        max_j= BlockPlacingHelper.getMaxJ(size);
    }

    //checks what item player is holding and then set ranges for rendering based on that item type
    private static void setSizing(EntityPlayer player){
        Item heldItem=player.getHeldItem().getItem();

        if(heldItem instanceof WoodenHand){
            setMinI(ConfigHandler.woodenToolSize);
            setMaxI(ConfigHandler.woodenToolSize);
            setMinJ(-2);
            setMaxJ(2);

        }else if (heldItem instanceof StoneHand){
            setMinI(ConfigHandler.stoneToolSize);
            setMaxI(ConfigHandler.stoneToolSize);
            setMinJ(ConfigHandler.stoneToolSize);
            setMaxJ(ConfigHandler.stoneToolSize);
            //}else if (heldItem instanceof ironHand){

            //}else if (heldItem instanceof goldenHand){

            //}else if (heldItem instanceof DiamondHand){

        }else{
            setMinI(0);
            setMaxI(0);
            setMinJ(0);
            setMaxJ(0);
        }

    }
}
