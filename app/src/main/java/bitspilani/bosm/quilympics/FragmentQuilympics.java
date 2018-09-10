package bitspilani.bosm.quilympics;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;

import bitspilani.bosm.LoginActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.quilympics.ItemQuestion;

import static com.google.common.primitives.Ints.min;

public class FragmentQuilympics extends Fragment {
    private static final String TAG = "QUILYMPICS";
    FirebaseFirestore db;
    private int roundTimePerQuestion;
    int markedAnswer = -1;
    int markedAnswer1 = -1, markedAnswer2 = -1;
    FirebaseUser user;
    int round_no, round_elimination;
//    int round_status;
    String round_desc = "";
    Source source;
    LinearLayout otp_container, elimination_container;
    ScrollView que_container;
    TextView tv_question_header, tv_question, tv_a, tv_b, tv_c, tv_d, tv_timer, tv_round,
            tv_result,
//            tv_desc,
            tv_timer_round;
    CardView cv_a, cv_b, cv_c, cv_d;
    Button bt_submit;
    EditText et_otp;
    ProgressDialog progressDialog;
    Calendar start_time_;
    Context context;

    int current_q_no = -1;

    boolean isEntered = false;

    ArrayList<ItemQuestion> questionArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quilympics, container, false);

        context = getContext();
        
        init(rootView);

        allowClickOnOptions(false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        source = Source.SERVER;

        //which round
        checkRound();

        tv_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAnswer(1);
            }
        });

        tv_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAnswer(2);
            }
        });

        tv_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAnswer(3);
            }
        });

        tv_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAnswer(4);
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_otp.getText().toString().isEmpty() || et_otp.getText().toString().length() != 4) {
                    Toast.makeText(context, "Entered OTP is not in correct format!", Toast.LENGTH_SHORT).show();
                } else {
                    submitOTP(et_otp.getText().toString());
                }
            }
        });

        return rootView;
    }

    private void init(View v) {
        tv_question_header = (TextView) v.findViewById(R.id.tv_question_header);
        tv_question = (TextView) v.findViewById(R.id.tv_question);
        tv_timer = (TextView) v.findViewById(R.id.tv_timer);
//        tv_desc = (TextView) v.findViewById(R.id.tv_desc);
        tv_timer_round = (TextView) v.findViewById(R.id.tv_timer_round);
        tv_round = (TextView) v.findViewById(R.id.tv_round);
        tv_result = (TextView) v.findViewById(R.id.tv_result);
        tv_a = (TextView) v.findViewById(R.id.tv_a);
        tv_b = (TextView) v.findViewById(R.id.tv_b);
        tv_c = (TextView) v.findViewById(R.id.tv_c);
        tv_d = (TextView) v.findViewById(R.id.tv_d);

        cv_a = (CardView) v.findViewById(R.id.cv_a);
        cv_b = (CardView) v.findViewById(R.id.cv_b);
        cv_c = (CardView) v.findViewById(R.id.cv_c);
        cv_d = (CardView) v.findViewById(R.id.cv_d);
        otp_container = (LinearLayout) v.findViewById(R.id.otp_container);
        que_container = (ScrollView) v.findViewById(R.id.que_container);
        elimination_container = (LinearLayout) v.findViewById(R.id.elimination_conatiner);

        otp_container.setVisibility(View.GONE);
        que_container.setVisibility(View.GONE);

        bt_submit = (Button) v.findViewById(R.id.bt_submit);
        et_otp = (EditText) v.findViewById(R.id.et_otp);

        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setCancelable(false);
        start_time_ = Calendar.getInstance();
    }

    private void showEliminationView(boolean is) {
        if (is) {
            otp_container.setVisibility(View.GONE);
            elimination_container.setVisibility(View.VISIBLE);
            que_container.setVisibility(View.GONE);
        } else {
            elimination_container.setVisibility(View.GONE);
            que_container.setVisibility(View.VISIBLE);
        }
    }

    private void toggleOTPVisibility(boolean is){
        if(is){
            tv_result.setVisibility(View.VISIBLE);
            bt_submit.setVisibility(View.GONE);
            et_otp.setVisibility(View.GONE);
        }else{
            tv_result.setVisibility(View.GONE);
            bt_submit.setVisibility(View.VISIBLE);
            et_otp.setVisibility(View.VISIBLE);
            et_otp.setText(null);
        }
    }

    private void checkRound() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        round_no = 1;
