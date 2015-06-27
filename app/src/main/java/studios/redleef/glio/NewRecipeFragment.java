package studios.redleef.glio;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class NewRecipeFragment extends Fragment {

    private static Context context;
    private static IngredientListAdapter mAdapter;

    private static ListView newIngredientList;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_new_recipe, container, false);

        // Inflate the layout for this fragment

        newIngredientList = (ListView)layout.findViewById(R.id.newIngredientListView);
        ingredients = new ArrayList<IngredientObject>();
        mAdapter = new IngredientListAdapter(getActivity(), ingredients);
        newIngredientList.setAdapter(mAdapter);


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

        final Button saveButton = (Button)layout.findViewById(R.id.saveNewRecipeButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, "SAVED", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
