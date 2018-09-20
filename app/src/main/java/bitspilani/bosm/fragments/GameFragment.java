package bitspilani.bosm.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.quilympics.FragmentQuilympics;
import bitspilani.bosm.roulette.RouletteHomeFragment;


public class GameFragment extends Fragment {

    private int REQ_CODE = 15420;
    private String TAG = "GAMEFRAGMENT";
    ProgressBar progressBar;
    private int type = 0;
    RelativeLayout rl_profile;
    TextView tv_name,tv_email,tv_logout;
            ImageView iv_profile;
    SignInButton iv_login;

    public GameFragment() {
        // Regquired empty public constructor
        HomeActivity.currentFragment = "GAMEFRAGMENT";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_game, container, false);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        Typeface oswald_regular = Typeface.createFromAsset(getContext().getAssets(),"fonts/RobotoCondensed-Bold.ttf");
        TextView tv_title = (TextView)view.findViewById(R.id.tv_header);
        tv_title.setTypeface(oswald_regular);

        rl_profile = (RelativeLayout)view.findViewById(R.id.rl_profile);
//        //rl_please_login = (RelativeLayout)view.findViewById(R.id.//rl_please_login);
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_email = (TextView)view.findViewById(R.id.tv_email);
        tv_logout =(TextView)view.findViewById(R.id.tv_logout);
        iv_profile = (ImageView) view.findViewById(R.id.iv_profile);
        iv_login = (SignInButton) view.findViewById(R.id.iv_login);


        if(mAuth.getCurrentUser()!= null) {
            rl_profile.setVisibility(View.VISIBLE);
//            //rl_please_login.setVisibility(View.GONE);
            tv_name.setText(mAuth.getCurrentUser().getDisplayName());
            tv_email.setText(mAuth.getCurrentUser().getEmail());

            Picasso.with(getContext()).load(mAuth.getCurrentUser().getPhotoUrl()).into(iv_profile);
        }else{
            rl_profile.setVisibility(View.GONE);
//            //rl_please_login.setVisibility(View.VISIBLE);
        }

        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLoader(true);
                type =3;
                login(v);
            }
        });


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLoader(true);
                mAuth.signOut();
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    viewLoader(false);
                                }
                                // ...
                            }
                        });
                //rl_please_login.setVisibility(View.VISIBLE);
                rl_profile.setVisibility(View.GONE);
            }
        });


        (view.findViewById(R.id.cv_quilympics)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    type =1;
                    login(view);
                }else {
                    FirebaseFirestore.getInstance().collection("constant").document("roulette").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                boolean is = Boolean.parseBoolean(task.getResult().getData().get("is_start").toString());
                                if(is){
                                    loadFragment(new RouletteHomeFragment());
                                }else{
                                    Toast.makeText(getContext(), "The game has not started yet!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        (view.findViewById(R.id.cv_roulette)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    type =2;
                    login(view);
                }else {
                    Toast.makeText(getContext(), "Not Started yet!", Toast.LENGTH_SHORT).show();
                    FirebaseFirestore.getInstance().collection("constant").document("quilympics").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                boolean is = Boolean.parseBoolean(task.getResult().getData().get("is_start").toString());
                                if(is){
                                    loadFragment(new FragmentQuilympics());
                                }else{
                    Toast.makeText(getContext(), "Game is not started yet!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });
//                    db.coll
                }

            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.currentFragment="GAMEFRAGMENT";
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
        viewLoader(true);
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
        }else{
            viewLoader(false);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            viewLoader(false);
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
                            Log.d(TAG,mAuth.getCurrentUser().getPhotoUrl()+"");
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
                                Toast.makeText(getContext(),"Logged In Successfully!",Toast.LENGTH_SHORT).show();
                                if(task.getResult().getData()==null){
                                    //user doesn't exist
//                                    Toast.makeText(getContext(),"han",Toast.LENGTH_SHORT).show();
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("email",user.getEmail());
                                    data.put("name",user.getDisplayName());
                                    data.put("password",user.getUid());
                                    data.put("username",user.getUid());
                                    data.put("score",200);
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
                                                rl_profile.setVisibility(View.VISIBLE);
                                                    //rl_please_login.setVisibility(View.GONE);
                                                    tv_name.setText(mAuth.getCurrentUser().getDisplayName());
                                                    tv_email.setText(mAuth.getCurrentUser().getEmail());

//                                                    iv_profile.setImageURI(mAuth.getCurrentUser().getPhotoUrl());
                                                    Picasso.with(getContext()).load(mAuth.getCurrentUser().getPhotoUrl().toString()).into(iv_profile);

//                                                startActivity(new Intent(getActivity(),HomeActivity.class));
//                                                finish();
                                            }else{
                                                Toast.makeText(getContext(),"Connection error!",Toast.LENGTH_SHORT).show();
                                                viewLoader(false);
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(getContext(),"Logged In Successfully!",Toast.LENGTH_SHORT).show();
                                    viewLoader(false);
                                        rl_profile.setVisibility(View.VISIBLE);
                                        //rl_please_login.setVisibility(View.GONE);
                                        tv_name.setText(mAuth.getCurrentUser().getDisplayName());
                                        tv_email.setText(mAuth.getCurrentUser().getEmail());
                                        iv_profile.setImageURI(mAuth.getCurrentUser().getPhotoUrl());
                                        Picasso.with(getContext()).load(mAuth.getCurrentUser().getPhotoUrl().toString()).into(iv_profile);
                                    }
//                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//                                    finish();
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
