package net.incongru.tichu.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.incongru.tichu.model.plays.BombOf4;
import net.incongru.tichu.model.plays.Pair;
import net.incongru.tichu.model.plays.Single;
import net.incongru.tichu.model.plays.Street;
import net.incongru.tichu.model.plays.Triple;

/**
 * Models most (all?) rules of the game, such that, maybe, one day (...) we can make this an interface and swap out some rules. Whatever.
 */
public class TichuRules {
    private final List<Play.PlayFactory> factories;

    public TichuRules() {
        this.factories = makeFactories();
    }

    // If rules were to change, one would not only subclass TichuRules, but probably provide different factories too.
    protected List<Play.PlayFactory> makeFactories() {
        return Arrays.asList(
                new Single.Factory(),
                new Pair.Factory(),
                new Triple.Factory(),
                new BombOf4.Factory(),
                new Street.Factory()
        );
    }

    public Play validate(Set<Card> cards) {
        for (Play.PlayFactory factory : factories) {
            Play candidate = factory.is(cards);
            if (candidate != null) {
                return candidate;
            }
        }
        // TODO should we throw ? or return a null object of sorts ? return null;
        // TODO see InvalidPlay
        return null;//throw new IllegalArgumentException("Not a valid play: " + cards);

    }

    public boolean isBomb(Play play) {
        return play.isBomb();
    }

    public boolean isValid(Play play) {
        // check the play even makes sense (no identical cards)
        // maybe it needs the deck or game to know what cards have already been discarded etc
        return true;
    }

    public boolean canPlayAfter(Play before, Play after) {
        if (!isValid(after)) {
            throw new IllegalStateException("Learn the rules. Call isValid() before canPlayAfter().");
        }
        return after.canBePlayedAfter(before);
    }

}
