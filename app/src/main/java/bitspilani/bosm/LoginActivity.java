package bitspilani.bosm;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.SetOptions;

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
    ProgressBar progressBar;

    EditText et_username;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        init();
        signupButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        //GoogleSignIn Button Listener
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        g_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLoader(true);
                signIn();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Constant.user = currentUser;
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
    }

    private void viewLoader(boolean is){
        if(is){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }


    private void init() {

        signupButton = (Button) findViewById(R.id.button_signup);
        loginButton = (ImageButton) findViewById(R.id.ib_login);
        googleSignInButton = (SignInButton) findViewById(R.id.button_g_sign_in);

        et_username = (EditText) findViewById(R.id.et_username);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        queue = Volley.newRequestQueue(this);  // this

        progressBar =(ProgressBar) findViewById(R.id.progressBar);
        g_login_button= (FloatingActionButton) findViewById(R.id.fab_g_login);
//        profileSharedPreferences = getSharedPreferences(Constant.PROFILE_SHARED_PREFERENCES,MODE_PRIVATE);

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
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            viewLoader(false);
            Toast.makeText(this,getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this,"Connection error!",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private void updateUI(final FirebaseUser user){
        if(user!=null){
            Constant.user = user;
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);

            db.collection("user").document(user.getUid()).get().addOnCompleteListener(
                    new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if(task.getResult().getData()==null){
                                    //user doesn't exist
                                    Toast.makeText(LoginActivity.this,"han",Toast.LENGTH_SHORT).show();
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("email",user.getEmail());
                                    data.put("name",user.getDisplayName());
                                    data.put("password",user.getUid());
                                    data.put("username",user.getUid());
                                    data.put("wallet",0.0);
                                    data.put("score",0);
                                    data.put("is_team",false);
                                    data.put("betting_amount",0);
                                    data.put("luck",2);
                                    data.put("power_bid_time", FieldValue.serverTimestamp());
                                    data.put("slot_time", FieldValue.serverTimestamp());
                                    db.collection("user").document(user.getUid())
                                            .set(data, SetOptions.merge());
                                }

                                viewLoader(false);
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this,"Connection error!",Toast.LENGTH_SHORT).show();
                                viewLoader(false);
                            }
                        }
                    }
            );

        }else{
            Toast.makeText(LoginActivity.this,"Connection error!",Toast.LENGTH_SHORT).show();
            viewLoader(false);
        }

    }


}


