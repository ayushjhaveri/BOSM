package bitspilani.bosm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;



public class RegistrationActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        //Dropdown menu for player/coach
        Spinner spinner_registerAs = findViewById(R.id.spinner_registerAs);
            String[] items = new String[]{"","PLAYER", "COACH"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_registeras, items);
        adapter.setDropDownViewResource(R.layout.spinner_item_registeras);
        spinner_registerAs.setAdapter(adapter);

        //Dropdown menu for college
        Spinner spinner_college = findViewById(R.id.spinner_college);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.College,
                R.layout.spinner_item_college);
        adapter2.setDropDownViewResource(R.layout.spinner_item_college);
        spinner_college.setAdapter(adapter2);

        //Dropdown menu for gender
        Spinner spinner_gender = findViewById(R.id.spinner_gender);
        String[] items_gender = new String[]{"","MALE", "FEMALE"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_gender, items_gender);
        adapter.setDropDownViewResource(R.layout.spinner_item_gender);
        spinner_gender.setAdapter(adapter3);

        //AlertDialog for sport selection Starts
        Button button_sport = (Button) findViewById(R.id.button_sport);
        final TextView text_sport= (TextView) findViewById(R.id.text_sport);

        button_sport.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity2.this);
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


                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();


            }
        });

        //AlertDialog for sport selection Ends




    }




}