//Author: Kevin Tarczali
import java.io.*;
import java.util.*;

/**
Outer class which holds all inner classes and the driver

Included Methods:
	readInStuff
	bound
	knapsack
	writeStuff
	main
**/
public class BestFirstSearch {

 static int n = 0;
 static int C = 0;
 static int optimalValue = 0;
 static int nodeVis = 1;
 static int leafVis = 0;
 static Comparator < Node > comparator;
 static PriorityQueue < Node > pQueue;
 static ArrayList < Node > list;
 static ArrayList < Integer > x;
 static ArrayList <Integer> op;

/**
	Nested class which contains the structure for the nodes

	Included Methods:
		Node <- Constructor
		getWeight
		add
		getProfit
		toString
**/
 public static class Node {
  int weight;
  int profit;
  int level;
  int bound;
  ArrayList < Integer > path;

/**
	Perameterized Constructor
**/
  public Node(int w, int c, int l, int b) {
   weight = w;
   profit = c;
   level = l;
   bound = b;
   path = new ArrayList < > ();
  }

	/**
		Default Constructor
	**/
  public Node() {
   weight = 0;
   profit = 0;
   level = 0;
   bound = 0;
   path = new ArrayList < > ();
  }

	/**
		Getter
	**/
  public int getWeight() {
   return weight;
  }

	/**
		Adds the element e to the path of this node
	**/
  public void add(int e) {
   path.add(e);
  }

	/**
		Getter
	**/
  public int getProfit() {
   return profit;
  }

	/**
		Override the toString method
	**/
  public String toString() {
   return weight + "," + profit;
  }
 }

/**
	Nested class which contains the comparator needed for the PriorityQueue

	Included Methods:
		compare
**/
 public static class NodeComparator implements Comparator < Node > {
  @Override
	/**
		Overrides the compare function
	**/
  public int compare(Node x, Node y) {
   if (x.bound > y.bound)
    return -1;
   if (x.bound < y.bound)
    return 1;
   return 0;
  }
 }

 /**
 	Reads in data from fileName

	Assumes file is formatted in the proper way
 **/
 static void readInStuff(String filename) {
  String line = null;	//temporary string
  int commaIndex = 0;	//will hold the index of the comma
  int profit = 0; 	//holds the profit of the node
  int weight = 0;		//holds the weight of the node

  try {
   FileReader fileReader = new FileReader(filename);
   BufferedReader bufferedReader = new BufferedReader(fileReader);
   line = bufferedReader.readLine();
   commaIndex = line.indexOf(',');
   n = Integer.parseInt(line.substring(0, commaIndex).trim());
   C = Integer.parseInt(line.substring(commaIndex + 1).trim());
   int i;

   for (i = 0; i < n; i++) {
    line = bufferedReader.readLine();
    commaIndex = line.indexOf(',');
    weight = Integer.parseInt(line.substring(0, commaIndex).trim());
    profit = Integer.parseInt(line.substring(commaIndex + 1).trim());
    Node temp = new Node(weight, profit, 0, 0);
    list.add(temp);
   }

   bufferedReader.close();
  } catch (FileNotFoundException ex) {
   System.out.println("Unable to open file '" + filename + "'");
  } catch (IOException ex) {
   System.out.println("Error reading file '" + filename + "'");
  }
 }

 /**
 	Computes the bound for node u with the fractional knapsack solution
 **/
 static int bound(Node u) {
  if (u.weight >= C)
   return 0;
  int bound = u.profit;
  int j = u.level + 1;
  int totweight = u.weight;
  while ((j < n) && (totweight + list.get(j).getWeight() <= C)) {
   totweight += list.get(j).getWeight();
   bound += list.get(j).getProfit();
   j++;
  }
  if (j < n)
   bound += (C - totweight) * list.get(j).getProfit() / list.get(j).getWeight();
  return bound;
 }

 /**
 	Starts at the root and finds an optimal solution to the 01 knapsack problem
	using the BestFirstSearch algorithm
 **/
 static int knapsack() {
  Node u = new Node(0, 0, 0, 0);	//parent node
  Node v = new Node(0, 0, 0, 0);	//child node

  u.level = -1;
  u.profit = 0;
  u.weight = 0;
  pQueue.add(u);

  int maxProfit = 0;
  boolean promising = false;
  while (pQueue.size() != 0) {
   promising = false;
   u = pQueue.peek();
  // System.out.println(maxProfit + " <-> " + u);
   pQueue.remove(u);
   if (u.bound < maxProfit){
		 leafVis++;
	  continue;
	}
   v = new Node();
   if (u.level == -1)
    v.level = 0;
   if (u.level == n - 1)
    continue;
   v.level = u.level + 1;
   v.weight = u.weight + list.get(v.level).getWeight();
   v.profit = u.profit + list.get(v.level).getProfit();
	 for(int i:u.path)
	 	v.add(i);
	 //v.path = u.path;
	 v.add(v.level);
   if (v.weight <= C && v.profit > maxProfit) {
    maxProfit = v.profit;
		op = v.path;
    //System.out.println("UPDATED OPTIMAL");
   }
   v.bound = bound(v);
   if (v.bound > maxProfit) {
    pQueue.add(v);
    promising = true;
   }
	 else
	 	leafVis++;
  // System.out.println("Yes Child = " + v);
   v = new Node();
   v.weight = u.weight;
   v.profit = u.profit;
   v.level = u.level + 1;
   v.bound = bound(v);
	 v.path = u.path;
   if (v.bound > maxProfit) {
    pQueue.add(v);
    promising = true;
   }
	 else
	 	leafVis++;
  // System.out.println(" No Child = " + v);
   nodeVis += 2;
  }
  //System.out.println("maxpro = " + maxProfit + " leafvis = " + leafVis);
  optimalValue = maxProfit;
  return maxProfit;
 }

 /**
 	Writes the solution to the file specified in the Perameter
 **/
 public static void writeStuff(String fileName) throws IOException {
  BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
  writer.write(n + "," + optimalValue + "," + op.size());
			writer.newLine();
	writer.write(nodeVis + "," + leafVis);
			writer.newLine();
	String temp;
	for(int i:op){
		writer.write(list.get(i).toString());
		writer.newLine();
	}
  writer.close();
 }

 /**
 	main method that drives the program
 **/
 public static void main(String[] args) {
  comparator = new NodeComparator();
  pQueue = new PriorityQueue < Node > (comparator);
  list = new ArrayList < Node > ();
  x = new ArrayList < Integer > ();
	op = new ArrayList<Integer>();
  readInStuff(args[0]);
  for (int i = 0; i < n; i++) {
   x.add(0);
  }
  knapsack();

  try {
   writeStuff(args[1]);
  } catch (IOException ex) {
   System.out.println("Error writing file");
  }
 }
}
