package bitspilani.bosm.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.florent37.viewtooltip.ViewTooltip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.io.LineReader;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.internal.InternalTokenProvider;
import com.like.IconType;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import bitspilani.bosm.R;
import bitspilani.bosm.fragments.SportFragment;
import bitspilani.bosm.items.ItemLive;

/**
 * Created by Ayush on 9/8/2018.
 */
//
//public class AdapterLive extends RecyclerView.Adapter<AdapterLive.ViewHolder> {
//
//    private ArrayList<ItemLive> arrayList;
//    private Context context;
//
//    private static final String TAG = "AdapterCart";
//
//    public AdapterLive(Context context, ArrayList<ItemLive> arrayList) {
//        this.arrayList = arrayList;
//        this.context = context;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_live, parent, false);
//
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        final ItemLive itemLive= arrayList.get(position);
//
//        holder.tv_college1.setText(itemLive.getCollegeName1());
//        holder.tv_college2.setText(itemLive.getCollegeName2());
//        holder.tv_time_date.setText(itemLive.getMatch_date()+" - "+itemLive.getMatch_time());
//        holder.tv_score1.setText(itemLive.getScore1());
//        holder.tv_score2.setText(itemLive.getScore2());
//        holder.tv_type.setText(itemLive.getMatch_round());
//        holder.tv_sport.setText(itemLive.getSport_name());
//        holder.tv_venue.setText(itemLive.getVenue());
//
//        holder.iv_vote1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                holder.tv_vote1.setText((itemLive.getVote1()+1));
////                holder.tv_vote2.setText(itemLive.getVote2());
//                holder.tv_vote1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        (float) itemLive.getVote1()));
//                holder.tv_vote1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        (float)itemLive.getVote2()));
//                holder.tv_vote1.setVisibility(View.VISIBLE);
//                holder.tv_vote2.setVisibility(View.VISIBLE);
//
//            }
//        });
//        holder.iv_vote2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView tv_college1, tv_college2, tv_venue, tv_time_date, tv_sport, tv_type, tv_score1, tv_score2,tv_vote1, tv_vote2;
//        ImageView iv_vote1, iv_vote2;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            tv_college1 = (TextView) itemView.findViewById(R.id.tv_college_one);
//            tv_college2 = (TextView) itemView.findViewById(R.id.tv_college_two);
//            tv_venue = (TextView) itemView.findViewById(R.id.tv_venue);
//            tv_score1 = (TextView) itemView.findViewById(R.id.tv_score_one);
//            tv_score2 = (TextView) itemView.findViewById(R.id.tv_score_two);
////            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
//            tv_time_date =(TextView) itemView.findViewById(R.id.tv_date_time);
//            tv_sport  = (TextView) itemView.findViewById(R.id.tv_sport);
//            tv_type =(TextView)itemView.findViewById(R.id.tv_round);
//            tv_vote1=(TextView)itemView.findViewById(R.id.tv_vote_one);
//            tv_vote2=(TextView)itemView.findViewById(R.id.tv_vote_two);
//            iv_vote1=(ImageView)itemView.findViewById(R.id.iv_vote1);
//            iv_vote2=(ImageView)itemView.findViewById(R.id.iv_vote2);
//
//        }
//    }
//
//
//}

import android.widget.BaseAdapter;
import android.widget.Toast;

import org.w3c.dom.Document;

import javax.annotation.Nullable;

import bitspilani.bosm.utils.Constant;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static bitspilani.bosm.HomeActivity.getDayOfMonthSuffix;
import static bitspilani.bosm.HomeActivity.toTitleCase;
import static com.android.volley.VolleyLog.TAG;
import static com.github.florent37.viewtooltip.ViewTooltip.ALIGN.CENTER;

/**
 * Created by Prashant on 4/5/2018.
 */

public class AdapterLive extends BaseAdapter implements StickyListHeadersAdapter, EventListener<QuerySnapshot> {

    private Query mQuery;
    private ProgressBar progressBar;
    private ListenerRegistration mRegistration;

    private ArrayList<DocumentSnapshot> mSnapshots = new ArrayList<>();

