
import java.io.File;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import Algorithms.*;
public class WriteRead {

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();
	public HashMap<String, Deque<Integer>> info;
	public boolean controlRead;
	public boolean needValues;
	public boolean keysExist;
	public int count;
	public int winsize;
	public int numOutliersA;
	public int numOutliersB;
	public int numOutliersC;
	public String nameFile;
	static int i =0;

	public WriteRead(HashMap<String, Deque<Integer>> info, int winsize){
		this.info=info;
		controlRead=true;
		needValues=true;
		keysExist=false;
		count=0;
		this.winsize=winsize;
		this.nameFile="";
	}
	public HashMap<String, Deque<Integer>> getInfo() {
		return this.info;
	}
	public boolean isControlRead() {
		return controlRead;
	}
	public boolean isNeedValues() {
		return needValues;
	}
	public boolean isKeysExist() {
		return keysExist;
	}

	public void write(File file, FileManager writer) throws InterruptedException{
		if(needValues && !rwl.isWriteLocked()){
			w.tryLock();
		}
		try{
			nameFile=file.getName();
			writer.parse(file, this.info);
			System.out.println(i++ + " es Lionel_Messi " +  info.get("es Lionel_Messi"));
			System.out.println("de Lionel_Messi " +  info.get("de Lionel_Messi"));
			System.out.println("en Lionel_Messil " +  info.get("en Lionel_Messi"));
			keysExist=true;
			try{
				Thread.sleep(500);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		finally{
			if(this.info.get("de Ballon_d%E2%80%99Or").size()>winsize){
				needValues=false;
				w.unlock();
			}
		}
	}

	public void compute(String key, Algorithm algorithm) throws Exception{
		r.lock();
		try{
			algorithm.calculate(info.get(key));
			if (key.equals("es Lionel_Messii")&& Double.compare(algorithm.probOutlier(),1.9)>0 ){
				numOutliersA++;
				System.out.println(numOutliersA + " "+nameFile + " "+ key + "\n" + " outlier: " +algorithm.toString() + "\n" + info.get(key) + "\n" + "FIN" );
//				System.out.println("outlier: "+algorithm.toString());
//				System.out.println(info.get(key));
//				System.out.println("¡YA!");
			}
			if (key.equals("de Lionel_Messi")&& Double.compare(algorithm.probOutlier(),1.9)>0 ){
				numOutliersB++;
				System.out.println(numOutliersA + " "+nameFile + " "+ key + "\n" + " outlier: " +algorithm.toString() + "\n" + info.get(key) + "\n" + "FIN" );
//				System.out.println("outlier: "+algorithm.toString());
//				System.out.println(info.get(key));
//				System.out.println("¡YA!");
			}
			if (key.equals("en Lionel_Messi")&& Double.compare(algorithm.probOutlier(),1.9)>0){
				numOutliersC++;
				System.out.println(numOutliersA + " "+nameFile + " "+ key + "\n" + " outlier: " +algorithm.toString() + "\n" + info.get(key) + "\n" + "FIN" );
//				System.out.println("outlier: "+algorithm.toString());
//				System.out.println(info.get(key));
//				System.out.println("¡YA!");
			}
//			if (key.equals("es Fukushima")&& Double.compare(algorithm.probOutlier(),3)>0 ){
//				numOutliersA++;
//				System.out.println(numOutliersA + " "+nameFile + " "+ key + "\n" + " outlier: " +algorithm.toString() + "\n" + info.get(key) + "\n" + "FIN" );
////				System.out.println("outlier: "+algorithm.toString());
////				System.out.println(info.get(key));
////				System.out.println("¡YA!");
//			}
//			if (key.equals("en Fukushima")&& Double.compare(algorithm.probOutlier(),3)>0){
//				numOutliersC++;
//				System.out.println(numOutliersA + " "+nameFile + " "+ key + "\n" + " outlier: " +algorithm.toString() + "\n" + info.get(key) + "\n" + "FIN" );
////				System.out.println("outlier: "+algorithm.toString());
////				System.out.println(info.get(key));
////				System.out.println("¡YA!");
//			}
//			if (key.equals("de Fukushima")&& Double.compare(algorithm.probOutlier(),3)>0 ){
//				numOutliersA++;
//				System.out.println(numOutliersA + " "+nameFile + " "+ key + "\n" + " outlier: " +algorithm.toString() + "\n" + info.get(key) + "\n" + "FIN" );
////				System.out.println("outlier: "+algorithm.toString());
////				System.out.println(info.get(key));
////				System.out.println("¡YA!");
//			}
			info.get(key).removeFirst();
			count++;
			if(count==info.keySet().size()-1){
				needValues=true;
			}
			try{
				Thread.sleep(500);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		finally{
			r.unlock();
			if(info.get(key).size()<winsize-1){
				controlRead=false;
			}
		}
	}
	public static void main (String[] args){
		WriteRead wr = new WriteRead(new HashMap<String, Deque<Integer>>(),10);
		FileManager fm = new FileManager();
		String rootDirectory = "/Users/alvaro/Documents/TUGraz/Master Thesis/subsets/trendSubset";
		List<File> files = fm.listDirectories(rootDirectory);
		while(files.isEmpty()){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ThreadWriter tw= new ThreadWriter(wr, files, fm);
		Thread writer = new Thread(tw);
		writer.setName("Writer");
		writer.start();
		while(!wr.isKeysExist()){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(String key: wr.getInfo().keySet()){
			ThreadReader tr = new ThreadReader(wr, key, new Hull());
			Thread reader = new Thread(tr);
			reader.setName("Reader: "+key);
			reader.start();
		}
	}
}


