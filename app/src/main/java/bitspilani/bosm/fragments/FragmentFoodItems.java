package bitspilani.bosm.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import bitspilani.bosm.CartActivity;
import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.WalletActivity;
import bitspilani.bosm.adapters.AdapterFoods;
import bitspilani.bosm.adapters.AdapterStalls;
import bitspilani.bosm.items.ItemFood;
import bitspilani.bosm.items.ItemStall;
import bitspilani.bosm.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFoodItems extends Fragment {


    private static final String TAG = "FragmentFoodItems";

    public FragmentFoodItems() {
        // Required empty public constructor
        HomeActivity.currentFragment = "FragmentFoodItems";
    }

    ImageButton ib_cart;
    TextView tv_stall_name;
    AdapterFoods adapterFoods;

    @SuppressLint("StaticFieldLeak")
    public static ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_food_items, container, false);
        init(rootView);

        tv_stall_name.setText(Constant.CURRENT_STALL_NAME);

        //setting up recycler view and firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        Query mQuery = db.collection("food_items").whereEqualTo("stall_id",Constant.CURRENT_STALL_ID);

        adapterFoods = new AdapterFoods(getActivity(),mQuery);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterFoods);



        return rootView;
    }

    private void init(View rootView){

        tv_stall_name = (TextView)rootView.findViewById(R.id.tv_stall_name);
        progressBar=(ProgressBar) rootView.findViewById(R.id.progressBar);
        Typeface oswald_regular = Typeface.createFromAsset(getContext().getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        tv_stall_name.setTypeface(oswald_regular);


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

    }



    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (adapterFoods != null) {
            adapterFoods.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterFoods != null) {
            adapterFoods.stopListening();
        }
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.commit();
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="FragmentFoodItems";
    }

}
