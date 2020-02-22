package net.incongru.tichu.simu.parse;

import net.incongru.tichu.model.ImmutableScore;
import net.incongru.tichu.model.Score;
import net.incongru.tichu.simu.cmd.PostActionCommandFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class PACLineParsersTest {

    // We return mocks for everything, all we want to check is that the correct factory method is called
    // so we just call verify() which is slightly less verbose
    @Mock(answer = Answers.RETURNS_MOCKS)
    private PostActionCommandFactory pacFactory;

    private PACLineParsers parsers;

    @BeforeEach
    void setUp() {
        parsers = new PACLineParsers(pacFactory);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(pacFactory);
    }

    @Test
    public void throwsOnUnknownPostActionCommand() {
        assertThatThrownBy(() -> parsers.parse(t("ice cream")))
                .isInstanceOf(LineParserException.class)
                .hasMessageContaining("[ice cream]")
                .hasMessageContaining("unrecognised post-action-command");
    }

    @Test
    public void recognisesExpectSuccess() {
        assertThat(parsers.parse(t("expect success"))).isNotNull();
        verify(pacFactory).expectSuccess();
    }

    @Test
    public void recognisesExpectError() {
        assertThat(parsers.parse(t("expect error foo"))).isNotNull();
        verify(pacFactory).expectError("foo");
    }

    @Test
    public void recognisesExpectInvalidPlay() {
        assertThat(parsers.parse(t("expect invalid-play too weak"))).isNotNull();
        verify(pacFactory).expectPlayResult(PostActionCommandFactory.ExpectablePlayResult.TooWeak);
    }

    @Test
    public void failsOnInvalidExpectedPlay() {
        assertThatThrownBy(() -> parsers.parse(t("expect invalid-play foo")))
                .isInstanceOf(IllegalArgumentException.class) // TODO LineParserException ?
                .hasMessageContaining("foo is not a valid ExpectablePlayResult");
    }

    @Test
    public void recognisesExpectWinTrick() {
        assertThat(parsers.parse(t("expect quinn wins trick"))).isNotNull();
        verify(pacFactory).expectWinTrick("quinn");
    }

    @Test
    public void recognisesExpectPlayOfType() {
        assertThat(parsers.parse(t("expect played BombOf4"))).isNotNull();
        verify(pacFactory).expectPlay(PostActionCommandFactory.ExpectablePlay.BombOf4);
    }

    @Test
    public void recognisesExpectPlayOfTypeWithSpacesAndCaseInsensitive() {
        assertThat(parsers.parse(t("expect played bomb OF 4"))).isNotNull();
        verify(pacFactory).expectPlay(PostActionCommandFactory.ExpectablePlay.BombOf4);
    }

    @Test
    public void recognisesExpectNextPlayer() {
        assertThat(parsers.parse(t("expect next player is jules"))).isNotNull();
        verify(pacFactory).expectNextPlayerToBe("jules");
    }

    @Test
    public void recognisesExpectEndRound() {
        assertThat(parsers.parse(t("expect end round"))).isNotNull();
        verify(pacFactory).expectEndOfRound();
    }

    @ParameterizedTest
    @MethodSource
    public void recognisesExpectGameStatus(String txt, PostActionCommandFactory.ExpectableGameState expectedGameState) {
        // game expectations
        // not negates the following (peek)
        // last word is a boolean predicate
        assertThat(parsers.parse(t(txt))).isNotNull();
        verify(pacFactory).expectGameState(expectedGameState);
    }

    static Stream<Arguments> recognisesExpectGameStatus() {
        return Stream.of(
                arguments("expect game ready", PostActionCommandFactory.ExpectableGameState.ReadyToStart),
                arguments("expect game not ready", PostActionCommandFactory.ExpectableGameState.NotReadyToStart),
                arguments("expect game started", PostActionCommandFactory.ExpectableGameState.Started),
                arguments("expect game not started", PostActionCommandFactory.ExpectableGameState.NotStarted)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void recognisesExpectRoundTeamScores(String txt, Score expectedScore) {
        assertThat(parsers.parse(t(txt))).isNotNull();
        verify(pacFactory).expectRoundScore(expectedScore);
    }

    static Stream<Arguments> recognisesExpectRoundTeamScores() {
        return Stream.of(
                arguments("expect round score to be 80:20", ImmutableScore.of(80, 20)),
                arguments("expect round score 20:80", ImmutableScore.of(20, 80))
        );
    }

    @ParameterizedTest
    @MethodSource
    public void recognisesExpectTotalTeamScores(String txt, Score expectedScore) {
        assertThat(parsers.parse(t(txt))).isNotNull();
        verify(pacFactory).expectTotalScore(expectedScore);
    }

    static Stream<Arguments> recognisesExpectTotalTeamScores() {
        return Stream.of(
                arguments("expect total score to be 100:200", ImmutableScore.of(100, 200)),
                arguments("expect total score 450:300", ImmutableScore.of(450, 300))
        );
    }

    @Test
    public void recognisesDebugPlayerHand() {
        assertThat(parsers.parse(t("debug quinn hand"))).isNotNull();
        verify(pacFactory).debugPlayerHand("quinn");
    }

    private TokenisedLine t(String line) {
        return new TokenisedLine(line);
    }
}