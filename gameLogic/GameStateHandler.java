package blackjack.gameLogic;

import blackjack.backend.Deck;
import blackjack.backend.Hand;

public class GameStateHandler {
    public enum GameState {
        DEALING, // osztás
        PLAYER_ROUND, // játékos köre
        DEALER_ROUND, // osztó köre
        ROUND_END // kör vége, értékszámítás
    }

    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private GameState currentState;


    public GameStateHandler() {
        this.deck = new Deck();
        this.deck.shuffle();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.currentState = GameState.DEALING;

    }

    // 21 felett van-e az adott kéz
    public void bustCheck(Hand currentHand) {
        if (currentHand.getHandValue() > 21) {
            currentState = GameState.ROUND_END;
        }
    }

    // új kör, 2-2 lap osztása
    public void newRound() {
        playerHand.clear();
        dealerHand.clear();

        playerHand.draw(deck.getDeck());
        dealerHand.draw(deck.getDeck());

        bustCheck(playerHand);

        currentState = GameState.PLAYER_ROUND;
    }

    // játékos húz
    public void hit() {
        if (currentState != GameState.PLAYER_ROUND) return; // failsafe

        playerHand.draw(deck.getDeck());
        bustCheck(playerHand);
    }

    // játékos megáll
    public void stand() {
        if (currentState != GameState.PLAYER_ROUND) return; // failsafe

        currentState = GameState.DEALER_ROUND;
    }

    private void runDealerTurn() {
        while(dealerHand.getHandValue() < 17) {
            dealerHand.draw(deck.getDeck());
        }

        currentState = GameState.ROUND_END;

    }

    // getter(ek)
    public GameState getCurrentState() { return currentState; }
}