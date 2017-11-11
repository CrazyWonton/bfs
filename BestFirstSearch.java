import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch{
	public static class Pair{
		int weight;
		int cost;
		int ratio;

		public Pair(int w, int c){
			weight = w;
			cost = c;
			ratio = c/w;
		}

		public String toString(){
			return "[" + weight + ", " + cost + "] = " + ratio;
		}
	}

	public static class PairComparator implements Comparator<Pair>{
    @Override
    public int compare(Pair x, Pair y){
        if (x.ratio > y.ratio)
            return -1;
        if (x.ratio < y.ratio)
            return 1;
        return 0;
    }
	}


	static int n = 0;
	static int C = 0;
	static Comparator<Pair> comparator;
	static PriorityQueue<Pair> pQueue;

	static void readInStuff(String filename) {
		String line = null;
			int commaIndex = 0;
			int cost = 0;
			int weight = 0;
			try {
				FileReader fileReader = new FileReader(filename);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				line = bufferedReader.readLine();
				commaIndex = line.indexOf(',');
				n = Integer.parseInt(line.substring(0,commaIndex).trim());
				C = Integer.parseInt(line.substring(commaIndex+1).trim());
				for(int i=0;i<n;i++){
					line = bufferedReader.readLine();
					commaIndex = line.indexOf(',');
					weight = Integer.parseInt(line.substring(0,commaIndex).trim());
					cost = Integer.parseInt(line.substring(commaIndex+1).trim());
					Pair temp = new Pair(weight, cost);
					pQueue.add(temp);
				}
				bufferedReader.close();
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open file '" + filename + "'");
			} catch (IOException ex) {
				System.out.println("Error reading file '" + filename + "'");
			}
		}

		public static void main(String[] args) {
			comparator = new PairComparator();
			pQueue = new PriorityQueue<Pair>(comparator);
			readInStuff(args[0]);
			while (pQueue.size() != 0)
	      System.out.println(pQueue.remove());
		}
}
