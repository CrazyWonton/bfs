import java.io.*;

public class BestFirstSearch{
static int n = 0;
static int C = 0;

	void readInStuff(string filename) {
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			line = bufferedReader.readLine();
			n = line.parseInt();
			int commaIndex = line.indexOf(',');
			C = line.substring(commaIndex).parseInt();
			
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void main(String[] args) {
		readInStuff(args[1]);
		System.out.println("Hello world");
	}
}
