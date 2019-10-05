package com.github.sscodes.playfair;

/**
 * Describes the nature of the relationship 2 (or more) letters
 * in a key table.  Specifically, if the letters:
 * <ul>
 *     <li>are present in the same row (<code>SAME_ROW</code></code></li>
 *     <li>are present in the same column (<code>SAME_COLUMN</code></code></li>
 *     <li>are neither present in the same row nor the same column (<code>NEITHER_SAME_ROW_NOR_SAME_COLUMN</code></code></li>
 * </ul>
 */
public enum KeyRelation {
    SAME_ROW,
    SAME_COLUMN,
    NEITHER_SAME_ROW_NOR_SAME_COLUMN
}
