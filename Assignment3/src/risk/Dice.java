/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author landr
 */
public class Dice {
    
    
    
    public static ArrayList<Integer> attackDice(int armies)
    {
        ArrayList <Integer> out = new ArrayList<>();
        for(int i = 0; i < armies && i < 3; i++){
            out.add((Integer)(int)(Math.random() * 6) + 1);
        }
        Collections.sort(out);           
        return out;
    }
    
    public static ArrayList<Integer> defenseDice(int armies)
    {
        ArrayList <Integer> out = new ArrayList<>();
        for(int i = 0; i < armies && i < 2; i++){
            out.add((Integer)(int)(Math.random() * 6) + 1);
        }
        Collections.sort(out);           
        return out;
    }
    
}
