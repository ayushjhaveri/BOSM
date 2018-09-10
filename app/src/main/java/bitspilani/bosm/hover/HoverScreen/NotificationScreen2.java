///*
// * Copyright 2016 Google Inc. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package bitspilani.bosm.hover.HoverScreen;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import java.util.ArrayList;
//
//import bitspilani.bosm.R;
//import bitspilani.bosm.adapters.AdapterEvents;
//import bitspilani.bosm.adapters.AdapterNotifications;
//import bitspilani.bosm.items.ItemEvent;
//import bitspilani.bosm.items.ItemNotification;
//import io.mattcarroll.hover.Content;
//
///**
// * A screen that is displayed in our Hello World Hover Menu.
// */
//public class NotificationScreen2 implements Content {
//
//    private final Context mContext;
//    private final View mWholeScreen;
//    private LayoutInflater inflater;
//
//    public NotificationScreen2(@NonNull Context context) {
//        mContext = context.getApplicationContext();
//        inflater = LayoutInflater.from(context);
//        mWholeScreen = createScreenView();
//
//    }
//
//    @NonNull
//    private View createScreenView() {
//        @SuppressLint("InflateParams") View wholeScreen = inflater.inflate(R.layout.activi, null, false);
//        //implement contents of layout
//
//        RecyclerView recyclerView = (RecyclerView)wholeScreen.findViewById(R.id.recycler_notifications);
//        ArrayList<ItemNotification> notificationArrayList  = new ArrayList<>();
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//        notificationArrayList.add(new ItemNotification(
//                "Bits Pilani v/s Bits Goa",
//                "The Football Match at Gym G will start in 20 minutes",
//                "20th Sep",
//                "18:00"
//        ));
//
//
//
//        AdapterNotifications adapterNotifications = new AdapterNotifications(mContext,notificationArrayList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapterNotifications);
//
//
//
//        return wholeScreen;
//    }
//
//    // Make sure that this method returns the SAME View.  It should NOT create a new View each time
//    // that it is invoked.
//    @NonNull
//    @Override
//    public View getView() {
//
//        return mWholeScreen;
//    }
//
//    @Override
//    public boolean isFullscreen() {
//        return true;
//    }
//
//    @Override
//    public void onShown() {
//        // No-op.
//    }
//
//    @Override
//    public void onHidden() {
//        // No-op.
//    }
//}
