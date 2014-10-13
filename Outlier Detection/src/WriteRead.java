
import java.io.File;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
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
	public int numOutliers;
	public String nameFile;
	
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
			keysExist=true;
			try{
				Thread.sleep(500);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		finally{
			if(this.info.get("de !_!").size()>winsize){
				needValues=false;
				w.unlock();
			}
		}
	}
	public void compute(String key, Algorithm algorithm) throws Exception{
		r.lock();
		try{
			algorithm.calculate(info.get(key));
			if (key.equals("de Hauptseite") && algorithm.probOutlier()>2){
				numOutliers++;
				System.out.println(numOutliers + " "+nameFile);
				//System.out.println(algorithm.toString());
				//System.out.println(info.get(key));
			}
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
		WriteRead wr = new WriteRead(new HashMap<String, Deque<Integer>>(), 15);
		FileManager fm = new FileManager();
		String rootDirectory = "/Volumes/My Passport/subset/";
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
			ThreadReader tr = new ThreadReader(wr, key, new Statistic());
			Thread reader = new Thread(tr);
			reader.setName("Reader: "+key);
			reader.start();
		}
	}
}


