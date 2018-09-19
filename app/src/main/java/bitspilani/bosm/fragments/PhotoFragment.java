package bitspilani.bosm.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterPhotos;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;


public class PhotoFragment extends Fragment {

    public PhotoFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment = "PhotoFragment";
    }

    Context context;
    RecyclerView recyclerView;
    AdapterPhotos mAdapter;
    ArrayList<String> phototUrlArrayList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        context = getContext();


        TextView tv_header = (TextView) view.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf");

        tv_header.setTypeface(oswald_regular);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FloatingActionButton fab= (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/folderview?id=12MNkXglyq1wHQtFWbgwWEYWBD1XsURla"));
                startActivity(browserIntent);
            }
        });

        Query mQuery = db.collection("photos").orderBy("timestamp", Query.Direction.DESCENDING);
        mAdapter = new AdapterPhotos(context,mQuery);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);
        recyclerView.setAdapter(alphaAdapter);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="PhotoFragment";
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
