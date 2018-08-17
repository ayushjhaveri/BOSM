package bitspilani.bosm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

//import bitspilani.bosm.CurrentSportActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.fragments.CurrentSportFragment;
import bitspilani.bosm.items.ItemCurrentSport;
import bitspilani.bosm.items.ItemMatch;
import bitspilani.bosm.utils.Constant;

/**
 * Created by Prashant on 4/7/2018.
 */

public class AdapterCurrentSport extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ItemMatch> arrayList;
    private Context context;

    private static final String TAG = "AdapterCart";

    public AdapterCurrentSport(Context context, ArrayList<ItemMatch> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
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
        final ItemMatch itemMatch = arrayList.get(position);

        switch (itemMatch.getMatchType()) {
            case Constant.ATHLETIC_TYPE_MATCH:
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
                    }else if(itemMatch.getWinner()==2){
                        holder2.tv_college_one.setTextColor(ContextCompat.getColor(context, R.color.silver));
                        holder2.tv_college_two.setTextColor(ContextCompat.getColor(context, R.color.gold));
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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (arrayList != null) {
            ItemMatch object = arrayList.get(position);
            if (object != null) {
                return object.getMatchType();
            }
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
