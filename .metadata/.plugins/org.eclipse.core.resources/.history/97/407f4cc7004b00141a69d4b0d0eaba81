
public class ThreadReader implements Runnable {

	public WriteRead wr;
	public String key;
	
	public ThreadReader(WriteRead wr, String key){
		this.wr=wr;
		this.key=key;
	}
	
	public void run(){
		while(wr.isControlRead()){
			try {
				wr.compute(key);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
