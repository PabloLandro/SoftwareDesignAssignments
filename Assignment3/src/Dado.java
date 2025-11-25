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
public class Dado {
    
    
    
    public static ArrayList<Integer> dadoAtaque(int ejercitos)
    {
        ArrayList <Integer> out = new ArrayList<>();
        for(int i = 0; i < ejercitos && i < 3; i++){
            out.add((Integer)(int)(Math.random() * 6) + 1);
        }
        Collections.sort(out);           
        return out;
    }
    
    public static ArrayList<Integer> dadoDefensa(int ejercitos)
    {
        ArrayList <Integer> out = new ArrayList<>();
        for(int i = 0; i < ejercitos && i < 2; i++){
            out.add((Integer)(int)(Math.random() * 6) + 1);
        }
        Collections.sort(out);           
        return out;
    }
    
}
