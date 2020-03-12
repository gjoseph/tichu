package net.incongru.tichu.action;

/**
 * Creates Action instances.
 * Currently mostly exists so we can mock out this interface in tests.
 */
public interface ActionFactory {

    <P extends ActionParam> Action<? extends P> actionFor(P param);

}
