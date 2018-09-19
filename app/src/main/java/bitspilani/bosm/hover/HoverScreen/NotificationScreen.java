/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bitspilani.bosm.hover.HoverScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterEvents;
import bitspilani.bosm.adapters.AdapterNotifications;
import bitspilani.bosm.items.ItemEvent;
import bitspilani.bosm.items.ItemNotification;
import bitspilani.bosm.notification.MyFirebaseMessagingService;
import io.mattcarroll.hover.Content;

import static bitspilani.bosm.utils.Constant.PREF;

/**
 * A screen that is displayed in our Hello World Hover Menu.
 */
public class NotificationScreen implements Content {

    private final Context mContext;
    private final View mWholeScreen;
    private LayoutInflater inflater;
    private RelativeLayout rl_empty;


    public NotificationScreen(@NonNull Context context) {
        mContext = context.getApplicationContext();
        inflater = LayoutInflater.from(context);
        mWholeScreen = createScreenView();

    }
   public AdapterNotifications adapterNotifications;

    @NonNull
    private View createScreenView() {
        @SuppressLint("InflateParams") View wholeScreen = inflater.inflate(R.layout.layout_hover_notification, null, false);
        HomeActivity.tv_number.setText("0");
        HomeActivity.tv_number.setVisibility(View.GONE);
        return wholeScreen;
    }

    // Make sure that this method returns the SAME View.  It should NOT create a new View each time
    // that it is invoked.
    @NonNull
    @Override
    public View getView() {

//        Toast.makeText(mContext, "asdfadsf", Toast.LENGTH_SHORT).show();
        HomeActivity.tv_number.setText("0");
        HomeActivity.tv_number.setVisibility(View.GONE);

        final TextView title =(TextView) mWholeScreen.findViewById(R.id.title);

        Typeface oswald_regular = Typeface.createFromAsset(mContext.getAssets(), "fonts/Oswald-Regular.ttf");

        title.setTypeface(oswald_regular);


        RecyclerView recyclerView = (RecyclerView)mWholeScreen.findViewById(R.id.recycler_notifications);


        rl_empty =(RelativeLayout)mWholeScreen.findViewById(R.id.rl_empty);

        final SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        Log.d("qqqqqqqqq","getview");

        Gson gson = new Gson();
        String list_string = appSharedPrefs.getString(PREF, "");

        Log.d( "qqqqqqqqqqq","notification "+list_string);

        Type type_1 = new TypeToken<List<ItemNotification>>(){}.getType();

        if(gson.fromJson(list_string, type_1)!=null)
            MyFirebaseMessagingService.list = gson.fromJson(list_string, type_1);


        if(MyFirebaseMessagingService.list.size() <=0) {
            rl_empty.setVisibility(View.VISIBLE);
        }


        Collections.sort(MyFirebaseMessagingService.list, new Comparator<ItemNotification>() {
            @Override
            public int compare(ItemNotification itemNotification, ItemNotification t1) {
                return t1.getCal().compareTo(itemNotification.getCal());
            }
        });

        // empty layout

        adapterNotifications = new AdapterNotifications(mContext,MyFirebaseMessagingService.list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterNotifications);



        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                Toast.makeText(MyActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                Toast.makeText(MyActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();
                MyFirebaseMessagingService.list.remove(position);


                SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                Gson gson = new Gson();

//                Type type_1 = new TypeToken<List<ItemNotification>>(){}.getType();


//        if(adapterNotifications!=null) {
//            Log.d(TAG,"updated");
//            adapterNotifications.notifyDataSetChanged();
//
//        }

                String json = gson.toJson(MyFirebaseMessagingService.list);

//                Log.d(TAG,"notification "+json);

                prefsEditor.putString(PREF, json);
                prefsEditor.apply();


                adapterNotifications.notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


//        Toast.makeText(mContext,"getview",Toast.LENGTH_SHORT).show();
        return mWholeScreen;
    }


    @Override
    public boolean isFullscreen() {
        HomeActivity.tv_number.setText("0");
        HomeActivity.tv_number.setVisibility(View.GONE);
//        Log.d("qqqqqqqqq","full screen");
//        Toast.makeText(mContext,"full screen",Toast.LENGTH_SHORT).show();
//        SharedPreferences appSharedPrefs = PreferenceManager
//                .getDefaultSharedPreferences(mContext);
//
//        Gson gson = new Gson();
//        String list_string = appSharedPrefs.getString(PREF, "");
//
//        Log.d("NOTIFICATION SCREEN","notification "+list_string);
//
//        Type type_1 = new TypeToken<List<ItemNotification>>(){}.getType();
//
//        if(gson.fromJson(list_string, type_1)!=null)
//            MyFirebaseMessagingService.list = gson.fromJson(list_string, type_1);
//
//        adapterNotifications.notifyDataSetChanged();

        return true;
    }

    @Override
    public void onShown() {
        HomeActivity.tv_number.setText("0");
        HomeActivity.tv_number.setVisibility(View.GONE);
//        Log.d("qqqqqqqqq","shown");
//        Toast.makeText(mContext,"shown",Toast.LENGTH_SHORT).show();
//        SharedPreferences appSharedPrefs = PreferenceManager
//                .getDefaultSharedPreferences(mContext);
//
//        Gson gson = new Gson();
//        String list_string = appSharedPrefs.getString(PREF, "");
//        Log.d("NOTIFICATION SCREEN","notification "+list_string);
//        Type type_1 = new TypeToken<List<ItemNotification>>(){}.getType();
//
//        if(gson.fromJson(list_string, type_1)!=null)
//            MyFirebaseMessagingService.list  = gson.fromJson(list_string, type_1);
//        // No-op.
        adapterNotifications.notifyDataSetChanged();
    }

    @Override
    public void onHidden() {
        HomeActivity.tv_number.setText("0");
        HomeActivity.tv_number.setVisibility(View.GONE);
//        Log.d("qqqqqqqqq","hidden");
//        Toast.makeText(mContext,"hidden",Toast.LENGTH_SHORT).show();
        // No-op.
    }
}
