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

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterLive;
import bitspilani.bosm.items.ItemLive;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class LiveFragment extends Fragment{

    AdapterLive adapterLive;

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);


        final ArrayList<ItemLive> liveArrayList = new ArrayList<>();
       liveArrayList.add(new ItemLive(
               0,
        1,
        "Football",
        "BITS Pilani",
        "BITS Goa",
        "Semi Final",
        "Gym G",
        "19:00",
        "21st Sep",
        "5",
        "2",
        56,
        87,
        -1));
        liveArrayList.add(new ItemLive(
                0,
                1,
                "Football",
                "BITS Pilani",
                "BITS Goa",
                "Semi Final",
                "Gym G",
                "19:00",
                "21st Sep",
                "5",
                "2",
                56,
                87,
                -1));

        liveArrayList.add(new ItemLive(
                1,
                1,
                "Football",
                "BITS Pilani",
                "BITS Goa",
                "Semi-final",
                "Gym G",
                "19:00",
                "21st Sep"));
        liveArrayList.add(new ItemLive(
                1,
                1,
                "Football",
                "BITS Pilani",
                "BITS Goa",
                "Semi-final",
                "Gym G",
                "19:00",
                "21st Sep"));
        liveArrayList.add(new ItemLive(
                1,
                1,
                "Football",
                "BITS Pilani",
                "BITS Goa",
                "Semi-final",
                "Gym G",
                "19:00",
                "21st Sep"));




        adapterLive = new AdapterLive(getActivity(),liveArrayList);

        StickyListHeadersListView stickyList_history = (StickyListHeadersListView) view.findViewById(R.id.lv_recent);
        stickyList_history.setAdapter(adapterLive);

        return view;
    }

}
