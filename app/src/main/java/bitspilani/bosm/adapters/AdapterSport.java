package bitspilani.bosm.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import bitspilani.bosm.CurrentSportActivity;
import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.fragments.CurrentSportFragment;
import bitspilani.bosm.fragments.SponsorsFragment;
import bitspilani.bosm.fragments.SportFragment;
import bitspilani.bosm.fragments.SportSelectedFragment;
import bitspilani.bosm.items.ItemNotification;
import bitspilani.bosm.utils.Constant;

import static bitspilani.bosm.utils.Constant.PREF;
import static bitspilani.bosm.utils.Constant.PREF_SPORT;

/**
 * Created by Prashant on 4/7/2018.
 */

public class AdapterSport extends FirestoreAdapter<AdapterSport.ViewHolder> {

    private Context context;
    private ProgressBar progressBar;
    private static final String TAG = "AdapterCart";
    private RelativeLayout rl_filled, rl_empty;

    public AdapterSport(Context context, Query query,ProgressBar progressBar, RelativeLayout rl_filled, RelativeLayout rl_empty) {
        super(query);
        this.progressBar  = progressBar;
        this.context = context;
        this.rl_empty=rl_empty;
        this.rl_filled=rl_filled;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sport, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        progressBar.setVisibility(View.GONE);
        rl_empty.setVisibility(View.GONE);
        rl_filled.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DocumentSnapshot document =  getSnapshot(position);
        final ItemSport itemSport  = new ItemSport(
                Integer.parseInt(document.getId()),
                document.getData().get("sport_name").toString(),
                Boolean.parseBoolean(document.getData().get("is_gender").toString())
        );

        holder.textView_name.setText(itemSport.getName());
        Typeface oswald_regular = Typeface.createFromAsset(context.getAssets(),"fonts/Oswald-Regular.ttf");

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            // the view being shared
//            holder.card_name.setTransitionName("transition" + position);
//        }
        holder.icon.setImageResource(SportFragment.iconHash.get(itemSport.getSport_id()));

        holder.notify.setShapeResource(R.raw.star);
        holder.notify.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final View view, boolean checked) {
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic(itemSport.getName().toLowerCase().replace(' ','_'))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        SharedPreferences appSharedPrefs = PreferenceManager
                                                .getDefaultSharedPreferences(context);

                                        Type type_1 = new TypeToken<ArrayList<String>>(){}.getType();
                                        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                                        Gson gson = new Gson();
                                        String list_string = appSharedPrefs.getString(PREF_SPORT, "");
                                        ArrayList<String> list = new ArrayList<>();
                                        if(gson.fromJson(list_string, type_1)!=null)
                                            list = gson.fromJson(list_string, type_1);
                                        list.add(itemSport.getName());
                                        String json = gson.toJson(list);
                                        Log.d(TAG,"sport_notif "+json);
                                        prefsEditor.putString(PREF_SPORT, json);
                                        prefsEditor.apply();
                                        Toast.makeText(context,"Subscribed for notifications!",Toast.LENGTH_SHORT).show();
//                                        ArrayList<String > arrayList = itemSport.getArrayList();
//                                        arrayList.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                        itemSport.setArrayList(arrayList);
//                                        Toast.makeText(context,"Great! You will be notified for each match",Toast.LENGTH_SHORT).show();
//                                        document.getReference().update("notification",arrayList);
                                    }else{
                                        holder.notify.setChecked(false);
                                        Toast.makeText(context,"Server error!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(itemSport.getName().toLowerCase().replace(' ','_'))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        SharedPreferences appSharedPrefs = PreferenceManager
                                                .getDefaultSharedPreferences(context);

                                        Type type_1 = new TypeToken<ArrayList<String>>(){}.getType();
                                        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                                        Gson gson = new Gson();
                                        String list_string = appSharedPrefs.getString(PREF_SPORT, "");
                                        ArrayList<String> list = new ArrayList<>();
                                        if(gson.fromJson(list_string, type_1)!=null)
                                            list = gson.fromJson(list_string, type_1);
                                        list.remove(itemSport.getName());
                                        String json = gson.toJson(list);
                                        Log.d(TAG,"sport_notif "+json);
                                        prefsEditor.putString(PREF_SPORT, json);
                                        prefsEditor.apply();

//                                        ArrayList<String > arrayList = itemSport.getArrayList();
//                                        arrayList.remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                        itemSport.setArrayList(arrayList);
//                                        document.getReference().update("notification",arrayList);
//                                        Toast.makeText(context,"Great! You will be notified for each match",Toast.LENGTH_SHORT).show();
                                    }else{
                                        holder.notify.setChecked(true);
                                        Toast.makeText(context,"Server error!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        Type type_1 = new TypeToken<ArrayList<String>>(){}.getType();
        Gson gson = new Gson();
        String list_string = appSharedPrefs.getString(PREF_SPORT, "");
        ArrayList<String> list = new ArrayList<>();
        if(gson.fromJson(list_string, type_1)!=null)
            list = gson.fromJson(list_string, type_1);

        if (list.contains(itemSport.getName())){
            holder.notify.setChecked(true);
        }else{
            holder.notify.setChecked(false);
        }

        holder.textView_name.setTypeface(oswald_regular);
        holder.rl.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Fragment fragment = new SportSelectedFragment();

                Constant.currentSport  = itemSport;
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

//                transaction.setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up);
                transaction.addToBackStack(null);

                transaction.replace(R.id.fl_view, fragment);
                transaction.commit();

//                context.startActivity(new Intent(context, CurrentSportActivity.class));
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        CardView card_name;
        ImageView icon;
        ShineButton notify;
        RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.tv_name);
            card_name= (CardView)itemView.findViewById(R.id.card_name);
            icon = (ImageView)itemView.findViewById(R.id.icon);
            notify = (ShineButton)itemView.findViewById(R.id.notify);
            rl = (RelativeLayout)itemView.findViewById(R.id.rl);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
