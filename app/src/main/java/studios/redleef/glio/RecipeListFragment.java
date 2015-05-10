package studios.redleef.glio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by Fred Lee on 5/10/2015.
 */
public class RecipeListFragment extends ListFragment{

    private static RecipeListAdapter mAdapter;
    private static Context context;

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
        for(int x = 0; x < 20; x++)
        {
            RecipeObject toAdd = new RecipeObject("Recipe Test #" + x , null);
            recipes.add(toAdd);
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

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.recipe_add_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(promptsView);

                // Set up the input
                final EditText inputName = (EditText) promptsView.findViewById(R.id.recipe_dialog_name_input);
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                        String userInputName = inputName.getText().toString();
                        //Set variables to null for now and replace them later if the toggle button is set
                        String addMonday = " ";
                        String addTuesday = " ";
                        String addWednesday = " ";
                        String addThursday = " ";
                        String addFriday = " ";
                        //Check the toggle buttons and set variables
                        if(mondayToggle.isChecked()) { addMonday = "M"; }
                        if(tuesdayToggle.isChecked()) { addTuesday = "T"; }
                        if(wednesdayToggle.isChecked()) { addWednesday = "W"; }
                        if(thursdayToggle.isChecked()) { addThursday = "R"; }
                        if(fridayToggle.isChecked()) { addFriday = "F"; }
                        //Create a new employee object using the variables taken from the dialog.
                        Employee toAdd = new Employee(userInputName, userInputAmount, addMonday, addTuesday, addWednesday, addThursday, addFriday);
                        addNewEmployee(toAdd);
                        */
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


        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.
        RecipeObject selected = recipes.get(position);

        //
        Toast.makeText(getActivity(), selected.getName(), Toast.LENGTH_SHORT).show();
    }

}