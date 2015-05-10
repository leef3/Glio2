package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/10/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

            //Find UI elements for Pay, Name, and Days
            holder.image = (ImageView) convertView.findViewById(R.id.day_item_image);

            holder.title = (TextView) convertView.findViewById(R.id.day_item_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Get the current Employee Object
        DayObject dayItem = dayList.get(position);

        //Set the contents of the UI elements
        holder.image.setImageResource(dayItem.getImage());

        holder.title.setText(dayItem.getName());

        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        TextView title;
    }

}