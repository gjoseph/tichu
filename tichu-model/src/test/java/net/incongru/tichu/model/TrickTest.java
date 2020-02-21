package net.incongru.tichu.model;

import com.google.common.collect.Sets;
import net.incongru.tichu.model.util.DeckConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Collections.emptySet;
import static net.incongru.tichu.model.TestUtil.samplePlayers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrickTest {

    private final TichuRules tichuRules = new TichuRules();
    private Players players;
    private Player alex, charlie, jules, quinn;

    @BeforeEach
    void setUp() {
        players = samplePlayers();
        alex = players.getPlayerByName("Alex").orElseThrow();
        charlie = players.getPlayerByName("Charlie").orElseThrow();
        jules = players.getPlayerByName("Jules").orElseThrow();
        quinn = players.getPlayerByName("Quinn").orElseThrow();
    }

    @Test
    public void isDoneOnceEveryBodyPassed() {
        final Trick trick = newTrickFromPlayer1(players);
        alex.deal(DeckConstants.B2);

        // 1st player plays
        assertThat(
                trick.play(alex, Sets.newHashSet(DeckConstants.B2)).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);

        // players 2 and 3 pass, we're not done yet
        assertThat(
                trick.play(charlie, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertThat(
                trick.play(jules, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertFalse(trick.isDone());

        // 4th player passes, we're done
        assertThat(
                trick.play(quinn, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.TRICK_END);
        assertTrue(trick.isDone());
    }

    @Test
    public void allPassIsNotDone() {
        final Trick trick = newTrickFromPlayer1(players);
        assertThat(
                trick.play(alex, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertFalse(trick.isDone());

        assertThat(
                trick.play(charlie, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertFalse(trick.isDone());

        assertThat(
                trick.play(jules, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertFalse(trick.isDone());

        assertThat(
                trick.play(quinn, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertFalse(trick.isDone());

        // and still not done when coming back to 1st player
        assertThat(
                trick.play(alex, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertFalse(trick.isDone());
    }

    @Test
    public void prevPlayReturnsPreviousNonPassPlay() {
        final Trick trick = newTrickFromPlayer1(players);
        alex.deal(DeckConstants.B2);
        quinn.deal(DeckConstants.G5);

        // 1st player plays
        assertThat(
                trick.play(alex, Sets.newHashSet(DeckConstants.B2)).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);

        // players 2 and 3 pass, we're not done yet
        assertThat(
                trick.play(charlie, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertThat(
                trick.play(jules, emptySet()).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertFalse(trick.isDone());

        // previous non-pass play is player 1's single
        assertThat(trick.previousNonPass().play().getCards()).isEqualTo(Collections.singleton(DeckConstants.B2));
        // player 4 plays another single, that's valid
        assertThat(
                trick.play(quinn, Sets.newHashSet(DeckConstants.G5)).result()
        ).isEqualTo(Play.PlayResult.Result.NEXTGOES);
        assertFalse(trick.isDone());
    }

    private Trick newTrickFromPlayer1(Players players) {
        return new Trick(tichuRules, players.cycleFrom(alex));
    }

}
