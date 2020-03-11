package net.incongru.tichu.action.impl;

import net.incongru.tichu.action.Action;
import net.incongru.tichu.action.ActionResult;
import net.incongru.tichu.action.ActionResult.Success;
import net.incongru.tichu.action.GameContext;
import net.incongru.tichu.model.Trick;

class NewTrick implements Action {
    @Override
    public ActionResult exec(GameContext ctx) {
        final Trick trick = ctx.game().currentRound().newTrick();
        ctx.log("New trick! %s", trick);
        return new Success() {
            // new trick
        };
    }
}
