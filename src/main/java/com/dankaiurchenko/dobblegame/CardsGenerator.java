package com.dankaiurchenko.dobblegame;

import java.util.HashSet;
import java.util.Set;

public class CardsGenerator {

    private final int N;
    private final int NUMBER_OF_ICONS_ON_A_CARD;
    private final int NUMBER_OF_CARDS;

    public CardsGenerator(int n) {
        if (!isPrime(n)) {
            throw new IllegalArgumentException("N should be prime! 2,3,5,7,11.....");
        }
        N = n;
        NUMBER_OF_ICONS_ON_A_CARD = n + 1;
        NUMBER_OF_CARDS = n ^ 2 + n + 1;
    }

    public Set<Card> generate() {
        Set<Card> cards = new HashSet<>(NUMBER_OF_CARDS);

        // first card
        Card card = newCard();
        for (int i = 0; i <= N; i++) {
            card.add(i);
        }
        cards.add(card);

        // N following cards
        for (int j = 0; j < N; j++) {
            card = newCard();
            card.add(0);
            for (int k = 0; k < N; k++) {
                card.add(N + 1 + N * j + k);
            }
            cards.add(card);
        }

        // N*N following cards
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                card = newCard();
                card.add(i + 1);
                for (int k = 0; k < N; k++) {
                    card.add(N + 1 + N * k + (i * k + j) % N); // Good for N = prime number
                }
                cards.add(card);
            }
        }
        return cards;
    }

    private Card newCard() {
        return new Card(NUMBER_OF_ICONS_ON_A_CARD);
    }


    public static boolean isPrime(int n) {
        boolean prime = true;
        for (int i = 2; i <= n / 2; ++i) {
            if (n % i == 0) {
                prime = false;
                break;
            }
        }
        return prime;
    }
}

