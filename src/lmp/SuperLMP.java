/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lmp;

/**
 *
 * @author Adrian
 */
public class SuperLMP {
    public static Double result;
    public static void main(String[] args) {
        Simulator mario = new Simulator(20, 20.0);
        result = mario.send();
        if(result==-1)
            System.out.print("Oh noes!");
        else
            System.out.println("Hooray! You sent at time: "+result);
    }
}
