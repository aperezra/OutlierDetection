//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import Algorithms.*;
//
//
//public class Compute {
//
//	public static Info info = new Info();
//	public static boolean controlRead = true;
//	public static boolean controlCompute = true;
//	public static boolean keys = false;
//	final static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
//	private static Lock r = rwl.readLock();
//	private static Lock w = rwl.writeLock();
//
//	private static class ThreadWrite extends Thread {
//		public FileManager reader; //A class in where there is a method to parse a file
//
//		public ThreadWrite(List<File> files, FileManager reader){
//			super();
//			this.reader=reader;
//			this.files=files;
//		}
//
//		public void run() {
//			while(controlRead) {
//				try {
//					write();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//		public void write() throws InterruptedException{
//			for (File file: files){
//				w.tryLock();
//				reader.parse(file, info);
//				System.out.println(info.getInfo().get("de Haiti"));
//				while(info.getSizeDeque()>10){
//					w.unlock();
//				}
//			}
//			controlRead=false; //When all the files have been parsed I want this thread to end.
//		}
//	}
//
//	private static class ThreadCompute extends Thread {
//
//		public String key;
//		public Density density; //Algorithm that I want to apply to the integers in the Deques.
//
//
//		public ThreadCompute(String key, Density density){
//			super();
//			this.key=key;
//			this.density=density;
//
//		}
//
//		public void run() {
//			while(controlCompute) {
//				try {
//					compute();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//		public void compute() throws InterruptedException{
//			r.lock();
//			
//			info.getInfo().get(key).removeFirst(); //I take out the first element, expecting that the other thread fills with a new value the deque
//			r.unlock();
//			if(info.isEmptyDeque(key)){
//				controlCompute=false;
//			}
//		}
//
//	}
//
////	public static void main (String[] args) throws InterruptedException{
////		FileManager fm = new FileManager();
////		String rootDirectory = "/Users/alvaro/Documents/TUGraz/Master Thesis/TrainingSet/";
////		List<File> files = fm.listDirectories(rootDirectory);
////		ThreadWrite threadWrite = new ThreadWrite(files, fm);
////		threadWrite.start();
////		List<Thread> threads = new ArrayList<Thread>();
////		for(String key: info.getInfo().keySet()){
////			ThreadCompute tc= new ThreadCompute(key, new Density());
////			threads.add(tc);
////			tc.start();
////		}
////	}
//} 
//
