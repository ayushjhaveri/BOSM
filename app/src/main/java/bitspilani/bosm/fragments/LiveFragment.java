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


    RecyclerView recyclerView;
    AdapterLive adapterLive;


    // newInstance constructor for creating fragment with arguments
    public static LiveFragment newInstance(int page, String title) {
        LiveFragment fragmentFirst = new LiveFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

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
        liveArrayList.add(new ItemLive(0,
                "Football",
                1,
                2,
                "BITS Pilani",
                "IIT Delhi",
                "2",
                "1",
                74,
                45,
                "Gym G",
                "19:00",
                "Day 2",
                "Semi-final",
                0));

        liveArrayList.add(new ItemLive(0,
                "Football",
                1,
                2,
                "BITS Pilani",
                "IIT Delhi",
                "2",
                "1",
                74,
                45,
                "Gym G",
                "19:00",
                "Day 2",
                "Semi-final",
                0));

        liveArrayList.add(new ItemLive(0,
                "Football",
                1,
                2,
                "BITS Pilani",
                "IIT Delhi",
                "2",
                "1",
                74,
                45,
                "Gym G",
                "19:00",
                "Day 2",
                "Semi-final",
                1));

        liveArrayList.add(new ItemLive(0,
                "Football",
                1,
                2,
                "BITS Pilani",
                "IIT Delhi",
                "2",
                "1",
                74,
                45,
                "Gym G",
                "19:00",
                "Day 2",
                "Semi-final",
                1));
        liveArrayList.add(new ItemLive(0,
                "Football",
                1,
                2,
                "BITS Pilani",
                "IIT Delhi",
                "2",
                "1",
                74,
                45,
                "Gym G",
                "19:00",
                "Day 2",
                "Semi-final",
                1));
        liveArrayList.add(new ItemLive(0,
                "Football",
                1,
                2,
                "BITS Pilani",
                "IIT Delhi",
                "2",
                "1",
                74,
                45,
                "Gym G",
                "19:00",
                "Day 2",
                "Semi-final",
                1));
        liveArrayList.add(new ItemLive(0,
                "Football",
                1,
                2,
                "BITS Pilani",
                "IIT Delhi",
                "2",
                "1",
                74,
                45,
                "Gym G",
                "19:00",
                "Day 2",
                "Semi-final",
                1)); liveArrayList.add(new ItemLive(0,
                "Football",
                1,
                2,
                "BITS Pilani",
                "IIT Delhi",
                "2",
                "1",
                74,
                45,
                "Gym G",
                "19:00",
                "Day 2",
                "Semi-final",
                1)); liveArrayList.add(new ItemLive(0,
                "Football",
                1,
                2,
                "BITS Pilani",
                "IIT Delhi",
                "2",
                "1",
                74,
                45,
                "Gym G",
                "19:00",
                "Day 2",
                "Semi-final",
                1));





        adapterLive = new AdapterLive(getActivity(),liveArrayList);

        StickyListHeadersListView stickyList_history = (StickyListHeadersListView) view.findViewById(R.id.lv_recent);
        stickyList_history.setAdapter(adapterLive);

        return view;
    }
}
