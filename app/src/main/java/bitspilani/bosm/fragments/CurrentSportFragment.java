package bitspilani.bosm.fragments;


import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterCurrentSport;
import bitspilani.bosm.items.ItemMatch;
import bitspilani.bosm.utils.Constant;

public class    CurrentSportFragment extends Fragment {

    AdapterCurrentSport adapterCurrentSport;
    HashMap<String, Integer> map;
    private int gender;


    public static CurrentSportFragment newInstance(int param1) {
        CurrentSportFragment fragment = new CurrentSportFragment();
        Bundle args = new Bundle();
        args.putInt("gender", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gender = -1;
        if (getArguments() != null) {
            gender = getArguments().getInt("gender");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_sport, container, false);
        Toast.makeText(getContext(), HomeActivity.currentFragment, Toast.LENGTH_SHORT).show();
        //Firestore data retrieval
        FirebaseApp.initializeApp(getContext());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view) ;

        Query mQuery = null;
        if(Constant.currentSport.isGender()){
            if(gender == 1) {
                mQuery = db.collection("scores").whereEqualTo("gender",1).whereEqualTo("sport_id",Constant.currentSport.getSport_id()).orderBy("timestamp").whereEqualTo("item_type",1);
            }else if(gender == 2){
                mQuery = db.collection("scores").whereEqualTo("gender",2).whereEqualTo("sport_id",Constant.currentSport.getSport_id()).orderBy("timestamp").whereEqualTo("item_type",1);
            }
        }else{
            mQuery = db.collection("scores").whereEqualTo("gender",0).whereEqualTo("sport_id",Constant.currentSport.getSport_id()).orderBy("timestamp").whereEqualTo("item_type",1);
        }

        adapterCurrentSport = new AdapterCurrentSport(getActivity(),mQuery);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCurrentSport);



        map = new HashMap<>();

        adapterCurrentSport.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (adapterCurrentSport != null) {
            adapterCurrentSport.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterCurrentSport != null) {
            adapterCurrentSport.stopListening();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="CurrentSportFragment";
    }
}
