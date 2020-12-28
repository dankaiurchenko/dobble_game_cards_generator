package com.dankaiurchenko.dobblegame;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
//        System.out.println(new CardsGenerator(7).toString());

        Set<Card> deckOfDobbleCards = new CardsGenerator(7).generate();
        System.out.println(deckOfDobbleCards);
        String resultingPath = "/home/bohdanayurchecko/Projects/dobble_game_generator/src/main/resources/generated/images/";
        new ImagesGenerator().generate(resultingPath,deckOfDobbleCards);
        new DocumentComposer().compose(resultingPath, ".+.png", "doc.docx");
    }
}
