# javaThreads

>>Multiply Table
The program creates two tables and multiply them. 
From Linear Algebra, we can multiply a matrix by a vector from the right, as long as the number of its columns is sufficient
matrix to be equal to the number of rows of the vector. For example if we have a matrix A of dimensions n x m and a vector v of dimensions m x 1, 
then the product A * v equals an n x 1 vector by applying the well-known matrix multiplication method with vector. 
An example is given in MultiplyTables.png
 
Assuming we have k threads, where k is a power of 2 and the array has dimensions n x m where n is also a power of 2 and n > k, 
design a solution that calculates the product A * v using the k threads in the best possible way. 
The program should "fill" the array A and the vector v with random numbers between 0 and 10.

Q: What the time needed for 1,2,4 and 8 Threads?





>>SimpsonsScript
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






>>Ipsum Thread Count
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









>>Shopping With Threads
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






Design and implementation of small-scale distributed systems,
using interprocess communication techniques.
We will use Java Sockets to synchronize processes that run on different computing systems.


>>ServerClient_HashTable
An application involving process communication with TCP sockets.
Server: The server starts its operation by building an empty hashtable that stores key-value pairs. 
The size of the matrix is ​​2^20. It then opens a socket in the door we pass through
parameter to the constructor (eg 8888, 9999, etc). It then waits to connect some clients.
Client: The client starts up and tries to connect to the server at the door
which we have defined. Again, the door should be passed as a parameter to its constructor
client.
After the client successfully connects to the server, the server starts a new service thread.

The client sends the server a trio of values ​​(A, B, C), where A has one of the values ​​0 (end of communication),
1 (insert), 2 (delete) and 3 (search), B is an integer indicating the key and
C an integer denoting the value has the key.

For example, if the client sends the pair (1,3,10), this means that the server should
inserts element 3 into the hash table with value 10. After successful insertion
of the element the server returns the value 1 to the client otherwise it returns 0. 
If the client sends the command (2,3) the server must delete the item with key 3 from the hash table.
If the deletion is successful, the server returns the value 1 otherwise it returns the value 0.
Finally if the client sends the command (3,5) then the server looks for the element with key 5 in the table and
returns to the client the corresponding value or 0 if the key does not exist.
The connection is removed when the client sends the (0,0) command.
Commands are read from the keyboard.




>>SC_PROCOS
Let be a process system consisting of consumer, producer type processes
and servers that communicate with TCP sockets. 
Each server has an integer variable (even storage) that records their inventory
of products available in total. The initial value of this variable is a random one
number between 1 and 1000.
Each producer randomly connects to one of the servers and adds a random X value
between 10 and 100 in the storage variable. If the new value of the storage variable exceeds the
1000, then the corresponding server prints a message on the screen and the storage value does not
updated. The producer then waits a random time interval between 1 and 10 seconds and connects 
to the next server also randomly.
Each consumer randomly connects to one of the servers and removes a random Y value
between 10 and 100 from the storage variable. If the new storage value becomes less than 1,
then the corresponding server prints a message on the screen and the storage value does not
is updated. It then waits for a random amount of time between 1 and 10 seconds and connects 
to the next server also randomly.

The number of servers must be determined from the beginning, however the program
it should be able to work with any number of servers, producers and consumers.

Each server should be able to support many at the same time
consumers and many producers, so it should support multi-threading.

Note: Although this system can work in a distributed format (the
processes to be on different computers with different IPs) assume that the
system will run on the same computer (localhost).
So, you can define for each server a different door for producers and consumers.
For example, if we have 3 servers, we can set ports 8881, 8882, 8883 to
the producers are connected and the doors 9991, 9992, 9993 to connect the consumers.

Thus, you will be able to choose a random server each time for each producer and each consumer to connect.
