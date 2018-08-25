package bitspilani.bosm.adapters;

public class ItemSport {
    private int sport_id;
    private String name;
    private boolean isGender;

    public ItemSport(int sport_id, String name, boolean isGender) {
        this.sport_id = sport_id;
        this.name = name;
        this.isGender = isGender;
    }

    public int getSport_id() {
        return sport_id;
    }

    public boolean  isGender() {
        return isGender;
    }

    public String getName() {
        return name;
    }
}
