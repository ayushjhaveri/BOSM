package bitspilani.bosm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import bitspilani.bosm.CartActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.fragments.FragmentFoodItems;
import bitspilani.bosm.items.ItemFood;
import bitspilani.bosm.utils.Constant;
import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

/**
 * Created by Prashant on 4/6/2018.
 */

public class AdapterFoods extends FirestoreAdapter<AdapterFoods.ViewHolder> {

    private  Context context;
    private static DecimalFormat df2 = new DecimalFormat("0.00");
    public AdapterFoods(Context context,Query query) {
        super(query);
        this.context=context;
    }


    @Override
    public AdapterFoods.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_food, parent, false);

        return new AdapterFoods.ViewHolder(itemView);
    }

    private static final String TAG = "AdapterFoods";


    @Override
    public void onBindViewHolder(final AdapterFoods.ViewHolder holder, final int position) {
        DocumentSnapshot document = getSnapshot(position);
        final ItemFood itemFood = new ItemFood(
                Integer.parseInt(document.getId()),
                Objects.requireNonNull(document.getData()).get("name").toString(),
                Double.parseDouble(Objects.requireNonNull(document.getData()).get("price").toString())
        );

        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Oswald-Regular.ttf");
//        textView_stall_name.setTypeface(oswald_regular);

        holder.tv_food_name.setText(itemFood.getFood_name());
        holder.tv_price.setText(context.getResources().getString(R.string.Rs)+df2.format(itemFood.getPrice()));
        holder.ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                S
                FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
                final Map<String, Object> data = new HashMap<>();
                data.put("food_id",itemFood.getFood_id());
                data.put("food_name",itemFood.getFood_name());
                data.put("food_price",itemFood.getPrice());
                data.put("quantity",1);
                data.put("stall_id",Constant.CURRENT_STALL_ID);
                data.put("stall_name",Constant.CURRENT_STALL_NAME);
                data.put("user_id",user.getUid());

                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setTimestampsInSnapshotsEnabled(true)
                        .build();
                db.setFirestoreSettings(settings);
                db.collection("cart").whereEqualTo("food_id",itemFood.getFood_id()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()>0){
                                Snackbar.make(view,"item already added to cart!",Snackbar.LENGTH_SHORT).show();
                            }else{
                                db.collection("latest_ids").document("cart").
                                        get().addOnCompleteListener(
                                        new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    int id = Integer.parseInt(
                                                            task.getResult().getData().get("value").toString());
                                                    id++;
                                                    final int finalId = id;
                                                    db.collection("cart").document(String.valueOf(id)).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Snackbar.make(view,"item added successfully!",Snackbar.LENGTH_SHORT).show();
                                                                db.collection("latest_ids").document("cart").update("value", finalId);
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                );


                            }
                        }
                    }
                });

            }
        });


    }


//    public void add_to_cart(final int food_id, final View view){
//        class GetData extends AsyncTask<Void, Void, String> {
//            @Override
//            protected void onPreExecute() {
//
//                FragmentFoodItems.progressBar.setVisibility(View.VISIBLE);
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                FragmentFoodItems.progressBar.setVisibility(View.GONE);
//                super.onPostExecute(s);
//                parseJSON(s);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//
//                try {
//                    URL url = new URL(Constant.URL_ADD_TO_CART);
//                    String urlParams = "user_id=" + Constant.currentItemUser.getId()
//                            +"&food_id="+food_id;
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
//                    if(!success) {
//                        Snackbar.make(view,"Item already added to the cart!",Snackbar.LENGTH_SHORT).show();
////                        Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
//                    }else {
//                        Snackbar.make(view,"Item added successfully!",Snackbar.LENGTH_SHORT).show();
////                        Toast.makeText(getContext(), "Item added successfully!", Toast.LENGTH_SHORT).show();
//                        FragmentFoodItems.progressBar.setVisibility(View.GONE);
//                    }
//                } catch (JSONException e) {
//                    FragmentFoodItems.progressBar.setVisibility(View.GONE);
//                    Toast.makeText(getContext(),getContext().getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        }
//        GetData gd = new GetData();
//        gd.execute();
//    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_food_name, tv_price;
        ImageButton ib_add;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_food_name = (TextView)itemView.findViewById(R.id.tv_food_name);
            tv_price = (TextView)itemView.findViewById(R.id.tv_price);
            ib_add = (ImageButton)itemView.findViewById(R.id.ib_add);


        }
    }

}
