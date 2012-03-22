package lmp;

import java.util.ArrayList;
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
    //private static final int MAX_LAMBDA = 20;
    //private static Random rand;
    
    public static double simulate()
    {
    	ArrayList<Node2> network = new ArrayList();
        Node2 node;
    	
        //double current;
        //double next;
        //double prev = -1;
        //current = node.Poisson();
        //next = current + node.Poisson();
        

        for (int i = 0; i < NUM_OF_NODES; i++)
        {  
               node = new Node2(0);         // So basically what I'm trying to do here
               network.add(node);           // is to add new node, intialize it's time to 0
               network.set(i, node.send()); // then set EACH node to a randomized X value...clearly set WONT work 
                                            // Some help/new direction would be appreciated! 
               System.out.println("# " + network.get(i).toString());
        }
        
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