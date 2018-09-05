package bitspilani.bosm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import bitspilani.bosm.adapters.AdapterCart;
import bitspilani.bosm.items.ItemCart;
import bitspilani.bosm.items.ItemFood;
import bitspilani.bosm.utils.Constant;
//import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class CartActivity extends Fragment {

    private static final String TAG = "CartActivity";
    //    Toolbar toolbar;
    RecyclerView recyclerView;
    AdapterCart adapterCart;
    public TextView tv_total_price;
    public RelativeLayout rl_empty_layout;

    ListenerRegistration listenerRegistration;
    private static DecimalFormat df2 = new DecimalFormat("0.00");

    ImageButton ib_pay;
    FirebaseFirestore db;
    FirebaseUser user;

    public CartActivity(){
        HomeActivity.currentFragment = "CartActivity";
    }

    public static ProgressBar progressBar;
    public static void viewLoader(boolean is){
        if(is){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cart, container, false);
        init(view);

        viewLoader(true);

        Source s = Source.SERVER;

        db = FirebaseFirestore.getInstance();


        user = FirebaseAuth.getInstance().getCurrentUser();
        Query mQuery = db.collection("cart").whereEqualTo("user_id", user.getUid());

        adapterCart = new AdapterCart(getActivity(), mQuery);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCart);

        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView) view.findViewById(R.id.tv_cart);
        tv_title.setTypeface(oswald_regular);


        //fab for wallet
        FloatingActionButton fab_wallet = (FloatingActionButton) view.findViewById(R.id.fab);
        fab_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new WalletActivity());
            }
        });

//        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        ib_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                db.collection("user").document(user.getUid()).get().addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {

                                    final double balance = Double.parseDouble(task.getResult().getData().get("wallet").toString());
                                    final double order_total = Double.parseDouble(tv_total_price.getText().toString().substring(1));
                                    if (order_total == 0) {
                                        Snackbar.make(view, "Cart is empty! PLease add an item", BaseTransientBottomBar.LENGTH_SHORT).show();
                                    } else {

                                        AlertDialog.Builder builder;
                                        builder = new AlertDialog.Builder(getActivity());
                                        View dialog_view = inflater.inflate(R.layout.dialog_cart, null);
                                        builder.setView(dialog_view);
                                        final AlertDialog alert = builder.create();

                                        //on yes button clicked to place order
                                        dialog_view.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alert.cancel();


                                                if (balance >= order_total) {
                                                    final int randomID = (int) (Math.random() * 9000) + 1000;
                                                    db.collection("cart").whereEqualTo("user_id", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                                    Map<String, Object> data = new HashMap<>();
                                                                    data.put("food_id", document.getData().get("food_id"));
                                                                    data.put("food_name", document.getData().get("food_name"));
                                                                    data.put("quantity", document.getData().get("quantity"));
                                                                    data.put("food_price", document.getData().get("food_price"));
                                                                    data.put("status", 0);
                                                                    data.put("stall_id", document.getData().get("stall_id"));
                                                                    data.put("stall_name", document.getData().get("stall_name"));

                                                                    document.getReference().delete();
                                                                    db.collection("orders").document(user.getUid()).collection(String.valueOf(randomID)).document().set(data, SetOptions.merge());
                                                                }
                                                                db.collection("latest_ids").document("transactions").
                                                                        get().addOnCompleteListener(
                                                                        new OnCompleteListener<DocumentSnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    int id = Integer.parseInt(
                                                                                            task.getResult().getData().get("value").toString());
                                                                                    id++;
                                                                                    final int finalId = id;

                                                                                    HashMap<String, Object> data = new HashMap<>();
                                                                                    data.put("order_unique_id", randomID);
                                                                                    data.put("user_id", user.getUid());
                                                                                    data.put("amount", order_total);
                                                                                    data.put("from", "Wallet");
                                                                                    data.put("to", "Stall");
                                                                                    data.put("remarks", "Item purchased from Stall");
                                                                                    data.put("timestamp", FieldValue.serverTimestamp());

                                                                                    db.collection("transactions").document(String.valueOf(id)).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            db.collection("latest_ids").document("transactions").update("value", finalId);
                                                                                            db.collection("user").document(user.getUid()).update("wallet", balance - order_total);
                                                                                        }
                                                                                    });

                                                                                }
                                                                            }
                                                                        }
                                                                );


                                                                //intent
                                                                loadFragment(OrderDetailsActivity.newInstance(randomID));
//                                                                progressBar.setVisibility(View.GONE);

                                                            } else {
//                                                                progressBar.setVisibility(View.GONE);
                                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                                            }
                                                        }
                                                    });


                                                } else {
                                                    Toast.makeText(getContext(), "Insufficient Balance!", Toast.LENGTH_SHORT).show();
//                                                    progressBar.setVisibility(View.GONE);
                                                    loadFragment(new AddMoneyBActivity().newInstance(order_total - balance));

//Inflate the fragment


                                                }
                                            }
                                        });

                                        //On button no clicked
                                        dialog_view.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alert.cancel();
                                            }
                                        });
                                        alert.show();


                                    }
                                }
                            }
                        });


