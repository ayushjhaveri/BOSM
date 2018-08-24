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
import android.widget.TextView;

import java.util.ArrayList;

//import bitspilani.bosm.CurrentSportActivity;
import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.fragments.CurrentSportFragment;
import bitspilani.bosm.fragments.SponsorsFragment;
import bitspilani.bosm.fragments.SportSelectedFragment;

/**
 * Created by Prashant on 4/7/2018.
 */

public class AdapterSport extends RecyclerView.Adapter<AdapterSport.ViewHolder> {

    private ArrayList<ItemSport> arrayList;
    private Context context;

    private static final String TAG = "AdapterCart";

    public AdapterSport(Context context, ArrayList<ItemSport> arrayList) {
        this.arrayList = arrayList;
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
        final ItemSport itemSport = arrayList.get(position);

        holder.textView_name.setText(itemSport.getName());
        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/Oswald-Regular.ttf");

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            // the view being shared
//            holder.card_name.setTransitionName("transition" + position);
//        }
        holder.textView_name.setTypeface(oswald_regular);
        holder.textView_name.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Fragment fragment = new SportSelectedFragment();

                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                transaction.addToBackStack(null);
                transaction.replace(R.id.fl_view, fragment);
                transaction.commit();

//                context.startActivity(new Intent(context, CurrentSportActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        CardView card_name;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.tv_name);
            card_name= (CardView)itemView.findViewById(R.id.card_name);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }

}
