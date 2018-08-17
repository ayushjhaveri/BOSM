package bitspilani.bosm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterCart;
import bitspilani.bosm.adapters.AdapterSport;
import bitspilani.bosm.adapters.ItemSport;

public class ScoreFragment extends Fragment{


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view) ;

        ArrayList<ItemSport> sportArrayList = new ArrayList<>();

        AdapterSport adapterSport = new AdapterSport(getContext(),sportArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterSport);


        sportArrayList.add(new ItemSport(
                1,"Football"
        ));
        sportArrayList.add(new ItemSport(
                1,"Basketball"
        ));

        sportArrayList.add(new ItemSport(
                1,"Hockey"
        ));

        sportArrayList.add(new ItemSport(
                1,"Volleyball"
        ));
        sportArrayList.add(new ItemSport(
                1,"Chess"
        ));
        sportArrayList.add(new ItemSport(
                1,"Pool"
        ));
        sportArrayList.add(new ItemSport(
                1,"Swimming"
        ));



        return view;
    }


}