    private ArrayList<Integer> arrayListAdapter = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context;
    private  RelativeLayout rl_filled, rl_empty;

    public AdapterLive(Context context, Query query,ProgressBar progressBar, RelativeLayout rl_filled, RelativeLayout rl_empty) {
        inflater = LayoutInflater.from(context);
        this.mQuery = query;
        this.context = context;
        this.progressBar = progressBar;
        this.rl_empty = rl_empty;
        this.rl_filled=rl_filled;
    }

    private void getArrayFromSnapshot(){
        arrayListAdapter.clear();
        int count =0;
        for(int i=0;i<mSnapshots.size();i++){
            DocumentSnapshot document  = mSnapshots.get(i);
            Timestamp timestamp  = document.contains("timestamp")?(Timestamp) document.getData().get("timestamp"):Timestamp.now();
            if(timestamp.compareTo(Timestamp.now())>0 && count <10){
                arrayListAdapter.add(i);
                count++;
            }
        }
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
        getArrayFromSnapshot();
        notifyDataSetChanged();
    }

    public void setQuery(Query query) {
        // Stop listening
        stopListening();

        // Clear existing data
        mSnapshots.clear();
        getArrayFromSnapshot();
        notifyDataSetChanged();

        // Listen to new query
        mQuery = query;
        startListening();
    }

    @Override
    public int getCount() {
        return arrayListAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListAdapter.get(position);
    }

    protected void onDocumentAdded(DocumentChange change) {

        mSnapshots.add(change.getNewIndex(), change.getDocument());
        getArrayFromSnapshot();
        notifyDataSetChanged();
//        notifyItemInserted(change.getNewIndex());
    }

    protected void onDocumentModified(DocumentChange change) {
        if (change.getOldIndex() == change.getNewIndex()) {
            // Item changed but remained in same position
            mSnapshots.set(change.getOldIndex(), change.getDocument());
            getArrayFromSnapshot();
            notifyDataSetChanged();
//            notifyItemChanged(change.getOldIndex());
        } else {
            // Item changed and changed position
            mSnapshots.remove(change.getOldIndex());
            mSnapshots.add(change.getNewIndex(), change.getDocument());
            getArrayFromSnapshot();
            notifyDataSetChanged();
//            notifyItemMoved(change.getOldIndex(), change.getNewIndex());
        }
    }

    protected void onDocumentRemoved(DocumentChange change) {
        mSnapshots.remove(change.getOldIndex());
//        notifyItemRemoved(change.getOldIndex());
        getArrayFromSnapshot();
        notifyDataSetChanged();
    }

    protected void onError(FirebaseFirestoreException e) {
        Log.w(TAG, "onError", e);
    };

    protected void onDataChanged() {

        progressBar.setVisibility(View.GONE);
        rl_empty.setVisibility(View.GONE);
        rl_filled.setVisibility(View.VISIBLE);
    }

    ItemLive itemLive ;
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final DocumentSnapshot document =  mSnapshots.get(arrayListAdapter.get(position));

//        holder.icon.setImageResource(SportFragment.iconHash.get(itemSport.getSport_id()));

        Log.d("aaaaaa",document.getData().toString());

