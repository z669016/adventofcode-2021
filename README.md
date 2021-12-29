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
in the list in values. For part 2, I created a new list from the original one (creating the sliding window values) and
reused the helper for counting. Runs in less than 15 ms on my laptop. 

## Day 2
I've created a ```Submarine``` class, with a ```move``` method taking a ```Command``` record as an input. The command 
has a direction and a distance attribute. This approach made it easy to process the input. For part 1 the input is 
transformed into commands and the commands were used to move the submarine.
For part 2, I inherited from the initial ```Submarine``` class and changed the ```move``` method according to 
instructions, then processed the command list again... pretty straight forward.

## Day 3
Solved the puzzles using counting and filtering streams (just used ```List<String>``` and ```charAt()```).

## Day 4
Started with creating a ```BingoCard``` class on which I could ```call``` numbers and which would keep track of marked 
numbers. The ```call``` method would return true if a row of column would be completely marked. The card could also
calculate it's ```score```.
Next I created a ```Bingo``` class which would play a bingo game on a list of cards given a list of numbers to call. 
The ```play``` method would return the first card which returns true on the called number. Part 1 will simply print 
the score of that first card. For part 2, I added a ```playForLast```method that removes each completed cards from the 
list and returns only the last card that completes.

## Day 5
Started with a ```Vent``` record which represents a vent with a starting ```Point``` and an end ```Point```. Then
created an OceanFloor record with a ```Grid``` and ```List``` of vents. The ```overlap()``` method of the ocean
floor simply counts all cells in the grid with a value greater than '1'. This works as an empty cell contains '.' which
is smaller than '1'.

To create the grid, find min and max X and Y values of the vents (and add 1), and beware that no assumption can be made 
on how start and end point are positioned relative to each other (above, below, left or right). To "walk" the positions
of a vent, I determined the direction based on the X and Y positions of the start and end coordinates. Then just walk 
from start to end of the vent and update ```position``` using the direction (just add the points). This works for part 
1 and part 2. Getting the direction right was a bit tricky (nasty if/else construction).

I created a static method to create an OceanFloor from the input data using a filter, which determines if a vent
should be included or not. For part 2 the filter always returns true, for part 1 it only returns true if X or Y of
the start and end point are equal (so the vents are a straight horizontal or vertical line).

## Day 6
For part 1 the brute force method ```LanternFishModel.progress(...)``` worked like a charm (just create a list and 
process the list for each day, adding new fish when needed). I expected this method would not work for part 2, as it
would probably increase the number of days or the input list to some insane number. Well, 256 days wasn't that much, 
but way too much for the problem at hand.

Solved part 2 by writing a recursive solution. That initially didn't run well either (as expected), so I added a cache
for the calculations (if a number of fish for a certain date is calculated, store the value and don't recalculate next 
time). After having the puzzle solved, I rewrote the class to remove the ```cache``` attribute at class level. Maybe 
not a very readable solution, but fun to do (feels a bit Javascript style coding),   

In the end, the solution is a ridiculously small algorithm ...

## Day 7
As opposed to yesterday, I did grasp this one from the start. Created a model class again (```FuelModel```), to
calculate the fuel consumption for all crabs (```altitudeFuelCost```), and a method to find the optimal-altitude and 
usage at that altitude (```optimalAltitude```) going over a range from minimum (including) to maximum (including) 
altitude in the input.
Part two required a different method for computing the fuel consumption, so I parameterized that method and passed
it into the ```altitudeFuelCost```, and ```optimalAltitude```. For the complicated computation, I decided to cache 
the fuel consumption once calculated. Overall quite straight forward.

## Day 8
Part 1 is really simple, just count the strings of length 2, 3, 4 or 7 ... done. Part 2 is a bit more tricky. I used 
a brute force on this one by creating a list of all possible permutations of "abcdefg". Then checked which 
permutation would get me a valid number on all the encoded values on the left, and then used that permutation to decode
the values on the right.

