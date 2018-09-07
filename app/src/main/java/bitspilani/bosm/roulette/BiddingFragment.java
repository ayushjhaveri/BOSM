package bitspilani.bosm.roulette;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterRoulette;
import bitspilani.bosm.fragments.SportFragment;
import bitspilani.bosm.items.ArrayObject;
import bitspilani.bosm.items.ItemRoulette;
import bitspilani.bosm.utils.Constant;
import io.grpc.Server;



public class BiddingFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "BiddingActivity";


    //    private TextView tvDescription;
    private TextView tvDate;
    private TextView tvDay;
    private TextView tvTime;
    private TextView tvStatus;
    private TextView tvVenue;
    private TextView tvTeamA;
    private TextView tvTeamB;
    private LinearLayout container;

    private ImageView ivBet;
    private TextView tvBidAmount;
    private ImageView ivPowBet;
    private TextView tvPowBidAmount;
    private TextView tvWallet;
    private TextView tvTimerPowBet;
    private TextView tvTimerSpin;
    private ImageView ivSpin;
    private TextView tvWon;

    private ItemRoulette itemRoulette;

    //user
    private int score;
    private Calendar powBidTime;
    private Calendar slotTime;
    private int luck;
    private int bettingAmount;

    //betting
//    private boolean done;
    private String team;
    private int amount;
    private boolean powerBid;

    private CardView cvTeamA;
    private CardView cvTeamB;
    private CardView cardViewWon;
    private CardView cardViewWallet;
    private CardView cardViewBet;
    private CardView cardViewPowBet;
    private CardView cardViewSpin;

    private boolean cvBetEnabled = true;
    private boolean cvPowBetEnabled = true;
    private boolean cvSpinEnabled = true;

    CountDownTimer countDownTimer;
    CountDownTimer countDownTimer2;

    final HashMap<Integer, Integer> items = new HashMap<>();
    JSONArray array = null;

    int sport_id;
    String doc_id;
    String sport_name;

    Source source;

    ProgressBar progressBar;

    FirebaseFirestore db;

    public BiddingFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment = "BiddingFragment";
    }


    public static BiddingFragment newInstance(int param1, String param2,String param3) {
        BiddingFragment fragment = new BiddingFragment();
        Bundle args = new Bundle();
        args.putInt("sport_id", param1);
        args.putString("doc_id", param2);
        args.putString("sport_name",param3);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            sport_id = getArguments().getInt("sport_id");
            doc_id = getArguments().getString("doc_id");
            sport_name = getArguments().getString("sport_name");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container1,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        View view = inflater.inflate(R.layout.fragment_bidding, container1, false);


        TextView tv_header = (TextView) view.findViewById(R.id.tv_header);
        Typeface oswald_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/KrinkesDecorPERSONAL.ttf");

        tv_header.setTypeface(oswald_regular);
        tv_header.setText(sport_name);


        db = FirebaseFirestore.getInstance();


//        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDay = (TextView) view.findViewById(R.id.tvDay);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        tvVenue = (TextView) view.findViewById(R.id.tvVenue);
        tvTeamA = (TextView) view.findViewById(R.id.tvTeamA);
        tvTeamB = (TextView) view.findViewById(R.id.tvTeamB);
        container = (LinearLayout) view.findViewById(R.id.container);
        tvWallet = (TextView) view.findViewById(R.id.tvWallet);
        tvWon = (TextView) view.findViewById(R.id.tvWon);
        cvTeamA = (CardView) view.findViewById(R.id.cvTeamA);
        cvTeamB = (CardView) view.findViewById(R.id.cvTeamB);
        cardViewWallet = (CardView) view.findViewById(R.id.card_view_wallet);
        cardViewBet = (CardView) view.findViewById(R.id.card_view_bid);
        cardViewPowBet = (CardView) view.findViewById(R.id.card_view_pow_bid);
        cardViewPowBet = (CardView) view.findViewById(R.id.card_view_pow_bid);
        cardViewSpin = (CardView) view.findViewById(R.id.card_view_spin);
        tvTimerPowBet = (TextView) view.findViewById(R.id.tvTimerPowBet);
        tvTimerSpin = (TextView) view.findViewById(R.id.tvTimerSpin);
        cardViewWon = (CardView) view.findViewById(R.id.cvWon);

        tvBidAmount = (TextView) view.findViewById(R.id.tvBidAmount);
        tvPowBidAmount = (TextView) view.findViewById(R.id.tvPowBidAmount);
        ivBet = (ImageView) view.findViewById(R.id.ivBet);
        ivPowBet = (ImageView) view.findViewById(R.id.ivPowBet);
        ivSpin = (ImageView) view.findViewById(R.id.ivSpin);

        cardViewWallet.setOnClickListener(this);
        cardViewBet.setOnClickListener(this);
        cardViewPowBet.setOnClickListener(this);
        cardViewSpin.setOnClickListener(this);

//        mySwipeRefreshLayout.setRefreshing(true);
        container.setVisibility(View.GONE);
        viewLoader(true);
         source = Source.SERVER;
        getData();

//        mySwipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        getData();
//                    }
//                }
//        );

        items.put(0, 1);
        items.put(1, 2);
        items.put(2, 3);
        items.put(3, 4);
        items.put(4, 5);
        items.put(5, 6);
        items.put(6, 7);
        items.put(7, 8);
        items.put(8, 9);
        items.put(9, 11);
        items.put(10, 12);
        items.put(11, 13);
        items.put(12, 14);
        items.put(13, 15);
        items.put(14, 16);

        return view;
    }

    private void viewLoader(boolean is){
        if(is){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    private void getData() {
        db.collection("scores").document(doc_id).get(source).addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            db.collection("user").document(Constant.user.getUid()).get(source).addOnCompleteListener(
                                    new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                            if (task1.isSuccessful()) {
                                                Log.d("gggggggggg",task1.getResult().getData().toString()+"fdfdsfdsfdfsdf");
                                                try {
                                                    final String doc_id = task.getResult().getId();
                                                    int sport_id = Integer.parseInt(task.getResult().getData().get("sport_id").toString());
                                                    String description = String.valueOf(task.getResult().getData().get("round"));
                                                    String venue = String.valueOf(task.getResult().getData().get("venue"));
                                                    String team_a = String.valueOf(task.getResult().getData().get("college1"));
                                                    String team_b = String.valueOf(task.getResult().getData().get("college2"));
                                                    int winner = 0;
                                                    boolean isResult = Boolean.parseBoolean(task.getResult().getData().get("is_result").toString());
                                                    if(isResult)
                                                        winner = Integer.parseInt(task.getResult().getData().get("winner").toString());

                                                    Timestamp timestamp = (Timestamp) task.getResult().getData().get("timestamp");
                                                    Date date = timestamp.toDate();
                                                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
                                                    Calendar calNow = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
                                                    cal.setTime(date);
                                                    int status = -1;
                                                    if (isResult) {
                                                        status = 1;
                                                    } else {
                                                        if (cal.compareTo(calNow) < 0) {
                                                            status = 0;
                                                        } else {
                                                            status = -1;
                                                        }
                                                    }

//                                                    Toast.makeText(getContext(),status+"",Toast.LENGTH_SHORT).show();

                                                    score = Integer.parseInt(task1.getResult().getData().get("score").toString());
                                                    luck = Integer.parseInt(task1.getResult().getData().get("luck").toString());
                                                    bettingAmount = Integer.parseInt(task1.getResult().getData().get("betting_amount").toString());

                                                    timestamp = (Timestamp) task1.getResult().getData().get("power_bid_time");
                                                    date = timestamp.toDate();
                                                    powBidTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
                                                    powBidTime.setTime(date);

                                                    timestamp = (Timestamp) task1.getResult().getData().get("slot_time");
                                                    date = timestamp.toDate();
                                                    slotTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
                                                    slotTime.setTime(date);
                                                    //bet


                                                    array = new JSONArray(task.getResult().getData().get("roulette").toString());

                                                    boolean isBet = false;

                                                    for (int i = 0; i < array.length(); i++) {
                                                        JSONObject object = array.getJSONObject(i);
                                                        if (object.getString("user_id").equals(Constant.user.getUid())) {
                                                            isBet = true;
                                                            team = object.getString("college");
                                                            amount = object.getInt("amount");
                                                            powerBid = object.getBoolean("power_bid");

                                                            if(team.equals(team_a)){
                                                                cvTeamA.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.bid));
                                                            }else if(team.equals(team_b)){
                                                                cvTeamB.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.bid));
                                                            }



                                                        }
                                                    }

                                                    if(isBet){
                                                        disableBet();
                                                        disablePowBet();
                                                    }else{
                                                        cvTeamA.setCardBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
                                                        cvTeamB.setCardBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
                                                        enableBet();
                                                        enablePowBet();
                                                    }

                                                    itemRoulette = new ItemRoulette(
                                                            doc_id,
                                                            sport_id,
                                                            task.getResult().getData().get("sport_name").toString(),
                                                            venue,
                                                            cal,
                                                            team_a,
                                                            team_b,
                                                            winner,
                                                            description,
                                                            isBet,
                                                            status
                                                    );

                                                    viewLoader(false);
                                                    container.setVisibility(View.VISIBLE);
                                                    showData();

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    Log.d(TAG,e.getMessage());
                                                }
                                            }
                                        }
                                    }
                            );
                        }
                    }
                }

        );

    }


    @SuppressLint("SimpleDateFormat")
    public void showData() {
//        if (!event.getDescription().isEmpty()) {
//            tvDescription.setText(event.getDescription());
//        }
        tvTeamA.setText(itemRoulette.getCollege1());
        tvTeamB.setText(itemRoulette.getCollege2());
        Calendar calendar = itemRoulette.getTimestamp();
        SimpleDateFormat f1 = new SimpleDateFormat("dd MMM yyyy");
        tvDate.setText(f1.format(calendar.getTime()));
        SimpleDateFormat f2 = new SimpleDateFormat("EEE");
        tvDay.setText(f2.format(calendar.getTime()));
        SimpleDateFormat f3 = new SimpleDateFormat("hh:mm a");
        tvTime.setText(f3.format(calendar.getTime()));

        tvVenue.setText(itemRoulette.getVenue());
        tvWallet.setText("" + score);

        refreshView();
    }

    private void changeTeamColor(String team, CardView cardView) {
        if (((itemRoulette.getWinner() == 1) && (itemRoulette.getCollege1().equals(team))) || ((itemRoulette.getWinner() == 2) && (itemRoulette.getCollege2().equals(team)))) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.bosm_roulette));
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_red));
        }
    }

    private void refreshView() {
        if (itemRoulette.getStatus() == -1) {
            tvStatus.setText(getStatusName(-1));
            tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            cardViewWon.setVisibility(View.GONE);
        } else if (itemRoulette.getStatus() == 0) {
            tvStatus.setText(getStatusName(0));
            tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.fav_green));
            cardViewWon.setVisibility(View.GONE);
        } else if (itemRoulette.getStatus() == 1) {
            cardViewWon.setVisibility(View.VISIBLE);
            tvStatus.setText(getStatusName(1));
            tvWon.setText(itemRoulette.getWinner());
            tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }

        if (itemRoulette.getStatus() == 1 && itemRoulette.isBetting_done()) {
            if (team.equals(itemRoulette.getCollege1())) {
                changeTeamColor(itemRoulette.getCollege1(), cvTeamA);
            } else {
                changeTeamColor(itemRoulette.getCollege2(), cvTeamB);
            }
        }

        if (score <= 20) {
            setWalletCritical();
        } else {
            removeWalletCritical();
        }

        if (itemRoulette.isBetting_done()) {
            if (powerBid) {
                leftAlign(ivPowBet);
                tvPowBidAmount.setText("$" + amount);
                tvPowBidAmount.setVisibility(View.VISIBLE);
                freezeCard(cardViewPowBet);
                disableBet();
            } else {
                leftAlign(ivBet);
                tvBidAmount.setText("$" + amount);
                tvBidAmount.setVisibility(View.VISIBLE);
                freezeCard(cardViewBet);
                disablePowBet();
            }
        } else {
            if (itemRoulette.getStatus() != -1) {
                disableBet();
                disablePowBet();
            }
            if (powBidTime.after(Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30")))) {
                disablePowBet();
                ivPowBet.setVisibility(View.GONE);
                long end = powBidTime.getTimeInMillis();
                long start = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30")).getTimeInMillis();
                final long[] duration = {end - start};

                countDownTimer = new CountDownTimer(duration[0], 1000) {

                    public void onTick(long millisUntilFinished) {
                        long sec = TimeUnit.MILLISECONDS.toSeconds(Math.abs(duration[0])) % 60;
                        long min = TimeUnit.MILLISECONDS.toMinutes(Math.abs(duration[0])) % 60;
                        long hr = TimeUnit.MILLISECONDS.toHours(Math.abs(duration[0]));
                        tvTimerPowBet.setVisibility(View.VISIBLE);
                        tvTimerPowBet.setText(hr + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
                        duration[0] -= 1000;
                    }

                    public void onFinish() {
                        tvTimerPowBet.setVisibility(View.GONE);
                        ivPowBet.setVisibility(View.VISIBLE);
                        enablePowBet();
                    }
                }.start();
            }
        }
        if (slotTime.after(Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30")))) {
            disableSpin();
            ivSpin.setVisibility(View.GONE);
            long end = slotTime.getTimeInMillis();
            long start = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30")).getTimeInMillis();
            final long[] duration = {end - start};

            countDownTimer2 = new CountDownTimer(duration[0], 1000) {

                public void onTick(long millisUntilFinished) {
                    long sec = TimeUnit.MILLISECONDS.toSeconds(Math.abs(duration[0])) % 60;
                    long min = TimeUnit.MILLISECONDS.toMinutes(Math.abs(duration[0])) % 60;
                    long hr = TimeUnit.MILLISECONDS.toHours(Math.abs(duration[0]));
                    tvTimerSpin.setVisibility(View.VISIBLE);
                    tvTimerSpin.setText(hr + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
                    duration[0] -= 1000;
                }

                public void onFinish() {
                    tvTimerSpin.setVisibility(View.GONE);
                    ivSpin.setVisibility(View.VISIBLE);
                    enableSpin();
                }
            }.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            countDownTimer.cancel();
            countDownTimer2.cancel();
        } catch (Exception e) {
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            countDownTimer.cancel();
            countDownTimer2.cancel();
        } catch (Exception e) {
        }
    }


    private void disableBet() {
        cvBetEnabled = false;
        cardViewBet.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey));
    }

    private void disablePowBet() {
        cvPowBetEnabled = false;
        cardViewPowBet.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey));
    }

    private void disableSpin() {
        cvSpinEnabled = false;
        cardViewSpin.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.grey));
    }

    private void removeWalletCritical() {
        cardViewWallet.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.bosm_roulette));
    }

    private void setWalletCritical() {
        cardViewWallet.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.errorColor));
    }

    private void enableBet() {
        cvBetEnabled = true;
        cardViewBet.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.bid));
    }

    private void enablePowBet() {
        cvPowBetEnabled = true;
        cardViewPowBet.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.power_bid_active));
    }

    private void enableSpin() {
        cvSpinEnabled = true;
        cardViewSpin.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.slot));
    }

    private void freezeCard(CardView cardView) {
        disableBet();
        disablePowBet();
        cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
    }

    private void leftAlign(View view) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        view.setLayoutParams(lp);
    }

    private void centerAlign(View view) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        view.setLayoutParams(lp);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.card_view_wallet) {
            showWalletDialog();
        } else if (id == R.id.card_view_bid && cvBetEnabled) {
            showBetDialog(false);
        } else if (id == R.id.card_view_pow_bid && cvPowBetEnabled) {
            showBetDialog(true);
        } else if (id == R.id.card_view_spin && cvSpinEnabled) {
            showSpinDialog();
        }
    }

    private void showWalletDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_wallet);

        final TextView tvAmount = (TextView) dialog.findViewById(R.id.tvAmount);
        final TextView tvBet = (TextView) dialog.findViewById(R.id.tvBet);

        tvAmount.setText("$" + score);
        tvBet.setText("$" + bettingAmount);

        dialog.show();
    }

    private void showBetDialog(final boolean powerBid) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_bet);

        if (powerBid) {
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            ImageView ivTitle = (ImageView) dialog.findViewById(R.id.ivTitle);
            tvTitle.setText("Power Bet");
            ivTitle.setImageResource(R.drawable.power_bid);
        }

        final RadioGroup rgTeam = (RadioGroup) dialog.findViewById(R.id.rgTeam);
        final RadioButton rbA = (RadioButton) dialog.findViewById(R.id.rbA);
        final RadioButton rbB = (RadioButton) dialog.findViewById(R.id.rbB);

        rbA.setText(itemRoulette.getCollege1());
        rbB.setText(itemRoulette.getCollege2());

        if (powerBid) {
            LinearLayout container = (LinearLayout) dialog.findViewById(R.id.container);
            container.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.power_bid_active));
        }

        final EditText etAmount = (EditText) dialog.findViewById(R.id.etAmount);

        final Button bEdit = (Button) dialog.findViewById(R.id.bBid);
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = etAmount.getText().toString();
                if (value.isEmpty() || Integer.parseInt(value) <= 0) {
                    Toast.makeText(getContext(), "Enter an amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(value) > score) {
                    Toast.makeText(getContext(), "Cannot be more than your wallet", Toast.LENGTH_SHORT).show();
                    return;
                }
                int qty;
                try {
                    qty = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Not a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }
                int powBid;
                if (powerBid) {
                    powBid = 1;
                } else {
                    powBid = 0;
                }

                String team;
                if (rgTeam.getCheckedRadioButtonId() == rbA.getId()) {
                    team = itemRoulette.getCollege1();
                } else {
                    team = itemRoulette.getCollege2();
                }

                placeBetOnline(powBid, team, qty, dialog);
            }
        });

        final Button bCancel = (Button) dialog.findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    ImageView left;
    ImageView mid;
    ImageView right;


    private void showSpinDialog() {

        spin_dialog = new Dialog(getContext());
        spin_dialog.setContentView(R.layout.dialog_spin_n_earn);

        left = (ImageView) spin_dialog.findViewById(R.id.left);
        mid = (ImageView) spin_dialog.findViewById(R.id.mid);
        right = (ImageView) spin_dialog.findViewById(R.id.right);

        final TextView tvWon = (TextView) spin_dialog.findViewById(R.id.tvWon);
        tvWon.setVisibility(View.GONE);

        final Button bSpin = (Button) spin_dialog.findViewById(R.id.bSpin);
        bSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bSpin.setOnClickListener(null);
                counter = 0;
                int interval = 15 / luck;
                values = new ArrayList<Integer>();
                int base = 0;
                for (int i = 0; i < luck; i++) {
                    values.add(new Random().nextInt(interval) + base);
                    base += interval;
                }
                final int final_n1 = new Random().nextInt(luck);
                final int final_n2 = new Random().nextInt(luck);
                final int final_n3 = new Random().nextInt(luck);


//                Config config = new Config();

                boolean won = false;
                if (final_n1 == final_n2 && final_n1 == final_n3) {
                    won = true;
                }


                final boolean finalWon = won;

                coun = 12;
                CountDownTimer countDownTimer3 = new CountDownTimer(5000, 250) {

                    public void onTick(long millisUntilFinished) {
                        coun--;
                        if (coun > 0) {
                            int n1 = new Random().nextInt(15);
                            int n2 = new Random().nextInt(15);
                            int n3 = new Random().nextInt(15);

                            left.setImageResource(SportFragment.iconHash.get(items.get(n1)));
                            mid.setImageResource(SportFragment.iconHash.get(items.get(n2)));
                            right.setImageResource(SportFragment.iconHash.get(items.get(n3)));
                        } else {
                            bSpin.setVisibility(View.INVISIBLE);
                            tvWon.setVisibility(View.VISIBLE);
                            if (finalWon) {
                                tvWon.setText("WON");
                            } else {
                                tvWon.setText("LOST");
                            }
                            left.setImageResource(SportFragment.iconHash.get(items.get(final_n1)));
                            mid.setImageResource(SportFragment.iconHash.get(items.get(final_n2)));
                            right.setImageResource(SportFragment.iconHash.get(items.get(final_n3)));
                        }
                    }

                    public void onFinish() {
                        slotOnline(spin_dialog, finalWon);
                    }
                }.start();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(spin_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        spin_dialog.show();

        spin_dialog.getWindow().setAttributes(lp);
    }

    int coun = 6;
    Dialog spin_dialog;
    boolean won;

    List<Integer> values;

    int counter = 0;

    private void placeBetOnline(final int _power_bid, final String _team, final int _amount, final Dialog dialog) {

        try {
            final ProgressDialog progressDialog;

            progressDialog = ProgressDialog.show(getContext(), "", "Please Wait...", true);

            final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
            if (_power_bid == 1) {
                calendar.add(Calendar.HOUR, 4);
            }


            if (itemRoulette.isBetting_done()) {
                Toast.makeText(getContext(), "already done!", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", Constant.user.getUid());
            jsonObject.put("amount", _amount);
            jsonObject.put("college", _team);
            jsonObject.put("power_bid", _power_bid == 1);

            array.put(jsonObject);

            Map<String, Object> data = new HashMap<>();


            ArrayList<Map<String,Object>> arrayList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                Map<String,Object> data2= new HashMap<>();
                data2.put("user_id",array.getJSONObject(i).get("user_id"));
                data2.put("amount",array.getJSONObject(i).get("amount"));
                data2.put("college",array.getJSONObject(i).get("college"));
                data2.put("power_bid",array.getJSONObject(i).get("power_bid"));
                arrayList.add(data2);
            }

            data.put("roulette",arrayList);
            db.collection("scores").document(doc_id).update(data).addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                int score_ = score - _amount;
                                int betting_amount_ = bettingAmount + _amount;

                                Log.d("aaaaaaaaaa","score " + score_);
                                Map<String, Object> dataUser = new HashMap<>();
                                dataUser.put("score", score_);
                                dataUser.put("betting_amount", betting_amount_);
                                if (_power_bid == 1) {
                                    dataUser.put("power_bid_time", new Timestamp(calendar.getTime()));
                                }
                                db.collection("user").document(Constant.user.getUid()).update(dataUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task1) {
                                        if (task1.isSuccessful()) {
                                            progressDialog.dismiss();
                                            getData();
                                            dialog.dismiss();
                                        }
                                    }
                                });

                            }

                        }
                    }
            );


        } catch (JSONException e) {
        }
    }


    private void slotOnline(final Dialog dialog, final boolean won) {
        final ProgressDialog progressDialog;

        progressDialog = ProgressDialog.show(getContext(), "", "Please Wait...", true);

        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
        calendar.add(Calendar.MINUTE, 30);


        db.collection("constant").document("roulette").get(source).addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            int amount = Integer.parseInt(task.getResult().getData().get("spin_amount").toString());
                            if (won) {
                                score = score + amount;
                            }
                            final Map<String, Object> data = new HashMap<>();
                            data.put("score", score);
                            data.put("slot_time",  new Timestamp(calendar.getTime()));

                            db.collection("user").document(Constant.user.getUid()).update(data).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                getData();
                                                dialog.dismiss();
                                            }
                                        }
                                    }
                            );


                        }
                    }
                }
        );


    }
    //script

    public static String getStatusName(int status) {
        if (status == -1) {
            return "Yet to start";
        } else if (status == 0) {
            return "Running";
        } else {
            return "Finished";
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="BiddingFragment";
    }
}
