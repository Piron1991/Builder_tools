package com.piron1991.builder_tools.init;



import com.piron1991.builder_tools.handler.ConfigHandler;

import com.piron1991.builder_tools.utilities.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import com.piron1991.builder_tools.utilities.RecipeHandlerHelper;

public class InitRecipe {

    private static final String[] WOODENHANDRECIPES=ConfigHandler.woodenToolRecipe;
    private static final String[] STONEHANDRECIPES=ConfigHandler.stoneToolRecipe;

    private static String finishedString;


    public static void init() {
        InitWoodRecipe();
        InitStoneRecipe();
    }


    private static void InitWoodRecipe(){
        //Initialize names for crafting recipe
        RecipeHandlerHelper obj1 = new RecipeHandlerHelper(WOODENHANDRECIPES[0]);
        RecipeHandlerHelper obj2 = new RecipeHandlerHelper(WOODENHANDRECIPES[1]);
        RecipeHandlerHelper obj3 = new RecipeHandlerHelper(WOODENHANDRECIPES[2]);
        RecipeHandlerHelper obj4 = new RecipeHandlerHelper(WOODENHANDRECIPES[3]);
        RecipeHandlerHelper obj5 = new RecipeHandlerHelper(WOODENHANDRECIPES[4]);
        RecipeHandlerHelper obj6 = new RecipeHandlerHelper(WOODENHANDRECIPES[5]);
        RecipeHandlerHelper obj7 = new RecipeHandlerHelper(WOODENHANDRECIPES[6]);
        RecipeHandlerHelper obj8 = new RecipeHandlerHelper(WOODENHANDRECIPES[7]);
        RecipeHandlerHelper obj9 = new RecipeHandlerHelper(WOODENHANDRECIPES[8]);

        //set recipe string and gets all itemStacks for later registering
        String string1=getRecipeString(obj1,obj2,obj3,1);
        String string2=getRecipeString(obj4,obj5,obj6,2);
        String string3=getRecipeString(obj7,obj8,obj9,3);
        ItemStack[] recipe={obj1.getItemStack(),obj2.getItemStack(),obj3.getItemStack(),obj4.getItemStack(),obj5.getItemStack(),obj6.getItemStack(),obj7.getItemStack(),obj8.getItemStack(),obj9.getItemStack(),};

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.woodhand), new Object[]
                {string1, string2, string3,
                        'A', recipe[0],
                        'B', recipe[1],
                        'C', recipe[2],
                        'D', recipe[3],
                        'E', recipe[4],
                        'F', recipe[5],
                        'G', recipe[6],
                        'H', recipe[7],
                        'I', recipe[8]}));

    }


    private static void InitStoneRecipe(){
        //Initialize names for crafting recipe
        RecipeHandlerHelper obj1 = new RecipeHandlerHelper(STONEHANDRECIPES[0]);
        RecipeHandlerHelper obj2 = new RecipeHandlerHelper(STONEHANDRECIPES[1]);
        RecipeHandlerHelper obj3 = new RecipeHandlerHelper(STONEHANDRECIPES[2]);
        RecipeHandlerHelper obj4 = new RecipeHandlerHelper(STONEHANDRECIPES[3]);
        RecipeHandlerHelper obj5 = new RecipeHandlerHelper(STONEHANDRECIPES[4]);
        RecipeHandlerHelper obj6 = new RecipeHandlerHelper(STONEHANDRECIPES[5]);
        RecipeHandlerHelper obj7 = new RecipeHandlerHelper(STONEHANDRECIPES[6]);
        RecipeHandlerHelper obj8 = new RecipeHandlerHelper(STONEHANDRECIPES[7]);
        RecipeHandlerHelper obj9 = new RecipeHandlerHelper(STONEHANDRECIPES[8]);

        //set recipe string and gets all itemStacks for later registering
        String string1=getRecipeString(obj1,obj2,obj3,1);
        String string2=getRecipeString(obj4,obj5,obj6,2);
        String string3=getRecipeString(obj7,obj8,obj9,3);
        ItemStack[] recipe={obj1.getItemStack(),obj2.getItemStack(),obj3.getItemStack(),obj4.getItemStack(),obj5.getItemStack(),obj6.getItemStack(),obj7.getItemStack(),obj8.getItemStack(),obj9.getItemStack(),};
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InitItems.stonehand), new Object[]
                {string1, string2, string3,
                        'A',  recipe[0],
                        'B', recipe[1],
                        'C', recipe[2],
                        'D', recipe[3],
                        'E', recipe[4],
                        'F', recipe[5],
                        'G', recipe[6],
                        'H', recipe[7],
                        'I', recipe[8]}));
    }


 private static String getRecipeString(RecipeHandlerHelper mat1,RecipeHandlerHelper mat2,RecipeHandlerHelper mat3, int stringNumber){
     finishedString="xyz";
     String mat1Name=mat1.getName();
     String mat2Name=mat2.getName();
     String mat3Name=mat3.getName();

     if (mat1Name.equals(" ")) { finishedString=finishedString.replace("x"," ");
     }else if(stringNumber==1) { finishedString=finishedString.replace("x","A");
     }else if(stringNumber==2) { finishedString=finishedString.replace("x","D");
     }else if(stringNumber==3) { finishedString=finishedString.replace("x","G");
     }

     if (mat2Name.equals(" ")) { finishedString=finishedString.replace("y"," ");
     }else if(stringNumber==1) { finishedString=finishedString.replace("y","B");
     }else if(stringNumber==2) { finishedString=finishedString.replace("y","E");
     }else if(stringNumber==3) { finishedString=finishedString.replace("y","H");
     }

     if (mat3Name.equals(" ")) { finishedString=finishedString.replace("z"," ");
     }else if(stringNumber==1) { finishedString=finishedString.replace("z","C");
     }else if(stringNumber==2) { finishedString=finishedString.replace("z","F");
     }else if(stringNumber==3) { finishedString=finishedString.replace("z","I");
     }
     return finishedString;
 }



}


