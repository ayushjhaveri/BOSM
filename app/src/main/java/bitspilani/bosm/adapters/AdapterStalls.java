package bitspilani.bosm.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bitspilani.bosm.R;
import bitspilani.bosm.fragments.FragmentFoodItems;
import bitspilani.bosm.items.ItemStall;
import bitspilani.bosm.utils.Constant;

/**
 * Created by Prashant on 4/6/2018.
 */

public class AdapterStalls extends ArrayAdapter<ItemStall> {
    Activity activity;
    public AdapterStalls(Context context , ArrayList<ItemStall> objects, Activity activity) {
        super(context, 0, objects);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ItemStall itemStall = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_stall_row, parent, false);
        }
        // Lookup view for data population
        TextView textView_stall_name = (TextView) convertView.findViewById(R.id.tv_stall_name);
        Typeface oswald_regular = Typeface.createFromAsset(getContext().getAssets(),"fonts/Oswald-Regular.ttf");
//        textView_stall_name.setTypeface(oswald_regular);
        // Populate the data into the template view using the data object
        textView_stall_name.setText(itemStall.getName());

        // Return the completed view to render on screen
        return convertView;
    }




}
