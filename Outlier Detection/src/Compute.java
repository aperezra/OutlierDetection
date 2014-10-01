import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Algorithms.*;


public class Compute {

	public static Info info = new Info();
	public static boolean controlRead = true;
	public static boolean controlCompute = true;
	public static boolean keys = false;
	public static int count=0;
	public static int numThreads=0;

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
				while(count==numThreads){
					int i=0;
					for (File file: files){
						System.out.println("vuelta: "+i);
						i++;
						reader.parse(file, info);
						keys=true;
						System.out.println(info.getInfo().get("de Brandenburger_Tor"));
						//System.out.println(info.getInfo());
						while(info.getSizeDeque()>10){
							count=0;
							info.wait();
							notifyAll();
						}
					}
				}
				controlRead=false;
			}

		}
	}

	private static class ThreadCompute extends Thread {

		public String key;
		public int id;
		public Density density;


		public ThreadCompute(String key, int id, Density density){
			super();
			this.key=key;
			this.id=id;
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
		}

		public void compute() throws InterruptedException{
			synchronized(info.getInfo().get(key)){
				if(count!=numThreads){
					info.wait();
					DescriptiveStats ds = new DescriptiveStats(info.getInfo().get(key));
					density.densityX(info.getInfo().get(key),ds.getMean(), info.getInfo().get(key).getLast());
					//				if (id==3){
					//					System.out.println(density.getDensities());
					//				}
					if(density.getDensities().size()>10){
						density.getDensities().removeFirst();
					}
					if(controlRead){
						info.getInfo().get(key).removeFirst();
						count++;
						info.notify();
						info.getInfo().get(key).wait();
					}
					if(info.isEmptyDeque(key)){
						controlCompute=false;
					}
				}
			}
		}
	}

	public static void main (String[] args) throws InterruptedException{
		double time_start = System.currentTimeMillis();
		FileManager fm = new FileManager();
		String rootDirectory = "/Users/alvaro/Documents/TUGraz/Master Thesis/TrainingSet/";
		List<File> files = fm.listDirectories(rootDirectory);
		count=numThreads;
		ThreadRead threadRead = new ThreadRead(files, fm);
		threadRead.start();
		while (!keys){
			System.out.print("");
		}
		int i=0;
		count=info.getInfo().keySet().size();
		System.out.println("La cuenta por favor " + count);
		for(String key: info.getInfo().keySet()){
			ThreadCompute tc= new ThreadCompute(key, i, new Density());
			i++;
			tc.start();
		}
		threadRead.join();
		double time_end = System.currentTimeMillis();
		System.out.println("the task has taken "+ ( time_end - time_start ) +" milliseconds");
	}
} 

