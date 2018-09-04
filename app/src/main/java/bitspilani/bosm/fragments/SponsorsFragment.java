package bitspilani.bosm.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterSponsors;
import bitspilani.bosm.items.ItemSponsor;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant on 14-05-2017.
 */

public class SponsorsFragment extends Fragment {

    ListView list_spons;
    List<ItemSponsor> list;
    Context context;
    Activity activity;

    public SponsorsFragment(){
        list=new ArrayList<ItemSponsor>();
        HomeActivity.currentFragment = "SponsorsFragment";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sponsors, container, false);


        initComponents(rootView);
        setupList();
        context = getContext();
        activity=getActivity();
        AdapterSponsors adapter = new AdapterSponsors(context,R.layout.row_sponsors,list,activity);
        list_spons.setAdapter(adapter);

        return rootView;
    }


    private void initComponents(View rootView){
        list_spons = (ListView) rootView.findViewById(R.id.list_spons);
    }

    private void setupList(){

        list.add(new ItemSponsor("One Plus", R.drawable.androidify_1));
        list.add(new ItemSponsor("One Plus", R.drawable.androidify_1));
        list.add(new ItemSponsor("One Plus", R.drawable.androidify_1));
        list.add(new ItemSponsor("One Plus", R.drawable.androidify_1));
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="SponsorsFragment";
    }

}
