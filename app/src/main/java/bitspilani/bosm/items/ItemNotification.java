package bitspilani.bosm.items;

public class ItemNotification {

    private String notif_name, notif_info, notif_date, notif_time;

    public ItemNotification(String notif_name, String notif_info, String notif_date, String notif_time) {
        this.notif_name = notif_name;
        this.notif_info = notif_info;
        this.notif_date = notif_date;
        this.notif_time = notif_time;

    }

    public String getNotif_name() {
        return notif_name;
    }

    public String getnotif_info() {
        return notif_info;
    }

    public String getNotif_date() {
        return notif_date;
    }

    public String getNotif_time() {
        return notif_time;
    }
}
