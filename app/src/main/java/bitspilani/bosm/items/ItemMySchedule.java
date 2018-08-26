package bitspilani.bosm.items;

public class ItemMySchedule {

    int sport_id;
    String sport_name;
    String collegeName1;
    String collegeName2;
    String match_type;
    String venue, match_time, match_date;
    int itemType;

    public ItemMySchedule(int sport_id, String sport_name, String collegeName1, String collegeName2, String match_type, String venue, String match_time, String match_date, int itemType) {
        this.sport_id = sport_id;
        this.sport_name = sport_name;
        this.collegeName1 = collegeName1;
        this.collegeName2 = collegeName2;
        this.match_type = match_type;
        this.venue = venue;
        this.match_time = match_time;
        this.match_date = match_date;
        this.itemType = itemType;
    }

    public int getSport_id() {
        return sport_id;
    }

    public String getSport_name() {
        return sport_name;
    }

    public String getCollegeName1() {
        return collegeName1;
    }

    public String getCollegeName2() {
        return collegeName2;
    }

    public String getMatch_type() {
        return match_type;
    }

    public String getVenue() {
        return venue;
    }

    public String getMatch_time() {
        return match_time;
    }

    public String getMatch_date() {
        return match_date;
    }

    public int getItemType() {
        return itemType;
    }
}
