import java.io.File;
import java.util.List;

import Algorithms.*;


public class Compute {

	public static Info info = new Info();

	
	
	private static class ThreadRead extends Thread {
		public FileManager reader;
		public List<File> files;

		public ThreadRead(FileManager reader, String sourceDirectory){
			super();
			this.reader=reader;
			this.files=reader.listDirectories(sourceDirectory);
		}

		public void run() {
			while(true) {
				try {
					read();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		public synchronized void read() throws InterruptedException{
			for (File file: files){
				reader.parse(file, info);

				for(String key: info.getInfo().keySet()){
					System.out.println(info.getInfo().get(key));
				}
				notify();
				wait();
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
			while(true) {
				try {
					wait();
					compute();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public synchronized void compute() throws InterruptedException{

			for(String key:info.getInfo().keySet()){

				DescriptiveStats stat = new DescriptiveStats(info.getInfo().get(key));
				int mean = stat.getMean();
				density.densityX(info.getInfo().get(key),mean,info.getInfo().get(key).getLast());
				System.out.println(density.getDensities());
			}
			notify();
		}
	}


	public static void main (String[] args){
		FileManager fm = new FileManager();
		String rootDirectory = "/Users/alvaro/Documents/TUGraz/Master Thesis/TrainingSet/";
		ThreadRead threadRead = new ThreadRead(fm, rootDirectory);
		ThreadCompute threadCompute = new ThreadCompute(new Density());
		threadRead.start();
		threadCompute.start();
	}





}
