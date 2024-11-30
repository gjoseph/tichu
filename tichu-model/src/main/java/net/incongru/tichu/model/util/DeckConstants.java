package net.incongru.tichu.model.util;

import net.incongru.tichu.model.card.Card;
import net.incongru.tichu.model.card.CardNumbers;
import net.incongru.tichu.model.card.CardSpecials;
import net.incongru.tichu.model.card.CardSuit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.TreeMap;
import net.incongru.tichu.model.Card;

/**
 *
 */
public class DeckConstants {

    /**
     * Gets a card by name, where the name is a constant defined in this class. This method
     * is case-insensitive, such that byName("r2")==byName("R2")
     *
     * @param n
     * @return
     */
    public static Card byName(String n) {
        final Card card = namedCards.get(n);
        if (card == null) {
            throw new IllegalArgumentException(n + " is not a valid card name");
        }
        return card;
    }

    // ==== Start CodeGen -- this was generated by class net.incongru.tichu.model.util.DeckConstantsCodeGen on 2020-06-25T14:02:42.702008Z
    public static final Card Phoenix = Card.of(CardSpecials.Phoenix);
    public static final Card _P = Phoenix;
    public static final Card MahJong = Card.of(CardSpecials.MahJong);
    public static final Card _1 = MahJong;
    public static final Card Dog = Card.of(CardSpecials.Dog);
    public static final Card _H = Dog;
    public static final Card Hound = Dog;
    public static final Card Dragon = Card.of(CardSpecials.Dragon);
    public static final Card _D = Dragon;
    public static final Card Pagoda_2 = Card.of(CardNumbers.Two, CardSuit.Pagoda);
    public static final Card B2 = Pagoda_2;
    public static final Card Pagoda_3 = Card.of(CardNumbers.Three, CardSuit.Pagoda);
    public static final Card B3 = Pagoda_3;
    public static final Card Pagoda_4 = Card.of(CardNumbers.Four, CardSuit.Pagoda);
    public static final Card B4 = Pagoda_4;
    public static final Card Pagoda_5 = Card.of(CardNumbers.Five, CardSuit.Pagoda);
    public static final Card B5 = Pagoda_5;
    public static final Card Pagoda_6 = Card.of(CardNumbers.Six, CardSuit.Pagoda);
    public static final Card B6 = Pagoda_6;
    public static final Card Pagoda_7 = Card.of(CardNumbers.Seven, CardSuit.Pagoda);
    public static final Card B7 = Pagoda_7;
    public static final Card Pagoda_8 = Card.of(CardNumbers.Eight, CardSuit.Pagoda);
    public static final Card B8 = Pagoda_8;
    public static final Card Pagoda_9 = Card.of(CardNumbers.Nine, CardSuit.Pagoda);
    public static final Card B9 = Pagoda_9;
    public static final Card Pagoda_10 = Card.of(CardNumbers.Ten, CardSuit.Pagoda);
    public static final Card B0 = Pagoda_10;
    public static final Card B10 = Pagoda_10;
    public static final Card Pagoda_Jack = Card.of(CardNumbers.Jack, CardSuit.Pagoda);
    public static final Card BJ = Pagoda_Jack;
    public static final Card Pagoda_Queen = Card.of(CardNumbers.Queen, CardSuit.Pagoda);
    public static final Card BQ = Pagoda_Queen;
    public static final Card Pagoda_King = Card.of(CardNumbers.King, CardSuit.Pagoda);
    public static final Card BK = Pagoda_King;
    public static final Card Pagoda_Ace = Card.of(CardNumbers.Ace, CardSuit.Pagoda);
    public static final Card BA = Pagoda_Ace;
    public static final Card Jade_2 = Card.of(CardNumbers.Two, CardSuit.Jade);
    public static final Card G2 = Jade_2;
    public static final Card Jade_3 = Card.of(CardNumbers.Three, CardSuit.Jade);
    public static final Card G3 = Jade_3;
    public static final Card Jade_4 = Card.of(CardNumbers.Four, CardSuit.Jade);
    public static final Card G4 = Jade_4;
    public static final Card Jade_5 = Card.of(CardNumbers.Five, CardSuit.Jade);
    public static final Card G5 = Jade_5;
    public static final Card Jade_6 = Card.of(CardNumbers.Six, CardSuit.Jade);
    public static final Card G6 = Jade_6;
    public static final Card Jade_7 = Card.of(CardNumbers.Seven, CardSuit.Jade);
    public static final Card G7 = Jade_7;
    public static final Card Jade_8 = Card.of(CardNumbers.Eight, CardSuit.Jade);
    public static final Card G8 = Jade_8;
    public static final Card Jade_9 = Card.of(CardNumbers.Nine, CardSuit.Jade);
    public static final Card G9 = Jade_9;
    public static final Card Jade_10 = Card.of(CardNumbers.Ten, CardSuit.Jade);
    public static final Card G0 = Jade_10;
    public static final Card G10 = Jade_10;
    public static final Card Jade_Jack = Card.of(CardNumbers.Jack, CardSuit.Jade);
    public static final Card GJ = Jade_Jack;
    public static final Card Jade_Queen = Card.of(CardNumbers.Queen, CardSuit.Jade);
    public static final Card GQ = Jade_Queen;
    public static final Card Jade_King = Card.of(CardNumbers.King, CardSuit.Jade);
    public static final Card GK = Jade_King;
    public static final Card Jade_Ace = Card.of(CardNumbers.Ace, CardSuit.Jade);
    public static final Card GA = Jade_Ace;
    public static final Card Sword_2 = Card.of(CardNumbers.Two, CardSuit.Sword);
    public static final Card K2 = Sword_2;
    public static final Card Sword_3 = Card.of(CardNumbers.Three, CardSuit.Sword);
    public static final Card K3 = Sword_3;
    public static final Card Sword_4 = Card.of(CardNumbers.Four, CardSuit.Sword);
    public static final Card K4 = Sword_4;
    public static final Card Sword_5 = Card.of(CardNumbers.Five, CardSuit.Sword);
    public static final Card K5 = Sword_5;
    public static final Card Sword_6 = Card.of(CardNumbers.Six, CardSuit.Sword);
    public static final Card K6 = Sword_6;
    public static final Card Sword_7 = Card.of(CardNumbers.Seven, CardSuit.Sword);
    public static final Card K7 = Sword_7;
    public static final Card Sword_8 = Card.of(CardNumbers.Eight, CardSuit.Sword);
    public static final Card K8 = Sword_8;
    public static final Card Sword_9 = Card.of(CardNumbers.Nine, CardSuit.Sword);
    public static final Card K9 = Sword_9;
    public static final Card Sword_10 = Card.of(CardNumbers.Ten, CardSuit.Sword);
    public static final Card K0 = Sword_10;
    public static final Card K10 = Sword_10;
    public static final Card Sword_Jack = Card.of(CardNumbers.Jack, CardSuit.Sword);
    public static final Card KJ = Sword_Jack;
    public static final Card Sword_Queen = Card.of(CardNumbers.Queen, CardSuit.Sword);
    public static final Card KQ = Sword_Queen;
    public static final Card Sword_King = Card.of(CardNumbers.King, CardSuit.Sword);
    public static final Card KK = Sword_King;
    public static final Card Sword_Ace = Card.of(CardNumbers.Ace, CardSuit.Sword);
    public static final Card KA = Sword_Ace;
    public static final Card Star_2 = Card.of(CardNumbers.Two, CardSuit.Star);
    public static final Card R2 = Star_2;
    public static final Card Star_3 = Card.of(CardNumbers.Three, CardSuit.Star);
    public static final Card R3 = Star_3;
    public static final Card Star_4 = Card.of(CardNumbers.Four, CardSuit.Star);
    public static final Card R4 = Star_4;
    public static final Card Star_5 = Card.of(CardNumbers.Five, CardSuit.Star);
    public static final Card R5 = Star_5;
    public static final Card Star_6 = Card.of(CardNumbers.Six, CardSuit.Star);
    public static final Card R6 = Star_6;
    public static final Card Star_7 = Card.of(CardNumbers.Seven, CardSuit.Star);
    public static final Card R7 = Star_7;
    public static final Card Star_8 = Card.of(CardNumbers.Eight, CardSuit.Star);
    public static final Card R8 = Star_8;
    public static final Card Star_9 = Card.of(CardNumbers.Nine, CardSuit.Star);
    public static final Card R9 = Star_9;
    public static final Card Star_10 = Card.of(CardNumbers.Ten, CardSuit.Star);
    public static final Card R0 = Star_10;
    public static final Card R10 = Star_10;
    public static final Card Star_Jack = Card.of(CardNumbers.Jack, CardSuit.Star);
    public static final Card RJ = Star_Jack;
    public static final Card Star_Queen = Card.of(CardNumbers.Queen, CardSuit.Star);
    public static final Card RQ = Star_Queen;
    public static final Card Star_King = Card.of(CardNumbers.King, CardSuit.Star);
    public static final Card RK = Star_King;
    public static final Card Star_Ace = Card.of(CardNumbers.Ace, CardSuit.Star);
    public static final Card RA = Star_Ace;
    // ==== End CodeGen

    private static final Map<String, Card> namedCards = cacheNamedCards();

    private static Map<String, Card> cacheNamedCards() {
        try {
            final Map<String, Card> namedCards = new TreeMap<>(
                String.CASE_INSENSITIVE_ORDER
            );
            final Field[] fields = DeckConstants.class.getFields();
            for (final Field field : fields) {
                int mod = field.getModifiers();
                if (
                    Modifier.isStatic(mod) &&
                    Modifier.isPublic(mod) &&
                    Modifier.isFinal(mod)
                ) {
                    if (Card.class.equals(field.getType())) {
                        final String fieldName = field.getName();
                        namedCards.put(
                            fieldName.toUpperCase(),
                            (Card) field.get(null)
                        );
                        // we know constants starting with _ are special cards and their shortName() impls start with a *
                        if (fieldName.startsWith("_")) {
                            namedCards.put(
                                fieldName.replace('_', '*').toUpperCase(),
                                (Card) field.get(null)
                            );
                        }
                    }
                }
            }
            return namedCards;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                "Can't access field values of " +
                DeckConstants.class +
                ", is this system too secure?",
                e
            );
        }
    }
}
