package bitspilani.bosm.items;


import java.util.Calendar;

public class ItemRoulette {

    private String doc_id;
    private int sport_id;
    private String sport_name;
    private String venue;
    private Calendar timestamp;
//    private int status;
    private String college1;
    private String college2;
    private int winner;
    private String round;
    private boolean betting_done;
    private int status;

    public ItemRoulette(String doc_id, int sport_id, String sport_name, String venue, Calendar timestamp, String college1, String college2, int winner, String round, boolean betting_done, int status) {
        this.doc_id = doc_id;
        this.sport_id = sport_id;
        this.sport_name = sport_name;
        this.venue = venue;
        this.timestamp = timestamp;
        this.status = status;
        this.college1 = college1;
        this.college2 = college2;
        this.winner = winner;
        this.round = round;
        this.betting_done = betting_done;
    }

    public int getStatus() {
        return status;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public int getSport_id() {
        return sport_id;
    }

    public String getSport_name() {
        return sport_name;
    }

    public String getVenue() {
        return venue;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public String getCollege1() {
        return college1;
    }

    public String getCollege2() {
        return college2;
    }

    public int getWinner() {
        return winner;
    }

    public String getRound() {
        return round;
    }

    public boolean isBetting_done() {
        return betting_done;
    }
}
