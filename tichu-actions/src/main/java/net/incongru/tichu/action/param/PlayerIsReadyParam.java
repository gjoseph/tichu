package net.incongru.tichu.action.param;

import net.incongru.tichu.action.ActionParam;
import net.incongru.tichu.model.UserId;
import org.immutables.value.Value;

@Value.Immutable
public interface PlayerIsReadyParam extends ActionParam {

    static WithActor<PlayerIsReadyParam> withActor(UserId player) {
        return new WithActor<>(player, ImmutablePlayerIsReadyParam.builder().build());
    }
}
