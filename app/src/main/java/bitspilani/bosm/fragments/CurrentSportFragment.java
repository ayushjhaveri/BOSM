package bitspilani.bosm.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;
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
    RelativeLayout rl_filled, rl_empty;
    private Context context;

    public static CurrentSportFragment newInstance(int param1) {
        CurrentSportFragment fragment = new CurrentSportFragment();
        Bundle args = new Bundle();
        args.putInt("gender", param1);
        fragment.setArguments(args);
        return fragment;
    }

    ProgressBar progressBar;


//    public static void viewLoader(boolean is){
//        if(is){
//           progressBar.setVisibility(View.VISIBLE);
//        }else {
//           progressBar.setVisibility(View.GONE);
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gender = -1;
        if (getArguments() != null) {
            gender = getArguments().getInt("gender");
        }
    }

    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_current_sport, container, false);
        context=getContext();
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
//        viewLoader(true);
//        Toast.makeText(context, HomeActivity.currentFragment, Toast.LENGTH_SHORT).show();
        //Firestore data retrieval

        rl_filled = (RelativeLayout)view.findViewById(R.id.rl_filled);
        rl_empty = (RelativeLayout)view.findViewById(R.id.rl_empty);
        rl_filled.setVisibility(View.VISIBLE);
        rl_empty.setVisibility(View.GONE);

        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected Object doInBackground(Object[] objects) {

                try {
                    Thread.sleep(Constant.sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(progressBar.getVisibility()==View.VISIBLE){
                    rl_filled.setVisibility(View.GONE);
                    rl_empty.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }
        };
        asyncTask.execute();

        FirebaseApp.initializeApp(context);

        FirebaseFirestore db = FirebaseFirestore.getInstance();



         recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view) ;

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

        adapterCurrentSport = new AdapterCurrentSport(getActivity(),mQuery,progressBar,recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCurrentSport);

//        recyclerView.smoothScrollToPosition(3);
//         ArrayList<ItemMatch> arrayList =       adapterCurrentSport.getMatchArray();
//
//         for(int i=0;i<arrayList.size();i++){
//             if(Calendar.getInstance().get(Calendar.DATE) == arrayList.get(i).getCalendar().get(Calendar.DATE)){
//        //         Log.d(TAG,"dSDSADASDSADSADSADASD" + position);
//                 CurrentSportFragment.recyclerView.smoothScrollToPosition(i+1);
//                 break;
//             }
//         }

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
