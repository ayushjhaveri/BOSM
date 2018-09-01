package bitspilani.bosm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

//import bitspilani.bosm.CurrentSportActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.fragments.CurrentSportFragment;
import bitspilani.bosm.items.ItemMatch;
import bitspilani.bosm.utils.Constant;

/**
 * Created by Prashant on 4/7/2018.
 */

public class AdapterCurrentSport extends FirestoreAdapter<RecyclerView.ViewHolder> {

    private Context context;

    private static final String TAG = "AdapterCart";


    public static String hash;

    public AdapterCurrentSport(Context context, Query query) {
        super(query);
        this.context = context;
        hash = "";
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constant.ATHLETIC_TYPE_MATCH:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_current_sport_athletic2, parent, false);
                return new AthleticViewHolder(view);
            case Constant.TEAM_MATCH:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_current_sport_team2, parent, false);
                return new TeamViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


        DocumentSnapshot document =  getSnapshot(position);

        Log.d(TAG,document.getData().toString()+" gfd");
        Timestamp timestamp  = (Timestamp) document.getData().get("timestamp");
        Date date = timestamp.toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);



        int matchType = Integer.parseInt(document.getData().get("match_type").toString());
        boolean isResult = Boolean.parseBoolean(document.getData().get("is_result").toString());


        ItemMatch itemMatch;

        switch (matchType) {
            case Constant.ATHLETIC_TYPE_MATCH:

                if(isResult) {
                    itemMatch = new ItemMatch(
                            Integer.parseInt(document.getData().get("match_type").toString()),
                            document.getData().get("sport_name").toString(),
                            document.getData().get("venue").toString(),
                            cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE),
                            cal.get(Calendar.DATE) + "",
                            document.getData().get("round").toString(),
                            document.getData().get("goldName").toString(),
                            document.getData().get("silverName").toString(),
                            document.getData().get("bronzeName").toString(),
                            document.getData().get("goldRecord").toString(),
                            document.getData().get("silverRecord").toString(),
                            document.getData().get("bronzeRecord").toString()
                            );
                }else{
                    itemMatch = new ItemMatch(
                            Integer.parseInt(document.getData().get("match_type").toString()),
                            document.getData().get("sport_name").toString(),
                            document.getData().get("venue").toString(),
                            cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE),
                            cal.get(Calendar.DATE) + "",
                            document.getData().get("round").toString()
                    );
                }

                if(!hash.equals(""+cal.get(Calendar.DATE))){
                    itemMatch.setHeader(true);
                    hash = ""+cal.get(Calendar.DATE);
                }else{
                    itemMatch.setHeader(false);
                }

                AthleticViewHolder holder1 = (AthleticViewHolder) holder;
                holder1.tv_sort_title.setText(itemMatch.getDate());
                holder1.tv_subtitle.setText(itemMatch.getType());
                holder1.tv_gold_name.setText(itemMatch.getGoldName());
                holder1.tv_gold_score.setText(itemMatch.getGoldRecord());
                holder1.tv_silver_name.setText(itemMatch.getSilverName());
                holder1.tv_silver_score.setText(itemMatch.getSilverRecord());
                holder1.tv_bronze_name.setText(itemMatch.getBronzeName());
                holder1.tv_bronze_score.setText(itemMatch.getBronzeRecord());
                holder1.tv_time.setText(itemMatch.getTime());
                holder1.tv_venue.setText(itemMatch.getVenue());

                if(itemMatch.getGoldName().equals("")){
                    holder1.rl_college.setVisibility(View.GONE);}
                    else{
                    holder1.rl_college.setVisibility(View.VISIBLE);
                }

