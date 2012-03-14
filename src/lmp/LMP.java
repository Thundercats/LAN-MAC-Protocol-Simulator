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
        ArrayList<Node> network = new ArrayList(); // Store the Nodes here
        int t = 0;
        int i;
            
        for(i = 0; i < nodes; i++)
        {
           network.add(new Node()); //Add nodes into the ArrayList
           network.get(network.size()).clear(); //Transmits the node we just created
        }
        
        while(true)
        {
            int count = 0;
            int j = -1;
            
            for (i = 0; i < nodes; i++)
            {
                if(network.get(i).transmit(t))
                {
                    j = i;
                    ++count;
                }
            }
            if(count == 1)
            {
                return t;
            }
            else if(count > 1)
            {
                for(i = 0; i < nodes; i++)
                {
                    if(network.get(i).transmit(t))
                    {
                        network.get(i).backoff();
                    }
                }
            }
            
            ++t;
        }
    }
    
    public static void main(String[] args) {
        int simulated = 0;
        for(int i = 0; i < 100; i++)
        {
            simulated += simulate(NUM_OF_NODES);
        }
        
        System.out.println(simulated);
    }
}