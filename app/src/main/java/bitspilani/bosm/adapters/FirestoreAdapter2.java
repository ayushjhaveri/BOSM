package bitspilani.bosm.adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bitspilani.bosm.items.ItemMatch;
import bitspilani.bosm.utils.Constant;

import static bitspilani.bosm.HomeActivity.getDayOfMonthSuffix;
import static bitspilani.bosm.HomeActivity.toTitleCase;

/**
 * RecyclerView adapter for displaying the results of a Firestore {@link Query}.
 *
 * Note that this class forgoes some efficiency to gain simplicity. For example, the result of
 * {@link DocumentSnapshot#toObject(Class)} is not cached so the same object may be deserialized
 * many times as the user scrolls.
 */
public abstract class FirestoreAdapter2<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>
        implements EventListener<QuerySnapshot> {

    private static final String TAG = "FirestoreAdapter";

    private Query mQuery;
    private ListenerRegistration mRegistration;

    private ArrayList<DocumentSnapshot> mSnapshots = new ArrayList<>();
    private ArrayList<ItemMatch> arrayList = new ArrayList<>();
    public Query getmQuery() {
        return mQuery;
    }

    public FirestoreAdapter2(Query query) {
        mQuery = query;
    }

    public ArrayList<ItemMatch> getMatchArray() {
        return arrayList;
    }


    private void generateArrayFromSnapShot() {
        arrayList.clear();
        String hash ="";
        for (int i = 0; i < mSnapshots.size(); i++) {
            DocumentSnapshot document = mSnapshots.get(i);
            Timestamp timestamp = document.contains("timestamp") ? (Timestamp) document.getData().get("timestamp") : Timestamp.now();
            Date date = timestamp.toDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d");
            SimpleDateFormat smf = new SimpleDateFormat("MMM");
            SimpleDateFormat stf = new SimpleDateFormat("h.mm a");
            SimpleDateFormat smf2 = new SimpleDateFormat("MMM");


            int matchType = document.contains("match_type") ? Integer.parseInt(document.getData().get("match_type").toString()) : 1;
            boolean isResult = document.contains("is_result") ? Boolean.parseBoolean(document.getData().get("is_result").toString()) : false;

            ItemMatch itemMatch =null;

            switch (matchType) {
                case Constant.ATHLETIC_TYPE_MATCH:
                    if (isResult) {
                        itemMatch = new ItemMatch(
                                0,
                                document.contains("sport_name") ? document.getData().get("sport_name").toString() : "",
                                document.contains("venue") ? document.getData().get("venue").toString() : "",
                                stf.format(date),
                                sdf.format(date) + getDayOfMonthSuffix(cal.get(Calendar.DATE)) + " " + smf.format(date),
                                document.contains("round") ? toTitleCase(document.getData().get("round").toString()) : "",
                                document.contains("goldName") ? document.getData().get("goldName").toString() : "",
                                document.contains("silverName") ? document.getData().get("silverName").toString() : "",
                                document.contains("bronzeName") ? document.getData().get("bronzeName").toString() : "",
                                document.contains("goldRecord") ? document.getData().get("goldRecord").toString() : "",
                                document.contains("silverRecord") ? document.getData().get("silverRecord").toString() : "",
                                document.contains("bronzeRecord") ? document.getData().get("bronzeRecord").toString() : "",
                                cal,
                                document.contains("extra_details")? document.getData().get("extra_details").toString():""
                        );
                    } else {
                        itemMatch = new ItemMatch(
                                0,
                                document.contains("sport_name") ? document.getData().get("sport_name").toString() : "",
                                document.contains("venue") ? document.getData().get("venue").toString() : "",
                                stf.format(date),
                                sdf.format(date) + getDayOfMonthSuffix(cal.get(Calendar.DATE)) + " " + smf.format(date),
                                document.contains("round") ? toTitleCase(document.getData().get("round").toString()) : "",
                                cal
                        );
                    }

                    //for isHeader

                    if(!hash.equals(String.valueOf(cal.get(Calendar.DATE)))){
                       hash =  String.valueOf(cal.get(Calendar.DATE));
                       itemMatch.setHeader(true);
                    }else{
                        itemMatch.setHeader(false);
                    }

                    break;

                case Constant.TEAM_MATCH:
                    if (isResult) {
                        itemMatch = new ItemMatch(
                                1,
                                document.contains("sport_name") ? document.getData().get("sport_name").toString() : "",
                                document.contains("venue") ? document.getData().get("venue").toString() : "",
                                stf.format(date),
                                sdf.format(date) + getDayOfMonthSuffix(cal.get(Calendar.DATE)) + " " + smf.format(date),
                                document.contains("round") ? toTitleCase(document.getData().get("round").toString()) : "",
                                document.contains("score1") ? document.getData().get("score1").toString() : "",
                                document.contains("score2") ? document.getData().get("score2").toString() : "",
                                document.contains("college1") ? document.getData().get("college1").toString() : "",
                                document.contains("college2") ? document.getData().get("college2").toString() : "",
                                document.contains("winner") ? Integer.parseInt(document.getData().get("winner").toString()) : 0,
                                document.contains("full_college1") ? toTitleCase((String) document.getData().get("full_college1")) : "",
                                document.contains("full_college2") ? toTitleCase((String) document.getData().get("full_college2")) : "",
                                cal,
                                document.contains("extra_details")? document.getData().get("extra_details").toString():""
                        );
                    } else {
                        itemMatch = new ItemMatch(
                                1,
                                document.contains("sport_name") ? document.getData().get("sport_name").toString() : "",
                                document.contains("venue") ? document.getData().get("venue").toString() : "",
                                stf.format(date),
                                sdf.format(date) + getDayOfMonthSuffix(cal.get(Calendar.DATE)) + " " + smf.format(date),
                                document.contains("round") ? toTitleCase(document.getData().get("round").toString()) : "",
                                document.contains("college1") ? document.getData().get("college1").toString() : "",
                                document.contains("college2") ? document.getData().get("college2").toString() : "",
                                document.contains("full_college1") ? toTitleCase((String) document.getData().get("full_college1")) : "",
                                document.contains("full_college2") ? toTitleCase((String) document.getData().get("full_college2")) : "",
                                cal
                        );
                    }

                    if(!hash.equals(String.valueOf(cal.get(Calendar.DATE)))){
                        hash =  String.valueOf(cal.get(Calendar.DATE));
                        itemMatch.setHeader(true);
                    }else{
                        itemMatch.setHeader(false);
                    }
                    break;

            }
            arrayList.add(itemMatch);

        }
    }



    @Override
    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "onEvent:error", e);
            onError(e);
            return;
        }

        // Dispatch the event
        Log.d(TAG, "onEvent:numChanges:" + documentSnapshots.getDocumentChanges().size());
        for (DocumentChange change : documentSnapshots.getDocumentChanges()) {
//            if(AdapterCurrentSport.hash!=null)
//                AdapterCurrentSport.hash="";
            switch (change.getType()) {
                case ADDED:
                    onDocumentAdded(change);
                    break;
                case MODIFIED:
                    onDocumentModified(change);
                    break;
                case REMOVED:
                    onDocumentRemoved(change);
                    break;
            }
            onDataChanged();
        }


    }

    public void startListening() {
        if (mQuery != null && mRegistration == null) {
            mRegistration = mQuery.addSnapshotListener(this);
        }
    }

    public void stopListening() {
        if (mRegistration != null) {
            mRegistration.remove();
            mRegistration = null;
        }

        mSnapshots.clear();
        generateArrayFromSnapShot();
        notifyDataSetChanged();
    }

    public void setQuery(Query query) {
        // Stop listening
        stopListening();

        // Clear existing data
        mSnapshots.clear();
        generateArrayFromSnapShot();
        notifyDataSetChanged();

        // Listen to new query
        mQuery = query;
        startListening();
    }

    @Override
    public int getItemCount() {
        return mSnapshots.size();
    }

