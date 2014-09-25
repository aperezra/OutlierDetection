import java.io.File;
import java.util.List;

import Algorithms.*;


public class Compute {

	public static Info info = new Info();
	public static boolean controlRead=true;
	public static boolean controlCompute=true;



	private static class ThreadRead extends Thread {
		public FileManager reader;
		public List<File> files;

		public ThreadRead(List<File> files, FileManager reader){
			super();
			this.reader=reader;
			this.files=files;

		}

		public void run() {
			while(controlRead) {
				try {
					read();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		public void read() throws InterruptedException{
			synchronized(info){
				int i=0;
				for (File file: files){
					reader.parse(file, info);
//					for(String key: info.getInfo().keySet()){
//						if(key.contains("Patch"))
//						System.out.println(i+" "+key + "  " +info.getInfo().get(key));	
//					}
					i++;
					while(info.getSizeDeque()>10){
						info.wait();
						info.notify();
					}
				}
				controlRead=false;
				System.out.println("He salido del while de tu puta madre");
			}

		}
	}

	private static class ThreadCompute extends Thread {
		public Density density;

		public ThreadCompute(Density density){
			super();
			this.density=density;
		}

		public void run() {
			while(controlCompute) {
				try {
					compute();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("He salido del while de compute");
		}

		public void compute() throws InterruptedException{
			synchronized(info){
				for(String key:info.getInfo().keySet()){
					DescriptiveStats stat = new DescriptiveStats(info.getInfo().get(key));
					int mean = stat.getMean();
					density.densityX(info.getInfo().get(key), mean,info.getInfo().get(key).getLast());
					if ((info.getInfo().get(key).size()>8)&&(density.ros()>0.9)){
						System.out.println("We've found what seems to be an outlier");
						System.out.println(density.getDensities());
						System.out.println(key + "  " +info.getInfo().get(key));	
					}
					if(density.getDensities().size()>10){
					density.getDensities().removeLast();
					}
					info.getInfo().get(key).removeFirst();
				}
				if(controlRead){
					info.notify();
					info.wait();
				}
				if(info.isEmptyDeque()){
					controlCompute=false;
				}
			}
		}
	}

	public static void main (String[] args) throws InterruptedException{
		FileManager fm = new FileManager();
		String rootDirectory = "/Users/alvaro/Documents/TUGraz/Master Thesis/TrainingSet/";
		List<File> files = fm.listDirectories(rootDirectory);
		ThreadRead threadRead = new ThreadRead(files, fm);
		ThreadCompute threadCompute = new ThreadCompute(new Density());
		threadRead.start();
		threadCompute.start();
	}
} 

