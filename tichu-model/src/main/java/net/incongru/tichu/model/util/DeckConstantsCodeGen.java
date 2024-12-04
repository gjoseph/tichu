package net.incongru.tichu.model.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Set;
import java.util.TreeSet;
import net.incongru.tichu.model.CardDeck;
import net.incongru.tichu.model.card.Card;
import net.incongru.tichu.model.card.CardComparators;
import net.incongru.tichu.model.card.CardSpecials;

public class DeckConstantsCodeGen {

    // Generates code for DeckConstants
    public static void main(String[] args) throws IOException {
        // CardDeck shuffles, so we force order the cards here.
        final Set<Card> cards = new TreeSet<>(CardComparators.BY_SUIT);
        final CardDeck deck = new CardDeck();
        cards.addAll(deck.allRemaining());

        replaceCodeGenSectionInFile(
            Path.of(
                "tichu-model/src/main/java/net/incongru/tichu/model/util/DeckConstants.java"
            ),
            generateJavaConstants(cards)
        );

        replaceCodeGenSectionInFile(
            Path.of("tichu-clients/packages/tichu-client-ts-lib/src/cards.ts"),
            generateTypescriptConstants(cards)
        );
    }

    private static void replaceCodeGenSectionInFile(
        Path srcPath,
        String newSourceCode
    ) throws IOException {
        final String oldSourceCode = Files.readString(srcPath);
        System.out.println(
            "Loaded " + srcPath + "(" + oldSourceCode.length() + " chars)"
        );
        final String newSrc = oldSourceCode.replaceFirst(
            "(?s)(?m)// ==== Start CodeGen(.*)// ==== End CodeGen",
            newSourceCode.trim()
        );
        Files.writeString(srcPath, newSrc, StandardCharsets.UTF_8);
        System.out.println(
            "Written " + srcPath + "(" + newSrc.length() + " chars)"
        );
    }

    private static String generateJavaConstants(Set<Card> cards) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter out = new PrintWriter(stringWriter);
        out.println();
        out.println(
            "    // ==== Start CodeGen -- this was generated by " +
            DeckConstantsCodeGen.class +
            " on " +
            Instant.now()
        );
        for (Card card : cards) {
            final String prefix = "    public static final Card ";
            final String constName;
            final String constVal;
            if (card.val().isSpecial()) {
                constName = card.val().niceName();
                constVal = " = Card.of(CardSpecials." + card.val() + ");";
            } else {
                constName = card.suit() + "_" + card.val().niceName();
                constVal =
                    " = Card.of(CardNumbers." +
                    card.val() +
                    ", CardSuit." +
                    card.suit() +
                    ");";
            }
            out.println(prefix + constName + constVal);
            out.println(
                prefix +
                card.shortName().replace('*', '_') +
                " = " +
                constName +
                ";"
            );

            // "10" cards have a 2-char shortnames like others, but we also want a nice constant
            if (card.val().shortName() == '0') {
                out.println(
                    prefix +
                    card.shortName().replace("0", "10") +
                    " = " +
                    constName +
                    ";"
                );
            }

            // alt. name for Dog
            if (card.val() == CardSpecials.Dog) {
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
        out.println(
            "// ==== Start CodeGen -- this was generated by " +
            DeckConstantsCodeGen.class +
            " on " +
            Instant.now()
        );
        out.println("export const AllCards: Array<Card> = [");
        cards.forEach(card -> {
            final boolean special = card.val().isSpecial();
            out
                .append("  {\n")
                .append("    name: \"")
                .append(card.name())
                .append("\",\n")
                .append("    shortName: \"")
                .append(card.shortName())
                .append("\",\n")
                .append("    scoreValue: ")
                .append(String.valueOf(card.val().scoreValue()))
                .append(",\n");
            if (special) {
                out
                    .append("    special: SpecialCards." + card.name())
                    .append(",\n");
            } else {
                out
                    .append("    suit: CardSuit.")
                    .append(card.suit().name())
                    .append(",\n");
                out
                    .append("    number: ")
                    .append(String.valueOf(card.val().playOrder()))
                    .append(",\n");
            }
            out
                .append("    type: \"")
                .append(special ? "special" : "normal")
                .append("\",\n");
            out.append("  },\n");
        });
        out.println("];");
        out.println("// ==== End CodeGen");

        return stringWriter.toString();
    }
}
