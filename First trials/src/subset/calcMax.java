package subset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
// English 800 y 1000
// Spanish 400 y 600
// German 400 y 600
public class calcMax {

	
	private static String[][] maxesp = new String[33][2]; 
	private static String[][] maxde = new String[33][2]; 
	private static String[][] maxen = new String[33][2];
	private static Multimap<String, Integer> result;
	
	public calcMax(){
		result = ArrayListMultimap.create();
		for (int i=0; i<33; i++){
			maxesp[i][0] = "default";
			maxesp[i][1] = "0";
			maxde[i][0] = "default";
			maxde[i][1] = "0";
			maxen[i][0] = "default";
			maxen[i][1] = "0";
		}
	}
	
	public String[][] maximo(File file){

		String[][] max = new String[33][2];
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file

			fr = new FileReader (file);
			br = new BufferedReader(fr);

			// Reading the file
			String line;

			while(((line=br.readLine())!=null)){
				String[] partes = line.split("\\s+");
			//	max=calcMaximum(maxen, partes, calcMinimum(maxen));
				max=calcMaximum(maxde, partes, calcMinimum(maxde));
			//	max=calcMaximum(maxesp, partes,  calcMinimum(maxesp));

			}
		}
		catch(Exception e1){
			e1.printStackTrace();
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
/*		for (int i=0;i<maxesp.length;i++){
			for (int j=0; j<maxtotal[i].length;j++){
				maxtotal[i][j]=maxesp[i][j];
			}
		}

		for (int i=maxesp.length;i<(2*maxesp.length);i++){
			for (int j=0; j<maxtotal[i].length;j++){
				maxtotal[i][j]=maxen[i-(maxesp.length)][j];
			}
		}
		for (int i=(2*maxesp.length);i<maxtotal.length;i++){
			for (int j=0; j<maxtotal[i].length;j++){
				maxtotal[i][j]=maxde[i-(2*maxesp.length)][j];
			}
		}*/
		return max;
	}


/*	public String[][] max(File file, String code) throws Exception{
		if(code.hashCode()!="es".hashCode()&&code.hashCode()!="de".hashCode()&&code.hashCode()!="en".hashCode()){
			throw new Exception("The code has to be either: es, en or de");
		}
		String[][] max = new String[33][4];
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file

			fr = new FileReader (file);
			br = new BufferedReader(fr);

			// Reading the file
			String line;

			while(((line=br.readLine())!=null)){
				String[] partes = line.split("\\s+");
				if(code.equals("en")){
					//System.out.println("English");
				max=calcMaximum(maxen, partes, "en", calcMinimum(maxen));
				}
				else{
					if(code.equals("de")){
						//System.out.println("German");
						max=calcMaximum(maxde, partes, "de", calcMinimum(maxde));
					}
					else{
						max=calcMaximum(maxesp, partes, "es", calcMinimum(maxesp));
						
					}
				}

			}
		}
		catch(Exception e1){
			e1.printStackTrace();
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
		for (int i=0;i<maxesp.length;i++){
			for (int j=0; j<maxtotal[i].length;j++){
				maxtotal[i][j]=maxesp[i][j];
			}
		}

		for (int i=maxesp.length;i<(2*maxesp.length);i++){
			for (int j=0; j<maxtotal[i].length;j++){
				maxtotal[i][j]=maxen[i-(maxesp.length)][j];
			}
		}
		for (int i=(2*maxesp.length);i<maxtotal.length;i++){
			for (int j=0; j<maxtotal[i].length;j++){
				maxtotal[i][j]=maxde[i-(2*maxesp.length)][j];
			}
		}
		return max;
	}
*/
	
	
	private static File[] listarDirectorios(){
		String sDirectorio = "/Users/alvaro/Documents/TUGraz/Master Thesis/PruebasMax/";
		File f = new File(sDirectorio);
		File[] ficheros = f.listFiles();
		return ficheros;	
	}

	private String[][] calcMaximum(String[][] array, String[] partes, int index){

//		if (partes[0].hashCode()==code.hashCode() ){
			int visits = Integer.parseInt(partes[1]);
			for (int i=0; i<array.length; i++){   
				if((visits>Integer.parseInt(array[i][1])) && (!array[i][0].equals(partes[0]))){
					for (int x=0; x<array[i].length; x++){
						array[index][x]=partes[x];
					}
					break;	
				}
			}
	//	}
	return array;
	}
	
	private String[][] calcMedia (String code, File file) throws Exception{
		if (!code.equals("es")&&!code.equals("en")&&!code.equals("de")){
			throw new Exception ("The code has to be either: en, de or es");
		}
		String[][] media = new String[33][4];
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file

			fr = new FileReader (file);
			br = new BufferedReader(fr);


			// Reading the file
			String line;
			int k=0;
			int n=0;
			int m=0;

			while(((line=br.readLine())!=null)){
				String[] partes = line.split("\\s+");
				int visits = Integer.parseInt(partes[2]);
				if (code.equals("es")){
					if(partes[0].equals("es")&& 400<visits && visits<600 && k<media.length){
						for(int j=0; j<media[k].length; j++){
							media[k][j]=partes[j];
						}			
						k++;
					}	
				}
				else{
					if (code.equals("en")){
						if(partes[0].equals("en")&& 800<visits && visits<1000 && n<media.length){
							for(int j=0; j<media[n].length; j++){
								media[n][j]=partes[j];
							}			
							n++;
						}	


					}else{
						if (code.equals("de")){
							if(partes[0].equals("de")&& 400<visits && visits<600 && m<media.length){
								for(int j=0; j<media[m].length; j++){
									media[m][j]=partes[j];
								}			
								m++;
							}
						}
					}

				}
			}
		}
		catch(Exception e1){
			e1.printStackTrace();
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
		return media;
	}
	
