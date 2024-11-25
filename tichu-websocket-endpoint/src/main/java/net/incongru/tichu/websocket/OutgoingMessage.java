package net.incongru.tichu.websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        // our json shapes will have a "messageType" with values as specified below
        property = "messageType"
)
@JsonSubTypes({
        // We specify the type here using the Immutable* impls so serialising uses the correct name
        // but we still need to also specify @JsonDeserialize(as) on the abstract types for deserialisation
        @JsonSubTypes.Type(value = ImmutableOutgoingChatMessage.class, name = "chat"),
        @JsonSubTypes.Type(value = GameActionResultMessage.class, name = "game"),
        // perhaps this should actually be part of GameActionResultMessage
        @JsonSubTypes.Type(value = GameStatusMessage.class, name = "status"),
        @JsonSubTypes.Type(value = PlayerHandMessage.class, name = "hand"),
        @JsonSubTypes.Type(value = ErrorMessage.class, name = "error"),
        @JsonSubTypes.Type(value = RoomActivityMessage.class, name = "activity")
})
public interface OutgoingMessage {

}