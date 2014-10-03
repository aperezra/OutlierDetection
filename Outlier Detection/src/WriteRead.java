import java.io.File;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import Algorithms.*;



public class WriteRead {

	public HashMap<String, Deque<Integer>> info;
	public boolean controlRead;
	public boolean needValues;
	public boolean keysExist;
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();
	public int count;

	public WriteRead(HashMap<String, Deque<Integer>> info){
		this.info=info;
		controlRead=true;
		needValues=true;
		keysExist=false;
		count=0;
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
			writer.parse(file, this.info);
			keysExist=true;
			//System.out.println("Escritura "+this.info.get("es 40_Principales"));
			try{
				Thread.sleep(100);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		finally{
			if(this.info.get("de Haiti").size()>10){
				needValues=false;
				w.unlock();
				System.out.println(rwl.isWriteLocked());
				System.out.println(rwl.getQueueLength());
			}
		}
		
	}

	public void compute(String key, Density density) throws InterruptedException{
		r.lock();
		try{
			DescriptiveStats  ds= new DescriptiveStats(info.get(key));
			density.densityX(info.get(key), ds.getMean(), info.get(key).getLast());
			System.out.println(density.getDensities());
			this.info.get(key).removeFirst();
			count++;
			if(count==info.keySet().size()-1){
				needValues=true;
			}
			try{
				Thread.sleep(100);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		finally{
			r.unlock();
			if(info.get(key).isEmpty()){
				controlRead=false;
			}
		}
	}

	public static void main (String[] args){
		WriteRead wr = new WriteRead(new HashMap<String, Deque<Integer>>());
		FileManager fm = new FileManager();
		String rootDirectory = "/Users/alvaro/Documents/TUGraz/Master Thesis/TrainingSet/";
		List<File> files = fm.listDirectories(rootDirectory);
		ThreadWriter tw= new ThreadWriter(wr, files, fm);
		Thread writer = new Thread(tw);
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
			ThreadReader tr = new ThreadReader(wr, key);
			Thread reader = new Thread(tr);
			reader.start();
		}

	}
}