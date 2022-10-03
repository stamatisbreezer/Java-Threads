# javaThreads

Multiply Table
The program creates two tables and multiply them. 
From Linear Algebra, we can multiply a matrix by a vector from the right, as long as the number of its columns is sufficient
matrix to be equal to the number of rows of the vector. For example if we have a matrix A of dimensions n x m and a vector v of dimensions m x 1, 
then the product A * v equals an n x 1 vector by applying the well-known matrix multiplication method with vector. 
An example is given in MultiplyTables.png
 
Assuming we have k threads, where k is a power of 2 and the array has dimensions n x m where n is also a power of 2 and n > k, 
design a solution that calculates the product A * v using the k threads in the best possible way. 
The program should "fill" the array A and the vector v with random numbers between 0 and 10.

Q: What the time needed for 1,2,4 and 8 Threads?




SimpsonsScript
A file named “simpsons_script_lines.csv” is given which contains the dialogues from all the episodes of the series.
Each line of the file includes information such as episode, speaking character, location, and the text.

A program in JAVA that will first load the lines of the file into an array, and then
will create k threads, each of which will take care of processing a part of the table.
The program will calculate the following:
1) the episode in which the dialogues had the largest number of words
2) the location where most dialogues took place
3) for each of the characters Bart, Homer, Margie and Lisa, print the most 
common word they use (from 5 characters or more) as well as how many times was used.
 
Time measurements for 1, 2, 4 and 8 threads.






Ipsum Thread Count
We will use open APIs that give us information as text and from them we will extract various statistics.
The following APIs generate plain text by calling HTTP GET method:
https://loripsum.net/api/10/plaintext
http://metaphorpsum.com/paragraphs/10

Program in JAVA that will use 1, 2, 4 or 8 threads to make a number of calls k per thread,
to an APIs  to calculate the following:
1) the average word length of the text from all texts produced. In every
execution (k-calls on n-threads) i.e. a number will be printed.
2) the percentage of occurrences of the characters of the English alphabet from all the texts that
were produced. In each execution (k-calls on n-threads) 26 numbers will be printed,
the sum of which will be 100.

Ignore punctuation.
Time measurements for 1, 2, 4 and 8 threads









Shopping With Threads
Synchronization and mutual exclusion.

We will simulate the operation of a clothing store.
The operation of the store is based on the following rules that must implement:
* A maximum of 40 people can be in the store at the same time.
* There are two fitting rooms, one for women, one for men and each fitting room has 5 separate rooms.
* There is a cash register in the store which, however, due to the COVID restrictions does not allow more than 10 people to be in line.

Implement the above functionality using semaphores assuming that the
percentage of women-men in the store is 50%-50%, considering that the time
service at the checkout is constant from the moment it's our turn to pay
(eg, 5 seconds) and also assuming that each person needs from 3 to 10
seconds to try on clothes in the fitting room before going to checkout. Also consider that every 2 to 5 seconds a new customer enters the store 
to try on and buy clothes.