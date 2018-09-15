package bitspilani.bosm.items;

/**
 * Created by Saksham on 22 Aug 2016.
 */
public class ItemSponsor {
    private String name, description;
    private int image;

    public ItemSponsor(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
}
