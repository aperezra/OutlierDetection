import java.io.File;
import java.io.IOException;
import java.util.List;


public class Main {


	public static void main(String[] args) throws IOException{
		FileManager rf = new FileManager();
		File[] directories = rf.listDirectories("/Users/alvaro/Documents/TUGraz/Master Thesis/Prueba/");

		//Code that creates the Training Set, getting randomly 200 files from the data set.
		/*List<Integer> used = new ArrayList<Integer>();
				int i = 0;
				while(i<201){
					int index = (int) Math.floor(Math.random()*directories.length);
					if(!used.contains(index) && !directories[index].getName().contains("_")){
						used.add(index);		
						String destFile ="/Users/alvaro/Documents/TUGraz/Master Thesis/TrainingSet/"+directories[index].getName();
						rf.copyFile(directories[index], new File(destFile));
						i++;
					}
				}*/
		//listing all the directories in the dataset
		for (int i=0; i<directories.length;i++){
			if(!directories[i].getName().equals(".DS_Store")){
				rf.parse(directories[i]);
			}
		}

		for (String key: rf.getData().keySet()){
			List<Integer> data = rf.getData().get(key);
			System.out.print(key+" has these visits: ");
			for(int i:data){
				System.out.print(i+" ");
			}
			System.out.println();

			Density density =new Density();
			//Knn knn = new Knn();
			DescriptiveStats stat = new DescriptiveStats(rf.getData().get(key));
			int mean = stat.getMean();
			System.out.println(" mean: "+mean);
			System.out.println("data size: " + data.size());
			for (int i=0; i<data.size(); i++){
				//knn.knnX(data,data.get(i), i);
				density.densityX(data, mean, data.get(i), i);
				//System.out.print(knn.getDistances().get(i) +" | ");
				System.out.print(density.getDensities().get(i) + " | ");
			}
			System.out.println();
			for (int i=0; i<data.size(); i++){
				System.out.print(density.ros(i)+ " | ");
				//System.out.print(knn.ros(i) +" | ");
			}

			System.out.println();
			System.out.println();
		}

	}

}