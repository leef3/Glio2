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
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by Fred Lee on 5/10/2015.
 */
public class WeekListFragment extends ListFragment{

    private static WeekListAdapter mAdapter;
    private static Context context;

    private static ArrayList<DayObject> days;

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = this.getActivity();
        // Initially there is no data
        setEmptyText("No Data Here");

        //Populate the list with test days
        days = new ArrayList<DayObject>();
        loadData();

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new WeekListAdapter(context, days);

        setListAdapter(mAdapter);
    }

    private static void loadData()
    {

        DayObject toAdd = new DayObject("Monday", R.drawable.mondaybg);
        days.add(toAdd);

        toAdd = new DayObject("Tuesday", R.drawable.tuesdaybg);
        days.add(toAdd);

        toAdd = new DayObject("Wednesday", R.drawable.wednesdaybg);
        days.add(toAdd);

        toAdd = new DayObject("Thursday", R.drawable.thursdaybg);
        days.add(toAdd);

        toAdd = new DayObject("Friday", R.drawable.fridaybg);
        days.add(toAdd);

        toAdd = new DayObject("Saturday", R.drawable.saturdaybg);
        days.add(toAdd);

        toAdd = new DayObject("Sunday", R.drawable.sundaybg);
        days.add(toAdd);
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
        RelativeLayout mLinearLayout = (RelativeLayout) inflater.inflate(
                R.layout.fragment_week_schedule, container, false);
        parent.addView(mLinearLayout, lvIndex, lv.getLayoutParams());

        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.
        DayObject selected = days.get(position);

        //
        //Toast.makeText(getActivity(), selected.getName(), Toast.LENGTH_SHORT).show();
    }

}