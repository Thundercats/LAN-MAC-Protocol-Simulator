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
    public static final int SLOT_LIMIT = 20000; // Run until this many time slots have passed
    public static int AVG_PACKET_SIZE = 512; // Average packet size
    public static final double CONVERSION = 0.015625; // The conversion factor to go from bytes -> time slots
    public static int NUM_OF_STATIONS = 20;
    public static double lambda = 20.0;

    public static void main(String[] args) {
        int throughAverage = 0; // Used to calclate the average throughput
        for (int i = 0; i <= 50; i++) {
            Simulator mario = new Simulator(NUM_OF_STATIONS, lambda);
            double sum = 0; // The summation of sums!
            while ((mario.getPacketsSent() * AVG_PACKET_SIZE * CONVERSION) <= SLOT_LIMIT) {
                result = mario.send(); // Do an initial send. If it fails, tryAgain(), else, hooray!
                if (result == -1) {
                    System.out.println("Hooray! You sent at time: " + mario.tryAgain()); // Method tryAgain() internally loops
                } else {
                    System.out.println("Hooray! You sent at time: " + result);
                }

                sum = +mario.getSentTime(); // Add the sent times together
                System.out.println("Packets sent successfully at time " + mario.getSuccessfulPacketsSent());
            }
            // Print out the calculated throughput
            System.out.println("Calculated throughput is: " + (mario.getSuccessfulPacketsSent() * AVG_PACKET_SIZE * 512) / (SLOT_LIMIT * 51.2 * Math.pow(10, -6))); //Throughput!
            // Calculate the average throuput
            throughAverage += (mario.getSuccessfulPacketsSent() * 8 * AVG_PACKET_SIZE) / (SLOT_LIMIT * 51.2 * Math.pow(10, -6));
        }
        // I think this is obvious
        System.out.println("Average throughput is: " + throughAverage / 50);
    }
}
