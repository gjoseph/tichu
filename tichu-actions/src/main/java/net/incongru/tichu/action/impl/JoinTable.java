package net.incongru.tichu.action.impl;

import net.incongru.tichu.action.Action;
import net.incongru.tichu.action.ActionParam;
import net.incongru.tichu.action.ActionResponse;
import net.incongru.tichu.action.GameContext;
import net.incongru.tichu.action.param.JoinTableParam;
import net.incongru.tichu.model.Player;
import net.incongru.tichu.model.Players;
import net.incongru.tichu.model.Team;

class JoinTable implements Action<JoinTableParam, JoinTableResult> {

    JoinTable() {}

    @Override
    public ActionResponse<JoinTableResult> exec(
        GameContext ctx,
        ActionParam.WithActor<JoinTableParam> param
    ) {
        // TODO validating team number should be role of action/rules, but where does error bubble up if invalid ?
        final Players players = ctx.game().players();
        final Team team = players.getTeam(param.param().team());
        players.join(new Player(param.actor()), team);

        // TODO return Error if team full, wrong player etc
        return new SimpleResponse<>(
            param.actor(),
            ActionType.JOIN,
            players.isComplete()
                ? JoinTableResult.OK_TABLE_IS_NOW_FULL
                : JoinTableResult.OK
        );
    }
}
