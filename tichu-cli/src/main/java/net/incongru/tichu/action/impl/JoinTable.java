package net.incongru.tichu.action.impl;

import net.incongru.tichu.action.Action;
import net.incongru.tichu.simu.SimulatedGameContext;

class JoinTable implements Action {
    private final String playerName;
    private final int team;

    JoinTable(String playerName, int team) {
        this.playerName = playerName;
        this.team = team;
    }

    @Override
    public Result exec(SimulatedGameContext ctx) {
        // TODO validating team number should be role of action/rules, but where does error bubble up if invalid ?
        throw new IllegalStateException("Not implemented yet");
    }
}
