package bitspilani.bosm.items;

import java.util.ArrayList;

public class ItemRound {
    private String round;
    private ArrayList<ItemRound2> match_details;

    public ItemRound(String round, ArrayList<ItemRound2> match_details) {
        this.round = round;
        this.match_details = match_details;
    }

    public String getRound() {
        return round;
    }

    public ArrayList<ItemRound2> getMatch_details() {
        return match_details;
    }
}
