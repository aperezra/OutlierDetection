import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Hull implements Algorithm{

	public List<Integer> hulls;

	public Hull(){
		super();
		this.hulls=new ArrayList<Integer>();
	}

	@Override
	public int distance(int reference, int val) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override	
	public double ros(int numHull) {
		return numHull;

	}

	/**
	 * Hull. Sort the list from lower to higher value so its possible to show the outermost layers.
	 *
	 * @param data the list with the visits
	 * @return the sorted list from lower to higher value
	 */
	public void hull(int val, List<Integer> data){
		if(hulls.isEmpty()){
			hulls = new ArrayList<Integer>(data);
		}
		else{
			hulls.add(val);
		}
		Collections.sort(hulls);
	}



}
