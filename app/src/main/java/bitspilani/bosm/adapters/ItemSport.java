package bitspilani.bosm.adapters;

public class ItemSport {
    int sport_id;
    String name;

    public ItemSport(int sport_id, String name) {
        this.sport_id = sport_id;
        this.name = name;
    }

    public int getSport_id() {
        return sport_id;
    }

    public String getName() {
        return name;
    }
}
