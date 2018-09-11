package bitspilani.bosm.utils;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Random;

import bitspilani.bosm.R;
import bitspilani.bosm.adapters.ItemSport;
/**
 * Created by Prashant on 4/1/2018.
 */

public class Constant {

    public static int NOTIIFCATION_TYPE_EVENT = 1; //intent to scores
    public static int NOTIIFCATION_TYPE_SCORES = 2; //intent to events
    public static int NOTIIFCATION_TYPE_3 = 3; // donlt know
    public static int sleep = 2000;
    public static ItemSport currentSport;
    public final static int ATHLETIC_TYPE_MATCH = 0;
    public final static int TEAM_MATCH = 1;

    public final static String PREF = "notification_list";

}
