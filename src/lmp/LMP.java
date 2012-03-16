package lmp;

import java.util.ArrayList;

/**
 *
 * @author T-CAPS
 */
public class LMP {
    private static final int NUM_OF_NODES = 5; // the # of stations to transmit between
    private static final int TIMES_TO_RUN = 100; // how times to simulate transmission between the # of stations
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
        
        // keep going until we get the number of results up to the # of nodes
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
    
    /**
     * To calculate the average of the total arrival times of each successful transmission
     * @param arrivalTimes the list of individual arrival time of each successful transmission
     * @return the average 
     */
    public static double avg(ArrayList arrivalTimes)
    {
        double total = 0;
        for(int i = 0;i <= arrivalTimes.size()-1; i++)
        {
            total =  total + (Integer) arrivalTimes.get(i);
        }
        return total / arrivalTimes.size(); // returns the average
    }
    
    public static void main(String[] args) {
    	//int simulated = 0;
    	// int runCount = 100;
    	// int n = 5; // number of stations
    	// ArrayList  oneRun = simulate(5);
    	//ArrayList<Integer> avg = new ArrayList();

    	System.out.format("%-3s\t%-3s\t%-3s\t%-3s\t%-3s\t\n", "#"+1,"#"+2,"#"+3,"#"+4,"#"+5);
    	
    	for(int i = 0; i < TIMES_TO_RUN; i++)
    	{
    		ArrayList oneRun = simulate(NUM_OF_NODES);
    		
    		//simulated += simulate(1);
    		//System.out.println("Attempt #" + i + " simulate is " + oneRun);
    		//avg.add(simulate(5));
    		for(int j=0;j<oneRun.size();j++)
    		{
    			//System.out.print(" Arrival "+(j+1)+", value: "+oneRun.get(j));
    			System.out.format("%-3d\t", oneRun.get(j));
    		}
    		System.out.println("");
    		//System.out.println("Average is: " +avg(oneRun)+"\n"); // Prints the average of the above values
    	}

    	// System.out.println("Average: " + avg(avg));
    	//System.out.println("Successful runs " + simulated);
    	//System.out.println("total sum: " + simulated);
    }
}