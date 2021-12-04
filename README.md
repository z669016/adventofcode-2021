# Advent of code 2021

## Convenience methods and classes
I have a small library with some convenience methods used for other AoC exercises. Like the ```ResourceLines``` class 
to read a resource file and transform the content into a ```List<String>```, or the CSV class to read a resource 
file containing comma separated values and returning a List of these values, optionally after transformation from 
```String``` to ```Integer```.

Also uses the algorithm library, which contains generic classes for addressing classic compute problems (from the book 
**Classic Computer Science Problems In Java** (c) Manning.com - 2020) 

It was never my intention to create the shortest program possible. I did try to create clear and simple implementations.

## Day 1
A simple exercise to start this year's edition. Part 1 is trivial. A helper method just counts the number of increases 
in the list in values. For part 2, I created a new list frm the original one (creating the sliding window values) and
reused the helper for counting. Runs in less than 15 ms on my laptop. 

## Day 2
I've created a ```Submarine``` class, with a ```move``` method taking a ```Command``` as an input. The command has
a direction and a distance attribute. This approach made it easy to process the input. For part 1 the input is 
transformed into commands and the commands were used to move the submarine.
For part 2, I extended the ```Submarine``` class and changed the ```move``` method according to instructions, then 
processed the command list again... pretty straight forward.

## Day 3
Solved the puzzles using counting and filtering streams (just used ```List<String>``` and ```charAt()```).

## Day 4
Started with creating a ```BingoCard``` class on which I could ```call``` numbers and which would keep track of marked 
numbers. The ```call``` method would return true if a row of column would be completely marked. The board could also
calculate it's ```score```.
Next I created a ```Bingo``` class which would play a bingo game on a list of cards given a list of numbers to call. 
The ```play``` method would return the first card which returns true on the called number. Part 1 will simply print 
the score of that first card. For part 2, I added a ```playForLast```method that removes each completed cards from the 
list and returns only the last card that completes.

