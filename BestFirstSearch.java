import java.io.*;
import java.util.*;

public class BestFirstSearch {

 static int n = 0;
 static int C = 0;
 static int optimalValue = 0;
 static int nodeVis = 1;
 static int leafVis = 0;
 static Stack < Node > optimalSolution;
 static Comparator < Node > comparator;
 static PriorityQueue < Node > pQueue;
 static ArrayList < Node > list;
 static ArrayList < Integer > x;

 public static class Node {
  int weight;
  int profit;
  int level;
  int bound;
  ArrayList < Integer > path;

  public Node(int w, int c, int l, int b) {
   weight = w;
   profit = c;
   level = l;
   bound = b;
   path = new ArrayList < > ();
  }

  public Node() {
   weight = 0;
   profit = 0;
   level = 0;
   bound = 0;
   path = new ArrayList < > ();
  }

  public int getWeight() {
   return weight;
  }

  public void add(int e) {
   path.add(e);
  }
  public int getProfit() {
   return profit;
  }
  public String toString() {
   return "[" + weight + ", " + profit + ", " + level + ", " + bound + "]";
  }
 }

 public static class NodeComparator implements Comparator < Node > {
  @Override
  public int compare(Node x, Node y) {
   if (x.bound > y.bound)
    return -1;
   if (x.bound < y.bound)
    return 1;
   return 0;
  }
 }

 static void readInStuff(String filename) {
  String line = null;
  int commaIndex = 0;
  int profit = 0;
  int weight = 0;

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

 static int knapsack() {
  Node u = new Node(0, 0, 0, 0);
  Node v = new Node(0, 0, 0, 0);

  u.level = -1;
  u.profit = 0;
  u.weight = 0;
  pQueue.add(u);

  int maxProfit = 0;
  boolean promising = false;
  while (pQueue.size() != 0) {
   promising = false;
   u = pQueue.peek();
   System.out.println(maxProfit + " <-> " + u);
   pQueue.remove(u);
   //optimalSolution.push(u);

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

   if (v.weight <= C && v.profit > maxProfit) {
    maxProfit = v.profit;
    System.out.println("UPDATED OPTIMAL");
   }

   v.bound = bound(v);

   if (v.bound > maxProfit) {
    pQueue.add(v);
    promising = true;
    optimalSolution.push(list.get(v.level));
   }
	 else
	 	leafVis++;
   System.out.println("Yes Child = " + v);
   v = new Node();

   v.weight = u.weight;
   v.profit = u.profit;
   v.level = u.level + 1;
   v.bound = bound(v);
   if (v.bound > maxProfit) {
    pQueue.add(v);
    promising = true;
   }
	 else
	 	leafVis++;

   System.out.println(" No Child = " + v);
   nodeVis += 2;
  }
  System.out.println("maxpro = " + maxProfit + " leafvis = " + leafVis);
  optimalValue = maxProfit;
  return maxProfit;
 }

 public static void writeStuff(String fileName) throws IOException {
  BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
  writer.write(n + "," + optimalValue + ",SIZEOFOPTIMALVALUE");

  writer.close();
 }

 public static void main(String[] args) {
  comparator = new NodeComparator();
  pQueue = new PriorityQueue < Node > (comparator);
  list = new ArrayList < Node > ();
  x = new ArrayList < Integer > ();
  optimalSolution = new Stack < Node > ();
  readInStuff(args[0]);
  System.out.println("[weight,profit,level,bound]\ncap =" + C);
  for (int i = 0; i < n; i++) {
   x.add(0);
   System.out.println("-" + list.get(i));
  }
  //bfs();
  knapsack();
  System.out.println(nodeVis);
  try {
   writeStuff(args[1]);
  } catch (IOException ex) {
   System.out.println("Error writing file");
  }

  while (!optimalSolution.empty())
   System.out.println(optimalSolution.pop());
  //while (pQueue.size() != 0)
  //	System.out.println(pQueue.remove());
 }
}
