package bitspilani.bosm.items;

import java.util.Calendar;

/**
 * Created by Prashant on 4/5/2018.
 */

public class ItemWalletHistory {
    private int transaction_id, user_id;
    private Calendar date;
    private double amount;
    private String order_id, from , to ,remarks;

    public ItemWalletHistory(int transaction_id, int user_id, Calendar date, double amount, String order_id, String from, String to, String remarks) {
        this.transaction_id = transaction_id;
        this.user_id = user_id;
        this.date = date;
        this.amount = amount;
        this.order_id = order_id;
        this.from = from;
        this.to = to;
        this.remarks = remarks;
    }

    public Calendar getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getOrder_id() {
        return order_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getRemarks() {
        return remarks;
    }
}
