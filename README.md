[![Build Status](https://travis-ci.com/brunoccc/sudoku.svg?branch=master)](https://travis-ci.com/brunoccc/sudoku)

# YET ANOTHER SUDOKU SOLVER

Yes! How exciting? 

Well, this is ~~probably~~ not the most efficient algorithm out there, but nevertheless I thought it could be interesting to share it as an open source project.

### HOW DOES IT WORK

It's basically a simple recursive algorithm that:

* Finds the empty cell with less possible alternatives
* Writes the first possible value
* Calls itself and sees if it solves the puzzle
* If not, tries with the next possible value
* If no more values, then it's an impossible task

Compared to a normal _brute-force_ algorithm (which tries all the possible combinations until a solution has been found), this code performs better because of two reasons:

* It tracks the possible alternatives for each cell (see below how)
* It always selects the cell with less possible alternatives. Ideally, if the Sudoku has been designed for a human solver, this means that some cells will have a very limited number of possibilities, and the backtracking will not be used often

I think that the tracking of which values are possible is quite interesting. Essentially it keeps a bitmask for each row, column, or block. Each bit set to 1 represents a possible value.  
When we write a value in a cell, we set to 0 the corresponding bit for the bitmasks of that row, that column and that block (i.e. "this value is no longer available").  
When we need to know the possible values for a cell, we simply combine the bitmasks of that row, column and block using a logical `AND`.

<TODO: I promise I will add some pictures>

### FILES

* `Main.java` is an example of how to use the solver
* `Sudoku.java` is the main class for creating the Sudoku and solving it
* `BitCandidates.java` is the helper class used by the solver for keeping track of the possible values

### PERFORMANCES

I tried some Sudokus from magazines or stuff found online, and it usually takes 1 or 2 milliseconds to solve them on a Macbook pro.

On Wikipedia there is a Sudoku designed to work against brute force algorithms, and it's solved in about 40ms. See:
https://en.wikipedia.org/wiki/Sudoku_solving_algorithms 

### HOW TO USE IT

In case you use Microsoft Visual Studio Code, you will find the configuration file in the repository, so it should be easy to simply use the integrated debugger.

Alternatively, you can use `gradle`:

* `gradle tests` Run tests
* `gradle run` Run the example app

Or it can be run from the command line with something like:

```
  cd src
  javac ./com/brunoccc/examples/Main.java
  java com.brunoccc.examples.Main
```

Anyway, please note that the Main file provided is just an example, and this code is meant to be used as a library or as a part of a more complex program.

### TO DO LIST

- [ ] Improve tests. I know, I should have written them before, but this code has been written in a sleepless night... 
- [ ] Add more examples, or ability to read a Sudoku from file
- [ ] Check if the puzzle allows multiple solutions and return this information (could be useful for creating Sudokus)
- [ ] Return an indicator of the complexity of the Sudoku for a human solver (e.g. EASY/MEDIUM/HARD or any other scale)
- [ ] Extend it for Sudokus with different sizes (hence removing all those 9s and 3s in the code :-/ ) 
- [ ] Add pictures to the readme
- [ ] Add more todos to this list


