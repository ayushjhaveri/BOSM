package bitspilani.bosm.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewtooltip.ViewTooltip;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

//import bitspilani.bosm.CurrentSportActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.fragments.CurrentSportFragment;
import bitspilani.bosm.fragments.MapFragment;
import bitspilani.bosm.items.ItemMatch;
import bitspilani.bosm.utils.Constant;

import static bitspilani.bosm.HomeActivity.getDayOfMonthSuffix;
import static bitspilani.bosm.HomeActivity.loadFrag;
import static bitspilani.bosm.HomeActivity.toTitleCase;
import static com.github.florent37.viewtooltip.ViewTooltip.ALIGN.CENTER;
//import static bitspilani.bosm.fragments.CurrentSportFragment.viewLoader;

/**
 * Created by Prashant on 4/7/2018.
 */

public class AdapterCurrentSport extends FirestoreAdapter2<RecyclerView.ViewHolder> {

    private Context context;
    private static final String TAG = "AdapterCart";



//    public static String hash;
    ProgressBar progressBar;
    public AdapterCurrentSport(Context context, Query query , ProgressBar progressBar) {
        super(query);
        this.context = context;
//        hash = "";
        this.progressBar = progressBar;
//        arrayList = new ArrayList<>();
    }


    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        ArrayList<ItemMatch> arrayList = getMatchArray();

        for(int i=0;i<arrayList.size();i++){
            if(Calendar.getInstance().get(Calendar.DATE) == arrayList.get(i).getCalendar().get(Calendar.DATE)){
                //         Log.d(TAG,"dSDSADASDSADSADSADASD" + position);
                CurrentSportFragment.recyclerView.smoothScrollToPosition(i+1);
                break;
            }
        }
//        CurrentSportFragment.viewLoader(false);

        progressBar.setVisibility(View.GONE);
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


//        DocumentSnapshot document =  getSnapshot(position);

//        Log.d(TAG,document.getData().toString()+" gfd");

//        Timestamp timestamp  = document.contains("timestamp")?(Timestamp) document.getData().get("timestamp"):Timestamp.now();
//        Date date = timestamp.toDate();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d");
//        SimpleDateFormat smf = new SimpleDateFormat("MMM");
//        SimpleDateFormat stf = new SimpleDateFormat("h.mm a");
//        SimpleDateFormat smf2 = new SimpleDateFormat("MMM");



//        int matchType = document.contains("match_type")?Integer.parseInt(document.getData().get("match_type").toString()):1;
//        boolean isResult = document.contains("is_result")?Boolean.parseBoolean(document.getData().get("is_result").toString()):false;

        ArrayList<ItemMatch> arrayList = getMatchArray();
        final ItemMatch itemMatch = arrayList.get(position);

        SimpleDateFormat smf2 = new SimpleDateFormat("MMM");
        switch (itemMatch.getMatchType()) {
            case Constant.ATHLETIC_TYPE_MATCH:

//                if(isResult) {
//                    itemMatch = new ItemMatch(
//                           0,
//                            document.contains("sport_name")?document.getData().get("sport_name").toString():"",
//                            document.contains("venue")?document.getData().get("venue").toString():"",
//                            stf.format(date),
//                            sdf.format(date)+getDayOfMonthSuffix(cal.get(Calendar.DATE))+" " + smf.format(date),
//                            document.contains("round")?toTitleCase(document.getData().get("round").toString()):"",
//                            document.contains("goldName")?document.getData().get("goldName").toString():"",
//                            document.contains("silverName")?document.getData().get("silverName").toString():"",
//                            document.contains("bronzeName")? document.getData().get("bronzeName").toString():"",
//                            document.contains("goldRecord")? document.getData().get("goldRecord").toString():"",
//                            document.contains("silverRecord")?document.getData().get("silverRecord").toString():"",
//                            document.contains("bronzeRecord")?document.getData().get("bronzeRecord").toString():""
//                            );
//                }else{
//                    itemMatch = new ItemMatch(
//                            0,
//                            document.contains("sport_name")?document.getData().get("sport_name").toString():"",
//                            document.contains("venue")?document.getData().get("venue").toString():"",
//                            stf.format(date),
//                            sdf.format(date)+getDayOfMonthSuffix(cal.get(Calendar.DATE))+" " + smf.format(date),
//                            document.contains("round")?toTitleCase(document.getData().get("round").toString()):""
//                    );
//                }
////
//                if(!hash.equals(""+cal.get(Calendar.DATE))){
//                    itemMatch.setHeader(true);
//                    hash = ""+cal.get(Calendar.DATE);
//                }else{
//                    itemMatch.setHeader(false);
//                }

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
                holder1.tv_date.setText(itemMatch.getCalendar().get(Calendar.DATE)
                        +getDayOfMonthSuffix(itemMatch.getCalendar().get(Calendar.DATE))+
                        " "+smf2.format(itemMatch.getCalendar().getTime()));

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
//                    if(Calendar.getInstance().get(Calendar.DATE) == itemMatch.getCalendar().get(Calendar.DATE)){
//                        Log.d(TAG,"dSDSADASDSADSADSADASD" + position);
//                        CurrentSportFragment.recyclerView.smoothScrollToPosition(position+1);
//                    }
                    holder1.rl_top.setVisibility(View.VISIBLE);
                    holder1.tv_sort_title.setText(itemMatch.getDate());
                }
                //for
                break;