## Day 9
A grid again,so probably not too difficult. for part 1, just search for all locations in the grid that do not have
an adjacent lower number (walk the entire grid and collect the points), and then calculate the "risk" for these 
points and sum them. Part 2 continues on part 1, create a basin around all lowest points (part 1), and create a set 
of all points around it (move outward like a breadth-first-search, using a queue) until you encounter a '9'. Then take 
the 3 largest basin sets to calculate the score.

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
must flash not more than once, and finally the flash-status on all octopuses must be reset at beginning of the next 
step. Part 1 needs to sum all flash count for 100 steps. Part 2 needs to loop until the flash count equals the number 
of octopuses.

## Day 12
Today was fun ... I expected I would need a specialized version of the ```GenericSearch.findAll``` (based of 
```GenericSearch.bsf```), so I started with a ```Cave``` class (which would know its neighbours) and a ```Maze``` class
that holds a list of caves and a ```start``` and ```end``` cave. Then I developed the ```MazeSearch```, with 
methods to search the maze for all possible ```paths```. The ```MazeSearch.successors``` would return all neighbours 
for a cave but would remove small caves from the list if they had already been visited (i.e. they are already in the 
current path). Part 2 allowed for the same approach but uses a specialized ```MazeSearch.successorsOneSmall```, which 
allows one small cave to be visited twice (only allow small caves as a successor if it hasn't been visited, or has been 
visited only once and no other small cave was visited more than once up until that point).

## Day 13
Folding a grid ...yeah right. Not difficult, but nasty to get right, especially if the folding line (horizontal or 
vertical) is not in the middle of the ```TransparentPaper``` paper.

First step is to create a list of points and a list of folding instructions from the input. Then create a grid from the 
list of points (setting the dots in the grid as '#'). Then implement the ```foldUp``` and ```foldLeft```. First create 
a new empty grid based on a size calculation of the new grid (beware the folding line might not be in the middle). 
Then copy parts of the old grid into the new one (copy first top/left part just top-down or left to right), and then
copy the bottom/right part reversed (bottom-up or right to left). When you have one, the second is pretty similar.

## Day 14
Looking at the challenge for part 1, the second part will most likely crash the part 1 solution due to an extreme number
of repetitions or and extreme growing list. I started with a ```PairInsertionRule``` record that returns the element 
to be inserted based on the transformation starting element.
The ```PairInsertionRules``` record reads and processes the input and provides the ```template()``` and the 
```rules()``` (which is a ```List<PairInsertionRule>```). For part 1 I've added a ```transform(int count)``` method
to the ```PairInsertionRules``` that transforms the template the requested number of times by applying the 
insertion rules. The final string grows fast so this probably won't work on part 2.
For part 2, I changed the approach and now the transformation first creates a list of element pairs from the input, 
and then transforms the element list into a new one, continuously updating the count of the element pairs. for 
calculating the occurrences of all individual elements, just sum the element count of the first element of each element 
pair, and add 1 for the final element. This means that during transformation, you need to remember the last element of 
the list. It may sound a bit fuzzy,but the code should be clear.

## Day 15
This sound like a Dijkstra problem, which you can solve using a specialized BFS as well. The challenge is ensuring you
stay on the shortest path, and purge search path that are no longer the shortest route to the current position. Which 
you can do by keeping a map of the shortest route (so far) up to the current point. As a search state, I
used the current ```Point``` in the grid and the total risk level up until that point. This work well as 
it allows the use of a ```PriorityQueue``` ordered by the ```totalRiskLevel``` as an optimization.
For part 2, the most tricky part (for me) was the expanding of the ```Cave``` to a size 5 times larger and initializing
the risk levels correctly. Once that challenge was solved, the search didn't need any additional optimization.

## Day 16
Wow ... that took quite some work ... Translation of the hex-string into binary String of zeroes and ones is straight 
forward. Indeed, I used a String presentation of the binary, for that is easier to parse. Then I created a
```PacketParser``` that could iterate over the binary data string. The ```next()```returns a ```Pair<Token,Long>``` 
identifying the token type and the token value.
For part one, I just filtered all VERSION_ID tokens and added their values. Part 2 was a bit more delicate, so I 
created a ```Calculator``` that took a string of hexData. From that it created a parser and parsed the input 
recursively to calculate the values. Not my everyday job, so it took a while to get it right.

