/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lmp;

/**
 *
 * @author T-CAP
 */
public class Node {
    private int k, collisionCount, numAttempt; // k is the time slots, collisionCount is the number of collisions
    										   // numAttempt is the # of attempts of retransmission
    private static final double BACKOFF_CONSTANT = 51.2; // μseconds! BackOff Constant is the constant
    													 // being multiplied when a collision happens.
    													 // First collision will wait k * 51.2, where 
    													 // k = 2^n-1, and n = # of collisions so far which in
    													 // this case is 1.
    													 // n = collisionCount
    													 // delay = k * 51.2, n will be chosen at random between
    													 // 0 to 2^n-1
    
    
    /**
     * Instantiate a network node
     */
    public Node()
    {
        k=0;
    }
    /**
     * 
     * @param initial - In case one would want a different
     *      initial value for k
     */
    public Node(int initial)
    {
        k=initial;
    }
    
    /**
     * Uses exponential backoff whenever a collision happens
     * @return the delay
     */
    public double backoff(int numberCollision)
    {
    	int n = numberCollision; // number of collisions seen so far
    	int min = 0;
    	double delay = 0;
    	delay = (Math.pow(2, n)) - 1;
    	delay += min + (int)(Math.random() * ((delay - min) + 1)); // min + (mathwhatever) * ((Max - Min) + 1); range is between 0...n
    												   // 2^k-1
								   					   // Randomly decides if k will be incremented or not
    	
    	System.out.println("num of delay "  + delay);
        return delay * BACKOFF_CONSTANT;
    }
    
    /**
     * Method to be run if a collision is detected
     */
    public void collide()
    {
        collisionCount++; // collision incremented, seen so far
        System.out.println("collision count " + collisionCount);
        k += (int) backoff(collisionCount); // our next attempt
    }
    
    /**
     * 
     * @return 
     */
    public boolean transmit(int time) // What are we actually transmitting?
    {
    	System.out.println("k is  " + k);
    	System.out.println("t is " + time);
    	
        return k == time; // Very clever, but but isn't 'k' an index for determining backoff?
        				  // k is definitely the index for determining what backoff value to use
        				  // whenever a collision happens... -Donald
    }
    
    
    /**
     * 
     * @return 
     */
    public boolean clear()
    {
        k=0; // sets k to 0...our number of attempts
        collisionCount = 0; // sets the collisionCount to 0
        return true; 
    }
}
