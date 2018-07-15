package bitspilani.bosm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.paytm.pgsdk.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bitspilani.bosm.items.ItemCollege;
import bitspilani.bosm.utils.Constant;


public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "ABCDEF";
    ArrayList<ItemCollege> collegeArrayList;
    ArrayList<String> ar_college = new ArrayList<String>();
    ArrayList<String> ar_sport = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Initializing Objects
        ImageButton button_register= (ImageButton) findViewById(R.id.ib_login);
        collegeArrayList=new ArrayList<>();

        getSportList();




        //Dropdown menu for player/coach
        final Spinner spinner_registerAs = findViewById(R.id.spinner_registerAs);
            String[] items = new String[]{"","PLAYER", "CAPTAIN", "COACH"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_registeras, items);
        adapter.setDropDownViewResource(R.layout.spinner_item_registeras);
        spinner_registerAs.setAdapter(adapter);

        //Dropdown menu for college
        String[] colleges = new String[ar_college.size()];
        colleges = ar_college.toArray(colleges);
        final Spinner spinner_college = findViewById(R.id.spinner_college);
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("Bits Pilani");
        arr.add("BITS Goa");
        arr.add("Bits Goa");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item_college, ar_college);
        adapter2.setDropDownViewResource(R.layout.spinner_item_college);
        spinner_college.setAdapter(adapter2);

        //Dropdown menu for gender
        final Spinner spinner_gender = findViewById(R.id.spinner_gender);
        String[] items_gender = new String[]{"","MALE", "FEMALE"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_gender, items_gender);
        adapter.setDropDownViewResource(R.layout.spinner_item_gender);
        spinner_gender.setAdapter(adapter3);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edit_name = (EditText) findViewById(R.id.edittext_name);
                EditText edit_email = (EditText) findViewById(R.id.edittext_email);
                EditText edit_username = (EditText) findViewById(R.id.edittext_username);
                EditText edit_password = (EditText) findViewById(R.id.edittext_password);
                EditText edit_phone = (EditText) findViewById(R.id.edittext_phone);
                TextView text_sport = (TextView) findViewById(R.id.text_sport);

                String name = edit_name.getText().toString();
                String email = edit_email.getText().toString();
                String username = edit_username.getText().toString();
                String password = edit_password.getText().toString();
                String registerAs = spinner_registerAs.getSelectedItem().toString();
                String phone = edit_phone.getText().toString();
                String college = spinner_college.getSelectedItem().toString();
                String sport = text_sport.getText().toString();
                String gender = spinner_gender.getSelectedItem().toString();

                sendDetails(name,email,username,password,registerAs,phone,college,sport,gender);
            }
        });

        //AlertDialog for sport selection Starts
        Button button_sport = (Button) findViewById(R.id.button_sport);
        final TextView text_sport= (TextView) findViewById(R.id.text_sport);


        button_sport.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);

                String[] sports = new String[ar_sport.size()];
                sports = ar_sport.toArray(sports);

                /*String[] sports = new String[]{
                        "CRICKET",
                        "SWIMMING",
                        "FOOTBALL",
                        "HOCKEY",
                        "TENNIS",
                        "TABLE TENNIS",
                        "TAEKWANDO (BOYS)"
                };*/

                // Boolean array for initial selected items
                final boolean[] checkedSports = new boolean[ar_sport.size()];
                Arrays.fill(checkedSports,false);

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

    public void getSportList(){
//        Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
//                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
//                progressBar.setVisibility(View.GONE);
                super.onPostExecute(s);
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    URL url = new URL("http://10.0.2.2:8000/register/sportlist_app/");
//                  String urlParams = "email=" + Constant.currentItemUser.getUser_email();

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    StringBuilder sb = new StringBuilder();

//                    OutputStream os = con.getOutputStream();
//                    os.write(urlParams.getBytes());
//                    os.flush();
 //                   os.close();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    String s = sb.toString().trim();
                    return s;

                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }

            private void parseJSON(String json) {
                try {
//                   Toast.makeText(WalletActivity.this,json,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response: " + json);
                    //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                    JSONObject jobject= new JSONObject(json);

                    JSONArray collegeArray = jobject.getJSONArray("college");

                    for(int i=0; i< collegeArray.length();i++){

                        JSONArray c = collegeArray.getJSONArray(i);
                        String college =  c.getString(0);
                        String city =  c.getString(1);
                        String state =  c.getString(2);

                        String collegeFull = college + ", " + city + ", " + state;
                        ar_college.add(collegeFull);

                        ItemCollege itemCollege = new ItemCollege(
                                c.getString(0),
                                c.getString(1),
                                c.getString(2),
                                c.getInt(3)
                        );
                    }

                    JSONArray sportArray = jobject.getJSONArray("data");
                    for(int i=0; i<sportArray.length(); i++){

                        JSONArray s = sportArray.getJSONArray(i);
                        String sport = s.getString(1);
                        ar_sport.add(sport);
                    }
//                    JSONObject root = new JSONObject(json);
//                    JSONObject response = root.getJSONObject("response");
//                    boolean success = response.getBoolean("success");
//                    if(success){
//                        double wallet = response.getDouble("balance");
//                        Constant.currentItemUser.setUser_wallet(wallet);
//                        textView_balance.setText(getResources().getString(R.string.Rs) + " "+ wallet);
//                    }else{
//                        textView_balance.setText(getResources().getString(R.string.Rs)+" ---");
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    public void sendDetails(final String name, final String email, final String username, final String password, final String registerAs, final String phone, final String college, final String sport, final String gender){
//        Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
//                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
//                progressBar.setVisibility(View.GONE);
                super.onPostExecute(s);
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    URL url = new URL("http://10.0.2.2:8000/register/sportlist_app/");
                  //String urlParams = "email=" + Constant.currentItemUser.getUser_email();
                    String urlName = "name=" + name;
                    String urlEmail = "email=" + email;
                    String urlUsername = "username=" + username;
                    String urlPassword = "password=" + password;
                    String urlRegisterAs = "registerAs=" + registerAs;
                    String urlPhone = "phone=" + phone;
                    String urlCollege = "college=" + Constant.currentItemCollege.getCollege_id();
                    String urlSport = "sport=" + sport;
                    String urlGender = "gender=" + gender;
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    StringBuilder sb = new StringBuilder();

                   OutputStream os = con.getOutputStream();
                    os.write(urlName.getBytes());
                    os.write(urlEmail.getBytes());
                    os.write(urlUsername.getBytes());
                    os.write(urlPassword.getBytes());
                    os.write(urlRegisterAs.getBytes());
                    os.write(urlPhone.getBytes());
                    os.write(urlCollege.getBytes());
                    os.write(urlSport.getBytes());
                    os.write(urlGender.getBytes());
                    os.flush();
                    os.close();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    String s = sb.toString().trim();
                    return s;

                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }

            private void parseJSON(String json) {
                try {
//                   Toast.makeText(WalletActivity.this,json,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response: " + json);
                    //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();

//                    JSONObject root = new JSONObject(json);
//                    JSONObject response = root.getJSONObject("response");
//                    boolean success = response.getBoolean("success");
//                    if(success){
//                        double wallet = response.getDouble("balance");
//                        Constant.currentItemUser.setUser_wallet(wallet);
//                        textView_balance.setText(getResources().getString(R.string.Rs) + " "+ wallet);
//                    }else{
//                        textView_balance.setText(getResources().getString(R.string.Rs)+" ---");
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }




}