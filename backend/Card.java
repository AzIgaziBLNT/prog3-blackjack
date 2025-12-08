package blackjack.backend;

import java.io.Serializable;

public class Card implements Serializable {
    private int value; // 2-14 (13 kártya, 2-től kezdve sorban kap értéket)
    private String suit; // szín, megjelenítésnél releváns
    private boolean hidden = false; // rejtett kártya-e (osztó esetében releváns megjelenítésnél)

    public Card(int v, String s) {
        this.value = v;
        this.suit = s;
    }
    public int getCardValue() { return this.value; }
    //public void setCardValue(int v) { this.value = v; }

    public String getSuit() { return this.suit; }
    public boolean getIsHidden() { return this.hidden; }
    public void setIsHidden(boolean b) { this.hidden = b;}
}
