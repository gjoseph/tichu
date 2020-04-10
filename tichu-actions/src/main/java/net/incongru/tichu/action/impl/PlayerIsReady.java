package net.incongru.tichu.action.impl;

import net.incongru.tichu.action.Action;
import net.incongru.tichu.action.ActionParam;
import net.incongru.tichu.action.ActionResult;
import net.incongru.tichu.action.ActionResult.Success;
import net.incongru.tichu.action.GameContext;
import net.incongru.tichu.action.param.PlayerIsReadyParam;
import net.incongru.tichu.model.Game;

class PlayerIsReady implements Action<PlayerIsReadyParam> {

    PlayerIsReady() {
    }

    @Override
    public ActionResult exec(GameContext ctx, ActionParam.WithActor<PlayerIsReadyParam> param) {
        ctx.player(param.actor()).setReady();
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
