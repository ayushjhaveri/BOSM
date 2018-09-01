package bitspilani.bosm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.firebase.firestore.SetOptions;
import com.paytm.pgsdk.Log;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import bitspilani.bosm.items.ItemUser;
import bitspilani.bosm.utils.Constant;

public class AddMoneyBActivity extends Fragment {

    private static final String TAG = "AddMoneyBActivity";

    private Toolbar toolbar;
    private TextView textView_balance, textView_add_amount1, textView_add_amount2, textView_add_amount3;
    EditText editText_amount;
    //    Button button_add;
    private ProgressBar progressBar, progressBar2;
    ImageButton ib_add;
    String checkSum = "";
    String amount = "0";
    String ORDER_ID = "";
    static String amt_needed;


    FirebaseUser user;
    FirebaseFirestore db;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            amt_needed = getArguments().getString("YourKey");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_money_b, container, false);
        init(view);


        user = FirebaseAuth.getInstance().getCurrentUser();

        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        getBalance();

        textView_add_amount1.setText("+ " + getResources().getString(R.string.Rs) + "50");
        textView_add_amount2.setText("+ " + getResources().getString(R.string.Rs) + "100");
        textView_add_amount3.setText("+ " + getResources().getString(R.string.Rs) + "200");
        editText_amount.setHint("Enter Amount(" + getResources().getString(R.string.Rs) + ")");

        textView_add_amount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_amount.getText().toString().isEmpty()) {
                    float initial = 50;
                    editText_amount.setText(initial + "");
                } else {
                    float initial = Float.parseFloat(editText_amount.getText().toString());
                    initial += 50;
                    editText_amount.setText(initial + "");
                }
            }
        });
        textView_add_amount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_amount.getText().toString().isEmpty()) {
                    float initial = 100;
                    editText_amount.setText(initial + "");
                } else {
                    float initial = Float.parseFloat(editText_amount.getText().toString());
                    initial += 100;
                    editText_amount.setText(initial + "");
                }
            }
        });
        textView_add_amount3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_amount.getText().toString().isEmpty()) {
                    float initial = 200;
                    editText_amount.setText(initial + "");
                } else {
                    float initial = Float.parseFloat(editText_amount.getText().toString());
                    initial += 200;
                    editText_amount.setText(initial + "");
                }
            }
        });


        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_amount.getText().toString().isEmpty() || Float.parseFloat(editText_amount.getText().toString()) <= 0) {
                    Snackbar.make(view, "Enter valid amount!", Snackbar.LENGTH_SHORT).show();
                } else {

                    add_amount(Double.parseDouble(editText_amount.getText().toString()));


//                    if (Constant.ACCOUNT_TYPE == Constant.ACCOUNT_TYPE_BITS) {
//                        add_amount();
////                    } else if (Constant.ACCOUNT_TYPE == Constant.ACCOUNT_TYPE_NON_BITS) {
//                    amount = editText_amount.getText().toString();
//                    ORDER_ID = Constant.random();
//                    Log.d(TAG,"ORDER ID: "+ORDER_ID);
//                        onStartTransaction();
//                    }
                }
            }
        });

//        Intent intent = getIntent();
//        if(intent!=null){
//            double amount = intent.getDoubleExtra("amount",0);
//            editText_amount.setText(amount+"");
//        }
        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setTypeface(oswald_regular);

        view.findViewById(R.id.rl_add_50).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Its listnening", Toast.LENGTH_LONG).show();
                add_amount(50.0);
            }
        });
        view.findViewById(R.id.rl_add_100).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_amount(100.0);
            }
        });
        view.findViewById(R.id.rl_add_150).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_amount(150.0);
            }
        });
        view.findViewById(R.id.rl_add_200).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_amount(200.0);
            }
        });
        view.findViewById(R.id.rl_add_350).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_amount(350.0);
            }
        });
        view.findViewById(R.id.rl_add_500).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_amount(500.0);
            }
        });


        if (amt_needed != "") {
            editText_amount.setText(amt_needed);
        }

        return view;

    }

    private void add_amount(final double amount) {

        if (user == null) {
            Toast.makeText(getActivity(), "Please login!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {

            db.collection("user").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    final Double prev_bal = Double.parseDouble(task.getResult().getData().get("wallet").toString());


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
                                        data.put("user_id", user.getUid());
                                        data.put("amount", amount);
                                        data.put("from", "User");
                                        data.put("order_unique_id", " ");
                                        data.put("to", "Wallet");
                                        data.put("remarks", "Money added to wallet");
                                        data.put("timestamp", FieldValue.serverTimestamp());

                                        db.collection("transactions").document(String.valueOf(id)).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    db.collection("latest_ids").document("transactions").update("value", finalId);
                                                    db.collection("user").document(user.getUid()).update("wallet", prev_bal + amount);
                                                    Toast.makeText(getActivity(), "Amount added successfully!", Toast.LENGTH_SHORT).show();
                                                    editText_amount.setText("");
                                                } else {
                                                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    } else {
                                        Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );


                }
            });

        }

    }

    private void getBalance() {
        textView_balance.setText(getResources().getString(R.string.Rs) + " ---");
        if (user == null) {
            Toast.makeText(getActivity(), "Please login!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            db.collection("user").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    textView_balance.setText(getResources().getString(R.string.Rs) + " " + task.getResult().getData().get("wallet").toString());
                }
            });

        }
    }


    private void init(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_top);
        textView_balance = (TextView) view.findViewById(R.id.textview_balance);
        textView_add_amount1 = (TextView) view.findViewById(R.id.textView3);
        textView_add_amount2 = (TextView) view.findViewById(R.id.textView2);
        textView_add_amount3 = (TextView) view.findViewById(R.id.textView4);
        editText_amount = (EditText) view.findViewById(R.id.et_amount);
//        button_add = (Button) view.findViewById(R.id.bt_add);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);
        ib_add = (ImageButton) view.findViewById(R.id.ib_add);
    }

    ListenerRegistration listenerRegistration;

    @Override
    public void onStart() {
        super.onStart();
        DocumentReference docRef = db.collection("user").document(user.getUid());
        listenerRegistration = docRef.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
//
//                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
//                        ? "Local" : "Server";

                if (snapshot != null && snapshot.exists()) {
//                    Log.d(TAG, source +
//" data: " + snapshot.getData());
                    textView_balance.setText(getResources().getString(R.string.Rs) + " " + snapshot.getData().get("wallet").toString());

                }
//                else {
//                    Log.d(TAG, source + " data: null");
//                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        listenerRegistration.remove();
    }
}
