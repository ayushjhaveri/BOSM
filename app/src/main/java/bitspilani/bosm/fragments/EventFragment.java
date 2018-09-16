package bitspilani.bosm.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterCurrentSport;
import bitspilani.bosm.adapters.AdapterEvents;
import bitspilani.bosm.items.ItemEvent;
import bitspilani.bosm.items.ItemMatch;
import bitspilani.bosm.utils.Constant;

import static com.android.volley.VolleyLog.TAG;
import static com.android.volley.VolleyLog.v;


public class EventFragment extends Fragment {
    AdapterEvents adapterEvents;
    ArrayList<ItemEvent> eventsArrayList;
    private ProgressBar progressBar;
    RelativeLayout rl_filled, rl_empty;

    public EventFragment(){
        HomeActivity.currentFragment="aaaaaaaa";

    }
    
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        context = getContext();
        rl_filled = (RelativeLayout)view.findViewById(R.id.rl_filled);
        rl_empty = (RelativeLayout)view.findViewById(R.id.rl_empty);
        rl_filled.setVisibility(View.VISIBLE);
        rl_empty.setVisibility(View.GONE);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        TextView tv_header = (TextView) view.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Regular.ttf");

        tv_header.setTypeface(oswald_regular);
        tv_header.setText("EVENTS");

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

        //Firestore data retrieval
        FirebaseApp.initializeApp(context);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);



        Query mQuery = db.collection("events").orderBy("timestamp");

        adapterEvents = new AdapterEvents(getActivity(),mQuery,progressBar, rl_filled, rl_empty);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterEvents);


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
        if (adapterEvents != null) {
            adapterEvents.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterEvents != null) {
            adapterEvents.stopListening();
        }
    }


}
