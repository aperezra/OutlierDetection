package Algorithms;

import java.util.ArrayDeque;
import java.util.Deque;

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
		Deque<Integer> trial = new ArrayDeque<Integer>(data);
		DescriptiveStats ds = new DescriptiveStats(trial);
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
