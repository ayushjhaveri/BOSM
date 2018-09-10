package bitspilani.bosm.quilympics;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterQuilympicsLeaderboard;
import bitspilani.bosm.adapters.AdapterRouletteLeaderboard;


public class LeaderboardFragment extends Fragment {

    private AdapterQuilympicsLeaderboard mAdapter;
    RecyclerView recyclerView;
    Context context;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quilympics_leaderboard, container, false);

        context = getContext();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view) ;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextView tv_header = (TextView) rootView.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/KrinkesDecorPERSONAL.ttf");

        tv_header.setTypeface(oswald_regular);

        Query query = db.collection("user").orderBy("quilympics_score");
        mAdapter = new AdapterQuilympicsLeaderboard(context,query);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }




    @Override
    public void onStart() {
        super.onStart();
        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }


}
