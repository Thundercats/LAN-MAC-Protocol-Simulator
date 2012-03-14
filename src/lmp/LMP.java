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
                    //System.out.println("count is " + count);
                }
            }
            //System.out.println("count is " + count);
            if(count == 1)
            {
                //System.out.println("t is " + t);
                return t;
            }
            else if(count > 1) // where collisions occurred
            {
                for(int i = 0; i < nodes; i++)
                {
                	//System.out.println("time is " + t);
                	
                    if(network.get(i).transmit(t))
                    {
                    	//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>INSIDE");
                        network.get(i).collide();
                    }
                }
            }
            
            ++t; // time t is incremented
           // System.out.println("here");
        }
    }
    public static double avg(ArrayList average)
    {
        double tot = 0;
        for(int i=0;i<=average.size()-1;i++)
            tot =  tot + (Integer) average.get(i);
        return tot/average.size();
    }
    
    public static void main(String[] args) {
        int simulated = 0;
        int runCount = 100;
        int n = 40; // number of stations
        ArrayList<Integer> avg = new ArrayList();

        for(int j = 1; j <= n; j++)
        {
        	for(int i = 0; i < runCount; i++)
        	{
        		simulated += simulate(j);
        		// System.out.println("Node "+j+ ", Iteration " +i+", value: " + simulate(j));
                        System.out.println(simulate(j));
                        avg.add(simulate(j));
        	}
                System.out.println("Average: " + avg(avg));
                System.out.println("Successful node #" + j + " " + simulated);
        }
        System.out.println("total sum: " + simulated);
    }
}