import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * The Class ReadFile.
 *
 * @author alvaro
 * @class FileReader This class read and parse from a text file and store it in a HashMap
 */
public class FileManager {


	/** The data. 
	 * @return */
	public HashMap<String, List<Integer>> data;


	/**
	 * Instantiates a new read file.
	 */
	public FileManager() {
		super();

		this.data = new HashMap<String, List<Integer>>();
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public HashMap<String, List<Integer>> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the data
	 */
	public void setData(HashMap<String, List<Integer>> data) {
		this.data = data;
	}

	/**
	 * Parses each file, storing the code and name of the wikipage as the key of the multimap, and the visits as its value.
	 *
	 * @param file the file
	 */
	public void parse(File file){
		FileReader fr = null;
		BufferedReader br = null;
		//File file = null;

		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file
			fr = new FileReader (file);
			br = new BufferedReader(fr);

			// Reading the file
			String line;
			while((line=br.readLine())!=null){
				String[] partes = line.split("\\s+");
				String key = (partes[0] +" "+ partes[1]);
				int visits = Integer.parseInt(partes[2]);

				if (!data.containsKey(key)){
					List<Integer> values = new ArrayList<Integer>();
					values.add(visits);
					data.put(key, values);
				}
				else{
					data.get(key).add(visits);
					data.put(key, data.get(key));

				}
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

	/**
	 * List directorioes.
	 *
	 * @return the file[]
	 */
	public File[] listDirectories(String sourceDirectory){
		File f = new File(sourceDirectory);
		File[] files = f.listFiles();
		return files;


	}

	public void copyFile(File sourceFile, File destFile) throws IOException { 

		if(!destFile.exists()) { 
			destFile.createNewFile(); 
		} 
		FileChannel source = null; 
		FileChannel destination = null; 
		try { 
			source = new FileInputStream(sourceFile).getChannel(); 
			destination = new FileOutputStream(destFile).getChannel(); 
			destination.transferFrom(source, 0, source.size()); 
		} 
		finally { 
			if(source != null) { 
				source.close(); 
			} 
			if(destination != null) { 
				destination.close(); 
			} 
		}
	}


}