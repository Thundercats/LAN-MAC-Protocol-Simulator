package lmp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Part C
 * Simulate for a given value of Lambda, the average number of slot times needed
 * before a successful transmission, called the contention interval.
 * @author T-CAPS
 */
public class LMP2 {
    private static final int NUM_OF_NODES = 5; // the # of stations to transmit between
    private static final int TIMES_TO_RUN = 100; // how times to simulate transmission between the # of stations
    //private static final double LAMBDA = 20.00; //MAX Lambda size
    
    private static ArrayList<Node2> nodes;
    private static ArrayList<Node2> sortedNodes;
    //private static LinkedList<Node2> collidingNodes;
        
    public static double simulate(double lambda)
    {
        nodes = new ArrayList<Node2>();
    	
        for (int i = 0; i < NUM_OF_NODES; i++)
        {  
        	//creates and adds a Node2 object with lambda value
        	//when creating Node2, it also generates
        	//the first contention time interval for
        	//each node/station
        	nodes.add(new Node2(lambda));           // is to add new node, intialize it's time to 0 
            //System.out.println("# " + nodes.get(i).toString());
        }
        
        //copy of created nodes
        sortedNodes = nodes;
        //sorts the nodes in ascending order of time
        Collections.sort(sortedNodes);
        //sets the smallest value
        //smallestValue = sortedNodes.get(0).getTime();
        //create a list of colliding nodes
        //collidingNodes = new LinkedList();
        
        //difference of two contention intervals
        double difference;
        //time for one station and another
        double time1, time2;
                
        //lets run the simulation...
        //while there's a collision
        boolean collision = true;
        // the collision count so far...
        int collisionCount = 0;
        double totalTime = 0;
        
        while(collision)
        {
        	//start with the first 
        	//start at 1 because, don't need to compare with 0th since it's min
        	//^^took those out...
            for (int j = 0; j < sortedNodes.size(); j++) 
            {
            	time1 = sortedNodes.get(j).getTime();
                
                for (int k = j + 1; k < sortedNodes.size(); k++)
                {
                	time2 = sortedNodes.get(k).getTime();
                	difference =  time2 - time1;
                	
                	// if the nodes collide...
                	if (difference <= 1) 
                    {
                		//add colliding nodes to list
                        //collidingNodes.add(sortedNodes.get(j)); //basically keep a collection of all of the nodes that have collided
                		
                		//increment collisions seen so far
                		collisionCount++;
                		//set nodes that collided
                		sortedNodes.get(j).didCollided();
                		sortedNodes.get(k).didCollided();
                    }
                	
                	
                	/*COMMENTED OUT JUST CUZ...*/
                	/* 
                	else 
                	{
                		boolean successful = false;
                		System.out.println("is it? " +
                				collidingNodes.get(j));
                		if (collidingNodes.isEmpty())
                		{
                			successful = true;
                			System.out.println("TRANSMISSION SUCCESSFUL"); // DO SOMETHING!
                		} 

                		else {
                			while (!collidingNodes.isEmpty()) {
                				System.out.println("NOT SUCCESSFUL"); // DO SOMETHING!
                				collidingNodes.pop().send(lambda);
                				sortedNodes.add(j, collidingNodes.pop()); // Not
                				positive, but I think this is right
                			}
                		}

                	}
                	***********/
                	
                }//end of inner for loop
                
            }//end of out for loop
            
            
            //if there are no collisions at all
            //break out of the while loop
            if (collisionCount == 0)
            {
            	//totalTime = 0;
            	for (int i = 0; i < sortedNodes.size(); i++)
            	{
            		totalTime = totalTime + sortedNodes.get(i).getTime();
            	}
            	
            	break;
            }
            //otherwise....continue
            else
            {
            	//reset the collisions seen so far
            	collisionCount = 0;
            	
            	//go through sorted node and...
            	for (int i = 0; i < sortedNodes.size(); i++)
            	{
            		//go generate next contention time interval
            		//for each node that collided
            		//and unset node to not collided
            		if (sortedNodes.get(i).isCollided())
            		{
            			sortedNodes.get(i).send(lambda);
            			sortedNodes.get(i).notCollided();
            		}
            		//if the node did not collide,transmission successful, remove from
            		//sortedNode...I guess...
            		else
            		{
            			//add up total successful transmission of stations
            			totalTime = totalTime + sortedNodes.get(i).getTime();
            			sortedNodes.remove(i);
            		}
            		
            		
            	}
            	
            	//gotta sort the sortedNodes again with the 
            	//new time contention.....
            	//sorts the nodes in ascending order of time
                Collections.sort(sortedNodes);
            }
            
        }//end of while loop
        
       //}
      /**
         * If there is nothing in CollidingNodes
         *    TRANSMISSION SUCCESSFUL
         * else
         *   Loop through those nodes
         *      node.send()
         */
        return totalTime;
    }
   

    
    public static void main(String[] args) {
    	int i;
        double sum;
        double lambda;
        
        for(lambda = 2.0; lambda <= 20.00; lambda += 2.0)
        {
            sum = 0;
            for(i = 0; i < TIMES_TO_RUN; i++)
            {
                sum += simulate(lambda);
            }
            System.out.println("Lambda\t" +  lambda + "\tsum\t" +  sum/TIMES_TO_RUN);
        }
        
    }
    	
}