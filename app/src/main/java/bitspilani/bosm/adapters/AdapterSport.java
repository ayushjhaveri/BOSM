package bitspilani.bosm.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import bitspilani.bosm.CartActivity;
//import bitspilani.bosm.CurrentSportActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.fragments.CurrentSportFragment;
import bitspilani.bosm.fragments.ScoreFragment;
import bitspilani.bosm.items.ItemCart;
import bitspilani.bosm.utils.Constant;

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
                .inflate(R.layout.layout_row_sport, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemSport itemSport = arrayList.get(position);

        holder.textView_name.setText(itemSport.getName());
        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/Oswald-Regular.ttf");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            // the view being shared
            holder.card_name.setTransitionName("transition" + position);
        }
        holder.textView_name.setTypeface(oswald_regular);
        holder.textView_name.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment currentSportFragment = new CurrentSportFragment();
                FragmentTransaction transaction = ((Activity)context).getFragmentManager().beginTransaction();

                transaction.addSharedElement(holder.card_name, ViewCompat.getTransitionName(holder.card_name));
                transaction.addToBackStack(null);
                transaction.replace(R.id.fl_view, currentSportFragment);
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
