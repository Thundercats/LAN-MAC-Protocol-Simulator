package lmp;

/**
 *
 * @author firen
 */
public class Node2 {
    private static double LAMBDA = 20.0; //added temporarily to test
    private static double t; //TIME TO SEND
    
    /**
     * Initializes a new Node at time t
     */
    public Node2()
    {
        t = 0;
    }

    
    /**
     * This gives our random variable
     * @param lambda the lambda value
     * @return random variable
     */
    public double Poisson()
    {
    	double u = 0;
    	double val;
    	
        u = Math.random();  
         
        val = (-1) * LAMBDA * Math.log(u); // x= -λ log u
        return val;
    }
    
    /**
     * Adds Poisson X value to current time
     */
    public void send()
    {
        t += Poisson();
    }
}
