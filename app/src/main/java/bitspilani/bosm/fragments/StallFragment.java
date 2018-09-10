package bitspilani.bosm.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import bitspilani.bosm.CartActivity;
import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.WalletActivity;
import bitspilani.bosm.adapters.AdapterEvents;
import bitspilani.bosm.adapters.AdapterStalls;
import bitspilani.bosm.items.ItemStall;
import bitspilani.bosm.utils.Constant;
//import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class StallFragment extends Fragment {

    private static final String TAG = "FragmentStall";
    AdapterStalls adapterStalls;
    private ProgressBar progressBar;
    Context context;
    RelativeLayout rl_filled, rl_empty;

    ImageButton ib_cart;

    public StallFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment = "StallFragment";

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_stall, container, false);

        context = getContext();
        init(rootView);



        progressBar.setVisibility(View.VISIBLE);

        rl_filled = (RelativeLayout)rootView.findViewById(R.id.rl_filled);
        rl_empty = (RelativeLayout)rootView.findViewById(R.id.rl_empty);
        rl_filled.setVisibility(View.VISIBLE);
        rl_empty.setVisibility(View.GONE);
        //imagebutton for cart
        ib_cart = (ImageButton) rootView.findViewById(R.id.ib_cart);
        ib_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new CartActivity());
            }
        });

        //fab for wallet
        FloatingActionButton fab_wallet = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new WalletActivity());
            }
        });

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


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        Query mQuery = db.collection("stalls");

        adapterStalls = new AdapterStalls(getActivity(),mQuery, progressBar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterStalls);

        return rootView;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.commit();
    }

    private void init(View rootView){
        progressBar=(ProgressBar) rootView.findViewById(R.id.progressBar);


        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView)rootView.findViewById(R.id.tv_stall);
        tv_title.setTypeface(oswald_regular);

    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="StallFragment";
    }

    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (adapterStalls != null) {
            adapterStalls.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterStalls != null) {
            adapterStalls.stopListening();
        }
    }

}
