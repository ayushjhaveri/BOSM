package bitspilani.bosm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterLive;
import bitspilani.bosm.items.ItemLive;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class LiveFragment extends Fragment{

    AdapterLive adapterLive;
    ArrayList<ItemLive> liveArrayList;

    public LiveFragment(){
        HomeActivity.currentFragment="aaaaaaaa";
    }
    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liveArrayList = new ArrayList<>();

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);


//        Toast.makeText(getActivity(), HomeActivity.currentFragment, Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        Query mQuery = db.collection("scores").orderBy("item_type").orderBy("timestamp");
//        .whereGreaterThan("timestamp", Timestamp.now()).limit(15);

        adapterLive = new AdapterLive(getActivity(),mQuery);

        StickyListHeadersListView stickyList_history = (StickyListHeadersListView) view.findViewById(R.id.lv_recent);
        stickyList_history.setAdapter(adapterLive);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="aaaaaaaa";
    }
    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (adapterLive != null) {
            adapterLive.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterLive != null) {
            adapterLive.stopListening();
        }
    }



}
