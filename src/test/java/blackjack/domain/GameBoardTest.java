package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;

import blackjack.domain.Cards.Card;
import blackjack.domain.Cards.Deck;
import blackjack.domain.Cards.Rank;
import blackjack.domain.Cards.Shape;
import blackjack.domain.participants.Name;
import blackjack.domain.participants.Player;
import blackjack.domain.participants.Players;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameBoardTest {

    private Player siso;
    private Player takan;
    private Player dealer;
    private Players players;
    private GameBoard gameBoard;

    @BeforeEach
    void beforeEach() {
        siso = new Player(new Name("시소"));
        takan = new Player(new Name("타칸"));

        Deck sisoDeck = new Deck();
        sisoDeck.addCard(new Card(Shape.HEART, Rank.JACK));
        sisoDeck.addCard(new Card(Shape.HEART, Rank.SIX)); // 16

        Deck takanDeck = new Deck();
        takanDeck.addCard(new Card(Shape.SPADE, Rank.ACE));
        takanDeck.addCard(new Card(Shape.SPADE, Rank.JACK)); // 21

        siso.receiveDeck(sisoDeck);
        takan.receiveDeck(takanDeck);

        List<Player> playerList = List.of(siso, takan);
        players = new Players(playerList);

        dealer = new Player(new Name("딜러"));
        gameBoard = new GameBoard(dealer, players);

    }

    @Test
    @DisplayName("52장 카드 생성한다.")
    void makeAllCardTest() {
        int result = gameBoard.getAllCardDeck().size();

        assertThat(result).isEqualTo(52);
    }

    @Test
    @DisplayName("플레이어의 수를 센다.")
    void countPlayerTest() {
        int result = gameBoard.countPlayers();

        assertThat(result).isEqualTo(2);
    }

    @Test
    @DisplayName("플레이어가 카드를 더 가질 수 있다.")
    void isPlayerNotOverTest() {
        boolean result = gameBoard.isPlayerNotOver(0);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("딜러가 카드를 더 가질 수 있다.")
    void isDealerNotOverTest() {
        dealer.receiveCard(new Card(Shape.HEART, Rank.SEVEN));
        dealer.receiveCard(new Card(Shape.HEART, Rank.SIX));

        boolean result = gameBoard.isDealerNotOver();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("초기에 딜러와 플레이어는 카드 두 장을 받는다.")
    void initialDistributeTest() {
        gameBoard.distributeInitialDecks();

        assertThat(dealer.getHand().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("플레이어가 카드 한 장을 더 받는다.")
    void addCardToPlayerTest() {
        gameBoard.addCardToPlayer(0);

        assertThat(siso.getHand().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("딜러가 카드 한 장을 더 받는다.")
    void addCardToDealerTest() {
        gameBoard.addCardToDealer();

        assertThat(dealer.getHand().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("플레이어가 모두 패배한 테스트")
    void victoryLoseTest() {
        dealer.receiveCard(new Card(Shape.HEART, Rank.ACE));
        dealer.receiveCard(new Card(Shape.HEART, Rank.JACK));

        Map<Player, Boolean> victoryResult = gameBoard.calculateVictory();


        assertThat(victoryResult.get(siso)).isFalse();
        assertThat(victoryResult.get(takan)).isFalse();
    }

    @Test
    @DisplayName("플레이어의 승리가 포함된 테스트")
    void victoryWinTest() {
        dealer.receiveCard(new Card(Shape.HEART, Rank.QUEEN));
        dealer.receiveCard(new Card(Shape.HEART, Rank.JACK));

        Map<Player, Boolean> victoryResult = gameBoard.calculateVictory();

        assertThat(victoryResult.get(takan)).isTrue();
        assertThat(victoryResult.get(siso)).isFalse();
    }
}
