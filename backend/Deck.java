package blackjack.backend;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ///  i segítségével suit (szín) meghatározása
    ///  j segítségével kártya meghatározása (number/face card)
    private ArrayList<Card> deck;
    public Deck() {
        this.deck= new ArrayList<>();

        for (SuitEnum se : SuitEnum.values()){
            for (int j=2;j <= 14; ++j) {
                Card c = new Card(j,se.toString());
                this.deck.add(c);
            }
        }
    }

    public int getDeckSize() { return deck.size(); }

    public ArrayList<Card> getDeck() { return this.deck; }
    public void setDeck(ArrayList<Card> d) { this.deck = d; }
    public void shuffle() { Collections.shuffle(this.getDeck()); }

    // színek (suit) enumja
    public enum SuitEnum {
        SPADE,
        CLUB,
        HEART,
        DIAMOND
    }

}
