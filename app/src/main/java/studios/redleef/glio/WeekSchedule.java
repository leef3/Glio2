package studios.redleef.glio;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//GSON Serializable Data
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WeekSchedule extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@liunk #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static Context context;
    private final static String SCALE_SAVE_NAME = "MASTER_SCALE_DATA";
    private final static String WEEKLY_MEAL_SAVE_NAME = "TEMP_WEEKLY_MEAL_DATA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_schedule_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        context = this;
        loadData();
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.actionbar);


    }

    public void loadData()
    {
        //Get the Master List of Scales
        SharedPreferences settings = context.getSharedPreferences("pref", 0);
        String objectData = settings.getString(SCALE_SAVE_NAME, "");
        if (!objectData.equals(""))
        {
            //List already exists meaning we have already created it before... Do nothing
        }
        else
        {
            //We want to populate the list of scales for the first time (This is a one time costly procedure)

            //Everything liquid measured in base unit of Fl. Ounce -- Will be normalized in IngredientObj
            ScaleObject newScale1 = new ScaleObject("Teaspoon", 0.16666667);
            ScaleObject newScale2 = new ScaleObject("Tablespoon", 0.5);
            ScaleObject newScale3 = new ScaleObject("Fl. Ounce", 1);
            ScaleObject newScale4 = new ScaleObject("Cup", 8);
            ScaleObject newScale5 = new ScaleObject("Pint", 16);
            ScaleObject newScale6 = new ScaleObject("Quart", 32);
            ScaleObject newScale7 = new ScaleObject("Gallon", 128);
            //TODO: Maybe add Liquid/Solid to the Scale Object so that combining them will be easier - No Confusion

            ArrayList<ScaleObject> tempScales = new ArrayList<ScaleObject>();
            tempScales.add(newScale1);
            tempScales.add(newScale2);
            tempScales.add(newScale3);
            tempScales.add(newScale4);
            tempScales.add(newScale5);
            tempScales.add(newScale6);
            tempScales.add(newScale7);

            //Base Unit for solids is a Gram
            ScaleObject newScale8 = new ScaleObject("g", 1);
            ScaleObject newScale9 = new ScaleObject("Kg", 1000);
            ScaleObject newScale10 = new ScaleObject("Mg", 0.001);
            ScaleObject newScale11 = new ScaleObject("Lb", 453.592);

            tempScales.add(newScale8);
            tempScales.add(newScale9);
            tempScales.add(newScale10);
            tempScales.add(newScale11);

            saveScales(tempScales);
        }
        //=======================FOR WEEKLY MEALS=================================
    }
    public void saveScales(ArrayList<ScaleObject> toAdd)
    {
        SharedPreferences.Editor settings = context.getSharedPreferences("pref",0).edit();
        String data = new Gson().toJson(toAdd);
        System.out.println("Data!: " + data);
        settings.putString(SCALE_SAVE_NAME, data);
        settings.commit();
    }
    public void saveIngredients(ArrayList<IngredientObject> toAdd)
    {

    }
    public void saveRecipes(ArrayList<RecipeObject> toAdd)
    {
        //TODO: THIS IS A PURCHASE OPTION FOR RECIPE BOOK ADDONS - ASIAN / AMERICAN / DIET
        //Just add to the existing Recipe List
    }

    public void saveWeeklyMeals(ArrayList<MealObject> toAdd)
    {
        SharedPreferences.Editor settings = context.getSharedPreferences("pref",0).edit();
        String data = new Gson().toJson(toAdd);
        System.out.println("Data!: " + data);
        settings.putString(WEEKLY_MEAL_SAVE_NAME, data);
        settings.commit();
    }


    @Override protected void onPause(){
        super.onPause();

        /*
        SharedPreferences.Editor settings = this.getPreferences(MODE_PRIVATE).edit();
        String data = new Gson().toJson(employeeList);
        System.out.println("Data!: " + data);
        settings.putString(EMPLOYEE_SAVE_NAME, data);
        settings.commit();
        */
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Home Selected
        if(position == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new WeekListFragment())
                    .commit();
            setTitle("Week Schedule");
        }
        else if(position == 1)
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new RecipeListFragment())
                    .commit();
            setTitle("Recipe List");
        }

        //Shopping Ingredients Selected
        else if(position == 2)
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new IngredientListFragment())
                    .commit();
            setTitle("Grocery List");
        }
        //Setting Selected
        else if(position == 3)
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new SettingsFragment())
                    .commit();
            setTitle("Settings");
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.week_schedule, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int layoutNum = R.layout.fragment_week_schedule;

            View rootView = inflater.inflate(layoutNum, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((WeekSchedule) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
