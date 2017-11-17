import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class BestFirstSearch{

	static int n = 0;
	static int C = 0;
	static int size = 0;
	static Comparator<Node> comparator;
	static PriorityQueue<Node> pQueue;
	static ArrayList<Node> list;
	static ArrayList<Node> knapsack;
	static ArrayList<Integer> x;

	public static class Node{
		int weight;
		int profit;
		int ratio;
		int level;
		int bound;

		public Node(int w, int c, int l, int b){
			weight = w;
			profit = c;
			if(w != 0)
				ratio = c/w;
			else
				ratio = -1;
			level = l;
			bound = b;
		}

		public int getWeight(){
			return weight;
		}

		public int getProfit(){
			return profit;
		}
		public String toString(){
			return "[" + weight + ", " + profit + ", " + ratio + ", " + level + ", " + bound + "]";
		}
	}

	public static class NodeComparator implements Comparator<Node>{
		@Override
			public int compare(Node x, Node y){
				if (x.ratio > y.ratio)
					return -1;
				if (x.ratio < y.ratio)
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
			n = Integer.parseInt(line.substring(0,commaIndex).trim());
			C = Integer.parseInt(line.substring(commaIndex+1).trim());
			int i;
			list.add(new Node(0,0,0,0));
			for(i=0;i<n;i++){
				line = bufferedReader.readLine();
				commaIndex = line.indexOf(',');
				weight = Integer.parseInt(line.substring(0,commaIndex).trim());
				profit = Integer.parseInt(line.substring(commaIndex+1).trim());
				Node temp = new Node(weight, profit,0,0);
				list.add(temp);
			}
			size = i;
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
		}
	}
	//I TOTALLY FUGGED UP ON THIS SHITTTTTOASPODNASJDPJABPSJDB
	static int bound(Node bnode){
		int weight = bnode.weight;
		int n = size;
		int i = bnode.level;
		int bound = bnode.profit;

		for(int j = bnode.level; j<n; j++)
			x.set(j,0);
		while((weight < C) && (i<=n)){
			if((weight+list.get(i).getWeight())<=C){
				x.set(i,1);
				weight += list.get(i).getWeight();
				bound += list.get(i).getProfit();
			}
			else{
				x.set(i,((C-weight)/list.get(i).getWeight()));
				weight = C;
				bound += (list.get(i).getProfit()*x.get(i));
			}
			i++;
		}
		//System.out.println(bnode + " = " + bound);
		return bound;
	}

	static void bfs(){
		Node u = new Node(0,0,0,0);
		Node v = new Node(0,0,0,0);
		int maxprofit=0;
		v.bound=bound(v);

		pQueue.add(v);
		while (pQueue.size()>0){
			v = pQueue.peek();
			System.out.println(maxprofit + " <-> " + v);
			pQueue.remove(v); //with best bound
			if (v.bound>maxprofit) {//expand v
				u.level= v.level+1;  	//u child of v
				//yes child
				u.weight=v.weight+list.get(u.level).getWeight();
				u.profit=v.profit+list.get(u.level).getProfit();
				u.ratio = u.profit/u.weight;

				if ((u.weight<=C) && (u.profit>maxprofit))
					maxprofit=u.profit;
				u.bound = bound(u);
				if (u.bound>maxprofit)
					pQueue.add(u);
				System.out.println("Yes Child = " + u);

				u.weight= v.weight; //not included
				u.profit= v.profit;
				if(u.weight!=0)
					u.ratio = u.profit/u.weight;
				else
					u.ratio = 0;
				u.bound=bound(u);
				if (u.bound>maxprofit)
					pQueue.add(u);
				System.out.println(" No Child = " + u);
			}	//node not expanded
		} //queue is empty
	}
	//---------------------------------------------

	public static void main(String[] args) {
		comparator = new NodeComparator();
		pQueue = new PriorityQueue<Node>(comparator);
		list = new ArrayList<Node>();
		x = new ArrayList<Integer>();
		readInStuff(args[0]);
		System.out.println("[weight,profit,ratio,level,bound]\ncap =" + C);
		for(int i=0;i<size;i++){
			x.add(0);
			System.out.println("-"+list.get(i));
		}
		bfs();
		//while (pQueue.size() != 0)
		//	System.out.println(pQueue.remove());
	}
}
