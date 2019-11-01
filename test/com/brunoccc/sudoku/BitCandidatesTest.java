package com.brunoccc.sudoku;

import org.junit.*;
import static org.junit.Assert.*;
 
public class BitCandidatesTest {

    private static final int GRID_SIZE = 9;

    private BitCandidates candidates;
 
    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        candidates = new BitCandidates();
    }

    /** 
     * Helper for converting an array of possible values into a bitmask
     */
    private int convertToBits(int[] values)  {
        int ret = 0;

        for (int i = 0; i < values.length; i++) {
            ret = ret | (1 << (values[i] - 1));
        }

        return ret;
    }
 
    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        candidates = null;
    }
    
    @Test
    public void testInitialValues() {
        int expectedBits = convertToBits(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                assertEquals("Initial value for (" + row + ", " + col + ")", expectedBits, candidates.getMask(row, col));
            }
        }
    }

    @Test
    public void testUseValueInSameRow() {
        final int testRow = 2;
        final int testCol  = 3;
        candidates.useVal(testRow, 8, 5); // Uses number 5 in the last column of test row
        int expectedBits = convertToBits(new int[] {1, 2, 3, 4, /* 5, */ 6, 7, 8, 9});
        assertEquals("Value for (" + testRow + ", " + testCol + ")", expectedBits, candidates.getMask(testRow, testCol));
    }

    @Test
    public void testUseValueInSameCol() {
        final int testRow = 2;
        final int testCol  = 3;
        candidates.useVal(8, testCol, 3); // Uses number 3 in the last row of test column
        int expectedBits = convertToBits(new int[] {1, 2, /* 3, */ 4, 5, 6, 7, 8, 9});
        assertEquals("Value for (" + testRow + ", " + testCol + ")", expectedBits, candidates.getMask(testRow, testCol));
    }

    @Test
    public void testUseValueInSameBlock() {
        final int testRow = 2;
        final int testCol  = 3;
        candidates.useVal(0, 5, 7); // Uses number 7 in the opposite corner of the block
        int expectedBits = convertToBits(new int[] {1, 2, 3, 4, 5, 6, /* 7, */ 8, 9});
        assertEquals("Value for (" + testRow + ", " + testCol + ")", expectedBits, candidates.getMask(testRow, testCol));
    }
}