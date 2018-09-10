package bitspilani.bosm.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import bitspilani.bosm.HomeActivity;
import bitspilani.bosm.R;
import bitspilani.bosm.utils.Constant;

/**
 * Created by Aditya on 11/09/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "StartingAndroid";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG,remoteMessage.getData().toString());

        sendNotification(
                remoteMessage.getData().containsKey("title")?remoteMessage.getData().get("title"):"",
                remoteMessage.getData().containsKey("body")?remoteMessage.getData().get("body"):"",
                remoteMessage.getData().containsKey("type")?remoteMessage.getData().get("type"):"");

    }


    private void sendNotification(String title, String messageBody, String type) {


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
}

