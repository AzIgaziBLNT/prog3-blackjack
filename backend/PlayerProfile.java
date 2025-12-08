package blackjack.backend;

import java.io.Serializable;

public class PlayerProfile implements Serializable {
    private static final long serialVersionUID = 1L; //verziókövetés szerializáláshoz
    private String name;
    private int winCount = 0;
    private int loseCount = 0;
    private long netWorth = 0; // összes nyereség/veszteség


    /// setterek
    public PlayerProfile(String n) { this.name = n; }
    public void setWinCount() { ++this.winCount; }
    public void setLoseCount() { ++this.loseCount; }
    public void setNetWorth(long p) { this.netWorth += p; }


    /// getterek
    public String getName() { return name; }
    public int getWinCount() { return winCount; }
    public int getLoseCount() { return loseCount; }
    public long getNetWorth() { return netWorth; }

    @Override
    public String toString() { return name; }
}
