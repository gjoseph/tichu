package net.incongru.tichu.action.impl;

import net.incongru.tichu.action.Action;
import net.incongru.tichu.model.Game;
import net.incongru.tichu.simu.SimulatedGameContext;

class PlayerIsReady implements Action {
    private final String playerName;

    PlayerIsReady(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public Result exec(SimulatedGameContext ctx) {
        ctx.player(playerName).setReady();
        final Game game = ctx.game();
        if (game.players().areAllReady()) {
            game.start(); // TODO do we want to check isReadyToStart?
            game.currentRound().start(); // TODO see net.incongru.tichu.model.Round.start
            return new Success() {
                // game started
            };
        } else {
            return new Success() {
                // played marked ready but game not started
            };
        }
    }
}
