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
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.AdapterEvents;
import bitspilani.bosm.adapters.AdapterNotifications;
import bitspilani.bosm.items.ItemEvent;
import bitspilani.bosm.items.ItemNotification;
import io.mattcarroll.hover.Content;

import static bitspilani.bosm.utils.Constant.PREF;

/**
 * A screen that is displayed in our Hello World Hover Menu.
 */
public class NotificationScreen implements Content {

    private final Context mContext;
    private final View mWholeScreen;
    private LayoutInflater inflater;

    public NotificationScreen(@NonNull Context context) {
        mContext = context.getApplicationContext();
        inflater = LayoutInflater.from(context);
        mWholeScreen = createScreenView();

    }
    AdapterNotifications adapterNotifications;
    List<ItemNotification> list;

    @NonNull
    private View createScreenView() {
        @SuppressLint("InflateParams") View wholeScreen = inflater.inflate(R.layout.layout_hover_notification, null, false);
        //implement contents of layout

        RecyclerView recyclerView = (RecyclerView)wholeScreen.findViewById(R.id.recycler_notifications);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        Gson gson = new Gson();
        String list_string = appSharedPrefs.getString(PREF, "");

        Log.d("NOTIFICATION SCREEN","notification "+list_string);

        Type type_1 = new TypeToken<List<ItemNotification>>(){}.getType();

        if(gson.fromJson(list_string, type_1)!=null)
            list = gson.fromJson(list_string, type_1);
        else
            list = new ArrayList<>();

        adapterNotifications = new AdapterNotifications(mContext,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterNotifications);

        return wholeScreen;
    }

    // Make sure that this method returns the SAME View.  It should NOT create a new View each time
    // that it is invoked.
    @NonNull
    @Override
    public View getView() {

        return mWholeScreen;
    }

    @Override
    public boolean isFullscreen() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        Gson gson = new Gson();
        String list_string = appSharedPrefs.getString(PREF, "");

        Log.d("NOTIFICATION SCREEN","notification "+list_string);

        Type type_1 = new TypeToken<List<ItemNotification>>(){}.getType();

        if(gson.fromJson(list_string, type_1)!=null)
            list = gson.fromJson(list_string, type_1);
        else
            list = new ArrayList<>();
        adapterNotifications.notifyDataSetChanged();

        return true;
    }

    @Override
    public void onShown() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        Gson gson = new Gson();
        String list_string = appSharedPrefs.getString(PREF, "");

        Log.d("NOTIFICATION SCREEN","notification "+list_string);

        Type type_1 = new TypeToken<List<ItemNotification>>(){}.getType();

        if(gson.fromJson(list_string, type_1)!=null)
            list = gson.fromJson(list_string, type_1);
        else
            list = new ArrayList<>();
        // No-op.
        adapterNotifications.notifyDataSetChanged();
    }

    @Override
    public void onHidden() {
        // No-op.
    }
}
