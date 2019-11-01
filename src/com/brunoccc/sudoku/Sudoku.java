package com.brunoccc.sudoku;

/**
 * The class for solving a Sudoku puzzle.
 * It is a pretty simple recursive algorithm, but more efficient than a dumb brute-force
 * iteration because it uses an efficient way of keeping track of the possible values 
 * for each cell.
 * 
 * NOTE: Although stricly speaking a sudoku only admits one solution, this class can of course
 * be used for grids that may allow multiple solutions (e.g. in a software which is trying 
 * to create sudokus).
 * However, this class doesn't do any check in this sense, and it simply returns the first
 * solution found. Being a stable algorithm, moreover, it will always return the same solution.
 */
public class Sudoku {

    private static class RowCol {
        int row;
        int col;
        public RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }
    } 

    // The grid itself. Note that this algorithm alters the original grid.
    private int[][] mGrid;

    // The data structure that keeps track of the possible values for each cell
    private BitCandidates mBitCandidates;

    /**
     * The constructor of a sudoku. Note that the grid passed as input will be changed during
     * the iterations, and will eventually contain the solution.
     */
    public Sudoku(int[][] grid) {
        mGrid = grid;
        initCandidates();
    }

    // Initialise the candidates data structure by adding the numbers already on the grid
    private void initCandidates() {
        mBitCandidates = new BitCandidates();
        for (int row = 0; row < mGrid.length; row++) {
            for (int col = 0; col < mGrid[row].length; col++) {
                final int val;
                if ((val = mGrid[row][col]) != 0) {
                    mBitCandidates.useVal(row, col, val);
                } 
            }
        }
    }

    /**
     * The method that calculates the solution.
     * @returns true if a solution has been found. The grid passed in the constructor contains the solution itself.
     */
    public boolean solve() {
        return recursiveSolve(mGrid);
    }

    // The recursive routine that searches the solution
    private boolean recursiveSolve(int grid[][]) {
        RowCol rowCol;

        while ((rowCol = getTopmostCell(grid)) != null) {
            final int row = rowCol.row;
            final int col = rowCol.col;

            int mask = mBitCandidates.getMask(row, col);
            for (int val = 1; mask != 0 && val <= 9; val++) {
                if ((mask & (1 << val - 1)) != 0) {
                    mBitCandidates.useVal(row, col, val);
                    grid[row][col] = val;
                    if (recursiveSolve(grid)) {
                        // This value allowed the completion
                        return true;
                    }
                    // Not the good value, try another
                    grid[row][col] = 0;
                    mBitCandidates.clearVal(row, col, val);
                }
            }
            // No values found, unsolvable
            return false;
        }
        // No empty cells, we're done!
        return true;
    }

    // The idea is that we always get the cell with less alternatives.
    // Ideally, this should let us to quickly identify situations with no solution: 
    // if a cell admits only one value, for instance, it's better if we try that one
    // sooner than later.
    private RowCol getTopmostCell(int[][] grid) {
    
        int bestRow = -1;
        int bestCol = -1;
        int bestNumberOfOptions = Integer.MAX_VALUE;
    
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) {
                    final int mask = mBitCandidates.getMask(row, col);
                    final int numberOfOptions = countBits(mask);
                    if (numberOfOptions < bestNumberOfOptions) {
                        // A cell with fewer options
                        bestRow = row;
                        bestCol = col;
                        bestNumberOfOptions = numberOfOptions;
                    }
                }
            }
        }

        if (bestRow >= 0 && bestCol >= 0) {
            return new RowCol(bestRow, bestCol);
        }
    
        return null;
    }

    // Counts how many bits are set in val
    private int countBits(int val) {
        int count = 0;
        while (val != 0) {
            if ((val & 1) != 0) {
                count++;
            }
            val = val >> 1;
        }
        return count;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        for (int row = 0; row < mGrid.length; row++) {
            if (row > 0 && row % 3 == 0) {
                builder.append("------+-------+------\n");
            }
            for (int col = 0; col < mGrid[row].length; col++) {
                if (col > 0 && col % 3 == 0) {
                    builder.append("| ");
                }
                final int cell = mGrid[row][col];
                if (cell > 0) {
                    builder.append(cell);
                } else {
                    builder.append("."); 
                }
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
