package com.brunoccc.examples;

import com.brunoccc.sudoku.Sudoku;

public class Main {

    // Wikipedia sudoku against brute force
    // See https://en.wikipedia.org/wiki/Sudoku_solving_algorithms
    private static int[][] SUDOKU_GRID = {
        { 0, 0, 0,   0, 0, 0,    0, 0, 0 },
        { 0, 0, 0,   0, 0, 3,    0, 8, 5 },
        { 0, 0, 1,   0, 2, 0,    0, 0, 0 },
    
        { 0, 0, 0,   5, 0, 7,    0, 0, 0 },
        { 0, 0, 4,   0, 0, 0,    1, 0, 0 },
        { 0, 9, 0,   0, 0, 0,    0, 0, 0 },
    
        { 5, 0, 0,   0, 0, 0,    0, 7, 3 },
        { 0, 0, 2,   0, 1, 0,    0, 0, 0 },
        { 0, 0, 0,   0, 4, 0,    0, 0, 9 }
    };

    public static void main(String[] args) {
        
        final Sudoku sudoku = new Sudoku(SUDOKU_GRID);
        
        System.out.println("Starting grid");
        System.out.println();
        System.out.print(sudoku);
        
        final long start = System.currentTimeMillis();
        final boolean solved = sudoku.solve();
        final long duration = System.currentTimeMillis() - start;

        System.out.println();
        if (solved) {
            System.out.println("Solved! Execution time: " + duration + "ms");
            System.out.println();
            System.out.print(sudoku);
        } else {
            System.out.println("Impossible! Execution time: " + duration + "ms");            
        }
    }
}