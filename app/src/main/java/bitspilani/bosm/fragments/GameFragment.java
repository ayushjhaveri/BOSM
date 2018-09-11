package bitspilani.bosm.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterPhotos;
import bitspilani.bosm.quilympics.FragmentQuilympics;
import bitspilani.bosm.roulette.RouletteHomeFragment;
import bitspilani.bosm.roulette.RouletteMainFragment;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;


public class GameFragment extends Fragment {

    private int REQ_CODE = 15420;
    private String TAG = "GAMEFRAGMENT";
    ProgressBar progressBar;
    private int type = 0;

    public GameFragment() {
        // Required empty public constructor
        HomeActivity.currentFragment = "PhotoFragment";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        Typeface oswald_regular = Typeface.createFromAsset(getContext().getAssets(),"fonts/KrinkesDecorPERSONAL.ttf");
        TextView tv_title = (TextView)view.findViewById(R.id.tv_header);
        tv_title.setTypeface(oswald_regular);

        (view.findViewById(R.id.cv_roulette)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    type =1;
                    login(view);
                }else
                    loadFragment(new RouletteHomeFragment());
            }
        });

        (view.findViewById(R.id.cv_quilympics)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    type =2;
                    login(view);
                }else
                    loadFragment(new FragmentQuilympics());

            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="PhotoFragment";
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_view, fragment);
        transaction.addToBackStack("transaction");
        transaction.commit();
    }

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth mAuth;

    private void login(View view){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        signIn();
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void viewLoader(boolean is){
        if(is){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
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
            if(Objects.requireNonNull(account.getEmail()).contains("@pilani.bits-pilani.ac.in")) {
                firebaseAuthWithGoogle(account);
            }else {
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "Only Bits-email login is enabled!", Toast.LENGTH_SHORT).show();
                                }
                                // ...
                            }
                        });
                viewLoader(false);

            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            viewLoader(false);
            Toast.makeText(getActivity(),getResources().getText(R.string.connection_error),Toast.LENGTH_SHORT).show();
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            viewLoader(false);
                            Toast.makeText(getContext(),"Connection error!",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void updateUI(final FirebaseUser user){
        if(user!=null){
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .setPersistenceEnabled(true)
                    .build();
            db.setFirestoreSettings(settings);

            db.collection("user").document(user.getUid()).get(Source.SERVER).addOnCompleteListener(
                    new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if(task.getResult().getData()==null){
                                    //user doesn't exist
//                                    Toast.makeText(getContext(),"han",Toast.LENGTH_SHORT).show();
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

                                    StringTokenizer stringTokenizer = new StringTokenizer(user.getEmail(),"@");
                                    String qrcdode = stringTokenizer.nextToken();
                                    data.put("qr_code", qrcdode);

                                    db.collection("user").document(user.getUid())
                                            .set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                viewLoader(false);
                                                if(type ==1){
                                                    loadFragment(new RouletteHomeFragment());
                                                }else if(type ==0){
                                                    loadFragment(new FragmentQuilympics());
                                                }
//                                                startActivity(new Intent(getActivity(),HomeActivity.class));
//                                                finish();
                                            }else{
                                                Toast.makeText(getContext(),"Connection error!",Toast.LENGTH_SHORT).show();
                                                viewLoader(false);
                                            }
                                        }
                                    });
                                }else{
                                    viewLoader(false);
                                    if(type ==1){
                                        loadFragment(new RouletteHomeFragment());
                                    }else if(type ==0){
                                        loadFragment(new FragmentQuilympics());
                                    }
//                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//                                    finish();
                                }
                            }else{
                                Toast.makeText(getContext(),"Connection error!",Toast.LENGTH_SHORT).show();
                                viewLoader(false);
                            }
                        }
                    }
            );
        }else{
            Toast.makeText(getActivity(),"Connection error!",Toast.LENGTH_SHORT).show();
            viewLoader(false);
        }
    }
}
