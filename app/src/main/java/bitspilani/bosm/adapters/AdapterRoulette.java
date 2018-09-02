package bitspilani.bosm.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.paytm.pgsdk.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemRoulette;
import bitspilani.bosm.roulette.BiddingFragment;
import bitspilani.bosm.utils.Constant;

public class AdapterRoulette extends FirestoreAdapter<AdapterRoulette.RouletteViewHolder> {

    Context context;
    public class RouletteViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_sport, tv_date_time, tv_round, tv_college1, tv_college2, tv_status;

        RelativeLayout rl;
        public RouletteViewHolder(View view) {
            super(view);
            tv_sport = (TextView)view.findViewById(R.id.tv_sport);
            tv_round = (TextView)view.findViewById(R.id.tv_round);
            tv_college1 = (TextView)view.findViewById(R.id.tv_college1);
            tv_college2 = (TextView)view.findViewById(R.id.tv_college2);
            tv_date_time = (TextView)view.findViewById(R.id.tv_date_time);
            tv_status = (TextView)view.findViewById(R.id.tv_status);
            rl = (RelativeLayout) view.findViewById(R.id.rl);
//            tv_venue = (TextView)view.findViewById(R.id.tv_venue);


        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.commit();
    }



    public AdapterRoulette(Context context, Query mQuery) {
        super(mQuery);
        this.context = context;
    }

    @Override
    public RouletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_roulette_main, parent, false);

        return new RouletteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RouletteViewHolder holder, int position) {

        final DocumentSnapshot document = getSnapshot(position);

        Log.d("hhhhhhhhhhhhhhhhhhhhh",document.getData().toString()+"jkkl");

        Timestamp timestamp  = (Timestamp) document.getData().get("timestamp");
        Date date = timestamp.toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean isResult = Boolean.parseBoolean(document.getData().get("winner").toString());
        Calendar calNow = Calendar.getInstance();
        cal.setTime(date);
        int status = -1;
        if (isResult) {
            status = 1;
        } else {
            if (cal.compareTo(calNow) < 0) {
                status = 0;
            } else {
                status = -1;
            }
        }

        JSONArray array = null;
        try {
            array = new JSONArray(document.getData().get("roulette").toString());
            boolean isBet = false;

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if (object.getString("user_id").equals(Constant.user.getUid())) {
                    isBet = true;
                }
            }


            final ItemRoulette itemRoulette = new ItemRoulette(
                    document.getId(),
                    Integer.parseInt(document.getData().get("sport_id").toString()),
                    document.getData().get("sport_name").toString(),
                    document.getData().get("venue").toString(),
                    cal,
                    document.getData().get("college1").toString(),
                    document.getData().get("college2").toString(),
                    Integer.parseInt(document.getData().get("winner").toString()),
                    document.getData().get("round").toString(),
                    isBet,
                    status
            );

            holder.tv_sport.setText(itemRoulette.getSport_name());
//        holder.tv_status.setText(String.valueOf(itemRoulette.getStatus()));
            Calendar calendar = itemRoulette.getTimestamp();
            SimpleDateFormat f = new SimpleDateFormat("EEE dd MMM, hh:mm a");

            holder.tv_date_time.setText(f.format(calendar.getTime()));
            holder.tv_college1.setText(itemRoulette.getCollege1());
            holder.tv_college2.setText(itemRoulette.getCollege2());
            holder.tv_round.setText(itemRoulette.getRound());

            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(BiddingFragment.newInstance(itemRoulette.getSport_id(), document.getId()));
                }
            });

            if(isBet){
                ///fdsnfjdsnffjkdsfnjdsdfnjndsjfndsjfkdsfndsjfnj
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}