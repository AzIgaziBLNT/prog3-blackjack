package blackjack.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTests {
    @Test
    @DisplayName("1. Card konstruktor teszt")
    public void cardConstructorTest() {
        Card c = new Card(2,"SPADE");
        assertEquals(2, c.getCardValue());
    }
}