//        round_status = -2;
        db.collection("quilympics").orderBy("order").get(source).addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Timestamp timestamp = (Timestamp) documentSnapshot.getData().get("start_time");
                                round_desc = documentSnapshot.getData().get("desc").toString();
                                start_time_.setTime(timestamp.toDate());
                                Timestamp now = new Timestamp(Calendar.getInstance().getTime());
                                Log.d(TAG,now.compareTo(timestamp)+"");
                                if (now.compareTo(timestamp)>0) {
                                    round_no++;
                                } else {
//                                    round_status = Integer.parseInt(documentSnapshot.getData().get("status").toString());
                                    break;
                                }
                            }
                            Log.d(TAG, "got round no " + round_no);
                            if (round_no == 1) {
                                //give entry
                                giveEntry(round_no, start_time_);
                            } else if (round_no <= 10) {
                                tv_round.setText("Round " + round_no+ " : "+round_desc);
//                                tv_desc.setText(round_desc);
                                db.collection("user").document( user.getUid().toString()).get(source).addOnCompleteListener(
                                        new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (Integer.parseInt(task.getResult().getData().get("prev_round").toString()) == round_no - 1 &&
                                                            Integer.parseInt(task.getResult().getData().get("is_qualified").toString()) == 1) {
                                                        //give entry
                                                        giveEntry(round_no, start_time_);
                                                    } else {
                                                        progressDialog.dismiss();
                                                        showEliminationView(true);
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Connection error!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                );
                            } else {
                                progressDialog.dismiss();
                                loadFragment(new LeaderboardFragment());
                                //show leadberboard
//                                Toast.makeText(context, "FINISHED", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
        );

    }

    private void showOTPContainer(boolean is) {
        if (is) {
            et_otp.setText(null);
            otp_container.setVisibility(View.VISIBLE);
            que_container.setVisibility(View.GONE);
        } else {
            otp_container.setVisibility(View.GONE);
            que_container.setVisibility(View.VISIBLE);
        }
    }



    private void submitOTP(final String otp) {
        db.collection("quilympics").document("" + round_no).get(source).addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().getData().get("otp").toString().equals(otp)) {
                                isEntered = true;
                                toggleOTPVisibility(true);
                            } else {
                                Toast.makeText(context, "OTP isn't correct!", Toast.LENGTH_SHORT).show();
                                et_otp.setText(null);
                            }
                        }
                    }
                }
        );

    }

    private void giveEntry(int round, Calendar start_time) {
        progressDialog.dismiss();
        Log.d(TAG, "entry given with round no " + round);
        if (round_no <= 10) {
            showOTPContainer(true);
            startRoundTimer(start_time);
        } else {
            Toast.makeText(context, "FINISHED", Toast.LENGTH_SHORT).show();
//            finish()
            //redirect to leaderboard and results
        }

    }

    private void allowClickOnOptions(boolean is) {
        tv_a.setClickable(is);
        tv_b.setClickable(is);
        tv_c.setClickable(is);
        tv_d.setClickable(is);
    }

    private void submitAnswer(int option) {
        markedAnswer = option;
        if (round_no == 1 || round_no == 2) {
            allowClickOnOptions(false);
            switch (option) {
                case 1:
                    db.collection("quilympics").document("" + round_no).collection("questions").document("" + current_q_no).get(source).addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int tap_a = Integer.parseInt(task.getResult().get("tap_a").toString());
                                        db.collection("quilympics").document("" + round_no).collection("questions").document("" + current_q_no)
                                                .update("tap_a", tap_a + 1);
                                    }
                                }
                            }
                    );
                    cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                    cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

                    tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                    tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));

                    break;
                case 2:
                    db.collection("quilympics").document("" + round_no).collection("questions").document("" + current_q_no).get(source).addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int tap_b = Integer.parseInt(task.getResult().get("tap_b").toString());
                                        db.collection("quilympics").document("" + round_no).collection("questions").document("" + current_q_no)
                                                .update("tap_b", tap_b + 1);
                                    }
                                }
                            }
                    );
                    cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                    cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

                    tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                    tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    break;
                case 3:
                    db.collection("quilympics").document("" + round_no).collection("questions").document("" + current_q_no).get(source).addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int tap_c = Integer.parseInt(task.getResult().get("tap_c").toString());
                                        db.collection("quilympics").document("" + round_no).collection("questions").document("" + current_q_no)
                                                .update("tap_c", tap_c + 1);
                                    }
                                }
                            }
                    );
                    cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                    cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

                    tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                    tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    break;
                case 4:
                    db.collection("quilympics").document("" + round_no).collection("questions").document("" + current_q_no).get(source).addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int tap_d = Integer.parseInt(task.getResult().get("tap_d").toString());
                                        db.collection("quilympics").document("" + round_no).collection("questions").document("" + current_q_no)
                                                .update("tap_d", tap_d + 1);
                                    }
                                }
                            }
                    );
                    cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));

                    tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                    break;
            }
        } else if (round_no == 3 || round_no == 4 || round_no == 5 || round_no == 9 || round_no == 7 || round_no == 8 || round_no == 10) {
            allowClickOnOptions(false);
            switch (option) {
                case 1:
                    cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                    cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

                    tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                    tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));

                    break;
                case 2:
                    cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                    cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

                    tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                    tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    break;
                case 3:
                    cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                    cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

                    tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                    tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    break;
                case 4:
                    cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                    cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));

                    tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
                    tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                    break;
            }
        } else if (round_no == 6) {
            if (!is_click) {
                is_click = true;
                markedAnswer1 = option;
                switch (option) {
                    case 1:
                        cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                        cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                        cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                        cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

                        tv_a.setClickable(false);
                        tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                        tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));

                        break;
                    case 2:
                        tv_b.setClickable(false);
                        cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                        cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                        cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                        cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

                        tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                        tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        break;
                    case 3:
                        tv_c.setClickable(false);
                        cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                        cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                        cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                        cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

                        tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                        tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        break;
                    case 4:
                        tv_d.setClickable(false);
                        cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                        cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                        cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
                        cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));

                        tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
                        tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                        break;
                }
            } else {
                markedAnswer2 = option;
                allowClickOnOptions(false);
                switch (option) {
                    case 1:
                        cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                        tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                        break;
                    case 2:
                        cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                        tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                        break;
                    case 3:
                        cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                        tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                        break;
                    case 4:
                        cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background2));
                        tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                        break;
                }
            }
        }
    }

    boolean is_click = false;

    private void getRoundDetails(final int round) {
        db.collection("quilympics").document(String.valueOf(round)).get(source).addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
//                            roundType = Integer.parseInt(task.getResult().getData().get("round_type").toString());
                            roundTimePerQuestion = Integer.parseInt(task.getResult().getData().get("round_time_per_question").toString()) * 1000;
                            round_elimination = Integer.parseInt(task.getResult().getData().get("elimination").toString());
//                            db.collection("quilympics").document("" + round).update("status", 0);
                            getRoundQuestions(round);
                        } else {
                            Toast.makeText(context, "Connection error!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );

    }

    private void getRoundQuestions(final int round) {
        db.collection("quilympics").document("" + round).collection("questions").get(source).addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "got questions " + task.getResult().size());
                            questionArrayList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (round == 1 || round == 2) {
                                    ItemQuestion itemQuestion = new ItemQuestion(
                                            Integer.parseInt(document.getId()),
                                            document.getData().get("question").toString(),
                                            document.getData().get("option_a").toString(),
                                            document.getData().get("option_b").toString(),
                                            document.getData().get("option_c").toString(),
                                            document.getData().get("option_d").toString());

                                    questionArrayList.add(itemQuestion);
                                } else if (round == 3 || round == 5 || round == 7 || round == 8 || round == 9 || round == 10) {
                                    Log.d(TAG, "round 3 questions fetched");
                                    ItemQuestion itemQuestion = new ItemQuestion(
                                            Integer.parseInt(document.getId()),
                                            document.getData().get("question").toString(),
                                            document.getData().get("option_a").toString(),
                                            document.getData().get("option_b").toString(),
                                            document.getData().get("option_c").toString(),
                                            document.getData().get("option_d").toString(),
                                            Integer.parseInt(document.getData().get("answer").toString()));
                                    questionArrayList.add(itemQuestion);
                                } else if (round == 6) {
                                    Log.d(TAG, "round 6 questions fetched");
                                    ItemQuestion itemQuestion = new ItemQuestion(
                                            Integer.parseInt(document.getId()),
                                            document.getData().get("question").toString(),
                                            document.getData().get("option_a").toString(),
                                            document.getData().get("option_b").toString(),
                                            document.getData().get("option_c").toString(),
                                            document.getData().get("option_d").toString(),
                                            Integer.parseInt(document.getData().get("answer1").toString()),
                                            Integer.parseInt(document.getData().get("answer2").toString())
                                    );
                                    questionArrayList.add(itemQuestion);
                                }
                            }
                            progressDialog.dismiss();
                            showOTPContainer(false);
                            displayQuestion(1, round);
                        } else {

                        }
                    }
                }
        );
    }

    private void displayQuestion(int q_number, int round) {
        int total_question = questionArrayList.size();
        if (q_number <= total_question) {
            tv_question_header.setText("Question " + q_number + "/" + total_question);
            tv_question.setText(questionArrayList.get(q_number - 1).getQuestion());
            tv_a.setText(questionArrayList.get(q_number - 1).getOptionA());
            tv_b.setText(questionArrayList.get(q_number - 1).getOptiionB());
            tv_c.setText(questionArrayList.get(q_number - 1).getOptionC());
            tv_d.setText(questionArrayList.get(q_number - 1).getOptionD());
            cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
            cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
            cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
            cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

            tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
            tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
            tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
            tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));
            current_q_no = q_number;
            allowClickOnOptions(true);
            startTimer(q_number, round);
        } else {
            progressDialog.setMessage("Getting Round Results ...");
            progressDialog.show();
            Log.d(TAG, "Elimination required");
            // break condition
            doEliminiation(round);
        }
    }

    //UTILITY FUNCTION
    public static int max(int a, int b, int c, int d) {

        int max = a;

        if (b > max)
            max = b;
        if (c > max)
            max = c;
        if (d > max)
            max = d;

        return max;
    }


    int ans = -1;

    public void startTimer(final int question, final int round) {
        new CountDownTimer(roundTimePerQuestion + 1400, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (((int) millisUntilFinished / 1000 - 1) < 10) {
                    tv_timer.setText("0" + String.valueOf(millisUntilFinished / 1000 - 1));
                } else {
                    tv_timer.setText(String.valueOf(millisUntilFinished / 1000 - 1) + "");
                }

            }

            @Override
            public void onFinish() {
                //answer verification here

                allowClickOnOptions(false);
                progressDialog.setMessage("Checking Answer... ");
                progressDialog.show();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 10s = 10000ms
                        verifyAnswerToServer(round, question);
                    }
                }, 10000);
            }
        }.start();
    }

    public void startRoundTimer(final Calendar start_time) {
        Calendar calendarNow = Calendar.getInstance();
        long left = start_time.getTimeInMillis() - calendarNow.getTimeInMillis();
        new CountDownTimer(left, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (((int) millisUntilFinished / 1000 - 1) < 10) {
                    tv_timer_round.setText("Round will start in 0" + String.valueOf(millisUntilFinished / 1000 - 1));
                } else {
                    tv_timer_round.setText("Round will start in " + String.valueOf(millisUntilFinished / 1000 - 1) + "");
                }
            }

            @Override
            public void onFinish() {
                db.collection("quilympics").document("" + round_no).update("status", 1);
                toggleOTPVisibility(false);
                if (!isEntered) {
                    isEntered = true;
                    et_otp.setEnabled(false);
                    bt_submit.setOnClickListener(null);
                    db.collection("user").document(user.getUid()).update("is_qualified", 0);
                    showEliminationView(true);
                }else{
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("score", 0);
                    db.collection("quilympics").document("" + round_no).collection("user").document(user.getUid()).set(data, SetOptions.merge());
                    db.collection("user").document(user.getUid()).update("quilympics_score",0);
                    progressDialog.setMessage("Fetching Questions ...");
                    progressDialog.show();
                    getRoundDetails(round_no);
                }
            }
        }.start();
    }

    private void verifyAnswerToServer(final int round, final int question) {
        if (round == 1) {
            ans = -1;
            db.collection("quilympics").document("" + round).collection("questions").document("" + question).get(source).addOnCompleteListener(
                    new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                int a = Integer.parseInt(task.getResult().getData().get("tap_a").toString());
                                int b = Integer.parseInt(task.getResult().getData().get("tap_b").toString());
                                int c = Integer.parseInt(task.getResult().getData().get("tap_c").toString());
                                int d = Integer.parseInt(task.getResult().getData().get("tap_d").toString());

                                ans = max(a, b, c, d);

                                if (ans == a) {
                                    updateSolution(1, round, question);
                                } else if (ans == b) {
                                    updateSolution(2, round, question);
                                } else if (ans == c) {
                                    updateSolution(3, round, question);
                                } else {
                                    updateSolution(4, round, question);
                                }
                            }
                        }
                    }
            );

        } else if (round == 2) {
            ans = -1;
            db.collection("quilympics").document("" + round).collection("questions").document("" + question).get(source).addOnCompleteListener(
                    new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                int a = Integer.parseInt(task.getResult().getData().get("tap_a").toString());
                                int b = Integer.parseInt(task.getResult().getData().get("tap_b").toString());
                                int c = Integer.parseInt(task.getResult().getData().get("tap_c").toString());
                                int d = Integer.parseInt(task.getResult().getData().get("tap_d").toString());

                                ans = min(a, b, c, d);

                                if (ans == a) {
                                    updateSolution(1, round, question);
                                } else if (ans == b) {
                                    updateSolution(2, round, question);
                                } else if (ans == c) {
                                    updateSolution(3, round, question);
                                } else {
                                    updateSolution(4, round, question);
                                }
                            }
                        }
                    }
            );
        } else if (round == 3 || round == 4 || round == 5 || round_no == 9 || round_no == 7 || round_no == 8 || round_no == 10) {
            ans = questionArrayList.get(question - 1).getAnswer();
            updateSolution(ans, round, question);
        } else if (round == 6) {
            updateSolution(questionArrayList.get(question - 1).getAnswer1(), questionArrayList.get(question - 1).getAnswer2(), round, question);
        }

    }

    private void updateUI(int answer) {
        cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
        cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
        cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
        cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

        tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
        tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
        tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
        tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));
        if (markedAnswer == 1) {
            cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        } else if (markedAnswer == 2) {
            cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        } else if (markedAnswer == 3) {
            cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        } else if (markedAnswer == 4) {
            cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        }
        switch (answer) {
            case 1:
                cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                break;
            case 2:
                cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                break;
            case 3:
                cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));

                break;
            case 4:
                cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                break;
        }

    }

    private void updateUI(int answer1, int answer2) {
        cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
        cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
        cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));
        cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_shade4));

        tv_a.setTextColor(ContextCompat.getColor(context, R.color.background2));
        tv_b.setTextColor(ContextCompat.getColor(context, R.color.background2));
        tv_c.setTextColor(ContextCompat.getColor(context, R.color.background2));
        tv_d.setTextColor(ContextCompat.getColor(context, R.color.background2));
        if (answer1 == 1) {
            cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        } else if (answer1 == 2) {
            cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        } else if (answer1 == 3) {
            cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        } else if (answer1 == 4) {
            cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        }
        if (answer2 == 1) {
            cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        } else if (answer2 == 2) {
            cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        } else if (answer2 == 3) {
            cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        } else if (answer2 == 4) {
            cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange_shade));
            tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
        }
        switch (answer1) {
            case 1:
                cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                break;
            case 2:
                cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                break;
            case 3:
                cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));

                break;
            case 4:
                cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                break;
        }
        switch (answer2) {
            case 1:
                cv_a.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_a.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                break;
            case 2:
                cv_b.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_b.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                break;
            case 3:
                cv_c.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_c.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));

                break;
            case 4:
                cv_d.setCardBackgroundColor(ContextCompat.getColor(context, R.color.fav_green));
                tv_d.setTextColor(ContextCompat.getColor(context, R.color.back_shade4));
                break;
        }

    }


    private void updateSolution(int answer, final int round, final int question) {
        //update UI
        progressDialog.dismiss();
        final int this_score;
        int markedOption = getMarkedOption();
        if (markedOption == answer) {
            updateUI(answer);
            this_score = 1;
        } else {
            updateUI(answer);
            this_score = 0;
        }
        db.collection("quilympics").document("" + round).collection("user").document(user.getUid()).get(source).addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            int server_score = Integer.parseInt(task.getResult().getData().get("score").toString());
                            task.getResult().getReference().update("score", (this_score + server_score)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        displayQuestion(question + 1, round);
                                    }
                                }
                            });
                        }
                    }
                }
        );
    }

    private void updateSolution(int answer1, int answer2, final int round, final int question) {
        //update UI
        progressDialog.dismiss();
        final int this_score;
        updateUI(answer1, answer2);
        if ((markedAnswer1 == answer1 && markedAnswer2 == answer2) || (markedAnswer1 == answer2 && markedAnswer2 == answer1)) {
            this_score = 1;
        } else {
            this_score = 0;
        }
        db.collection("quilympics").document("" + round).collection("user").document(user.getUid()).get(source).addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            int server_score = Integer.parseInt(task.getResult().getData().get("score").toString());
                            task.getResult().getReference().update("score", (this_score + server_score)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        displayQuestion(question + 1, round);
                                    }
                                }
                            });
                        }
                    }
                }
        );
    }

    private int getMarkedOption() {
        //TODO::
        return markedAnswer;
    }


    private void doEliminiation(final int round) {
        db.collection("quilympics").document("" + round).update("status", 1);
        if (round == 1 || round == 2) {
            db.collection("quilympics").document("" + round).collection("user").get(source).addOnCompleteListener(
                    new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            int score = 0;
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    score = score + Integer.parseInt(documentSnapshot.getData().get("score").toString());
//                                    if(documentSnapshot.getData().get("score").toString().equals("0")) {
//                                        db.collection("user").document(documentSnapshot.getId()).update("is_qualified", 0);
//                                    }else{
                                    db.collection("user").document(documentSnapshot.getId()).update("is_qualified", 1);
//                                    }9
                                    db.collection("user").document(documentSnapshot.getId()).update("prev_round", round);

                                    progressDialog.dismiss();
                                    checkRound();
                                    db.collection("user").document(documentSnapshot.getId()).update("curr_round", round);
                                }
                                final int finalScore = score;
                                db.collection("user").document(user.getUid()).get(source).addOnCompleteListener(
                                        new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    int curr_score = 0;
                                                    if(task.getResult().getData().containsKey("quilympics_score")){
                                                        curr_score = Integer.parseInt(task.getResult().getData().get("quilympics_score").toString());
                                                    }
                                                    task.getResult().getReference().update("quilympics_score",(curr_score + finalScore));
                                                }
                                            }
                                        }
                                );
                            }
                        }
                    }
            );

        } else if (round == 3 || round == 4 || round == 5 || round == 6 || round == 7 || round == 8 || round == 9) {
            db.collection("quilympics").document("" + round).collection("user").orderBy("score", Query.Direction.ASCENDING).get(source).addOnCompleteListener(
                    new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int total_user = task.getResult().size();
                                int elimination_no = (total_user * round_elimination) / 100;
                                if (total_user - elimination_no < 5) {
                                    elimination_no = total_user - 5;
                                }
                                int i = 1;
                                int score = 0;
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    score = score + Integer.parseInt(documentSnapshot.getData().get("score").toString());
                                    if (i <= elimination_no) {
                                        db.collection("user").document(documentSnapshot.getId()).update("is_qualified", 0);
                                    } else {
                                        db.collection("user").document(documentSnapshot.getId()).update("is_qualified", 1);
                                    }
                                    i++;
                                    db.collection("user").document(documentSnapshot.getId()).update("prev_round", round);
                                }
                                final int finalScore = score;
                                db.collection("user").document(user.getUid()).get(source).addOnCompleteListener(
                                        new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    int curr_score = 0;
                                                    if(task.getResult().getData().containsKey("quilympics_score")){
                                                        curr_score = Integer.parseInt(task.getResult().getData().get("quilympics_score").toString());
                                                    }
                                                    task.getResult().getReference().update("quilympics_score",(curr_score + finalScore));
                                                }
                                            }
                                        }
                                );
