package bitspilani.bosm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterCart;
import bitspilani.bosm.adapters.AdapterSport;
import bitspilani.bosm.adapters.ItemSport;

public class SportFragment extends Fragment{

    AdapterSport adapterSport;
    public static HashMap<Integer,Integer> iconHash;
    static {
        iconHash = new HashMap<>();
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
        View view = inflater.inflate(R.layout.fragment_score, container, false);


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view) ;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        Query mQuery = db.collection("Sports");

        adapterSport = new AdapterSport(getContext(),mQuery);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterSport);

        iconHash.put(1,R.drawable.football);
        iconHash.put(2,R.drawable.basketball);
        iconHash.put(3,R.drawable.cricket);
        iconHash.put(4,R.drawable.athletics);
        iconHash.put(5,R.drawable.powerlifting);
        iconHash.put(6,R.drawable.taekwondo);
        iconHash.put(7,R.drawable.badminton);
        iconHash.put(8,R.drawable.hockey);
        iconHash.put(9,R.drawable.tabletennis);
        iconHash.put(10,R.drawable.swimming);




        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (adapterSport != null) {
            adapterSport.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterSport != null) {
            adapterSport.stopListening();
        }
    }


}
