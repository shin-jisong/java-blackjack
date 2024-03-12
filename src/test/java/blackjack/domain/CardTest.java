package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;

import blackjack.domain.cards.Card;
import blackjack.domain.cards.Rank;
import blackjack.domain.cards.Shape;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CardTest {

    @Test
    @DisplayName("카드가 잘 생성된다.")
    void constructorSuccessTest() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new Card(Shape.HEART, Rank.ACE));
    }

    @Test
    @DisplayName("카드가 에이스임이 참이다.")
    void isAceTrueTest() {
        Card card = new Card(Shape.HEART, Rank.ACE);

        assertThat(card.isAce()).isTrue();
    }

    @Test
    @DisplayName("카드가 에이스임이 거짓이다.")
    void isAceFailTest() {
        Card card = new Card(Shape.HEART, Rank.JACK);

        assertThat(card.isAce()).isFalse();
    }
}
