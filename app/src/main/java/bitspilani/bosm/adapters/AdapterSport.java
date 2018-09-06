package bitspilani.bosm.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//import bitspilani.bosm.CurrentSportActivity;
import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.fragments.CurrentSportFragment;
import bitspilani.bosm.fragments.SponsorsFragment;
import bitspilani.bosm.fragments.SportFragment;
import bitspilani.bosm.fragments.SportSelectedFragment;
import bitspilani.bosm.utils.Constant;

/**
 * Created by Prashant on 4/7/2018.
 */

public class AdapterSport extends FirestoreAdapter<AdapterSport.ViewHolder> {

    private Context context;
    private ProgressBar progressBar;
    private static final String TAG = "AdapterCart";

    public AdapterSport(Context context, Query query,ProgressBar progressBar) {
        super(query);
        this.progressBar  = progressBar;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sport, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DocumentSnapshot document =  getSnapshot(position);
        final ItemSport itemSport  = new ItemSport(
                Integer.parseInt(document.getId()),
                document.getData().get("sport_name").toString(),
                Boolean.parseBoolean(document.getData().get("is_gender").toString())
        );

        holder.textView_name.setText(itemSport.getName());
        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/Oswald-Regular.ttf");

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            // the view being shared
//            holder.card_name.setTransitionName("transition" + position);
//        }
        holder.icon.setImageResource(SportFragment.iconHash.get(itemSport.getSport_id()));

        holder.notify.setShapeResource(R.raw.star);
        holder.notify.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final View view, boolean checked) {
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic(itemSport.getName().toLowerCase().replace(' ','_'))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context,"Great! You will be notified for each match",Toast.LENGTH_SHORT).show();
                                    }else{
                                        holder.notify.setChecked(false);
                                        Toast.makeText(context,"Server error!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(itemSport.getName().toLowerCase())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
//                                        Toast.makeText(context,"Great! You will be notified for each match",Toast.LENGTH_SHORT).show();
                                    }else{
                                        holder.notify.setChecked(true);
                                        Toast.makeText(context,"Server error!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        holder.textView_name.setTypeface(oswald_regular);
        holder.rl.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Fragment fragment = new SportSelectedFragment();

                Constant.currentSport  = itemSport;
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

//                transaction.setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up);
                transaction.addToBackStack(null);

                transaction.replace(R.id.fl_view, fragment);
                transaction.commit();

//                context.startActivity(new Intent(context, CurrentSportActivity.class));
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        CardView card_name;
        ImageView icon;
        ShineButton notify;
        RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.tv_name);
            card_name= (CardView)itemView.findViewById(R.id.card_name);
            icon = (ImageView)itemView.findViewById(R.id.icon);
            notify = (ShineButton)itemView.findViewById(R.id.notify);
            rl = (RelativeLayout)itemView.findViewById(R.id.rl);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
