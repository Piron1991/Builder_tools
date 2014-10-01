package com.piron1991.builder_tools.utilities;

import net.minecraft.block.*;
import net.minecraft.world.World;

public class BlockRotationHelper {


    //NOOO! Dont look at me! im awefull!


    public static int rotateMetaToFace(World world, Block block, int x,int y, int z,int face,int meta, float[] hits, int axis){
        if (block instanceof BlockLadder) {
            return block.onBlockPlaced(world, x, y, z, face, hits[0], hits[1], hits[2], meta);
        }
        if(block instanceof BlockSlab){
            meta=meta>=8 ? meta-8:meta;
            if (face !=0&& face !=1) {
               return ((double) hits[1] <= 0.5D) ? meta : meta | 8;
            }
            return meta;
        }
        if(block instanceof BlockStairs){
            //TODO BUG HUNT for stairs not always rotating properly(mby fixed)
            switch(face){

                case 0:{
                    if (axis==0)return 6;
                    if (axis==1)return 5;
                    if (axis==2)return 7;
                    if (axis==3)return 4;
                    break;
                }
                case 1:{
                    if (axis==0) return 2;
                    if (axis==1) return 1;
                    if (axis==2) return 3;
                    if (axis==3) return 0;
                    break;
                }
                case 2:{
                    return (((double)hits[1] <= 0.5D) ? 2 : 6);
                }
                case 3:{
                    return (((double)hits[1] <= 0.5D) ? 3 : 7);
                }
                case 4:{
                    return (((double)hits[1] <= 0.5D) ? 0 : 4);
                }
                case 5:{
                    return (((double)hits[1] <= 0.5D) ? 1 : 5);
                }
            }
        }
        if(block instanceof BlockLog){
            if (face==0||face==1){return meta%4;}
            if (face==2||face==3){return meta%4+8;}
            if (face==4||face==5){return meta%4+4;}
        }
        //TODO test rotation on all blocks below
        if(block instanceof BlockQuartz || block instanceof BlockRotatedPillar){
            return block.onBlockPlaced(world, x, y, z, face, hits[0], hits[1], hits[2], meta);
        }
        if (block instanceof BlockPumpkin){
            return axis+1 & 3;
            //int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
            //p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, l, 2);
        }
        if (block instanceof BlockEndPortalFrame){
            return ((axis & 3) + 2) % 4;
            //int l = ((MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
           // p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, l, 2);
        }
        if (block instanceof BlockTripWireHook){
            return block.onBlockPlaced(world, x, y, z, face, hits[0], hits[1], hits[2], meta);
        }
        if (block instanceof BlockRailBase){
            if (axis==0 ||axis ==2) return 0x0;
            else return 0x1;
           // 0x0: flat track going North-South
           // 0x1: flat track going West-East
        }
        if (block instanceof BlockVine){
            return block.onBlockPlaced(world, x, y, z, face, hits[0], hits[1], hits[2], meta);
        }
        if(block instanceof BlockPistonBase){
            int temp=axis & 3;
            return temp == 0 ? 2 : (temp == 1 ? 5 : (temp == 2 ? 3 : (temp == 3 ? 4 : 0)));
        }
        return meta;
    }

}
