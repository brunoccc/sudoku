package com.brunoccc.sudoku;

/**
 * A class for keeping track of the candidate values for each cell of the sudoku grid.
 * Exactly as many human players, the code tries to keep track of all possible values for a cell.
 * Therefore, every time a number is written into a cell, that value is no longer available for
 * other cells in the same row, column or block.
 * 
 * For better performances, we use bitmasks, where each bit represents a possible 1 to 9 value.
 * We have 3 bitmasks representing the values available for each row, column and block. 
 * When we write a value in a cell, we clear the corresponding bit in each one of these masks.
 * When we want to know which values are available for a specific cell, we use the logical AND
 * on the three masks.
 */
class BitCandidates {
    
    private static final int ALL_BITS = 0x1FF; // 9 bits set to 1
    
    private int colsMask[];

    private int rowsMask[];

    private int blocksMask[][];
    
    public BitCandidates() {
        colsMask = new int[9];
        rowsMask = new int[9];
        blocksMask = new int[3][3];
        
        for (int i = 0; i < 9; i++) {
            colsMask[i] = ALL_BITS;
            rowsMask[i] = ALL_BITS;
            blocksMask[i/3][i%3] = ALL_BITS;
        }
    }
    
    /** 
     * Marks a value as used for that row, that col and that block
     */
    public void useVal(int row, int col, int val) {
        final int bit = ~(1 << (val - 1));
        colsMask[col] = colsMask[col] & bit;
        rowsMask[row] = rowsMask[row] & bit;
        blocksMask[row/3][col/3] = blocksMask[row/3][col/3] & bit;
    }

    /** 
     * Sets a value as available again for that row, that col and that block
     */
    public void clearVal(int row, int col, int val) {
        final int bit = (1 << (val - 1));
        colsMask[col] = colsMask[col] | bit;
        rowsMask[row] = rowsMask[row] | bit;
        blocksMask[row/3][col/3] = blocksMask[row/3][col/3] | bit;
    }

    /**
     *  Returns the bitmask for the valid values for that cell
     */
    public int getMask(int row, int col) {
        final int mask = rowsMask[row] & colsMask[col] & blocksMask[row/3][col/3];
        return mask;
    }
}
