package bitspilani.bosm.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Objects;

import bitspilani.bosm.R;
import bitspilani.bosm.fragments.FragmentFoodItems;
import bitspilani.bosm.items.ItemStall;
import bitspilani.bosm.utils.Constant;

/**
 * Created by Prashant on 4/6/2018.
 */

public class AdapterStalls extends FirestoreAdapter<AdapterStalls.ViewHolder>  {

    private Context context;
    public AdapterStalls(Context context,Query query) {
        super(query);
        this.context=context;
    }


    @Override
    public AdapterStalls.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_stall, parent, false);

        return new AdapterStalls.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterStalls.ViewHolder holder, final int position) {
        DocumentSnapshot document = getSnapshot(position);
        final ItemStall itemStall = new ItemStall(
                Integer.parseInt(document.getId()),
                Objects.requireNonNull(document.getData()).get("name").toString()
        );

        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Regular.ttf");
//        textView_stall_name.setTypeface(oswald_regular);

        holder.tv_stall_name.setText(itemStall.getName());
        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.CURRENT_STALL_ID = itemStall.getStall_id();
                Constant.CURRENT_STALL_NAME = itemStall.getName();
                loadFragment(new FragmentFoodItems());
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_stall_name;
        RelativeLayout rl_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_stall_name = (TextView)itemView.findViewById(R.id.tv_stall_name);
            rl_layout = (RelativeLayout)itemView.findViewById(R.id.rl_layout);
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.commit();
    }


}
