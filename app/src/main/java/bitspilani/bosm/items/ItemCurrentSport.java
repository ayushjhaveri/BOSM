package bitspilani.bosm.items;

public class ItemCurrentSport {

    int college_id_1;
    int college_id_2;
    String collegeName1;
    String collegeName2;
    String score1, score2;
    String venue, match_time, match_date, match_type;

    public ItemCurrentSport(int college_id_1, int college_id_2, String collegeName1, String collegeName2, String score1, String score2, String venue, String match_time, String match_date, String match_type) {
        this.college_id_1 = college_id_1;
        this.college_id_2 = college_id_2;
        this.collegeName1 = collegeName1;
        this.collegeName2 = collegeName2;
        this.score1 = score1;
        this.score2 = score2;
        this.venue = venue;
        this.match_time = match_time;
        this.match_date = match_date;
        this.match_type = match_type;
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

    public String getVenue() {
        return venue;
    }

    public String getMatch_time() {
        return match_time;
    }

    public String getMatch_date() {
        return match_date;
    }

    public String getMatch_type() {
        return match_type;
    }
}
