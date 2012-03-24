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
    private static final int TIMES_TO_RUN = 100; // how times to simulate transmission between the # of stations
    //private static final double LAMBDA = 20.00; //MAX Lambda size
    
    private static ArrayList<Node2> nodes;
    private static ArrayList<Node2> sortedNodes;
    private static LinkedList<Node2> collidingNodes;
    
    private static double smallestValue;
    
    public static double simulate(double lambda)
    {
        nodes = new ArrayList();
    	
        for (int i = 0; i < NUM_OF_NODES; i++)
        {  
               nodes.add(new Node2(lambda));           // is to add new node, intialize it's time to 0 
               //System.out.println("# " + nodes.get(i).toString());
        }
        
        sortedNodes = nodes;
        Collections.sort(sortedNodes);
        smallestValue = sortedNodes.get(0).getTime();
        
        collidingNodes = new LinkedList();
        while(true)
        {
            for (int j = 1; j < NUM_OF_NODES; j++) //start at 1 because, don't need to compare with 0th since it's min
            {
                double difference;
                difference = smallestValue - sortedNodes.get(j).getTime();

                if (difference <= 1) {
                    collidingNodes.add(sortedNodes.get(j)); //basically keep a collection of all of the nodes that have collided
                } else {
//                boolean successful = false;
//                System.out.println("is it? " + collidingNodes.get(j));
//                if (collidingNodes.isEmpty()) 
//                {
//                    successful = true;
//                    System.out.println("TRANSMISSION SUCCESSFUL"); // DO SOMETHING!
//                } else {
//                    while (!collidingNodes.isEmpty()) {
//                        System.out.println("NOT SUCCESSFUL"); // DO SOMETHING!
//                        collidingNodes.pop().send(lambda);
//                        //sortedNodes.add(j, collidingNodes.pop()); // Not positive, but I think this is right
//                    }
//                }
                    
                }
            }
        }
        
       //}
      /**
         * If there is nothing in CollidingNodes
         *    TRANSMISSION SUCCESSFUL
         * else
         *   Loop through those nodes
         *      node.send()
         */
        return smallestValue;
    }
   

    
    public static void main(String[] args) {
    	int i;
        double sum;
        double lambda;
        
        for(lambda = 2.0; lambda <= 20.00; lambda += 2.0)
        {
            sum = 0;
            for(i = 0; i < TIMES_TO_RUN; i++)
            {
                sum += simulate(lambda);
            }
            System.out.println("Lambda\t" +  lambda + "\tsum\t" +  sum/TIMES_TO_RUN);
        }
        
    }
    	
}