//                if(Double.parseDouble(tv_total_price.)){
//                    getWalletAmount();
//                }else{
//                    Toast.makeText(CartActivity.this,"Please enter items in the cart first!",Toast.LENGTH_SHORT).show();
//                }
            }
        });

        tv_total_price.setText(getContext().getResources().getString(R.string.Rs) + " --");

//        progressBar.setVisibility(View.VISIBLE);
        mQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double sum = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                sum = sum + (Double.parseDouble(document.getData().get("food_price").toString()) * Integer.parseInt(document.getData().get("quantity").toString()));
                            }
                            tv_total_price.setText(getContext().getResources().getString(R.string.Rs) + " " + df2.format(sum) + "");
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
//        progressBar.setVisibility(View.GONE);
        return view;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.commit();
    }


    private void init(View view) {
//        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rl_empty_layout = (RelativeLayout) view.findViewById(R.id.rl_empty_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
//        tv_pay=(TextView) findViewById(R.id.tv_pay);

//        iv_back = (ImageView)findViewById(R.id.iv_back);
        ib_pay = (ImageButton) view.findViewById(R.id.ib_pay);
    }


    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (adapterCart != null) {
            adapterCart.startListening();
        }

        Query query = db.collection("cart").whereEqualTo("user_id", user.getUid());
        listenerRegistration = query.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    com.paytm.pgsdk.Log.w(TAG, "Listen failed.", e);
                    return;
                }
//
//                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
//                        ? "Local" : "Server";
//                    Log.d(TAG, source +
//" data: " + snapshot.getData());

                double sum = 0;
                for (QueryDocumentSnapshot document : snapshot) {
                    sum = sum + (Double.parseDouble(document.getData().get("food_price").toString()) * Integer.parseInt(document.getData().get("quantity").toString()));
                }
                tv_total_price.setText(getContext().getResources().getString(R.string.Rs) + " " + sum + "");
            }
//                else {
//                    Log.d(TAG, source + " data: null");
//
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterCart != null) {
            adapterCart.stopListening();
        }

        listenerRegistration.remove();
    }