            case Constant.TEAM_MATCH:
//                if(isResult) {
//                    itemMatch = new ItemMatch(
//                            1,
//                            document.contains("sport_name")?document.getData().get("sport_name").toString():"",
//                            document.contains("venue")?document.getData().get("venue").toString():"",
//                            stf.format(date),
//                            sdf.format(date)+getDayOfMonthSuffix(cal.get(Calendar.DATE))+" " + smf.format(date),
//                            document.contains("round")?toTitleCase(document.getData().get("round").toString()):"",
//                            document.contains("score1")?document.getData().get("score1").toString():"",
//                            document.contains("score2")?document.getData().get("score2").toString():"",
//                            document.contains("college1")?document.getData().get("college1").toString():"",
//                            document.contains("college2")?document.getData().get("college2").toString():"",
//                            document.contains("winner")?Integer.parseInt(document.getData().get("winner").toString()):0,
//                            document.contains("full_college1")?toTitleCase((String)document.getData().get("full_college1")):"",
//                            document.contains("full_college2")?toTitleCase((String)document.getData().get("full_college2")):""
//                    );
//                }else{
//                    itemMatch = new ItemMatch(
//                           1,
//                            document.contains("sport_name")?document.getData().get("sport_name").toString():"",
//                            document.contains("venue")?document.getData().get("venue").toString():"",
//                            stf.format(date),
//                            sdf.format(date)+getDayOfMonthSuffix(cal.get(Calendar.DATE))+" " + smf.format(date),
//                            document.contains("round")?toTitleCase(document.getData().get("round").toString()):"",
//                            document.contains("college1")?document.getData().get("college1").toString():"",
//                            document.contains("college2")?document.getData().get("college2").toString():"",
//                            document.contains("full_college1")?toTitleCase((String)document.getData().get("full_college1")):"",
//                            document.contains("full_college2")?toTitleCase((String)document.getData().get("full_college2")):""
//                    );
//                }


//                String q =
//                for(int i=0;i<documentSnapshots.size();i++){
//                    DocumentSnapshot d = documentSnapshots.get(i);
//                    if()
//                }
//                if(!hash.equals(""+cal.get(Calendar.DATE))){
//                    itemMatch.setHeader(true);
//                    hash = ""+cal.get(Calendar.DATE);
//                }else{
//                    itemMatch.setHeader(false);
//                }

                final TeamViewHolder holder2 = (TeamViewHolder) holder;
                holder2.tv_sort_title.setText(itemMatch.getDate());
                holder2.tv_subtitle.setText(itemMatch.getType());
                holder2.tv_time.setText(itemMatch.getTime());
                holder2.tv_venue.setText(itemMatch.getVenue());
                holder2.tv_college_one.setText(itemMatch.getCollege1());
                holder2.tv_college_two.setText(itemMatch.getCollege2());
                holder2.tv_score_one.setText(itemMatch.getScore1());
                holder2.tv_score_two.setText(itemMatch.getScore2());
                holder2.tv_date.setText(itemMatch.getCalendar().get(Calendar.DATE)
                        +getDayOfMonthSuffix(itemMatch.getCalendar().get(Calendar.DATE))+
                        " "+smf2.format(itemMatch.getCalendar().getTime()));

                holder2.tv_college_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        ViewTooltip
                                .on(holder2.tv_college_one)
                                .autoHide(true, 2000)
                                .corner(30)
                                .color(ContextCompat.getColor(context,R.color.back_shade2))
                                .align(CENTER)
                                .position(ViewTooltip.Position.TOP)
                                .text(itemMatch.getFullCollege1())
                                .show();
                    }
                });

                holder2.tv_college_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        ViewTooltip
                                .on(holder2.tv_college_two)
                                .autoHide(true, 2000)
                                .corner(30)
                                .color(ContextCompat.getColor(context,R.color.back_shade2))
                                .align(CENTER)
                                .position(ViewTooltip.Position.TOP)
                                .text(itemMatch.getFullCollege2())
                                .show();
                    }
                });

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


                holder.itemView.findViewById(R.id.iv_map).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW,
//                                Uri.parse(map_nav_url));
//                        context.startActivity(intent);
                    }
                });

                break;
        }


    }

//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }


    @Override
    public int getItemViewType(int position) {
//        DocumentSnapshot document =  getSnapshot(position);
//        if (document != null && document.exists()) {
//            Log.d("aaaaaa",document.getId()+"ffgfg");
           return getMatchArray().get(position).getMatchType();
//            ItemMatch object = arrayList.get(position);
//            if (object != null) {
//                return object.getMatchType();
//            }
//        }
//        return 0;
    }

    public class AthleticViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_top, rl_college;
        TextView tv_sort_title, tv_subtitle;
        TextView tv_gold_name, tv_gold_score;
        TextView tv_silver_name, tv_silver_score;
        TextView tv_bronze_name, tv_bronze_score;
        TextView tv_time, tv_venue;
        TextView tv_date;

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
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);

        }
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_top;
        TextView tv_sort_title, tv_subtitle;
        TextView tv_college_one, tv_score_one;
        TextView tv_college_two, tv_score_two;
        TextView tv_time, tv_venue;
        LinearLayout ll_score;
        TextView tv_date;

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
            tv_date=(TextView)itemView.findViewById(R.id.tv_date);

        }
    }

}
