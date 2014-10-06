package Algorithms;
import java.util.Deque;;
// TODO: Auto-generated Javadoc
/**
 * From a Deque of integers compute the min, max, avg, med, & stand deviation.
 */
public class DescriptiveStats
{

	/** The values. */
	Deque<Integer> values; 


	/**
	 * Instantiates a new descriptive stats.
	 *
	 * @param values the values
	 */
	public DescriptiveStats(Deque<Integer> values) {
		this.values = values;
	}





	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public Deque<Integer> getvalues() {
		return values;
	}


	/**
	 * Sets the values.
	 *
	 * @param values the new values
	 */
	public void setvalues(Deque<Integer> values) {
		this.values = values;
	}


	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	public int getMin()
	{
		int min = values.getFirst();
		for(int i: values){      
			if(min>i){
				min=i;
			}
		}
		return min;
	}

	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public int getMax()
	{
		int max = values.getFirst();
		for(int i: values){
			if(max<i)
				max=i;
		}
		return max;
	}


//	/**
//	 * Gets the median.
//	 *
//	 * @return the median
//	 */
//	public int getMedian()
//	{
//		Collections.sort(values);
//		int middle = values.size()/2;
//		if(values.size()%2==1){
//			return values.get(middle);
//		}
//		return (values.get(middle-1) + values.get(middle));
//	}


	/**
	 * Gets the mean.
	 *
	 * @return the mean
	 */
	public int getMean(){

		int mean = 0;
		int sum = 0;
		for(int i: values){
			sum += i;
		}
		if(values.size()!=0){
			mean = sum/values.size();
			return mean;
		}
		return 0;
	}

	/**
	 * Gets the standard dev.
	 *
	 * @param m the m
	 * @return the standard dev
	 */
	public double getStandardDev(double mean){
		double result = 0;
		double total = 0;
		for(int i: values){
			result += Math.pow(i-mean,2);
		}
		total = result/values.size();
		double standev = Math.sqrt(total);
		return standev;
	}

}