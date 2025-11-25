/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.ArrayList;

/**
 *
 * @author landr
 */
public abstract class Army {
    
    
    public Army(){
    }
    
    //Método hallar mayor dice
    public int maxDice(ArrayList<Integer> dice) {
        int aux = 0;
        for (int i = 0; i < dice.size(); i++) {
            if (dice.get(i).intValue() > dice.get(aux).intValue()) {
                aux = i;
            }
        }
        return aux;
    }
    
    //Método hallar menor dice
    public int minDice(ArrayList<Integer> dice) {
        int aux = 0;
        for (int i = 0; i < dice.size(); i++) {
            if (dice.get(i).intValue() < dice.get(aux).intValue()) {
                aux = i;
            }
        }
        return aux;
    }
    abstract ArrayList<Integer> attack(ArrayList<Integer> attackDice);
    
}
