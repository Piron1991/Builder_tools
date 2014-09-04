package com.piron1991.builder_tools.client.items;


import com.piron1991.builder_tools.handler.ConfigHandler;
import com.piron1991.builder_tools.utilities.BlockPlacingHelper;
import com.piron1991.builder_tools.utilities.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import java.util.HashMap;



public class WoodenHand extends ItemBase {

    public final Minecraft mc;
    public WoodenHand() {
        super();
        this.setUnlocalizedName("WoodenHand");
        this.mc = Minecraft.getMinecraft();
    }


    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int face, float hit_x, float hit_y, float hit_z) {
        if (!world.isRemote ){
            boolean teCheck=world.getBlock(x,y,z).hasTileEntity(world.getBlockMetadata(x,y,z));
            if(!player.isSneaking() && !teCheck) {
                //save to nbt clicked block name and its meta
               setCLickedBlock(itemstack, world, x, y, z);

            } else if (player.isSneaking()) {
                //get saved block name and meta
                HashMap map = getClickedBlock(itemstack, world, x, y, z);


                Block chosenBlock = Block.getBlockFromName((String) map.get(getChosenBlockNameString()));
                int meta;
                if (map.get(getChosenBlockMetaString())==null){
                    meta=0;
                }else{
                    meta = (Integer) map.get(getChosenBlockMetaString());
                }
                LogHelper.info(chosenBlock+":"+meta);
                if (chosenBlock != null) {
                    //array for easy block placing checks and use
                    int[] tempCord={x,y,z};
                    tempCord= BlockPlacingHelper.sideChecker(face, tempCord);

                    //get block that player clicked
                    Block clickedBlock = world.getBlock(x, y, z);
                    //check if should place on x or z, for top and bottom face use only
                    int xzCheck= BlockPlacingHelper.drawAxisChecker(Math.abs(player.rotationYaw) % 360);

                    //set sizing for block placing
                    int min_i= BlockPlacingHelper.getMinI(getPlacingSize());
                    int max_i= BlockPlacingHelper.getMaxI(getPlacingSize());
                    int min_j=1;
                    int max_j=1;
                    LogHelper.info(getPlacingSize() + "   " + min_i + "     " + max_i);
                    switch (face) {
                        case 0: {
                            if (xzCheck == 0 || xzCheck == 2) {
                                for (int j =min_j;j<=max_j;j++){
                                    LogHelper.info(tempCord[1]);
                                    for (int i = min_i; i <= max_i; i++) {
                                        tempCord[0]=tempCord[0]-i;
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord) && checkInventory(player,chosenBlock)){
                                            consumeItem(player,chosenBlock);
                                            world.setBlock(tempCord[0],tempCord[1],tempCord[2], chosenBlock, meta, 3);
                                        }
                                        tempCord[0]=tempCord[0]+i;
                                    }
                                    tempCord[1]=tempCord[1]-1;

                                }
                            } else if (xzCheck == 1 || xzCheck == 3) {
                                for (int j =min_j;j<=max_j;j++) {
                                    for (int i = min_i; i <= max_i; i++) {
                                        tempCord[2] = tempCord[2] - i;
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            world.setBlock(tempCord[0], tempCord[1], tempCord[2], chosenBlock, meta, 3);
                                        }
                                        tempCord[2] = tempCord[2] + i;
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
                                        tempCord[0] = tempCord[0] - i;
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            world.setBlock(tempCord[0], tempCord[1], tempCord[2], chosenBlock, meta, 3);
                                        }
                                        tempCord[0] = tempCord[0] + i;
                                    }
                                    tempCord[1] = tempCord[1] + 1;
                                }

                            } else if (xzCheck == 1 || xzCheck == 3) {
                                for (int j = min_j; j <= max_j; j++) {
                                    for (int i = min_i; i <= max_i; i++) {
                                        tempCord[2] = tempCord[2] - i;
                                        if (checkCollisions(world, clickedBlock, x, y, z, tempCord) && checkInventory(player,chosenBlock)) {
                                            consumeItem(player,chosenBlock);
                                            world.setBlock(tempCord[0], tempCord[1], tempCord[2], chosenBlock, meta, 3);
                                        }
                                        tempCord[2] = tempCord[2] + i;
                                    }
                                    tempCord[1] = tempCord[1] + 1;
                                }
                            }
                            break;
                        }

                        case 2: {
                            for (int j = min_j; j <= max_j; j++) {
                                for (int i = min_i; i <= max_i; i++) {
                                    tempCord[0] = tempCord[0] - i;
                                    if (checkCollisions(world, clickedBlock, x, y, z, tempCord) && checkInventory(player,chosenBlock)) {
                                        consumeItem(player,chosenBlock);
                                        world.setBlock(tempCord[0], tempCord[1], tempCord[2], chosenBlock, meta, 3);
                                    }
                                    tempCord[0] = tempCord[0] + i;
                                }
                                tempCord[2] = tempCord[2] - 1;
                            }

                            break;

                        }
                        case 3: {
                            for (int j = min_j; j <= max_j; j++) {
                                for (int i = min_i; i <= max_i; i++) {
                                    tempCord[0] = tempCord[0] - i;
                                    if (checkCollisions(world, clickedBlock, x, y, z, tempCord) && checkInventory(player,chosenBlock)) {
                                        consumeItem(player,chosenBlock);
                                        world.setBlock(tempCord[0], tempCord[1], tempCord[2], chosenBlock, meta, 3);
                                    }
                                    tempCord[0] = tempCord[0] + i;
                                }
                                tempCord[2] = tempCord[2] + 1;
                            }

                            break;
                        }
                        case 4: {
                            for (int j = min_j; j <= max_j; j++) {
                                for (int i = min_i; i <= max_i; i++) {
                                    tempCord[2]=tempCord[2]-i;
                                    if (checkCollisions(world, clickedBlock, x, y, z, tempCord) && checkInventory(player,chosenBlock)){
                                        consumeItem(player,chosenBlock);
                                        world.setBlock(tempCord[0],tempCord[1],tempCord[2], chosenBlock, meta, 3);
                                    }
                                    tempCord[2]=tempCord[2]+i;
                                }
                                tempCord[0] = tempCord[0] - 1;
                            }

                            break;
                        }
                        case 5: {
                            for (int j = min_j; j <= max_j; j++) {

                                for (int i = min_i; i <= max_i; i++) {
                                    tempCord[2]=tempCord[2]-i;
                                    if (checkCollisions(world, clickedBlock, x, y, z, tempCord) && checkInventory(player,chosenBlock)){
                                        consumeItem(player,chosenBlock);
                                        world.setBlock(tempCord[0],tempCord[1],tempCord[2], chosenBlock, meta, 3);
                                    }
                                    tempCord[2]=tempCord[2]+i;
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

    @Override
    public boolean onItemLeftClick(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int face, float hit_x, float hit_y, float hit_z) {

        return true;
    }


    public int getPlacingSize(){return ConfigHandler.woodenToolSize;}
}