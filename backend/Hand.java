package blackjack.backend;

import blackjack.gameLogic.GameStateHandler;

import java.util.ArrayList;

public class Hand  {
    private final ArrayList<Card> hand;
    public Hand() { this.hand = new ArrayList<>(); }
    public ArrayList<Card> getHand() { return hand; }

    public int getHandValue() {
        int handValue = 0;
        boolean hasAce = false;
        for (int i=0; i < hand.size(); ++i) {
            if (hand.get(i).getCardValue() <= 10)
                handValue += hand.get(i).getCardValue();
            else if (hand.get(i).getCardValue() > 10 && hand.get(i).getCardValue() < 14)
                handValue += 10;
            else if (hand.get(i).getCardValue() == 14) {
                handValue += 11;
                hasAce = true;
            }
        }

        // ha van ász
        if (hasAce && handValue > 21)
            handValue = handValue - 11 + 1; // ász értéke 11 helyett 1-et ér, mert így nem haladja meg a kéz értéke a 21-et

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
