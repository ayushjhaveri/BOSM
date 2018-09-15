package bitspilani.bosm.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Objects;

import bitspilani.bosm.R;
import bitspilani.bosm.fragments.SportFragment;
import bitspilani.bosm.items.ItemRoulette;
import bitspilani.bosm.roulette.BiddingFragment;
import bitspilani.bosm.utils.Constant;

public class AdapterRoulette extends FirestoreAdapter<AdapterRoulette.RouletteViewHolder> {

    Context context;
    private ProgressBar progressBar;
    private RelativeLayout rl_filled, rl_empty;

    public class RouletteViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_sport, tv_date_time, tv_round, tv_college1, tv_college2, tv_status;
        ImageView star, ivLogo;
        RelativeLayout rl;


        public RouletteViewHolder(View view) {
            super(view);
            star = (ImageView) view.findViewById(R.id.star);
            ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
            tv_sport = (TextView) view.findViewById(R.id.tv_sport);
            tv_round = (TextView) view.findViewById(R.id.tv_round);
            tv_college1 = (TextView) view.findViewById(R.id.tv_college1);
            tv_college2 = (TextView) view.findViewById(R.id.tv_college2);
            tv_date_time = (TextView) view.findViewById(R.id.tv_date_time);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            rl = (RelativeLayout) view.findViewById(R.id.rl);
//            tv_venue = (TextView)view.findViewById(R.id.tv_venue);


        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public AdapterRoulette(Context context, Query mQuery, ProgressBar progressBar, RelativeLayout rl_filled, RelativeLayout rl_empty) {
        super(mQuery);
        this.context = context;
        this.progressBar = progressBar;
        this.rl_empty = rl_empty;
        this.rl_filled = rl_filled;
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        progressBar.setVisibility(View.GONE);
        rl_empty.setVisibility(View.GONE);
        rl_filled.setVisibility(View.VISIBLE);
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

        Timestamp timestamp = document.contains("timestamp") ? (Timestamp) document.getData().get("timestamp") : Timestamp.now();
        Date date = timestamp.toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean isResult = document.contains("is_result") ? Boolean.parseBoolean(document.getData().get("is_result").toString()) : false;
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
            array = new JSONArray(Objects.requireNonNull(document.getData().get("roulette").toString()));
            boolean isBet = false;

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if (object.getString("user_id").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    isBet = true;
                }
            }

            int winner = 0;
            if (isResult) {
                winner = document.contains("winner") ? Integer.parseInt(document.getData().get("winner").toString()) : 0;
            }
            final ItemRoulette itemRoulette = new ItemRoulette(
                    document.getId(),
                    document.contains("sport_id") ? Integer.parseInt(document.getData().get("sport_id").toString()) : 0,
                    document.contains("sport_name") ? document.getData().get("sport_name").toString() : "",
                    document.contains("venue") ? document.getData().get("venue").toString() : "",
                    cal,
                    document.contains("college1") ? document.getData().get("college1").toString() : "",
                    document.contains("college2") ? document.getData().get("college2").toString() : "",
                    winner,
                    document.contains("round") ? document.getData().get("round").toString() : "",
                    isBet,
                    status
            );

            holder.ivLogo.setImageResource(SportFragment.iconHash.get(itemRoulette.getSport_id()));
            holder.tv_sport.setText(itemRoulette.getSport_name());
//        holder.tv_status.setText(String.valueOf(itemRoulette.getStatus()));
            Calendar calendar = itemRoulette.getTimestamp();
            SimpleDateFormat f = new SimpleDateFormat("EEE dd MMM, h.mm a");

            holder.tv_date_time.setText(f.format(calendar.getTime()));
            holder.tv_college1.setText(itemRoulette.getCollege1());
            holder.tv_college2.setText(itemRoulette.getCollege2());
            holder.tv_round.setText(itemRoulette.getRound());

            holder.tv_status.setText(getStatusName(status));

            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(BiddingFragment.newInstance(itemRoulette.getSport_id(), document.getId(), itemRoulette.getSport_name()));
                }
            });

            if (isBet) {
                holder.star.setImageResource(R.drawable.star_filled_100);
                ImageViewCompat.setImageTintList(holder.star, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.background2)));
//                holder.star.setColorFilter(, android.graphics.PorterDuff.Mode.MULTIPLY);
                ///fdsnfjdsnffjkdsfnjdsdfnjndsjfndsjfkdsfndsjfnj
            } else {
                holder.star.setImageResource(R.drawable.star_100);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public static String getStatusName(int status) {
        if (status == -1) {
            return "Yet to start";
        } else if (status == 0) {
            return "Running";
        } else {
            return "Finished";
        }
    }



}