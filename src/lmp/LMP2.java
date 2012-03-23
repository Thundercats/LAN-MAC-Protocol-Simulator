package lmp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Part C
 * Simulate for a given value of Lambda, the average number of slot times needed
 * before a successful transmission, called the contention interval.
 * @author T-CAPS
 */
public class LMP2 {
    private static final int NUM_OF_NODES = 5; // the # of stations to transmit between
    //private static final int TIMES_TO_RUN = 100; // how times to simulate transmission between the # of stations
    private static final double LAMBDA = 20;
    //private static Random rand;
    private static ArrayList<Node2> nodes;
    private static ArrayList<Node2> sortedNodes;
    private static LinkedList<Node2> collidingNodes;
    private static double smallestValue;
    
    public static double simulate()
    {
        nodes = new ArrayList();
        Node2 node;
    	

        for (int i = 0; i < NUM_OF_NODES; i++)
        {  
               // One way to do it:
               // Node2 node = new Node2(LAMBDA);         // So basically what I'm trying to do here
               
               // ...but I prefer this:
               nodes.add(new Node2(LAMBDA));           // is to add new node, intialize it's time to 0 
               System.out.println("# " + nodes.get(i).toString());
        }
        
        sortedNodes = nodes;
        Collections.sort(sortedNodes);
        smallestValue = sortedNodes.get(0).getTime();
        
        collidingNodes = new LinkedList();
        for (int j = 1; j < NUM_OF_NODES; j++) //start at 1 because, don't need to compare with 0th since it's min
        {
            double x;
            x = smallestValue - sortedNodes.get(j).getTime();
            if(x <= 0)
            {
                collidingNodes.add(sortedNodes.get(j)); //basically keep a collection of all of the nodes that have collided
            }
            else
            {
                break; //I know this is not good convention but we can add a boolean here or something
            }
        }
        
        if(collidingNodes.isEmpty())
            System.out.println("TRANSMISSION SUCCESSFUL"); // DO SOMETHING!
        else
        {
            while(!collidingNodes.isEmpty())
            {
                System.out.println("COLLISION!"); // DO SOMETHING!
                collidingNodes.pop().send(LAMBDA); // Not positive, but I think this is right
            }
        }
                
        /**
         * If there is nothing in CollidingNodes
         *    TRANSMISSION SUCCESSFUL
         * else
         *   Loop through those nodes
         *      node.send()
         */
        
        
        
        
        // while collision, adjacent times within +/- 1 slot
//        while( (current - prev < 1) || (next - current < 1) )
//        {
//            prev = current;
//            current = next;
//            next += Poisson(lambda);
//        }
//        
//        return current;
        return 0;
    }
    
    /**
     * Gets the minimum time value of a node/station
     * @return the index of the node/station (the station #)
     */
    public static int getMin()
    {
    	int index = 0;
    	double min = 0;
    	
    	min = nodes.get(0).getTime();
    	for (int i = 0; i < nodes.size(); i++)
    	{
    		if (nodes.get(i).getTime() < min)
    		{
    			index = i;
    			min = nodes.get(i).getTime();
    		}
    	}
    	
    	return index;
    }

    
    public static void main(String[] args) {
//    	int i;
//        double sum;
//        double lambda;
//        for(lambda = 2.0; lambda <= 20.00; lambda += 2.0)
////        {
////            sum = 0;
////            for(i = 0; i < TIMES_TO_RUN; i++)
////            {
////                //sum += simulate(lambda);
////                //System.out.println("Lambda\t" +  lambda + "\tsum\t" +  sum);
////            }
////        }
            
           simulate();
        
        
        
    }
    	
}