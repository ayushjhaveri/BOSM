package bitspilani.bosm.items;

/**
 * Created by Prashant on 4/6/2018.
 */

public class ItemFood {
    private int food_id;
    private String food_name;
    private double price;

    public ItemFood(int food_id, String food_name, double price) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.price = price;
    }

    public int getFood_id() {
        return food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public double getPrice() {
        return price;
    }
}
