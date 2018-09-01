package bitspilani.bosm.items;

import java.util.Calendar;

/**
 * Created by Prashant on 4/5/2018.
 */

public class ItemWalletHistory {
    private int transaction_id;
    private Calendar date;
    private double amount;
    private String order_unique_id, from , to ,remarks;

    public ItemWalletHistory(int transaction_id, Calendar date, double amount, String order_unique_id, String from, String to, String remarks) {
        this.transaction_id = transaction_id;
        this.date = date;
        this.amount = amount;
        this.order_unique_id = order_unique_id;
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
        return order_unique_id;
    }

    public int getTransaction_id() {
        return transaction_id;
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
