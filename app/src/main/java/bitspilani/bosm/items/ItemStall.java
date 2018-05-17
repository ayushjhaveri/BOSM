package bitspilani.bosm.items;

/**
 * Created by Prashant on 4/6/2018.
 */

public class ItemStall {
    private int stall_id;
    private String name, qrcode;

    public ItemStall(int stall_id, String name) {

        this.stall_id = stall_id;
        this.name = name;
//        this.qrcode = qrcode;
    }

    public int getStall_id() {
        return stall_id;
    }

    public String getName() {
        return name;
    }

    public String getQrcode() {
        return qrcode;
    }
}
