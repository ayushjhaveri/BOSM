package bitspilani.bosm.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
import bitspilani.bosm.R;
import bitspilani.bosm.items.ItemCart;
import bitspilani.bosm.utils.Constant;
import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by Prashant on 4/7/2018.
 */

public class AdapterCart extends FirestoreAdapter<AdapterCart.ViewHolder> {

    private Context context;

    private static final String TAG = "AdapterCart";

    public AdapterCart(Context context, Query query) {
        super(query);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cart, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final DocumentSnapshot document =  getSnapshot(position);
        final ItemCart itemCart = new ItemCart(
                Integer.parseInt(document.getId()),
                Integer.parseInt(document.getData().get("food_id").toString()),
                Integer.parseInt(document.getData().get("stall_id").toString()),
                Integer.parseInt(document.getData().get("quantity").toString()),
                document.getData().get("food_name").toString(),
                document.getData().get("stall_name").toString(),
                Double.parseDouble(document.getData().get("food_price").toString())
        );

        holder.textView_name.setText(itemCart.getName());
        holder.textView_price.setText(context.getResources().getString(R.string.Rs) + " "+itemCart.getPrice()+"");
        holder.progressBar.setVisibility(View.GONE);

        holder.ib_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                document.getReference().delete();
                notifyItemRemoved(position);
            }
        });

        holder.stepperTouch.stepper.setMin(1);
        holder.stepperTouch.stepper.setMax(45);
        holder.stepperTouch.stepper.setValue(itemCart.getQuantity());
        holder.stepperTouch.enableSideTap(true);
        holder.stepperTouch.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean positive) {
                document.getReference().update("quantity",value);
            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name, textView_price;
        ImageView iv_update_cart;
        ImageButton ib_remove;
        StepperTouch stepperTouch;
//        ProgressBar progressBar;
        RelativeLayout progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.tv_name);
            textView_price = (TextView) itemView.findViewById(R.id.tv_price);
//            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            progressBar=(RelativeLayout) itemView.findViewById(R.id.rl_progress);
            ib_remove = (ImageButton) itemView.findViewById(R.id.ib_remove);
            iv_update_cart=(ImageView)itemView.findViewById(R.id.iv_update_cart);
            stepperTouch = (StepperTouch)itemView.findViewById(R.id.stepperTouch);
        }
    }

//
//    public void update_cart(final ViewHolder holder, final ItemCart itemCart, final int quantity){
//        class GetData extends AsyncTask<Void, Void, String> {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                holder.iv_update_cart.setVisibility(View.GONE);
//                holder.progressBar.setVisibility(View.VISIBLE);
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                holder.iv_update_cart.setVisibility(View.GONE);
//                holder.progressBar.setVisibility(View.GONE);
//                parseJSON(s);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//
//                try {
//                    URL url = new URL(Constant.URL_UPDATE_CART);
//                    String urlParams = "cart_id=" + itemCart.getCart_id() +
//                            "&quantity="+quantity;
//
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setDoOutput(true);
//                    StringBuilder sb = new StringBuilder();
////
//                    OutputStream os = con.getOutputStream();
//                    os.write(urlParams.getBytes());
//                    os.flush();
//                    os.close();
//
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//                    String json;
//                    while ((json = bufferedReader.readLine()) != null) {
//                        sb.append(json + "\n");
//                    }
//
//                    String s = sb.toString().trim();
//                    return s;
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return "error";
//                }
//            }
//
//            private void parseJSON(String json) {
//                try {
////                    Toast.makeText(LoginActivity.this,json,Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "Response: " + json);
//                    JSONObject root = new JSONObject(json);
//                    JSONObject response = root.getJSONObject("response");
//                    boolean success = response.getBoolean("success");
//                    if(success){
////                        notifyDataSetChanged();
////                        double update_sum = CartActivity.sum - (itemCart.getTotalPrice()) +(itemCart.getPrice()*quantity);
////                        CartActivity.sum = update_sum;
////                        CartActivity.tv_total_price.setText(context.getResources().getString(R.string.Rs)+" "+update_sum+"");
//                        itemCart.setQuantity(quantity);
//                    }else{
//                        Toast.makeText(context,"try again!",Toast.LENGTH_SHORT).show();
//                        holder.iv_update_cart.setVisibility(View.VISIBLE);
//                        holder.progressBar.setVisibility(View.GONE);
//                    }
//                } catch (JSONException e) {
//                    holder.iv_update_cart.setVisibility(View.VISIBLE);
//                    holder.progressBar.setVisibility(View.GONE);
//                    Toast.makeText(context,context.getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        }
//        GetData gd = new GetData();
//        gd.execute();
//    }
//
//    public void del_cart_item(final ItemCart itemCart, final int pos){
//        class GetData extends AsyncTask<Void, Void, String> {
//            @Override
//            protected void onPreExecute() {
//                CartActivity.progressBar.setVisibility(View.VISIBLE);
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                CartActivity.progressBar.setVisibility(View.GONE);
//                super.onPostExecute(s);
//                parseJSON(s);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//
//                try {
//                    URL url = new URL(Constant.URL_DEL_CART_ITEM);
//                    String urlParams = "cart_id=" + itemCart.getCart_id();
//
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setDoOutput(true);
//                    StringBuilder sb = new StringBuilder();
////
//                    OutputStream os = con.getOutputStream();
//                    os.write(urlParams.getBytes());
//                    os.flush();
//                    os.close();
//
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//                    String json;
//                    while ((json = bufferedReader.readLine()) != null) {
//                        sb.append(json + "\n");
//                    }
//
//                    String s = sb.toString().trim();
//                    return s;
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return "error";
//                }
//            }
//
//            private void parseJSON(String json) {
//                try {
////                    Toast.makeText(LoginActivity.this,json,Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "Response: " + json);
//                    JSONObject root = new JSONObject(json);
//                    JSONObject response = root.getJSONObject("response");
//                    boolean success = response.getBoolean("success");
//                    if(success){
////                        arrayList.remove(pos);
//                        notifyItemRemoved(pos);
////                        CartActivity.tv_items.setText("Items("+arrayList.size()+")");
////                        if(arrayList.size()==0){
////                            CartActivity.tv_items.setText("");
////                        }
////                        double update_sum = CartActivity.sum - (itemCart.getTotalPrice());
////                        CartActivity.sum = update_sum;
////                        CartActivity.tv_total_price.setText(context.getResources().getString(R.string.Rs)+" "+update_sum+"");
//                    }else{
//                        Toast.makeText(context,"try again!",Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    CartActivity.progressBar.setVisibility(View.GONE);
//                    Toast.makeText(context,context.getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        }
//        GetData gd = new GetData();
//        gd.execute();
//    }


}
