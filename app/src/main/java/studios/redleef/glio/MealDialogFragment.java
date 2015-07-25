package studios.redleef.glio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MealDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MealDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    private static final String ARG_TITLE = "title";

    Context context;

    // TODO: Rename and change types of parameters
    private int position;
    private String title;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static MealDialogFragment newInstance(int position, String title) {
        MealDialogFragment fragment = new MealDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public MealDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
            title = getArguments().getString(ARG_TITLE);

            context = getActivity();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.new_meal_add_dialog, null);
        //AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptsView);

        final TextView mealTitle = (TextView) promptsView.findViewById(R.id.mealDialogTitle);
        mealTitle.setText(title);

        //Set up the spinners
        final Spinner ingredientSpinner = (Spinner) promptsView.findViewById(R.id.newMealDialogRecipeSpinner);

        //TODO REPLACE WITH RECIPE ARRAY
        ArrayAdapter<CharSequence> ingredientAdapter = ArrayAdapter.createFromResource(context, R.array.ingredient_array, android.R.layout.simple_spinner_item);
        ingredientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientSpinner.setAdapter(ingredientAdapter);

        final Button addRecipeButton = (Button) promptsView.findViewById(R.id.addRecipeToDialogButton);

        //TODO: CHANGE FROM MANUAL SPINNER ADD TO LISTVIEW ADD ELEMENT
        final ListView mealDialogListView = (ListView) promptsView.findViewById(R.id.addMealDialogListView);

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
        return builder.show();

    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    */
/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
