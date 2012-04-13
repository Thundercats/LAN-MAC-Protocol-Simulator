package lmp;

import java.util.ArrayList;
import java.util.Collections;

public class Network 
{
    private static ArrayList<Double> xVal = new ArrayList();
    private static Node node;
    private static int numOfStations = 20;
    private static int MAX_TIME_SLOT = 20000;
    private static Double min = 0.0;
    private static Packet minPacket, collidedPacket;
    
    private static int numOfSuccessfulPackets;
    private static int timeSuccessful;
    
    private static ArrayList<Node> nodes = new ArrayList(); // Will eventually store nodes
    private static ArrayList<Packet> packets = new ArrayList();
    private static ArrayList<Packet> collidedPackets = new ArrayList();
    private static Double currentTime = 0.0;
    
    // made this static global
    private static int indexOfMin = 0; //Valid for C
    
    /**
     * Creates packets for given number of stations
     * @param alambda 
     */
    public static void generatePackets(double alambda) {
        for (int i = 0; i < numOfStations; i++) {

            node = new Node(i, alambda);
            nodes.add(node);
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
        //int collisionCount = 0;
        numOfSuccessfulPackets = 0;
        timeSuccessful = 0;
        //Packet currentPacket, packetToBeTransmitted, successfulPacket;
        
        //clear this list of nodes breh
        nodes.clear();
        packets.clear();
        double difference;
        
        // creating individual stations and storing them into
        // a arraylist with each of their own contention time...
        //for (int i = 0; i < numOfStations; i++) 
        //{
            //node = new Node(i, lambda);
            //nodes.add(node); //Add nodes into the ArrayList
            //nodes.get(i).send(lambda); //generate contention time
            generatePackets(lambda); //<--takes care of what we WERE doing with the loop before
            
        //}

        packets = getSortedPacketList(); //get all of the packets that are waiting to be transmitted sorted  
        minPacket = packets.get(0); //min
        
        while (currentTime <= MAX_TIME_SLOT) 
        {
            boolean noCollision = true;
            
            for (int j = 0; j < numOfStations; j++) //Go through loop and check whether there is collision at the particular j
            {
                difference = packets.get(j).getContentionInterval() - minPacket.getContentionInterval();
                
                if(difference <= 1 && j != minPacket.getStationName())
                {
                    noCollision = false;
                    collidedPacket = packets.get(j);
                    break;
                }
            }
            
            if (noCollision)
            {
                currentTime += 8; //since successful add 8 time slots
                numOfSuccessfulPackets++; //increase numberOfSuccessful packets, used for throughput calculations
                packets.get(0).setStationTime(currentTime); //set the transmittedTime
            } 
            else 
            {
                currentTime++; //increments time by only 1 time slot since its a collision
                
                minPacket.incrementCollision(); //increment collision for the specific packet
                collidedPacket.incrementCollision(); //increment collision for the collided packet
                
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
     * @return 
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