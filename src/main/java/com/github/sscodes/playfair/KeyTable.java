package com.github.sscodes.playfair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a "key table" as used in the Playfair cipher
 */
public class KeyTable {

    /** The key table is a 5x5 grid  */
    private static final short TABLE_SIZE = 5;

    private static final char UPPERCASE_A = 'A';
    private static final char UPPERCASE_Z = 'Z';

    /**
     * Omit this character when generating the table, to accommodate the 25 character constraint imposed by the
     * 5x5 grid
     * */
    private static final char DEFAULT_OMIT_CHAR = 'J';

    /**
     * When consecutive duplicate characters are encountered in the plaintext, insert this character between them.
     * Similarly, append this character to the end of the plaintext if its length is an odd number.
     */
    private static final char DEFAULT_DUPLICATE_CHAR_SEPARATOR = 'X';

    /** The contents of this key table */
    private char[][] contents = new char[TABLE_SIZE][TABLE_SIZE];

    /**
     * Instantiate a new key table, using the provided passphrase.
     *
     * @param passphrase The desired passphrase to use when generating the key table
     */
    public KeyTable(String passphrase) {
        this(passphrase, DEFAULT_OMIT_CHAR);
    }

    /**
     * Instantiate a new key key table, using the provided passphrase and "omit character".
     *
     * @param passphrase The desired passphrase to use when generating the key table
     * @param omitChar The character to omit when generating the key table (typically 'J' or 'Q')
     */
    public KeyTable(String passphrase, char omitChar) {
        if(passphrase == null) {
            throw new IllegalArgumentException(("Passphrase cannot be null!"));
        }

        if(!isValidString(passphrase)) {
            throw new IllegalArgumentException("Invalid passphrase!  Passphrase must consist of all letters.");
        }

        passphrase = passphrase.toUpperCase();

        if(omitChar < UPPERCASE_A || omitChar > UPPERCASE_Z) {
            throw new IllegalArgumentException("omitChar must be in the range A-Z!");
        }

        // Initialize the key table
        Set<Character> usedChars = new HashSet<>();

        StringBuilder key = new StringBuilder(25);
        for(int i = 0; i < passphrase.length(); i++) {
            Character c = Character.valueOf(passphrase.charAt(i));

            if(Character.isSpaceChar(c) || c == omitChar) {
                continue;
            }

            if(!usedChars.contains(c)) {
                key.append(c);
                usedChars.add(c);
            }
        }

        if(key.length() < TABLE_SIZE * TABLE_SIZE) {
            for(char c = UPPERCASE_A; c <= UPPERCASE_Z; c++) {
                if(!usedChars.contains(c) && c != omitChar) {
                    key.append(c);
                }
            }
        }

        String k = key.toString();
        for(int row = 0; row < contents.length; row++) {
            for(int col = 0; col < contents[row].length; col++) {
                contents[row][col] = k.charAt((row * TABLE_SIZE) + col);
            }
        }
    }

    /**
     * Encrypt the cleartext using this key table.
     *
     * @param cleartext The text in which to encrypt
     * @return The ciphertext corresponding to the provided input
     */
    public String encrypt(String cleartext) {
        if(!isValidString(cleartext)) {
            throw new IllegalArgumentException("Unable to encrypt!  Only alphabetic and space characters are supported.");
        }

        cleartext = cleartext.toUpperCase();
        cleartext = cleartext.replaceAll(" " , "");

        StringBuilder sb = new StringBuilder();
        char prevChar = Character.MIN_VALUE;
        for(int i = 0; i < cleartext.length(); i++) {
            char c = cleartext.charAt(i);

            if(prevChar == c) {
                sb.append(DEFAULT_DUPLICATE_CHAR_SEPARATOR);
            }
            sb.append(c);
            prevChar = c;
        }

        if(sb.length() % 2 != 0) {
            sb.append(DEFAULT_DUPLICATE_CHAR_SEPARATOR);
        }

        List<Digram> digrams = new ArrayList<>();
        for(int i = 0; i < sb.length(); i = i+2) {
            Digram d = new Digram(sb.charAt(i), sb.charAt(i+1));
            digrams.add(d);
        }

        StringBuilder encrypted = new StringBuilder();
        for(Digram d : digrams) {
            encrypted.append(transform(d, true));
        }

        return encrypted.toString();
    }

    /**
     * Decrypt the ciphertext using this key table.
     *
     * @param ciphertext The ciphertext in which to decrypt
     * @return The cleartext corresponding to the provided input
     */
    public String decrypt(String ciphertext) {
        if(!isValidString(ciphertext)) {
            throw new IllegalArgumentException("Unable to decrypt!  Only alphabetic and space characters are supported.");
        }

        ciphertext = ciphertext.toUpperCase();
        ciphertext = ciphertext.replaceAll(" " , "");

        List<Digram> digrams = new ArrayList<>();
        for(int i = 0; i < ciphertext.length(); i = i+2) {
            Digram d = new Digram(ciphertext.charAt(i), ciphertext.charAt(i+1));
            digrams.add(d);
        }

        StringBuilder decryptedDigrams = new StringBuilder();
        for(Digram d : digrams) {
            decryptedDigrams.append(transform(d, false));
        }

        String decrypted = decryptedDigrams.toString();

        if(decrypted.charAt(decrypted.length() - 1) == DEFAULT_DUPLICATE_CHAR_SEPARATOR) {
            decrypted = decrypted.substring(0, decrypted.lastIndexOf(DEFAULT_DUPLICATE_CHAR_SEPARATOR));
        }

        if(decrypted.indexOf(DEFAULT_DUPLICATE_CHAR_SEPARATOR) != -1) {
            char currentChar;
            for(int i = 1; i < decrypted.length() - 1; i++) {
                currentChar = decrypted.charAt(i);
                if(currentChar == DEFAULT_DUPLICATE_CHAR_SEPARATOR) {
                    char prevChar = decrypted.charAt(i - 1);
                    char nextChar = decrypted.charAt(i + 1);
                    if (prevChar == nextChar) {
                        decrypted = decrypted.replaceAll(new String(new char[]{prevChar, currentChar, nextChar}),
                                new String(new char[]{prevChar, nextChar}));
                    }
                }
            }
        }

        return decrypted;
    }

