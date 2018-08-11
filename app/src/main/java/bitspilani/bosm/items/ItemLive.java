package bitspilani.bosm.items;

public class ItemLive {
    int sport_id;
    String sport_name;
    int college_id_1;
    int college_id_2;
    String collegeName1;
    String collegeName2;
    String score1, score2;
    int vote1, vote2;
    String match_type;
    String venue, match_time, match_date;
    int itemType;

    public ItemLive(int sport_id, String sport_name, int college_id_1, int college_id_2, String collegeName1, String collegeName2, String score1, String score2, int vote1, int vote2, String venue, String match_time, String match_date,String match_type,
                    int itemType) {
        this.sport_id = sport_id;
        this.sport_name = sport_name;
        this.college_id_1 = college_id_1;
        this.college_id_2 = college_id_2;
        this.collegeName1 = collegeName1;
        this.collegeName2 = collegeName2;
        this.score1 = score1;
        this.score2 = score2;
        this.vote1 = vote1;
        this.vote2 = vote2;
        this.venue = venue;
        this.match_time = match_time;
        this.match_date = match_date;
        this.match_type = match_type;
        this.itemType = itemType;
    }

    public String getMatch_type() {
        return match_type;
    }

    public int getSport_id() {
        return sport_id;
    }

    public String getSport_name() {
        return sport_name;
    }

    public int getCollege_id_1() {
        return college_id_1;
    }

    public int getCollege_id_2() {
        return college_id_2;
    }

    public String getCollegeName1() {
        return collegeName1;
    }

    public String getCollegeName2() {
        return collegeName2;
    }

    public String getScore1() {
        return score1;
    }

    public String getScore2() {
        return score2;
    }

    public int getVote1() {
        return vote1;
    }

    public int getVote2() {
        return vote2;
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