        int ITEM_TYPE = Integer.parseInt(document.getData().get("item_type").toString());
        switch(ITEM_TYPE){

            case 0:


                final LiveViewHolder liveViewHolder;
                if (convertView == null) {
                    liveViewHolder = new LiveViewHolder();
                    convertView = inflater.inflate(R.layout.row_live, parent, false);

                    liveViewHolder.tv_college1 = (TextView) convertView.findViewById(R.id.tv_college_one);
                    liveViewHolder.tv_college2 = (TextView) convertView.findViewById(R.id.tv_college_two);
                    liveViewHolder.tv_venue = (TextView) convertView.findViewById(R.id.tv_venue);
                    liveViewHolder.tv_score1 = (TextView) convertView.findViewById(R.id.tv_score_one);
                    liveViewHolder.tv_score2 = (TextView) convertView.findViewById(R.id.tv_score_two);
//            progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
                    liveViewHolder.tv_time_date = (TextView) convertView.findViewById(R.id.tv_date_time);
                    liveViewHolder.tv_sport = (TextView) convertView.findViewById(R.id.tv_sport);
                    liveViewHolder.tv_type = (TextView) convertView.findViewById(R.id.tv_round);
                    liveViewHolder.tv_vote1 = (TextView) convertView.findViewById(R.id.tv_vote_one);
                    liveViewHolder.tv_vote2 = (TextView) convertView.findViewById(R.id.tv_vote_two);
                    liveViewHolder.iv_vote1 = (LikeButton) convertView.findViewById(R.id.iv_vote1);
                    liveViewHolder.iv_vote2 = (LikeButton) convertView.findViewById(R.id.iv_vote2);
                    liveViewHolder.ll_vote = (LinearLayout)convertView.findViewById(R.id.ll_vote);
                    liveViewHolder.rl_vote_one=(RelativeLayout)convertView.findViewById(R.id.rl_vote_one);
                    liveViewHolder.rl_vote_two=(RelativeLayout)convertView.findViewById(R.id.rl_vote_two);
                    liveViewHolder.container = (ShimmerFrameLayout)convertView.findViewById(R.id.shimmer_view_container);



                    convertView.setTag(liveViewHolder);
                } else {
                    liveViewHolder = (LiveViewHolder) convertView.getTag();
                }
//
//                liveViewHolder.iv_vote1.setLikeDrawableRes(R.drawable.icon_like);
//                liveViewHolder.iv_vote1.setUnlikeDrawableRes(R.drawable.icon_like);
//                liveViewHolder.iv_vote2.setLikeDrawableRes(R.drawable.icon_like);
//                liveViewHolder.iv_vote2.setUnlikeDrawableRes(R.drawable.icon_like);


                //use here
                Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/SawarabiMincho-Regular.ttf");
                liveViewHolder.tv_type.setTypeface(oswald_regular);
//                liveViewHolder.container.startShimmer(); // If auto-start is set to false



                Timestamp timestamp  = document.contains("timestamp")?(Timestamp) document.getData().get("timestamp"):Timestamp.now();
                Date date = timestamp.toDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                String month_format = "MMM";
                SimpleDateFormat sdf_month = new SimpleDateFormat(month_format);
                String time_format = "h.mm a";
                SimpleDateFormat sdf_time = new SimpleDateFormat(time_format);

                int id = 0;
                String sport_name="";
                String college1 = "", college2="",round="",venue="",score1="",score2="",full_college1="",full_college2="";
                int vote1=0, vote2=0,isVote =0;
                try{
                    id =   Integer.parseInt(document.getData().get("sport_id").toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    sport_name =   toTitleCase((String)document.getData().get("sport_name"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    college1 =   ((String)document.getData().get("college1")).toUpperCase();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    college2 =   ((String)document.getData().get("college2")).toUpperCase();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    round =   toTitleCase((String)document.getData().get("round"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    venue =   (String)document.getData().get("venue");
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    score1 =   (String)document.getData().get("score1");
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    score2 =    (String)document.getData().get("score2");
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    full_college1 =   toTitleCase((String)document.getData().get("full_college1"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    full_college2 =   toTitleCase((String)document.getData().get("full_college2"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    vote1 =    Integer.parseInt(document.getData().get("vote1").toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    vote2 =   Integer.parseInt(document.getData().get("vote2").toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    isVote =  Integer.parseInt(document.getData().get("is_vote").toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                itemLive = new ItemLive(
                        0,
                        id,
                        sport_name,
                        college1,
                        college2,
                        round,
                        venue,
                        sdf_time.format(cal.getTime()),
                        cal.get(Calendar.DATE)
                                +getDayOfMonthSuffix(cal.get(Calendar.DATE))+
                                " "+sdf_month.format(date)+"",
                        score1,
                        score2,
                        vote1,
                        vote2,
                        isVote,
                        full_college1,
                        full_college2
                );

                liveViewHolder.tv_college1.setText(itemLive.getCollegeName1());
                liveViewHolder.tv_college2.setText(itemLive.getCollegeName2());
                liveViewHolder.tv_time_date.setText(itemLive.getMatch_date() + " - " + itemLive.getMatch_time());
                liveViewHolder.tv_score1.setText(itemLive.getScore1());
                liveViewHolder.tv_score2.setText(itemLive.getScore2());
                liveViewHolder.tv_type.setText(itemLive.getMatch_round());
                liveViewHolder.tv_sport.setText(itemLive.getSport_name());
                liveViewHolder.tv_venue.setText(itemLive.getVenue());

                liveViewHolder.tv_college1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        ViewTooltip
                                .on(liveViewHolder.tv_college1)
                                .autoHide(true, 2000)
                                .corner(30)
                                .color(ContextCompat.getColor(context,R.color.back_shade2))
                                .align(CENTER)
                                .position(ViewTooltip.Position.TOP)
                                .text( document.contains("full_college1")?toTitleCase((String)document.getData().get("full_college1")):"")
                                .show();
                    }
                });

                liveViewHolder.tv_college2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        ViewTooltip
                                .on(liveViewHolder.tv_college2)
                                .autoHide(true, 2000)
                                .corner(30)
                                .color(ContextCompat.getColor(context,R.color.back_shade2))
                                .align(CENTER)
                                .position(ViewTooltip.Position.TOP)
                                .text( document.contains("full_college2")?toTitleCase((String)document.getData().get("full_college2")):"")
                                .show();
                    }
                });




                //customizing vote1 and vote2 icons
//                liveViewHolder.iv_vote1.setIcon(IconType.Thumb);
//                liveViewHolder.iv_vote2.setIcon(IconType.Thumb);


                if(itemLive.getIsVote()>0){
//                    liveViewHolder.iv_vote1.setEnabled(T);
//                    liveViewHolder.iv_vote2.setEnabled(false);

                    if(itemLive.getIsVote()==1){
                        liveViewHolder.iv_vote1.setEnabled(false);
                        liveViewHolder.iv_vote2.setEnabled(true);
                        liveViewHolder.iv_vote1.setLiked(true);
                        liveViewHolder.iv_vote2.setLiked(false);
//                        liveViewHolder.iv_vote1.setLikeDrawableRes(R.drawable.like);
//                        liveViewHolder.iv_vote2.setLikeDrawableRes(R.drawable.dislike);

                        liveViewHolder.rl_vote_one.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                                , itemLive.getVote1()));
                        liveViewHolder.rl_vote_two.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                itemLive.getVote2()));

                        liveViewHolder.rl_vote_one.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_one.setBackgroundTintList(context.getResources().getColorStateList(R.color.rl_liked));
                        liveViewHolder.rl_vote_two.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_two.setBackgroundTintList(context.getResources().getColorStateList(R.color.rl_unliked));

                        liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                        liveViewHolder.tv_vote1.setText(itemLive.getVote1()+"");

                        liveViewHolder.tv_vote2.setVisibility(View.INVISIBLE);
                        liveViewHolder.tv_vote2.setText(itemLive.getVote2()+"");


                        Animation animSlide_vote1 = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.live_slide_left);
                        liveViewHolder.rl_vote_one.startAnimation(animSlide_vote1);
                        animSlide_vote1.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Animation animFade_vote1 = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.fade);

                                liveViewHolder.tv_vote1.startAnimation(animFade_vote1);
                                liveViewHolder.tv_vote1.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        Animation animSlide_vote2 = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.live_slide_right);
                        liveViewHolder.rl_vote_two.startAnimation(animSlide_vote2);
                        animSlide_vote2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                liveViewHolder.tv_vote2.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {


                                Animation animFade_vote2 = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.fade);
                                liveViewHolder.tv_vote2.startAnimation(animFade_vote2);
                                liveViewHolder.tv_vote2.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });


                        liveViewHolder.rl_vote_one.setVisibility(View.VISIBLE);

//
//                        liveViewHolder.iv_vote1.setEnabled(false);
//                        liveViewHolder.iv_vote2.setEnabled(true);


                    }else if(itemLive.getIsVote()==2){
//
                        liveViewHolder.iv_vote1.setEnabled(true);
                        liveViewHolder.iv_vote2.setEnabled(false);

                        liveViewHolder.iv_vote1.setLiked(false);
                        liveViewHolder.iv_vote2.setLiked(true);
//                        liveViewHolder.iv_vote1.setLikeDrawableRes(R.drawable.dislike);
//                        liveViewHolder.iv_vote2.setLikeDrawableRes(R.drawable.like);


                        liveViewHolder.rl_vote_one.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                                , itemLive.getVote1()));
                        liveViewHolder.rl_vote_two.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                itemLive.getVote2()));

                        liveViewHolder.rl_vote_one.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_one.setBackgroundTintList(context.getResources().getColorStateList(R.color.rl_unliked));
                        liveViewHolder.rl_vote_two.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_two.setBackgroundTintList(context.getResources().getColorStateList(R.color.rl_liked));

                        liveViewHolder.tv_vote1.setText(itemLive.getVote1()+"");
                        liveViewHolder.tv_vote2.setText(itemLive.getVote2()+"");

                        Animation animSlide_vote1 = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.live_slide_left);

                        liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                        liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_one.setVisibility(View.VISIBLE);
                        liveViewHolder.rl_vote_two.setVisibility(View.VISIBLE);
                        liveViewHolder.rl_vote_one.startAnimation(animSlide_vote1);
                        animSlide_vote1.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Animation animFade_vote1 = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.fade);

                                liveViewHolder.tv_vote1.startAnimation(animFade_vote1);
                                liveViewHolder.tv_vote1.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        Animation animSlide_vote2 = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.live_slide_right);
                        liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                        liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_one.setVisibility(View.VISIBLE);
                        liveViewHolder.rl_vote_two.setVisibility(View.VISIBLE);
                        liveViewHolder.rl_vote_two.startAnimation(animSlide_vote2);
                        animSlide_vote2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                liveViewHolder.tv_vote2.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {


                                Animation animFade_vote2 = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.fade);
                                liveViewHolder.tv_vote2.startAnimation(animFade_vote2);
                                liveViewHolder.tv_vote2.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

