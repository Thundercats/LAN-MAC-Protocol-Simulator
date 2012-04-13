package lmp;

import java.util.PriorityQueue;
import java.util.Random;

/**
 *
 * @author srisreed
 */
public class Node implements Comparable<Node> {
    
    private Double lambda;
    private Double time;
    private double delayVal;
    private boolean collided;
    private Random ran;
    private int name;
    
    private PriorityQueue<Packet> packetQueue;
    
    
    public Node(int aName, double aLambda)
    {
        name = aName;
        lambda = aLambda;
        time = 0.0;
        collided = false;
        packetQueue = new PriorityQueue<Packet>();
    }
    
    public double backoff(int numOfTimesCollided, double currTime)
    {
        ran = new Random();
        int n = numOfTimesCollided;
        delayVal = ran.nextInt((int) (Math.pow(2, n))-1);
        return currTime + delayVal;
    }
    
    public double poisson(double aLambda)
    {
        double u;
        double val;
        
        u = Math.random();
        
        val = (-1) * Math.log(u) * aLambda;
        packetQueue.add(new Packet(name, val));
        return val;
    }
    
    public void send(double aLambda)
    {
        time += poisson(aLambda);
    }
    
    public Packet createPacket(int i, double time)
    {
          return new Packet(i,time);
    }
    
    public void addPacketsToQueue(Packet aPacket)
    {
        packetQueue.add(aPacket);
    }
    
    public Packet getNextPacketToBeTransmitted()
    {
        return packetQueue.poll();
    }
        
    
    public double getLambda()
    {
        return lambda;
    }
    
    public double getTime()
    {
        return time;
    }

    @Override
    public int compareTo(Node t) {
        return this.time.compareTo(t.time);
    }
    
}
