
package lmp;

import java.util.ArrayList;

public class Network {

    private static ArrayList<Double> xVal = new ArrayList();
    private static Node2 node;
    private static int size = 20;
    private static int RUNCOUNT = 100;
    private static Double min = 0.0;

    public static Double simulate(double lambda) 
    {
        xVal.clear(); // ArrayList of x values
        int indexOfMin = 0;
        double current = 0.0;
        node = new Node2(lambda);
        
        for (int i = 0; i < size; i++) 
        {
            current = node.poisson(lambda);
            xVal.add(i, current);
        }

        indexOfMin = getMin();
        min = xVal.get(indexOfMin);

        while (true) 
        {
            boolean noCollision = true;
            
            for (int j = 0; j < size; j++) //Go through loop and check whether there is collision at the particular j
            {
                if (xVal.get(j) - min <= 1 && j != indexOfMin) 
                {
                    noCollision = false;
                }
            }
            
            if (noCollision) 
            {
                return min;
            } 
            else 
            {
                //TODO:
                //RE-TRANSMIT USING EXP BACKOFF
                //COUNT YO BITS
                xVal.set(indexOfMin, min + node.poisson(lambda));
                indexOfMin = getMin();
                min = xVal.get(indexOfMin); //updated to new min
            }
        }
    }

    public static int getMin() {
        int index = 0;
        double min = 0.0;

        min = xVal.get(0);
        
        for (int i = 0; i < xVal.size(); i++) 
        {
            if (xVal.get(i) < min) 
            {
                index = i;
                min = xVal.get(i);
            }
        }

        return index;
    }

    public static void main(String[] args) 
    {
        Double sum = 0.0;
        Double lambda = 0.0;
        
        for (lambda = 20.0; lambda >= 4.0; lambda -= 2.0) 
        {
            for (int i = 0; i < RUNCOUNT; i++) 
            {
                sum += simulate(lambda);
            }

            System.out.println("Lambda\t" + lambda + "\tsum\t" + sum / RUNCOUNT);
            sum = 0.0;
        }
    }
}