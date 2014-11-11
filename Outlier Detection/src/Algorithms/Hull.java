package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


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

		if(index==0){ 
			if(hulls.get(index+1)>hulls.get(index)+sigma){
				return 1.5;
			}
			if(hulls.get(index+1)>hulls.get(index)+2*sigma){
				return 2.0;
			}
			else{
				return 1.0;
			}
		}
		if(index==1){
			if(hulls.get(index-1)>hulls.get(index)+sigma){
				return 1.0; 
			}
			if(hulls.get(index-1)>hulls.get(index)+2*sigma){
				return 0.75;
			}
			else{
				return 1.5;
			}
		}
		if(index==size-1){
			if(hulls.get(index-1)<hulls.get(index)+sigma){
				return 1.5; 
			}
			if(hulls.get(index-1)<hulls.get(index)+2*sigma){
				return 2.0;
			}
			else{
				return 1.0;
			}
		}	
		if(index==size-2){
			if(hulls.get(index+1)>hulls.get(index)+sigma){
				return 1.0;
			}
			if(hulls.get(index+1)>hulls.get(index)+2*sigma){
				return 0.75;
			}
			else{
				return 1.5;
			}
		}
		return 0.0;
	} 


	@Override
	public void calculate(Deque<Integer> data){
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(data);
		hulls.clear();
		list.remove(list.size()-1);
		DescriptiveStats ds = new DescriptiveStats(list);
		this.sigma = ds.getStandardDev(ds.getMean());
		this.value = data.getLast();
		hulls.addAll(list);
		Collections.sort(hulls);
	}

	@Override
	public String toString(){
		return this.hulls.toString() + " " + value;

	}




}