## Day 17
Sometimes, when I think a lot, I make mistakes ... and this was just one of those days. I started of way too difficult,
took several detours and ended op with less than 100 lines of readable code. This is so annoying.

It's no use to check for hits with velocity.x < 1 (because after with velocity.x == 0 the probe would only go straight 
down, and with velocity.x < 0 it would move in the wrong direction), and also not useful to check with 
velocity.x > bottomRight.x because after only one step you would be past the target area regardless of the velocity.y.
Velocity.y should be at least bottomRight.y and probably less that abs(bottomRight.y). 

Knowing this, I used a double loop over the velocity.x and velocity.y ranges to determine every possible combinations
of possibly correct velocity.x and velocity.y values. Then create a hit list of all combinations that indeed end up in 
the target area. For part 1, get the highest value for velocity.y from the hit list and from that velocity.y determine 
the maximum height. For part 2 (now so simple), take the size of the hit list.

## Day 18
Started over again twice, and was finally able to make it. Choosing the right data structure to start with appears
to be vital. Changing the approach half way is very difficult, at least to me.

It also took a while to have the right order of actions. I stored the numbers in a tree, and walked the tree searching
for explosions going from left to right  depth first. That's fine for explosions, which only occur at the lowest level,
This fails on split actions that can occur on every level, so those need to be taken left to right top first.

It's almost frustrating how quickly the answer gets calculated compared to the time it took towrite the correct code :-(

## Day 19
My goodness, that caused me quite some headache. My initial idea, was to use scanner 0 as the base scanner, and assume 
its scanner location to be (0,0,0). Then do all possible rotations on the other scanners and save these (to 
prevent recalculation). Then keep looping over all remaining scanners and find a rotated scanner that has 12 (or 
more) beacons in common with the base scanner. 

To see if the rotated scanner has 12 beacons in common with the base scanner, calculate the difference between the 
beacons of the base scanner and the rotated scanner. The beacons that both scanners have in common have the same 
distance, and that distance (x,y,z) is equal to the location of the rotated scanner (relative to the scanner of the
base scanner, which is (0,0,0)). Then add all beacons of the rotated scanner (after adding the scanner position to 
the beacon position), but prevent doubles in the list.

Loop over these steps until all scanners have been located and all unique beacons have been added to the base scanner
beacon list.

