import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;




/**
 * The Class ReadFile.
 *
 * @author alvaro
 * @class FileReader This class read and parse from a text file and store it in a HashMap
 */
public class FileManager {


	//	/** The data. 
	//	 * @return */
	//	public Info info;
	//
	//
	//	/**
	//	 * Instantiates a new read file.
	//	 */
	//	public FileManager() {
	//		super();
	//		HashMap<String, Deque<Integer>>  map= new HashMap<String, Deque<Integer>>();
	//		this.info= new Info(map);
	//	}
	//
	//	/**
	//	 * Gets the data.
	//	 *
	//	 * @return the data
	//	 */
	//	public Info getData() {
	//		return info;
	//	}
	//
	//	/**
	//	 * Sets the data.
	//	 *
	//	 * @param data the data
	//	 */
	//	public void setData(Info info) {
	//		this.info = info;
	//	}

	/**
	 * Parses each file, storing the code and name of the wikipage as the key of the multimap, and the visits as its value.
	 *
	 * @param file the file
	 */
	public void parse(File file, HashMap<String, Deque<Integer>> info){
		FileReader fr = null;
		BufferedReader br = null;
		//File file = null;
		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file
			fr = new FileReader (file);
			br = new BufferedReader(fr);
			// Reading the file
			String line;
			if(!file.getName().equals(".DS_Store") || !file.getName().contains(".")){
				while((line=br.readLine())!=null){
					String[] partes = line.split("\\s+");
					String key = (partes[0] +" "+ partes[1]);
					int visits = Integer.parseInt(partes[2]);
					if (!info.containsKey(key)){
						Deque<Integer> values = new ArrayDeque<Integer>();
						values.offerFirst(visits);
						info.put(key, values);
					}
					else{
						info.get(key).offerLast(visits);
						info.put(key, info.get(key));

					}
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
	 * List directories.
	 *
	 * @return the file[]
	 */
	public List<File> listDirectories(String sourceDirectory){
		File f = new File(sourceDirectory);
		File[] filesIni = f.listFiles();
		List<File> files = new ArrayList<File>();
		//To filter hidden files from the directory.
		for(File file: filesIni){
			if(!file.isHidden()){
				files.add(file);
			}
			
		}
		Collections.sort(files);
//		List<File> newfiles = new ArrayList<File>();
//		int j=0;
//		for(File file: files){
//			if (file.getName().equals("pagecounts-20110308-000000")){
//				j=files.indexOf(file);
//			}
//		}
//		for(int i=j; i<files.size();i++){
//			newfiles.add(files.get(i));
//		}
//		Collections.sort(newfiles);
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
