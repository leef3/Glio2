package studios.redleef.glio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by Fred Lee on 5/10/2015.
 */
public class IngredientListFragment extends ListFragment
{

    private static IngredientListAdapter mAdapter;
    private static Context context;

    //List of ingredients compiled from this weeks recipe / meals chosen
    private static ArrayList<IngredientObject> tempIngredients;

    //List of recipes from this weeks meals
    private static ArrayList<RecipeObject> tempRecipes;

    private static ArrayList<IngredientObject> singleRecipeIngredientList;

    private final static String RECIPE_SAVE_NAME = "MASTER_RECIPE_DATA";

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = this.getActivity();
        // Initially there is no data
        setEmptyText("No Data Here");

        //Populate the list with test ingredients
        tempIngredients = new ArrayList<IngredientObject>();
        tempRecipes = new ArrayList<RecipeObject>();

        loadData();

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new IngredientListAdapter(context, tempIngredients, true);

        setListAdapter(mAdapter);
    }

    private static void loadData()
    {
        //Get the Master List of Recipes
        SharedPreferences settings = context.getSharedPreferences("pref", 0);
        String objectData = settings.getString(RECIPE_SAVE_NAME, "");
        if (!objectData.equals("")) {
            System.out.println("Object Data: " + objectData);
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<RecipeObject>>() {
            }.getType();
            JsonArray jArray = new JsonParser().parse(objectData).getAsJsonArray();
            for (JsonElement e : jArray) {
                RecipeObject c = gson.fromJson(e, RecipeObject.class);
                tempRecipes.add(c);
            }
        }
        //Add all the recipe ingredients to temporary ingredient shopping list

        for(int x = 0; x < tempRecipes.size(); x++)
        {
            RecipeObject tempSingleRecipe = tempRecipes.get(x);
            singleRecipeIngredientList = new ArrayList<IngredientObject>(tempSingleRecipe.getList());

            for(int y = 0; y < singleRecipeIngredientList.size(); y++)
            {
                boolean found = false;
                for(int z = 0; z < tempIngredients.size(); z++)
                {
                    if(singleRecipeIngredientList.get(y).getName().equals(tempIngredients.get(z).getName()))
                    {
                        //TODO: RIGHT NOW VIEW DOES NOT DISPLAY CHANGE IN NORMALIZEDAMOUNT
                        //CONFIGURE SO THAT IT CONVERTS TO ANY UNIT YOU WANT BUT FOR NOW MOCK IT
                        //BY ADDING A NEW AMOUNT WITH BASE SCALE AS SCALE AND NORMALIZED AS AMOUNT
                        ScaleObject tempScale = new ScaleObject("Base Unit", 1);
                        tempIngredients.get(z).addAmount(singleRecipeIngredientList.get(y).getNormalizedAmount(), tempScale);
                        found =true;
                    }
                }
                //Just to be safe........
                if(!found)
                {
                    IngredientObject temp = new IngredientObject(singleRecipeIngredientList.get(y).getName());
                    //Base Unit New Scale Object --- But in the future just get this from our existing list
                    ScaleObject tempScale = new ScaleObject("Base Unit", 1);
                    temp.addAmount(singleRecipeIngredientList.get(y).getNormalizedAmount(), tempScale);
                    tempIngredients.add(temp);
                }

                /*

                for(int z = 0; z < tempIngredients.size(); z++)
                {
                    if(tempIngredients.get(z).getName().equals(temp.getName()))
                    {
                        //Item already exists, just combine normalizedAmounts
                        tempIngredients.get(z).addNormalizedAmount(temp.getAmount() * temp.getScale().getMultiplier());

                        //TODO: Maybe use a break here? Does it break out of IF or FOR loop?
                    }
                    //Just to be safe........
                    else if(!exists)
                    {
                        //Base Unit New Scale Object --- But in the future just get this from our existing list
                        ScaleObject tempScale = new ScaleObject("Base Unit", 1);
                        temp.addAmount(singleRecipeIngredientList.get(y).getNormalizedAmount(), tempScale);
                        tempIngredients.add(temp);
                    }
                }
                */
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container,
                savedInstanceState);
        ListView lv = (ListView) layout.findViewById(android.R.id.list);
        ViewGroup parent = (ViewGroup) lv.getParent();

        // Remove ListView and add CustomView  in its place
        int lvIndex = parent.indexOfChild(lv);
        parent.removeViewAt(lvIndex);
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(
                R.layout.fragment_shopping_list, container, false);
        parent.addView(mLinearLayout, lvIndex, lv.getLayoutParams());

        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.
        IngredientObject selected = tempIngredients.get(position);

        Toast.makeText(getActivity(), selected.getName(), Toast.LENGTH_SHORT).show();
    }

}