package net.incongru.tichu.action;

/**
 * Creates Action instances.
 * Currently mostly exists so we can mock out this interface in tests.
 */
public interface ActionFactory {

    Action init();

}
