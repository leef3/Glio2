package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/10/2015.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//Class to handle the population of the List of Days
public class WeekListAdapter extends BaseAdapter
{
    Context context;
    protected ArrayList<DayObject> dayList;
    LayoutInflater inflater;

    //Constructor
    public WeekListAdapter(Context context, ArrayList<DayObject> dayList) {
        this.dayList = dayList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
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
            holder.breakfast.setTag(position);

            holder.lunch = (TextView) convertView.findViewById(R.id.day_item_lunch);
            holder.lunch.setTag(position);

            holder.dinner = (TextView) convertView.findViewById(R.id.day_item_dinner);
            holder.dinner.setTag(position);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Get the current day object
        DayObject dayItem = dayList.get(position);

        //Set the contents of the UI elements
        holder.image.setImageResource(dayItem.getImage());

        holder.title.setText(dayItem.getName());

        //Set null textviews if nothing is chosen yet
        /*
        if(dayItem.getMeal("Breakfast").equals(null))
        {
            holder.breakfast.setText("Breakfast: Click to set");
        }
        else
        {
            //Fill the textview up with list of recipes
            holder.breakfast.setText(dayItem.getList().get(0).getName());
        }
        if(dayItem.getMeal("Lunch") == (null))
        {
            holder.lunch.setText("Lunch: Click to set");
        }
        if(dayItem.getMeal("Dinner") == (null))
        {
            holder.dinner.setText("Dinner: Click to set");
        }
        */
        holder.breakfast.setText("Breakfast: Tap to set");
        holder.breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempPosition = (Integer) v.getTag();
                Toast.makeText(context, dayList.get(tempPosition).getName() + " Breakfast", Toast.LENGTH_SHORT).show();
            }
        });
        holder.lunch.setText("Lunch: Tap to set");
        holder.lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempPosition = (Integer) v.getTag();
                Toast.makeText(context, dayList.get(tempPosition).getName() + " Lunch", Toast.LENGTH_SHORT).show();
            }
        });
        holder.dinner.setText("Dinner: Tap to set");
        holder.dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempPosition = (Integer) v.getTag();
                Toast.makeText(context, dayList.get(tempPosition).getName() + " Dinner", Toast.LENGTH_SHORT).show();
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

    /*
    private void chooseMealDialog(int position)
    {
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.recipe_add_dialog, null);
        //AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptsView);
    }
    */

}