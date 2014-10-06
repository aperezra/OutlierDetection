package Algorithms;

import java.util.Deque;

public interface Algorithm {
	
	void calculate(Deque<Integer> data);
	
	double probOutlier();
	
	int getSize();
	
	void deleteFirst();


}
