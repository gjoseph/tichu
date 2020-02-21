package net.incongru.tichu.simu.parse;

import net.incongru.tichu.simu.Simulation;
import net.incongru.tichu.simu.cmd.PostActionCommandFactory;
import net.incongru.tichu.simu.util.NameableEnum;

import java.util.Arrays;

import static net.incongru.tichu.simu.parse.FunctionsBasedLineParser.simpleParser;

/**
 * Parsers for PostActionCommands; the leading `-` for PACs is expected to have been removed prior to calling these.
 */
class PACLineParsers extends AbstractLineParsers<Simulation.PostActionCommand> {

    PACLineParsers(PostActionCommandFactory pacFactory) {
        super("post-action-command", Arrays.asList(
                simpleParser(
                        t -> expect(t, "success"),
                        t -> pacFactory.expectSuccess()
                ),
                simpleParser(
                        t -> expect(t, "error"), // TODO exactly what are our scenarios, if not invalid plays
                        t -> pacFactory.expectError(t.remainder())
                ),
                simpleParser(
                        t -> expect(t, "invalid-play"), // TODO other keywords?
                        t -> {
                            final PostActionCommandFactory.ExpectablePlayResult expectedPlayResult = NameableEnum.byName(PostActionCommandFactory.ExpectablePlayResult.class, t.remainder());
                            return pacFactory.expectPlayResult(expectedPlayResult);
                        }
                ),
                simpleParser(
                        t -> expect(t, "game"),
                        t -> {
                            final PostActionCommandFactory.ExpectableGameState expectedGameState = NameableEnum.byName(PostActionCommandFactory.ExpectableGameState.class, t.remainder());
                            return pacFactory.expectGameState(expectedGameState);
                        }
                ),
                simpleParser(
                        t -> expect(t, "played"),
                        t -> {
                            final PostActionCommandFactory.ExpectablePlay play = NameableEnum.byName(PostActionCommandFactory.ExpectablePlay.class, t.remainder());
                            return pacFactory.expectPlay(play);
                        }
                ),
                simpleParser(
                        t -> t.test(1, "next") && t.test(1, "player") && t.test(1, "is") && t.test(0, "expect"),
                        t -> pacFactory.expectNextPlayerToBe(t.remainder())
                ),
                simpleParser(
                        t -> {// <player> wins trick
                            return t.test(2, "wins") && t.test(2, "trick")
                                    && t.test(0, "expect");
                        },
                        t -> pacFactory.expectWinTrick(t.pop(0))
                ),
                simpleParser(
                        t -> t.test(2, "round") && expect(t, "end"),
                        t -> pacFactory.expectEndOfRound()
                ),
                simpleParser(
                        t -> expect(t, "score"),
                        t -> pacFactory.expectRoundScore(t.pop(0), t.popInt(0))
                ),
                simpleParser(
                        t -> t.peek(0, "debug") && t.test(2, "hand"),
                        t -> pacFactory.debugPlayerHand(t.pop(1))
                )
        ));
    }

    private static boolean expect(TokenisedLine t, String s) {
        return t.test(1, s) && t.test(0, "expect");
    }

}
