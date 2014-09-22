package subset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;




/**
 * @author alvaro
 *
 */
public class Subset {




	public Multimap<String, Integer> subsetEs;
	public Multimap<String, Integer> subsetDe;
	public Multimap<String, Integer> subsetEn;
//	public String[][] subsetTotal;





	public Subset() {
		super();
		this.subsetEs = ArrayListMultimap.create();
		this.subsetDe = ArrayListMultimap.create();
		this.subsetEn = ArrayListMultimap.create();
		//this.subsetTotal = subsetTotal;
	}

	public void initializeSubsets(File file, String code) throws Exception{
		if(!code.equals("es")&&!code.equals("en")&&!code.equals("de")){
			throw new Exception("The code has to be either: en, de or es");
		}
		int k = 0;
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
				if (code.equals("es")){
					subsetEs.put(partes[0], Integer.parseInt(partes[1]));
				}
				if(code.equals("en")){
						subsetEn.put(partes[0], Integer.parseInt(partes[1]));
//						for (int i=0; i<subsetEn[k].length; i++){
//							subsetEn[k][i]=partes[i];	
//						}
//						k++;
					
				}	
				if (code.equals("de")){
					subsetDe.put(partes[0], Integer.parseInt(partes[1]));
//							for (int i=0; i<subsetDe[k].length; i++){
//								subsetDe[k][i]=partes[i];
//							}
//							k++;
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



	}

/*	public void initializeSubsetTotal(){
		for(int i=0; i<subsetEs.length; i++){
			subsetTotal[i][0]="es";
			subsetTotal[i][1]=subsetEs[i][0];
			subsetTotal[i][2]=subsetEs[i][1];

		}
		for(int i=subsetEs.length; i<2*subsetEn.length; i++){
			subsetTotal[i][0]="en";
			subsetTotal[i][1]=subsetEn[i][0];
			subsetTotal[i][2]=subsetEn[i][1];
		}
		for(int i=2*subsetEn.length; i<subsetTotal.length; i++){
			subsetTotal[i][0]="de";
			subsetTotal[i][1]=subsetDe[i][0];
			subsetTotal[i][2]=subsetDe[i][1];
		}

	}
*/
	public String[][] createSubsetFile(File file){
		String[][] subset = new String[99*3][4];
		Multimap<String,Integer> multisubsetEs = ArrayListMultimap.create();
		Multimap<String,Integer> multisubsetEn = ArrayListMultimap.create();
		Multimap<String,Integer> multisubsetDe = ArrayListMultimap.create();
		FileReader fr = null;
		BufferedReader br = null;
		int k=0;
		try {
			// Opening the file and creating a BufferedReader that allows to parse all the text included in the file
			fr = new FileReader (file);
			br = new BufferedReader(fr);

			// Reading the file
			String line;
			String code;
			while(((line=br.readLine())!=null)){
				String[] partes = line.split("\\s+");
				code = partes[0];
				if (code.equals("es") && k<subset.length){
					if(subsetEs.containsKey(partes[1])){
						for (int j=0; j<partes.length;j++ ){
							subset[k][j]=partes[j];
							multisubsetEs.put(partes[1], Integer.parseInt(partes[2]));
						}
						k++;
					}
				}
				if(code.equals("en") && k<subset.length){
						if(subsetEn.containsKey(partes[1])){
							for (int j=0; j<partes.length;j++ ){
								subset[k][j]=partes[j];
								multisubsetEn.put(partes[1], Integer.parseInt(partes[2]));
							}
							k++;		
					    }
				}	
				if (code.equals("de") && k<subset.length){
							if(subsetDe.containsKey(partes[1])){
								for (int j=0; j<partes.length;j++ ){
									subset[k][j]=partes[j];
									multisubsetDe.put(partes[1], Integer.parseInt(partes[2]));
								}
								k++;
							}
						}
			}
			if(k!=subset.length){
				for (String key : subsetEs.keys()) {
					if(!multisubsetEs.containsKey(key)){
						subset[k][0]="es";
						subset[k][1]=key;
						subset[k][2]="0";
						subset[k][3]="0";
						k++;
					}
				}
				for (String key : subsetEn.keys()) {
					if(!multisubsetEn.containsKey(key)){
						subset[k][0]="en";
						subset[k][1]=key;
						subset[k][2]="0";
						subset[k][3]="0";
						k++;
					}
				}
				for (String key : subsetDe.keys()) {
					if(!multisubsetDe.containsKey(key)){
						subset[k][0]="de";
						subset[k][1]=key;
						subset[k][2]="0";
						subset[k][3]="0";
						k++;
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
		return subset;



	}


	private File[] listarDirectorios(){
		String sDirectorio = "/Volumes/My Passport/wikistats";
		File f = new File(sDirectorio);
		File[] ficheros = f.listFiles();
		return ficheros;


	}

	public void writeFile(String[][] subset, FileWriter file){
		{

			PrintWriter pw = null;
			try
			{
				pw = new PrintWriter(file);

				for (int i = 0; i < subset.length; i++){
					for (int j=0; j<subset[i].length;j++){
						pw.print(subset[i][j]+ " ");
					}
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

	}

	public static void main(String [] arg) throws Exception {
		Subset subset = new Subset( );
		File fileEs = new File ("/Users/alvaro/Documents/TUGraz/Master Thesis/aqui/multimapEsFin");
		File fileEn = new File ("/Users/alvaro/Documents/TUGraz/Master Thesis/aqui/multimapEnFin");
		File fileDe = new File ("/Users/alvaro/Documents/TUGraz/Master Thesis/aqui/multimapDeFin");
		subset.initializeSubsets(fileEs, "es");
		subset.initializeSubsets(fileEn, "en");
		subset.initializeSubsets(fileDe, "de");
		File[] directorios = subset.listarDirectorios();
		for (int i=0; i<directorios.length;i++){
			if(!directorios[i].getName().equals(".DS_Store")){
				FileWriter filewr = new FileWriter("/Volumes/My Passport/subset/"+directorios[i].getName());
				subset.writeFile(subset.createSubsetFile(directorios[i]), filewr);

			}
		}
	}


	

}



