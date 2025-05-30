package co.edu.uptc.server.model;
//
//
//import co.edu.uptc.enums.Rank;
//import co.edu.uptc.enums.Suit;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//
//public class Deck {
//   private List<Card> cards;
//   private final Random random;
//
//   public Deck() {
//       this.random = new Random();
//       initializeDeck();
//       shuffle();
//   }
//
//   private void initializeDeck() {
//       cards = new ArrayList<>();
//       for (Suit suit : Suit.values()) {
//           for (Rank rank : Rank.values()) {
//               cards.add(new Card(suit, rank));
//           }
//       }
//   }
//
//   public void shuffle() {
//       Collections.shuffle(cards, random);
//   }
//
//   public Card dealCard() {
//       if (cards.isEmpty()) {
//           initializeDeck();
//           shuffle();
//       }
//       return cards.removeLast();
//   }
//
//   public int remainingCards() {
//       return cards.size();
//   }
//}