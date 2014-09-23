package Algorithms;
import java.util.*;
/**
 * Read from a standard input a series of exam scores,    
 * then compute the min, max, avg, med, & stand deviation.
 */
public class DescriptiveStats
{

	List<Integer> scores; 


	public DescriptiveStats(List<Integer> scores) {
		this.scores = scores;
	}





	public List<Integer> getScores() {
		return scores;
	}


	public void setScores(List<Integer> scores) {
		this.scores = scores;
	}


	public int getMin()
	{
		int min = scores.get(0);
		for(int i: scores){      
			if(min>i){
				min=i;
			}
		}
		return min;
	}

	public int getMax()
	{
		int max = scores.get(0);
		for(int i: scores){
			if(max<i)
				max=i;
		}
		return max;
	}

	public int getMedian()
	{
		Collections.sort(scores);
		int middle = scores.size()/2;
		if(scores.size()%2==1){
			return scores.get(middle);
		}
		return (scores.get(middle-1) + scores.get(middle));
	}


	public int getMean()
	{

		int mean = 0;
		int sum = 0;
		for(int i: scores){
			sum += i;
		}
		mean = sum/scores.size();
		return mean;
	}

	public double getStandardDev(int m)
	{
		int mean = m;
		double result =0;
		double total = 0;
		for(int i: scores){
			result += Math.pow(i-mean,2);
			

		}
		total = result/scores.size();
		double standev = Math.sqrt(total);
		return standev;
	}

}