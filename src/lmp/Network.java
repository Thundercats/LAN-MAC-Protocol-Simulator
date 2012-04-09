package lmp;

import java.util.ArrayList;

public class Network 
{
    private static ArrayList<Double> xVal = new ArrayList(); // This needs to go eventually
    private static ArrayList<Node2> nodes = new ArrayList(); // Will eventually store nodes
    // private static Node2 node;
    private static int numOfStations = 20;
    private static int RUNCOUNT = 100;
    private static Double min = 0.0;
    
    private static int numOfSuccessfulPackets;
    private static int timeSuccessful;
    
    
    public static Double simulate(double lambda) 
    {
        for(int i = 0; i < numOfStations; i++)
        {
           nodes.add(new Node2(lambda)); //Add nodes into the ArrayList
        }
        int indexOfMin = 0;
        double current = 0.0;
        int collisionCount = 0;
        xVal.clear();
        numOfSuccessfulPackets = 0;
        timeSuccessful = 0;
        
       // node = new Node2(lambda); // create a single node
        
        for (int i = 0; i < numOfStations; i++) 
        {
            current = nodes.get(i).poisson(lambda); // call poisson with a specified lambda
            xVal.add(i, current);
        }

        indexOfMin = getMin();
        min = xVal.get(indexOfMin);

        while (true) 
        {
            boolean noCollision = true;
            
            for (int j = 0; j < numOfStations; j++) //Go through loop and check whether there is collision at the particular j
            {
                if (xVal.get(j) - min <= 1 && j != indexOfMin) 
                {
                    // System.out.println("Collision!");
                    noCollision = false; // A collision!
                }
            }
            
            if (noCollision)
            {
                numOfSuccessfulPackets++;
                timeSuccessful += 8;
                return min;
            } 
            else // There was a collision
            {
                // FIX THIS LINE:
                // xVal.set(indexOfMin, min + node.poisson(lambda)); // TODO: change to Exp Backoff for Part D
                
                collisionCount++;
                //xVal.set(indexOfMin, min + node.backoff(collisionCount)); // Should be collisions of a particular node, not total collisions
                indexOfMin = getMin();
                min = xVal.get(indexOfMin); //updated to new min
            }
        }
    }
    
    public static int getTime()
    {
        System.out.println("This is " + timeSuccessful);
        return timeSuccessful;
    }
    
    public static int getPackets()
    {
        System.out.println("The packets are " + numOfSuccessfulPackets);
        return numOfSuccessfulPackets;
    }
    
    public static int getMin() 
    {
        int index = 0;
        double min = 0.0;

        min = xVal.get(0);
        
        for (int i = 0; i < xVal.size(); i++) 
        {
            if (xVal.get(i) < min) 
            {
                index = i;
                min = xVal.get(i);
            }
        }

        return index;
    }

    public static void main(String[] args) 
    {
        /**
         * throughput = (numOfSuccessfulPackets * 8 * 512) / totalTime; (time taken to successfully transmit)
         * delay = (timeSentSuccessfully - timeCreated);
         * delay jitter (variance?)
         */
        
        System.out.println(args.length); // used for invoking via terminal
        
        Double sum = 0.0;
        Double lambda = 0.0;
        double throughput = 0.0;
        
        for (lambda = 20.0; lambda >= 4.0; lambda -= 2.0) 
        {
            for (int i = 0; i < RUNCOUNT; i++) 
            {
                sum += simulate(lambda);
               // throughput += (getPackets() * 8 * 512) / getTime();
                
            }
            
            System.out.println("Lambda\t" + lambda + "\tsum\t" + sum / RUNCOUNT);
            //System.out.println("Lambda\t" + lambda + "\tsum\t" + throughput);
            
            sum = 0.0;
        }
    }
}