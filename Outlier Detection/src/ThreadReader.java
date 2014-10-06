import Algorithms.*;


public class ThreadReader implements Runnable {

	public WriteRead wr;
	public String key;
	public Algorithm algorithm;
	
	public ThreadReader(WriteRead wr, String key, Algorithm algorithm){
		this.wr=wr;
		this.key=key;
		this.algorithm=algorithm;
	}
	
	public void run(){
		while(wr.isControlRead()){
			try {
				wr.compute(key, algorithm);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
