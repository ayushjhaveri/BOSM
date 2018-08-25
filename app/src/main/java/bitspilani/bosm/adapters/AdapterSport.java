package bitspilani.bosm.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

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

    private static final String TAG = "AdapterCart";

    public AdapterSport(Context context, Query query) {
        super(query);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sport, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DocumentSnapshot document =  getSnapshot(position);
        final ItemSport itemSport  = new ItemSport(
                Integer.parseInt(document.getId()),
                document.getData().get("sportName").toString(),
                Boolean.parseBoolean(document.getData().get("isGender").toString())
        );

        holder.textView_name.setText(itemSport.getName());
        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/Oswald-Regular.ttf");

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            // the view being shared
//            holder.card_name.setTransitionName("transition" + position);
//        }
        holder.icon.setImageResource(SportFragment.iconHash.get(itemSport.getSport_id()));
        holder.textView_name.setTypeface(oswald_regular);
        holder.textView_name.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Fragment fragment = new SportSelectedFragment();

                Constant.currentSport  = itemSport;
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

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

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.tv_name);
            card_name= (CardView)itemView.findViewById(R.id.card_name);
            icon = (ImageView)itemView.findViewById(R.id.icon);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }

}
