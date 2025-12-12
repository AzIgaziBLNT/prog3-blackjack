package blackjack.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.shuffle;
import static org.junit.jupiter.api.Assertions.*;
public class DeckTests {
    @Test
    @DisplayName("2. Deck 52 elemből áll")
    public void getDeckSizeTest() {
        Deck d = new Deck();
        assertEquals(52, d.getDeckSize());
    }

    @Test
    @DisplayName("3. Deck minden színből (4) 13 kártyát tartalmaz")
    public void suitTest() {
        Deck d = new Deck();

        ArrayList<Card> clubs = new ArrayList<>();
        ArrayList<Card> spades = new ArrayList<>();
        ArrayList<Card> hearts = new ArrayList<>();
        ArrayList<Card> diamonds = new ArrayList<>();

        ArrayList<Card> temp = d.getDeck();
        for (int i=0; i< d.getDeckSize();++i)
        {
            if (temp.get(i).getSuit().equals("C")) clubs.add(temp.get(i));
            else if (temp.get(i).getSuit().equals("S")) spades.add(temp.get(i));
            else if (temp.get(i).getSuit().equals("H")) hearts.add(temp.get(i));
            else if (temp.get(i).getSuit().equals("D")) diamonds.add(temp.get(i));
        }

        assertAll(
                "Csoportosított assertionök a színek számaira",
                () -> assertEquals(13, clubs.size()),
                () -> assertEquals(13, spades.size()),
                () -> assertEquals(13, hearts.size()),
                () -> assertEquals(13, diamonds.size())
        );
    }

    @Test
    @DisplayName("4. Deck shuffle után nem ugyabban a sorrendben tartalmazza a kártyákat")
    public void deckShuffleTest(){
        Deck d = new Deck();
        ArrayList<Card> originalDeck = new ArrayList<>(d.getDeck()); //eredeti Card objektumokkal töltjük fel (shallow copy)

        d.shuffle(); //keverés

        assertEquals(originalDeck.size(), d.getDeckSize());
        assertNotEquals(originalDeck, d.getDeck());
    }


    @Test
    @DisplayName("5. Deck-ben nincs duplikáció")
    public void noDupeCardsTest() {
        Deck d = new Deck();
        ArrayList<Card> cards = d.getDeck();

        Set<String> cardStrings = new HashSet<>();

        for (Card c : cards)
            cardStrings.add(c.getSuit() + " " + c.getCardValue());

        assertEquals(52, cardStrings.size());
    }

    @Test
    @DisplayName("6. Minden számértékből pontosan 4 db van")
    public void valueCountCheck() {
        Deck d = new Deck();
        ArrayList<Card> cards = d.getDeck();

        int[] rankCounts = new int[15]; // 0-14 indexek, a 0 és 1 üres marad

        for (Card c : cards)
            rankCounts[c.getCardValue()]++;

        assertAll(
                "Érték checkek",
                () -> assertEquals(4, rankCounts[2]),
                () -> assertEquals(4, rankCounts[3]),
                () -> assertEquals(4, rankCounts[4]),
                () -> assertEquals(4, rankCounts[5]),
                () -> assertEquals(4, rankCounts[6]),
                () -> assertEquals(4, rankCounts[7]),
                () -> assertEquals(4, rankCounts[8]),
                () -> assertEquals(4, rankCounts[9]),
                () -> assertEquals(4, rankCounts[10]),
                () -> assertEquals(4, rankCounts[11]),
                () -> assertEquals(4, rankCounts[12]),
                () -> assertEquals(4, rankCounts[13]),
                () -> assertEquals(4, rankCounts[14])
        );
    }

}