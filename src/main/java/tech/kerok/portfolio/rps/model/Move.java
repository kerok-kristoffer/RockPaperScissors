package tech.kerok.portfolio.rps.model;

public enum Move {
    Rock(0),
    Paper(1),
    Scissors(2);

    private final int value;

    Move(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
