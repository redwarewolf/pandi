import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Pandi {

	public static Map<String, String> readFile (File file) throws IOException {       
	   final String delimiter = ",";

		FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String[] tempArr;
        Map<String, String> dictionary = new HashMap<String, String>();

        while((line = br.readLine()) != null) {
           tempArr = line.split(delimiter);
 	      dictionary.put(tempArr[0], tempArr[1]);
           System.out.print(tempArr[0]);
           System.out.print(";");
           System.out.println(tempArr[1]);
           System.out.println("\n");
        }
    return dictionary;
	}
}