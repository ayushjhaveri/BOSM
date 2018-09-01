package bitspilani.bosm.items;

/**
 * Created by Prashant on 4/7/2018.
 */

public class ItemOrder {
    private int food_id, quantity, stall_id;
    private String food_name;
    private String stall_name;
    private double price;
    private int status;

    public ItemOrder(int food_id, int quantity, int stall_id, String food_name, String stall_name, double price, int status) {
        this.food_id = food_id;
        this.quantity = quantity;
        this.stall_id = stall_id;
        this.food_name = food_name;
        this.stall_name = stall_name;
        this.price = price;
        this.status = status;
    }

    public int getFood_id() {
        return food_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStall_id() {
        return stall_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getStall_name() {
        return stall_name;
    }

    public double getPrice() {
        return price;
    }

    public int getStatus() {
        return status;
    }
}
