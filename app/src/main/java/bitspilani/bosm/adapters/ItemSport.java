package bitspilani.bosm.adapters;

import java.util.ArrayList;

public class ItemSport {
    private int sport_id;
    private String name;
    private boolean isGender;
    private ArrayList<String> arrayList;

    public ItemSport(int sport_id, String name, boolean isGender,ArrayList<String> arrayList) {
        this.sport_id = sport_id;
        this.name = name;
        this.arrayList = arrayList;
        this.isGender = isGender;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public int getSport_id() {
        return sport_id;
    }

    public String getName() {
        return name;
    }

    public boolean isGender() {
        return isGender;
    }


}

