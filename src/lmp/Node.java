/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lmp;

/**
 *
 * @author T-CAP
 */
public class Node {
    private int k;
    private static double BACKOFF_CONSTANT = 51.2; // μseconds! 
    public Node()
    {
        //Fill this in
    }
    
    /* 
     * Is the transmission successful?
     */
    public boolean isSuccessfull()
    {
        return true; // not actually always true
    }
    
    public double backoff()
    {
    	k += Math.random() % 2;
        return k*BACKOFF_CONSTANT;
    }
}
