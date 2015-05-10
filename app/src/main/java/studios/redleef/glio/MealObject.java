package studios.redleef.glio;

import java.util.ArrayList;

/**
 * Created by Fred Lee on 5/8/2015.
 */
public class MealObject {

    private String name;
    private ArrayList<RecipeObject> dishList;

    public MealObject(String newName)
    {
        name = newName;
        dishList = new ArrayList<RecipeObject>();
    }
    public MealObject(String newName, ArrayList<RecipeObject> existingDishList)
    {
        name = newName;
        dishList = existingDishList;
    }

    public String getName() {return name;}
    public ArrayList<RecipeObject> getList() {return dishList; }

    public void addRecipe(RecipeObject toAdd)
    {
        dishList.add(toAdd);
    }
}
