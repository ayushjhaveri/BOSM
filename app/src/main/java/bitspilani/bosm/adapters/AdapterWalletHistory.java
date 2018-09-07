package bitspilani.bosm.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;

import bitspilani.bosm.AddMoneyBActivity;
import bitspilani.bosm.OrderDetailsActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemWalletHistory;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static bitspilani.bosm.HomeActivity.getDayOfMonthSuffix;
import static com.android.volley.VolleyLog.TAG;
import static com.android.volley.VolleyLog.d;

/**
 * Created by Prashant on 4/5/2018.
 */

public class AdapterWalletHistory extends BaseAdapter implements StickyListHeadersAdapter,
        EventListener<QuerySnapshot> {
    private Query mQuery;
    private ListenerRegistration mRegistration;

    private ArrayList<DocumentSnapshot> mSnapshots = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("0.00");




    public AdapterWalletHistory(Context context, Query query) {
        inflater = LayoutInflater.from(context);
        this.mQuery = query;
        this.context=context;
    }




    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "onEvent:error", e);
            onError(e);
            return;
        }

        // Dispatch the event
        Log.d(TAG, "onEvent:numChanges:" + queryDocumentSnapshots.getDocumentChanges().size());
        for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
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
        }

        onDataChanged();

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
        notifyDataSetChanged();
    }

    public void setQuery(Query query) {
        // Stop listening
        stopListening();

        // Clear existing data
        mSnapshots.clear();
        notifyDataSetChanged();

        // Listen to new query
        mQuery = query;
        startListening();
    }

    @Override
    public int getCount() {
        return mSnapshots.size();
    }

    @Override
    public Object getItem(int position) {
        return mSnapshots.get(position);
    }

    protected void onDocumentAdded(DocumentChange change) {
        mSnapshots.add(change.getNewIndex(), change.getDocument());
        notifyDataSetChanged();
//        notifyItemInserted(change.getNewIndex());
    }

    protected void onDocumentModified(DocumentChange change) {
        if (change.getOldIndex() == change.getNewIndex()) {
            // Item changed but remained in same position
            mSnapshots.set(change.getOldIndex(), change.getDocument());
            notifyDataSetChanged();
//            notifyItemChanged(change.getOldIndex());
        } else {
            // Item changed and changed position
            mSnapshots.remove(change.getOldIndex());
            mSnapshots.add(change.getNewIndex(), change.getDocument());
            notifyDataSetChanged();
//            notifyItemMoved(change.getOldIndex(), change.getNewIndex());
        }
    }

    protected void onDocumentRemoved(DocumentChange change) {
        mSnapshots.remove(change.getOldIndex());
//        notifyItemRemoved(change.getOldIndex());
        notifyDataSetChanged();
    }

    protected void onError(FirebaseFirestoreException e) {
        Log.w(TAG, "onError", e);
    };

    protected void onDataChanged() {}

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final DocumentSnapshot document =  mSnapshots.get(position);

        Timestamp timestamp  = document.contains("timestamp")?(Timestamp) document.getData().get("timestamp"):Timestamp.now();
        Date date = timestamp.toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        final ItemWalletHistory itemWalletHistory = new ItemWalletHistory(
                Integer.parseInt(document.getId()),
                cal,
                 document.contains("amount")?Double.parseDouble(document.getData().get("amount").toString()):0,
                document.contains("order_unique_id")?document.getData().get("order_unique_id").toString():"",
                document.contains("from")?document.getData().get("from").toString():"",
                document.contains("to")?document.getData().get("to").toString():"",
                document.contains("remarks")?document.getData().get("remarks").toString():""
        );

        //Inflating row
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_wallet_history, parent, false);

           holder.textView_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.textView_header = (TextView) convertView.findViewById(R.id.tv_header);
            holder.textView_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            holder.textView_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.rl= (RelativeLayout) convertView.findViewById(R.id.rl);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if(itemWalletHistory.getOrder_id().equals(" ")){
            holder.textView_header.setText("Added to Wallet");
            holder.textView_amount.setTextColor(ContextCompat.getColor(context,R.color.colorPosAmount));
            holder.textView_amount.setText( "+ "+context.getResources().getString(R.string.Rs)+" "+df2.format(itemWalletHistory.getAmount())+"");
            holder.textView_comment.setVisibility(View.GONE);

            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(new AddMoneyBActivity());
                }
            });
        }else{
            holder.textView_header.setText("Paid for Order");
            holder.textView_amount.setTextColor(ContextCompat.getColor(context,R.color.colorNegAmount));
            holder.textView_amount.setText( "- "+context.getResources().getString(R.string.Rs)+" "+ df2.format(itemWalletHistory.getAmount())+"");

            holder.rl.setOnClickListener(   new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(OrderDetailsActivity.newInstance(Integer.parseInt(itemWalletHistory.getOrder_id())));
                }
            });
        }
        holder.textView_comment.setText("#"+itemWalletHistory.getOrder_id());
        SimpleDateFormat timeFormat = new SimpleDateFormat("h.mm a");


        holder.textView_time.setText(timeFormat.format(itemWalletHistory.getDate().getTime()));



        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        //Inflating row header
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.row_wallet_history_header, parent, false);
            holder.textView_transaction_date = (TextView) convertView.findViewById(R.id.tv_transaction_date);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        final DocumentSnapshot document =  mSnapshots.get(position);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String text = dateFormat.format(document.getData().get("").getDate().getTime());

        Timestamp timestamp  = document.contains("timestamp")?(Timestamp) document.getData().get("timestamp"):Timestamp.now();
        Date date = timestamp.toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String month_format = "MMM";
        SimpleDateFormat sdf_month = new SimpleDateFormat(month_format);
        holder.textView_transaction_date.setText(cal.get(Calendar.DATE)
                +getDayOfMonthSuffix(cal.get(Calendar.DATE))+
                " "+sdf_month.format(date)+"");
        return convertView;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.commit();
    }
    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
//        String text = dateFormat.format(arrayList.get(position).getDate().getTime());
//        return arrayList.get(position).getDate().get(Calendar.DATE);
        final DocumentSnapshot document =  mSnapshots.get(position);

        Timestamp timestamp  =document.contains("timestamp") ?(Timestamp) document.getData().get("timestamp"):Timestamp.now();
        Date date = timestamp.toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    class HeaderViewHolder {
        TextView textView_transaction_date;
    }

    class ViewHolder {
        TextView textView_header, textView_comment, textView_time;
        RelativeLayout rl;
        TextView textView_amount;
    }

}