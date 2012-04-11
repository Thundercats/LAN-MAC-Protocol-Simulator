package lmp;

import java.util.ArrayList;
import java.util.Scanner;

public class Network
{
    //private static ArrayList<Double> xVal = new ArrayList();
    private static Node2 node;
    private static int numOfStations = 20;
    // private static int RUNCOUNT = 100;
    private static int MAX_TIME_SLOT = 100;
    private static Double min = 0.0;
    
    private static int numOfSuccessfulPackets;
    private static int timeSuccessful;
    private static ArrayList<Node2> nodes = new ArrayList(); // Will eventually store nodes
    
    
    // made this static global
    private static int indexOfMin = 0;
    
    
    public static Double simulate(double lambda) 
    {
        int collisionCount = 0;
        numOfSuccessfulPackets = 0;
        timeSuccessful = 0;
        Packet currentPacket, packetToBeTransmitted;
        
        //clear this list of nodes breh
        nodes.clear();
        
        // creating individual stations and storing them into
        // a arraylist with each of their own contention time...
        for (int i = 0; i < numOfStations; i++)
        {
            node = new Node2(lambda);
        	nodes.add(node); //Add nodes into the ArrayList
        	nodes.get(i).send(lambda); //generate contention time
                
            currentPacket = node.createPacket(i, node.getTime()); //creating packets
            node.addPacketsToQueue(currentPacket); //adding packets
        }

        packetToBeTransmitted = node.getNextPacketToBeTransmitted();
        
        // we need to get the minimum contention
        min = getMinContention(nodes);
        
        while (true) 
        {
            boolean noCollision = true;
            
            for (int j = 0; j < numOfStations; j++) //Go through loop and check whether there is collision at the particular j
            {
                if (nodes.get(j).getTime() - min <= 1 && j != indexOfMin) 
                //if(nodes.get(j).getTime() - packetToBeTransmitted.getStationTime() <= 1 && j != indexOfMin) doesn't do right thing just yet
                {
                    noCollision = false;
                }
            }
            
            if (noCollision)
            {
                return min;
            } 
            else 
            {
                
                //xVal.set(indexOfMin, min + node.poisson(lambda));
            	// add poisson to current contention time to current node...
                nodes.get(indexOfMin).send(lambda);
                
                collisionCount++;
                //xVal.set(indexOfMin, min + node.backoff(collisionCount));
                //indexOfMin = getMin();
                //min = xVal.get(indexOfMin); //updated to new min
                
                // getting new min
                min = getMinContention(nodes);
            }
        }
    }
    
    
    /**
     * Gets the minimum contention time of a list of nodes
     * @param list the list of nodes
     * @return the minimum contention time
     */
	public static double getMinContention(ArrayList<Node2> list)
	{
		double min = 0.0;

		// min is the first station
		min = list.get(0).getTime();
		indexOfMin = 0;

		for (int i = 0; i < list.size(); i++) 
		{
			// see if current station is min contention time

			if (list.get(i).getTime() < min) 
			{
				min = list.get(i).getTime();
				indexOfMin = i;
			}
		}

		return min;
	}
    
    public static void main(String[] args) 
    {
        /**
         * throughput = (numOfSuccessfulPackets * 8 * 512) / totalTime; (time taken to successfully transmit)
         * delay = (timeSentSuccessfully - timeCreated);
         * delay jitter (variance?)
         */
        Scanner in = new Scanner(System.in);
        Double sum = 0.0;
        Double lambda = 0.0;
        double throughput = 0.0;
        
        System.out.println("Welcome to the LAN-MAC-Protocol simulator! Edit values? Y/N\nDefaults to N="+MAX_TIME_SLOT+" and lambda=20.0 to 4.0");
        if((in.next().toLowerCase().compareTo("y"))==0)
        {
            System.out.println("Please enter a value for lambda: ");
            lambda = in.nextDouble(); // Read user input for lambda
            System.out.println("Please enter a value for N (number of stations): ");
            MAX_TIME_SLOT = in.nextInt(); // Read user input for N
            System.out.println("Packets do not work for now.");
            
            // Here comes the actual work:
            for (int i = 0; i < MAX_TIME_SLOT; i++) 
            {
                sum += simulate(lambda);
                
            }
            System.out.println("Lambda\t" + lambda + "\tsum\t" + sum / MAX_TIME_SLOT);
            
            System.exit(0); // Exit and return 0
        }
        
        for (lambda = 20.0; lambda >= 4.0; lambda -= 2.0) 
        {
            for (int i = 0; i < MAX_TIME_SLOT; i++) 
            {
                sum += simulate(lambda);
               // throughput += (getPackets() * 8 * 512) / getTime();
                
            }
            
            System.out.println("Lambda\t" + lambda + "\tsum\t" + sum / MAX_TIME_SLOT);
            //System.out.println("Lambda\t" + lambda + "\tsum\t" + throughput);
            
            sum = 0.0;
        }
        
    }
}