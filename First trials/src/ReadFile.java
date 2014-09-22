
import java.io.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;



/**
 * @author Alvaro Pérez Ramón
 * @version 1.0 (frist trials)
 * Class that parse the files from wikistats
 */
public class ReadFile {
	
	public Multimap<Integer, Integer> map;
	
	
	public ReadFile(Multimap<Integer, Integer> map) {
		super();
		this.map = map;
	}

	private void parse(String pathToFile){
		

		FileReader fr = null;
		BufferedReader br = null;
		File file = null;

		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file
			file = new File(pathToFile);
			fr = new FileReader (file);
			br = new BufferedReader(fr);
			
			// Reading the file
			String line;
			while((line=br.readLine())!=null){
				String[] partes = line.split("\\s+");
				int key = (partes[0] + partes[1]).hashCode();
				int visits = Integer.parseInt(partes[2]);
				map.put(key, visits);
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			// Closing the file, just to ensure that it is closed either there are exceptions or not.
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}

	}

	public void compare(String pathToFile){
	
		FileReader fr = null;
		BufferedReader br = null;
		File file = null;


		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file
			file = new File(pathToFile);
			fr = new FileReader (file);
			br = new BufferedReader(fr);

			// Reading the file
			String line;
			while((line=br.readLine())!=null){
				String[] partes = line.split("\\s+");
				int key = (partes[0] + partes[1]).hashCode();
				int visits = Integer.parseInt(partes[2]);
				
				this.map.put(key,visits);

			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			// Closing the file, just to ensure that it is closed either there are exceptions or not.
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}

	}

	public static void main(String [] arg) {
		Multimap<Integer, Integer> map = ArrayListMultimap.create();

		ReadFile trial = new ReadFile(map);
		trial.parse("/Users/alvaro/Documents/TUGraz/Master Thesis/Develope/First trials/src/PRUEBA.txt");

		trial.compare("/Users/alvaro/Documents/TUGraz/Master Thesis/Develope/First trials/src/PRUEBA2.txt");
		
		trial.compare("/Users/alvaro/Documents/TUGraz/Master Thesis/Develope/First trials/src/PRUEBA3.txt");

		System.out.println(map.get(1517100165));
		System.out.println(map.get(-9616714));
		System.out.println(map.get(-214544956));
		System.out.println(map.get(29207801));


	}

}








    