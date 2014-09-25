import java.util.Deque;
import java.util.HashMap;



public class Info {

	public HashMap<String, Deque<Integer>> info;

	public Info() {
		super();
		this.info = new HashMap<String, Deque<Integer>>();
	}

	public HashMap<String, Deque<Integer>> getInfo() {
		return info;
	}

	public void setInfo(HashMap<String, Deque<Integer>> info) {
		this.info = info;
	} 
	
	public int getSizeDeque(){
		for (String key: info.keySet()){
			return info.get(key).size();
		}
		return 0;
	}
	public boolean isEmptyDeque(){
		for (String key: info.keySet()){
			return info.get(key).isEmpty();
		}
		return true;
	}
	
}
