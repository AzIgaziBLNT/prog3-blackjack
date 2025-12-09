package blackjack.logic;

import blackjack.backend.Deck;
import blackjack.backend.Hand;
import blackjack.backend.PlayerProfile;

public class GameStateHandler {
    public enum GameState {
        DEALING, // osztás
        PLAYER_ROUND, // játékos köre
        DEALER_ROUND, // osztó köre
        ROUND_END // kör vége, értékszámítás
    }

    public enum EndState {
        PUSH, // döntetlen
        LOSE, // játékos veszt
        WIN, // játékos nyer, mert közelebb van a 21-hez mint az osztó
        PLAYER_BUST, // 21 fölé ment a játékos
        DEALER_BUST, // 21 fölé ment az osztó
        BLACKJACK, // első 2 lap 21, és osztó nem blackjack-et ért el
        NONE // kezdeti állapot
    }

    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private long bet;
    private GameState currentState;
    private EndState endState;


    public GameStateHandler() {
        this.deck = new Deck();
        this.deck.shuffle();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.currentState = GameState.DEALING;
        this.endState = EndState.NONE;
    }

    // 21 felett van-e az adott kéz
    public void bustCheck(Hand currentHand) {
        if (currentHand.getHandValue() > 21) {
            currentState = GameState.ROUND_END;
        }
        evaluateRound();
    }

    // új kör, 2-2 lap osztása
    public void newRound() {
        playerHand.clear();
        dealerHand.clear();

        playerHand.draw(deck.getDeck());
        dealerHand.draw(deck.getDeck());
        dealerHand.getHand().get(1).setIsHidden(true);

        bustCheck(playerHand);

        currentState = GameState.PLAYER_ROUND;
        endState = EndState.NONE;
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
        runDealerTurn();
    }

    private void runDealerTurn() {
        dealerHand.getHand().get(1).setIsHidden(false);

        while(dealerHand.getHandValue() < 17) {
            dealerHand.draw(deck.getDeck());
        }

        currentState = GameState.ROUND_END;
        evaluateRound();
    }

    private void evaluateRound() {
        if (this.currentState != GameState.ROUND_END) return; // failsafe

        if (playerHand.getHandValue() > 21 && dealerHand.getHandValue() > 21) // push
            setEndState(EndState.PUSH);
        else if (playerHand.getHandValue() > 21) // player bust
            setEndState(EndState.PLAYER_BUST);
        else if (dealerHand.getHandValue() > 21) // dealer bust
            setEndState(EndState.DEALER_BUST);
        else if (playerHand.getHandValue() == 21 && playerHand.getHand().size() == 2) // blackjack
            setEndState(EndState.BLACKJACK);
        else if (playerHand.getHandValue() < dealerHand.getHandValue()) // lose
            setEndState(EndState.LOSE);
        else if (playerHand.getHandValue() > dealerHand.getHandValue()) // win
            setEndState(EndState.WIN);
    }

    public String payout(PlayerProfile profile) {
        if (endState == EndState.NONE) return "";

        String msg = "";
        switch (endState) {
            case WIN:
                msg = "GG EZ! Nyertél " + 2*bet + " JMF-et";
                profile.setNetWorth(2*bet);
                profile.setWinCount();
                break;
            case LOSE:
                msg = "Az osztó nyert. Vesztettél " + bet + " JMF-et";
                profile.setNetWorth(-bet);
                profile.setLoseCount();
                break;
            case PLAYER_BUST:
                msg = "Besokalltál (bust)! Vesztettél " + bet + " JMF-et";
                profile.setNetWorth(-bet);
                profile.setLoseCount();
                break;
            case DEALER_BUST:
                msg = "Az osztó besokallt (bust)! Nyertél " + 2*bet + " JMF-et";
                profile.setNetWorth(2*bet);
                profile.setWinCount();
                break;
            case PUSH:
                msg = "Döntetlen (push)! A tét visszajár";
                break;
            case BLACKJACK:
                msg = "BLACKJACK!!! Nyertél: " + 3*bet + " JMF-et!!!";
                profile.setNetWorth(3*bet);
                profile.setWinCount();
                break;
            default:
                break;
        }

        return msg;
    }

    // setterek / getterek
    public GameState getCurrentState() { return currentState; }
    public EndState getEndState() { return endState; }
    public void setEndState(EndState es) { this.endState = es; }
    public Hand getPlayerHand() { return playerHand; }
    public Hand getDealerHand() { return dealerHand; }
    public void setBet(long b) { this.bet = b; }
    public long getBet() { return bet; }
}