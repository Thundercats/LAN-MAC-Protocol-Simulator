/*
 * Like LMP, but super
 */
package lmp;

/**
 *
 * @author Adrian
 */
public class SuperLMP {
    public static Double result;
    public static final int SLOT_LIMIT=20000; // Run until this many time slots have passed
    public static final int AVG_PACKET_SIZE = 512; // Average packet size
    public static final double CONVERSION = 0.015625; // The conversion factor to go from bytes -> time slots
    public static void main(String[] args) {
        int throughAverage=0;
        for(int i=0;i<=50;i++)
        {
        Simulator mario = new Simulator(20, 20.0);
        double sum = 0;
        while((mario.getPacketsSent() * AVG_PACKET_SIZE * CONVERSION) <= SLOT_LIMIT)
        {
        result = mario.send();
        if(result==-1)
        {
             System.out.println("Hooray! You sent at time: "+mario.tryAgain());
        }
        else
            System.out.println("Hooray! You sent at time: "+result);
        
        sum=+mario.getSentTime(); // Add the sent times together
        System.out.println("Packets sent successfully: "+mario.getSuccessfulPacketsSent());
        }
        System.out.println("Calculated throughput is: " + (mario.getSuccessfulPacketsSent() * AVG_PACKET_SIZE * 512) / (SLOT_LIMIT * 51.2 * Math.pow(10, -6))); //Throughput!
        throughAverage+=(mario.getSuccessfulPacketsSent() * 8 * AVG_PACKET_SIZE ) / (SLOT_LIMIT * 51.2 * Math.pow(10, -6));
        }
        System.out.println("Average throughput is: " + throughAverage/50);
}
}
