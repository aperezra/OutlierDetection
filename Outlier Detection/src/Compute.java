import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
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
	final static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private static class ThreadWrite extends Thread {
		public FileManager reader;
		public List<File> files;

		public ThreadWrite(List<File> files, FileManager reader){
			super();
			this.reader=reader;
			this.files=files;
		}

		public void run() {
			while(controlRead) {
				try {
					write();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void write() throws InterruptedException{
			int i=0;
			for (File file: files){
				if(!rwl.isWriteLocked()){
					System.out.println("Cojo el cerrojo que escribe");
					rwl.writeLock().lock();
				}
				System.out.println("vuelta: "+i);
				i++;
				reader.parse(file, info);
				keys=true;
				System.out.println(info.getInfo().get("de Brandenburger_Tor"));
				while(info.getSizeDeque()>10){
//					System.out.println("Suelto el cerrojo que escribe");
					if(rwl.isWriteLocked()){
						System.out.println("Suelto el cerrojo que escribe");
						rwl.writeLock().unlock();
					}
				}
			}
			controlRead=false;
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
			//			System.out.println("Aqui estamos "+ id);
			//			DescriptiveStats ds = new DescriptiveStats(info.getInfo().get(key));
			//			density.densityX(info.getInfo().get(key),ds.getMean(), info.getInfo().get(key).getLast());
			//			if(density.getDensities().size()>10){
			//				density.getDensities().removeFirst();
			//			}
			rwl.readLock().lock();
			System.out.println("Cojo el cerrojo que lee");
			info.getInfo().get(key).removeFirst();
			if(controlRead ){
				if (key.equals("de Brandenburger_Tor")){
					System.out.println("saco el valor: "+info.getInfo().get(key).removeFirst());
				}
				System.out.println("Suelto el cerrojo que lee");
				rwl.readLock().unlock();
			}
			if(info.isEmptyDeque(key)){
				controlCompute=false;
			}
		}

	}

	public static void main (String[] args) throws InterruptedException{
		FileManager fm = new FileManager();
		String rootDirectory = "/Users/alvaro/Documents/TUGraz/Master Thesis/TrainingSet/";
		List<File> files = fm.listDirectories(rootDirectory);
		ThreadWrite threadWrite = new ThreadWrite(files, fm);
		threadWrite.start();
		while (!keys){
			System.out.print("");
		}
		int i=0;
		ThreadCompute tc = new ThreadCompute ("de Brandenburger_Tor", 1, new Density());
		tc.start();
		//		List<Thread> threads = new ArrayList<Thread>();
		//		for(String key: info.getInfo().keySet()){
		//			ThreadCompute tc= new ThreadCompute(key, i, new Density());
		//			threads.add(tc);
		//			i++;
		//			tc.start();
		//		}
		threadWrite.join();
		//		for(Thread t:threads){
		//			t.join();
		//		}
	}
} 

