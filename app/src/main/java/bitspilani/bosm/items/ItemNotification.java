package bitspilani.bosm.items;

import java.util.Calendar;

public class ItemNotification {

    private String notif_title, notif_body;
    private Calendar cal;

    public ItemNotification(String notif_title, String notif_body, Calendar cal) {
        this.notif_title = notif_title;
        this.notif_body = notif_body;
        this.cal = cal;
    }

    public String getNotif_title() {
        return notif_title;
    }

    public String getNotif_body() {
        return notif_body;
    }

    public Calendar getCal() {
        return cal;
    }
}