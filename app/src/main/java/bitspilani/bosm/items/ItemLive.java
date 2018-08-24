package bitspilani.bosm.items;

public class ItemLive {

    //Common
    int sport_id;
    String sport_name;
    String collegeName1;
    String collegeName2;
    String match_round;
    String venue, match_time, match_date;
    int itemType;

    //Live
    String score1, score2;
    int vote1, vote2;
    int isVote;


    //Trending
    int matchType;


    //Live Constructor
    public ItemLive(int itemType, int sport_id, String sport_name, String collegeName1, String collegeName2, String match_round, String venue, String match_time, String match_date, String score1, String score2, int vote1, int vote2, int isVote) {
        this.sport_id = sport_id;
        this.sport_name = sport_name;
        this.collegeName1 = collegeName1;
        this.collegeName2 = collegeName2;
        this.match_round = match_round;
        this.venue = venue;
        this.match_time = match_time;
        this.match_date = match_date;
        this.itemType = itemType;
        this.score1 = score1;
        this.score2 = score2;
        this.vote1 = vote1;
        this.vote2 = vote2;
        this.isVote = isVote;
        this.matchType = -1;
    }

    //Trending Constructor

    public ItemLive(int itemType, int matchType ,int sport_id, String sport_name, String collegeName1, String collegeName2, String match_round, String venue, String match_time, String match_date) {
        this.sport_id = sport_id;
        this.sport_name = sport_name;
        this.collegeName1 = collegeName1;
        this.collegeName2 = collegeName2;
        this.match_round = match_round;
        this.venue = venue;
        this.match_time = match_time;
        this.match_date = match_date;
        this.itemType = itemType;
        this.matchType = matchType;

        this.score1 = "";
        this.score2 = "";
        this.vote1 = -1;
        this.vote2 = -1;
        this.isVote = -1;
    }

    public int getMatchType() {
        return matchType;
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

    public String getMatch_round() {
        return match_round;
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

    public int getIsVote() {
        return isVote;
    }

    public void setIsVote(int isVote) {
        this.isVote = isVote;
    }
}
