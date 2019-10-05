package com.github.sscodes.playfair;

/**
 * A digram is a group of 2 letters.
 */
public class Digram {
    private char firstLetter;
    private char secondLetter;

    /**
     * Instantiates a new digram with the specifies letters
     *
     * @param firstLetter The first letter of this diagram
     * @param secondLetter The second letter of this diagram
     */
    public Digram(char firstLetter, char secondLetter) {
        this.firstLetter = firstLetter;
        this.secondLetter = secondLetter;
    }

    /**
     * Accessor method to retrieve the first letter of this digram
     *
     * @return The first letter corresponding to this digram
     */
    public char getFirstLetter() {
        return firstLetter;
    }

    /**
     * Accessor method to retrieve the second letter of this digram
     *
     * @return The second letter corresponding to this digram
     */
    public char getSecondLetter() {
        return secondLetter;
    }

    @Override
    public String toString() {
        return firstLetter + "" + secondLetter;
    }
}
