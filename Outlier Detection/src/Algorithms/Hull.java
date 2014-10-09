package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class Hull.
 */
public class Hull implements Algorithm{

	/** The hulls. */
	public List<Integer> hulls;
	public int value;
	public double sigma;

	/**
	 * Instantiates a new hull.
	 */
	public Hull(){
		super();
		this.hulls=new ArrayList<Integer>();
		this.value=0;
		this.sigma=0;
	
	}


	/**
	 * Gets the hulls.
	 *
	 * @return the hulls
	 */
	public List<Integer> getHulls() {
		return hulls;
	}

	@Override
	public int getSize(){
		return hulls.size();
	}

	@Override
	public void deleteFirst(){
		hulls.remove(0);
	}

	@Override
	public double probOutlier() {
		double size=hulls.size();
		int index=hulls.indexOf(value);
		if(index==0 && hulls.get(index+1)>hulls.get(index)+sigma) return 1.5;
		if(index==size-1 && hulls.get(index-1)<hulls.get(index)-sigma) return 1.5;
		return 0.0;
	}

	@Override
	public void calculate(Deque<Integer> data){
		DescriptiveStats ds = new DescriptiveStats(data);
		this.sigma = ds.getStandardDev(ds.getMean());
		this.value = data.getLast();
		hulls.clear();
		hulls.addAll(data);
		Collections.sort(hulls);
	}

	@Override
	public String toString(){
		return this.hulls.toString() + " " + value;

	}




}
