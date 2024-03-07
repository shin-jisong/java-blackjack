package domain;

import java.util.List;
import java.util.Map;

public class Participants {

    private static final int DEALER_COUNT = 1;
    private static final int DEALER_BOUNDARY_SCORE = 17;

    private final Player dealer;
    private final Players players;


    public Participants(Player dealer, Players players) {
        this.dealer = dealer;
        this.players = players;
    }

    public void receivePlayerCard(Card card, int playerIndex) {
        players.receiveOnePlayerCard(card, playerIndex);
    }

    public void receiveDealerCard(Card card) {
        dealer.receiveCard(card);
    }

    public void receiveInitialDecks(List<Deck> decks) {
        Deck dealerDeck = decks.remove(decks.size() - 1);
        dealer.receiveDeck(dealerDeck);

        for (int index = 0; index < players.size(); index++) {
            Deck currentDeck = decks.get(index);
            players.receiveOnePlayerDeck(currentDeck, index);
        }
    }

    public Map<Player, Boolean> calculateResult() {
        return players.calculateResult(dealer.calculateScore());
    }

    public boolean isPlayerNotOver(int playerIndex) {
        return players.isOnePlayerNotOver(playerIndex);
    }

    public boolean isDealerNotOver() {
        return dealer.isNotOver(DEALER_BOUNDARY_SCORE);
    }

    public int count() {
        return countPlayers() + DEALER_COUNT;
    }

    public int countPlayers() {
        return players.size();
    }

    public Name getOnePlayerName(int playerIndex) {
        return players.getOnePlayerName(playerIndex);
    }

    public Player getOnePlayer(int playerIndex) {
        return players.getOnePlayer(playerIndex);
    }

    public Player getDealer() {
        return dealer;
    }

    public Players getPlayers() {
        return players;
    }
}
