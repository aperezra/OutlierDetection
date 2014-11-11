package Algorithms;

import java.util.Deque;
import java.util.concurrent.CopyOnWriteArrayList;

public class Statistic implements Algorithm {

	double probOutlier;
	int mean;
	double sigma;
	
	public Statistic(){
		super();
		this.probOutlier=0;
	}
	
	
	@Override
	public void calculate(Deque<Integer> data) {	
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(data);
		list.remove(list.size()-1);
		DescriptiveStats ds = new DescriptiveStats(list);
		this.mean = ds.getMean();
		this.sigma = ds.getStandardDev(mean);
		if(data.getLast()>mean){
			this.probOutlier=((data.getLast()-mean)/sigma);
		}
		if(data.getLast()<mean){
			this.probOutlier=((mean-data.getLast())/sigma);
		}
	}

	@Override
	public double probOutlier() {
		return probOutlier;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteFirst() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString(){
		return ("mean " +mean+" sigma "+sigma);
	}

}
