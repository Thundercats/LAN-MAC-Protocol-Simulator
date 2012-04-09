/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lmp;

/**
 *
 * @author srisreed
 */
class Packet {
    
    private int stationName;
    private Double contentionInterval;
    private double transmittedTime;
    private int collisionCount;
    
    public Packet(int nameOfStation, double generatedTime)
    {
       contentionInterval = generatedTime;
       stationName = nameOfStation;
       transmittedTime = 0.0;
       collisionCount = 0;
    }
    
    public double getContentionInterval()
    {
        return contentionInterval;
    }
    
    public int getStationName()
    {
        return stationName;
    }
    
    public double getStationTime()
    {
        return transmittedTime;
    }
    
    public void setContentionInterval(double interval)
    {
        contentionInterval = interval;
    }
    
    public void setStationName(int name)
    {
        stationName = name;
    }
    
    public void incrementCollision()
    {
        collisionCount++;
    }
    
    public void setStationTime(double time)
    {
        transmittedTime = time;
    }
    
    public int getCollisionCount()
    {
        return collisionCount;
    }
    
    public String toString()
    {
        return contentionInterval+" "+stationName;
        
    }
}