//                        liveViewHolder.iv_vote1.setEnabled(true);
//                        liveViewHolder.iv_vote2.setEnabled(false);

                    }
                }else{
                    liveViewHolder.iv_vote1.setEnabled(true);
                    liveViewHolder.iv_vote2.setEnabled(true);

                    liveViewHolder.iv_vote1.setLiked(false);
                    liveViewHolder.iv_vote2.setLiked(false);

                    liveViewHolder.rl_vote_one.setVisibility(View.INVISIBLE);
                    liveViewHolder.rl_vote_two.setVisibility(View.INVISIBLE);

                }



                final ItemLive finalitem = itemLive;
                liveViewHolder.iv_vote1.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
//                        Toast.makeText(context, "previous vote : "+itemLive.getIsVote(), Toast.LENGTH_SHORT).show();
                        DocumentReference documentReference = document.getReference();

                        if(itemLive.getIsVote() == 2){

                            int new_vote2 = finalitem.getVote2()-1;
                            documentReference.update("vote2", new_vote2);
                        }

                        int new_vote1 = finalitem.getVote1()+1;
                        documentReference.update("is_vote", 1);
                        documentReference.update("vote1",new_vote1)
                                .addOnSuccessListener(new OnSuccessListener< Void >() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Updated Successfully",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {

                    }
                });


                liveViewHolder.iv_vote2.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {


                        DocumentReference documentReference = document.getReference();

                        if(itemLive.getIsVote() == 1){
                            int new_vote1 = finalitem.getVote1()-1;
                            documentReference.update("vote1", new_vote1);
                        }

                        int new_vote2 = finalitem.getVote2() + 1;
                        documentReference.update("is_vote", "2");
                        documentReference.update("vote2", new_vote2)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Updated Successfully",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {

                    }
                });


                break;
            case 1:

                final TrendingViewHolder holder;
                if (convertView == null) {
                    holder = new TrendingViewHolder();
                    convertView = inflater.inflate(R.layout.row_trending, parent, false);

                    holder.tv_college1 = (TextView) convertView.findViewById(R.id.tv_college_one);
                    holder.tv_college2 = (TextView) convertView.findViewById(R.id.tv_college_two);
                    holder.tv_venue = (TextView) convertView.findViewById(R.id.tv_venue);
//            progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
                    holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                    holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                    holder.tv_sport = (TextView) convertView.findViewById(R.id.tv_sport);
                    holder.tv_type = (TextView) convertView.findViewById(R.id.tv_subtitle);
                    holder.ll_college = (RelativeLayout) convertView.findViewById(R.id.ll_college);
                    holder.iv_sport = (ImageView)convertView.findViewById(R.id.iv_sport);
                    holder.iv_map = (ImageView)convertView.findViewById(R.id.iv_map);

                    convertView.setTag(holder);
                } else {
                    holder = (TrendingViewHolder) convertView.getTag();
                }

                timestamp  = document.contains("timestamp")?(Timestamp) document.getData().get("timestamp"):Timestamp.now();
                date = timestamp.toDate();
                cal = Calendar.getInstance();
                cal.setTime(date);
                Date date2 = timestamp.toDate();
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(date);
                String month_format2 = "MMM";
                SimpleDateFormat sdf_month2 = new SimpleDateFormat(month_format2);
                String time_format2 = "h.mm a";
                SimpleDateFormat sdf_time2 = new SimpleDateFormat(time_format2);

                if (document.contains("match_type")?Integer.parseInt(document.getData().get("match_type").toString())==1:true){
                itemLive = new ItemLive(
                        1,
                        document.contains("sport_id")?Integer.parseInt(document.getData().get("sport_id").toString()):0,
                        document.contains("sport_name")?toTitleCase((String)document.getData().get("sport_name")):"",
                        document.contains("college1")?((String)document.getData().get("college1")).toUpperCase():"",
                        document.contains("college2")?((String)document.getData().get("college2")).toUpperCase():"",
                        document.contains("round")?toTitleCase((String)document.getData().get("round")):"",
                        document.contains("venue")?(String)document.getData().get("venue"):"",
                        sdf_time2.format(cal2.getTime()),
                        cal2.get(Calendar.DATE)
                                +getDayOfMonthSuffix(cal2.get(Calendar.DATE))+
                                " "+sdf_month2.format(date2)+"",
                        document.contains("full_college1")?toTitleCase((String)document.getData().get("full_college1")):"",
                         document.contains("full_college2")?toTitleCase((String)document.getData().get("full_college2")):""
                );
                }else{

                    itemLive = new ItemLive(
                            1,
                            document.contains("sport_id")?Integer.parseInt(document.getData().get("sport_id").toString()):0,
                            document.contains("sport_name")?toTitleCase((String)document.getData().get("sport_name")):"",
                            document.contains("round")?toTitleCase((String)document.getData().get("round")):"",
                            document.contains("venue")?(String)document.getData().get("venue"):"",
                            sdf_time2.format(cal2.getTime()),
                            cal2.get(Calendar.DATE)
                                    +getDayOfMonthSuffix(cal2.get(Calendar.DATE))+
                                    " "+sdf_month2.format(date2)+""
                    );
                }


                holder.tv_time.setText(itemLive.getMatch_time());
                holder.tv_date.setText(itemLive.getMatch_date());
                holder.tv_type.setText(itemLive.getMatch_round());
                holder.tv_sport.setText(itemLive.getSport_name());
                holder.tv_venue.setText(itemLive.getVenue());
                holder.iv_sport.setImageResource(SportFragment.iconHash.get(itemLive.getSport_id()));

                holder.iv_map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("venue").document(itemLive.getVenue()).get().addOnCompleteListener(
                                new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if(task.isSuccessful()){
                                            String latitude = task.getResult().contains("latitude")?task.getResult().getData().get("latitude").toString():"28.363821";
                                            String longitude = task.getResult().contains("longitude")?task.getResult().getData().get("latitude").toString():"75.587029";
                                            final String map_nav_url="http://maps.google.com/maps?daddr="+latitude+","+longitude+"";

                                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse(map_nav_url));
                                            context.startActivity(intent);
                                        }else{
                                            String latitude = "28.363821";
                                            String longitude = "75.587029";
                                            final String map_nav_url="http://maps.google.com/maps?daddr="+latitude+","+longitude+"";

                                            Intent intent = new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse(map_nav_url));
                                            context.startActivity(intent);
                                        }
                                    }
                                }
                        );
