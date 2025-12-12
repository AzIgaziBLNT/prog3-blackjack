package blackjack.backend;

import java.util.ArrayList;

public class Hand  {
    private final ArrayList<Card> hand;
    public Hand() { this.hand = new ArrayList<>(); }
    public ArrayList<Card> getHand() { return hand; }

    public int getHandValue() {
        int handValue = 0;
        int aceCount = 0;
        for (int i=0; i < hand.size(); ++i) {
            if (hand.get(i).getCardValue() <= 10) // 2-10
                handValue += hand.get(i).getCardValue();
            else if (hand.get(i).getCardValue() > 10 && hand.get(i).getCardValue() < 14) // J, Q, K
                handValue += 10;
            else if (hand.get(i).getCardValue() == 14) { // A
                handValue += 11;
                ++aceCount;
            }
        }

        // ha van ász
        while (handValue > 21 && aceCount > 0) {
            handValue = handValue - 11 + 1; // ász értéke 11 helyett 1-et ér, mert így nem haladja meg a kéz értéke a 21-et
            --aceCount;
        }

        return handValue;
    }

    public void draw(ArrayList<Card> d) {
        if (this.hand.isEmpty()) {
            for (int i = 0; i < 2; ++i) {
                this.hand.add(d.removeFirst()); // legfelső kártyát húzzuk (és egyben kivesszük a pakliból)
            }
        } else {
            this.hand.add(d.removeFirst());
        }
    }

    public void clear() {
        this.hand.clear();
    }
}
