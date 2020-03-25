package net.incongru.tichu.model.util;

import net.incongru.tichu.model.Card;
import net.incongru.tichu.model.CardDeck;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DeckConstantsCodeGen {

    // Generates code for DeckConstants
    public static void main(String[] args) throws IOException {
        // CardDeck shuffles, so we force order the cards here.
        final Set<Card> cards = new TreeSet<>(Card.Comparators.BY_SUIT);
        final CardDeck deck = new CardDeck();
        cards.addAll(deck.allRemaining());

        final String javaConstants = generateJavaConstants(cards);
        final Path srcPath = Path.of("tichu-model/src/main/java/net/incongru/tichu/model/util/DeckConstants.java");
        final String oldSrc = Files.readString(srcPath);
        System.out.println("Loaded " + srcPath + "(" + oldSrc.length() + " chars)");
        final String newSrc = oldSrc.replaceFirst("(?s)(?m)^\\s+?// ==== Start CodeGen(.*)// ==== End CodeGen\\s+^", javaConstants);
        final Path path = Files.writeString(srcPath, newSrc, StandardCharsets.UTF_8);
        System.out.println("Written " + srcPath + "(" + newSrc.length() + " chars)");

        final String typescriptConstants = generateTypescriptConstants(cards);
        System.out.println("typescriptConstants = " + typescriptConstants);
    }

    private static String generateJavaConstants(Set<Card> cards) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter out = new PrintWriter(stringWriter);
        out.println();
        out.println("    // ==== Start CodeGen -- this was generated by " + DeckConstantsCodeGen.class + " on " + Instant.now());
        for (Card card : cards) {
            final String prefix = "    public static final Card ";
            final String constName;
            final String constVal;
            if (card.getVal().isSpecial()) {
                constName = card.getVal().niceName();
                constVal = " = new Card(Card.CardSpecials." + card.getVal() + ");";
            } else {
                constName = card.getSuit() + "_" + card.getVal().niceName();
                constVal = " = new Card(Card.CardNumbers." + card.getVal() + ", Card.CardSuit." + card.getSuit() + ");";
            }
            out.println(prefix + constName + constVal);
            out.println(prefix + card.shortName().replace('*', '_') + " = " + constName + ";");

            // "10" cards have a 2-char shortnames like others, but we also want a nice constant
            if (card.getVal().shortName() == '0') {
                out.println(prefix + card.shortName().replace("0", "10") + " = " + constName + ";");
            }

            // alt. name for Dog
            if (card.getVal() == Card.CardSpecials.Dog) {
                out.println(prefix + "Hound = Dog;");
            }
        }
        out.println("    // ==== End CodeGen");
        out.println();

        return stringWriter.toString();
    }

    private static String generateTypescriptConstants(Set<Card> cards) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter out = new PrintWriter(stringWriter);
        out.println("export const Cards: Array<Card> = [");
        out.println(
                cards.stream().map(card -> {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("  { ")
                            .append("name: \"").append(card.name()).append("\", ")
                            .append("shortName: \"").append(card.shortName()).append("\", ")
                            .append("scoreValue: ").append(card.getVal().scoreValue()).append(", ");
                    final boolean special = card.getVal().isSpecial();
                    if (special) {
                        sb.append("special: SpecialCards." + card.name());
                    } else {
                        sb.append("suit: CardSuit.").append(card.getSuit().name()).append(", ");
                        sb.append("number: ").append(card.getVal().playOrder());
                    }
                    sb.append(" } as ").append(special ? "SpecialCard" : "NormalCard");
                    return sb.toString();
                }).collect(Collectors.joining(",\n"))
        );
        out.println("];");

        return stringWriter.toString();
    }
}
