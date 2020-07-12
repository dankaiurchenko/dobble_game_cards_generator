package com.dankaiurchenko.dobblegame;

import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CardsGeneratorTest {

    @org.junit.jupiter.api.Test
    void generate() {
        Set<Card> cards = new CardsGenerator(7).generate();

        for (Card card : cards) {
            for (Card integers : cards) {
                if (!card.equals(integers)) {
                    Set<Integer> itemsInCommon = new HashSet<>(card.getIcons());
                    itemsInCommon.retainAll(integers.getIcons());
                    Assertions.assertEquals(1, itemsInCommon.size());
                }
            }
        }
    }

    @org.junit.jupiter.api.Test
    void isPrimeTest() {
        Assertions.assertTrue(CardsGenerator.isPrime(2));
        Assertions.assertTrue(CardsGenerator.isPrime(3));
        Assertions.assertTrue(CardsGenerator.isPrime(5));
        Assertions.assertTrue(CardsGenerator.isPrime(7));
        Assertions.assertTrue(CardsGenerator.isPrime(11));

        Assertions.assertFalse(CardsGenerator.isPrime(20));
        Assertions.assertFalse(CardsGenerator.isPrime(30));
        Assertions.assertFalse(CardsGenerator.isPrime(8));
    }

    @org.junit.jupiter.api.Test
    void getIconsPackTest() {
        Set<Card> cards = new CardsGenerator(3).generate();
        Set<String> iconsPack = new ImagesGenerator().getIconsPack(cards);
        Assertions.assertEquals(cards.size(), iconsPack.size());

        for (String s : iconsPack) {
            Assertions.assertTrue(s.endsWith(".svg"));
        }
    }

    @org.junit.jupiter.api.Test
    void getTemplatesTest() {
        List<File> templates = new ImagesGenerator().getTemplates();
        Assertions.assertEquals(1, templates.size());

        Assertions.assertTrue(templates.get(0).getName().startsWith("template"));
        Assertions.assertTrue(templates.get(0).getName().endsWith(".svg"));
    }

    @org.junit.jupiter.api.Test
    void generateCardTest() throws IOException {
        Set<Card> deckOfDobbleCards = new CardsGenerator(3).generate();
        new ImagesGenerator().generate("/home/bohdanayurchecko/Projects/dobble_game_generator/src/main/resources/generated/", deckOfDobbleCards);
    }

    @org.junit.jupiter.api.Test
    void composeDocumentTest() throws IOException {
        Set<Card> deckOfDobbleCards = new CardsGenerator(3).generate();
        new ImagesGenerator().generate("/home/bohdanayurchecko/Projects/dobble_game_generator/src/main/resources/generated/", deckOfDobbleCards);
        new DocumentComposer().compose("/home/bohdanayurchecko/Projects/dobble_game_generator/src/main/resources/generated/", ".+png", "document.docx");
    }

}