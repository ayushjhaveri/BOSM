package bitspilani.bosm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.text.DecimalFormat;
import java.util.Objects;

import bitspilani.bosm.R;
import bitspilani.bosm.fragments.FragmentFoodItems;
import bitspilani.bosm.items.ItemOrder;
import bitspilani.bosm.items.ItemStall;
import bitspilani.bosm.utils.Constant;

/**
 * Created by Prashant on 4/6/2018.adapterStalls
 */

public class AdapterOrder extends FirestoreAdapter<AdapterOrder.ViewHolder>  {

    private Context context;
    DecimalFormat df = new DecimalFormat("#.00");
    public AdapterOrder(Context context, Query query) {
        super(query);
        this.context=context;
    }


    @Override
    public AdapterOrder.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order, parent, false);

        return new AdapterOrder.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterOrder.ViewHolder holder, final int position) {
        DocumentSnapshot document = getSnapshot(position);
        final ItemOrder itemOrder = new ItemOrder(
                document.contains("food_id")?Integer.parseInt((document.getData()).get("food_id").toString()):0,
                document.contains("quantity")?Integer.parseInt((document.getData()).get("quantity").toString()):0,
                document.contains("stall_id")?Integer.parseInt((document.getData()).get("stall_id").toString()):0,
                document.contains("food_name")?document.getData().get("food_name").toString():"",
                document.contains("stall_name")?document.getData().get("stall_name").toString():"",
                document.contains("food_price")?Double.parseDouble(((document.getData()).get("food_price").toString())):0,
                document.contains("status")?Integer.parseInt((document.getData()).get("status").toString()):0
        );

        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Regular.ttf");
//        textView_stall_name.setTypeface(oswald_regular);

        holder.tv_stall_name.setText(itemOrder.getStall_name());
        holder.tv_food_name.setText(itemOrder.getFood_name());
        holder.tv_price.setText(context.getResources().getString(R.string.Rs)+" "+df.format(itemOrder.getPrice()));
        holder.tv_quantity.setText("x "+itemOrder.getQuantity());
//        holder.tv_status.setText(itemOrder.getStatus()+"");
        if(itemOrder.getStatus()==0){
            holder.tv_status.setText("Waiting");
        }
        else if(itemOrder.getStatus()==1){
            holder.tv_status.setText("Accepted");
        }
        if(itemOrder.getStatus()==2){
            holder.tv_status.setText("Declined");
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_food_name, tv_stall_name, tv_price, tv_quantity, tv_status;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_food_name = (TextView)itemView.findViewById(R.id.tv_food_name);
            tv_stall_name = (TextView)itemView.findViewById(R.id.tv_stall_name);
            tv_price = (TextView)itemView.findViewById(R.id.tv_price);
            tv_quantity = (TextView)itemView.findViewById(R.id.tv_quantity);
            tv_status = (TextView)itemView.findViewById(R.id.tv_status);

        }
    }


}
