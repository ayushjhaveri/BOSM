package bitspilani.bosm.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
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

import static com.android.volley.VolleyLog.TAG;


public class EventFragment extends Fragment {
    AdapterEvents adapterEvents;
    ArrayList<ItemEvent> eventsArrayList;

    public EventFragment(){
        HomeActivity.currentFragment = "EventFragment";
    }

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

        TextView tv_header = (TextView) view.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/KrinkesDecorPERSONAL.ttf");

        tv_header.setTypeface(oswald_regular);

        tv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                db.setFirestoreSettings(settings);

                Map<String,Object> data = new HashMap<>();
                data.put("college1","BITS");
                data.put("college2","TITS");
                data.put("is_result",true);
                data.put("match_round","Round 1");
                data.put("match_type",1);
                data.put("score1",12);
                data.put("score2",17);
                data.put("sport_name","Athletics");
                data.put("timestamp", FieldValue.serverTimestamp());
                data.put("venue","SAC");
                data.put("gender",1);
                data.put("hits",2);
                data.put("winner",1);
                data.put("sport_id",1);

                db.collection("scores").document().set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"done",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(),"false",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //Firestore data retrieval
        FirebaseApp.initializeApp(getContext());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);



        Query mQuery = db.collection("events");

        adapterEvents = new AdapterEvents(getActivity(),mQuery);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterEvents);


        return view;
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
