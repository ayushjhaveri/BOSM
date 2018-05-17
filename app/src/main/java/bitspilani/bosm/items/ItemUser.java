package bitspilani.bosm.items;

import bitspilani.bosm.utils.Constant;

/**
 * Created by Prashant on 4/1/2018.
 */

public class ItemUser {
    private int id;
    private String user_name;
    private String user_email;
    private String user_google_profile_image;
    private double user_wallet;

    public ItemUser(int id, String user_name, String user_email, double user_wallet) {
        this.id = id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_wallet = user_wallet;

        if(user_email.contains("pilani.bits-pilani.ac.in")){
            Constant.ACCOUNT_TYPE =  Constant.ACCOUNT_TYPE_BITS;
        }else{
            Constant.ACCOUNT_TYPE = Constant.ACCOUNT_TYPE_NON_BITS;
        }
    }

    public int getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_google_profile_image() {
        return user_google_profile_image;
    }

    public double getUser_wallet() {
        return user_wallet;
    }

    public void setUser_wallet(double user_wallet) {
        this.user_wallet = user_wallet;
    }
}