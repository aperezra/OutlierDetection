import java.io.File;
import java.util.List;


public class ThreadWriter implements Runnable{

	public WriteRead wr;
	public List<File> files; //All the files I want to parse.
	public FileManager fm;
	
	public ThreadWriter(WriteRead wr, List<File> files, FileManager fm){
		this.wr=wr;
		this.files=files;
		this.fm=fm;
	}
	
	public void run(){
		for(File file: files){
			try {
				wr.write(file,fm);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
