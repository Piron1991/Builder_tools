package com.piron1991.builder_tools.client.items;

import com.piron1991.builder_tools.handler.ConfigHandler;
import com.piron1991.builder_tools.utilities.BlockPlacingHelper;
import com.piron1991.builder_tools.utilities.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.HashMap;

public class StoneHand extends ItemBase {


    public StoneHand() {
        super();
        this.setUnlocalizedName("StoneHand");
    }


    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int face, float hit_x, float hit_y, float hit_z) {
        if (!world.isRemote ){

            //boolean teCheck=world.getBlock(x,y,z).hasTileEntity(world.getBlockMetadata(x,y,z));
            if(!player.isSneaking()) {
                //save to nbt clicked block name and its meta
                setCLickedBlock(itemstack, world, x, y, z);

            } else if (player.isSneaking()) {
                //get saved block name and meta
                HashMap map = getClickedBlock(itemstack, world, x, y, z);

                Block chosenBlock = Block.getBlockFromName((String) map.get(BlockName));
                int meta;
                if (map.get(BlockMeta)==null){
                    meta=0;
                }else{
                    meta = (Integer) map.get(BlockMeta);
                }
                if (chosenBlock != null) {
                    //array for easy block placing checks and use
                    int[] tempCord={x,y,z};
                    float[] tempHit={hit_x,hit_y,hit_z};
                    tempCord= BlockPlacingHelper.sideChecker(face, tempCord);

                    //get block that player clicked
                    Block clickedBlock = world.getBlock(x, y, z);
                    //check if should place on x or z, for top and bottom face use only
                    int xzCheck= BlockPlacingHelper.drawAxisChecker(Math.abs(player.rotationYaw) % 360);

                    //set sizing for block placing
                    int min_i= BlockPlacingHelper.getMinI(getPlacingSize());
                    int max_i= BlockPlacingHelper.getMaxI(getPlacingSize());
                    int min_j= BlockPlacingHelper.getMinJ(getPlacingSize());
                    int max_j= BlockPlacingHelper.getMaxJ(getPlacingSize());

                    boolean yCheck=BlockPlacingHelper.getSideAxis();

                    switch (face) {
                        case 0: {
                            if (xzCheck == 0 || xzCheck == 2) {
                                for (int j =min_j;j<=max_j;j++){
                                    for (int i = min_i; i <= max_i; i++) {
                                        LogHelper.info(tempCord[0]+"i: "+i+"/max_i: "+max_i);
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0]-i,tempCord[1],tempCord[2]) && checkInventory(player,chosenBlock)){
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, i,0,0,xzCheck);
                                            //world.setBlock(tempCord[0]-i,tempCord[1],tempCord[2], chosenBlock, meta, 3);

                                        }
                                    }
                                    tempCord[1]=tempCord[1]-1;

                                }
                            } else if (xzCheck == 1 || xzCheck == 3) {
                                for (int j =min_j;j<=max_j;j++) {
                                    for (int i = min_i; i <= max_i; i++) {
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0], tempCord[1], tempCord[2]-i) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, 0,0,i,xzCheck);
                                            //world.setBlock(tempCord[0], tempCord[1], tempCord[2]-i, chosenBlock, meta, 3);
                                        }
                                    }
                                    tempCord[1] = tempCord[1] - 1;
                                }
                            }
                            break;
                        }
                        case 1: {
                            if (xzCheck == 0 || xzCheck == 2) {
                                for (int j =min_j;j<=max_j;j++) {
                                    for (int i = min_i; i <= max_i; i++) {
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0]-i, tempCord[1], tempCord[2]) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, i,0,0,xzCheck);
                                            //world.setBlock(tempCord[0]-i, tempCord[1], tempCord[2], chosenBlock, meta, 3);
                                        }
                                    }
                                    tempCord[1] = tempCord[1] + 1;
                                }

                            } else if (xzCheck == 1 || xzCheck == 3) {
                                for (int j = min_j; j <= max_j; j++) {
                                    for (int i = min_i; i <= max_i; i++) {
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0], tempCord[1], tempCord[2]-i) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, 0,0,i,xzCheck);
                                            //world.setBlock(tempCord[0], tempCord[1], tempCord[2]-i, chosenBlock, meta, 3);
                                        }
                                    }
                                    tempCord[1] = tempCord[1] + 1;
                                }
                            }
                            break;
                        }

                        case 2: {
                            for (int j = min_j; j <= max_j; j++) {
                                for (int i = min_i; i <= max_i; i++) {
                                    if (yCheck){
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0]-i, tempCord[1], tempCord[2]) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, i,0,0,xzCheck);
                                            //world.setBlock(tempCord[0]-i, tempCord[1], tempCord[2], chosenBlock, meta, 3);
                                             }
                                    }else{
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0], tempCord[1]-i, tempCord[2]) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, 0,i,0,xzCheck);
                                            //world.setBlock(tempCord[0], tempCord[1]-i, tempCord[2], chosenBlock, meta, 3);
                                        }
                                    }
                                }
                                tempCord[2] = tempCord[2] - 1;
                            }

                            break;

                        }
                        case 3: {
                            for (int j = min_j; j <= max_j; j++) {
                                for (int i = min_i; i <= max_i; i++) {

                                    if (yCheck){
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0]-i, tempCord[1], tempCord[2]) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, i,0,0,xzCheck);
                                            //world.setBlock(tempCord[0]-i, tempCord[1], tempCord[2], chosenBlock, meta, 3);
                                             }
                                    }else{
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0], tempCord[1]-i, tempCord[2]) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, 0,i,0,xzCheck);
                                            //world.setBlock(tempCord[0], tempCord[1]-i, tempCord[2], chosenBlock, meta, 3);
                                             }
                                    }

                                }
                                tempCord[2] = tempCord[2] + 1;
                            }

                            break;
                        }
                        case 4: {
                            for (int j = min_j; j <= max_j; j++) {
                                for (int i = min_i; i <= max_i; i++) {


                                    if (yCheck){
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0], tempCord[1], tempCord[2]-i) && checkInventory(player,chosenBlock)){
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, 0,0,i,xzCheck);
                                            //world.setBlock(tempCord[0], tempCord[1], tempCord[2]-i, chosenBlock, meta, 3);
                                        }
                                    }else{
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0], tempCord[1]-i, tempCord[2]) && checkInventory(player,chosenBlock)){
                                            consumeItem(player,chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, 0,i,0,xzCheck);
                                            //world.setBlock(tempCord[0], tempCord[1]-i, tempCord[2], chosenBlock, meta, 3);
                                        }
                                    }
                                }

                                tempCord[0] = tempCord[0] - 1;
                            }

                            break;
                        }
                        case 5: {
                            for (int j = min_j; j <= max_j; j++) {
                                for (int i = min_i; i <= max_i; i++) {

                                    if (yCheck){
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord[0], tempCord[1], tempCord[2]-i) && checkInventory(player,chosenBlock)){
                                            consumeItem(player, chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, 0,0,i,xzCheck);
                                            //world.setBlock(tempCord[0], tempCord[1], tempCord[2]-i, chosenBlock, meta, 3);
                                        }
                                    }else{
                                        if (checkCollisions(world, clickedBlock, x, y, z,tempCord[0], tempCord[1]-i, tempCord[2]) && checkInventory(player,chosenBlock)){
                                            consumeItem(player, chosenBlock);
                                            placeBlockOnWorld(world, chosenBlock,tempCord,tempHit,face,meta, 0,i,0,xzCheck);
                                            //world.setBlock(tempCord[0], tempCord[1]-i, tempCord[2], chosenBlock, meta, 3);
                                        }
                                    }
                                }
                                tempCord[0] = tempCord[0] + 1;
                            }

                            break;
                        }

                    }
                }




            }
        }
        // True if something happen and false if it don't
        return true;
    }


    public int getPlacingSize(){return ConfigHandler.stoneToolSize;}
}
