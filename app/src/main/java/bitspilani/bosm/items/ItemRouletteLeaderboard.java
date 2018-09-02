package bitspilani.bosm.items;

public class ItemRouletteLeaderboard {
    private String email;
    private String name;
    private int score;
    private int bettingAmount;

    public ItemRouletteLeaderboard(String email, String name, int score, int bettingAmount) {
        this.email = email;
        this.name = name;
        this.score = score;
        this.bettingAmount = bettingAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(int bettingAmount) {
        this.bettingAmount = bettingAmount;
    }
}
