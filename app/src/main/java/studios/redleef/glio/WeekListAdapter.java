package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/10/2015.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.app.DialogFragment;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//Class to handle the population of the List of Days
public class WeekListAdapter extends BaseAdapter
{
    Context context;
    protected ArrayList<DayObject> dayList;
    LayoutInflater inflater;
    FragmentManager fragManager;

    //Constructor
    public WeekListAdapter(Context context, ArrayList<DayObject> dayList) {
        this.dayList = dayList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        fragManager = ((WeekSchedule) context).getSupportFragmentManager();
    }

    //Returns count,
    public int getCount() {
        return dayList.size();
    }
    public DayObject getItem(int position) {
        return dayList.get(position);
    }
    public long getItemId(int position)
    {
        return position;
    }

    //Method to add the information from each day into a row in the listview.
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = this.inflater.inflate(R.layout.day_list_item, parent, false);

            //Find UI elements for Day Name / Meal Name / Background Image
            holder.image = (ImageView) convertView.findViewById(R.id.day_item_image);

            holder.title = (TextView) convertView.findViewById(R.id.day_item_title);

            holder.breakfast = (TextView) convertView.findViewById(R.id.day_item_breakfast);


            holder.lunch = (TextView) convertView.findViewById(R.id.day_item_lunch);


            holder.dinner = (TextView) convertView.findViewById(R.id.day_item_dinner);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Get the current day object
        DayObject dayItem = dayList.get(position);

        holder.dinner.setTag(position);
        holder.lunch.setTag(position);
        holder.breakfast.setTag(position);

        //Set the contents of the UI elements
        holder.image.setImageResource(dayItem.getImage());

        holder.title.setText(dayItem.getName());

        //Set the title of the dialog box
        holder.breakfast.setText("Breakfast: Tap to set");
        holder.breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempPosition = (Integer) v.getTag();
                Toast.makeText(context, dayList.get(tempPosition).getName() + " Breakfast", Toast.LENGTH_SHORT).show();
                //chooseMealDialog(tempPosition, dayList.get(tempPosition).getName() + " Breakfast");
                showDialog(tempPosition, dayList.get(tempPosition).getName() + " Breakfast");

            }
        });
        holder.lunch.setText("Lunch: Tap to set");
        holder.lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempPosition = (Integer) v.getTag();
                Toast.makeText(context, tempPosition + " Lunch", Toast.LENGTH_SHORT).show();
                showDialog(tempPosition, dayList.get(tempPosition).getName() + " Lunch");
            }
        });
        holder.dinner.setText("Dinner: Tap to set");
        holder.dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempPosition = (Integer) v.getTag();
                Toast.makeText(context, dayList.get(tempPosition).getName() + " Dinner", Toast.LENGTH_SHORT).show();
                showDialog(tempPosition, dayList.get(tempPosition).getName() + " Dinner");
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        TextView title;

        //Meals
        TextView breakfast;
        TextView lunch;
        TextView dinner;
    }

    private void showDialog(int position, String title)
    {
        //FragmentTransaction ft = fragManager.beginTransaction();
        MealDialogFragment newDialog = MealDialogFragment.newInstance(position, title);
        newDialog.show(fragManager, "new_meal_dialog");
    }

    //=======================GRAVEYARD BEYOND THIS PT==============================================
    /*
    private void chooseMealDialog(int position, String title)
    {
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.meal_add_dialog, null);
        //AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptsView);

        final TextView mealTitle = (TextView)promptsView.findViewById(R.id.mealDialogTitle);
        mealTitle.setText(title);

        //Set up the spinners
        final Spinner ingredientSpinner = (Spinner) promptsView.findViewById(R.id.mealDialogRecipeSpinner);
        ArrayAdapter<CharSequence> ingredientAdapter = ArrayAdapter.createFromResource(context, R.array.ingredient_array, android.R.layout.simple_spinner_item);
        ingredientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientSpinner.setAdapter(ingredientAdapter);

        final Button addNewSpinner = (Button) promptsView.findViewById(R.id.mealDialogAddNewSpinner);


        //final ListView mealDialogListView = (ListView) promptsView.findViewById(R.id.addMealDialogListView);


        //Pass in the promptsView so that we can find our linear layout later
        addNewSpinner.setTag(promptsView);

        addNewSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int tempPosition = (Integer) v.getTag();
                //Toast.makeText(context, "Add New Recipe Spinner -- Also Position =  " + tempPosition, Toast.LENGTH_SHORT).show();

                //Get the total dialog view from the tag
                View dialogView = (View) v.getTag();
                //Increment counter

                //Find the spinner layout
                LinearLayout spinnerLayout = (LinearLayout) dialogView.findViewById(R.id.mealDialogRecipeSpinnerLinearLayout);

                //Count the children of the layout -- Make sure we don't exceed say...10 meals (who is gonna eat that much anyways?!)
                int numMeals = spinnerLayout.getChildCount();

                if (numMeals < 10)
                {
                    //Create the new spinner
                    Spinner newSpinner = new Spinner(context);
                    ArrayAdapter<CharSequence> ingredientAdapter = ArrayAdapter.createFromResource(context, R.array.ingredient_array, android.R.layout.simple_spinner_item);
                    ingredientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    newSpinner.setAdapter(ingredientAdapter);

                    newSpinner.setBackgroundColor(Color.parseColor("#C5E1A5"));
                    newSpinner.setPadding(0, 10, 0, 10);
                    //Add the view to the spinner layout
                    spinnerLayout.addView(newSpinner);
                }
                else
                {
                    addNewSpinner.setEnabled(false);
                    Toast.makeText(context, "Sorry, Meal limit is 10", Toast.LENGTH_SHORT).show();
                }


            }
        });

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //RIGHT NOW ONLY ADDS THE FIRST SPINNER VALUE, VERY SOON LISTVIEW CYCLE WILL COLLECT ALL RECIPES
                int recipeIndex = ingredientSpinner.getSelectedItemPosition();
                
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
    */


}