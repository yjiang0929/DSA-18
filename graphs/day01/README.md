# Graphs Day 01 - Recursive Backtracking

This homework is due on **Next Monday (April 2) at 10pm**.

# Resources

https://www.cs.utexas.edu/~scottm/cs314/handouts/slides/Topic13RecursiveBacktracking.pdf

# Learning Goals

Students should be able to:

- Understand how certain problems can be represented/solved using graph algorithms.
- Use depth-first-search and backtracking in order to solve these problems.

# Assignment

**Do (at least) 3 of the following problems**

## Permutations

Given a `List` of unique integers `A`, return all possible permutations of that list. For example, given `[1, 2, 3]`, return:

- [1, 2, 3]
- [1, 3, 2]
- [2, 1, 3]
- [2, 3, 1]
- [3, 1, 2]
- [3, 2, 1]

## N-Queens

Write a program that determines all possible configurations of N queens on an NxN chessboard such that no two of them attack each other. This means that no two queens can share a row, column, or diagonal. If you are confused regarding the rules of chess or how a queen can move, check out [this](https://www.chessusa.com/chess-rules.html) tutorial.

For example, here is one of the two 4-queens solutions:

![](https://developers.google.com/optimization/images/queens/sol_4x4_b.png)

Write your code in the `NQueens` class. Note that for the tests to pass, you will need to represent the chess-board as a `char[n][n]` - an NxN 2-D `char` array. Represent blank squares with a period: `'.'`, and queens with a capital `'Q'`.

<details>
  <summary>Hint 1</summary>
  There must be a queen in every row of the chess board. Start at the top row of the board, and try to assign a queen to each row. Pass along which row you are currently assigning as a variable in your recursive function.
</details>

<details>
  <summary>Hint 2</summary>
  Use a data structure with O(1) access to store which columns contain queens.
</details>

<details>
  <summary>Hint 3</summary>
  We have provided a function to check if a Queen is on a the same diagonal as an input location (read the function comment to understand what it does). You may find this function helpful when checking if a location is valid for a queen.
</details>

## Coins on a Clock

You have a bag of coins, and you can place them on each hour on a clock. For each coin you put down, you advance its own value clockwise before placing the next coin. So if you place nickel on 12 o'clock, and you want to put down a penny, you place it on 5 o'clock. The game is won if you find a sequence of coin placements, starting at the top of the clock, that results in each hour being covered.

Write a program that, given a number of pennies, nickels, and dimes, as well as the number of hours in a day (you could have a clock with `10` at the top of the clock, and `1`-`9` around it), outputs all solutions to the coins on a clock puzzle (return the clocks, represented by arrays where index `0` is 12 o'clock). You can assume the total number of coins and hours in a day are equal.

Represent a solved clock using a `char[]`, where `'p', 'n', 'd'` are the coins. The top of the clock is at index `0` in the array.

For example, given 4 pennies, 4 nickles, and 4 dimes, and 12 hours in a day, one solution is:

```
Time of day:   12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
Output array: [p,  d, n, p, p, n, d, p, d, n, n,  d]
```

- You first place a penny at 12 o'clock, index 0 in the array.
- You must put a coin at 1 o'clock, so you place a dime at index 1.
- You now must place a coin at 11 o'clock (at index 11). You place another dime. (Note how placing a penny would cause you to lose, as there is already a coin at 12 o'clock)
- You are now at 9 o'clock. You place a nickel.
- At 2 o'clock, you place another nickel.
- Etc.

<details>
  <summary>Hint 1</summary>
  Keep track of how many of each coin you have remaining. If all are 0, then you have completed the puzzle.
</details>


<details>
  <summary>Hint 2</summary>
  Make one of your recursion variables the current location to place a coin (for example, call it `i`). Ie, for your first recursive call, `i=0`. If you then place a dime, in your next recursive call, `i=10`.
</details>

## Cryptarithmetic Puzzle

You may have seen puzzles in the form:

```
  SEND
+ MORE
-----
 MONEY
```

Your goal is to assign each character a digit `0`-`9` such that the addition works out correctly. You are given the two addends as `String`s, `S1` and `S2`, and the sum as the string `S3`. Return a `Map` in the form `{character -> value}`.

You'll note that assigning every character to `0` will solve any puzzle, so we'll add the constraint that leading characters cannot be `0`s. One (boring) solution to this problem is:

```
  9000
+ 1000
------
 10000
```

A more interesting solution is:

```
  9902
+ 1189
------
 11091
```

We've provided a function that will allow you to make assignments in a random order, if you'd like your algorithm to come up with cool solutions (if you try 0, 1, 2, etc in order, you'll likely end up with the first solution). You can use the function like this:

```
for (int i: randomOrder()) {
    // i iterates the values 0-9, in a random order
    // some code here
}
```
<details>
  <summary>Hint 1</summary>
  Keep track of a `Set` of unassigned characters in your recursion.
</details>


### (Optional) Optimizations

Certain implementations of this algorithm may be really slow on longer test cases. You may be able to optimize your code by making assignments from right-to-left in the strings, and terminating early when you make an assignment that couldn't be correct. For example, in the `SEND + MORE = MONEY` case, if we assign `D` and `E` to `4`, and `Y` to `7`, we already know this assignment will be incorrect.

## Checkoffs
- Know the space and time complexity of your algorithms
- Find the coolest anagram for your name (if your name is longer than 6 characters, then you don't have to do it.)