//                                db.collection("quilympics").document("" + round).update("status", 1);
                                progressDialog.dismiss();
                                checkRound();
                            }
                        }
                    }
            );
        } else if (round == 10) {
            db.collection("quilympics").document("" + round).collection("user").orderBy("score", Query.Direction.ASCENDING).get(source).addOnCompleteListener(
                    new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int score = 0;
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    score = score + Integer.parseInt(documentSnapshot.getData().get("score").toString());
                                    db.collection("user").document(documentSnapshot.getId()).update("prev_round", round);
                                }
                                final int finalScore = score;
                                db.collection("user").document(user.getUid()).get(source).addOnCompleteListener(
                                        new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    int curr_score = 0;
                                                    if (task.getResult().getData().containsKey("quilympics_score")) {
                                                        curr_score = Integer.parseInt(task.getResult().getData().get("quilympics_score").toString());
                                                    }
                                                    task.getResult().getReference().update("quilympics_score", (curr_score + finalScore)).addOnCompleteListener(
                                                            new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        progressDialog.dismiss();
                                                                        loadFragment(new LeaderboardFragment());
                                                                    }
                                                                }
                                                            }
                                                    );
                                                }
                                            }
                                        }
                                );
                            }}});


            Toast.makeText(context, "GAME UP! DISPLAY LEADERBOARD....", Toast.LENGTH_SHORT).show();
        }
        //eliminate and update user collection
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.commit();
    }

}