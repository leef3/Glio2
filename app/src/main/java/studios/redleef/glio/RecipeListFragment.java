package studios.redleef.glio;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
public class RecipeListFragment extends ListFragment {

    private static RecipeListAdapter mAdapter;
    private static Context context;

    private final static String RECIPE_SAVE_NAME = "MASTER_RECIPE_DATA";
    private static ArrayList<RecipeObject> recipes;

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = this.getActivity();
        // Initially there is no data
        setEmptyText("No Data Here");

        //Populate the list with test recipes
        recipes = new ArrayList<RecipeObject>();
        loadData();

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new RecipeListAdapter(context, recipes);

        setListAdapter(mAdapter);
    }

    private static void loadData()
    {
        //Get the Master List of Recipes
        SharedPreferences settings = context.getSharedPreferences("pref",0);
        String objectData = settings.getString(RECIPE_SAVE_NAME, "");
        if (!objectData.equals("")) {
            System.out.println("Object Data: " + objectData);
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<RecipeObject>>() {
            }.getType();
            JsonArray jArray = new JsonParser().parse(objectData).getAsJsonArray();
            for (JsonElement e : jArray) {
                RecipeObject c = gson.fromJson(e, RecipeObject.class);
                recipes.add(c);
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
                R.layout.fragment_recipe_list, container, false);
        parent.addView(mLinearLayout, lvIndex, lv.getLayoutParams());

        final Button addButton = (Button)mLinearLayout.findViewById(R.id.add_recipe_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment newRecipeFrag = new NewRecipeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, newRecipeFrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.
        RecipeObject selected = recipes.get(position);
        recipes.remove(position);
        mAdapter.notifyDataSetChanged();
        saveData();
        Toast.makeText(getActivity(), selected.getName(), Toast.LENGTH_SHORT).show();
    }

    private static void saveData()
    {
        SharedPreferences.Editor settings = context.getSharedPreferences("pref",0).edit();
        String data = new Gson().toJson(recipes);
        System.out.println("Data!: " + data);
        settings.putString(RECIPE_SAVE_NAME, data);
        settings.commit();
    }
}
