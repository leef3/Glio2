package studios.redleef.glio;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Fred Lee on 5/10/2015.
 */
public class RecipeListFragment extends ListFragment {

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

        //
        Toast.makeText(getActivity(), selected.getName(), Toast.LENGTH_SHORT).show();
    }

}