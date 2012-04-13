package lmp;

import java.util.ArrayList;
import java.util.Collections;

public class Network 
{
    private static ArrayList<Double> xVal = new ArrayList(); // contention time arraylist
    private static Node node; // a node instance
    private static int numOfStations = 20; // # of current stations
    private static int MAX_TIME_SLOT = 20000; // max # of time slots to run up till
    private static Double min = 0.0; // min # constant
    private static Packet minPacket, collidedPacket; // the minimum time of a packet, the packet that collided
    
    private static int numOfSuccessfulPackets; // the number of successful packets so far
    private static int timeSuccessful; // the number of successful packets transmitted
    
    private static ArrayList<Node> nodes = new ArrayList(); // a arraylist of nodes/stations
    private static ArrayList<Packet> packets = new ArrayList(); // a arraylist of packets
    private static ArrayList<Packet> collidedPackets = new ArrayList(); // a arraylist of packets that collided
    private static Double currentTime = 0.0; // the current contention time of type double
    
    // made this static global
    private static int indexOfMin = 0; //Valid for C, the index of the packet that contains the minimum contention time
    
    /**
     * Creates packets for given number of stations
     * @param alambda the current lambda value
     */
    public static void generatePackets(double alambda) 
    {
        for (int i = 0; i < numOfStations; i++) 
        {
        	// creates new stations of type node
            node = new Node(i, alambda);
            // add stations to arraylist
            nodes.add(node);
            // then for every station, attempt to transmit
            // generating unique contention time for every station
            nodes.get(i).send(alambda);
        }
    }

    /**
     * Gets the generated packets, checks for collisions, adds backoff to collided packets, 
     * continues to generate new packets and adds to queue,
     * it will do all of this until the condition <= 20,000 time slots is met!
     * @param lambda 
     */
    public static void simulate(double lambda) 
    {
    	// We'll go ahead and initialize some variables
        //int collisionCount = 0;
        numOfSuccessfulPackets = 0;
        timeSuccessful = 0;
        //Packet currentPacket, packetToBeTransmitted, successfulPacket;
        
        /* clear this list of nodes breh
       	   need to clear because the arraylists are static */
        nodes.clear();
        packets.clear();
        
        /* we'll go ahead and make a temporary variable to hold the
           difference of the contention time of two stations to see
           if they collide or not between
           t-1 & t+1 */
        double difference;
        
        /* creating individual stations and storing them into
           a arraylist with each of their own contention time... */
        //for (int i = 0; i < numOfStations; i++) 
        //{
            //node = new Node(i, lambda);
            //nodes.add(node); //Add nodes into the ArrayList
            //nodes.get(i).send(lambda); //generate contention time
        
            generatePackets(lambda); //<--takes care of what we WERE doing with the loop before.
            						 // And lets go ahead and generate these packets for stations.
            
        //}

        packets = getSortedPacketList(); //get all of the packets that are waiting to be transmitted sorted  
        minPacket = packets.get(0); // the packet with the minimum contention time, the first element of the sorted list
        
        /* Let's go ahead and start simulating!
         * When the current time reaches the max time slots, STOP!
         */
        while (currentTime <= MAX_TIME_SLOT) 
        {
            boolean noCollision = true; // No collisions so far because we're just started...
            
            //Go through loop and check whether there is collision at the particular j
            for (int j = 0; j < numOfStations; j++) 
            {
            	// storing the difference
                difference = packets.get(j).getContentionInterval() - minPacket.getContentionInterval();
                
                // checking if stations collides, if it does then...
                if(difference <= 1 && j != minPacket.getStationName())
                {
                    noCollision = false; // that means we have a collision!
                    collidedPacket = packets.get(j); // So then we get the packet that collided with
                    								 // the minPacket and store it into a list of
                    								 // collidedPackets
                    break; // After getting the collided packet, break of the for loop!
                }
            }// end of for loop
            
            // if no stations collide with one another....
            if (noCollision)
            {
                currentTime += 8; //since successful, add 8 time slots
                numOfSuccessfulPackets++; //increase numberOfSuccessful packets, used for throughput calculations
                packets.get(0).setStationTime(currentTime); //set the transmittedTime
            } 
            // OTHERWISE, stations collided!
            else 
            {
                currentTime++; //increments time by only 1 time slot since its a collision
                
                minPacket.incrementCollision(); //increment collision for the specific packet
                collidedPacket.incrementCollision(); //increment collision for the collided packet
                
                // apply backoff to collided packets
                nodes.get(minPacket.getStationName()).backoff(minPacket.getCollisionCount(), minPacket.getContentionInterval()); //backoff
                nodes.get(collidedPacket.getStationName()).backoff(collidedPacket.getCollisionCount(), collidedPacket.getContentionInterval()); //backoff
                
                //packets.set(minPacket.getStationName(), minPacket);
                //packets.set(collidedPacket.getStationName(),collidedPacket);
                
                packets = getSortedPacketList(); //get all of the packets that are waiting to be transmitted sorted
                minPacket = packets.get(0); //min
                //minPacket = getMinPacket(packets);
                
                
                /**
                 * Need to add waiting packets into list....Need to generate new packets per station 
                 */
            }
            
            generatePackets(lambda); //generates new packets after checking and dealing with collisions...need to continue doing this until <= 20,000
        }
    }
    
    
    
