package studios.redleef.glio;

import java.util.ArrayList;

/**
 * Created by Fred Lee on 5/8/2015.
 */
public class RecipeObject {

    private  String name;
    private ArrayList<IngredientObject> ingredientList;

    //Constructor 1
    public RecipeObject(String newName)
    {
        name = newName;
        ingredientList = new ArrayList<IngredientObject>();
    }
    //Constructor 2
    public RecipeObject(String newName, ArrayList<IngredientObject> ingredientListIn)
    {
        name = newName;

        if(ingredientListIn != null) {
            this.ingredientList = ingredientListIn;
        }
    }

    //Getter Methods
    public String getName() {return name;}
    public ArrayList<IngredientObject> getList() {
        if(ingredientList != null) {
            return ingredientList;
        }
        else
        {
            return ingredientList = new ArrayList<IngredientObject>();
        }
    }

    public void addIngredient(IngredientObject toAdd)
    {
        ingredientList.add(toAdd);
    }

    public void removeIngredient(IngredientObject toRemove)
    {
        //NOT NEEDED FOR NOW -- LONG CLICK JUST REMOVES IT FROM ARRAYLIST IN DIALOG BEFORE SAVE
        for(int x = 0; x < ingredientList.size(); x++)
        {
            if(ingredientList.get(x).getName() == toRemove.getName())
            {

            }
        }
    }
}
