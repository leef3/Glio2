package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/11/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//Class to handle the population of the List of Ingredientss
public class IngredientListAdapter extends BaseAdapter
{
    Context context;
    protected ArrayList<IngredientObject> ingredientList;
    LayoutInflater inflater;

    //Constructor
    public IngredientListAdapter(Context context, ArrayList<IngredientObject> ingredientList) {
        this.ingredientList = ingredientList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    //Returns count,
    public int getCount() {
        return ingredientList.size();
    }
    public IngredientObject getItem(int position) {
        return ingredientList.get(position);
    }
    public long getItemId(int position)
    {
        return position;
    }

    //Method to add the information from each Ingredient into a row in the listview.
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.ingredient_list_item, parent, false);

            //Find UI elements for Pay, Name, and Days
            holder.name = (TextView) convertView.findViewById(R.id.ingredient_list_item_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Get the current Employee Object
        IngredientObject ingredientItem = ingredientList.get(position);

        //Set the contents of the UI elements
        holder.name.setText(IngredientObject.getName());

        return convertView;
    }

    private class ViewHolder {
        TextView name;
    }

}