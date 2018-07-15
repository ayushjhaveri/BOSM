package bitspilani.bosm.utils;

import android.content.SharedPreferences;

import java.util.Random;

import bitspilani.bosm.items.ItemCollege;
import bitspilani.bosm.items.ItemUser;

/**
 * Created by Prashant on 4/1/2018.
 */

public class Constant {

    //API URL's
//  public static String BASE_URL = "http://172.17.44.85:8000/";
    public static String BASE_URL = "http://bosm.pythonanywhere.com/";
//        public static String BASE_URL = "http://192.168.43.72:8000/";
//   public static String BASE_URL = "http://192.168.43.57:8000/";

    public static String URL_VALIDATING_ACCCOUNT= BASE_URL + "login/";
    public static String URL_GET_WALLET= BASE_URL + "get_wallet/";
    public static String URL_GET_WALLET_HISTORY = BASE_URL + "get_wallet_transactions/";
    public static String URL_ADD_AMOUNT_TO_WALLET = BASE_URL + "add_money_to_wallet/";
    public static String URL_GET_STALL_LIST = BASE_URL + "get_stall_list/";
    public static String URL_GET_FOOD_ITEM_LIST = BASE_URL + "get_food_item_list/";
    public static String URL_ADD_TO_CART= BASE_URL + "add_to_cart/";
    public static String URL_GET_USER_CART= BASE_URL + "get_user_cart/";
    public static String URL_UPDATE_CART= BASE_URL + "update_cart/";
    public static String URL_DEL_CART_ITEM= BASE_URL + "del_cart_item/";
    public static String URL_PLACE_ORDER= BASE_URL + "place_order/";
    public static String URL_GENERATE_CHECKSUM = BASE_URL + "generate_checksum/";

    public static String AUTHORIZATION_TOKEN = "6b48b17765eeec11e361b2a379b22f2d9e58cc45";

    public static String USER_EMAIL = "user_email";
    public static String PROFILE_SHARED_PREFERENCES = "profile_shared_preferences";
    public static int ACCOUNT_TYPE_BITS =0;
    public static int ACCOUNT_TYPE_NON_BITS =1;

    public static boolean IS_LOGIN = false;
    public static ItemUser currentItemUser;
    public static String TAG_IS_LOGIN = "isLogin";
    private static final int MAX_LENGTH = 10;

    public static int CURRENT_STALL_ID;
    public static String CURRENT_STALL_NAME= "";

    public static int ACCOUNT_TYPE= ACCOUNT_TYPE_NON_BITS;

    public static void login(String email, SharedPreferences profieSharedPreferences){
        IS_LOGIN = true;
        saveLoginToSharedPreferences(profieSharedPreferences,email);
    }
    private static void saveLoginToSharedPreferences(SharedPreferences profieSharedPreferences, String email){
        SharedPreferences.Editor editor = profieSharedPreferences.edit();
        editor.putBoolean(TAG_IS_LOGIN,true);
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    public static String random() {
        Random r = new Random();
        int i1 = r.nextInt(100000000);
        return Integer.toString(i1);
    }

    public static ItemCollege currentItemCollege;
}
