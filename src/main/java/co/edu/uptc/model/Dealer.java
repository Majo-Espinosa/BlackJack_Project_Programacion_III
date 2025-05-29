package co.edu.uptc.model;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Dealer {
    private Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public int getHandValue() {
        return hand.getValue();
    }

    public boolean isBusted() {
        return hand.isBusted();
    }

    public void clearHand() {
        hand.clear();
    }

    public Hand getHand() {
        return hand;
    }

    //Obtener la lista de cartas y su visibilidad
    public Map<String, Object> toMap(boolean showAllCards) {
        if (showAllCards) {
            // Mostrar todas las cartas (al final del juego o durante el turno del dealer)
            return Map.of(
                "hand", hand.toMap(),
                "isBusted", isBusted()
            );
        } else {
            // Durante el juego, mostrar solo la primera carta
            List<Card> allCards = hand.getCards();

            if (allCards.isEmpty()) {
                // No hay cartas
                return Map.of(
                    "hand", Map.of(
                        "cards", List.of(),
                        "value", 0,
                        "isBlackjack", false,
                        "isBusted", false
                    ),
                    "isBusted", false
                );
            }
            // Crear lista de cartas visibles (primera carta + carta oculta)
            List<String> visibleCards = new ArrayList<>();
            visibleCards.add(allCards.get(0).toString()); // Primera carta visible
            if (allCards.size() > 1) {
                visibleCards.add("CARTA OCULTA"); // Segunda carta oculta
            }
            // Solo mostrar el valor de la primera carta
            int visibleValue = allCards.get(0).getValue();
            return Map.of(
                "hand", Map.of(
                    "cards", visibleCards,
                    "value", visibleValue,
                    "isBlackjack", false, // No mostrar blackjack durante el juego
                    "isBusted", false
                ),
                "isBusted", false
            );
        }
    }
}