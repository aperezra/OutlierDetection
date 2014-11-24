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
		double os = 0;
		if(index==0){ 
			if(hulls.get(index+1)>hulls.get(index)+2*sigma){
				os= 2.0;
			}
			else{
				if(hulls.get(index+1)>hulls.get(index)+sigma){

					os= 1.75;
				}
				else{

					os= 1.5;
				}
			}
		}
		if(index==1){
			if(hulls.get(index-1)>hulls.get(index)+2*sigma){

				os= 0.75;
			}
			else{
				if(hulls.get(index-1)>hulls.get(index)+sigma){

					os= 1.0; 
				}

				else{

					os= 1.5;
				}
			}
		}
		if(index==size-1){
//			System.out.println("Aqui estoy " + this.sigma +" "+ hulls.get(index)+" " + hulls.get(index-1) +  " resta " + (hulls.get(index)-hulls.get(index-1)));
//			System.out.println((Double.compare((double)hulls.get(index-1)+2*sigma,(double)hulls.get(index))));
			if(Double.compare((double)hulls.get(index-1)+2*sigma,(double)hulls.get(index))<0){
				os= 2.0;
			}
			else{
				if(hulls.get(index-1)+sigma<hulls.get(index)){

					os= 1.75; 
				}

				else{
					os= 1.5;
				}
			}
		}	
		if(index==size-2){
			if(hulls.get(index+1)>hulls.get(index)+2*sigma){
				os= 0.75;
			}
			else{
				if(hulls.get(index+1)>hulls.get(index)+sigma){
					os= 1.0;
				}
				else{
					os= 1.5;
				}
			}
		}
		//if( os>1.5)System.out.println(" outlier score "+ os);
		return os;

	} 


	@Override
	public void calculate(Deque<Integer> data){
		hulls.clear();
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(data);
		hulls.addAll(list);
		Collections.sort(hulls);
		list.remove(list.size()-1);
		DescriptiveStats ds = new DescriptiveStats(list);
		this.sigma = ds.getStandardDev(ds.getMean());
		this.value = data.getLast();
	}

	@Override
	public String toString(){
		return this.hulls.toString() + " " + value + " sigma "+ this.sigma;

	}




}
