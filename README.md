# Advent of code 2021

## Convenience methods and classes
I have a small library with some convenience methods used for other AoC exercises. Like the ```ResourceLines``` class 
to read a resource file and transform the content into a ```List<String>```, or the CSV class to read a resource 
file containing comma separated values and returning a List of these values, optionally after transformation from 
```String``` to ```Integer```.

Also uses the algorithms library, which contains generic classes for addressing classic compute problems (from the book 
**Classic Computer Science Problems In Java** (c) Manning.com - 2020) 

It was never my intention to create the shortest program possible. I did try to create clear and simple implementations.

## Day 1
A simple exercise to start this year's edition. Part 1 is trivial. A helper method just counts the number of increases 
in the list in values. For part 2, I created a new list frm the original one (creating the sliding window values) and
reused the helper for counting. Runs in less than 15 ms on my laptop. 