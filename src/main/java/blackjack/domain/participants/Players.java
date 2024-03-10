package blackjack.domain.participants;


import blackjack.domain.Cards.Card;
import blackjack.domain.Cards.Deck;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Players {
    public static final int MAX_SCORE = 21;

    private final List<Player> players;

    public Players(List<Player> players) {
        this.players = players;
    }

    public Map<Player, Boolean> calculateVictory(int dealerScore) {
        Map<Player, Boolean> result = new LinkedHashMap<>();
        players.forEach(player -> result.put(player, isPlayerWin(player, dealerScore)));
        return result;
    }

    private boolean isPlayerWin(Player player, int dealerScore) {
        if (player.calculateScore() > MAX_SCORE) {
            return false;
        }
        if (dealerScore > MAX_SCORE) {
            return true;
        }
        return dealerScore < player.calculateScore();
    }

    public void receiveOnePlayerCard(Card card, int playerIndex) {
        players.get(playerIndex).receiveCard(card);
    }

    public void receiveOnePlayerDeck(Deck deck, int playerIndex) {
        players.get(playerIndex).receiveDeck(deck);
    }

    public boolean isOnePlayerNotOver(int playerIndex) {
        return players.get(playerIndex).isNotOver(MAX_SCORE);
    }

    public int size() {
        return players.size();
    }

    public Name getOnePlayerName(int playerIndex) {
        return players.get(playerIndex).getName();
    }

    public Player getOnePlayer(int playerIndex) {
        return players.get(playerIndex);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
