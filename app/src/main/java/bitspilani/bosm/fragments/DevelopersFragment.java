package bitspilani.bosm.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterDevelopers;
import bitspilani.bosm.adapters.AdapterPhotos;
import bitspilani.bosm.adapters.AdapterSponsors;
import bitspilani.bosm.items.ItemSponsor;
import bitspilani.bosm.utils.Constant;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant on 14-05-2017.
 */

public class DevelopersFragment extends Fragment {

    List<ItemSponsor> list;
    Context context;
    Activity activity;
    AdapterDevelopers mAdapter;
    RelativeLayout rl_filled, rl_empty;
    ProgressBar progressBar;

    public DevelopersFragment(){
        list=new ArrayList<ItemSponsor>();
        HomeActivity.currentFragment = "SponsorsFragment";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_developers, container, false);

        context = getContext();
        activity=getActivity();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        rl_filled = (RelativeLayout)rootView.findViewById(R.id.rl_filled);
        rl_empty = (RelativeLayout)rootView.findViewById(R.id.rl_empty);
        rl_filled.setVisibility(View.VISIBLE);
        rl_empty.setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);


        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

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

        Query mQuery = db.collection("developers").orderBy("order");
        mAdapter = new AdapterDevelopers(context,mQuery, progressBar,rl_filled, rl_empty);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);
        recyclerView.setAdapter(alphaAdapter);

        TextView tv_header = (TextView) rootView.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Bold.ttf");

        tv_header.setTypeface(oswald_regular);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="DevelopersFragment";
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
