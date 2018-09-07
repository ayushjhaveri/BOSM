package bitspilani.bosm.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;

import bitspilani.bosm.HomeActivity;
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

    ProgressBar progressBar;

//    ArrayList<Integer> arrayList = new ArrayList<>();
    public SportFragment(){
        HomeActivity.currentFragment="aaaaaaaa";
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
//        Toast.makeText(getActivity(), HomeActivity.currentFragment, Toast.LENGTH_SHORT).show();
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view) ;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query mQuery = db.collection("sports").orderBy("sport_name");


        adapterSport = new AdapterSport(getContext(), mQuery, progressBar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterSport);



        iconHash.put(1,R.drawable.athletics);
        iconHash.put(2,R.drawable.swimming);
        iconHash.put(3,R.drawable.taekwondo);
        iconHash.put(4,R.drawable.badminton);
        iconHash.put(5,R.drawable.basketball);
        iconHash.put(6,R.drawable.cricket);
        iconHash.put(7,R.drawable.football);
        iconHash.put(8,R.drawable.hockey);
        iconHash.put(9,R.drawable.squash); //onilne icon left
        iconHash.put(10,R.drawable.tennis);  //misc icon left
        iconHash.put(11,R.drawable.tabletennis);
        iconHash.put(12,R.drawable.volleyball);
        iconHash.put(13,R.drawable.carrom);
        iconHash.put(14,R.drawable.chess);
        iconHash.put(15,R.drawable.powerlifting);
        iconHash.put(16,R.drawable.snooker);
        iconHash.put(17,R.drawable.circle);
        iconHash.put(18,R.drawable.circle);



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
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="aaaaaaaa";
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterSport != null) {
            adapterSport.stopListening();
        }
    }


}
