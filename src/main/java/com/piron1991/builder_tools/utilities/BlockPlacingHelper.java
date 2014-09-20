package com.piron1991.builder_tools.utilities;


public class BlockPlacingHelper {

    protected static boolean sideAxis=false;

    public static short drawAxisChecker(float yaw) {

        //yaw to block center: 0(325:45) / face: 2, should render from +x,-x
        //yaw to block center: 90(45:135) / face: 5, should render from +z,-z
        //yaw to block center: 180(135:225) / face: 3, should render from -x,+x
        //yaw to block center: 270(225:325) / face: 4 should render from -z,+z
        if (yaw > 325 || yaw <= 45){
            return 0;
        }else if(yaw > 45 && yaw <= 135){
            return 1;
        }else if(yaw > 135 && yaw <= 225){
            return 2;
        }else if(yaw > 225 && yaw <= 325){
            return 3;
        }else return 4;

    }


    public static int[] sideChecker(int face, int[] cord) {
        switch (face) {
            case 0: {
                //pitch<0
                cord[1] = cord[1] - 1;
                return cord;
            }
            case 1: {
                //pitch>0
                cord[1] = cord[1] + 1;
                return cord;
            }
            case 2: {
                //yaw 325:45
                cord[2] = cord[2] - 1;
                return cord;
            }
            case 3: {
                //yaw 135:225
                cord[2] = cord[2] + 1;
                return cord;
            }
            case 4: {
                //yaw 225:315
                cord[0] = cord[0] - 1;
                return cord;
            }
            case 5: {
                //yaw 45:135
                cord[0] = cord[0] + 1;
                return cord;
            }
            default: {
                return cord;
            }
        }
    }

    public static int getMinI(int size){
        return (int)-Math.floor(size/2);
    }

    public static int getMaxI(int size){
        return (int)Math.floor(size/2);
    }
    public static int getMinJ(int size){
        return (int)-Math.floor(size/2);
    }
    public static int getMaxJ(int size){
        return (int)Math.floor(size/2);
    }

    public static boolean getSideAxis(){return sideAxis;}
    public static void setSideAxis(){sideAxis=!sideAxis;}

}




