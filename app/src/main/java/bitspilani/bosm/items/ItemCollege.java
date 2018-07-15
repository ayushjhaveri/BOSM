package bitspilani.bosm.items;

public class ItemCollege {
    private String college, city, state;
    private int college_id;

    public ItemCollege(String college, String city, String state, int college_id){
        this.college=college;
        this.city=city;
        this.state=state;
        this.college_id=college_id;
    }

    public String getCollege() {
        return college;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getCollege_id() {
        return college_id;
    }
}