    /**
     * Sorts and returns the packets in order
     * @return the sorted list of packets from ascending order
     */
    public static ArrayList<Packet> getSortedPacketList()
    {
        ArrayList<Packet> sortedList = new ArrayList();
        
        for(Node n: nodes)
        {
            sortedList.add(n.getNextPacketToBeTransmitted());
        }
        
        Collections.sort(sortedList);
        return sortedList;
    }
    
    
    /*
     * Current not using any of the min methods, instead I am just sorting them in the method ABOVE
     */
//    /**
//     * Gets the minimum contention time of a list of nodes
//     * @param list the list of nodes
//     * @return the minimum contention time
//     */
//    public static double getMinContention(ArrayList<Node> list)
//    {
//        double min = 0.0;
//
//        // min is the first station
//        min = list.get(0).getTime();
//        indexOfMin = 0;
//        for (int i = 0; i < list.size(); i++) {
//            // see if current station is min contention time
//
//            if (list.get(i).getTime() < min) {
//                min = list.get(i).getTime();
//                indexOfMin = i;
//            }
//        }
//
//        return min;
//    }
//    
//    public static Packet getMinPacket(ArrayList<Packet> list)
//    {
//        Packet min;
//
//        min = list.get(0);
//
//        for (int i = 0; i < list.size(); i++) {
//
//            if (list.get(i).getContentionInterval() < min.getContentionInterval()) {
//                min = list.get(i);
//            }
//        }
//
//        return min;
//    }
        
    
    /**
     * This method needs to be re-written for D! We don't need lambda loop because she will input her own lambda
     * but for testing purposes it's okay!
     * @param args 
     */
    public static void main(String[] args) 
    {
        /**
         * throughput = (numOfSuccessfulPackets * 8 * 512) / totalTime; (time taken to successfully transmit)
         * delay = (timeSentSuccessfully - timeCreated);
         * delay jitter (variance?)
         */
        Double sum = 0.0;
        Double lambda = 0.0;
        double throughput = 0.0;
        ArrayList<Packet> resultPackets = new ArrayList();
        
        for (lambda = 20.0; lambda >= 4.0; lambda -= 2.0) 
        {
            for (int i = 0; i < MAX_TIME_SLOT; i++) 
            {
                simulate(lambda);
                //System.out.println(" " + resultPackets.get(i).toString());
                
            }
            
                System.out.println("lambda " + lambda + " current time is " + currentTime);
                
            //System.out.println("lambda " + lambda + " current time is " + currentTime);
            //System.out.println("Lambda\t" + lambda + "\tsum\t" + sum / RUNCOUNT);
            //System.out.println("Lambda\t" + lambda + "\tsum\t" + throughput);
            throughput = (numOfSuccessfulPackets * 8 * 512) / (MAX_TIME_SLOT * 51.2 * Math.pow(10, -6));
            
            sum = 0.0;
        }
        
    }
}