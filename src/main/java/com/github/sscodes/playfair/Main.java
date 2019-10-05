package com.github.sscodes.playfair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Driver class to illustrate the <a href="https://en.wikipedia.org/wiki/Playfair_cipher">Playfair cipher</a>.
 */
public class Main {

    /** Defines the maximum number of characters per "word" when outputting the result of encryption/decryption */
    private static final int MAX_CHARS_PER_WORD = 4;

    /**
     * The entry point of the application
     *
     * @param args Command line arguments, if present
     * @throws IOException In the event of an unexpected I/O error
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Do you wish to encrypt [e] or decrypt [d] ? ");
        String action = br.readLine();

        while(!action.equalsIgnoreCase("e") &&
                !action.equalsIgnoreCase("encrypt") &&
                !action.equalsIgnoreCase("d") &&
                !action.equalsIgnoreCase("decrypt")) {
            System.out.print("Invalid response, \"" + action + "\".  Do you wish to encrypt [e] or decrypt [d] ? ");
            action = br.readLine();
        }

        boolean encrypt = "e".equalsIgnoreCase(action) || "encrypt".equalsIgnoreCase(action);

        System.out.print("Enter the " + (encrypt ? "plain" : "cipher") + "text : ");
        String txt = br.readLine();

        System.out.print("Enter the key : ");
        String key = br.readLine();

        KeyTable kt = new KeyTable(key);
        System.out.println(kt);

        if(encrypt) {
            String encrypted = kt.encrypt(txt);
            System.out.println("Encrypted : " + splitText(encrypted));
        } else {
            String decrypted = kt.decrypt(txt);
            System.out.println("Decrypted : " + splitText(decrypted));
        }
    }

    /**
     * Split the text into "words" by inserting a space after every nth character.
     *
     * @param text The text to split
     * @return The text, with spaces inserted after every nth character
     * @see #MAX_CHARS_PER_WORD
     */
    private static String splitText(String text) {
        StringBuilder split = new StringBuilder();

        for(int i = 0; i < text.length(); i++) {
            if(i % (MAX_CHARS_PER_WORD) == 0) {
                split.append(" ");
            }
            split.append(text.charAt(i));
        }

        return split.toString();
    }
}
