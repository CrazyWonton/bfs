Author: Kevin Tarczali
Date: 19 November 2017
Class: CS375

Programming Assignment 2

Description:
  This program takes in a list of items provided by a file and finds the optimal
  solution using the Best First Search algorithm. Once solved, it writes the
  solution to an output file.

To Compile:
  Type 'make' into the terminal, then press ENTER

To Run:
  Type 'java BestFirstSearch ' followed by the input file then the output file,
  then press ENTER

Data Structures:
  The program utilizes a PriorityQueue with a comparator based on the bounds
  of each node to sort the items. All others are ArrayLists, and hold various
  bits of information regarding the optimal solution and the list of nodes.

Runtime:
  knapsack is bounded by O(n*c), where n is the number of items and c is the
  capacity of the knapsack
