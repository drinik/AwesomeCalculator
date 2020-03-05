package com.github.drinik.calculator;

public enum CalculatorItem {
    CLEAR("CC"),
    PLUS_MINUS("+/-"),
    EMPTY_1(""),
    EMPTY_2(""),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    DIVISION("/"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    MULTIPLICATION("x"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    SUBTRACTION("-"),
    ZERO("0"),
    DECIMAL("."),
    EQUALS("="),
    ADDITION("+");

    final String value;

    CalculatorItem(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
