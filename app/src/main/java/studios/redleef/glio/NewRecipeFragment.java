package studios.redleef.glio;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//For Data Save
//GSON Serializable Data
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class NewRecipeFragment extends Fragment {

    private static Context context;
    private static IngredientListAdapter mAdapter;

    private final static String RECIPE_SAVE_NAME = "MASTER_RECIPE_DATA";

    private static ListView newIngredientList;


    //So that SAVE button adds this constructed recipeobject to the array
    private static RecipeObject toAdd;

    //We need the master recipe list to add the new recipe to
    private static ArrayList<RecipeObject> recipes;
    private static ArrayList<IngredientObject> ingredients;

    public NewRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = this.getActivity();
        // Initially there is no data
        //setEmptyText("Click on 'Add Ingredient' to add ingredients");

        //Populate the list with test recipes
        recipes = new ArrayList<RecipeObject>();
        loadData();
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
                recipes.add(c);
            }
        }
    }

    private static void saveData()
    {
        SharedPreferences.Editor settings = context.getSharedPreferences("pref",0).edit();
        String data = new Gson().toJson(recipes);
        System.out.println("Data!: " + data);
        settings.putString(RECIPE_SAVE_NAME, data);
        settings.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment - Return this later and call it as context
        View layout = inflater.inflate(R.layout.fragment_new_recipe, container, false);

        //Ingredient Listview initialization
        newIngredientList = (ListView)layout.findViewById(R.id.newIngredientListView);
        ingredients = new ArrayList<IngredientObject>();
        mAdapter = new IngredientListAdapter(getActivity(), ingredients);
        newIngredientList.setAdapter(mAdapter);

        //TODO Add OnLongClickListener for Deleting Ingredients

        //Find the NAME EditText for later use in constructing toAdd
        final EditText newName = (EditText)layout.findViewById(R.id.newRecipeNameEditText);

        //================================BIG ASS DIALOG CHUNK=================================
        final Button addButton = (Button)layout.findViewById(R.id.addIngredientButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(context);
                final View promptsView = li.inflate(R.layout.recipe_add_dialog, null);
                //AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(promptsView);


                //Set up the spinners
                final Spinner ingredientSpinner = (Spinner) promptsView.findViewById(R.id.ingredient_spinner);
                ArrayAdapter<CharSequence> ingredientAdapter = ArrayAdapter.createFromResource(context, R.array.ingredient_array, android.R.layout.simple_spinner_item);
                ingredientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ingredientSpinner.setAdapter(ingredientAdapter);

                final Spinner scaleSpinner = (Spinner) promptsView.findViewById(R.id.scale_spinner);
                ArrayAdapter<CharSequence> scaleAdapter = ArrayAdapter.createFromResource(context, R.array.scale_array, android.R.layout.simple_spinner_item);
                scaleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                scaleSpinner.setAdapter(scaleAdapter);


                //Set up the textview to reflect the seekbar amount
                final TextView amount;
                amount = (TextView)promptsView.findViewById(R.id.amount);

                //Set up the SeekBar Amount Listener
                final SeekBar amountBar = (SeekBar)promptsView.findViewById(R.id.amountSeekBar);
                amountBar.setProgress(0);
                amountBar.setMax(50);
                amountBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        //For some reason it crashes when not a string and cant use toString
                        amount.setText(progress + "");
                    }
                });

                // Set up the input
                //final EditText inputName = (EditText) promptsView.findViewById(R.id.recipe_dialog_name_input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String ingredientName = ingredientSpinner.getSelectedItem().toString();

                        IngredientObject ingredientToAdd = new IngredientObject(ingredientName);
                        //TODO Add Scale and Amount (Scale needs to be a scale object)

                        //Instead of creating a new scale we get it from master list (create for test)
                        ScaleObject ingredientScale = new ScaleObject("Quart", 2.2);
                        ingredientToAdd.addScale(ingredientScale);

                        ingredientToAdd.addAmount(Double.valueOf(amount.getText().toString()));
                        addNewIngredient(ingredientToAdd);
                    }
                });
                //Cancel if user selects cancel
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                //Show the dialog
                builder.show();
            }
        });
        //==============================BIG ASS DIALOG CHUNK END==================================

        final Button saveButton = (Button)layout.findViewById(R.id.saveNewRecipeButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Check if Name Form is null
                if(newName.getText().toString().equals(""))
                {
                    AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(context);
                    confirmBuilder.setMessage("You should name your recipe!");
                    // Set up the buttons
                    confirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    confirmBuilder.show();
                }
                //Ingredient List is Empty
                else if(ingredients.size() < 1)
                {
                    AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(context);
                    confirmBuilder.setMessage("A recipe needs ingredients!");
                    // Set up the buttons
                    confirmBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    confirmBuilder.show();
                }
                else
                {
                    //Create the new recipe object
                    toAdd = new RecipeObject(newName.getText().toString());
                    for(int x = 0; x < ingredients.size(); x++)
                    {
                        toAdd.addIngredient(ingredients.get(x));
                    }
                    recipes.add(toAdd);
                    saveData();

                    getFragmentManager().popBackStack();
                }
            }
        });

        //Programically press Back on 'CANCEL'
        final Button cancelButton = (Button)layout.findViewById(R.id.cancelNewRecipeButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //Return the view
        return layout;

    }

    public void addNewIngredient(IngredientObject toAdd)
    {
        ingredients.add(0, toAdd);
        mAdapter.notifyDataSetChanged();
    }
    public void removeNewIngredient(IngredientObject toAdd)
    {
        ingredients.remove(toAdd);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

}
