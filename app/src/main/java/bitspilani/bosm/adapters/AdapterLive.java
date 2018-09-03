package bitspilani.bosm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
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

import javax.annotation.Nullable;

import bitspilani.bosm.utils.Constant;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static bitspilani.bosm.HomeActivity.getDayOfMonthSuffix;
import static bitspilani.bosm.HomeActivity.toTitleCase;
import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Prashant on 4/5/2018.
 */

public class AdapterLive extends BaseAdapter implements StickyListHeadersAdapter, EventListener<QuerySnapshot> {


    private Query mQuery;

    private ListenerRegistration mRegistration;

    private ArrayList<DocumentSnapshot> mSnapshots = new ArrayList<>();

//    private ArrayList<ItemLive> arrayList;
    private LayoutInflater inflater;
    private Context context;

    public AdapterLive(Context context, Query query) {
        inflater = LayoutInflater.from(context);
//        this.arrayList = arrayList;
        this.mQuery = query;
        this.context = context;
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

        final DocumentSnapshot document =  mSnapshots.get(position);

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

                //use here
                Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/SawarabiMincho-Regular.ttf");
                liveViewHolder.tv_type.setTypeface(oswald_regular);
//                liveViewHolder.container.startShimmer(); // If auto-start is set to false



                Timestamp timestamp  = (Timestamp) document.getData().get("timestamp");
                Date date = timestamp.toDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                String month_format = "MMM";
                SimpleDateFormat sdf_month = new SimpleDateFormat(month_format);
                String time_format = "h.mm a";
                SimpleDateFormat sdf_time = new SimpleDateFormat(time_format);

                ItemLive itemLive = new ItemLive(
                        0,
                         Integer.parseInt(document.getData().get("sport_id").toString()),
                        toTitleCase((String)document.getData().get("sport_name")),
                        ((String)document.getData().get("college1")).toUpperCase(),
                        ((String)document.getData().get("college2")).toUpperCase(),
                        toTitleCase((String)document.getData().get("round")),
                        toTitleCase((String)document.getData().get("venue")),
                        sdf_time.format(cal.getTime()),
                        cal.get(Calendar.DATE)
                                +getDayOfMonthSuffix(cal.get(Calendar.DATE))+
                                " "+sdf_month.format(date)+"",
                        (String)document.getData().get("score1"),
                        (String)document.getData().get("score2"),
                        Integer.parseInt(document.getData().get("vote1").toString()),
                        Integer.parseInt(document.getData().get("vote2").toString()),
                        Integer.parseInt(document.getData().get("is_vote").toString())
                );

                liveViewHolder.tv_college1.setText(itemLive.getCollegeName1());
                liveViewHolder.tv_college2.setText(itemLive.getCollegeName2());
                liveViewHolder.tv_time_date.setText(itemLive.getMatch_date() + " - " + itemLive.getMatch_time());
                liveViewHolder.tv_score1.setText(itemLive.getScore1());
                liveViewHolder.tv_score2.setText(itemLive.getScore2());
                liveViewHolder.tv_type.setText(itemLive.getMatch_round());
                liveViewHolder.tv_sport.setText(itemLive.getSport_name());
                liveViewHolder.tv_venue.setText(itemLive.getVenue());

                //customizing vote1 and vote2 icons
                liveViewHolder.iv_vote1.setIcon(IconType.Thumb);
                liveViewHolder.iv_vote2.setIcon(IconType.Thumb);


                if(itemLive.getIsVote()>0){
                    liveViewHolder.iv_vote1.setEnabled(false);
                    liveViewHolder.iv_vote2.setEnabled(false);

                    if(itemLive.getIsVote()==1){
                        liveViewHolder.iv_vote1.setLiked(true);
                        liveViewHolder.iv_vote2.setLiked(false);

                        liveViewHolder.rl_vote_one.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                                , itemLive.getVote1()));
                        liveViewHolder.rl_vote_two.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                itemLive.getVote2()));

                        liveViewHolder.rl_vote_one.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_two.setVisibility(View.INVISIBLE);

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


                        liveViewHolder.iv_vote1.setEnabled(false);
                        liveViewHolder.iv_vote2.setEnabled(false);

                    }else if(itemLive.getIsVote()==2){
                        liveViewHolder.iv_vote1.setLiked(false);
                        liveViewHolder.iv_vote2.setLiked(true);


                        liveViewHolder.rl_vote_one.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                                , itemLive.getVote1()));
                        liveViewHolder.rl_vote_two.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                itemLive.getVote2()));

                        liveViewHolder.rl_vote_one.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_two.setVisibility(View.INVISIBLE);

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

                        liveViewHolder.iv_vote1.setEnabled(false);
                        liveViewHolder.iv_vote2.setEnabled(false);

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

                        int new_vote1 = finalitem.getVote1()+1;
                        DocumentReference documentReference = document.getReference();
                        documentReference.update("is_vote", "1");
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

                        int new_vote2 = finalitem.getVote2() + 1;
                        DocumentReference documentReference = document.getReference();
                        documentReference.update("is_vote", "1");
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
                    holder.ll_college = (LinearLayout) convertView.findViewById(R.id.ll_college);
                    holder.iv_sport = (ImageView)convertView.findViewById(R.id.iv_sport);

                    convertView.setTag(holder);
                } else {
                    holder = (TrendingViewHolder) convertView.getTag();
                }

                timestamp  = (Timestamp) document.getData().get("timestamp");
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

                if (Integer.parseInt(document.getData().get("match_type").toString())==1){
                itemLive = new ItemLive(
                        1,
                        Integer.parseInt(document.getData().get("sport_id").toString()),
                        toTitleCase((String)document.getData().get("sport_name")),
                        ((String)document.getData().get("college1")).toUpperCase(),
                        ((String)document.getData().get("college2")).toUpperCase(),
                        toTitleCase((String)document.getData().get("round")),
                        toTitleCase((String)document.getData().get("venue")),
                        sdf_time2.format(cal2.getTime()),
                        cal2.get(Calendar.DATE)
                                +getDayOfMonthSuffix(cal2.get(Calendar.DATE))+
                                " "+sdf_month2.format(date2)+""
                );
                }else{

                    itemLive = new ItemLive(
                            1,
                            Integer.parseInt(document.getData().get("sport_id").toString()),
                            toTitleCase((String)document.getData().get("sport_name")),
                            toTitleCase((String)document.getData().get("round")),
                            toTitleCase((String)document.getData().get("venue")),
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

                if(itemLive.getMatchType() == Constant.ATHLETIC_TYPE_MATCH){
                    holder.ll_college.setVisibility(View.GONE);
                }else if (itemLive.getMatchType() == Constant.TEAM_MATCH){
                    holder.tv_college1.setText(itemLive.getCollegeName1());
                    holder.tv_college2.setText(itemLive.getCollegeName2());
                    holder.ll_college.setVisibility(View.VISIBLE);
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

        DocumentSnapshot document =  mSnapshots.get(position);
//        Timestamp timestamp  = (Timestamp) document.getData().get("timestamp");
//        Date date = timestamp.toDate();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//
        int ITEM_TYPE =  Integer.parseInt(document.getData().get("item_type").toString());

        if (ITEM_TYPE == 0) {
            holder.header_text.setText("Live");
        } else if (ITEM_TYPE == 1) {
            holder.header_text.setText("Upcoming");
        }

        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");

        holder.header_text.setTypeface(oswald_regular);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        DocumentSnapshot document =  mSnapshots.get(position);

        return Integer.parseInt(document.getData().get("item_type").toString());
    }
//

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
//        String text = dateFormat.format(arrayList.get(position).getDate().getTime());
        DocumentSnapshot document =  mSnapshots.get(position);

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
        LinearLayout ll_college;
        ImageView iv_sport;
    }


}



