package bitspilani.bosm.items;

/**
 * Created by Prashant on 4/7/2018.
 */

public class ItemCart {
    private int cart_id, food_id, quantity, stall_id;
    private String name;
    private String stall_name;
    private double price;

    public ItemCart(int cart_id, int food_id,int stall_id, int quantity, String name,String stall_name, double price) {
        this.cart_id = cart_id;
        this.food_id = food_id;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.stall_id = stall_id;
        this.stall_name  = stall_name;
    }

    public int getCart_id() {
        return cart_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public double getTotalPrice() {
        return price*quantity;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
