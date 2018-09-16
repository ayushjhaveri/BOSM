package bitspilani.bosm.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.adapters.AdapterDevelopers;
import bitspilani.bosm.items.ItemDeveloper;
import bitspilani.bosm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelopersFragment extends Fragment {


    public DevelopersFragment() {
        HomeActivity.currentFragment.equals("DevelopersFragment");

    }

    List<ItemDeveloper> items;
    Context context;
    Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_developers, container, false);

        context=getContext();
        activity=getActivity();
        setupList();
        ListView listView = (ListView) rootView.findViewById(R.id.developers);
        ListAdapter listAdapter = new AdapterDevelopers(context, R.id.blapic, items, activity);
        listView.setAdapter(listAdapter);

        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Bold.ttf");
        TextView title = (TextView)rootView.findViewById(R.id.tv_header);
        title.setTypeface(oswald_regular);

        return rootView;
    }

    private void setupList(){
        items = new ArrayList<>();
        items.add(new ItemDeveloper(R.drawable.prashant,"Prashant Khandelwal","Lead Developer"));
        items.add(new ItemDeveloper(R.drawable.pallav,"Pallav Soni","Designer"));
        items.add(new ItemDeveloper(R.drawable.arpit,"Arpit Anshuman","Admin Portal UI"));
        items.add(new ItemDeveloper(R.drawable.ananya,"Ananya Pupneja","Admin Portal Backend"));



    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="DevelopersFragment";
    }

}
