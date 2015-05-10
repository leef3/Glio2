package studios.redleef.glio;

import java.util.ArrayList;

/**
 * Created by Fred Lee on 5/8/2015.
 */
public class RecipeObject {

    private String name;
    private ArrayList<IngredientObject> recipeList;

    //Constructor 1
    public RecipeObject(String newName)
    {
        name = newName;
    }
    //Constructor 2
    public RecipeObject(String newName, ArrayList<IngredientObject> existingList)
    {
        name = newName;
        recipeList = existingList;
    }

    //Getter Methods
    public String getName() {return name;}
    public ArrayList<IngredientObject> getList() {return recipeList;}

    public void addIngredient(IngredientObject toAdd)
    {
        recipeList.add(toAdd);
    }
}