//    protected DocumentSnapshot getSnapshot(int index) {
//        return mSnapshots.get(index);
//    }

    protected void onDocumentAdded(DocumentChange change) {
        mSnapshots.add(change.getNewIndex(), change.getDocument());
        generateArrayFromSnapShot();
//        notifyItemInserted(change.getNewIndex());
        notifyDataSetChanged();
    }

    protected void onDocumentModified(DocumentChange change) {
        if (change.getOldIndex() == change.getNewIndex()) {
            // Item changed but remained in same position
            mSnapshots.set(change.getOldIndex(), change.getDocument());

//            notifyItemChanged(change.getOldIndex());
        } else {
            // Item changed and changed position
            mSnapshots.remove(change.getOldIndex());
            mSnapshots.add(change.getNewIndex(), change.getDocument());
//            notifyItemMoved(change.getOldIndex(), change.getNewIndex());
        }
        generateArrayFromSnapShot();
        notifyDataSetChanged();

    }

    protected void onDocumentRemoved(DocumentChange change) {
        mSnapshots.remove(change.getOldIndex());
        generateArrayFromSnapShot();
        notifyDataSetChanged();
//        notifyItemRemoved(change.getOldIndex());
    }

    protected void onError(FirebaseFirestoreException e) {
        Log.w(TAG, "onError", e);
    };

    protected void onDataChanged() {}
}