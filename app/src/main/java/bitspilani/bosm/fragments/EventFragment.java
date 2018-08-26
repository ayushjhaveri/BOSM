package bitspilani.bosm.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterCurrentSport;
import bitspilani.bosm.adapters.AdapterEvents;
import bitspilani.bosm.items.ItemEvent;
import bitspilani.bosm.items.ItemMatch;

import static com.android.volley.VolleyLog.TAG;


public class EventFragment extends Fragment {
    AdapterEvents adapterEvents;
    ArrayList<ItemEvent> eventsArrayList;

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

        //Firestore data retrieval
        FirebaseApp.initializeApp(getContext());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


//        eventsArrayList.add(new ItemEvent(
//                0,
//                "Street Football",
//                "21st Sep",
//                "19:00",
//                "Main Audi",
//                "This is the information about this Event"
//        ));
//
//        eventsArrayList.add(new ItemEvent(
//                0,
//                "Street Football",
//                "21st Sep",
//                "19:00",
//                "Main Audi",
//                "This is the information about this Event"
//        ));
//        eventsArrayList.add(new ItemEvent(
//                0,
//                "Street Football",
//                "21st Sep",
//                "19:00",
//                "Main Audi",
//                "This is the information about this Event"
//        ));
//        eventsArrayList.add(new ItemEvent(
//                0,
//                "Street Football",
//                "21st Sep",
//                "19:00",
//                "Main Audi",
//                "This is the information about this Event"
//        ));
//        eventsArrayList.add(new ItemEvent(
//                0,
//                "Street Football",
//                "21st Sep",
//                "19:00",
//                "Main Audi",
//                "This is the information about this Event"
//        ));eventsArrayList.add(new ItemEvent(
//                0,
//                "Street Football",
//                "21st Sep",
//                "19:00",
//                "Main Audi",
//                "This is the information about this Event"
//        ));
//        eventsArrayList.add(new ItemEvent(
//                0,
//                "Street Football",
//                "21st Sep",
//                "19:00",
//                "Main Audi",
//                "This is the information about this Event"
//        ));
//        eventsArrayList.add(new ItemEvent(
//                0,
//                "Street Football",
//                "21st Sep",
//                "19:00",
//                "Main Audi",
//                "This is the information about this Event"
//        ));

        Query mQuery = db.collection("events");

        adapterEvents = new AdapterEvents(getActivity(),mQuery);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterEvents);


//        db.collection("events")
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(TAG, document.getId() + " => " + document.getData());
//
//                        Timestamp timestamp = (Timestamp) document.getData().get("timestamp");
//                        Date date = timestamp.toDate();
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(date);
//
//                        ItemEvent itemEvent = new ItemEvent(
//                                document.getId(),
//                                document.getData().get("title").toString(),
//                                cal.get(Calendar.DATE) + "",
//                                cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE),
//                                document.getData().get("venue").toString(),
//                                document.getData().get("text").toString(),
//                                document.getData().get("club").toString()
//                        );
//                        eventsArrayList.add(itemEvent);
//                    }
//                    adapterEvents.notifyDataSetChanged();
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                    Toast.makeText(getContext(),"Please check your connection!",Toast.LENGTH_SHORT).show();
//                }
//            }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
//            }
//        });

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



//package bitspilani.bosm.adapters;
//
//        import android.content.Context;
//        import android.support.v7.widget.RecyclerView;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.TextView;
//
//        import com.google.firebase.Timestamp;
//        import com.google.firebase.firestore.DocumentSnapshot;
//        import com.google.firebase.firestore.Query;
//        import com.ramotion.foldingcell.FoldingCell;
//
//        import java.util.ArrayList;
//        import java.util.Calendar;
//        import java.util.Date;
//
//        import bitspilani.bosm.R;
//        import bitspilani.bosm.items.ItemEvent;
//
//public class AdapterEvents extends RecyclerView.Adapter<AdapterEvents.ViewHolder> {
//
//    private Context context;
//    private ArrayList<ItemEvent> eventArrayList;
//
//    private static final String TAG = "AdapterCart";
//
//    public AdapterEvents(Context context, ArrayList<ItemEvent> eventArrayList) {
//        this.context = context;
//        this.eventArrayList = eventArrayList;
//    }
//
//    @Override
//    public AdapterEvents.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_event, parent, false);
//
//        return new AdapterEvents.ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final AdapterEvents.ViewHolder holder, final int position) {
//
////        Timestamp timestamp  = (Timestamp) document.getData().get("timestamp");
////        Date date = timestamp.toDate();
////        Calendar cal = Calendar.getInstance();
////        cal.setTime(date);
////
////        ItemEvent itemEvent = new ItemEvent(
////                document.getId(),
////                document.getData().get("title").toString(),
////                cal.get(Calendar.DATE)+"",
////                cal.get(Calendar.HOUR) +":"+ cal.get(Calendar.MINUTE),
////                document.getData().get("venue").toString(),
////                document.getData().get("text").toString(),
////                document.getData().get("club").toString()
////        );
//        ItemEvent itemEvent = eventArrayList.get(position);
//
//        // use itemEvent here
//
//        holder.tv_title.setText(itemEvent.getEvent_name());
//        holder.tv_event_name.setText(itemEvent.getEvent_name());
//
//        holder.tv_info.setText(itemEvent.getEvent_text());
//
//        holder.tv_club.setText(itemEvent.getEvent_club());
//        holder.tv_date.setText(itemEvent.getEvent_date());
//        holder.tv_time.setText(itemEvent.getEvent_time());
//        holder.tv_venue.setText(itemEvent.getEvent_venue());
//
//        holder.tv_club2.setText(itemEvent.getEvent_club());
//        holder.tv_date2.setText(itemEvent.getEvent_date());
//        holder.tv_time2.setText(itemEvent.getEvent_time());
//        holder.tv_venue2.setText(itemEvent.getEvent_venue());
//
//        holder.fc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.fc.toggle(false);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return eventArrayList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView tv_title, tv_info, tv_club2, tv_venue2, tv_date2, tv_time2, tv_event_name,  tv_club, tv_venue, tv_date, tv_time ;
//        FoldingCell fc;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            tv_title = (TextView)itemView.findViewById(R.id.tv_title);
//            tv_info = (TextView)itemView.findViewById(R.id.tv_info);
//            tv_club2 = (TextView)itemView.findViewById(R.id.tv_club2);
//            tv_venue2 = (TextView)itemView.findViewById(R.id.tv_venue2);
//            tv_date2 = (TextView)itemView.findViewById(R.id.tv_date2);
//            tv_time2 = (TextView)itemView.findViewById(R.id.tv_time2);
//
//            tv_club = (TextView)itemView.findViewById(R.id.tv_club);
//            tv_venue = (TextView)itemView.findViewById(R.id.tv_venue);
//            tv_date = (TextView)itemView.findViewById(R.id.tv_date);
//            tv_time = (TextView)itemView.findViewById(R.id.tv_time);
//            tv_event_name = (TextView)itemView.findViewById(R.id.tv_event_name);
//
//            fc = (FoldingCell) itemView.findViewById(R.id.folding_cell);
//
//        }
//    }
//
//}
//
