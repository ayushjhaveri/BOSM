package bitspilani.bosm.items;

public class ItemSport {

    private String sport, round, matchDate, matchTime, venue, collegeOne, collegeTwo, scoreOne, scoreTwo;

    public ItemSport(String sport, String round, String matchDate, String matchTime, String venue, String collegeOne, String collegeTwo, String scoreOne, String scoreTwo){
        this.sport = sport;
        this.round = round;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.venue = venue;
        this.collegeOne = collegeOne;
        this.collegeTwo = collegeTwo;
        this.scoreOne = scoreOne;
        this.scoreTwo = scoreTwo;
    }

    public String getSport() {
        return sport;
    }

    public String getRound() {
        return round;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public String getVenue() {
        return venue;
    }

    public String getCollegeOne() {
        return collegeOne;
    }

    public String getCollegeTwo() {
        return collegeTwo;
    }

    public String getScoreOne() {
        return scoreOne;
    }

    public String getScoreTwo() {
        return scoreTwo;
    }
}
