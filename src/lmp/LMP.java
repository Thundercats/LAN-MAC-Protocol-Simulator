package lmp;

import java.util.ArrayList;

/**
 *
 * @author T-CAPS
 */
public class LMP {
    private static final int NUM_OF_NODES = 5;
    /**
     * @param args the command line arguments
     */
    
    public static int simulate(int nodes)
    {
        ArrayList<Node> network = new ArrayList<Node>(); // Store the Nodes here
        int t = 0; // initial time t is 0
            
        for(int i = 0; i < nodes; i++)
        {
           network.add(new Node()); //Add nodes into the ArrayList
           network.get(i).clear(); //Transmits the node we just created
        }
        
        while(true)
        {
            int count = 0;
            //int j = -1;
            
            for (int i = 0; i < nodes; i++)
            {
            	// get the node and if transmit is 
                if(network.get(i).transmit(t))
                {
                    ++count;
                    System.out.println("count is " + count);
                }
            }
            //System.out.println("count is " + count);
            if(count == 1)
            {
                //System.out.println("t is " + t);
                return t;
            }
            else if(count > 1)
            {
                for(int i = 0; i < nodes; i++)
                {
                	System.out.println("time is " + t);
                	
                    if(network.get(i).transmit(t))
                    {
                    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>INSIDE");
                        network.get(i).collide();
                    }
                }
            }
            
            ++t; // time t is incremented
           // System.out.println("here");
        }
    }
    
    public static void main(String[] args) {
        int simulated = 0;
        for(int i = 0; i < 100; i++)
        {
            simulated += simulate(2);
        }
        
        System.out.println(simulated);
    }
}