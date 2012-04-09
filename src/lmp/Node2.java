package lmp;

import java.util.PriorityQueue;
import java.util.Random;

/**
 *
 * @author T-CATS
 */
public class Node2 implements Comparable<Node2> {
	//private static double LAMBDA = 20.0; //added temporarily to test
	private Double time; //TIME TO SEND
	private Double lambda;
	private int n; // Number of collisions
	//new node has no collisions
	private boolean collided;
	private Random ran;
	private PriorityQueue<Packet> packetQueue;


	/**
	 * Initializes a new Node at time t
	 */
	public Node2(double aLambda)
	{
		lambda = aLambda;
		time = 0.0;
		collided = false;
		packetQueue = new PriorityQueue<Packet>();
	}

	/**
	 * This gives our random variable
	 * @param lambda the lambda value
	 * @return random variable
	 */
	public double poisson(double aLambda)
	{
		double u;
		double val;

		u = Math.random();  

		val = (-1) * Math.log(u) * aLambda; // x= -λ log u
		return val;
	}

	/**
	 * Adds Poisson X value to current time
	 */
	public void send(double aLambda)
	{
		n=0; // Resets the number of collisions. Used for backoff()
		time += poisson(aLambda);
		//should we keep a collection of times as well?
	}


	@Override
	public int compareTo(Node2 o) 
	{
		return this.time.compareTo(o.time);
	}

	public double getLambda()
	{
		return lambda;
	}

	public double getTime()
	{
		return time;
	}

	/**
	 * Checks to see if the node collided
	 * @return true if collided, false otherwise
	 */
	public boolean isCollided()
	{
		//returns the status of the node if it is collided or not
		return collided;
	}

	/**
	 * Sets the boolean value of collided to true if collided
	 */
	public void didCollided()
	{
		collided = true;
	}

	/**
	 * Sets the boolean value of collided to false
	 */
	public void notCollided()
	{
		collided = false;
	}

	@Override
	public String toString()
	{
		return "Start: "+ time + "\n"; // Formats the String a little more neatly
	}

	public int backoff() // version of backoff that does not rely on an external parameter
	{
		n++; // number of collisions seen so far
		ran = new Random(n);
		int min = 0;
		int delay = 0;
		delay = ((int) ((Math.pow(2, n)) - 1)) + ran.nextInt();
		return delay; // * BACKOFF_CONSTANT;
	}

	public int backoff(int numberCollision) 
	{
		int n = numberCollision; // number of collisions seen so far
		ran = new Random(n);
		int min = 0;
		int delay = 0;
		delay = ((int) ((Math.pow(2, n)) - 1)) + ran.nextInt();
		// System.out.println("num of delay " + delay);

		// delay += min + (int)(Math.random() * ((delay - min) + 1)); // min +
		// (mathwhatever) * ((Max - Min) + 1); range is between 0...n
		// 2^k-1
		// delay = (int) (Math.random()); // Randomly decides if k will be
		// incremented or not

		return delay; // * BACKOFF_CONSTANT;
	}

	public Packet createPacket(int i, double time) 
	{
		return new Packet(i, time);
	}

	public void addPacketsToQueue(Packet aPacket) 
	{
		packetQueue.add(aPacket);
	}

	public Packet getNextPacketToBeTransmitted() 
	{
		return packetQueue.poll();
	}

	/**
	 * This gives our random variable
	 * @param lambda the lambda value
	 * @return random variable
	 */
	/* public static double Poisson(double lambda)
    {
    	double u = 0;
    	double val;
    	Random rand;
    	rand = new Random();
    	double max = Double.MAX_VALUE;
    	double min = Double.MIN_VALUE;

    	while (u == 0)
    	{

    	  //u = Math.random()/Double.MAX_VALUE;;
          //u = rand.nextInt();
    		u = (min + (Math.random() * ((max - min) + 1))) / Double.MAX_VALUE;
          //System.out.println("val IS: " + u);
    	}
    	val = - Math.log(u)*lambda; // x= -λ log u
        //System.out.println("val IS: " + val);


    	return val;
    }
	 */
}