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
        k=0;
    }
    /**
     * 
     * @param initial - In case one would want a different
     *      initial value for k
     */
    public Node(int initial)
    {
        k=initial;
    }
    
    /* *
     * Is the transmission successful?
     * @return 
     */
    public boolean isSuccessfull()
    {
        return true; // not actually always true
    }
    
    private double backoff()
    {
    	k += Math.random() % 2;
        return k*BACKOFF_CONSTANT;
    }
    
    
    
    /**
     * 
     * @return 
     */
    public boolean clear()
    {
        k=0;
        return true;
    }
}