//                        Intent intent = new Intent(Intent.ACTION_VIEW,
//                                Uri.parse(map_nav_url));
//                        context.startActivity(intent);
                    }
                });


                if(itemLive.getMatchType() == Constant.ATHLETIC_TYPE_MATCH){
                    holder.ll_college.setVisibility(View.GONE);
                }else if (itemLive.getMatchType() == Constant.TEAM_MATCH){
                    holder.tv_college1.setText(itemLive.getCollegeName1());
                    holder.tv_college2.setText(itemLive.getCollegeName2());
                    holder.ll_college.setVisibility(View.VISIBLE);
                    holder.tv_college1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ViewTooltip
                                    .on(holder.tv_college1)
                                    .autoHide(true, 2000)
                                    .corner(30)
                                    .color(ContextCompat.getColor(context,R.color.back_shade2))
                                    .align(CENTER)
                                    .position(ViewTooltip.Position.TOP)
                                    .text(document.contains("full_college1")?toTitleCase((String)document.getData().get("full_college1")):"")
                                    .show();
                        }
                    });

                    holder.tv_college2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            ViewTooltip
                                    .on(holder.tv_college2)
                                    .autoHide(true, 2000)
                                    .corner(30)
                                    .color(ContextCompat.getColor(context,R.color.back_shade2))
                                    .align(CENTER)
                                    .position(ViewTooltip.Position.TOP)
                                    .text(
                                            document.contains("full_college2")?toTitleCase((String)document.getData().get("full_college2")):"")
                                    .show();
                        }
                    });



                }


            break;
        }


        return convertView;
    }



    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        //Inflating row header
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.row_live_header, parent, false);
            holder.header_text = (TextView) convertView.findViewById(R.id.tv_header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        DocumentSnapshot document =  mSnapshots.get(arrayListAdapter.get(position));
//        Timestamp timestamp  = (Timestamp) document.getData().get("timestamp");
//        Date date = timestamp.toDate();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//
        int ITEM_TYPE =  Integer.parseInt(document.getData().get("item_type").toString());

        if (ITEM_TYPE == 0) {
            holder.header_text.setText("LIVE");
        } else if (ITEM_TYPE == 1) {
            holder.header_text.setText("UPCOMING");
        }

        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/RobotoCondensed-Bold.ttf");

        holder.header_text.setTypeface(oswald_regular);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        DocumentSnapshot document =  mSnapshots.get(arrayListAdapter.get(position));

        return Integer.parseInt(document.getData().get("item_type").toString());
    }
//

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
//        String text = dateFormat.format(arrayList.get(position).getDate().getTime());
        DocumentSnapshot document =  mSnapshots.get(arrayListAdapter.get(position));

        return Integer.parseInt(document.getData().get("item_type").toString());
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    class HeaderViewHolder {
        TextView header_text;
    }

    class LiveViewHolder {
        TextView tv_college1, tv_college2, tv_venue, tv_time_date, tv_sport, tv_type, tv_score1, tv_score2, tv_vote1, tv_vote2;
        LikeButton iv_vote1, iv_vote2;
        LinearLayout ll_vote;
        RelativeLayout rl_vote_one, rl_vote_two;
        ShimmerFrameLayout container;

    }

    class TrendingViewHolder {
        TextView tv_college1, tv_college2, tv_venue, tv_time,tv_date, tv_sport, tv_type;
        RelativeLayout ll_college;
        ImageView iv_sport, iv_map;
    }


}



