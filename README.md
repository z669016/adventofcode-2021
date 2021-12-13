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

## Day 5
Started with a ```Vent``` record which represents a vent with a starting ```Point``` and an end ```Point```. Then
created an OceanFloor record with a ```Grid``` and ```List``` of vents. The ```overlap()``` method of the ocean
floor simply counts all cells in the grid with a value greater tha '1'. This works as an empty cell contains '.' which
is smaller than '1'.

To create the grid, find min and max X and Y values of the vents (and add 1), and beware that no assumption can be made 
on how start and end point are positioned relative to each other (above, below, left or right). To "walk" the positions
of a vent, I determined the direction based on the X and Y positions of the start and end coordinates. Then just walk 
from start to end of the vent and update position using the direction (just add the points). This works for part 1 and 
part 2. Getting the direction right was a bit tricky (nasty if/else construction).

I created a static method to create an OceanFloor from the input data using a filter, which determines if a vent
should be included or not.For part 2 the filter always returns true, for part 1 it only returns true if X or Y of
the start and end point are equal.

## Day 6
For part 1 the brute force method ```LanternFishModel.progress(...)``` worked like a charm (just create a list and 
process the list for each day, adding new fish when needed). I expected this method would not work for part 2, as it
would probably increase the number of days or the input list to some insane number. Well, 256 days wasn't that much, 
but way too much for the problem at hand.

Solved part 2 by writing a recursive solution. That initially didn't run well either (as expected), so I added a cache
for the calculations (if a number of fish for a certain date is calculated, store the value and don't recalculate next 
time). After having the puzzle solved, I rewrote the class to remove the ```cache``` attribute at class level. Maybe 
not a more readable solution, but fun to do (feels a bit Javascript style coding),   

In the end, the solution is a ridiculously small algorithm ...

## Day 7
As opposed to yesterday, I did grasp this one from the start. Created a model class again (```FuelModel```), to
calculate the fuel consumption for all crabs (```altitudeFuelCost```), and a method to find the optimal-altitude and 
usage at that altitude (```optimalAltitude```)going over a range from minimum (including) to maximum (including) 
altitude in the input.
Part two required a different method for computing the fuel consumption, so I parameterized that method and passed
it into the ```altitudeFuelCost```, and ```optimalAltitude```. For the complicated computation, I decided to cache 
the fuel consumption once calculated. Overall quite straight forward.

## Day 8
Part 1 is really simple, just count the strings of length 2, 3, 4 or 7 ... done. Part 2 is a bit more tricky. I used 
a brute force on this one ... I' ve created a list of all possible permutations of "abcdefg". Then checked which 
permutation would get me a valid number on all teh encoded values on the left, and then used that permutation to decode
the values on the right.

## Day 9
A grid again,so probably not too difficult. for part 1, just search for all locations in the grid that do not have
an adjacent lower number(walk the entire grid and collect the points), and then calculate the "risk" for these 
points and sum them. Part 2 continues on part 1, create a basin around all lowest points (part 1), and create a set 
of all points around it (move outward like a breadth-first-search, using a queue) until you encounter a '9'. Then take 
the 3 largest sets to calculate the score.

## Day 10
A nice puzzle today and not so complicated. I started with using a queue to do the matching. The ```Chunk.validate``` 
method checks a chunk by pushing any open-symbol onto the stack and on every close-symbol it checks if the close-symbol
matches the open-symbol at the top of the stack. The method returns the validated state (```CORRUPTED```), and the 
symbol that was identified as corrupted. When the end of the chunk was encountered and the stack wasn't empty the chunk
was considered ```INCOMPLETE``` and the method would return the incomplete state and the list of close-symbols that is 
expected based on the open-symbols still on the stack (as all of these are missing their close-symbol).
For part 1, the ```corrupted``` method validates the input and returns a list of the corrupted results, then the 
```corruptedScore``` method calculates the score. For part 2, the ```incomplete``` method validates the input and 
returns a list of the uncompleted results, then the ```incompleteScore``` method calculates the score for the missing 
chunk parts. 

## Day 11
Again not too difficult today. The tricky part is to ensure the energy level will not be raised anymore once an octopus 
has flashed during a step. So at the beginning of each step the energy level must be raised by 1, then all octopuses 
must flash not more than once, and finally the flash-status on all octopuses must be reset for the next step.
Part 1 needs to sum all flash count for 100 steps. Part 2 needs to loop until the flash count equals the number of
octopuses.

## Day 12
Today was fun ... I expected I would need a specialized version of the ```GenericSearch.findAll``` (based of 
```GenericSearch.bsf```), so I started with a ```Cave``` class (which would know its neighbours) and a ```Maze``` class
that holds a list of caves and a ```start``` and ```end``` cave attribute. Then I developed the ```MazeSearch```, with 
methods to search the maze for all possible ```paths```. The ```MazeSearch.successors``` would return all neighbours 
for a cave but would remove small caves from the list if they had already been visited (i.e. they are already in the 
current path). Part 2 allowed for the same approach but uses a specialized ```MazeSearch.successorsOneSmall```, which 
allows one small cave to be visited twice (only allow small caves as a successor, if it hasn't been visited or has been 
visited only once and no other small cave was visited more than once up until that point).

## Day 13
Folding a grid ...yeah right. Not difficult, but nasty to get right, especially if the folding line (horizontal or 
vertical) is not in the middle of the paper... 

First step is to create a list of points and a list of folding instructions from the input. Then create a grid from the 
list of points (setting the dots in the grid as '#'). Then implement the ```foldUp``` and ```foldLeft```. First create 
a new empty grid based on the size calculation of the new grid (beware the folding line might not be in the middle). 
Then copy part sof the old grid into the new one (copy first top/left part just top-down or left to right), and then
copy the bottom/right part reversed (bottom-up or right to left). When you have one, the second is pretty similar.