	private int calcMinimum(String[][] array){
		int index=0;
		int min = Integer.parseInt(array[0][1]);
		for (int i=1; i<array.length;i++){
			if(Integer.parseInt(array[i][1])<min){
				min=Integer.parseInt(array[i][1]);
				index=i;				
			}
		}	
		return index;
	}

	private void finalArray(String[][] array){
		for (int i=0; i<array.length; i++){
			for(int j = 0; j<array[i].length; j++){
				if(array[i][1]!=null){
					String key = array[i][1];
					int visits = Integer.parseInt(array[i][2]);
					result.put(key,visits);
				}
			}
		}
		
	}
	
	private Multimap<String, Integer> meanCalc(){
		Multimap<String, Integer> mean = ArrayListMultimap.create();
		for (String key : result.keys()) {
			int visits = 0;
			Object[] val = result.get(key).toArray();
			for (int i=0; i<val.length; i++){
				visits+=(Integer)val[i];
			}
			visits=visits/val.length;
			if(!mean.containsKey(key)){
				mean.put(key, visits);
			}
			
		}

		
		
		return mean;

	}
	
	public void writeFileMultimap(FileWriter file, Multimap<String, Integer> mean){
		PrintWriter pw = null;
		try
		{
			pw = new PrintWriter(file);

			for (String key : mean.keys()) {
				pw.print(key + " " + mean.get(key) + " ");
				pw.println("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para 
				// asegurarnos que se cierra el fichero.
				if (null != file)
					file.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void writeFileArray(FileWriter file, String[][] mean){
		PrintWriter pw = null;
		try
		{
			pw = new PrintWriter(file);

			for (int i=0; i<mean.length;i++) {
				pw.print(mean[i][0] + " " + mean[i][1] + " ");
				pw.println("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para 
				// asegurarnos que se cierra el fichero.
				if (null != file)
					file.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public Multimap<String, Integer> readFile (File file){
		FileReader fr = null;
		BufferedReader br = null;
		Multimap<String, Integer> multimap= ArrayListMultimap.create();
		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file

			fr = new FileReader (file);
			br = new BufferedReader(fr);

			// Reading the file
			String line;

			while(((line=br.readLine())!=null)){
				String[] partes = line.split("\\s+");
				multimap.put(partes[0], Integer.parseInt(partes[1]));
				

			}
		}
		catch(Exception e1){
			e1.printStackTrace();
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
		return multimap;
		
	}
	
	public String[][] gallina(File file, Multimap<String, Integer> multimap, String code){
		String[][] gallina = new String[33][2];
		FileReader fr = null;
		BufferedReader br = null;
		int k = 0;
		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file

			fr = new FileReader (file);
			br = new BufferedReader(fr);

			// Reading the file
			String line;

			while(((line=br.readLine())!=null)){
				String[] partes = line.split("\\s+");
				if (partes[0].equals(code) && !multimap.containsKey(partes[1]) && k<gallina.length && !partes[1].contains("%")&& !partes[1].contains("(")&& !partes[1].contains("$")&&!partes[1].contains("1")&&!partes[1].contains("2")&&!partes[1].contains("3")&&!partes[1].contains("7")&&!partes[1].contains("8")&&!partes[1].contains("9")&& !partes[1].contains(".")&&!partes[1].contains("-")&&!partes[1].contains("/")){
					gallina[k][0]=partes[1]; gallina[k][1]=partes[2];
					k++;
					continue;
				}

			}
		}
		catch(Exception e1){
			e1.printStackTrace();
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
		for (int i=0; i<gallina.length; i++){
			System.out.println(gallina[i][0] + "  " +gallina[i][1]);
		}
		return gallina;
	}

	public static void main(String[] args) throws Exception{
		calcMax calcMax= new calcMax();
/*		File file = new File("/Users/alvaro/Documents/TUGraz/Master Thesis/Dataset sample/pagecounts-20110101-140000");
		String [][] result= calcMax.calcMedia("es", file);

		
		for(int i=0; i<result.length;i++){
			for(int j = 0; j<result[i].length; j++){
				System.out.print(result[i][j] + " ");
			}
			System.out.println("");	
		}*/
//		File[] directorios = listarDirectorios();
//		for (int i=0; i<directorios.length;i++){
//			if(!listarDirectorios()[i].getName().equals(".DS_Store")){
//				calcMax.finalArray(calcMax.calcMedia("de",directorios[i]));
//			}
//		}
	/*	File file = new File("/Users/alvaro/Documents/TUGraz/Master Thesis/aqui/multimapDe");
		String[][] impr = calcMax.maximo(file );
		for (int i=0; i<impr.length; i++){
			System.out.println(impr[i][0] + " " + impr[i][1]);
			
		}
*/	//	calcMax.meanCalc();
		File file1 = new File("/Users/alvaro/Documents/TUGraz/Master Thesis/Dataset sample/pagecounts-20110101-140000");
		FileWriter filewr = new FileWriter("/Users/alvaro/Documents/TUGraz/Master Thesis/aqui/multimapDeMinimo");
//		calcMax.writeFileMultimap(filewr,calcMax.meanCalc());
		File file = new File("/Users/alvaro/Documents/TUGraz/Master Thesis/aqui/multimapDeFin");
		calcMax.writeFileArray(filewr, calcMax.gallina(file1,calcMax.readFile(file), "de"));

	}

}