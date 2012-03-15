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
    
    public static ArrayList simulate(int nodes)
    {
        ArrayList<Node> network = new ArrayList(); // Store the Nodes here
        ArrayList<Integer> results = new ArrayList(); // Store the results
        int t = 0; // initial time t is 0
            
        for(int i = 0; i < nodes; i++)
        {
           network.add(new Node()); //Add nodes into the ArrayList
           network.get(i).clear(); //Transmits the node we just created
        }
        
        while(results.size()<nodes)
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
            if(count == 1) // Successful Transmission
            {
                //System.out.println("t is " + t);
                // return t;
                results.add(t); //Store the time
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
        return results; // Return the results
    }
    public static double avg(ArrayList average)
    {
        double total = 0;
        for(int i = 0;i <= average.size()-1; i++)
            total =  total + (Integer) average.get(i);
        return total / average.size();
    }
    
    public static void main(String[] args) {
        int simulated = 0;
        int runCount = 100;
        int n = 5; // number of stations
        // ArrayList  oneRun = simulate(5);
        ArrayList<Integer> avg = new ArrayList();
        
        	for(int i = 0; i < runCount; i++)
        	{
                    ArrayList  oneRun = simulate(5);
        		//simulated += simulate(1);
                        //System.out.println("Attempt #" + i + " simulate is " + oneRun);
                        //avg.add(simulate(5));
                    for(int j=0;j<oneRun.size();j++)
                        System.out.println("Arrival "+(j+1)+", value: "+oneRun.get(j));
                    System.out.println("Average is: " +avg(oneRun)+"\n"); // Prints the average of the above values
        	}
                
                // System.out.println("Average: " + avg(avg));
                //System.out.println("Successful runs " + simulated);
                //System.out.println("total sum: " + simulated);
    }
}