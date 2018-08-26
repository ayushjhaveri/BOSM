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

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterCurrentSport;
import bitspilani.bosm.items.ItemMatch;
import bitspilani.bosm.utils.Constant;

public class CurrentSportFragment extends Fragment {

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
                Log.d("aaaaaaaaaabbbbbbbbbb","boys");
                mQuery = db.collection("scores").document("boys").collection(Constant.currentSport.getSport_id()+"").orderBy("timestamp");
            }else if(gender == 2){
                Log.d("aaaaaaaaaabbbbbbbbbb","girls");
                mQuery = db.collection("scores").document("girls").collection(Constant.currentSport.getSport_id()+"").orderBy("timestamp");
            }
        }else{
            Log.d("aaaaaaaaaabbbbbbbbbb","none");
            mQuery = db.collection("scores").document("none").collection(Constant.currentSport.getSport_id()+"").orderBy("timestamp");
        }

        adapterCurrentSport = new AdapterCurrentSport(getActivity(),mQuery);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCurrentSport);



        map = new HashMap<>();
//
//     //Athletic Resultss
//        currentSportArrayList1.add(new ItemMatch(
//                0,
//                0,
//                "Athletics",
//                "Gym G",
//                "10:00",
//                "20th Sep",
//                "Running Event-5000m",
//                "BITS",
//                "SBMJC",
//                "ZAKHIR",
//                "18:55",
//                "19:19",
//                "19:22"
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                0,
//                0,
//                "Athletics",
//                "Gym G",
//                "10:00",
//                "20th Sep",
//                "Running Event-5000m",
//                "BITS",
//                "SBMJC",
//                "ZAKHIR",
//                "18:55",
//                "19:19",
//                "19:22"
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                0,
//                0,
//                "Athletics",
//                "Gym G",
//                "10:00",
//                "20th Sep",
//                "Running Event-5000m",
//                "BITS",
//                "SBMJC",
//                "ZAKHIR",
//                "18:55",
//                "19:19",
//                "19:22"
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                0,
//                0,
//                "Athletics",
//                "Gym G",
//                "10:00",
//                "20th Sep",
//                "Running Event-5000m",
//                "BITS",
//                "SBMJC",
//                "ZAKHIR",
//                "18:55",
//                "19:19",
//                "19:22"
//        ));
//
//     //Athletic Schedule
//        currentSportArrayList1.add(new ItemMatch(
//                0,
//                0,
//                "Athletics",
//                "Gym G", "11:00",
//                "21st Sep",
//                "Relays - 4*100"
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                0,
//                0,
//                "Athletics",
//                "Gym G", "11:00",
//                "21st Sep",
//                "Relays - 4*100"
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                0,
//                0,
//                "Athletics",
//                "Gym G", "11:00",
//                "21st Sep",
//                "Relays - 4*100"
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                0,
//                0,
//                "Athletics",
//                "Gym G", "11:00",
//                "21st Sep",
//                "Relays - 4*100"
//        ));
////
////     //Team Results
//        currentSportArrayList1.add(new ItemMatch(
//                1,
//                1,
//                "Football",
//                "Gym G",
//                "16:00",
//                "22nd Sep",
//                "Semi-Final",
//                "5",
//                "0",
//                "BITS Pilani",
//                "MNIT Manipur",
//
//                1
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                1,
//                1,
//                "Football",
//                "Gym G",
//                "16:00",
//                "22nd Sep",
//                "Semi-Final",
//                "5",
//                "0",
//                "BITS Pilani",
//                "MNIT Manipur",
//
//                1
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                1,
//                1,
//                "Football",
//                "Gym G",
//                "16:00",
//                "22nd Sep",
//                "Semi-Final",
//                "5",
//                "0",
//                "BITS Pilani",
//                "MNIT Manipur",
//
//                1
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                1,
//                1,
//                "Football",
//                "Gym G",
//                "16:00",
//                "22nd Sep",
//                "Semi-Final",
//                "5",
//                "0",
//                "BITS Pilani",
//                "MNIT Manipur",
//
//                1
//        ));
////
//     //Team Schedule
//     currentSportArrayList1.add(new ItemMatch(
//             1,
//             1,
//             "Football",
//             "Gym G",
//             "18:00",
//             "23rd Sep",
//             "Final",
//             "BITS Pilani",
//             "BITS Goa"
//     ));
//        currentSportArrayList1.add(new ItemMatch(
//                1,
//                1,
//                "Football",
//                "Gym G",
//                "18:00",
//                "23rd Sep",
//                "Final",
//                "BITS Pilani",
//                "BITS Goa"
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                1,
//                1,
//                "Football",
//                "Gym G",
//                "18:00",
//                "23rd Sep",
//                "Final",
//                "BITS Pilani",
//                "BITS Goa"
//        ));
//        currentSportArrayList1.add(new ItemMatch(
//                1,
//                1,
//                "Football",
//                "Gym G",
//                "18:00",
//                "23rd Sep",
//                "Final",
//                "BITS Pilani",
//                "BITS Goa"
//        ));
//


//        for(int i=0;i<currentSportArrayList1.size();i++){
//            if(map.containsKey(currentSportArrayList1.get(i).getDate())){
//                currentSportArrayList1.get(i).setHeader(false);
//            }else{
//                map.put(currentSportArrayList1.get(i).getDate(),i);
//                currentSportArrayList1.get(i).setHeader(true);
//            }
//        }


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
}
