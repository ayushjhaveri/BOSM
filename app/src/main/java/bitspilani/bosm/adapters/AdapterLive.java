package bitspilani.bosm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.actions.ItemListIntents;
import com.like.IconType;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;

import bitspilani.bosm.CartActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemCart;
import bitspilani.bosm.items.ItemLive;
import bitspilani.bosm.items.ItemMatch;
import bitspilani.bosm.utils.Constant;

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
//        holder.tv_type.setText(itemLive.getMatch_type());
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

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemWalletHistory;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Prashant on 4/5/2018.
 */

public class AdapterLive extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<ItemLive> arrayList;
    private LayoutInflater inflater;
    private Context context;

    public AdapterLive(Context context, ArrayList<ItemLive> arrayList) {
        inflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = arrayList.get(position).getItemType();
        final ItemLive itemLive = arrayList.get(position);

        switch(type){
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

                liveViewHolder.tv_college1.setText(itemLive.getCollegeName1());
                liveViewHolder.tv_college2.setText(itemLive.getCollegeName2());
                liveViewHolder.tv_time_date.setText(itemLive.getMatch_date() + " - " + itemLive.getMatch_time());
                liveViewHolder.tv_score1.setText(itemLive.getScore1());
                liveViewHolder.tv_score2.setText(itemLive.getScore2());
                liveViewHolder.tv_type.setText(itemLive.getMatch_type());
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
                    }else if(itemLive.getIsVote()==2){
                        liveViewHolder.iv_vote1.setLiked(false);
                        liveViewHolder.iv_vote2.setLiked(true);
                    }
                }else{
                    liveViewHolder.iv_vote1.setEnabled(true);
                    liveViewHolder.iv_vote2.setEnabled(true);

                    liveViewHolder.iv_vote1.setLiked(false);
                    liveViewHolder.iv_vote2.setLiked(false);
                }



                liveViewHolder.iv_vote1.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        liveViewHolder.rl_vote_one.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                                ,itemLive.getVote1()+1));
                        liveViewHolder.rl_vote_two.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                itemLive.getVote2()));

                        liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                        liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_one.setVisibility(View.VISIBLE);
                        liveViewHolder.rl_vote_two.setVisibility(View.VISIBLE);


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
                                liveViewHolder.tv_vote1.setText(Integer.toString(itemLive.getVote1()+1));
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
                                liveViewHolder.tv_vote2.setText(itemLive.getVote2()+"");
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        liveViewHolder.iv_vote1.setEnabled(false);
                        liveViewHolder.iv_vote2.setEnabled(false);
                        itemLive.setIsVote(1);

                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {

                    }
                });


                liveViewHolder.iv_vote2.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        liveViewHolder.rl_vote_one.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                                ,itemLive.getVote1()));
                        liveViewHolder.rl_vote_two.setLayoutParams(new LinearLayout.LayoutParams(0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                itemLive.getVote2()+1));

                        liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                        liveViewHolder.tv_vote1.setVisibility(View.INVISIBLE);
                        liveViewHolder.rl_vote_one.setVisibility(View.VISIBLE);
                        liveViewHolder.rl_vote_two.setVisibility(View.VISIBLE);



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
                                liveViewHolder.tv_vote1.setText(Integer.toString(itemLive.getVote1()));
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
                                liveViewHolder.tv_vote2.setText(Integer.toString(itemLive.getVote2()+1));
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        liveViewHolder.iv_vote1.setEnabled(false);
                        liveViewHolder.iv_vote2.setEnabled(false);
                        itemLive.setIsVote(2);

                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {

                    }
                });


                break;
            case 1:

                TrendingViewHolder holder;
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

                    convertView.setTag(holder);
                } else {
                    holder = (TrendingViewHolder) convertView.getTag();
                }

                //use here

                holder.tv_college1.setText(itemLive.getCollegeName1());
                holder.tv_college2.setText(itemLive.getCollegeName2());
                holder.tv_time.setText(itemLive.getMatch_time());
                holder.tv_date.setText(itemLive.getMatch_date());
                holder.tv_type.setText(itemLive.getMatch_type());
                holder.tv_sport.setText(itemLive.getSport_name());
                holder.tv_venue.setText(itemLive.getVenue());

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
            convertView = inflater.inflate(R.layout.layout_live_row_header, parent, false);
            holder.header_text = (TextView) convertView.findViewById(R.id.tv_header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        if (arrayList.get(position).getItemType() == 0) {
            holder.header_text.setText("Live");
        } else if (arrayList.get(position).getItemType() == 1) {
            holder.header_text.setText("Trending");
        }

        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");

        holder.header_text.setTypeface(oswald_regular);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position).getItemType();
    }
    

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
//        String text = dateFormat.format(arrayList.get(position).getDate().getTime());
        return arrayList.get(position).getItemType();
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
    }


}



