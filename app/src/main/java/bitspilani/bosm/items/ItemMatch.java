package bitspilani.bosm.items;

import java.util.Calendar;

public class ItemMatch {
    int matchType;
    String sportName;
    String venue;
    String time,date;
    Calendar calendar;
    String matchRound;
    boolean isHeader;

    //ATHLETIC TYPE

    String goldName;
    String silverName;
    String bronzeName;

    String goldRecord;
    String silverRecord;
    String bronzeRecord;

    //TEAM MATCHES
    String college1, college2, score1, score2;
    String fullCollege1, fullCollege2;
    int winner;

    public ItemMatch(){
        this.goldName ="";
        this.bronzeName="";
        this.bronzeRecord="";
        this.college1="";
        this.college2="";
        this.fullCollege1="";
        this.fullCollege2="";
        this.goldRecord="";
        this.score1="";
        this.score2="";
        this.silverName="";
        this.silverRecord="";
        this.winner =0;
        this.isHeader=false;
    }

    //SCHEDULE ATHLETIC TYPE
    public ItemMatch(int matchType, String sportName, String venue, String time, String date, String matchRound ,Calendar calendar) {
        this();
        this.matchType = matchType;
        this.sportName = sportName;
        this.calendar = calendar;
        this.venue = venue;
        this.time = time;
        this.date = date;
        this.matchRound = matchRound;
    }

    //RESULT ATHLETIC TYPE
    public ItemMatch(int matchType, String sportName, String venue, String time, String date, String type, String goldName, String silverName, String bronzeName, String goldRecord, String silverRecord, String bronzeRecord ,Calendar calendar) {
        this();
        this.matchType = matchType;
        this.sportName = sportName;
        this.venue = venue;
        this.time = time;
        this.calendar = calendar;
        this.date = date;
        this.matchRound = type;
        this.goldName = goldName;
        this.silverName = silverName;
        this.bronzeName = bronzeName;
        this.goldRecord = goldRecord;
        this.silverRecord = silverRecord;
        this.bronzeRecord = bronzeRecord;
    }

    //SCHEDULE TEAM TYPE
    public ItemMatch(int matchType, String sportName, String venue, String time, String date, String type, String college1, String college2, String fullCollege1, String fullCollege2 ,Calendar calendar) {
        this();
        this.matchType = matchType;
        this.sportName = sportName;
        this.calendar = calendar;
        this.venue = venue;
        this.time = time;
        this.date = date;
        this.matchRound = type;
        this.college1 = college1;
        this.college2 = college2;
        this.fullCollege1=fullCollege1;
        this.fullCollege2=fullCollege2;
    }

    //RESULT TEAM TYPE
    public ItemMatch(int matchType, String sportName, String venue, String time, String date, String type, String score1, String score2, String college1, String college2,int winner, String fullCollege1, String fullCollege2 ,Calendar calendar) {
        this();
        this.matchType = matchType;
        this.sportName = sportName;
        this.venue = venue;
        this.time = time;
        this.date = date;
        this.calendar = calendar;
        this.matchRound = type;
        this.college1=college1;
        this.college2=college2;
        this.score1 = score1;
        this.score2 = score2;
        this.winner = winner;
        this.fullCollege1=fullCollege1;
        this.fullCollege2=fullCollege2;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getMatchType() {
        return matchType;
    }

    public String getSportName() {
        return sportName;
    }

    public String getVenue() {
        return venue;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return matchRound;
    }

    public String getGoldName() {
        return goldName;
    }

    public String getSilverName() {
        return silverName;
    }

    public String getBronzeName() {
        return bronzeName;
    }

    public String getGoldRecord() {
        return goldRecord;
    }

    public String getSilverRecord() {
        return silverRecord;
    }

    public String getBronzeRecord() {
        return bronzeRecord;
    }

    public String getCollege1() {
        return college1;
    }

    public String getCollege2() {
        return college2;
    }

    public String getScore1() {
        return score1;
    }

    public String getScore2() {
        return score2;
    }

    public int getWinner() {
        return winner;
    }

    public String getMatchRound() {
        return matchRound;
    }

    public String getFullCollege1() {
        return fullCollege1;
    }

    public String getFullCollege2() {
        return fullCollege2;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }
}
