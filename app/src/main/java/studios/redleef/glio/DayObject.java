package studios.redleef.glio;

import java.util.ArrayList;

/**
 * Created by Fred Lee on 5/8/2015.
 */
public class DayObject {

    private String name;
    private int image;

    //Meals already contain names for breakfast / lunch / dfinner
    private ArrayList<MealObject> mealList;

    public DayObject(String newName, int newImage)
    {
        name = newName;
        image = newImage;
        mealList = new ArrayList<MealObject>();
    }
    //Getter Methods
    public String getName() {return name;}

    public int getImage() {return image;}

    public ArrayList<MealObject> getList() {return mealList;}

    //Return the Recipe List of a Meal object found in the Day's Meal List (Based on Breakfast lunch dinner)
    public MealObject getMeal(String toFind)
    {
        for(int x = 0; x < mealList.size(); x++)
        {
            if(mealList.get(x).equals(toFind))
            {
                return mealList.get(x);
            }
        }
        return null;
    }

}
