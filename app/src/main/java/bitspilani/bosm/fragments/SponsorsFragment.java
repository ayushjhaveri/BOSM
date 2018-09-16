package bitspilani.bosm.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

        TextView tv_header = (TextView) rootView.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");

        tv_header.setTypeface(oswald_regular);

        return rootView;
    }


    private void initComponents(View rootView){
        list_spons = (ListView) rootView.findViewById(R.id.list_spons);
    }

    private void setupList(){

        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));
        list.add(new ItemSponsor("Paytm","Title Sponsor", R.drawable.logo_paytm));

        }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="SponsorsFragment";
    }

}
