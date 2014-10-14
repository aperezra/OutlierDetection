package Algorithms;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;




public class Density implements Algorithm {

	public Deque<Double> densities;
	public double max;
	public double lof;


	public Density() {
		super();
		this.densities = new ArrayDeque<Double>();
		this.max = 0;
		this.lof=0;
	}

	public Deque<Double> getDensities() {
		return densities;
	}

	public void setDensities(Deque<Double> densities) {
		this.densities = densities;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public int getSize(){
		return densities.size();
	}

	public int distance(int reference, int val) {
		int distance = Math.abs(val-reference);
		return distance;

	}

	/**
	 * Density.
	 *
	 * @param data the data
	 * @param p reference point to which is calculated the density
	 * @param x the point to which we are calculating the density
	 */
	public void calculate(Deque<Integer> data){
		double sum=0;
		int k=5;
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(data);
		int[] knn = knn(list,k);
		for (int i=0; i<knn.length;i++){
			sum+=(lrd(list,k,knn[i])/lrd(list,k,list.get(list.size()-1)));
		}
		this.lof=(sum/knn.length);
	}
	

	//The bigger ros is, the likelier is to be an outlier
	public double probOutlier() {
		return lof;
	}

	public double reachKDistance(int k, int o, int p, CopyOnWriteArrayList<Integer> list){
		List<Integer> distance= new ArrayList<Integer>();
		for(int i: list){
			distance.add(distance(o,i));
		}			
		int[] lowestValues = new int[k];
		Arrays.fill(lowestValues, Integer.MAX_VALUE);
		for(int n : distance) {
			if(n < lowestValues[k-1]) {
				lowestValues[k-1] = n;
				Arrays.sort(lowestValues);
			}
		}
		double reachKDistance= Math.max(lowestValues[k-1], distance(p,o));
		return reachKDistance;
	}


	public double lrd(CopyOnWriteArrayList<Integer> list,int k, int p){
		double sum=0;
		int[] knn = knn(list, k);
		for (int i=0; i<knn.length; i++){
			sum+=reachKDistance(k,knn[i],p, list);
		}	
		return (sum/k);
	}

	public int[] knn(List<Integer> data, int k){
		List<Integer> knn = new ArrayList<Integer>();
		int[] result= new int[k];
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(data);
		for (int i=0;i<list.size()-1;i++){
			knn.add(distance(list.get(i),list.get(list.size()-1)));
		}
		Collections.sort(knn);
		for (int i=0; i<result.length;i++){
			if(list.get(list.size()-1)>knn.get(i)){
				result[i]=list.get(list.size()-1)-knn.get(i);
			}
			else{
				result[i]=list.get(list.size()-1)+knn.get(i);
			}
		}
		return result;
	}

	@Override
	public void deleteFirst() {
		densities.removeFirst();
	}

	@Override
	public String toString(){
		return this.densities.toString();	
	}



}
