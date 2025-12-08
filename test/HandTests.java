package blackjack.test;

import blackjack.backend.Card;
import blackjack.backend.Deck;
import blackjack.backend.Hand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HandTests {

    @Test
    @DisplayName("7. Lap húzása csökkenti a pakli méretét")
    public void drawCardTest() {
        Deck d = new Deck();
        int initialSize = d.getDeckSize();

        Hand player = new Hand();

        player.draw(d.getDeck()); // 2 kártyát húzunk, mert üres a kezünk
        player.draw(d.getDeck()); // húzunk mégegyet (hit)

        assertAll(
                "Csoportos assertek húzásokra (draw)",
                () -> assertNotNull(player.getHand()),
                () -> assertEquals(initialSize - 3, d.getDeckSize()), // a méretnek csökkennie kell 3-mal
                () -> assertFalse(d.getDeck().contains(player.getHand().getFirst())), //a  kihúzott lap már nem lehet a pakliban
                () -> assertFalse(d.getDeck().contains(player.getHand().get(1))),
                () -> assertFalse(d.getDeck().contains(player.getHand().get(2)))
        );
    }
}
