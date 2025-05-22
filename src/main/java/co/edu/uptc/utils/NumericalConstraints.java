package co.edu.uptc.utils;

public enum NumericalConstraints {
    // solo tiene una baraja
    DECK_SIZE(1.0),

    CARD_HEIGTH(0.2712),
    CARD_WIDTH(0.108),

    DECK_X(0.015),
    DECK_Y(0.09),

    FP_X(0.0703),
    FP_Y(0.53),

    SP_X(0.370),
    SP_Y(0.62),

    TP_X(0.6770),

    DCARD_X(0.1744),
    DCARD_Y(0.20);

    private final double value;

    NumericalConstraints(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
