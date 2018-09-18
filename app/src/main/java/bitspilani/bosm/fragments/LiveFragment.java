package bitspilani.bosm.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import bitspilani.bosm.utils.Constant;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class LiveFragment extends Fragment{

    AdapterLive adapterLive;
    ArrayList<ItemLive> liveArrayList;

    ProgressBar progressBar;
    RelativeLayout rl_filled, rl_empty;

    Context context;

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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);

        context = getContext();

        rl_filled = (RelativeLayout)view.findViewById(R.id.rl_filled);
        rl_empty = (RelativeLayout)view.findViewById(R.id.rl_empty);
        rl_filled.setVisibility(View.VISIBLE);
        rl_empty.setVisibility(View.GONE);

        progressBar =(ProgressBar)view.findViewById(R.id.progressBar);

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
//                    rl_filled.setVisibility(View.GONE);
                    rl_empty.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }
        };
        asyncTask.execute();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query mQuery = db.collection("scores").orderBy("item_type").orderBy("timestamp");
//        .whereGreaterThan("timestamp", Timestamp.now()).limit(15);

        adapterLive = new AdapterLive(getActivity(),mQuery,progressBar, rl_filled, rl_empty);

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
