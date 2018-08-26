package bitspilani.bosm.items;

/**
 * Created by Saksham on 22 Aug 2016.
 */
public class ItemDeveloper {
    private String name;
    private String desc;
    private int image;

    public ItemDeveloper(int image, String name, String desc) {
        this.desc = desc;
        this.image = image;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String name) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }



}