    /**
     * Transforms a digram per the rules of the Playfair cipher
     *
     * @param input The digram in which to transform
     * @param encrypt True if the digram is to be encrypted, false otherwise
     * @return The transformed digram (i.e. encrypted or decrypted)
     */
    private Digram transform(Digram input, boolean encrypt) {
        char firstChar = input.getFirstLetter();
        char secondChar = input.getSecondLetter();

        boolean foundFirstChar = false;
        int firstCharX = -1, firstCharY = -1;

        boolean foundSecondChar = false;
        int secondCharX = -1, secondCharY = -1;

        // Locate the letters in the key table...
        lettersearch:
        for(int y = 0; y < contents.length; y++) {
            for (int x = 0; x < contents[y].length; x++) {
                if(contents[y][x] == firstChar) {
                    firstCharX = x;
                    firstCharY = y;
                    foundFirstChar =true;
                }
                if(contents[y][x] == secondChar) {
                    secondCharX = x;
                    secondCharY = y;
                    foundSecondChar = true;
                }
                if(foundFirstChar && foundSecondChar) {
                    break lettersearch;
                }
            }
        }

        KeyRelation relation = null;
        if(firstCharX == secondCharX) {
            relation = KeyRelation.SAME_COLUMN;
        } else if(firstCharY == secondCharY) {
            relation = KeyRelation.SAME_ROW;
        } else {
            relation = KeyRelation.NEITHER_SAME_ROW_NOR_SAME_COLUMN;
        }

        return transform(relation, firstCharX, firstCharY, secondCharX, secondCharY, encrypt);

    }

    /**
     * Performs a transformation against this key table given a <code>KeyRelation</code> and a set of coordinates.
     *
     * @param relation Specifies the type of translation to be performed based on the relationship of a digram's letters
     * @param x1 The "x" coordinate of the first letter of a digram
     * @param y1 The "y" coordinate of the first letter of a digram
     * @param x2 The "x" coordinate of the second letter of a digram
     * @param y2 The "y" coordinate of the second letter of a digram
     * @param encrypt True if decryption is desired, false otherwise
     * @return A digram resulting from the transformation
     *
     * @see #transform(Digram, boolean)
     */
    private Digram transform(KeyRelation relation, int x1, int y1, int x2, int y2, boolean encrypt) {
        Digram transformed = null;
        switch(relation) {
            case SAME_ROW:
                int newX1, newX2;
                if(encrypt) {
                    newX1 = ++x1;
                    newX2 = ++x2;
                } else {
                    newX1 = --x1;
                    newX2 = --x2;
                }

                if(newX1 == TABLE_SIZE) {
                    newX1 = 0;
                }
                if(newX2 == TABLE_SIZE) {
                    newX2 = 0;
                }

                if(newX1 < 0) {
                    newX1 = TABLE_SIZE - 1;
                }
                if(newX2 < 0) {
                    newX2 = TABLE_SIZE - 1;
                }

                transformed = new Digram(contents[y1][newX1], contents[y2][newX2]);
                break;
            case SAME_COLUMN:
                int newY1, newY2;
                if(encrypt) {
                    newY1 = ++y1;
                    newY2 = ++y2;
                } else {
                    newY1 = --y1;
                    newY2 = --y2;
                }

                if(newY1 == TABLE_SIZE) {
                    newY1 = 0;
                }
                if(newY2 == TABLE_SIZE) {
                    newY2 = 0;
                }

                if(newY1 < 0) {
                    newY1 = TABLE_SIZE - 1;
                }
                if(newY2 < 0) {
                    newY2 = TABLE_SIZE - 1;
                }

                transformed = new Digram(contents[newY1][x1], contents[newY2][x2]);
                break;
            case NEITHER_SAME_ROW_NOR_SAME_COLUMN:
                transformed = new Digram(contents[y1][x2], contents[y2][x1]);
                break;
        }

        return transformed;
    }

    /**
     * Test a string to see if it is compatible/valid for the Playfair cipher.
     * A String is considered valid if it comprises of any combination of
     * alphabetic and space characters.
     *
     * @param str The string to test
     * @return True if <code>str</code> is valid for the Playfair cipher, false otherwise
     */
    private boolean isValidString(String str) {
        if(str == null) {
            return false;
        }

        for(int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            if(!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < contents.length; i++) {
            for(int j = 0; j < contents[i].length; j++) {
                sb.append(" ");
                sb.append(contents[i][j]);
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