////    public void getCartList(){
////        class GetData extends AsyncTask<Void, Void, String> {
////            @Override
////            protected void onPreExecute() {
////                progressBar.setVisibility(View.VISIBLE);
////                super.onPreExecute();
////            }
////
////            @Override
////            protected void onPostExecute(String s) {
////                progressBar.setVisibility(View.GONE);
////                super.onPostExecute(s);
////                parseJSON(s);
////            }
////
////            @Override
////            protected String doInBackground(Void... params) {
////
////                try {
////                    URL url = new URL(Constant.URL_GET_USER_CART);
////                    String urlParams = "user_id=" + Constant.currentItemUser.getId();
////
////                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
////                    con.setDoOutput(true);
////                    StringBuilder sb = new StringBuilder();
//////
////                    OutputStream os = con.getOutputStream();
////                    os.write(urlParams.getBytes());
////                    os.flush();
////                    os.close();
////
////                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
////
////                    String json;
////                    while ((json = bufferedReader.readLine()) != null) {
////                        sb.append(json + "\n");
////                    }
////
////                    String s = sb.toString().trim();
////                    return s;
////
////                } catch (IOException e) {
////                    e.printStackTrace();
////                    return "error";
////                }
////            }
////
////            private void parseJSON(String json) {
////                try {
//////                    Toast.makeText(LoginActivity.this,json,Toast.LENGTH_SHORT).show();
////                    Log.d(TAG, "Response: " + json);
////                    JSONObject root = new JSONObject(json);
////                    JSONObject response = root.getJSONObject("response");
////                    boolean success = response.getBoolean("success");
////                    if(success){
////                        rl_empty_layout.setVisibility(View.GONE);
////                        sum=0;
////                        JSONArray jsonArray = response.getJSONArray("cart_list");
////                        for (int i=0;i<jsonArray.length();i++){
////                            JSONObject jsonObject = jsonArray.getJSONObject(i);
////                            ItemCart itemFood = new ItemCart(
////                                    jsonObject.getInt("cart_id"),
////                                    jsonObject.getInt("food_id"),
////                                    jsonObject.getInt("quantity"),
////                                    jsonObject.getString("food_name"),
////                                    jsonObject.getDouble("food_price")
////                            );
////                            sum += (itemFood.getTotalPrice());
////                            cartArrayList.add(itemFood);
////                        }
////                        tv_total_price.setText(getResources().getString(R.string.Rs)+" "+sum+"");
////                        tv_items.setText("Items("+cartArrayList.size()+")");
////                        adapterCart.notifyDataSetChanged();
////                    }else{
////                    rl_empty_layout.setVisibility(View.VISIBLE);
////                    progressBar.setVisibility(View.GONE);
////                    }
////                } catch (JSONException e) {
////                    progressBar.setVisibility(View.GONE);
////                    Toast.makeText(CartAicon_cartctivity.this,getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
////                    e.printStackTrace();
////                }
////            }
////        }
////        GetData gd = new GetData();
////        gd.execute();
////    }
//    public void place_order(){
//        class GetData extends AsyncTask<Void, Void, String> {
//            @Override
//            protected void onPreExecute() {
//                progressBar.setVisibility(View.VISIBLE);
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                progressBar.setVisibility(View.GONE);
//                super.onPostExecute(s);
//                parseJSON(s);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//
//                try {
//                    URL url = new URL(Constant.URL_PLACE_ORDER);
//                    ArrayList<String> cart_ids = new ArrayList<>();
//                    for(int i=0;i<cartArrayList.size();i++){
//                        cart_ids.add(cartArrayList.get(i).getCart_id()+"");
//                    }
//                    String parameter = android.text.TextUtils.join(",", cart_ids);
//                    Log.d(TAG,"Parameter: "+parameter);
//
//                    String urlParams = "cart_ids=" + parameter+
//                            "&amount="+sum;
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
//                        //go to new activity
//                        Intent intent =new Intent(CartActivity.this,OrderDetailsActivity.class);
//                        intent.putExtra("amount",response.getDouble("amount"));
//                        intent.putExtra("order_unique_id",response.getString("order_unique_id"));
//                        Calendar calendar = Calendar.getInstance();
//                        String timestamp = response.getString("timestamp");
//                        String[] date_time = timestamp.split("T");
//                        String[] date= date_time[0].split("-");
//                        String[] time= date_time[1].split(":");
//                        calendar.set(Calendar.YEAR,Integer.parseInt(date[0]));
//                        calendar.set(Calendar.MONTH,Integer.parseInt(date[1]));
//                        calendar.set(Calendar.DATE,Integer.parseInt(date[2]));
//
//                        calendar.set(Calendar.HOUR,Integer.parseInt(time[0]));
//                        calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
////                            calendar.add(Calendar.SECOND,Integer.parseInt(time[2]));
//                        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
//                        String timestamp1 = timeFormat.format(calendar.getTime());
//                        intent.putExtra("timestamp",timestamp1);
//                        startActivity(intent);
//
//                        double balance = Constant.currentItemUser.getUser_wallet();
//                        balance=balance-sum;
//                        CartActivity.sum = 0;
//                        tv_total_price.setText(getResources().getString(R.string.Rs)+" "+sum+"");
//                        tv_items.setText("");
//                        cartArrayList.clear();
//                        rl_empty_layout.setVisibility(View.VISIBLE);
//                        adapterCart.notifyDataSetChanged();
//                        Constant.currentItemUser.setUser_wallet(balance);
//                        Toast.makeText(CartActivity.this,"Order placed successfully!",Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(CartActivity.this,"low balance!",Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(CartActivity.this,getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        }
//        GetData gd = new GetData();
//        gd.execute();
//    }

//    public void getWalletAmount(){
////        Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
//        class GetData extends AsyncTask<Void, Void, String> {
//            @Override
//            protected void onPreExecute() {
//                progressBar.setVisibility(View.VISIBLE);
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                progressBar.setVisibility(View.GONE);
//                super.onPostExecute(s);
//                parseJSON(s);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//
//                try {
//                    URL url = new URL(Constant.URL_GET_WALLET);
//                    String urlParams = "email=" + Constant.currentItemUser.getUser_email();
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
////                   Toast.makeText(WalletActivity.this,json,Toast.LENGTH_SHORT).show();
//                    com.paytm.pgsdk.Log.d(TAG, "Response: " + json);
//                    JSONObject root = new JSONObject(json);
//                    JSONObject response = root.getJSONObject("response");
//                    boolean success = response.getBoolean("success");
//                    if(success){
//                        double wallet = response.getDouble("balance");
////                        Toast.makeText(CartActivity.this,"sum :"+sum,Toast.LENGTH_SHORT).show();
//                        if(sum<=wallet){
//                            Toast.makeText(CartActivity.this,"Placing your order!",Toast.LENGTH_SHORT).show();
//                            place_order();
//                        }else{
//                            //redirect to adding wallet
//                            double amount=sum-wallet;
//                            Intent intent = new Intent(CartActivity.this,AddMoneyBActivity.class);
//                            intent.putExtra("amount",amount);
//                            startActivity(intent);
//
//                        }
//                        Constant.currentItemUser.setUser_wallet(wallet);
//                    }else{
//                        Toast.makeText(CartActivity.this,getResources().getString(R.string.connection_error),Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        GetData gd = new GetData();
//        gd.execute();
//    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="CartActivity";
    }
}

