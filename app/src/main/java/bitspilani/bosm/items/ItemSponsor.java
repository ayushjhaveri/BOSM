package bitspilani.bosm.items;

/**
 * Created by Saksham on 22 Aug 2016.
 */
public class ItemSponsor {
    private String name;
    private int image;

    public ItemSponsor(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
