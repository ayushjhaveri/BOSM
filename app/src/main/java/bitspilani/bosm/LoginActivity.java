package bitspilani.bosm;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import bitspilani.bosm.items.ItemUser;
import bitspilani.bosm.utils.Constant;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "LoginActivity";
    Button signupButton;
    ImageButton loginButton;
    FloatingActionButton  g_login_button;
    SignInButton googleSignInButton;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    RequestQueue queue;
    ImageView progressBar;

    EditText et_username;


    private SharedPreferences profileSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startActivity(new Intent(this,HomeActivity.class));
        finish();
        //initializing Objects
        init();

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        login(account);

        //Listener to
        signupButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), RegistrationOld.class);
//                startActivity(intent);

            }
        });

//        et_username.setText(Constant.BASE_URL);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String hashed = BCrypt.hashpw(et_username.getText().toString(), BCrypt.gensalt(12));
//                Log.d(TAG,"HASHED: "+hashed);
//                Snackbar.make(view,hashed,Snackbar.LENGTH_INDEFINITE).show();
//                et_username.setText(hashed);
//                Constant.BASE_URL = et_username.getText().toString();
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                startActivity(new Intent(LoginActivity.this, WelcomeLogin.class));
            }
        });

        //GoogleSignIn Button Listener
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                signIn();
            }
        });

        g_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                signIn();
            }
        });

    }

    private void init() {

        signupButton = (Button) findViewById(R.id.button_signup);
        loginButton = (ImageButton) findViewById(R.id.ib_login);
        googleSignInButton = (SignInButton) findViewById(R.id.button_g_sign_in);

        et_username = (EditText) findViewById(R.id.et_username);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        queue = Volley.newRequestQueue(this);  // this

        progressBar =(ImageView) findViewById(R.id.progressBar);
        g_login_button= (FloatingActionButton) findViewById(R.id.fab_g_login);
        profileSharedPreferences = getSharedPreferences(Constant.PROFILE_SHARED_PREFERENCES,MODE_PRIVATE);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            login(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this,getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
        }
    }

//    private void updateUI(final GoogleSignInAccount account) {
//
//        //validating if user is signed up or not
//        StringRequest postRequest = new StringRequest(Request.Method.POST, Constant.URL_VALIDATING_ACCCOUNT,
//                new Response.Listener<String>(){
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d(TAG, "Response: " + response);
//                        Toast.makeText(LoginActivity.this,"Respoonse " +response,Toast.LENGTH_LONG).show();
////                        try {
////                            JSONObject root = new JSONObject(response);
////                            boolean isSignedUp = root.getBoolean("isSignedUp");
////
////                            if (isSignedUp) {
////
////                                //initiate Login
////                                JSONObject login_root = root.getJSONObject("login");
////                                boolean isSuccess = login_root.getBoolean("success");
////                                String message = login_root.getString("message");
////                                if(isSuccess){
////                                    Constant.currentItemUser = new ItemUser(
////                                            login_root.getString("user_name"),
////                                            login_root.getString("user_gender"),
////                                            login_root.getString("user_email"),
////                                            login_root.getInt("user_phone"),
////                                            login_root.getString("user_google_profile_image"),
////                                            Float.parseFloat(login_root.getString("user_wallet"))
////                                    );
////                                    Constant.login(account.getEmail(),profileSharedPreferences);
////                                }else{
////                                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
////                                }
////                            } else {
////
////                                //initiate Signup
//////                                signUp(account);
////                            }
////
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d(TAG, "Error Response: " + error.toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put(Constant.USER_EMAIL, account.getEmail());
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//        };
//        postRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//        queue.add(postRequest);
//    }

    private void signUp(GoogleSignInAccount account) {

    }

    public void login(final GoogleSignInAccount account){
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                progressBar.setVisibility(View.GONE);
                super.onPostExecute(s);
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    URL url = new URL(Constant.URL_VALIDATING_ACCCOUNT);
                    String urlParams = "email=" + account.getEmail()+
                            "&name="+account.getDisplayName();

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    StringBuilder sb = new StringBuilder();
//
                    OutputStream os = con.getOutputStream();
                    os.write(urlParams.getBytes());
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
//                    Toast.makeText(LoginActivity.this,json,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response: " + json);
                    JSONObject root = new JSONObject(json);
                    JSONObject response = root.getJSONObject("response");
                    int id = response.getInt("id");
                    String name = response.getString("name");
                    String email = response.getString("email");
                    double wallet = response.getDouble("wallet");
                    ItemUser itemUser = new ItemUser(id,name,email,wallet);
                    Constant.currentItemUser = itemUser;
                    Constant.IS_LOGIN =true;
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

}


