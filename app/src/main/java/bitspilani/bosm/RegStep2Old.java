package bitspilani.bosm;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class RegStep2Old extends AppCompatActivity implements View.OnClickListener {

    ImageView imageInfo;
    RelativeLayout relativeLayout_cricket,relativeLayout_swimming;
    CardView card_cricket, card_swimming;
    int height_cricket, height_swimming;
    Button refreshReg, addMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_regstep2);

        relativeLayout_cricket = (RelativeLayout) findViewById(R.id.expandable_cricket);
        relativeLayout_swimming = (RelativeLayout) findViewById(R.id.expandable_swimming);

        card_cricket = (CardView) findViewById(R.id.card_cricket);
        card_swimming = (CardView) findViewById(R.id.card_swimming);

        card_cricket.setOnClickListener(this);
        card_swimming.setOnClickListener(this);

        relativeLayout_cricket.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        relativeLayout_cricket.getViewTreeObserver().removeOnPreDrawListener(this);
                        relativeLayout_cricket.setVisibility(View.GONE);
                        relativeLayout_swimming.setVisibility(View.GONE);
                        //relativeLayout2.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        relativeLayout_cricket.measure(widthSpec, heightSpec);
                        height_cricket = relativeLayout_cricket.getMeasuredHeight();
                        relativeLayout_swimming.measure(widthSpec, heightSpec);
                        height_swimming = relativeLayout_swimming.getMeasuredHeight();
                        //relativeLayout2.measure(widthSpec, heightSpec);
                        //height2 = relativeLayout.getMeasuredHeight();
                        return true;
                    }
                });

        imageInfo = (ImageView) findViewById(R.id.icon_info);
        imageInfo.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegStep2Old.this, WelcomeLogin.class));
            }

        });

        //Refresh Registered Button
        refreshReg = (Button)findViewById(R.id.btn_refresh_cricket);
        refreshReg.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v) {
                //Do Something
            }});

        //Add Participant Button
        addMember = (Button)findViewById(R.id.btn_add_cricket);
        addMember.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v) {
                //Do Something
                AlertDialog.Builder builder = new AlertDialog.Builder(RegStep2Old.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_old_addparticipant, null);

                //AlertDialog for sport selection Starts
                Button button_sport = (Button) mView.findViewById(R.id.button_sport);
                final TextView text_sport= (TextView) mView.findViewById(R.id.text_sport);

                button_sport.setOnClickListener(new View.OnClickListener()   {
                    @Override
                    public void onClick(View v) {
                        // Build an AlertDialog
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegStep2Old.this);
                        String[] sports = new String[]{
                                "CRICKET",
                                "SWIMMING",
                                "FOOTBALL",
                                "HOCKEY",
                                "TENNIS",
                                "TABLE TENNIS",
                                "TAEKWANDO (BOYS)"
                        };

                        // Boolean array for initial selected items
                        final boolean[] checkedSports = new boolean[]{
                                false,
                                false,
                                false,
                                false,
                                false,
                                false,
                                false
                        };

                        final List<String> sportsList = Arrays.asList(sports);

                        builder.setMultiChoiceItems(sports, checkedSports, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                                // Update the current focused item's checked status
                                checkedSports[which] = isChecked;

                                // Get the current focused item
                                String currentItem = sportsList.get(which);

                                // Notify the current action
                                //Toast.makeText(getApplicationContext(),
                                // currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Specify the dialog is not cancelable
                        builder.setCancelable(false);

                        // Set a title for alert dialog
                        builder.setTitle("Select a sport(s)");

                        // Set the positive/yes button click listener
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something when click positive button
                                //text_sport.setText("Your preferred colors..... \n");
                                text_sport.setText("");

                                for (int i = 0; i<checkedSports.length; i++){

                                    boolean checked = checkedSports[i];

                                    if (checked && text_sport.getText().equals("")) {
                                        text_sport.setText(sportsList.get(i) );
                                    }
                                    else if (checked) text_sport.setText(text_sport.getText() +"\n "+ sportsList.get(i) );

                                }
                            }
                        });


                        // Set the neutral/cancel button click listener
                        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something when click the neutral button
                            }
                        });


                        android.app.AlertDialog dialog = builder.create();
                        // Display the alert dialog on interface
                        dialog.show();


                    }
                });


                builder.setView(mView);
                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();


            }});
    }


    private void expand(RelativeLayout layout, int layoutHeight) {
        layout.setVisibility(View.VISIBLE);
        ValueAnimator animator = slideAnimator(layout, 0, layoutHeight);
        animator.start();
    }

    private void collapse(final RelativeLayout layout) {
        int finalHeight = layout.getHeight();
        ValueAnimator mAnimator = slideAnimator(layout, finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                layout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }


    private ValueAnimator slideAnimator(final RelativeLayout layout, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
                layoutParams.height = value;
                layout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_cricket:
                if (relativeLayout_cricket.getVisibility() == View.GONE) {
                    expand(relativeLayout_cricket, height_cricket);
                    collapse(relativeLayout_swimming);
                } else {
                    collapse(relativeLayout_cricket);
                }
                break;

            case R.id.card_swimming:
                if (relativeLayout_swimming.getVisibility() == View.GONE) {
                    expand(relativeLayout_swimming, height_swimming);
                    collapse(relativeLayout_cricket);
                } else {
                    collapse(relativeLayout_swimming);
                }
                break;

            //case R.id.viewmore2:
            //   if (relativeLayout2.getVisibility() == View.GONE) {
            //      expand(relativeLayout2, height2);
            //   } else {
            //       collapse(relativeLayout2);
            //   }
            //    break;
        }
    }




    }

