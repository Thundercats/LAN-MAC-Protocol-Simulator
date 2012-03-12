/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lmp;

/**
 *
 * @author firen
 */
public class Node {
    private double k;
    public Node()
    {
        //Fill this in
    }
    
    /* 
     * Is the transmission successful?
     */
    public boolean isSuccessfull()
    {
        return true;
    }
    
    public double backoff()
    {
        return k += Math.random() % 2;  
    }
}
