package net.incongru.tichu.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.annotation.Nonnull;
import jakarta.websocket.Session;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        // our json shapes will have a "type" with values as specified below
        property = "messageType"
)
@JsonSubTypes({
        // We specify the type here using the Immutable* impls so serialising uses the correct name
        // but we still need to also specify @JsonDeserialize(as) on the abstract types for deserialisation
        @JsonSubTypes.Type(value = IncomingChatMessage.class, name = "chat"),
        @JsonSubTypes.Type(value = GameActionMessage.class, name = "game")
})
public interface IncomingMessage {

    /**
     * Incoming messages always have a transaction ID, generated by the client. No guarantees are made for it to be unique,
     * it must only be unique enough for the client to identify responses generated against this message.
     */
    @Nonnull
    @JsonProperty("txId")
    String clientTxId();

    // Visitor pattern to be implemented by subtypes
    void accept(Session session, String roomId, MessageHandler visitor);
}