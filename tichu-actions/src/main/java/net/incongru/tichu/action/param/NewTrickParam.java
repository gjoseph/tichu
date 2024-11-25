package net.incongru.tichu.action.param;

import net.incongru.tichu.action.ActionParam;
import net.incongru.tichu.model.UserId;
import org.immutables.value.Value;

@Value.Immutable
public interface NewTrickParam extends ActionParam {

    static WithActor<NewTrickParam> withActor(UserId player) {
        return new WithActor<>(player, ImmutableNewTrickParam.builder().build());
    }
}
