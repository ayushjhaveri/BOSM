package bitspilani.bosm.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.hover.MultipleSectionsHoverMenuService;
import bitspilani.bosm.items.ItemNotification;
import bitspilani.bosm.utils.Constant;

import static bitspilani.bosm.utils.Constant.PREF;

/**
 * Created by Aditya on 11/09/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "StartingAndroid";

   public static List<ItemNotification> list = new ArrayList<>();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG,remoteMessage.getData().toString());

        sendNotification(
                remoteMessage.getData().containsKey("title")?remoteMessage.getData().get("title"):"",
                remoteMessage.getData().containsKey("body")?remoteMessage.getData().get("body"):"",
                remoteMessage.getData().containsKey("type")?remoteMessage.getData().get("type"):"");

    }




    private void sendNotification(String title, String messageBody, String type) {

        updateMyActivity(getApplicationContext(),"dsaasdasd");
//
//        Log.d(TAG,title);
//        Log.d(TAG,messageBody);
//        Log.d(TAG,teamA);
//        Log.d(TAG,teamB);
//        Log.d(TAG,venue);
//        Log.d(TAG,type);

//        venue=venue.replaceAll("_"," ");

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if(type.equals(String.valueOf(Constant.NOTIIFCATION_TYPE_EVENT))){
            intent.putExtra("NOTIFICATION","1");
        }else if(type.equals(String.valueOf(Constant.NOTIIFCATION_TYPE_SCORES))){
            intent.putExtra("NOTIFICATION","2");
        }


        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());

        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String list_string = appSharedPrefs.getString(PREF, "");

        Type type_1 = new TypeToken<List<ItemNotification>>(){}.getType();

        if(gson.fromJson(list_string, type_1)!=null)
            list = gson.fromJson(list_string, type_1);

        ItemNotification itemNotification = new ItemNotification(
                title,
                messageBody,
                Calendar.getInstance()
        );
        list.add(itemNotification);



//        if(adapterNotifications!=null) {
//            Log.d(TAG,"updated");
//            adapterNotifications.notifyDataSetChanged();
//
//        }

        String json = gson.toJson(list);

        Log.d(TAG,"notification "+json);

        prefsEditor.putString(PREF, json);
        prefsEditor.apply();

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

//        String msg = "";
//
//        if(type.equals("1")){
//            //schedule
//            if (teamA.equals("")){
//                inboxStyle.addLine("Venue: " + venue);
//                inboxStyle.addLine(messageBo`dy);
//            } else {
//                inboxStyle.addLine(teamA + " vs " + teamB);
//                inboxStyle.addLine("Venue: " + venue);
//                inboxStyle.addLine(messageBody);
//            }
//        }else if (type.equals("2")){
//            // Result
//            if (teamA.equals("")){
//                inboxStyle.addLine(msg += messageBody);
//            } else {
//                inboxStyle.addLine(teamA + " vs " + teamB);
//                if (venue.equals("1")) {
//                    inboxStyle.addLine(msg += "Winner: " + teamA);
//                } else if (venue.equals("2")) {
//                    inboxStyle.addLine("Winner: " + teamB);
//                }
//                inboxStyle.addLine(messageBody);
//            }
//        }
//        inboxStyle.setBigContentTitle(title);

        String channelId ="default";

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
//                .setStyle(inboxStyle);

//        notificationBuilder.mNumber = 1;
//        notificationBuilder.mContext =

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NotificationID.getID(), notificationBuilder.build());
    }

    // This function will create an intent. This intent must take as parameter the "unique_name" that you registered your activity with
    static void updateMyActivity(Context context, String message) {

        Intent intent = new Intent("unique_name");

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }
}