Initially, I really didn't get the 24 transformations right... I checked Reddit
for this one and found a reference on [How to get all 24 rotations of a 3-dimensional array?](https://stackoverflow.com/questions/16452383/how-to-get-all-24-rotations-of-a-3-dimensional-array])
so I added those methods to my ```Point3D``` class. 

For part 1, count the unique beacons in the beacons list of the base scanner. For part 2, calculate the max distance
between all scanner locations.

## Day 20
What seemed to be a relatively simple puzzle turned out to be very nasty, and I needed the help of the Reddit blogs
to understand the issue. And even then ... it took a while to understand the problem.

I've created ```Image``` record to hold the picture and an ```ImageEnhanced``` record (I love those stateless objects)
to manipulate the image and hold the algorithms' transformation table (array). Before the transformation, I increased 
the size of the image with a clean row/column all around. That certainly simplified the enhancement process.

The trick was in "flashing" ... huh? Yes, indeed ... the fist character in the algorithm table was a '#' and the last 
one was a '.'. This means, if all pixels around the pixel to transformed are dark then the current pixel will bbe lit
(entry 0 in the transformation table). AND ... this is the case for the entire infinite universe around the original 
image. The last entry in the transformation table (entry 512) is '.', which means, if all pixels in the block of 9 are 
lit, then the new status will be off (dark). AND ... this is the case for the entire infinite universe around the 
original image. SO, the infinite universe will be "flashing"... And that is what you need to take into account!

My sample transformation table, didn't have this typical behaviour. I really wonder who thought of this edge case. I 
certainly didn't.

The transformation itself is easy and fast, so part 2 was just a repetition of part 1.

## Day 21
The game with the regular die is straight forward, just remember that the positions on the board start with 1, and 
not with 0 (subtract one before you do modulo 10).
The quantum die is tricky as it plays many many many games with the same outcomes, so you need to cache the results of 
all possible positions. I started with a ```Die``` interface and hoped it could be reused for part 2, well no. So that
part of the code is basically useless for part 2.

## Day 22
I used a ```Range3D``` class containing a ```Point3D``` min and ```Point3D``` max, and a ```Command``` class containing
the on/off command and te range to process the input. The ```Range3D``` class can determine overlap and containment.

For part 1, the ```Reactor``` class only processes commands with a range that is contained in the range of
(-50, -50, -50) ... (50, 50, 50) by creating a ```Set<Point3D>``` and removing/adding the points in the range of 
the command, and returns the size of the result set. This of course doesn't work for part 2 where ranges will probably be too big.

For part 2, I decided to carve out overlapping parts, so I added a ```carve``` method to the Range3D, that carves out 
the other range from the current range (carve out block to the left and right according to x-axis, the block below and 
above according to y-axis, and the block in front and behind according to z-axis). Then ```Reactor``` processes the 
commands and returns the sum of the range sizes (```point3DCount```) of all on-commands. I refactored part 1 to use
the same approach as well.

## Day 23
A search approach again, and looking at the simplicity of the board, it is probably exploding the options. To limit
the runtime, I used a priority queue and once basis functions seemed to work, I started to add avoiding possible 
dead ends. That worked well for part 1 but got me into trouble again at part 2. After some debugging, I found lots of
duplicated state in the queue ... indeed I forgot to remember the visited states ... OMG ... this was so stupid.

After adding visited stated (never visit them again), runtime was fine 3.3 second for part 1 and 2.3 second for part 2.

## Day 24
Now this was something I didn't expect at all. I wouldn't have solved without help of Reddit, and even then it took me a
while before I understood the challenge and the logic behind it.

Of course, I started with writing an ALU and MONAD, knowing that finding a 14 digit number would take ages doing it 
brute force.

In the code the Z-variable is being used as a stack (yes, a stack), by multiplying the current Z-value with 26  
and adding another value to it (w + constant). The X-variable is used to pop the top of the stack (add x z; mod x 26;)

Overall the code contains 14 blocks, one foreach digit in the input. 7 blocks become a push, which are the ones that 
contain "add x <constant>" with a constant that ensures the next "eql x w" to fail). The other7 blocks become a pop that
check if the latest input digit equals the top of the stack + constant.

Once I got the concept, I annotated the puzzle input (```src/main/resources/day24.txt```).

For my input the code blocks translated into
```
push digit(1) + 12
push digit(2) + 7
push digit(3) + 8
push digit(4) + 8
push digit(5) + 15
check digit(6) == pop - 16 ==> digit(6) == digit(5) + 15 - 16 == digit(5) - 1
push digit(7) + 8
check digit(8) == pop - 11 ==> digit(8) == digit(7) + 8 - 11 == digit(7) - 3
check digit(9) == pop - 13 ==> digit(9) == digit(4) + 8 - 13 == digit(4) - 5
push digit(10) + 13
check digit(11) == pop - 8 ==> digit(11) == digit(10) + 13 - 8 == digit(10) + 5
check digit(12) == pop - 1 ==> digit(12) == digit(3) + 8 - 1 == digit(3) + 7
check digit(13) == pop - 4 ==> digit(13) == digit(2) + 7 - 4 == digit(2) + 3
check digit(14) == pop - 14 ==> digit(14) == digit(1) + 12 - 14 == digit(1) - 2
```

Once I had this figured out, part 1 was to find the biggest number (what gives you the highest number one
each position), and for part 2 the same with the lowest possible number.

Amazing, how someone can think of such a puzzle...