//                if(CurrentSportFragment.map.containsKey(itemMatch.getDate())){
//
//                    holder1.rl_top.setVisibility(View.GONE);
//                }else{
//                    CurrentSportFragment.map.put(itemMatch.getDate(),position);
//                    holder1.rl_top.setVisibility(View.VISIBLE);
//                    holder1.tv_sort_title.setText(itemMatch.getDate());
//                }

                if(!itemMatch.isHeader()){
                    holder1.rl_top.setVisibility(View.GONE);
                }else{
                    holder1.rl_top.setVisibility(View.VISIBLE);
                    holder1.tv_sort_title.setText(itemMatch.getDate());
                }
                //for
                break;

            case Constant.TEAM_MATCH:
                if(isResult) {
                    itemMatch = new ItemMatch(
                            Integer.parseInt(document.getData().get("match_type").toString()),
                            document.getData().get("sport_name").toString(),
                            document.getData().get("venue").toString(),
                            cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE),
                            cal.get(Calendar.DATE) + "",
                            "hinjjin",
                            document.getData().get("score1").toString(),
                            document.getData().get("score2").toString(),
                            document.getData().get("college1").toString(),
                            document.getData().get("college2").toString(),
                            Integer.parseInt(document.getData().get("winner").toString())
                    );
                }else{
                    itemMatch = new ItemMatch(
                            Integer.parseInt(document.getData().get("match_type").toString()),
                            document.getData().get("sport_name").toString(),
                            document.getData().get("venue").toString(),
                            cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE),
                            cal.get(Calendar.DATE) + "",
                            document.getData().get("round").toString(),
                            document.getData().get("college1").toString(),
                            document.getData().get("college2").toString()
                    );
                }

                if(!hash.equals(""+cal.get(Calendar.DATE))){
                    itemMatch.setHeader(true);
                    hash = ""+cal.get(Calendar.DATE);
                }else{
                    itemMatch.setHeader(false);
                }

                TeamViewHolder holder2 = (TeamViewHolder) holder;
                holder2.tv_sort_title.setText(itemMatch.getDate());
                holder2.tv_subtitle.setText(itemMatch.getType());
                holder2.tv_time.setText(itemMatch.getTime());
                holder2.tv_venue.setText(itemMatch.getVenue());
                holder2.tv_college_one.setText(itemMatch.getCollege1());
                holder2.tv_college_two.setText(itemMatch.getCollege2());
                holder2.tv_score_one.setText(itemMatch.getScore1());
                holder2.tv_score_two.setText(itemMatch.getScore2());

                if(itemMatch.getWinner()==0) {
                    holder2.ll_score.setVisibility(View.GONE);
                    holder2.tv_college_one.setTextColor(ContextCompat.getColor(context, R.color.silver));
                    holder2.tv_college_two.setTextColor(ContextCompat.getColor(context, R.color.silver));
                }else{
                    if(itemMatch.getWinner()==1) {
                        holder2.tv_college_one.setTextColor(ContextCompat.getColor(context, R.color.gold));
                        holder2.tv_college_two.setTextColor(ContextCompat.getColor(context, R.color.silver));
                        holder2.tv_score_one.setTextColor(ContextCompat.getColor(context, R.color.gold));
                        holder2.tv_score_two.setTextColor(ContextCompat.getColor(context, R.color.silver));
                    }else if(itemMatch.getWinner()==2){
                        holder2.tv_college_one.setTextColor(ContextCompat.getColor(context, R.color.silver));
                        holder2.tv_college_two.setTextColor(ContextCompat.getColor(context, R.color.gold));
                        holder2.tv_score_two.setTextColor(ContextCompat.getColor(context, R.color.gold));
                        holder2.tv_score_one.setTextColor(ContextCompat.getColor(context, R.color.silver));
                    }

                    holder2.ll_score.setVisibility(View.VISIBLE);
                }

                if(!itemMatch.isHeader()){
                    holder2.rl_top.setVisibility(View.GONE);
                }else{
                    holder2.rl_top.setVisibility(View.VISIBLE);
                    holder2.tv_sort_title.setText(itemMatch.getDate());
                }

                break;
        }


    }

//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }


    @Override
    public int getItemViewType(int position) {
        DocumentSnapshot document =  getSnapshot(position);
        if (document != null && document.exists()) {
            Log.d("aaaaaa",document.getId()+"ffgfg");
           return Integer.parseInt(Objects.requireNonNull(document.getData()).get("match_type").toString());
//            ItemMatch object = arrayList.get(position);
//            if (object != null) {
//                return object.getMatchType();
//            }
        }
        return 0;
    }

    public class AthleticViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_top, rl_college;
        TextView tv_sort_title, tv_subtitle;
        TextView tv_gold_name, tv_gold_score;
        TextView tv_silver_name, tv_silver_score;
        TextView tv_bronze_name, tv_bronze_score;
        TextView tv_time, tv_venue;

        public AthleticViewHolder(View itemView) {
            super(itemView);
            rl_top = (RelativeLayout) itemView.findViewById(R.id.rl_top);
            tv_sort_title = (TextView) itemView.findViewById(R.id.tv_sort_title);
            tv_subtitle = (TextView) itemView.findViewById(R.id.tv_subtitle);
            tv_gold_name = (TextView) itemView.findViewById(R.id.tv_gold_name);
            tv_gold_score = (TextView) itemView.findViewById(R.id.tv_gold_score);
            tv_silver_name = (TextView) itemView.findViewById(R.id.tv_silver_name);
            tv_silver_score = (TextView) itemView.findViewById(R.id.tv_silver_score);
            tv_bronze_name = (TextView) itemView.findViewById(R.id.tv_bronze_name);
            tv_bronze_score = (TextView) itemView.findViewById(R.id.tv_bronze_score);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_venue = (TextView) itemView.findViewById(R.id.tv_venue);
            rl_college=(RelativeLayout)itemView.findViewById(R.id.rl_college);

        }
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_top;
        TextView tv_sort_title, tv_subtitle;
        TextView tv_college_one, tv_score_one;
        TextView tv_college_two, tv_score_two;
        TextView tv_time, tv_venue;
        LinearLayout ll_score;

        public TeamViewHolder(View itemView) {
            super(itemView);
            rl_top = (RelativeLayout) itemView.findViewById(R.id.rl_top);
            tv_sort_title = (TextView) itemView.findViewById(R.id.tv_sort_title);
            tv_subtitle = (TextView) itemView.findViewById(R.id.tv_subtitle);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_venue = (TextView) itemView.findViewById(R.id.tv_venue);
            tv_college_one = (TextView) itemView.findViewById(R.id.tv_college_one);
            tv_college_two = (TextView) itemView.findViewById(R.id.tv_college_two);
            tv_score_one = (TextView) itemView.findViewById(R.id.tv_score_one);
            tv_score_two = (TextView) itemView.findViewById(R.id.tv_score_two);
            ll_score=(LinearLayout)itemView.findViewById(R.id.ll_score);

        }
    }

}
